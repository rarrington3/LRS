package gov.hud.lrs.common.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import gov.hud.lrs.common.entity.ReviewScopeRef;

public interface ReviewScopeRefRepository extends JpaRepository<ReviewScopeRef, String> {

	@Cacheable("ReviewScopeRef")
	public ReviewScopeRef findByCode(String code);

}
