package org.budget.tracker.expenseapp.service;

import org.budget.tracker.expenseapp.app.Expense;
import org.budget.tracker.expenseapp.rest.request.CreateExpenseRequest;
import org.budget.tracker.expenseapp.rest.request.GetExpensesRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ExpenseService {

    void createExpense(CreateExpenseRequest request);

    List<Expense> getExpenses();

    List<Expense> getExpensesWithFilter(GetExpensesRequest request);
}
