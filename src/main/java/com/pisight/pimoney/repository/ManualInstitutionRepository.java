package com.pisight.pimoney.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.pisight.pimoney.constants.QueryConstant;
import com.pisight.pimoney.repository.entities.ManualInstitution;

@Repository
@RepositoryRestResource(exported = false)
public interface ManualInstitutionRepository  extends CrudRepository<ManualInstitution, Long>{
	
	@Override
	List<ManualInstitution> findAll();
	
	@Query(QueryConstant.GET_MANUAL_INSTITUTIONS_BY_COUNTRY)
	List<ManualInstitution> findByCountryCode(@Param("countryCode") String countryCode);
	
	@Query(QueryConstant.GET_MANUAL_INSTITUTIONS_BY_CODE)
	ManualInstitution findByInstCode(@Param("instCode") String instCode);

	@Query(QueryConstant.GET_PDF_INSTITUTION_BY_INSTITUTIONNAME_COUNTRYCODE)
	ManualInstitution findByInstitutionName(@Param("institutionName") String institutionName, @Param("countryCode") String countryCode);
	
	@Query(QueryConstant.GET_PDF_INSTITUTION_BY_INSTITUTIONNAME)
	List<ManualInstitution> findByInstitutionName(@Param("institutionName") String institutionName);

}
