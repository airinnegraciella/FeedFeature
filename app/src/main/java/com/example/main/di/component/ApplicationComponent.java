package com.example.main.di.component;

import com.example.FeedApplication;
import com.example.main.core.domain.user.repository.LoginRepository;
import com.example.main.di.module.ActivityModule;
import com.example.main.di.module.ApplicationModule;
import com.example.main.di.module.data.FeedRepositoryModule;
import com.example.main.di.module.data.LoginRepositoryModule;
import com.example.main.di.module.data.ServiceModule;
import com.example.main.di.module.data.SharedPreferenceModule;
import com.example.main.di.module.data.UserRepositoryModule;
import com.example.main.di.scope.ApplicationScope;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@ApplicationScope
@Component(modules ={
        AndroidSupportInjectionModule.class,
        ApplicationModule.class, SharedPreferenceModule.class,
        FeedRepositoryModule.class, ServiceModule.class,
        UserRepositoryModule.class, ActivityModule.class, LoginRepositoryModule.class})
public interface ApplicationComponent extends AndroidInjector<FeedApplication> {
    
    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<FeedApplication> {}
}
