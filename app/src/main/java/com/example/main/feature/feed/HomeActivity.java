package com.example.main.feature.feed;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.featurefeed.data.source.model.remote.response.ResponseDislikeFeed;
import com.example.featurefeed.domain.model.DislikeFeed;
import com.example.featurefeed.domain.usecase.DislikeFeedUseCase;
import com.example.featurefeed.presentation.comment.FeedCommentActivity;
import com.example.featurefeed.presentation.like.FeedLikeActivity;
import com.example.main.LoginActivity;
import com.example.main.R;
import com.example.main.core.base.ICallback;
import com.example.main.core.data.retrofit.IMyAPI;
import com.example.main.core.data.retrofit.RetrofitClient;
import com.example.main.core.data.sharedPreference.SharedPreferenceManager;
import com.example.featurefeed.data.source.model.local.Feed;
import com.example.featurefeed.data.source.model.remote.response.feed.ResponseFeedPagination;
import com.example.featurefeed.presentation.adapter.FeedAdapter;
import com.example.featurefeed.domain.usecase.LikeFeedUseCase;
import com.example.main.core.domain.user.usecase.GetCurrentUserUseCase;
import com.example.main.pagination.PaginationScrollListener;
import com.example.main.core.utils.Constant;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class HomeActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, FeedAdapter.ClickListener {

    IMyAPI myAPI;
    RecyclerView recycler_feeds;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    SharedPreferenceManager spm;
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
    SwipeRefreshLayout refreshLayout;
    FloatingActionButton buttonAdd;
    FeedAdapter feedAdapter;
    LikeFeedUseCase likeFeedUseCase;
    DislikeFeedUseCase dislikeFeedUseCase;
    GetCurrentUserUseCase getCurrentUserUseCase;
    private int currentEmployeeId = 0;
    boolean isLoading = false, isLastPage = false;
    int totalPage = 0, currentPage = 1;
    private final int LIMIT = 5;

    public static Intent getIntent(Context context) {
        return new Intent(context, HomeActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initSP();
        initAdapter();
        initView();
        initListener();
        intAPI();
        checkLogIn();
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                loadFirstPage();
            }
        });
        /*
        getCurrentUserUseCase.execute("", (ICallback<CurrentUser>)(new ICallback<CurrentUser>() {
            @Override
            public void onDisposableAcquired(Disposable disposable) {

            }

            @Override
            public void onSuccess(CurrentUser result) {
                currentEmployeeId =result.getEmployeeId();
            }

            @Override
            public void onError(String error) {

            }

            @Override
            public void onInputEmpty() {

            }
        }));

         */

    }

    private void initView() {
        //View
        recycler_feeds = (RecyclerView) findViewById(R.id.recycler_feeds);
        recycler_feeds.setHasFixedSize(true);
        recycler_feeds.setLayoutManager(linearLayoutManager);
        recycler_feeds.setAdapter(feedAdapter);

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.layout_swipe);
        buttonAdd = (FloatingActionButton) findViewById(R.id.btn_add_feed);
    }

    private void initAdapter() {
        feedAdapter = new FeedAdapter(HomeActivity.this);
    }


    private void intAPI() {
        //Init API
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(IMyAPI.class);
    }

    private void initListener() {
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        refreshLayout.setOnRefreshListener(this);
        recycler_feeds.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage++;
                loadNextPage();
            }

            @Override
            public int getTotalPageCount() {
                return totalPage;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });


    }

    public void onStartRefresh() {
        refreshLayout.setRefreshing(true);
    }

    public void onStopRefresh() {
        refreshLayout.setRefreshing(false);
    }

    public void showMessage(String message) {
        Toast.makeText(this, message,Toast.LENGTH_LONG).show();
    }

    public void onAcceptLoadFeedFirstPage(List<Feed> feedList, int total_page) {
        feedAdapter.addAll(feedList);

        totalPage = total_page;

        if (currentPage < totalPage) {
            feedAdapter.addLoadingFooter();
            isLastPage = false;
        } else {
            isLastPage = true;
        }
    }

    private void onAcceptLoadFeedNextPage(List<Feed> feed_list, int total_page) {
        feedAdapter.removeLoadingFooter();
        isLoading = false;

        feedAdapter.addAll(feed_list);

        totalPage = total_page;

        if (currentPage != totalPage) {
            feedAdapter.addLoadingFooter();
            isLastPage = false;
        } else {
            isLastPage = true;
        }
    }


    private void loadFirstPage() {
        feedAdapter.resetIsLoadingAdded();
        feedAdapter.getFeedList().clear();
        feedAdapter.notifyDataSetChanged();
        currentPage = 1;

        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                loadFirstPageFromServer(currentPage);
            }
        });
    }

    private void loadFirstPageFromServer(int currentPage) {
        onStartRefresh();
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
                        onAcceptLoadFeedFirstPage(responseFeed.getFeedPagination().getFeed_list(),responseFeed.getFeedPagination().getTotal_page());
                        onStopRefresh();
                    }

                    @Override
                    public void onError(Throwable e) {
                        showMessage(e.getMessage());
                        onStopRefresh();
                    }
                });
    }


    private void loadNextPage() {
        loadNextPageFromServer(currentPage);
    }

    private void loadNextPageFromServer(int currentPage) {
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
                        onAcceptLoadFeedNextPage(responseFeed.getFeedPagination().getFeed_list(),responseFeed.getFeedPagination().getTotal_page());
                    }

                    @Override
                    public void onError(Throwable e) {
                        onErrorLoadNextPage(e.getMessage());

                    }
                });
    }

    private void onErrorLoadNextPage(String message) {
        feedAdapter.showRetry(true, message);
    }


    private void checkLogIn() {
        if (!spm.isLoggedIn()) {
            logout();
        }
    }

    @SuppressLint("CommitPrefEdits")
    private void initSP() {
        SharedPreferences sp = getSharedPreferences(Constant.SP_APP, Context.MODE_PRIVATE);
        spm = new SharedPreferenceManager(sp, sp.edit());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    private void logout() {
        spm.saveSPString(Constant.SP_EMAIL, "");
        spm.saveSPInt(Constant.SP_EMPLOYEE_ID, 0);
        spm.saveSPBoolean(Constant.SP_IS_LOGGED_IN, false);
        Intent loginIntent = LoginActivity.getIntent(this);
        loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.btn_logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        loadFirstPage();
    }

    @Override
    public void onClickTotalLike(int feedId) {
        startActivity(FeedLikeActivity.getIntent(HomeActivity.this, feedId));
    }

    @Override
    public void onClickTotalComment(int feedId, int position) {
        startActivity(FeedCommentActivity.getIntent(HomeActivity.this,feedId,position));

    }

    @Override
    public void onClickBtnLike(int feedId, int isLiked, int position) {
        onStartRefresh();
        if(isLiked == 1){
            dislikeFeedUseCase.execute(new DislikeFeed(feedId, currentEmployeeId), (ICallback<ResponseDislikeFeed>)(new ICallback<ResponseDislikeFeed>() {
                @Override
                public void onDisposableAcquired(Disposable disposable) {
                    compositeDisposable.add(disposable);
                }

                @Override
                public void onSuccess(ResponseDislikeFeed result) {

                }

                @Override
                public void onError(String error) {

                }

                @Override
                public void onInputEmpty() {

                }
            }));
        }
    }

    @Override
    public void onClickBtnComment(int feedId, int position) {
        startActivity(FeedCommentActivity.getIntent(HomeActivity.this,feedId,position));
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

    @Override
    public void retryPageLoad() {
        loadNextPageFromServer(currentPage);
    }
}
