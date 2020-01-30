package com.example.featurefeed.domain.usecase;

import com.example.featurefeed.data.source.model.remote.response.ResponseCreateFeedComment;
import com.example.featurefeed.domain.model.CreateFeedComment;
import com.example.featurefeed.domain.repository.FeedRepository;
import com.example.main.core.base.BaseUseCase;
import com.example.main.core.base.ICallback;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

public class CreateFeedCommentUseCase extends BaseUseCase<CreateFeedComment, ICallback<ResponseCreateFeedComment>> {
    
    private FeedRepository feedRepository;
    
    @Inject
    public CreateFeedCommentUseCase(FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }
    
    @Override
    public void execute(CreateFeedComment createFeedComment, final ICallback<ResponseCreateFeedComment> callback) {
        feedRepository.createFeedComment(createFeedComment.getFeedId(), createFeedComment.getMakerId(), createFeedComment.getComment(), createFeedComment.getImage(), new ICallback<ResponseCreateFeedComment>() {
            @Override
            public void onDisposableAcquired(Disposable disposable) {
                callback.onDisposableAcquired(disposable);
            }
            
            @Override
            public void onSuccess(ResponseCreateFeedComment result) {
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
