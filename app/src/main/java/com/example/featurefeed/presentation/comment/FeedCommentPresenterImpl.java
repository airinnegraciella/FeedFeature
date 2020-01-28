package com.example.featurefeed.presentation.comment;

import android.app.Activity;
import android.content.Intent;

import com.example.featurefeed.data.source.model.remote.response.ResponseCreateFeedComment;
import com.example.featurefeed.data.source.model.remote.response.ResponseDeleteFeedComment;
import com.example.featurefeed.data.source.model.remote.response.comment.FeedCommentPagination;
import com.example.featurefeed.domain.model.CreateFeedComment;
import com.example.featurefeed.domain.model.GetFeedCommentPagination;
import com.example.featurefeed.domain.usecase.CreateFeedCommentUseCase;
import com.example.featurefeed.domain.usecase.DeleteFeedCommentUseCase;
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
    private CreateFeedCommentUseCase createFeedCommentUseCase;
    private DeleteFeedCommentUseCase deleteFeedCommentUseCase;

    private int feedId;
    private int positionFeed;
    private int currentEmployeeId = 0;
    private final int LIMIT = 5;

    FeedCommentPresenterImpl(FeedCommentContract.View view,
                             GetFeedCommentPaginationUseCase getFeedCommentPaginationUseCase,
                             GetCurrentUserUseCase getCurrentUserUseCase,
                             CreateFeedCommentUseCase createFeedCommentUseCase,
                             DeleteFeedCommentUseCase deleteFeedCommentUseCase) {
        this.view = view;
        this.getFeedCommentPaginationUseCase = getFeedCommentPaginationUseCase;
        this.getCurrentUserUseCase = getCurrentUserUseCase;
        this.createFeedCommentUseCase = createFeedCommentUseCase;
        this.deleteFeedCommentUseCase = deleteFeedCommentUseCase;
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
        getFeedCommentPaginationUseCase.execute(new GetFeedCommentPagination(currentEmployeeId, feedId, currentPage, LIMIT), new ICallback<FeedCommentPagination>() {
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
        getFeedCommentPaginationUseCase.execute(new GetFeedCommentPagination(currentEmployeeId, feedId, currentPage, LIMIT), new ICallback<FeedCommentPagination>() {
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

    @Override
    public void onBtnCommentClick(String comment) {
        view.onStartLoad();
        createFeedCommentUseCase.execute(new CreateFeedComment(feedId, currentEmployeeId, comment, ""), new ICallback<ResponseCreateFeedComment>() {
            @Override
            public void onDisposableAcquired(Disposable disposable) {
                compositeDisposable.add(disposable);
            }

            @Override
            public void onSuccess(ResponseCreateFeedComment result) {
                view.onCommentSuccess(result.getFeedComment());
                view.onStopLoad();
            }

            @Override
            public void onError(String error) {
                view.onCommentError(error);
                view.onStopLoad();
            }

            @Override
            public void onInputEmpty() {

            }
        });
    }

    @Override
    public void onClickEditComment(int feedCommentId, String feedComment, String feedCommentImage, int position) {
        view.navigateToEditFeed(feedCommentId, feedComment, position);
    }

    @Override
    public void onClickDeleteComment(int feedCommentId, final int position) {
        view.onStartLoad();
        deleteFeedCommentUseCase.execute(feedCommentId, new ICallback<ResponseDeleteFeedComment>() {
            @Override
            public void onDisposableAcquired(Disposable disposable) {
                compositeDisposable.add(disposable);
            }
    
            @Override
            public void onSuccess(ResponseDeleteFeedComment result) {
                view.onDeleteCommentSuccess(position);
                view.onStopLoad();
            }
    
            @Override
            public void onError(String error) {
                view.showMessage("$error#$feedCommentId");
                view.onStopLoad();
            }
    
            @Override
            public void onInputEmpty() {
        
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (data != null) {
                int position = data.getIntExtra("positionKey", 0);
                String newComment = data.getStringExtra("newComment");

                view.editComment(position, newComment, "");
            }
        }
    }
}
