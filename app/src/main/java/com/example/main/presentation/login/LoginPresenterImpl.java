package com.example.main.presentation.login;

import com.example.main.core.base.ICallback;
import com.example.main.core.data.source.user.model.remote.ResponseLogin;
import com.example.main.core.domain.user.model.LoginUser;
import com.example.main.domain.usecase.LoginUseCase;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class LoginPresenterImpl implements LoginContract.Presenter {
    
    private LoginContract.View view;
    private CompositeDisposable compositeDisposable;
    
    private LoginUseCase loginUseCase;
    
    @Inject
    public LoginPresenterImpl(LoginContract.View view, LoginUseCase loginUseCase) {
        this.view = view;
        this.loginUseCase = loginUseCase;
    }
    
    @Override
    public void onButtonLoginClick(String userId, String password) {
        view.onStartLogin();
        loginUseCase.execute(new LoginUser(userId, password), new ICallback<ResponseLogin>() {
            @Override
            public void onDisposableAcquired(Disposable disposable) {
                compositeDisposable.add(disposable);
            }
    
            @Override
            public void onSuccess(ResponseLogin result) {
                view.successLogin();
            }
    
            @Override
            public void onError(String error) {
                view.failLogin(error);
            }
    
            @Override
            public void onInputEmpty() {
                view.onInputEmpty();
            }
        });
    
    }
    
    @Override
    public void onCreate() {
        compositeDisposable = new CompositeDisposable();
    }
}
