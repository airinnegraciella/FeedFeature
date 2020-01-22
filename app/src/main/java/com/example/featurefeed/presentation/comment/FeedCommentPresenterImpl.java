package com.example.featurefeed.presentation.comment;

import com.example.featurefeed.data.source.model.remote.response.comment.FeedCommentPagination;
import com.example.featurefeed.domain.model.GetFeedComment;
import com.example.featurefeed.domain.usecase.GetFeedCommentPaginationUseCase;
import com.example.main.core.base.ICallback;
import com.example.main.core.domain.user.model.CurrentUser;
import com.example.main.core.domain.user.usecase.GetCurrentUserUseCase;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class FeedCommentPresenterImpl implements FeedCommentContract.Presenter {

    private FeedCommentContract.View view;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private GetFeedCommentPaginationUseCase getFeedCommentPaginationUseCase;
    private GetCurrentUserUseCase getCurrentUserUseCase;

    private int feedId;
    private int positionFeed;
    private int currentEmployeeId = 0;
    private final int LIMIT = 5;

    FeedCommentPresenterImpl(FeedCommentContract.View view, GetFeedCommentPaginationUseCase getFeedCommentPaginationUseCase, GetCurrentUserUseCase getCurrentUserUseCase) {
        this.view = view;
        this.getFeedCommentPaginationUseCase = getFeedCommentPaginationUseCase;
        this.getCurrentUserUseCase = getCurrentUserUseCase;
    }

    @Override
    public void onCreate(int feedId, int positionFeed) {
        this.feedId = feedId;
        this.positionFeed = positionFeed;
        getCurrentUserUseCase.execute("", new ICallback<CurrentUser>() {
            @Override
            public void onDisposableAcquired(Disposable disposable) {

            }

            @Override
            public void onSuccess(CurrentUser result) {
                currentEmployeeId = result.getEmployeeId();
            }

            @Override
            public void onError(String error) {

            }

            @Override
            public void onInputEmpty() {

            }
        });
    }

    @Override
    public void loadFirstPageFromServer(int currentPage) {
        view.onStartLoad();
        compositeDisposable.clear();
        getFeedCommentPaginationUseCase.execute(new GetFeedComment(currentEmployeeId, feedId, currentPage, LIMIT), new ICallback<FeedCommentPagination>() {
            @Override
            public void onDisposableAcquired(Disposable disposable) {
                compositeDisposable.add(disposable);
            }

            @Override
            public void onSuccess(FeedCommentPagination result) {
                view.onAcceptLoadFeedCommentFirstPage(result.getFeed_comment_list(), result.getTotal_page());
                view.onStopLoad();
            }

            @Override
            public void onError(String error) {
                view.onErrorLoad(error);
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
        getFeedCommentPaginationUseCase.execute(new GetFeedComment(currentEmployeeId, feedId, currentPage, LIMIT), new ICallback<FeedCommentPagination>() {
            @Override
            public void onDisposableAcquired(Disposable disposable) {
                compositeDisposable.add(disposable);
            }

            @Override
            public void onSuccess(FeedCommentPagination result) {
                view.onAcceptLoadFeedCommentNextPage(result.getFeed_comment_list(), result.getTotal_page());
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
