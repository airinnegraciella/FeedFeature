package com.example.main.di.module.presentation.feedlike;

import com.example.featurefeed.presentation.adapter.FeedLikesAdapter;
import com.example.featurefeed.presentation.like.FeedLikeActivity;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class FeedLikeAdapterModule {
    @Binds
    abstract FeedLikesAdapter.ClickListener provideClickListener(FeedLikeActivity feedLikeActivity);
}
