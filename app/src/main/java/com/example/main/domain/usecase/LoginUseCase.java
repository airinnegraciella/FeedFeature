package com.example.main.domain.usecase;

import com.example.main.core.base.BaseUseCase;
import com.example.main.core.base.ICallback;
import com.example.main.core.data.source.user.model.remote.ResponseLogin;
import com.example.main.core.domain.user.model.CurrentUser;
import com.example.main.core.domain.user.model.LoginUser;
import com.example.main.core.domain.user.repository.LoginRepository;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

public class LoginUseCase extends BaseUseCase<LoginUser, ICallback<ResponseLogin>> {
    
    private LoginRepository loginRepository;
    
    @Inject
    public LoginUseCase(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }
    
    @Override
    public void execute(final LoginUser loginUser, final ICallback<ResponseLogin> callback) {
        if(loginUser.getUserId().isEmpty() || loginUser.getPassword().isEmpty()){
            callback.onInputEmpty();
        }
        else{
            loginRepository.validateLogin(loginUser.getUserId(), loginUser.getPassword(), new ICallback<ResponseLogin>() {
                @Override
                public void onDisposableAcquired(Disposable disposable) {
                    callback.onDisposableAcquired(disposable);
                }
    
                @Override
                public void onSuccess(ResponseLogin result) {
                    if(result.getStatus().equalsIgnoreCase("Success")){
                        loginRepository.setCurrentUserLogin(new CurrentUser(loginUser.getUserId(), result.getEmployee().getEmployeeId()));
                        callback.onSuccess(result);
                    }
                    else{
                        callback.onError(result.getMessage());
                    }
                }
    
                @Override
                public void onError(String error) {
                    callback.onError(error);
                }
    
                @Override
                public void onInputEmpty() {
        
                }
            });
        }
    }
}
