package com.example.main.di.module.presentation.home;

import com.example.main.presentation.home.HomeActivity;
import com.example.main.presentation.home.HomeContract;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class HomeActivityMvpModule {
    @Binds
    abstract HomeContract.View provideView(HomeActivity homeActivity);
}
