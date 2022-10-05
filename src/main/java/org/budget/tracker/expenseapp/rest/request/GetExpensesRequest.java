package org.budget.tracker.expenseapp.rest.request;

import org.elasticsearch.search.sort.SortOrder;

import java.util.List;

public class GetExpensesRequest {

    private List<String> fields;
    private String searchQuery;
    private String sortBy;
    private SortOrder order;

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public SortOrder getOrder() {
        return order;
    }

    public void setOrder(SortOrder order) {
        this.order = order;
    }
}
