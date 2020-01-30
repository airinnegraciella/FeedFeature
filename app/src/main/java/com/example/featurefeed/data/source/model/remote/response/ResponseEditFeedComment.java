package com.example.featurefeed.data.source.model.remote.response;

public class ResponseEditFeedComment {
    private String status;
    private String message;
    private String result;
    
    public ResponseEditFeedComment(String status, String message, String result) {
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
    
    public String getResult() {
        return result;
    }
    
    public void setResult(String result) {
        this.result = result;
    }
}
