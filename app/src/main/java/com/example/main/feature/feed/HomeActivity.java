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

import com.example.featurefeed.data.source.model.local.Feed;
import com.example.featurefeed.presentation.adapter.FeedAdapter;
import com.example.featurefeed.presentation.comment.FeedCommentActivity;
import com.example.featurefeed.presentation.like.FeedLikeActivity;
import com.example.main.LoginActivity;
import com.example.main.R;
import com.example.main.core.data.sharedPreference.SharedPreferenceManager;
import com.example.main.core.utils.Constant;
import com.example.main.pagination.PaginationScrollListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class HomeActivity extends AppCompatActivity
        implements SwipeRefreshLayout.OnRefreshListener, FeedAdapter.ClickListener, HomeContract.View {

    RecyclerView recycler_feeds;
    SharedPreferenceManager spm;
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
    SwipeRefreshLayout refreshLayout;
    FloatingActionButton buttonAdd;
    FeedAdapter feedAdapter;
    HomePresenterImpl homePresenter;
    boolean isLoading = false, isLastPage = false;
    int totalPage = 0, currentPage = 1;

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
        checkLogIn();
        homePresenter = new HomePresenterImpl(this,spm);
        homePresenter.onCreate();
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                loadFirstPage();
            }
        });

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

    @SuppressLint("CommitPrefEdits")
    private void initSP() {
        SharedPreferences sp = getSharedPreferences(Constant.SP_APP, Context.MODE_PRIVATE);
        spm = new SharedPreferenceManager(sp, sp.edit());
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

    @Override
    public void onStartRefresh() {
        refreshLayout.setRefreshing(true);
    }

    @Override
    public void onStopRefresh() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void navigateToLikeList(int feedId) {
        startActivity(FeedLikeActivity.getIntent(HomeActivity.this, feedId));

    }

    @Override
    public void navigateToCommentList(int feedId, int position) {
        startActivity(FeedCommentActivity.getIntent(HomeActivity.this,feedId,position));
    }

    @Override
    public void setLikeFeed(int position) {
        feedAdapter.setLikeFeed(position);
    }

    @Override
    public void setDislikeFeed(int position) {
        feedAdapter.setDisLikeFeed(position);
    }

    @Override
    public void setTotalComment(int position, int totalComment) {
        feedAdapter.setTotalComment(position, totalComment);
    }

    @Override
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

    @Override
    public void onAcceptLoadFeedNextPage(List<Feed> feed_list, int total_page) {
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
                homePresenter.loadFirstPageFromServer(currentPage);
            }
        });
    }

    private void loadNextPage() {
        homePresenter.loadNextPageFromServer(currentPage);
    }

    @Override
    public void onErrorLoadNextPage(String message) {
        feedAdapter.showRetry(true, message);
    }


    private void checkLogIn() {
        if (!spm.isLoggedIn()) {
            logout();
        }
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
        homePresenter.onClickTotalLike(feedId);
    }

    @Override
    public void onClickTotalComment(int feedId, int position) {
        homePresenter.onClickTotalComment(feedId, position);
    }

    @Override
    public void onClickBtnLike(int feedId, int isLiked, int position) {
        homePresenter.onClickBtnLike(feedId, isLiked, position);
    }

    @Override
    public void onClickBtnComment(int feedId, int position) {
        homePresenter.onClickBtnComment(feedId, position);
    }

    @Override
    public void onClickEmp(int empId) {
        homePresenter.onClickEmp(empId);
    }

    @Override
    public void onClickImagePost(String imageName) {
        homePresenter.onClickImagePost(imageName);
    }

    @Override
    public void onClickPost(Feed feed) {
        homePresenter.onClickPost(feed);
    }

    @Override
    public void onClickEditFeed(int feedId, String feedPost, String feedImage, int position) {
        homePresenter.onClickEditFeed(feedId, feedPost, feedImage, position);
    }

    @Override
    public void onClickDeleteFeed(int feedId, int position) {
        homePresenter.onClickDeleteFeed(feedId, position);
    }

    @Override
    public void retryPageLoad() {
        homePresenter.loadNextPageFromServer(currentPage);
    }
}
