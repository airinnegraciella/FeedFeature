package com.example.main.core.base;

import io.reactivex.disposables.Disposable;

public interface ICallback<P> {
    void onDisposableAcquired(Disposable disposable);
    
    void onSuccess(P result);
    
    void onError(String error);
    
    void onInputEmpty();
}
