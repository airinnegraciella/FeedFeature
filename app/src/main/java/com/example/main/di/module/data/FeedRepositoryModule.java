package com.example.main.di.module.data;

import com.example.featurefeed.data.source.repository.FeedRepositoryImpl;
import com.example.featurefeed.domain.repository.FeedRepository;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class FeedRepositoryModule {
    @Binds
    abstract FeedRepository provideFeedRepository(FeedRepositoryImpl feedRepositoryImpl);
}
