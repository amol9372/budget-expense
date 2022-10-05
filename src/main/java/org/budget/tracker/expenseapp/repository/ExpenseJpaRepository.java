package org.budget.tracker.expenseapp.repository;

import org.budget.tracker.expenseapp.db.JExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseJpaRepository extends JpaRepository<JExpense, Integer> {

      List<JExpense> findByGroupId(Integer groupId);

}
