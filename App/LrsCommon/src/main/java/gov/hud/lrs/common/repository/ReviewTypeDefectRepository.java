package gov.hud.lrs.common.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import gov.hud.lrs.common.entity.ReviewTypeDefect;
import gov.hud.lrs.common.entity.ReviewTypeDefectId;
import gov.hud.lrs.common.entity.ReviewTypeRef;

@Repository
public interface ReviewTypeDefectRepository extends CrudRepository<ReviewTypeDefect, ReviewTypeDefectId> {

	List<ReviewTypeDefect> findByIdReviewTypeId(String reviewTypeId);
	List<ReviewTypeDefect> findByIdDefectId(String defectId);
	long deleteByIdDefectId(String defectId);
	List<ReviewTypeDefect> findByReviewTypeRef(ReviewTypeRef reviewTypeRef);
}
