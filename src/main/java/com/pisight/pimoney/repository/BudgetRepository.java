package com.pisight.pimoney.repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import com.pisight.pimoney.constants.QueryConstant;
import com.pisight.pimoney.repository.entities.Budget;

@Repository
@RepositoryRestResource(exported = false)
public interface BudgetRepository extends CrudRepository<Budget, Long>{

	@Override
	List<Budget> findAll();
	
	@Query(QueryConstant.GET_BUDGET_BY_ID)
	Budget fetchBudgetById(@Param("id") UUID id);

	@Query(QueryConstant.GET_BUDGET_BY_CATEGORY_AND_DATE)
	Budget fetchBudgetByCategoryAndDATE(@Param("userId") UUID userId, @Param("category") String category, @Param("budgetDate") Date budgetDate);

	@Query(QueryConstant.GET_FUTURE_BUDGETS)
	List<Budget> updateFutureBudgets(@Param("userId") UUID userId, @Param("category") String category, @Param("budgetDate") Date budgetDate);

	@Query(QueryConstant.GET_BUDGET_BY_DATE)
	List<Budget> fetchBudgetsByDATE(@Param("userId") UUID userId, @Param("budgetDate") Date budgetDate);

	@Query(QueryConstant.GET_BUDGET_BY_USER_ID)
	List<Budget> fetchBudgetsByUserId(@Param("userId") UUID userId);
}
