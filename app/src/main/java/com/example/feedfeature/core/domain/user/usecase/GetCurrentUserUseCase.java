package com.example.feedfeature.core.domain.user.usecase;

import com.example.feedfeature.core.base.BaseUseCase;
import com.example.feedfeature.core.base.ICallback;
import com.example.feedfeature.core.domain.user.model.CurrentUser;
import com.example.feedfeature.core.domain.user.repository.UserRepository;

import io.reactivex.disposables.Disposable;

public class GetCurrentUserUseCase extends BaseUseCase<String, ICallback<CurrentUser>> {
    UserRepository userRepository;
    @Override
    public void execute(String s, final ICallback<CurrentUser> callback) {
        userRepository.getCurrentUserLogin((ICallback<CurrentUser>)(new ICallback<CurrentUser>() {
            @Override
            public void onDisposableAcquired(Disposable disposable) {

            }

            @Override
            public void onSuccess(CurrentUser result) {
                callback.onSuccess(result);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }

            @Override
            public void onInputEmpty() {
                callback.onInputEmpty();
            }
        }));
    }
}
