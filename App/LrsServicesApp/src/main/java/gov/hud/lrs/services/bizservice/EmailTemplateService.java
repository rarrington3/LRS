package gov.hud.lrs.services.bizservice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.hud.lrs.common.dto.LrsApiV1DTOs.EmailTemplateVersionDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.EmailTemplateVersionLiteDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.NameValuePairDTO;
import gov.hud.lrs.common.entity.Email;
import gov.hud.lrs.common.entity.EmailTemplate;
import gov.hud.lrs.common.entity.EmailTemplateVariable;
import gov.hud.lrs.common.entity.EmailTemplateVersion;
import gov.hud.lrs.common.exception.NotFoundException;
import gov.hud.lrs.common.repository.EmailRepository;
import gov.hud.lrs.common.repository.EmailTemplateVersionRepository;
import gov.hud.lrs.common.security.SecurityService;
import gov.hud.lrs.common.service.CommonEmailService;
import gov.hud.lrs.common.util.DateUtils;


@Service
public class EmailTemplateService {
	private static final String LINE_BREAK = "<br/>";
	
	@Autowired private EmailTemplateVersionRepository emailTemplateVersionRepository;
	@Autowired private EmailRepository emailRepository;

	@Autowired private SecurityService securityService;
	@Autowired private CommonEmailService commonEmailService;
	
	public List<EmailTemplateVersion> getAllEmailTemplateVersions(EmailTemplate  emailTemplate) {
		return emailTemplateVersionRepository.findByEmailTemplate(emailTemplate);
	}
	
	public EmailTemplateVersion getEmailTemplateVersion(String templateVersionId) {
		EmailTemplateVersion templateVersion = emailTemplateVersionRepository.findOne(templateVersionId);
		if(templateVersion == null) {
			throw new NotFoundException("TemplateVersionId not found : " + templateVersionId);
		}
		return templateVersion;
	}

	public EmailTemplateVersion updateAdminEmailTemplateVersion(String templateVersionId, EmailTemplateVersionDTO emailTemplateVersionDTO) {
		EmailTemplateVersion templateVersion = emailTemplateVersionRepository.findOne(templateVersionId);
		if(templateVersion == null) {
			throw new NotFoundException("TemplateVersionId not found : " + templateVersionId);
		}
		templateVersion.setName(emailTemplateVersionDTO.getEmailName());
		templateVersion.setVersionNumber(emailTemplateVersionDTO.getVersion());
		templateVersion.setDescription(emailTemplateVersionDTO.getDescription());
		Date now = new Date();
		templateVersion.setUpdatedTs(now);
		templateVersion.setUpdatedBy(securityService.getUserId());
		templateVersion.setActivatedTs(now);
		templateVersion.setActiveInd(emailTemplateVersionDTO.getIsActive() ? 'Y' : 'N');
		templateVersion.setEmailTemplateVersionId(emailTemplateVersionDTO.getId());
		templateVersion.setSubject(emailTemplateVersionDTO.getSubject());
		templateVersion.setBody(emailTemplateVersionDTO.getCopy());		
		emailTemplateVersionRepository.save(templateVersion);
		
		return templateVersion;
	}
	
	public EmailTemplateVersionLiteDTO convertEmailTemplateVersionToEmailTemplateVersionLiteDTO(EmailTemplateVersion templateVersion) {
		EmailTemplateVersionLiteDTO templateDTO = new EmailTemplateVersionLiteDTO();
		templateDTO.setEmailName(templateVersion.getName());
		templateDTO.setVersion(templateVersion.getVersionNumber());
		templateDTO.setLastModifiedDate(DateUtils.convertDateToNoonUtcDate(templateVersion.getUpdatedTs()));
		templateDTO.setLastModifiedBy(templateVersion.getUpdatedBy());
		templateDTO.setActivationDate(DateUtils.convertDateToNoonUtcDate(templateVersion.getActivatedTs()));
		templateDTO.setIsActive(templateVersion.getActiveInd() == 'Y' ? true : false);
		templateDTO.setId(templateVersion.getEmailTemplateVersionId());
		
		return templateDTO;
	}
	
	public EmailTemplateVersionDTO convertEmailTemplateVersionToEmailTemplateVersionDTO(EmailTemplateVersion templateVersion) {
		EmailTemplateVersionDTO templateDTO = new EmailTemplateVersionDTO();
		templateDTO.setEmailName(templateVersion.getName());
		templateDTO.setEmailCategory(templateVersion.getEmailTemplate().getDescription());
		templateDTO.setVersion(templateVersion.getVersionNumber());
		templateDTO.setDescription(templateVersion.getDescription());
		templateDTO.setLastModifiedDate(DateUtils.convertDateToNoonUtcDate(templateVersion.getUpdatedTs()));
		templateDTO.setLastModifiedBy(templateVersion.getUpdatedBy());
		templateDTO.setActivationDate(DateUtils.convertDateToNoonUtcDate(templateVersion.getActivatedTs()));
		templateDTO.setIsActive(templateVersion.getActiveInd() == 'Y' ? true : false);
		templateDTO.setId(templateVersion.getEmailTemplateVersionId());
		templateDTO.setSubject(templateVersion.getSubject());
		templateDTO.setCopy(templateVersion.getBody());
		
		List<NameValuePairDTO> documentVariables = new ArrayList<NameValuePairDTO>();
		for(EmailTemplateVariable templateVariable : templateVersion.getEmailTemplate().getEmailTemplateVariables()) {
			NameValuePairDTO nameVarDto = new NameValuePairDTO();
			nameVarDto.setName(templateVariable.getName());
			nameVarDto.setValue(templateVariable.getToken());
			documentVariables.add(nameVarDto);
		}
		
		templateDTO.setDocumentVariables(documentVariables);
		
		return templateDTO;
	}
	
	public EmailTemplateVersion duplicateEmailTempateVersion(String templateVersionId, String description) {
		EmailTemplateVersion templateVersion = emailTemplateVersionRepository.findOne(templateVersionId);
		if(templateVersion == null) {
			throw new NotFoundException("TemplateVersionId not found : " + templateVersionId);
		}
		EmailTemplateVersion duplicate = new EmailTemplateVersion();
		duplicate.setName(templateVersion.getName());
		duplicate.setEmailTemplate(templateVersion.getEmailTemplate());
		duplicate.setVersionNumber(emailTemplateVersionRepository.getMaxTemplateVersionNumber(templateVersion.getEmailTemplate().getEmailTemplateId()) + 1);
		duplicate.setDescription(description);
		duplicate.setActiveInd('N');
		duplicate.setSubject(templateVersion.getSubject());
		duplicate.setBody(templateVersion.getBody());
		duplicate.setCreatedBy(templateVersion.getCreatedBy());
		duplicate.setUpdatedBy(securityService.getUserId());
		Date now = new Date();
		duplicate.setCreatedTs(now);
		duplicate.setUpdatedTs(now);
		emailTemplateVersionRepository.save(duplicate);
		
		return duplicate;
	}
	
	public void activateEmailTemplateVersion(String templateVersionId) {
		EmailTemplateVersion templateVersion = emailTemplateVersionRepository.findOne(templateVersionId);
		if(templateVersion == null) {
			throw new NotFoundException("TemplateVersionId not found : " + templateVersionId);
		}
	
		// get the current active template if there is one and set active_ind to N
		EmailTemplateVersion currActiveTemplate = emailTemplateVersionRepository.findActiveEmailTemplateVersion(templateVersion.getEmailTemplate().getEmailTemplateId());		
		if(currActiveTemplate != null) {
			currActiveTemplate.setActiveInd('N');
			emailTemplateVersionRepository.saveAndFlush(currActiveTemplate);
		}
		templateVersion.setActiveInd('Y');
		Date now = new Date();
		templateVersion.setActivatedTs(now);
		templateVersion.setUpdatedBy(securityService.getUserId());
		templateVersion.setUpdatedTs(now);
		emailTemplateVersionRepository.save(templateVersion);
	}
	
	public void sendTestEmail(String templateVersionId, String emailAddress) {
		EmailTemplateVersion emailTemplateVersion = emailTemplateVersionRepository.findOne(templateVersionId);
		if(emailTemplateVersion == null) {
			throw new NotFoundException("Email template version " + templateVersionId + " not found");
		}
		
		String tokenReplacedSubject = commonEmailService.generateTestEmailText(emailTemplateVersion.getSubject());
		String body = emailTemplateVersion.getBody();
		body = body.replaceAll(System.lineSeparator(), LINE_BREAK);
		String tokenReplacedBody = commonEmailService.generateTestEmailText(body);
		Email email = commonEmailService.createEmail(tokenReplacedSubject, tokenReplacedBody, emailAddress, null, emailTemplateVersion.getEmailTemplate());
		emailRepository.save(email);
	}

}
