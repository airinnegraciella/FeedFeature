package com.example.main.di.module.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.main.core.utils.Constant;
import com.example.main.di.qualifier.ApplicationContext;

import dagger.Module;
import dagger.Provides;

@Module
public class SharedPreferenceModule {
    @Provides
    SharedPreferences provideSP(@ApplicationContext Context context){
        return context.getSharedPreferences(Constant.SP_APP, Context.MODE_PRIVATE);
    }
    
    @Provides
    SharedPreferences.Editor provideSPEditor(SharedPreferences sharedPreferences){
        return sharedPreferences.edit();
    }
}
