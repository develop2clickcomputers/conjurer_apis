package com.pisight.pimoney.repository;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.pisight.pimoney.constants.QueryConstant;
import com.pisight.pimoney.repository.entities.HoldingAssetEntity;

@Repository
@RepositoryRestResource(exported = false)
public interface HoldingAssetRepository extends CrudRepository<HoldingAssetEntity, UUID>{
	
	@Query(QueryConstant.GET_ASSET_BY_USER_ID)
	List<HoldingAssetEntity> findByUserId(@Param("userId") UUID userId);
	
	@Transactional
	@Modifying
	@Query(QueryConstant.SOFT_DELETE_HOLDING_ASSET)
	Integer deleteHoldingAssets(@Param("accountId") UUID accountId);

	@Transactional
	@Modifying
	@Query(QueryConstant.DELETE_HOLDING_ASSET)
	Integer deleteHoldingAssets(@Param("accountId") UUID accountId, @Param("stmtId") String stmtId);

	@Transactional
	@Modifying
	@Query(QueryConstant.DELETE_SECURITY_MASTER)
	Integer deleteSecurityMaster(@Param("accountId") UUID accountId, @Param("stmtId")String stmtId);


}
