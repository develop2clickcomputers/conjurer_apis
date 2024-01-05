package com.pisight.pimoney.repository;

import java.util.List;

import org.springframework.data.gemfire.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.pisight.pimoney.constants.QueryConstant;
import com.pisight.pimoney.repository.entities.SubCategory;

@Repository
@RepositoryRestResource(exported = false)
public interface SubCategoryRepository extends CrudRepository<SubCategory, Long>{

	@Override
	List<SubCategory> findAll();

	@Query(QueryConstant.GET_SUB_CATEGORY_BY_NAME_AND_CATEGORY_ID)
	SubCategory findSubCategoryByNameAndCategoryId(@Param("name") String name, @Param("categoryId") Long categoryId);
}
