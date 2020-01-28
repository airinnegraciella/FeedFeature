package com.example.featurefeed.presentation.like;

import com.example.featurefeed.data.source.model.remote.response.like.FeedLikePagination;
import com.example.featurefeed.domain.model.GetFeedLikePagination;
import com.example.featurefeed.domain.usecase.GetFeedLikePaginationUseCase;
import com.example.main.core.base.ICallback;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class FeedLikePresenterImpl implements FeedLikeContract.Presenter {

    private FeedLikeContract.View view;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private GetFeedLikePaginationUseCase getFeedLikePaginationUseCase;

    private int feedID;
    private final int LIMIT = 5;

    FeedLikePresenterImpl(FeedLikeContract.View view, GetFeedLikePaginationUseCase getFeedLikePaginationUseCase) {
        this.view = view;
        this.getFeedLikePaginationUseCase = getFeedLikePaginationUseCase;
    }

    @Override
    public void onCreate(int feedId) {
        feedID = feedId;
    }

    @Override
    public void loadFirstPageFromServer(int currentPage) {
        view.onStartLoad();
        compositeDisposable.clear();
        getFeedLikePaginationUseCase.execute(new GetFeedLikePagination(feedID, currentPage, LIMIT), new ICallback<FeedLikePagination>() {
            @Override
            public void onDisposableAcquired(Disposable disposable) {
                compositeDisposable.add(disposable);
            }

            @Override
            public void onSuccess(FeedLikePagination result) {
                view.onAcceptLoadFeedLikeFirstPage(result.getFeed_like_list(), result.getTotal_page());
                view.onStopLoad();
            }

            @Override
            public void onError(String error) {
                view.showMessage(error);
                view.onStopLoad();
            }

            @Override
            public void onInputEmpty() {

            }
        });
    }

    @Override
    public void loadNextPageFromServer(int currentPage) {
        compositeDisposable.clear();
        getFeedLikePaginationUseCase.execute(new GetFeedLikePagination(feedID, currentPage, LIMIT), new ICallback<FeedLikePagination>() {
            @Override
            public void onDisposableAcquired(Disposable disposable) {
                compositeDisposable.add(disposable);
            }

            @Override
            public void onSuccess(FeedLikePagination result) {
                view.onAcceptLoadFeedLikeNextPage(result.getFeed_like_list(), result.getTotal_page());
            }

            @Override
            public void onError(String error) {
                view.onErrorLoadNextPage(error);
            }

            @Override
            public void onInputEmpty() {

            }
        });
    }

}
