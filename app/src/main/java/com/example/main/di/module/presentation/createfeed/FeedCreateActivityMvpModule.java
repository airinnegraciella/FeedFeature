package com.example.main.di.module.presentation.createfeed;

import com.example.featurefeed.presentation.create_feed.FeedCreateActivity;
import com.example.featurefeed.presentation.create_feed.FeedCreateContract;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class FeedCreateActivityMvpModule {
    @Binds
    abstract FeedCreateContract.View provideView(FeedCreateActivity feedCreateActivity);
}
