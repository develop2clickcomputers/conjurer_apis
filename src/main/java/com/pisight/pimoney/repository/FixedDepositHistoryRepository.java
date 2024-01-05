package com.pisight.pimoney.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.pisight.pimoney.constants.QueryConstant;
import com.pisight.pimoney.repository.entities.FixedDepositAccountHistoryEntity;

@Repository
@RepositoryRestResource(exported = false)
public interface FixedDepositHistoryRepository extends CrudRepository<FixedDepositAccountHistoryEntity, UUID>{

	@Override
	List<FixedDepositAccountHistoryEntity> findAll();

	@Query(QueryConstant.FETCH_FD_ACCOUNT_HISTOTY)
	List<FixedDepositAccountHistoryEntity> fetchFDAccountHistory(@Param("accountHash") String accountHash);

	FixedDepositAccountHistoryEntity findByFingerprintAndAccountHash(String fingerPrint, String accountHash);

}
