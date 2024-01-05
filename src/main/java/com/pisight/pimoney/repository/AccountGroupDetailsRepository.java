package com.pisight.pimoney.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.pisight.pimoney.constants.QueryConstant;
import com.pisight.pimoney.repository.entities.AccountGroupDetails;

@Repository
@RepositoryRestResource(exported = false)
public interface AccountGroupDetailsRepository extends CrudRepository<AccountGroupDetails, Long>{

	@Query(QueryConstant.FETCH_DETAILS)
	AccountGroupDetails fetchDetails(@Param("userId") UUID userId, @Param("institutionId") UUID instId, @Param("accountNumber") String accountNumber);

	@Query(QueryConstant.FETCH_DETAILS_BY_USER_ID)
	List<AccountGroupDetails> fetchDetailsByUserId(@Param("userId") UUID userId);

	@Query(QueryConstant.FETCH_DETAILS_BY_GROUP_ID)
	List<AccountGroupDetails> fetchDetailsByGroupId(@Param("userId") UUID userId, @Param("groupId") String groupId);
}
