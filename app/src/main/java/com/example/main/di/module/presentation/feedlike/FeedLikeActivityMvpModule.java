package com.example.main.di.module.presentation.feedlike;

import com.example.featurefeed.presentation.like.FeedLikeActivity;
import com.example.featurefeed.presentation.like.FeedLikeContract;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class FeedLikeActivityMvpModule {
    @Binds
    abstract FeedLikeContract.View provideView(FeedLikeActivity feedLikeActivity);
}
