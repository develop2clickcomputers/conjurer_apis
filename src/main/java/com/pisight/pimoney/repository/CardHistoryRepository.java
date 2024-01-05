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
import com.pisight.pimoney.repository.entities.CardAccountHistory;

@Repository
@RepositoryRestResource(exported = false)
public interface CardHistoryRepository extends CrudRepository<CardAccountHistory, UUID>{

	@Override
	List<CardAccountHistory> findAll();

	@Query(QueryConstant.FETCH_CARD_ACCOUNT_HISTOTY)
	List<CardAccountHistory> fetchCardAccountHistory(@Param("accountHash") String accountHash, @Param("userId") UUID userId);

	CardAccountHistory findByFingerprintAndAccountHash(String fingerPrint, String accountHash);
	
	@Transactional
	@Modifying
	@Query(QueryConstant.DELETE_CARD_ACCOUNT_HISTOTY)
	void deleteCardHistory(@Param("accountHash") String accountHash, @Param("stmtDate") Date stmtDate, @Param("userId") UUID userId);
}
