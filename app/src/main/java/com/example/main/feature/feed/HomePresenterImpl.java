package com.example.main.feature.feed;

import android.widget.TextView;

import com.example.featurefeed.data.source.model.local.Feed;
import com.example.featurefeed.data.source.model.remote.response.ResponseDislikeFeed;
import com.example.featurefeed.data.source.model.remote.response.ResponseLikeFeed;
import com.example.featurefeed.data.source.model.remote.response.feed.ResponseFeedPagination;
import com.example.featurefeed.domain.model.DislikeFeed;
import com.example.featurefeed.domain.model.LikeFeed;
import com.example.featurefeed.domain.repository.FeedRepository;
import com.example.featurefeed.domain.repository.FeedRepositoryImpl;
import com.example.featurefeed.domain.usecase.DislikeFeedUseCase;
import com.example.featurefeed.domain.usecase.LikeFeedUseCase;
import com.example.main.core.base.ICallback;
import com.example.main.core.data.retrofit.IMyAPI;
import com.example.main.core.data.retrofit.RetrofitClient;
import com.example.main.core.data.sharedPreference.SharedPreferenceManager;
import com.example.main.core.domain.user.model.CurrentUser;
import com.example.main.core.domain.user.repository.UserRepositoryImpl;
import com.example.main.core.domain.user.usecase.GetCurrentUserUseCase;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class HomePresenterImpl implements HomeContract.Presenter {

    HomeContract.View view;
    IMyAPI myAPI;
    SharedPreferenceManager spm;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    LikeFeedUseCase likeFeedUseCase;
    DislikeFeedUseCase dislikeFeedUseCase;
    GetCurrentUserUseCase getCurrentUserUseCase;

    private int currentEmployeeId = 0;
    private final int LIMIT = 5;

    public HomePresenterImpl(HomeContract.View view, SharedPreferenceManager spm){
        this.view = view;
        this.spm = spm;
    }

    @Override
    public void onCreate() {
        initAPI();
        likeFeedUseCase = new LikeFeedUseCase(new FeedRepositoryImpl(myAPI));
        dislikeFeedUseCase = new DislikeFeedUseCase(new FeedRepositoryImpl(myAPI));
        getCurrentUserUseCase = new GetCurrentUserUseCase(new UserRepositoryImpl(spm));
        getCurrentUserUseCase.execute("",(ICallback<CurrentUser>)(new ICallback<CurrentUser>() {
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
        }));
    }

    private void initAPI() {
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(IMyAPI.class);
    }

    @Override
    public void loadFirstPageFromServer(int currentPage) {
        view.onStartRefresh();
        compositeDisposable.clear();
        myAPI.feed(currentPage,LIMIT,spm.getSPEmployeeId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ResponseFeedPagination>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(ResponseFeedPagination responseFeed) {
                        view.onAcceptLoadFeedFirstPage(responseFeed.getFeedPagination().getFeed_list(),responseFeed.getFeedPagination().getTotal_page());
                        view.onStopRefresh();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showMessage(e.getMessage());
                        view.onStopRefresh();
                    }
                });

    }

    @Override
    public void loadNextPageFromServer(int currentPage) {
        compositeDisposable.clear();
        myAPI.feed(currentPage,LIMIT,spm.getSPEmployeeId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ResponseFeedPagination>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(ResponseFeedPagination responseFeed) {
                        view.onAcceptLoadFeedNextPage(responseFeed.getFeedPagination().getFeed_list(),responseFeed.getFeedPagination().getTotal_page());
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.onErrorLoadNextPage(e.getMessage());

                    }
                });
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
        if(isLiked == 1){
            dislikeFeedUseCase.execute(new DislikeFeed(feedId, currentEmployeeId), (ICallback<ResponseDislikeFeed>)(new ICallback<ResponseDislikeFeed>() {
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
            }));
        }
        else{
            likeFeedUseCase.execute(new LikeFeed(feedId, currentEmployeeId), (ICallback<ResponseLikeFeed>)(new ICallback<ResponseLikeFeed>() {
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
            }));
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

    }

    @Override
    public void onClickDeleteFeed(int feedId, int position) {

    }
}
