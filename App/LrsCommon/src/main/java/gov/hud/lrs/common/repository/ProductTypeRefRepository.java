package gov.hud.lrs.common.repository;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gov.hud.lrs.common.entity.ProductTypeRef;

@Repository
public interface ProductTypeRefRepository extends JpaRepository<ProductTypeRef, String> {

	@Override
	@Cacheable("ProductTypeRef")
	List<ProductTypeRef> findAll();
	
	@Cacheable("ProductTypeRef")
	ProductTypeRef findByProductTypeCd(String productTypeCd);

}
