package com.example.main.di.module;

import com.example.featurefeed.presentation.comment.FeedCommentActivity;
import com.example.featurefeed.presentation.create_feed.FeedCreateActivity;
import com.example.featurefeed.presentation.edit_comment.EditFeedCommentActivity;
import com.example.featurefeed.presentation.like.FeedLikeActivity;
import com.example.main.di.module.presentation.createfeed.FeedCreateActivityMvpModule;
import com.example.main.di.module.presentation.editcomment.EditFeedCommentActivityMvpModule;
import com.example.main.di.module.presentation.feedcomment.FeedCommentActivityMvpModule;
import com.example.main.di.module.presentation.feedcomment.FeedCommentAdapterModule;
import com.example.main.di.module.presentation.feedlike.FeedLikeActivityMvpModule;
import com.example.main.di.module.presentation.feedlike.FeedLikeAdapterModule;
import com.example.main.di.module.presentation.home.HomeActivityMvpModule;
import com.example.main.di.module.presentation.home.HomeAdapterModule;
import com.example.main.di.module.presentation.login.LoginActivityMvpModule;
import com.example.main.di.scope.ActivityScope;
import com.example.main.presentation.home.HomeActivity;
import com.example.main.presentation.login.LoginActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {
    @ContributesAndroidInjector(modules = {FeedCreateActivityMvpModule.class})
    @ActivityScope
    abstract FeedCreateActivity provideFeedCreateActivity();
    
    @ContributesAndroidInjector(modules = {EditFeedCommentActivityMvpModule.class})
    @ActivityScope
    abstract EditFeedCommentActivity provideEditFeedCommentActivity();
    
    @ContributesAndroidInjector(modules = {FeedCommentActivityMvpModule.class, FeedCommentAdapterModule.class})
    @ActivityScope
    abstract FeedCommentActivity provideFeedCommentActivity();
    
    @ContributesAndroidInjector(modules = {FeedLikeActivityMvpModule.class, FeedLikeAdapterModule.class})
    @ActivityScope
    abstract FeedLikeActivity provideFeedLikeActivity();
    
    @ContributesAndroidInjector(modules = {HomeActivityMvpModule.class, HomeAdapterModule.class})
    @ActivityScope
    abstract HomeActivity provideHomeActivity();
    
    @ContributesAndroidInjector(modules = {LoginActivityMvpModule.class})
    @ActivityScope
    abstract LoginActivity provideLoginActivity();
    
}
