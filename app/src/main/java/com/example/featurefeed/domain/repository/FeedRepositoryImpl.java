package com.example.featurefeed.domain.repository;

import com.example.featurefeed.data.source.model.remote.response.ResponseDislikeFeed;
import com.example.featurefeed.data.source.model.remote.response.ResponseLikeFeed;
import com.example.main.core.base.ICallback;
import com.example.main.core.data.retrofit.HostSelectionInterceptor;
import com.example.main.core.data.retrofit.IMyAPI;
import com.example.main.core.utils.Constant;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FeedRepositoryImpl implements FeedRepository {

    private IMyAPI myAPI;
    private HostSelectionInterceptor hostSelectionInterceptor;

    public FeedRepositoryImpl(IMyAPI myAPI){
        this.myAPI = myAPI;
        hostSelectionInterceptor = new HostSelectionInterceptor();
    }

    @Override
    public void likeFeed(int feedId, int employeeId, final ICallback<ResponseLikeFeed> callback) {
        hostSelectionInterceptor.setHost(Constant.getHOST());
        myAPI.likeFeed(feedId, employeeId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<ResponseLikeFeed>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        callback.onDisposableAcquired(d);
                    }

                    @Override
                    public void onSuccess(ResponseLikeFeed response) {
                        callback.onSuccess(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }
        });
    }

    @Override
    public void dislikeFeed(int feedId, int employeeId, final ICallback<ResponseDislikeFeed> callback) {
        hostSelectionInterceptor.setHost(Constant.getHOST());
        myAPI.dislikeFeed(feedId, employeeId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<ResponseDislikeFeed>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        callback.onDisposableAcquired(d);
                    }

                    @Override
                    public void onSuccess(ResponseDislikeFeed response) {
                        callback.onSuccess(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }
        });
    }
}
