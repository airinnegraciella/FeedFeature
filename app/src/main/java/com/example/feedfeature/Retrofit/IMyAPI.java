package com.example.feedfeature.Retrofit;

import com.example.feedfeature.Model.Feed;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.POST;

public interface IMyAPI {
    @POST("getFeedPagination")
    Single<List<Feed>> getPosts();
}
