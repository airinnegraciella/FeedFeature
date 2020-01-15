package com.example.feedfeature.domain.repository;

import com.example.feedfeature.core.base.ICallback;
import com.example.featurelike.data.source.remote.response.ResponseLikeFeed;

import okhttp3.MultipartBody;

public interface FeedRepository {
    void createFeed(int makerId, String post, String image, ICallback<ResponseCreateFeed> callback );

    void editFeed(int feedId, int makerId, String post, String image,
                  ICallback<ResponseEditFeed> callback );

    void getFeedPagination(int empId, int page, int limit,
                           ICallback<ResponseGetFeedPagination> callback );

    void createFeedComment(int feedId, int makerId, String comment, String image,
                           ICallback<ResponseCreateFeedComment> callback);

    void editFeedComment(int feedCommentId, int makerId, String comment, String image,
                         ICallback<ResponseEditFeedComment> callback);

    void getFeedCommentPagination(int employeeId, int feedId, int page, int limit,
                                  ICallback<ResponseGetFeedCommentPagination> callback);

    void likeFeed(int feedId, int employeeId,
                  ICallback<ResponseLikeFeed> callback);

    void dislikeFeed(int feedId, int employeeId,
                     ICallback<ResponseDislikeFeed> callback);

    void getFeedLikePagination(int feedId, int page, int limit,
                               ICallback<ResponseGetFeedLikePagination> callback);

    void uploadFeedImage(MultipartBody.Part file,
                         ICallback<ResponseFeedUploadImage> callback);

    void getTotalFeedComment(int feedId,  ICallback<ResponseTotalFeedComment> callback);

    void deleteFeed(int feedId,  ICallback<ResponseDeleteFeed> callback);

    void deleteFeedComment(int feedCommentId,  ICallback<ResponseDeleteFeedComment> callback);

    void getFeed(int feedId, int employeeId,  ICallback<ResponseGetFeed> callback);
}
