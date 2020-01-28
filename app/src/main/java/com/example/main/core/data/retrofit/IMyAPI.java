package com.example.main.core.data.retrofit;

import androidx.annotation.StringDef;

import com.example.featurefeed.data.source.model.remote.response.ResponseCreateFeed;
import com.example.featurefeed.data.source.model.remote.response.ResponseCreateFeedComment;
import com.example.featurefeed.data.source.model.remote.response.ResponseDeleteFeed;
import com.example.featurefeed.data.source.model.remote.response.ResponseDeleteFeedComment;
import com.example.featurefeed.data.source.model.remote.response.ResponseDislikeFeed;
import com.example.featurefeed.data.source.model.remote.response.ResponseEditFeed;
import com.example.featurefeed.data.source.model.remote.response.ResponseEditFeedComment;
import com.example.featurefeed.data.source.model.remote.response.ResponseGetFeed;
import com.example.featurefeed.data.source.model.remote.response.ResponseLikeFeed;
import com.example.featurefeed.data.source.model.remote.response.ResponseTotalFeedComment;
import com.example.featurefeed.data.source.model.remote.response.feed.ResponseFeedPagination;
import com.example.featurefeed.data.source.model.remote.response.comment.ResponseFeedCommentPagination;
import com.example.featurefeed.data.source.model.remote.response.like.ResponseFeedLikesPagination;
import com.example.featurefeed.data.source.model.remote.response.ResponseLogin;

import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface IMyAPI {
    @FormUrlEncoded
    @POST("user/login")
    Single<ResponseLogin> login(
            @Field("userId") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("feed/getFeedPagination")
    Single<ResponseFeedPagination> feed(
            @Field("page") int page,
            @Field("limit") int limit,
            @Field("empId") int empId
    );

    @FormUrlEncoded
    @POST("feed/getFeedLikePagination")
    Single<ResponseFeedLikesPagination> feed_likes(
            @Field("feedId") int feedId,
            @Field("page") int page,
            @Field("limit") int limit
    );

    @FormUrlEncoded
    @POST("feed/getFeedCommentPagination")
    Single<ResponseFeedCommentPagination> feed_comments(
            @Field("empId") int empId,
            @Field("feedId") int feedId,
            @Field("page") int page,
            @Field("limit") int limit
    );

    @FormUrlEncoded
    @POST("feed/likeFeed")
    Single<ResponseLikeFeed> likeFeed(
            @Field("feedId") int feedId,
            @Field("employeeId") int employeeId
    );

    @FormUrlEncoded
    @POST("feed/dislikeFeed")
    Single<ResponseDislikeFeed> dislikeFeed(
            @Field("feedId") int feedId,
            @Field("employeeId") int employeeId
    );

    @FormUrlEncoded
    @POST("feed/createFeed")
    Single<ResponseCreateFeed> createFeed(
            @Field("makerId") int makerId,
            @Field("post") String post,
            @Field("image") String image
    );

    @FormUrlEncoded
    @POST("feed/getFeedCommentPagination")
    Single<ResponseFeedCommentPagination> getFeedCommentPagination(
            @Field("empId") int empId,
            @Field("feedId") int feedId,
            @Field("page") int page,
            @Field("limit") int limit
    );

    @FormUrlEncoded
    @POST("feed/getFeedLikePagination")
    Single<ResponseFeedLikesPagination> getFeedLikePagination(
            @Field("feedId") int feedId,
            @Field("page") int page,
            @Field("limit") int limit
    );

    @FormUrlEncoded
    @POST("feed/editFeed")
    Single<ResponseEditFeed> editFeed(
            @Field("feedId") int feedId,
            @Field("makerId") int makerId,
            @Field("post") String post,
            @Field("image") String image
    );

    @FormUrlEncoded
    @POST("feed/deleteFeed")
    Single<ResponseDeleteFeed> deleteFeed(
            @Field("feedId") int feedId
    );

    @FormUrlEncoded
    @POST("feed/createFeedComment")
    Single<ResponseCreateFeedComment> createFeedComment(
            @Field("feedId") int feedId,
            @Field("makerId") int makerId,
            @Field("comment") String comment,
            @Field("image") String image
    );

    @FormUrlEncoded
    @POST("feed/editFeedComment")
    Single<ResponseEditFeedComment> editFeedComment(
            @Field("feedCommentId") int feedCommentId,
            @Field("makerId") int makerId,
            @Field("comment") String comment,
            @Field("image") String image
    );
    
    @FormUrlEncoded
    @POST("feed/deleteFeedComment")
    Single<ResponseDeleteFeedComment> deleteFeedComment(
            @Field("feedCommentId") int feedCommentId
    );
    
    @FormUrlEncoded
    @POST("feed/getTotalFeedComment")
    Single<ResponseTotalFeedComment> getTotalFeedComment(
            @Field("feedId") int feedId
    );
    
    @FormUrlEncoded
    @POST("feed/getFeed")
    Single<ResponseGetFeed> getFeed(
            @Field("feedId") int feedId,
            @Field("employeeId") int employeeId
    );

}
