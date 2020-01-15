package com.example.featurelike.data.source.remote.response.feed;

import com.example.featurelike.data.source.local.Feed;

import java.util.List;

public class FeedPagination {
    private int page;
    private int total_item;
    private int total_page;
    private List<Feed> feed_list;

    public FeedPagination(int page, int total_item, int total_page, List<Feed> feed_list) {
        this.page = page;
        this.total_item = total_item;
        this.total_page = total_page;
        this.feed_list = feed_list;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_item() {
        return total_item;
    }

    public void setTotal_item(int total_item) {
        this.total_item = total_item;
    }

    public int getTotal_page() {
        return total_page;
    }

    public void setTotal_page(int total_page) {
        this.total_page = total_page;
    }

    public List<Feed> getFeed_list() {
        return feed_list;
    }

    public void setFeed_list(List<Feed> feed_list) {
        this.feed_list = feed_list;
    }
}
