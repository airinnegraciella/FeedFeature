package com.example.featurefeed.data.source.model.remote.response;

import com.example.featurefeed.data.source.model.local.FeedComment;
import com.google.gson.annotations.SerializedName;

public class ResponseCreateFeedComment {

    private String status;
    private String message;

    @SerializedName("result")
    private FeedComment feedComment;

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

    public FeedComment getFeedComment() {
        return feedComment;
    }

    public void setFeedComment(FeedComment feedComment) {
        this.feedComment = feedComment;
    }
}
