package com.pisight.pimoney.repository;

import java.util.Date;
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
import com.pisight.pimoney.repository.entities.InvestmentAccountHistoryEntity;

@Repository
@RepositoryRestResource(exported = false)
public interface InvestmentHistoryRepository extends CrudRepository<InvestmentAccountHistoryEntity, UUID>{

	@Override
	List<InvestmentAccountHistoryEntity> findAll();

	InvestmentAccountHistoryEntity findByFingerprintAndAccountHash(String fingerPrint, String accountHash);
	
	@Query(QueryConstant.FETCH_INVESTMENT_ACCOUNT_HISTOTY)
	List<InvestmentAccountHistoryEntity> fetchInvestmentAccountHistory(@Param("accountHash") String accountHash, @Param("userId") UUID userId);

	@Transactional
	@Modifying
	@Query(QueryConstant.DELETE_INVESTMENT_ACCOUNT_HISTOTY)
	void deleteInvestmentHistory(@Param("accountHash") String accountHash, @Param("stmtDate") Date stmtDate, @Param("userId") UUID userId);
}
