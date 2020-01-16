package com.example.featurefeed.data.source.model.remote.response.like;

import com.example.featurefeed.data.source.model.local.FeedLike;

import java.util.List;

public class FeedLikePagination {

    private String page;
    private String total_item;
    private int total_page;
    private List<FeedLike> feed_like_list;

    public FeedLikePagination(String page, String total_item, int total_page, List<FeedLike> feed_like_list) {
        this.page = page;
        this.total_item = total_item;
        this.total_page = total_page;
        this.feed_like_list = feed_like_list;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getTotal_item() {
        return total_item;
    }

    public void setTotal_item(String total_item) {
        this.total_item = total_item;
    }

    public int getTotal_page() {
        return total_page;
    }

    public void setTotal_page(int total_page) {
        this.total_page = total_page;
    }

    public List<FeedLike> getFeed_like_list() {
        return feed_like_list;
    }

    public void setFeed_like_list(List<FeedLike> feed_like_list) {
        this.feed_like_list = feed_like_list;
    }
}
