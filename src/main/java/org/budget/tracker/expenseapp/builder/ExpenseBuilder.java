package org.budget.tracker.expenseapp.builder;

import org.budget.tracker.expenseapp.db.JExpense;
import org.budget.tracker.expenseapp.db.JGroup;
import org.budget.tracker.expenseapp.rest.request.CreateExpenseRequest;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.Objects;

public class ExpenseBuilder {

    public static JExpense with(CreateExpenseRequest request, @Nullable JGroup group) {

        var expense = new JExpense();
        expense.setName(request.getName());
        expense.setCost(request.getCost());
        if (Objects.nonNull(group)) {
            expense.setGroupId(group.getId());
        }
        expense.setCategory(request.getCategory());
        expense.setPaidBy(request.getPaidBy());
        expense.setCreatedBy(request.getCreatedBy());
        expense.setCreatedOn(LocalDateTime.now());
        expense.setUpdatedOn(LocalDateTime.now());

        return expense;
    }

}
