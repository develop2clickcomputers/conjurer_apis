package com.pisight.pimoney.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.pisight.pimoney.constants.QueryConstant;
import com.pisight.pimoney.repository.entities.BatchFileDetail;

@Repository
@RepositoryRestResource(exported = false)
public interface BatchFileDetailRepository extends CrudRepository<BatchFileDetail, UUID>{

	@Override
	List<BatchFileDetail> findAll();

	@Query(QueryConstant.FETCH_BATCH_FILE_DETAIL_BY_USER)
	List<BatchFileDetail> fetchByuserId(@Param("userId") UUID userId);

	@Query(QueryConstant.FETCH_BATCH_FILE_DETAIL_BY_BATCH_ID)
	BatchFileDetail findByBatchId(@Param("userId") UUID userId, @Param("batchId") String batchId, 
			@Param("instName") String instName);

	@Query(QueryConstant.FETCH_BATCH_FILE_DETAIL_BY_USER)
	List<BatchFileDetail> fetchBatchByuserId(@Param("userId") UUID userId);

	@Query(QueryConstant.FETCH_BATCH_FILE_DETAIL_BY_USERID_BATCH_ID)
	List<BatchFileDetail> fetchBatchByuserIdAndBatchId(@Param("userId") UUID userId, @Param("batchId") String batchId);
}
