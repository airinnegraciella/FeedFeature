package com.example.featurefeed.domain.usecase;

import com.example.featurefeed.data.source.model.remote.response.feed.FeedPagination;
import com.example.featurefeed.data.source.model.remote.response.feed.ResponseFeedPagination;
import com.example.featurefeed.domain.model.GetFeedPagination;
import com.example.featurefeed.domain.repository.FeedRepository;
import com.example.main.core.base.BaseUseCase;
import com.example.main.core.base.ICallback;

import io.reactivex.disposables.Disposable;

public class GetFeedPaginationUseCase extends BaseUseCase<GetFeedPagination, ICallback<FeedPagination>> {

    private FeedRepository feedRepository;

    public GetFeedPaginationUseCase(FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }

    @Override
    public void execute(GetFeedPagination getFeedPagination, final ICallback<FeedPagination> callback) {
        feedRepository.getFeedPagination(getFeedPagination.getEmpId(), getFeedPagination.getPage(), getFeedPagination.getLimit(), new ICallback<ResponseFeedPagination>() {
            @Override
            public void onDisposableAcquired(Disposable disposable) {
                callback.onDisposableAcquired(disposable);
            }

            @Override
            public void onSuccess(ResponseFeedPagination result) {
                if (result.getStatus().equalsIgnoreCase("Success")) {
                    callback.onSuccess(result.getFeedPagination());
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
