package com.pisight.pimoney.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.pisight.pimoney.constants.QueryConstant;
import com.pisight.pimoney.repository.entities.Rider;

@Repository
@RepositoryRestResource(exported = false)
public interface RiderRepository extends CrudRepository<Rider, UUID>{

	@Override
	List<Rider> findAll();

	@Query(QueryConstant.FETCH_RIDER_BY_CARRIER_PLAN_ID)
	List<Rider> fetchRiderByCarrierAndPlanId(@Param("carrierId") UUID carrierId, @Param("planId") UUID planId);
}
