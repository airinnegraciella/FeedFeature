package com.example.featurefeed.domain.usecase;

import com.example.featurefeed.data.source.model.local.Feed;
import com.example.featurefeed.data.source.model.remote.response.ResponseCreateFeed;
import com.example.featurefeed.domain.model.CreateFeed;
import com.example.featurefeed.domain.repository.FeedRepository;
import com.example.main.core.base.BaseUseCase;
import com.example.main.core.base.ICallback;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

public class CreateFeedUseCase extends BaseUseCase<CreateFeed, ICallback<Feed>> {
    private FeedRepository feedRepository;
    
    @Inject
    public CreateFeedUseCase(FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }
    
    @Override
    public void execute(CreateFeed createFeed, final ICallback<Feed> callback) {
        feedRepository.createFeed(createFeed.getMakerId(), createFeed.getPost(), createFeed.getImage()
                , new ICallback<ResponseCreateFeed>() {
                    @Override
                    public void onDisposableAcquired(Disposable disposable) {
                        callback.onDisposableAcquired(disposable);
                    }
                    
                    @Override
                    public void onSuccess(ResponseCreateFeed response) {
                        if (response.getStatus().equalsIgnoreCase("Success")) {
                            callback.onSuccess(response.getFeed());
                            
                        } else {
                            callback.onError(response.getMessage());
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
