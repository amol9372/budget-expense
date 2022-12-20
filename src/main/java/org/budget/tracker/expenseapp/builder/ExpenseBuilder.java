package org.budget.tracker.expenseapp.builder;

import org.budget.tracker.expenseapp.app.Expense;
import org.budget.tracker.expenseapp.db.JExpense;
import org.budget.tracker.expenseapp.rest.request.CreateExpenseRequest;

import java.time.LocalDateTime;
import java.util.Objects;

public class ExpenseBuilder {

  public static JExpense with(CreateExpenseRequest request) {

    var expense = new JExpense();
    expense.setName(request.getName());
    // cost should not be 0
    expense.setCost(request.getCost().doubleValue());
    expense.setCategory(request.getCategory());
    expense.setCategoryId(request.getCategoryId());
    expense.setSubCategory(request.getSubCategory());
    expense.setPaidBy(request.getPaidBy());
    expense.setCreatedBy(request.getCreatedBy());
    expense.setCreatedOn(LocalDateTime.now());
    expense.setUpdatedOn(LocalDateTime.now());
    expense.setBudgetId(request.getBudgetId());
    //expense.setUserDefined(true);
    return expense;
  }

  public static Expense with(JExpense jExpense, CreateExpenseRequest request){
    var expense = new Expense();
    expense.setId(jExpense.getId());
    expense.setCost(request.getCost());
    expense.setName(request.getName());
    expense.setCategory(request.getCategory());
    expense.setCreatedBy(request.getCreatedBy());
    expense.setPaidBy(request.getPaidBy());
    expense.setCreatedOn(jExpense.getCreatedOn());
    expense.setBudgetId(request.getBudgetId());
    return expense;
  }
}
