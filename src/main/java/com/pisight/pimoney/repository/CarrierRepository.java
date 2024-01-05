package com.pisight.pimoney.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.pisight.pimoney.constants.QueryConstant;
import com.pisight.pimoney.repository.entities.Carrier;

@Repository
@RepositoryRestResource(exported = false)
public interface CarrierRepository extends CrudRepository<Carrier, UUID>{

	@Override
	List<Carrier> findAll();

	@Query(QueryConstant.FETCH_CARRIER_BY_ID)	
	Carrier fetchCarrierById(@Param("carrierId") UUID carrierId);

}
