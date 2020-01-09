package com.example.feedfeature.data.source.remote.response;

import com.example.feedfeature.data.source.local.Employee;
import com.example.feedfeature.data.source.local.FeedPagination;
import com.google.gson.annotations.SerializedName;

public class ResponseFeed {
    private String status;
    private String message;

    @SerializedName("result")
    private FeedPagination feedPagination;

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

    public FeedPagination getFeedPagination() {
        return feedPagination;
    }

    public void setFeedPagination(FeedPagination feedPagination) {
        this.feedPagination = feedPagination;
    }
}
