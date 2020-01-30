package com.example.main.di.module.presentation.feedcomment;

import com.example.featurefeed.presentation.adapter.FeedCommentsAdapter;
import com.example.featurefeed.presentation.comment.FeedCommentActivity;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class FeedCommentAdapterModule {
    @Binds
    abstract FeedCommentsAdapter.ClickListener provideClickListener (FeedCommentActivity feedCommentActivity);
}
