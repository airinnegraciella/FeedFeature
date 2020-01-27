package com.example.featurefeed.domain.model;

public class EditFeedComment {
    private int feedCommentId;
    private int makerId;
    private String comment;
    private String image;

    public EditFeedComment(int feedCommentId, int makerId, String comment, String image) {
        this.feedCommentId = feedCommentId;
        this.makerId = makerId;
        this.comment = comment;
        this.image = image;
    }

    public int getFeedCommentId() {
        return feedCommentId;
    }

    public void setFeedCommentId(int feedCommentId) {
        this.feedCommentId = feedCommentId;
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
