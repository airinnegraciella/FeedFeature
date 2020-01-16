package com.example.main.core.data.retrofit;

import com.example.featurefeed.data.source.model.remote.response.ResponseDislikeFeed;
import com.example.featurefeed.data.source.model.remote.response.ResponseLikeFeed;
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
}
