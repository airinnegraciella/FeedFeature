package com.example.featurefeed.data.source.model.remote.response.like;

import com.google.gson.annotations.SerializedName;

public class ResponseFeedLikesPagination {

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
