package com.example.main.di.module;

import android.content.Context;

import com.example.FeedApplication;
import com.example.main.di.qualifier.ApplicationContext;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ApplicationModule {
    @Binds
    @ApplicationContext
    abstract Context provideApplicationContext(FeedApplication feedApplication);
}
