package org.budget.tracker.expenseapp.rest.request;

public class UpsertBudgetCategoryRequest {

    private String category;
    private String subCategory;
    private Boolean isExisting;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public Boolean getExisting() {
        return isExisting;
    }

    public void setExisting(Boolean existing) {
        isExisting = existing;
    }
}
