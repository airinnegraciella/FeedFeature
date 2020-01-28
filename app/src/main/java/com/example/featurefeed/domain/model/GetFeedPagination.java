package com.example.featurefeed.domain.model;

public class GetFeedPagination {
    private int empId;
    private int page;
    private int limit;

    public GetFeedPagination(int empId, int page, int limit) {
        this.empId = empId;
        this.page = page;
        this.limit = limit;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
