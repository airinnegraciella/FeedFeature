package com.example.featurefeed.domain.usecase;

import com.example.featurefeed.data.source.model.remote.response.comment.FeedCommentPagination;
import com.example.featurefeed.data.source.model.remote.response.comment.ResponseFeedCommentPagination;
import com.example.featurefeed.domain.model.GetFeedCommentPagination;
import com.example.featurefeed.domain.repository.FeedRepository;
import com.example.main.core.base.BaseUseCase;
import com.example.main.core.base.ICallback;

import io.reactivex.disposables.Disposable;

public class GetFeedCommentPaginationUseCase extends BaseUseCase<GetFeedCommentPagination, ICallback<FeedCommentPagination>> {

    private FeedRepository feedRepository;

    public GetFeedCommentPaginationUseCase(FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }

    @Override
    public void execute(GetFeedCommentPagination getFeedCommentPagination, final ICallback<FeedCommentPagination> callback) {
        feedRepository.getFeedCommentPagination(getFeedCommentPagination.getEmployeeId(), getFeedCommentPagination.getFeedId(), getFeedCommentPagination.getPage(), getFeedCommentPagination.getLimit(), new ICallback<ResponseFeedCommentPagination>() {
            @Override
            public void onDisposableAcquired(Disposable disposable) {
                callback.onDisposableAcquired(disposable);
            }

            @Override
            public void onSuccess(ResponseFeedCommentPagination result) {
                if (result.getStatus().equalsIgnoreCase("Success")) {
                    callback.onSuccess(result.getFeedCommentPagination());
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
