package com.example.main.di.module.presentation.home;

import com.example.featurefeed.presentation.adapter.FeedAdapter;
import com.example.main.presentation.home.HomeActivity;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class HomeAdapterModule {
    @Binds
    abstract FeedAdapter.ClickListener provideClickListener(HomeActivity homeActivity);
}
