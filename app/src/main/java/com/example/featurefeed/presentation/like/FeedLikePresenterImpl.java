package com.example.featurefeed.presentation.like;

import com.example.featurefeed.data.source.model.remote.response.like.FeedLikePagination;
import com.example.featurefeed.data.source.model.remote.response.like.ResponseFeedLikesPagination;
import com.example.featurefeed.domain.model.GetFeedLike;
import com.example.featurefeed.domain.usecase.GetFeedLikePaginationUseCase;
import com.example.main.core.base.ICallback;
import com.example.main.core.data.retrofit.IMyAPI;
import com.example.main.core.data.retrofit.RetrofitClient;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class FeedLikePresenterImpl implements FeedLikeContract.Presenter {

    private FeedLikeContract.View view;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private GetFeedLikePaginationUseCase getFeedLikePaginationUseCase;

    private int feedID;
    private final int LIMIT = 5;

    @Inject
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
        getFeedLikePaginationUseCase.execute(new GetFeedLike(feedID, currentPage, LIMIT), new ICallback<FeedLikePagination>() {
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
        getFeedLikePaginationUseCase.execute(new GetFeedLike(feedID, currentPage, LIMIT), new ICallback<FeedLikePagination>() {
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
