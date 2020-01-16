package com.example.featurefeed.data.source.model.local;

import com.google.gson.annotations.SerializedName;

public class FeedLike {

    @SerializedName("FeedLikeId")
    private int feedLikeId;
    @SerializedName("FeedId")
    private int feedId;
    @SerializedName("EmployeeId")
    private int employeeId;
    @SerializedName("MakerName")
    private String makerName;
    @SerializedName("MakerImagePath")
    private String makerImagePath;
    @SerializedName("UpdatedDate")
    private String updatedDate;

    public FeedLike(){

    }

    public FeedLike(int feedLikeId, int feedId, int employeeId, String makerName, String makerImagePath, String updatedDate) {
        this.feedLikeId = feedLikeId;
        this.feedId = feedId;
        this.employeeId = employeeId;
        this.makerName = makerName;
        this.makerImagePath = makerImagePath;
        this.updatedDate = updatedDate;
    }

    public int getFeedLikeId() {
        return feedLikeId;
    }

    public void setFeedLikeId(int feedLikeId) {
        this.feedLikeId = feedLikeId;
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

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }
}
