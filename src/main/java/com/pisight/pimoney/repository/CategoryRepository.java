package com.pisight.pimoney.repository;

import java.util.List;

import org.springframework.data.gemfire.repository.Query;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.pisight.pimoney.constants.QueryConstant;
import com.pisight.pimoney.repository.entities.Category;

@Repository
@RepositoryRestResource(exported = false)
public interface CategoryRepository extends CrudRepository<Category, Long>{

	@Override
	List<Category> findAll();

	@Query(QueryConstant.GET_CATEGORY_BY_NAME)
	Category findCategoryByName(@Param("name") String name);
	
}
