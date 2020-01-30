package com.example.featurefeed.domain.model;

public class LikeFeed {
    private int feedId;
    private int employeeId;
    
    public LikeFeed(int feedId, int employeeId) {
        this.feedId = feedId;
        this.employeeId = employeeId;
    }
    
    public int getFeedId() {
        return feedId;
    }
    
    public void setFeedId(int feedId) {
        this.feedId = feedId;
    }
    
    public int getEmployeeId() {
        return employeeId;
    }
    
    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }
}
