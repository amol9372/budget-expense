package org.budget.tracker.expenseapp.elasticsearch;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseESRepository
        extends ElasticsearchRepository<ESExpense, Integer> {

    @Query("{\"match\": {\"name\": {\"query\": \"?0\"}}}")
    Page<ESExpense> findByName(String name, Pageable pageable);

    List<ESExpense> findByName(String name);

}
