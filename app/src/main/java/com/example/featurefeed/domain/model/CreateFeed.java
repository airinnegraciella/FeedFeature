package com.example.featurefeed.domain.model;

public class CreateFeed {
    private int makerId;
    private String post;
    private String image;
    
    public CreateFeed(int makerId, String post, String image) {
        this.makerId = makerId;
        this.post = post;
        this.image = image;
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
