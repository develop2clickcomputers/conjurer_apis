package com.pisight.pimoney.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.pisight.pimoney.constants.QueryConstant;
import com.pisight.pimoney.repository.entities.StatementParsingDetail;

@Repository
@RepositoryRestResource(exported = false)
public interface StatementParsingDetailRepository extends CrudRepository<StatementParsingDetail, Long>{

	@Override
	List<StatementParsingDetail> findAll();

	@Query(QueryConstant.GET_DETAILS_BY_DATE)
	List<StatementParsingDetail> fetchDetailsByDate(@Param("date") Date date);
}
