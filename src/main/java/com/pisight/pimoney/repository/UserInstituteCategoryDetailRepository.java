package com.pisight.pimoney.repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.pisight.pimoney.constants.QueryConstant;
import com.pisight.pimoney.repository.entities.UserInstituteCategoryDetail;

@Repository
@RepositoryRestResource(exported = false)
public interface UserInstituteCategoryDetailRepository extends CrudRepository<UserInstituteCategoryDetail, UUID>{
	
	@Override
	List<UserInstituteCategoryDetail> findAll();
	
	@Query(QueryConstant.GET_USER_INST_CAT_DETAIL)
	Set<UserInstituteCategoryDetail> fetchUserInstituteCategoryDetails();
	
	@Query(QueryConstant.GET_USER_INST_CAT_DETAIL_BY_PTID)
	Set<UserInstituteCategoryDetail> fetchUserInstitutecategoryDetailsByPID(@Param("pitrackerId") String pitrackerId);
	
	@Query(QueryConstant.GET_USER_INST_CAT_DETAIL_BY_PTID_CATEGORY)
	UserInstituteCategoryDetail fetchUserInstitutecategoryDetailsByPIDCategory(@Param("pitrackerId") String pitrackerId, @Param("category") String category);

}
