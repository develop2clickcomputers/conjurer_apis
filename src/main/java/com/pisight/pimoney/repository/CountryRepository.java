package com.pisight.pimoney.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.pisight.pimoney.repository.entities.Country;

@Repository
@RepositoryRestResource(exported = false)
public interface CountryRepository extends CrudRepository<Country, Long>{

	@Override
	List<Country> findAll();
}
