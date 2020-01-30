package com.example.featurefeed.domain.model;

public class EditFeed {
    private int feedId;
    private int makerId;
    private String post;
    private String image;
    
    public EditFeed(int feedId, int makerId, String post, String image) {
        this.feedId = feedId;
        this.makerId = makerId;
        this.post = post;
        this.image = image;
    }
    
    public int getFeedId() {
        return feedId;
    }
    
    public void setFeedId(int feedId) {
        this.feedId = feedId;
    }
    
    public int getMakerId() {
        return makerId;
    }
    
    public void setMakerId(int makerId) {
        this.makerId = makerId;
    }
    
    public String getPost() {
        return post;
    }
    
    public void setPost(String post) {
        this.post = post;
    }
    
    public String getImage() {
        return image;
    }
    
    public void setImage(String image) {
        this.image = image;
    }
}
