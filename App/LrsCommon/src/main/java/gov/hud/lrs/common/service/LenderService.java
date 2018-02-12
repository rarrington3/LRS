package gov.hud.lrs.common.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import gov.hud.lrs.common.dto.MuleLenderDTO.MuleLender;
import gov.hud.lrs.common.dto.MuleLenderDTO.MuleLender.AdministrativeContact.AdminContact;
import gov.hud.lrs.common.dto.MuleLenderSearchResultsDTO;
import gov.hud.lrs.common.dto.MuleLenderSearchResultsDTO.MuleLenderSearchResults;
import gov.hud.lrs.common.entity.Job;
import gov.hud.lrs.common.entity.Lender;
import gov.hud.lrs.common.repository.LenderRepository;
import gov.hud.lrs.common.security.FhacUser;
import gov.hud.lrs.common.security.SecurityService;
import gov.hud.lrs.common.util.Pair;
import gov.hud.lrs.common.util.Util;

@Service
public class LenderService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${lrs.mule.lenders.uri}") private String muleLendersUri;
	@Value("${lrs.mule.lenders.rootUri}") private String muleLendersRootUri;
	@Value("${lrs.mule.lenders.useOAuth}") private boolean muleLendersUseOAuth;
	@Value("${lrs.mule.piiMask}") private boolean piiMask;

	@Autowired private LenderRepository lenderRepository;

	@Autowired private SecurityService securityService;

	@PersistenceContext private EntityManager entityManager;

	private Executor executor = Executors.newFixedThreadPool(5);

	@Autowired
	@Qualifier("lenderServiceMuleClient")
	private MuleClient muleClient;

	// tries to load the lender from the DB, if not there then mule, if that fails then creates a dummy DB record
	@Transactional
	public Lender getOrCreateDummyLender(String lenderId) {
		Lender lender = getLender(lenderId);
		if (lender != null) {
			return lender;
		}

		lender = new Lender();
		lender.setLenderId(lenderId);
		lender.setName("<Unknown lender " + lenderId + ">");
		lender.setActiveInd('Y');
		lender.setDummyInd('Y');
		// email null intentionally
		lender.setCreatedBy(securityService.getUserId());
		lender.setCreatedTs(new Date());
		lender = lenderRepository.save(lender);

		return lender;
	}

	private Lender getLenderFromMule(String lenderId) {
		ResponseEntity<MuleLenderSearchResultsDTO> responseEntity;
		try {
			responseEntity = muleClient.get(
				muleLendersRootUri + muleLendersUri + "/" + lenderId,
				null,
				MuleLenderSearchResultsDTO.class,
				muleLendersUseOAuth
			);
		} catch (Exception e) {
			// the mule lender service is unreliable, so we do not propagate exceptions
			logger.error("Mule lender call failed out for lender ID " + lenderId, e);
			return null;
		}

		MuleLenderSearchResultsDTO muleLenderSearchResultsDTO = responseEntity.getBody();
		MuleLenderSearchResults lenderSearchResults = null;
		String statusCode = "";
		String statusMessage = "";
		if (muleLenderSearchResultsDTO != null) {
			lenderSearchResults = muleLenderSearchResultsDTO.getLenderSearchResults();
			if (lenderSearchResults != null) {
				statusCode = lenderSearchResults.getStatusCode();
				statusMessage = lenderSearchResults.getStatusMessage();
			}
		}

		if (!statusCode.equals("0")) {
			logger.debug("Mule get lender for lenderId " + lenderId + " failed with status code " + statusCode + ": " + statusMessage);
			return null;
		}

		MuleLender muleLender = lenderSearchResults.getLenders().get(0).getLender();
		if (!muleLender.getInstitutionId().equals(lenderId)) {
			// sanity check
			logger.debug("Mule lender service returned different lender ID (" + lenderSearchResults.getLenders().get(0).getLender().getInstitutionId());
			return null;
		}

		Lender lender = new Lender();
		lender.setLenderId(muleLender.getInstitutionId());
		lender.setName(muleLender.getInstitutionName());
		if (
			(muleLender.getAdministrativeContact() != null) &&
			(muleLender.getAdministrativeContact().getAdminContact() != null)
		) {
			AdminContact adminContact =	muleLender.getAdministrativeContact().getAdminContact();
			if (piiMask) {
				adminContact.setEmail("leapautobulkemails+" + muleLender.getInstitutionId() + "P@gmail.com");
				adminContact.setSecondaryEmail("leapautobulkemails+" + muleLender.getInstitutionId() + "S@gmail.com");
			}
			if (adminContact.getEmail() != null) {
				lender.setEmail(adminContact.getEmail().trim());
			}
			if (adminContact.getSecondaryEmail() != null) {
				lender.setSecondaryEmail(adminContact.getSecondaryEmail().trim());
			}
		}
		lender.setActiveInd('Y');
		lender.setDummyInd('N');
		lender.setCreatedBy(securityService.getUserId());
		lender.setCreatedTs(new Date());

		return lender;
	}

	@Transactional
	public Lender getLender(String lenderId) {
		Lender lender = lenderRepository.findOne(lenderId);
		if (lender != null) {
			return lender;
		}

		logger.debug("Lender " + lenderId + " not found in LRSDB; looking up in Mule");

		lender = getLenderFromMule(lenderId);
		if (lender != null) {
			lender = lenderRepository.save(lender);
			lenderRepository.flush();
		}

		return lender;
	}

	@Transactional
	public void refreshLenders(int count, Job job) {	// pass 0 for all lenders
		MDC.put("logFileName", "lenderRefresh");

		FhacUser fhacUser = securityService.getFhacUser();
		String userId = fhacUser.getUserId();
		Date now = new Date();

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		Date yesterday = calendar.getTime();

		TypedQuery<Lender> query = entityManager
			.createQuery(
				"select l from Lender l where " +
					"(l.dummyInd = 'Y') or (" +
						"(l.activeInd = 'Y') and " +
						"((l.updatedTs <= :yesterday) or (l.updatedTs is null))" +
					")" +
				"order by l.dummyInd desc, l.updatedTs",
				Lender.class
			)
			.setParameter("yesterday", yesterday);
		if (count > 0) {
			query.setFirstResult(0);
			query.setMaxResults(count);
		}
		List<Lender> lenders = query.getResultList();

		logger.debug("Refreshing lenders " + Util.map(lenders, l -> l.getLenderId()));

		CompletionService<Pair<Lender, Lender>> completionService = new ExecutorCompletionService<Pair<Lender, Lender>>(executor);
		int remainingFutures = 0;
		for (Lender lender : lenders) {
			completionService.submit(new Callable<Pair<Lender, Lender>>() {
				public Pair<Lender, Lender> call() {
					securityService.setFhacUser(fhacUser);
					Lender refreshedLender = getLenderFromMule(lender.getLenderId());
					return Pair.create(lender, refreshedLender);
				}
			});

			remainingFutures++;
		}

		while (remainingFutures > 0) {
			try {
				Future<Pair<Lender, Lender>> future = completionService.take();
				Pair<Lender, Lender> pair = future.get();
				Lender lender = pair.first;
				Lender refreshedLender = pair.second;
				if (refreshedLender != null) {
					if (refreshedLender.getLenderId().equals(lender.getLenderId())) {
						logger.debug("Refreshed lender " + refreshedLender.getLenderId() + " (" + (lenders.size() - remainingFutures + 1) + "/" + lenders.size() + ")");
						lender.setName(refreshedLender.getName());
						lender.setEmail(refreshedLender.getEmail());
						lender.setSecondaryEmail(refreshedLender.getSecondaryEmail());
						lender.setDummyInd('N');
						lender.setUpdatedBy(userId);
						lender.setUpdatedTs(now);
						lender = lenderRepository.save(lender);
						logger.debug("");
					} else {
						logger.warn("Requested lender " + lender.getLenderId() + " from mule but got back lender ID " + refreshedLender.getActiveInd());
					}

				} else {
					logger.warn("Couldn't refresh lender " + lender.getLenderId() + "; skipping");
				}
			} catch (Throwable t) {
				throw new RuntimeException(t);
			}

			remainingFutures--;
		}

		logger.debug("Done refreshing lenders");
		MDC.remove("logFileName");
	}

}
