package com.example.featurefeed.domain.usecase;

import com.example.featurefeed.data.source.model.remote.response.ResponseDislikeFeed;
import com.example.featurefeed.domain.model.DislikeFeed;
import com.example.featurefeed.domain.repository.FeedRepository;
import com.example.main.core.base.BaseUseCase;
import com.example.main.core.base.ICallback;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

public class DislikeFeedUseCase extends BaseUseCase<DislikeFeed, ICallback<ResponseDislikeFeed>> {
    FeedRepository feedRepository;

    @Inject
    public DislikeFeedUseCase(FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }

    @Override
    public void execute(DislikeFeed params, final ICallback<ResponseDislikeFeed> callback) {
        feedRepository.dislikeFeed(params.getFeedId(), params.getEmployeeId(), (ICallback<ResponseDislikeFeed>) (new ICallback<ResponseDislikeFeed>() {
            @Override
            public void onDisposableAcquired(Disposable disposable) {
                callback.onDisposableAcquired(disposable);
            }

            @Override
            public void onSuccess(ResponseDislikeFeed result) {
                if (result.getStatus().equalsIgnoreCase("Success")) {
                    callback.onSuccess(result);
                } else {
                    callback.onError(result.getMessage());
                }

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
