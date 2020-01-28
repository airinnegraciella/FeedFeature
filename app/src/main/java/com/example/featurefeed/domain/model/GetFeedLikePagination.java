package com.example.featurefeed.domain.model;

public class GetFeedLikePagination {
    private int feedId;
    private int page;
    private int limit;

    public GetFeedLikePagination(int feedId, int page, int limit) {
        this.feedId = feedId;
        this.page = page;
        this.limit = limit;
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
