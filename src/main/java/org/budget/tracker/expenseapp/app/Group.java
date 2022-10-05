package org.budget.tracker.expenseapp.app;

import java.util.Currency;
import java.util.List;

public class Group {

    private String name;
    private List<String> members;
    private Currency maxBudget;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    public Currency getMaxBudget() {
        return maxBudget;
    }

    public void setMaxBudget(Currency maxBudget) {
        this.maxBudget = maxBudget;
    }
}
