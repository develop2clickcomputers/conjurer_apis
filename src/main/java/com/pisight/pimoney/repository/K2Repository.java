package com.pisight.pimoney.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.pisight.pimoney.constants.QueryConstant;
import com.pisight.pimoney.repository.entities.BankAccountEntity;
import com.pisight.pimoney.repository.entities.BankTransactionEntity;
import com.pisight.pimoney.repository.entities.CardAccountEntity;
import com.pisight.pimoney.repository.entities.CardTransactionEntity;
import com.pisight.pimoney.repository.entities.Configuration;
import com.pisight.pimoney.repository.entities.Currency;
import com.pisight.pimoney.repository.entities.FixedDepositAccountEntity;
import com.pisight.pimoney.repository.entities.HoldingAssetEntity;
import com.pisight.pimoney.repository.entities.InvestmentAccountEntity;
import com.pisight.pimoney.repository.entities.InvestmentTransactionEntity;
import com.pisight.pimoney.repository.entities.LoanAccountEntity;
import com.pisight.pimoney.repository.entities.LoanTransactionEntity;
import com.pisight.pimoney.repository.entities.User;


@Repository
@RepositoryRestResource(exported = false)
public interface K2Repository extends CrudRepository<Configuration, Long>{
	
	@Query(QueryConstant.GET_CONFIGURATION)
	Configuration fetchConfiguration(@Param("type") String type, @Param("key") String key);
	
	@Query(QueryConstant.GET_USER)
	User fetchUser(@Param("username") String username);
	
	@Query(QueryConstant.GET_BANK_ACCOUNT)
	BankAccountEntity fetchBankAccount(@Param("id") UUID id);
	
	@Query(QueryConstant.GET_CARD_ACCOUNT)
	CardAccountEntity fetchCardAccount(@Param("id") UUID id);
	
	@Query(QueryConstant.GET_LOAN_ACCOUNT)
	LoanAccountEntity fetchLoanAccount(@Param("id") UUID id);
	
	@Query(QueryConstant.GET_FIXED_DEPOSIT_ACCOUNT)
	FixedDepositAccountEntity fetchFixedDepositAccount(@Param("id") UUID id);
	
	@Query(QueryConstant.GET_INVESTMENT_ACCOUNT)
	InvestmentAccountEntity fetchInvestmentAccount(@Param("id") UUID id);
	
	@Query(QueryConstant.GET_HOLDING_ASSET)
	HoldingAssetEntity fetchHoldingAsset(@Param("id") UUID id);
	
	@Query(QueryConstant.GET_BANK_TRANSACTION)
	BankTransactionEntity fetchBankTransaction(@Param("id") UUID id);
	
	@Query(QueryConstant.GET_CARD_TRANSACTION)
	CardTransactionEntity fetchCardTransaction(@Param("id") UUID id);
	
	@Query(QueryConstant.GET_LOAN_TRANSACTION)
	LoanTransactionEntity fetchLoanTransaction(@Param("id") UUID id);
	
	@Query(QueryConstant.GET_INVESTMENT_TRANSACTION)
	InvestmentTransactionEntity fetchInvestmentTransaction(@Param("id") UUID id);
	
	@Query(QueryConstant.GET_CURRENCY_LIST)
	List<Currency> fetchCurrencyList();
	
	@Query(QueryConstant.GET_BANK_MANUAL_ACCOUNT)
	BankAccountEntity fetchBankManualAccount(@Param("userId") UUID userId);
	
	@Query(QueryConstant.GET_CARD_MANUAL_ACCOUNT)
	CardAccountEntity fetchCardManualAccount(@Param("userId") UUID userId);
	
	@Query(QueryConstant.GET_LOAN_MANUAL_ACCOUNT)
	LoanAccountEntity fetchLoanManualAccount(@Param("userId") UUID userId);
	
	@Query(QueryConstant.GET_FIXED_DEPOSIT_MANUAL_ACCOUNT)
	FixedDepositAccountEntity fetchFixedDepositManualAccount(@Param("userId") UUID userId);
	
	
	
	@Query(QueryConstant.GET_BANK_ACCOUNT_BY_BANK_ID)
	List<BankAccountEntity> fetchBankAccountByBankId(@Param("bankId") String trackerId);
	
	@Query(QueryConstant.GET_CARD_ACCOUNT_BY_BANK_ID)
	List<CardAccountEntity> fetchCardAccountByBankId(@Param("bankId") String trackerId);
	
	@Query(QueryConstant.GET_LOAN_ACCOUNT_BY_BANK_ID)
	List<LoanAccountEntity> fetchLoanAccountByBankId(@Param("bankId") String trackerId);
	
	@Query(QueryConstant.GET_FIXED_DEPOSIT_ACCOUNT_BY_BANK_ID)
	List<FixedDepositAccountEntity> fetchFixedDepositAccountByBankId(@Param("bankId") String trackerId);
	
	@Query(QueryConstant.GET_INVESTMENT_ACCOUNT_BY_BANK_ID)
	List<InvestmentAccountEntity> fetchInvestmentAccountByBankId(@Param("bankId") String trackerId);

	

}
