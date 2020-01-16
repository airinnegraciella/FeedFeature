package com.example.featurefeed.presentation.like;

import com.example.featurefeed.data.source.model.remote.response.like.ResponseFeedLikesPagination;
import com.example.main.core.data.retrofit.IMyAPI;
import com.example.main.core.data.retrofit.RetrofitClient;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class FeedLikePresenterImpl implements FeedLikeContract.Presenter {

    FeedLikeContract.View view;
    IMyAPI myAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    private int feedID;
    private final int LIMIT = 5;

    public FeedLikePresenterImpl(FeedLikeContract.View view){
        this.view = view;
    }

    @Override
    public void onCreate(int feedId) {
        feedID = feedId;
        initAPI();
    }

    private void initAPI() {
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(IMyAPI.class);
    }

    @Override
    public void loadFirstPageFromServer(int currentPage) {
        view.onStartLoad();
        compositeDisposable.clear();
        myAPI.feed_likes(feedID,currentPage,LIMIT)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ResponseFeedLikesPagination>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(ResponseFeedLikesPagination responseFeedLikesPagination) {
                        view.onAcceptLoadFeedLikeFirstPage(responseFeedLikesPagination.getFeedLikePagination().getFeed_like_list(), responseFeedLikesPagination.getFeedLikePagination().getTotal_page());
                        view.onStopLoad();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showMessage(e.getMessage());
                        view.onStopLoad();
                    }
                });
    }

    @Override
    public void loadNextPageFromServer(int currentPage) {
        compositeDisposable.clear();
        myAPI.feed_likes(feedID,currentPage,LIMIT)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ResponseFeedLikesPagination>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(ResponseFeedLikesPagination responseFeedLikesPagination) {
                        view.onAcceptLoadFeedLikeNextPage(responseFeedLikesPagination.getFeedLikePagination().getFeed_like_list(), responseFeedLikesPagination.getFeedLikePagination().getTotal_page());
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.onErrorLoadNextPage(e.getMessage());
                    }
                });
    }

}
