package org.budget.tracker.expenseapp.repository;

import org.budget.tracker.expenseapp.db.JUsers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersJpaRepository extends JpaRepository<JUsers, Integer> {

    Optional<JUsers> findByEmail(String email);
}
