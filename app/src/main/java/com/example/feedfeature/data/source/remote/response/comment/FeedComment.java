package com.example.feedfeature.data.source.remote.response.comment;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class FeedComment  {
    @SerializedName("FeedCommentId")
    private int feedCommentId;
    @SerializedName("FeedId")
    private int feedId;
    @SerializedName("MakerId")
    private int makerId;
    @SerializedName("MakerName")
    @Nullable
    private String makerName;
    @SerializedName("MakerImagePath")
    @Nullable
    private String makerImagePath;
    @SerializedName("Comment")
    @Nullable
    private String comment;
    @SerializedName("CommentImagePath")
    @Nullable
    private String commentImagePath;
    @SerializedName("UpdatedDate")
    @Nullable
    private String updatedDate;
    @SerializedName("IsMine")
    private int isMine;

    public FeedComment(){

    }

    public FeedComment(int feedCommentId, int feedId, int makerId, @Nullable String makerName, @Nullable String makerImagePath, @Nullable String comment, @Nullable String commentImagePath, @Nullable String updatedDate, int isMine) {
        this.feedCommentId = feedCommentId;
        this.feedId = feedId;
        this.makerId = makerId;
        this.makerName = makerName;
        this.makerImagePath = makerImagePath;
        this.comment = comment;
        this.commentImagePath = commentImagePath;
        this.updatedDate = updatedDate;
        this.isMine = isMine;
    }

    public int getFeedCommentId() {
        return feedCommentId;
    }

    public void setFeedCommentId(int feedCommentId) {
        this.feedCommentId = feedCommentId;
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

    @Nullable
    public String getMakerName() {
        return makerName;
    }

    public void setMakerName(@Nullable String makerName) {
        this.makerName = makerName;
    }

    @Nullable
    public String getMakerImagePath() {
        return makerImagePath;
    }

    public void setMakerImagePath(@Nullable String makerImagePath) {
        this.makerImagePath = makerImagePath;
    }

    @Nullable
    public String getComment() {
        return comment;
    }

    public void setComment(@Nullable String comment) {
        this.comment = comment;
    }

    @Nullable
    public String getCommentImagePath() {
        return commentImagePath;
    }

    public void setCommentImagePath(@Nullable String commentImagePath) {
        this.commentImagePath = commentImagePath;
    }

    @Nullable
    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(@Nullable String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public int getIsMine() {
        return isMine;
    }

    public void setIsMine(int isMine) {
        this.isMine = isMine;
    }
}
