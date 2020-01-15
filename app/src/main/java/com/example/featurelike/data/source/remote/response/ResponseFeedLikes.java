package com.example.featurelike.data.source.remote.response;

import com.example.featurelike.data.source.remote.response.like.FeedLikePagination;
import com.google.gson.annotations.SerializedName;

public class ResponseFeedLikes {

    private String status;
    private String message;

    @SerializedName("result")
    private FeedLikePagination feedLikePagination;

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

    public FeedLikePagination getFeedLikePagination() {
        return feedLikePagination;
    }

    public void setFeedLikePagination(FeedLikePagination feedLikePagination) {
        this.feedLikePagination = feedLikePagination;
    }
}
