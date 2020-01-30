package com.example.main.di.module.data;

import com.example.main.core.domain.user.repository.UserRepository;
import com.example.main.core.data.source.user.repository.UserRepositoryImpl;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class UserRepositoryModule {
    
    @Binds
    abstract UserRepository provideUserRepository(UserRepositoryImpl userRepositoryImpl);
}
