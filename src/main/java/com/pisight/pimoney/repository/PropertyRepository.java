package com.pisight.pimoney.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import com.pisight.pimoney.constants.QueryConstant;
import com.pisight.pimoney.repository.entities.Property;

@Repository
@RepositoryRestResource(exported = false)
public interface PropertyRepository extends CrudRepository<Property, Long>{

	@Override
	List<Property> findAll();
	
	@Query(QueryConstant.GET_PROPERTY_BY_ID)
	Property fetchPropertyById(@Param("id") UUID id);
}
