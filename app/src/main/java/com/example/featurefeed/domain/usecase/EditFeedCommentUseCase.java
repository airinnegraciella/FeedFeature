package com.example.featurefeed.domain.usecase;

import com.example.featurefeed.data.source.model.remote.response.ResponseEditFeedComment;
import com.example.featurefeed.domain.model.EditFeedComment;
import com.example.featurefeed.domain.repository.FeedRepository;
import com.example.main.core.base.BaseUseCase;
import com.example.main.core.base.ICallback;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

public class EditFeedCommentUseCase extends BaseUseCase<EditFeedComment, ICallback<ResponseEditFeedComment>> {
    private FeedRepository feedRepository;
    
    @Inject
    public EditFeedCommentUseCase(FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }
    
    @Override
    public void execute(EditFeedComment editFeedComment, final ICallback<ResponseEditFeedComment> callback) {
        feedRepository.editFeedComment(editFeedComment.getFeedCommentId(), editFeedComment.getMakerId(), editFeedComment.getComment(), editFeedComment.getImage(), new ICallback<ResponseEditFeedComment>() {
            @Override
            public void onDisposableAcquired(Disposable disposable) {
                callback.onDisposableAcquired(disposable);
            }
            
            @Override
            public void onSuccess(ResponseEditFeedComment result) {
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
