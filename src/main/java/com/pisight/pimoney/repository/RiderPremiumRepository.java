package com.pisight.pimoney.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.pisight.pimoney.constants.QueryConstant;
import com.pisight.pimoney.repository.entities.RiderPremium;

@Repository
@RepositoryRestResource(exported = false)
public interface RiderPremiumRepository extends CrudRepository<RiderPremium, UUID>{

	@Override
	List<RiderPremium> findAll();

	@Query(QueryConstant.FETCH_RIDER_PREMIUM_BY_IR_MAP_ID)	
	RiderPremium fetchRiderPremiumByIRMapId(@Param("irMapId") UUID irMapId);

}
