package org.budget.tracker.expenseapp.service;

import org.budget.tracker.expenseapp.elasticsearch.ESExpense;
import org.budget.tracker.expenseapp.rest.request.CreateExpenseRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ExpenseService {

    void createExpense(CreateExpenseRequest request);

    List<ESExpense> getExpenses();

}
