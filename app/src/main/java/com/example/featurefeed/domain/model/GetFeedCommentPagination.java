package com.example.featurefeed.domain.model;

public class GetFeedCommentPagination {
    private int employeeId;
    private int feedId;
    private int page;
    private int limit;

    public GetFeedCommentPagination(int employeeId, int feedId, int page, int limit) {
        this.employeeId = employeeId;
        this.feedId = feedId;
        this.page = page;
        this.limit = limit;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getFeedId() {
        return feedId;
    }

    public void setFeedId(int feedId) {
        this.feedId = feedId;
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
