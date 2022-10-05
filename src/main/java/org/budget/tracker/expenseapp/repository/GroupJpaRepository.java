package org.budget.tracker.expenseapp.repository;

import org.budget.tracker.expenseapp.db.JGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupJpaRepository extends JpaRepository<JGroup, Integer> {
    JGroup findByName(String name);
}
