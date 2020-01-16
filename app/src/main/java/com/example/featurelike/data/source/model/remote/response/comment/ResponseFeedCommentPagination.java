package com.example.featurelike.data.source.model.remote.response.comment;

import com.google.gson.annotations.SerializedName;

public class ResponseFeedCommentPagination {

    private String status;
    private String message;

    @SerializedName("result")
    private FeedCommentPagination feedCommentPagination;

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

    public FeedCommentPagination getFeedCommentPagination() {
        return feedCommentPagination;
    }

    public void setFeedCommentPagination(FeedCommentPagination feedCommentPagination) {
        this.feedCommentPagination = feedCommentPagination;
    }
}
