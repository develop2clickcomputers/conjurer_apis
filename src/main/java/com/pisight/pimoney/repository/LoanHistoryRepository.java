package com.pisight.pimoney.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.pisight.pimoney.constants.QueryConstant;
import com.pisight.pimoney.repository.entities.LoanAccountHistory;

@Repository
@RepositoryRestResource(exported = false)
public interface LoanHistoryRepository extends CrudRepository<LoanAccountHistory, UUID>{

	@Override
	List<LoanAccountHistory> findAll();
	
	@Query(QueryConstant.FETCH_LOAN_ACCOUNT_HISTOTY)
	List<LoanAccountHistory> fetchLoanAccountHistory(@Param("accountHash") String accountHash);

	LoanAccountHistory findByFingerprintAndAccountHash(String fingerPrint, String accountHash);

	
}
