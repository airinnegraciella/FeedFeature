package com.example.featurefeed.domain.usecase;

import com.example.featurefeed.data.source.model.remote.response.ResponseDeleteFeed;
import com.example.featurefeed.domain.repository.FeedRepository;
import com.example.main.core.base.BaseUseCase;
import com.example.main.core.base.ICallback;

import io.reactivex.disposables.Disposable;

public class DeleteFeedUseCase extends BaseUseCase<Integer, ICallback<ResponseDeleteFeed>> {
    private FeedRepository feedRepository;

    public DeleteFeedUseCase(FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }

    @Override
    public void execute(Integer integer, final ICallback<ResponseDeleteFeed> callback) {
        feedRepository.deleteFeed(integer, new ICallback<ResponseDeleteFeed>() {
            @Override
            public void onDisposableAcquired(Disposable disposable) {
                callback.onDisposableAcquired(disposable);
            }

            @Override
            public void onSuccess(ResponseDeleteFeed result) {
                if(result.getStatus().equalsIgnoreCase("Success")){
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
                callback.onInputEmpty();
            }
        });
    }
}
