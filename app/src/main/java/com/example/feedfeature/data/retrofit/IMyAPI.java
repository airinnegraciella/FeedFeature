package com.example.feedfeature.data.retrofit;

import com.example.feedfeature.data.source.local.Feed;
import com.example.feedfeature.data.source.remote.response.ResponseFeed;
import com.example.feedfeature.data.source.remote.response.ResponseFeedLikes;
import com.example.feedfeature.data.source.remote.response.ResponseLogin;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface IMyAPI {
    @FormUrlEncoded
    @POST("user/login")
    Single<ResponseLogin> login(@Field("userId") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("feed/getFeedPagination")
    Single<ResponseFeed> feed(@Field("page") int page, @Field("limit") int limit, @Field("empId") int empId);

    @FormUrlEncoded
    @POST("feed/getFeedLikePagination")
    Single<ResponseFeedLikes> feed_likes(@Field("feedId") int feedId, @Field("page") int page, @Field("limit") int limit);

}
