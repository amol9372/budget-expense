package org.budget.tracker.expenseapp.rest.controller;

import org.budget.tracker.expenseapp.elasticsearch.ESExpense;
import org.budget.tracker.expenseapp.rest.request.CreateExpenseRequest;
import org.budget.tracker.expenseapp.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("expense")
public class ExpenseController {

    @Autowired
    ExpenseService expenseService;

    @GetMapping("health-check")
    public String healthCheck() {
        return "This controller is working";
    }

    @PostMapping
    public void createExpense(@RequestBody CreateExpenseRequest request) {
        expenseService.createExpense(request);
    }

    @GetMapping
    public List<ESExpense> getExpenses(){
        return expenseService.getExpenses();
    }

//    @PostMapping
//    public List<Expense> getExpenses(@RequestBody GetExpensesRequest request){
//      return null;
//    }

}
