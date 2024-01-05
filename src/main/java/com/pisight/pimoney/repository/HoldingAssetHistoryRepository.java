package com.pisight.pimoney.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.pisight.pimoney.repository.entities.HoldingAssetHistory;

@Repository
@RepositoryRestResource(exported = false)
public interface HoldingAssetHistoryRepository extends CrudRepository<HoldingAssetHistory, UUID>{

	List<HoldingAssetHistory> findBystatementId(String stmtId);
	
}
