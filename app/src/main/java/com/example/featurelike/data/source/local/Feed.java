package com.example.featurelike.data.source.local;

import com.google.gson.annotations.SerializedName;

public class Feed {
    @SerializedName("FeedId")
    private int feedId;
    @SerializedName("MakerId")
    private int makerId;
    @SerializedName("MakerName")
    private String makerName;
    @SerializedName("MakerImagePath")
    private String makerImagePath;
    @SerializedName("Post")
    private String post;
    @SerializedName("PostImagePath")
    private String postImagePath;
    @SerializedName("UpdatedDate")
    private String updatedDate;
    @SerializedName("TotalComment")
    private int totalComment;
    @SerializedName("TotalLike")
    private int totalLike;
    @SerializedName("IsLiked")
    private int isLiked;
    @SerializedName("IsMine")
    private int isMine;

    public Feed() {

    }

    public Feed(int feedId, int makerId, String makerName, String makerImagePath, String post, String postImagePath, String updatedDate, int totalComment, int totalLike, int isLiked, int isMine) {
        this.feedId = feedId;
        this.makerId = makerId;
        this.makerName = makerName;
        this.makerImagePath = makerImagePath;
        this.post = post;
        this.postImagePath = postImagePath;
        this.updatedDate = updatedDate;
        this.totalComment = totalComment;
        this.totalLike = totalLike;
        this.isLiked = isLiked;
        this.isMine = isMine;
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

    public String getMakerName() {
        return makerName;
    }

    public void setMakerName(String makerName) {
        this.makerName = makerName;
    }

    public String getMakerImagePath() {
        return makerImagePath;
    }

    public void setMakerImagePath(String makerImagePath) {
        this.makerImagePath = makerImagePath;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getPostImagePath() {
        return postImagePath;
    }

    public void setPostImagePath(String postImagePath) {
        this.postImagePath = postImagePath;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public int getTotalComment() {
        return totalComment;
    }

    public void setTotalComment(int totalComment) {
        this.totalComment = totalComment;
    }

    public int getTotalLike() {
        return totalLike;
    }

    public void setTotalLike(int totalLike) {
        this.totalLike = totalLike;
    }

    public int getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(int isLiked) {
        this.isLiked = isLiked;
    }

    public int getIsMine() {
        return isMine;
    }

    public void setIsMine(int isMine) {
        this.isMine = isMine;
    }
}