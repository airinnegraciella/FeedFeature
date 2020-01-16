package com.example.featurefeed.domain.usecase;

import com.example.main.core.base.BaseUseCase;
import com.example.main.core.base.ICallback;
import com.example.featurefeed.domain.model.LikeFeed;
import com.example.featurefeed.data.source.model.remote.response.ResponseLikeFeed;
import com.example.featurefeed.domain.repository.FeedRepository;

import io.reactivex.disposables.Disposable;

public class LikeFeedUseCase extends BaseUseCase<LikeFeed, ICallback<ResponseLikeFeed>> {
    private final FeedRepository feedRepository;

    public LikeFeedUseCase(FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }

    @Override
    public void execute(LikeFeed params, final ICallback<ResponseLikeFeed> callback) {
        feedRepository.likeFeed(params.getFeedId(), params.getEmployeeId(), (ICallback<ResponseLikeFeed>)(new ICallback<ResponseLikeFeed>() {
            @Override
            public void onDisposableAcquired(Disposable disposable) {
                callback.onDisposableAcquired(disposable);
            }

            @Override
            public void onSuccess(ResponseLikeFeed result) {
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
        }) );
    }
}
