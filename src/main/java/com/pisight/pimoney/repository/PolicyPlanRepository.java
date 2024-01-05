package com.pisight.pimoney.repository;

import java.util.List;
import java.util.UUID;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.pisight.pimoney.constants.QueryConstant;
import com.pisight.pimoney.repository.entities.PolicyPlan;

@Repository
@RepositoryRestResource(exported = false)
public interface PolicyPlanRepository extends CrudRepository<PolicyPlan, UUID>{

	@Override
	List<PolicyPlan> findAll();

	@Query(QueryConstant.FETCH_PLAN_BY_CARRIER_ID)
	List<PolicyPlan> fetchPlanByCarrierId(@Param("carrierId") UUID carrierId);
}
