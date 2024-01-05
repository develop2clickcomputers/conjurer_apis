package com.pisight.pimoney.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.pisight.pimoney.constants.QueryConstant;
import com.pisight.pimoney.repository.entities.OnlineInstitution;

@Repository
@RepositoryRestResource(exported = false)
public interface OnlineInstitutionRepository  extends CrudRepository<OnlineInstitution, Long>{
	
	@Override
	List<OnlineInstitution> findAll();
	
	@Query(QueryConstant.GET_ONLINE_INSTITUTIONS_BY_COUNTRY)
	List<OnlineInstitution> findByCountryCode(@Param("countryCode") String countryCode);
	
	@Query(QueryConstant.GET_ONLINE_INSTITUTIONS_BY_CODE)
	OnlineInstitution findByInstCode(@Param("instCode") String instCode);

}
