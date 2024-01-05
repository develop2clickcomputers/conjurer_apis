package com.pisight.pimoney.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.pisight.pimoney.repository.entities.Merchant;

@Repository
@RepositoryRestResource(exported = false)
public interface MerchantRepository extends CrudRepository<Merchant, Long>{

	@Override
	List<Merchant> findAll();	
}
