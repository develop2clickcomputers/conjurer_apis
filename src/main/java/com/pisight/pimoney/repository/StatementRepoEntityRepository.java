package com.pisight.pimoney.repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.pisight.pimoney.constants.QueryConstant;
import com.pisight.pimoney.repository.entities.StatementRepositoryEntity;

public interface StatementRepoEntityRepository  extends CrudRepository<StatementRepositoryEntity, UUID> {
	
	@Query(QueryConstant.GET_STATEMENT_REPO_BY_USER_ID)
	List<StatementRepositoryEntity> fetchStatementRepoByUserId(@Param("userId") UUID userId);

	@Query(QueryConstant.GET_STATEMENT_REPO_BY_GROUP_ID)
	List<StatementRepositoryEntity> fetchStmtRepoByGroupId(@Param("userId") UUID userId, @Param("groupId") String groupId);

	@Query(QueryConstant.GET_STATEMENT_REPO_BY_ACCNO_STMTDATE_INSTID)
	StatementRepositoryEntity fetchStmtRepo(@Param("stmtDate") Date stmtDate, @Param("instId") UUID instId, @Param("userId") UUID userId);
}