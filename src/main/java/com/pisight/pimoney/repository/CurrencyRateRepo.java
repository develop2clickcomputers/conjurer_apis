package com.pisight.pimoney.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.pisight.pimoney.constants.QueryConstant;
import com.pisight.pimoney.repository.entities.CurrencyRate;

import java.lang.String;


@Repository
@RepositoryRestResource(exported = false)
public interface CurrencyRateRepo extends CrudRepository<CurrencyRate, Long> {

	@Override
	List<CurrencyRate> findAll();
	
//	@Query(QueryConstant.GET_RATE_BY_CURRENCY_AND_DATE)
//	CurrencyRate findByCurrencyAndDate(@Param("currency") String currency, @Param("date") Date date);
	
	CurrencyRate  findFirstByCurrencyAndDate(String currency, Date date);
	
	@Query(QueryConstant.GET_RATE_BY_DATE)
	List<?> findByDate(@Param("date") Date date);
	
}
