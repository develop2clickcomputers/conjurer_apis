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
import com.pisight.pimoney.repository.entities.BankTransactionEntity;
import com.pisight.pimoney.repository.entities.CardTransactionEntity;
import com.pisight.pimoney.repository.entities.LoanTransactionEntity;

@Repository
@RepositoryRestResource(exported = false)
public interface TransactionRepository extends CrudRepository<BankTransactionEntity, Long>{

	@Override
	List<BankTransactionEntity> findAll();
	
	@Query(QueryConstant.GET_BANK_TXN_DETAILS_BY_DATE_INTERVAL)
	List<BankTransactionEntity> getBankTransactionDetailsByDateInterval(@Param("id") UUID id, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
	
	@Query(QueryConstant.GET_CARD_TXN_DETAILS_BY_DATE_INTERVAL)
	List<CardTransactionEntity> getCardTransactionDetailsByDateInterval(@Param("id") UUID id, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
	
	@Query(QueryConstant.GET_LOAN_TXN_DETAILS_BY_DATE_INTERVAL)
	List<LoanTransactionEntity> getLoanTransactionDetailsByDateInterval(@Param("id") UUID id, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

	@Transactional
	@Modifying
	@Query(QueryConstant.SOFT_DELETE_BANK_TXN)
	Integer deleteBankTxns(@Param("accountId") UUID accountId);
	
	
	@Transactional
	@Modifying
	@Query(QueryConstant.SOFT_DELETE_CARD_TXN)
	Integer deleteCardTxns(@Param("accountId") UUID accountId);
	
	@Transactional
	@Modifying
	@Query(QueryConstant.SOFT_DELETE_LOAN_TXN)
	Integer deleteLoanTxns(@Param("accountId") UUID accountId);
	
	@Transactional
	@Modifying
	@Query(QueryConstant.SOFT_DELETE_INVESTMENT_TXN)
	Integer deleteInvTxns(@Param("accountId") UUID accountId);
	
	@Transactional
	@Modifying
	@Query(QueryConstant.DELETE_BANK_TXN)
	Integer deleteBankTxns(@Param("accountId") UUID accountId, @Param("stmtId") String stmtId);
	
	
	@Transactional
	@Modifying
	@Query(QueryConstant.DELETE_CARD_TXN)
	Integer deleteCardTxns(@Param("accountId") UUID accountId, @Param("stmtId") String stmtId);
	
	@Transactional
	@Modifying
	@Query(QueryConstant.DELETE_LOAN_TXN)
	Integer deleteLoanTxns(@Param("accountId") UUID accountId, @Param("stmtId") String stmtId);
	
	@Transactional
	@Modifying
	@Query(QueryConstant.DELETE_INVESTMENT_TXN)
	Integer deleteInvTxns(@Param("accountId") UUID accountId, @Param("stmtId") String stmtId);
}
