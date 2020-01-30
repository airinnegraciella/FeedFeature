package com.example.main.di.module.presentation.feedcomment;

import com.example.featurefeed.presentation.comment.FeedCommentActivity;
import com.example.featurefeed.presentation.comment.FeedCommentContract;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class FeedCommentActivityMvpModule {
    @Binds
    abstract FeedCommentContract.View provideView(FeedCommentActivity feedCommentActivity);
}
