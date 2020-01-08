package com.example.feedfeature.Model;

public class Feed {
    private String FeedId;
    private String MakerId;
    private String MakerName;
    private String MakerImagePath;
    private String Post;
    private String PostImagePath;
    private String UpdatedDate;
    private String TotalComment;
    private String TotalLike;
    private String IsLiked;
    private String IsMine;

    public Feed(){

    }

    public Feed(String feedId, String makerId, String makerName, String makerImagePath, String post, String postImagePath, String updatedDate, String totalComment, String totalLike, String isLiked, String isMine) {
        FeedId = feedId;
        MakerId = makerId;
        MakerName = makerName;
        MakerImagePath = makerImagePath;
        Post = post;
        PostImagePath = postImagePath;
        UpdatedDate = updatedDate;
        TotalComment = totalComment;
        TotalLike = totalLike;
        IsLiked = isLiked;
        IsMine = isMine;
    }


}
