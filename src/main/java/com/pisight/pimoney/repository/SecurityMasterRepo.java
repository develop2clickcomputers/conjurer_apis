package com.pisight.pimoney.repository;


import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.pisight.pimoney.repository.entities.SecurityMaster;

@Repository
@RepositoryRestResource(exported = false)
public interface SecurityMasterRepo  extends CrudRepository<SecurityMaster, UUID>{

}
