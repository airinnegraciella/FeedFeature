package com.example.feedfeature.data.source.remote.response;

import com.example.feedfeature.data.source.remote.response.comment.FeedCommentPagination;
import com.google.gson.annotations.SerializedName;

public class ResponseFeedComments {

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
