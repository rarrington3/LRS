package gov.hud.lrs.common.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gov.hud.lrs.common.entity.ReviewProcessExceptionTypeRef;

@Repository
public interface ReviewProcessExceptionTypeRefRepository extends JpaRepository<ReviewProcessExceptionTypeRef, String> {

	@Cacheable("ReviewProcessExceptionTypeRef")
	ReviewProcessExceptionTypeRef findByCode(String code);

}
