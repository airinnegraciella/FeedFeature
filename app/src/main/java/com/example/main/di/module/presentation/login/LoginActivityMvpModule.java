package com.example.main.di.module.presentation.login;

import com.example.main.presentation.login.LoginActivity;
import com.example.main.presentation.login.LoginContract;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class LoginActivityMvpModule {
    @Binds
    abstract LoginContract.View provideView(LoginActivity loginActivity);
}
