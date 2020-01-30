package com.example.featurefeed.domain.usecase;

import com.example.featurefeed.data.source.model.remote.response.ResponseDeleteFeedComment;
import com.example.featurefeed.domain.repository.FeedRepository;
import com.example.main.core.base.BaseUseCase;
import com.example.main.core.base.ICallback;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

public class DeleteFeedCommentUseCase extends BaseUseCase<Integer, ICallback<ResponseDeleteFeedComment>> {
    
    private FeedRepository feedRepository;
    
    @Inject
    public DeleteFeedCommentUseCase(FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }
    
    @Override
    public void execute(Integer integer, final ICallback<ResponseDeleteFeedComment> callback) {
        feedRepository.deleteFeedComment(integer, new ICallback<ResponseDeleteFeedComment>() {
            @Override
            public void onDisposableAcquired(Disposable disposable) {
                callback.onDisposableAcquired(disposable);
            }
            
            @Override
            public void onSuccess(ResponseDeleteFeedComment result) {
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
