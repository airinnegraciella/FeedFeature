package com.example.main.di.module.data;

import com.example.main.core.data.source.user.repository.LoginRepositoryImpl;
import com.example.main.core.domain.user.repository.LoginRepository;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class LoginRepositoryModule {
    
    @Binds
    abstract LoginRepository provideLoginRepository(LoginRepositoryImpl loginRepository);
}
