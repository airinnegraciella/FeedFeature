package com.example.featurefeed.data.source.model.remote.response.comment;

import com.example.featurefeed.data.source.model.local.FeedComment;

import java.util.List;

public class FeedCommentPagination {
    
    private String page;
    private String total_item;
    private int total_page;
    private List<FeedComment> feed_comment_list;
    
    public FeedCommentPagination(String page, String total_item, int total_page, List<FeedComment> feed_comment_list) {
        this.page = page;
        this.total_item = total_item;
        this.total_page = total_page;
        this.feed_comment_list = feed_comment_list;
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
    
    public List<FeedComment> getFeed_comment_list() {
        return feed_comment_list;
    }
    
    public void setFeed_comment_list(List<FeedComment> feed_comment_list) {
        this.feed_comment_list = feed_comment_list;
    }
}
