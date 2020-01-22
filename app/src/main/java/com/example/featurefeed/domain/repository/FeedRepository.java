package com.example.featurefeed.domain.repository;

import com.example.featurefeed.data.source.model.remote.response.ResponseCreateFeed;
import com.example.featurefeed.data.source.model.remote.response.ResponseDislikeFeed;
import com.example.featurefeed.data.source.model.remote.response.ResponseEditFeed;
import com.example.featurefeed.data.source.model.remote.response.comment.ResponseFeedCommentPagination;
import com.example.featurefeed.data.source.model.remote.response.feed.ResponseFeedPagination;
import com.example.featurefeed.data.source.model.remote.response.like.ResponseFeedLikesPagination;
import com.example.main.core.base.ICallback;
import com.example.featurefeed.data.source.model.remote.response.ResponseLikeFeed;

public interface FeedRepository {
    void createFeed(int makerId, String post, String image, ICallback<ResponseCreateFeed> callback);

    void editFeed(int feedId, int makerId, String post, String image, ICallback<ResponseEditFeed> callback );

    void getFeedPagination(int empId, int page, int limit, ICallback<ResponseFeedPagination> callback);

//    void createFeedComment(int feedId, int makerId, String comment, String image, ICallback<ResponseCreateFeedComment> callback);

//    void editFeedComment(int feedCommentId, int makerId, String comment, String image, ICallback<ResponseEditFeedComment> callback);

    void getFeedCommentPagination(int employeeId, int feedId, int page, int limit, ICallback<ResponseFeedCommentPagination> callback);

    void likeFeed(int feedId, int employeeId, ICallback<ResponseLikeFeed> callback);

    void dislikeFeed(int feedId, int employeeId, ICallback<ResponseDislikeFeed> callback);

    void getFeedLikePagination(int feedId, int page, int limit, ICallback<ResponseFeedLikesPagination> callback);

//    void uploadFeedImage(MultipartBody.Part file, ICallback<ResponseFeedUploadImage> callback);

//    void getTotalFeedComment(int feedId, ICallback<ResponseTotalFeedComment> callback);

//    void deleteFeed(int feedId,  ICallback<ResponseDeleteFeed> callback);

//    void deleteFeedComment(int feedCommentId,  ICallback<ResponseDeleteFeedComment> callback);

//    void getFeed(int feedId, int employeeId,  ICallback<ResponseGetFeed> callback);
}
