package org.budget.tracker.expenseapp.repository;

import org.budget.tracker.expenseapp.db.JUserGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserGroupJpaRepository extends JpaRepository<JUserGroup, Integer> {


}
