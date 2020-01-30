package com.example.main.core.data.source.user.repository;

import com.example.main.core.base.ICallback;
import com.example.main.core.data.sharedPreference.SharedPreferenceManager;
import com.example.main.core.domain.user.model.CurrentUser;
import com.example.main.core.domain.user.repository.UserRepository;

import javax.inject.Inject;

public class UserRepositoryImpl implements UserRepository {
    private final SharedPreferenceManager spm;
    
    @Inject
    public UserRepositoryImpl(SharedPreferenceManager spm) {
        this.spm = spm;
    }

    @Override
    public void getCurrentUserLogin(ICallback<CurrentUser> callback) {
        callback.onSuccess(new CurrentUser(spm.getSPUserId(), spm.getSPEmployeeId()));
    }

}
