package com.example.featurelike.data.source.remote.response.like;

public class ResponseLikeFeedPagination {
    private String status;
    private String message;
    private FeedLikePagination result;

    public ResponseLikeFeedPagination(String status, String message, FeedLikePagination result) {
        this.status = status;
        this.message = message;
        this.result = result;
    }

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

    public FeedLikePagination getResult() {
        return result;
    }

    public void setResult(FeedLikePagination result) {
        this.result = result;
    }
}
