package org.budget.tracker.expenseapp.app;

enum GenericCategory {

    GROCERY("groceries"), ELECTRONICS("electronics"), RENT("rent"), WATER("water"), INTERNET("internet"), CELLPHONE("cellphone");

    private final String value;

    GenericCategory(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}

