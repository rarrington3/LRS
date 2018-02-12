package gov.hud.lrs.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import gov.hud.lrs.common.entity.EmailTemplate;
import gov.hud.lrs.common.entity.EmailTemplateVersion;

@Repository
public interface EmailTemplateVersionRepository extends JpaRepository<EmailTemplateVersion, String> {
	
	public List<EmailTemplateVersion> findByEmailTemplate(EmailTemplate emailTemplate);
	
	@Query(value = "SELECT MAX(VERSION_NUMBER) FROM EMAIL_TEMPLATE_VERSION WHERE EMAIL_TEMPLATE_ID = ?1", nativeQuery = true)
	int getMaxTemplateVersionNumber(String emailTemplateId);
	
	@Query(value = "SELECT * FROM EMAIL_TEMPLATE_VERSION WHERE EMAIL_TEMPLATE_ID = ?1 AND ACTIVE_IND = 'Y'", nativeQuery = true)
	EmailTemplateVersion findActiveEmailTemplateVersion(String emailTemplateId);

	EmailTemplateVersion findByEmailTemplateCodeAndActiveInd(String emailTemplateCode, char activeInd);
}
