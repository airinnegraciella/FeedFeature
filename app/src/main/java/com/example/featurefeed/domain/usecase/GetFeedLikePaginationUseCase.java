package com.example.featurefeed.domain.usecase;

import com.example.featurefeed.data.source.model.local.FeedLike;
import com.example.featurefeed.data.source.model.remote.response.like.FeedLikePagination;
import com.example.featurefeed.data.source.model.remote.response.like.ResponseFeedLikesPagination;
import com.example.featurefeed.domain.model.GetFeedLike;
import com.example.featurefeed.domain.repository.FeedRepository;
import com.example.main.core.base.BaseUseCase;
import com.example.main.core.base.ICallback;

import io.reactivex.disposables.Disposable;

public class GetFeedLikePaginationUseCase extends BaseUseCase<GetFeedLike, ICallback<FeedLikePagination>> {

    private FeedRepository feedRepository;

    public GetFeedLikePaginationUseCase(FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }

    @Override
    public void execute(GetFeedLike getFeedLike, final ICallback<FeedLikePagination> callback) {
        feedRepository.getFeedLikePagination(getFeedLike.getFeedId(), getFeedLike.getPage(), getFeedLike.getLimit(), new ICallback<ResponseFeedLikesPagination>() {
            @Override
            public void onDisposableAcquired(Disposable disposable) {
                callback.onDisposableAcquired(disposable);
            }

            @Override
            public void onSuccess(ResponseFeedLikesPagination result) {
                if(result.getStatus().equalsIgnoreCase("Success")){
                    callback.onSuccess(result.getFeedLikePagination());
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
