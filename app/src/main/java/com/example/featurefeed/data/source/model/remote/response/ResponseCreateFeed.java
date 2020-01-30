package com.example.featurefeed.data.source.model.remote.response;

import com.example.featurefeed.data.source.model.local.Feed;
import com.google.gson.annotations.SerializedName;

public class ResponseCreateFeed {
    private String status;
    private String message;
    
    @SerializedName("result")
    private Feed feed;
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public Feed getFeed() {
        return feed;
    }
    
    public void setFeed(Feed feed) {
        this.feed = feed;
    }
}
