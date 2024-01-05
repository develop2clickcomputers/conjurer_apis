package com.pisight.pimoney.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.pisight.pimoney.constants.QueryConstant;
import com.pisight.pimoney.repository.entities.FileRepositoryEntity;

public interface FileRepoEntityRepository extends CrudRepository<FileRepositoryEntity, UUID>  {

	@Query(QueryConstant.GET_FILE_REPO_BY_USER_ID)
	List<FileRepositoryEntity> fetchFileRepoByUserId(@Param("userId") UUID userId);
}
