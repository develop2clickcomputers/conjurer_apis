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
import com.pisight.pimoney.repository.entities.UserInstituteDetail;

@Repository
@RepositoryRestResource(exported = false)
public interface UserInstituteDetailRepository  extends CrudRepository<UserInstituteDetail, UUID>{
	
	
	@Override
	List<UserInstituteDetail> findAll();
	
	@Query(QueryConstant.GET_USER_INST_DETAIL)
	Set<UserInstituteDetail> fetchUserInstitutesDetails();

	@Query(QueryConstant.GET_USER_INST_DETAIL_BY_PTID)
	UserInstituteDetail fetchUserInstituteDetailByPID(@Param("pitrackerId") String pitrackerId);
	
	@Query(QueryConstant.GET_USER_INST_DETAIL_BY_USER_ID)
	Set<UserInstituteDetail> fetchUserInstituteDetailByUserId(@Param("userId") UUID userId);
	
	@Query(QueryConstant.GET_USER_INST_DETAIL_BY_USER_INST)
	Set<UserInstituteDetail> fetchUserInstituteDetailByUserInst(@Param("userId") UUID userId, @Param("institutionCode") String institutionCode);
	
	@Query(QueryConstant.GET_USER_INST_DETAIL_BY_TRACKER_ID)
	UserInstituteDetail fetchUserInstituteDetailByTrackerId(@Param("trackerId") String trackerId);

}
