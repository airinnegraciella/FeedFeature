package com.example.featurefeed.domain.usecase;

import com.example.featurefeed.data.source.model.remote.response.ResponseEditFeed;
import com.example.featurefeed.domain.model.EditFeed;
import com.example.featurefeed.domain.repository.FeedRepository;
import com.example.main.core.base.BaseUseCase;
import com.example.main.core.base.ICallback;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

public class EditFeedUseCase extends BaseUseCase<EditFeed, ICallback<ResponseEditFeed>> {
    private FeedRepository feedRepository;
    
    @Inject
    public EditFeedUseCase(FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }
    
    @Override
    public void execute(EditFeed editFeed, final ICallback<ResponseEditFeed> callback) {
        feedRepository.editFeed(editFeed.getFeedId(), editFeed.getMakerId(), editFeed.getPost(), editFeed.getImage(), new ICallback<ResponseEditFeed>() {
            @Override
            public void onDisposableAcquired(Disposable disposable) {
                callback.onDisposableAcquired(disposable);
            }
            
            @Override
            public void onSuccess(ResponseEditFeed result) {
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
        });
    }
}
