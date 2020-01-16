package com.example.featurelike.domain.usecase;

import com.example.featurelike.data.source.model.remote.response.ResponseDislikeFeed;
import com.example.featurelike.domain.model.DislikeFeed;
import com.example.featurelike.domain.repository.FeedRepository;
import com.example.feedfeature.core.base.BaseUseCase;
import com.example.feedfeature.core.base.ICallback;

import io.reactivex.disposables.Disposable;

public class DislikeFeedUseCase extends BaseUseCase<DislikeFeed, ICallback<ResponseDislikeFeed>> {
    FeedRepository feedRepository;
    @Override
    public void execute(DislikeFeed params, final ICallback<ResponseDislikeFeed> callback) {
        feedRepository.dislikeFeed(params.getFeedId(), params.getEmployeeId(), (ICallback<ResponseDislikeFeed>)(new ICallback<ResponseDislikeFeed>() {
            @Override
            public void onDisposableAcquired(Disposable disposable) {
                callback.onDisposableAcquired(disposable);
            }

            @Override
            public void onSuccess(ResponseDislikeFeed result) {
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
