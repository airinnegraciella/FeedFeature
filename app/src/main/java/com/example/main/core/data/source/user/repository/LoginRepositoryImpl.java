package com.example.main.core.data.source.user.repository;

import com.example.main.core.data.sharedPreference.SharedPreferenceManager;
import com.example.main.core.data.source.user.model.remote.ResponseLogin;
import com.example.main.core.base.ICallback;
import com.example.main.core.data.retrofit.IMyAPI;
import com.example.main.core.domain.user.model.CurrentUser;
import com.example.main.core.domain.user.repository.LoginRepository;
import com.example.main.core.utils.Constant;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginRepositoryImpl implements LoginRepository {
    
    private IMyAPI myAPI;
    private SharedPreferenceManager spm;
    
    @Inject
    public LoginRepositoryImpl(IMyAPI myAPI, SharedPreferenceManager spm) {
        this.myAPI = myAPI;
        this.spm = spm;
    }
    
    @Override
    public void validateLogin(String email, String password, final ICallback<ResponseLogin> callback) {
        myAPI.login(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ResponseLogin>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        callback.onDisposableAcquired(d);
                    }
                    
                    @Override
                    public void onSuccess(ResponseLogin response) {
                        callback.onSuccess(response);
                    }
                    
                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }
                });
        
    }
    
    @Override
    public void setCurrentUserLogin(CurrentUser currentUser) {
        spm.saveSPString(Constant.SP_EMAIL, currentUser.getUserId());
        spm.saveSPInt(Constant.SP_EMPLOYEE_ID, currentUser.getEmployeeId());
        spm.saveSPBoolean(Constant.SP_IS_LOGGED_IN, true);
    }
}
