package gov.hud.lrs.services.bizservice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import gov.hud.lrs.common.dto.LrsApiV1DTOs.LenderSelectionAdjustmentDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.LiteLenderDTO;
import gov.hud.lrs.common.entity.Lender;
import gov.hud.lrs.common.entity.LenderIncreasedSelection;
import gov.hud.lrs.common.entity.UnderwriterIncreasedSelection;
import gov.hud.lrs.common.exception.NotFoundException;
import gov.hud.lrs.common.repository.LenderIncreasedSelectionRepository;
import gov.hud.lrs.common.repository.UnderwriterIncreasedSelectionRepository;
import gov.hud.lrs.common.security.SecurityService;
import gov.hud.lrs.common.service.LenderService;
import gov.hud.lrs.common.service.UnderwriterService;
import gov.hud.lrs.common.util.DateUtils;

@Service
public class IncreasedSelectionService {

	@Autowired private LenderIncreasedSelectionRepository lenderIncreasedSelectionRepository;
	@Autowired private UnderwriterIncreasedSelectionRepository underwriterIncreasedSelectionRepository;

	@Autowired private LenderService lenderService;
	@Autowired private SecurityService securityService;
	@Autowired private UnderwriterService underwriterService;

	public List<LenderSelectionAdjustmentDTO> getLenderIncreasedSelections() {
		List<LenderIncreasedSelection> lenderIncreasedSelections = lenderIncreasedSelectionRepository.findAll();
		List<LenderSelectionAdjustmentDTO> lenderSelectinAdjustmentDTOs = new ArrayList<LenderSelectionAdjustmentDTO>();

		for(LenderIncreasedSelection lenderIncreasedSelection : lenderIncreasedSelections) {
			LenderSelectionAdjustmentDTO lenderSelectionAdjustmentDTO = new LenderSelectionAdjustmentDTO();
			Lender lender = lenderIncreasedSelection.getLender();
			LiteLenderDTO liteLenderDTO = new LiteLenderDTO();
			liteLenderDTO.setLenderId(lender.getLenderId());
			liteLenderDTO.setName(lender.getName());
			lenderSelectionAdjustmentDTO.setLender(liteLenderDTO);
			lenderSelectionAdjustmentDTO.setStartDate(DateUtils.convertDateToNoonUtcDate(lenderIncreasedSelection.getEffectiveDate()));
			lenderSelectionAdjustmentDTO.setPercentToReview(new BigDecimal(lenderIncreasedSelection.getTargetFwdPct().intValue()));
			lenderSelectinAdjustmentDTOs.add(lenderSelectionAdjustmentDTO);
		}

		return lenderSelectinAdjustmentDTOs;
	}

	public List<LenderSelectionAdjustmentDTO> getUnderwriterIncreasedSelections() {
		List<UnderwriterIncreasedSelection> underwriterIncreasedSelections = Lists.newArrayList(underwriterIncreasedSelectionRepository.findAll());
		List<LenderSelectionAdjustmentDTO> lenderSelectionAdjustmentDTOs = new ArrayList<LenderSelectionAdjustmentDTO>();

		for(UnderwriterIncreasedSelection underwriterIncreasedSelection : underwriterIncreasedSelections) {
			LenderSelectionAdjustmentDTO lenderSelectionAdjustmentDTO = new LenderSelectionAdjustmentDTO();
			LiteLenderDTO liteLenderDTO = underwriterService.getUnderwriter(underwriterIncreasedSelection.getUnderwriterId());
			if (liteLenderDTO != null) {
				lenderSelectionAdjustmentDTO.setLender(liteLenderDTO);
			}

			lenderSelectionAdjustmentDTO.setStartDate(DateUtils.convertDateToNoonUtcDate(underwriterIncreasedSelection.getEffectiveDate()));
			lenderSelectionAdjustmentDTO.setPercentToReview(new BigDecimal(underwriterIncreasedSelection.getFwdPercent().intValue()));
			lenderSelectionAdjustmentDTOs.add(lenderSelectionAdjustmentDTO);
		}

		return lenderSelectionAdjustmentDTOs;
	}

	@Transactional
	public LenderIncreasedSelection createLenderIncreasedSelection(String mtgee5) {
		// just used to verify if it exists or not
		Lender lender = lenderService.getLender(mtgee5);
		if (lender == null) {
			throw new NotFoundException("No lender for lenderId " + mtgee5);
		}

		Date now = new Date();
		String userId = securityService.getUserId();
		LenderIncreasedSelection lenderIncreasedSelection = new LenderIncreasedSelection();
		lenderIncreasedSelection.setLender(lender);
		lenderIncreasedSelection.setTargetFwdPct(0);
		lenderIncreasedSelection.setTargetHecmPct(0);
		lenderIncreasedSelection.setEffectiveDate(now);
		lenderIncreasedSelection.setCreatedBy(userId);
		lenderIncreasedSelection.setCreatedTs(now);
		lenderIncreasedSelection.setUpdatedBy(userId);
		lenderIncreasedSelection.setUpdatedTs(now);
		return lenderIncreasedSelectionRepository.save(lenderIncreasedSelection);
	}

	@Transactional
	public void updateLenderIncreasedSelection(String lenderId, int percent) {
		LenderIncreasedSelection lenderIncreasedSelection = lenderIncreasedSelectionRepository.findOne(lenderId);
		if (lenderIncreasedSelection == null) {
			throw new NotFoundException("No LenderIncreasedSelection for lenderId " + lenderId);
		}

		lenderIncreasedSelection.setTargetFwdPct(percent);
		lenderIncreasedSelection.setTargetHecmPct(percent);
		lenderIncreasedSelection.setUpdatedBy(securityService.getUserId());
		lenderIncreasedSelection.setUpdatedTs(new Date());
		lenderIncreasedSelectionRepository.save(lenderIncreasedSelection);
	}

	@Transactional
	public void deleteLenderIncreasedSelection(String lenderId) {
		if (!lenderIncreasedSelectionRepository.exists(lenderId)) {
			throw new NotFoundException("No LenderIncreasedSelection for lenderId " + lenderId);
		}
		lenderIncreasedSelectionRepository.delete(lenderId);
	}

	@Transactional
	public UnderwriterIncreasedSelection createUnderwriterIncreasedSelection(String underwriterId) {
		if (underwriterService.getUnderwriter(underwriterId) == null) {
			throw new NotFoundException("No underwriter for underwriterId " + underwriterId);
		}
		UnderwriterIncreasedSelection underwriterIncreasedSelection = new UnderwriterIncreasedSelection();
		underwriterIncreasedSelection.setUnderwriterId(underwriterId);
		underwriterIncreasedSelection.setFwdPercent((short)0);
		underwriterIncreasedSelection.setHecmPercent((short)0);
		Date now = new Date();
		String userId = securityService.getUserId();
		underwriterIncreasedSelection.setEffectiveDate(now);
		underwriterIncreasedSelection.setCreatedBy(userId);
		underwriterIncreasedSelection.setCreatedTs(now);
		underwriterIncreasedSelection.setUpdatedBy(userId);
		underwriterIncreasedSelection.setUpdatedTs(now);
		return underwriterIncreasedSelectionRepository.save(underwriterIncreasedSelection);
	}

	@Transactional
	public UnderwriterIncreasedSelection updateUnderwriterIncreasedSelection(String underwriterId, short percent) {
		UnderwriterIncreasedSelection underwriterIncreasedSelection = underwriterIncreasedSelectionRepository.findOne(underwriterId);
		if (underwriterIncreasedSelection == null) {
			throw new NotFoundException("No UnderwriterIncreasedSelection for underwriterId " + underwriterId);
		}

		underwriterIncreasedSelection.setFwdPercent(percent);
		underwriterIncreasedSelection.setHecmPercent(percent);
		underwriterIncreasedSelection.setUpdatedBy(securityService.getUserId());
		underwriterIncreasedSelection.setUpdatedTs(new Date());
		return underwriterIncreasedSelectionRepository.save(underwriterIncreasedSelection);
	}

	@Transactional
	public void deleteUnderwriterIncreasedSelection(String underwriterId) {
		if (!underwriterIncreasedSelectionRepository.exists(underwriterId)) {
			throw new NotFoundException("No UnderwriterIncreasedSelection with underwriterId " + underwriterId);
		}
		underwriterIncreasedSelectionRepository.delete(underwriterId);
	}

}
