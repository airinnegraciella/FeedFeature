package com.example.main.feature.feed;

import com.example.featurefeed.data.source.model.local.Feed;
import com.example.featurefeed.data.source.model.remote.response.ResponseDislikeFeed;
import com.example.featurefeed.data.source.model.remote.response.ResponseLikeFeed;
import com.example.featurefeed.data.source.model.remote.response.feed.FeedPagination;
import com.example.featurefeed.domain.model.DislikeFeed;
import com.example.featurefeed.domain.model.GetFeed;
import com.example.featurefeed.domain.model.LikeFeed;
import com.example.featurefeed.domain.usecase.DislikeFeedUseCase;
import com.example.featurefeed.domain.usecase.GetFeedPaginationUseCase;
import com.example.featurefeed.domain.usecase.LikeFeedUseCase;
import com.example.main.core.base.ICallback;
import com.example.main.core.domain.user.model.CurrentUser;
import com.example.main.core.domain.user.usecase.GetCurrentUserUseCase;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class HomePresenterImpl implements HomeContract.Presenter {

    private HomeContract.View view;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private LikeFeedUseCase likeFeedUseCase;
    private DislikeFeedUseCase dislikeFeedUseCase;
    private GetCurrentUserUseCase getCurrentUserUseCase;
    private GetFeedPaginationUseCase getFeedPaginationUseCase;

    private int currentEmployeeId = 0;
    private final int LIMIT = 5;

    HomePresenterImpl(HomeContract.View view, LikeFeedUseCase likeFeedUseCase, DislikeFeedUseCase dislikeFeedUseCase, GetCurrentUserUseCase getCurrentUserUseCase, GetFeedPaginationUseCase getFeedPaginationUseCase) {
        this.view = view;
        this.likeFeedUseCase = likeFeedUseCase;
        this.dislikeFeedUseCase = dislikeFeedUseCase;
        this.getCurrentUserUseCase = getCurrentUserUseCase;
        this.getFeedPaginationUseCase = getFeedPaginationUseCase;
    }

    @Override
    public void onCreate() {
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
                view.showMessage(error);
            }

            @Override
            public void onInputEmpty() {

            }
        });
    }

    @Override
    public void loadFirstPageFromServer(int currentPage) {
        view.onStartRefresh();
        compositeDisposable.clear();
        getFeedPaginationUseCase.execute(new GetFeed(currentEmployeeId, currentPage, LIMIT), new ICallback<FeedPagination>() {
            @Override
            public void onDisposableAcquired(Disposable disposable) {
                compositeDisposable.add(disposable);
            }

            @Override
            public void onSuccess(FeedPagination result) {
                view.onAcceptLoadFeedFirstPage(result.getFeed_list(), result.getTotal_page());
                view.onStopRefresh();
            }

            @Override
            public void onError(String error) {
                view.showMessage(error);
                view.onStopRefresh();
            }

            @Override
            public void onInputEmpty() {

            }
        });

    }

    @Override
    public void loadNextPageFromServer(int currentPage) {
        compositeDisposable.clear();
        getFeedPaginationUseCase.execute(new GetFeed(currentEmployeeId, currentPage, LIMIT), new ICallback<FeedPagination>() {
            @Override
            public void onDisposableAcquired(Disposable disposable) {
                compositeDisposable.add(disposable);
            }

            @Override
            public void onSuccess(FeedPagination result) {
                view.onAcceptLoadFeedNextPage(result.getFeed_list(), result.getTotal_page());
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
    public void onAddFeedButtonClick() {
        view.navigateToAddFeed();
    }

    @Override
    public void onClickTotalLike(int feedId) {
        view.navigateToLikeList(feedId);
    }

    @Override
    public void onClickTotalComment(int feedId, int position) {
        view.navigateToCommentList(feedId, position);
    }

    @Override
    public void onClickBtnLike(int feedId, int isLiked, final int position) {
        view.onStartRefresh();
        if (isLiked == 1) {
            dislikeFeedUseCase.execute(new DislikeFeed(feedId, currentEmployeeId), new ICallback<ResponseDislikeFeed>() {
                @Override
                public void onDisposableAcquired(Disposable disposable) {
                    compositeDisposable.add(disposable);
                }

                @Override
                public void onSuccess(ResponseDislikeFeed result) {
                    view.setDislikeFeed(position);
                    view.onStopRefresh();
                }

                @Override
                public void onError(String error) {
                    view.showMessage(error);
                    view.onStopRefresh();
                }

                @Override
                public void onInputEmpty() {

                }
            });
        } else {
            likeFeedUseCase.execute(new LikeFeed(feedId, currentEmployeeId), new ICallback<ResponseLikeFeed>() {
                @Override
                public void onDisposableAcquired(Disposable disposable) {
                    compositeDisposable.add(disposable);
                }

                @Override
                public void onSuccess(ResponseLikeFeed result) {
                    view.setLikeFeed(position);
                    view.onStopRefresh();
                }

                @Override
                public void onError(String error) {
                    view.showMessage(error);
                    view.onStopRefresh();
                }

                @Override
                public void onInputEmpty() {

                }
            });
        }
    }

    @Override
    public void onClickBtnComment(int feedId, int position) {
        view.navigateToCommentList(feedId, position);
    }

    @Override
    public void onClickEmp(int empId) {

    }

    @Override
    public void onClickImagePost(String imageName) {

    }

    @Override
    public void onClickPost(Feed feed) {

    }

    @Override
    public void onClickEditFeed(int feedId, String feedPost, String feedImage, int position) {
        view.navigateToEditFeed(feedId, feedPost, feedImage, position);
    }

    @Override
    public void onClickDeleteFeed(int feedId, int position) {

    }
}
