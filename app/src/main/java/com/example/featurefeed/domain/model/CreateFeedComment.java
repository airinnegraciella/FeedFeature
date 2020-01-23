package com.example.featurefeed.domain.model;

public class CreateFeedComment {

    private int feedId;
    private int makerId;
    private String comment;
    private String image;

    public CreateFeedComment(int feedId, int makerId, String comment, String image) {
        this.feedId = feedId;
        this.makerId = makerId;
        this.comment = comment;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
