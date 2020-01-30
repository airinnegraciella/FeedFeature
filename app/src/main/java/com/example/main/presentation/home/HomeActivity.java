package com.example.main.presentation.home;

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
import com.example.featurefeed.data.source.repository.FeedRepositoryImpl;
import com.example.featurefeed.domain.usecase.DeleteFeedUseCase;
import com.example.featurefeed.domain.usecase.DislikeFeedUseCase;
import com.example.featurefeed.domain.usecase.GetFeedPaginationUseCase;
import com.example.featurefeed.domain.usecase.LikeFeedUseCase;
import com.example.featurefeed.presentation.adapter.FeedAdapter;
import com.example.featurefeed.presentation.comment.FeedCommentActivity;
import com.example.featurefeed.presentation.create_feed.FeedCreateActivity;
import com.example.featurefeed.presentation.like.FeedLikeActivity;
import com.example.main.presentation.login.LoginActivity;
import com.example.main.R;
import com.example.main.core.data.retrofit.IMyAPI;
import com.example.main.core.data.retrofit.RetrofitClient;
import com.example.main.core.data.sharedPreference.SharedPreferenceManager;
import com.example.main.core.data.source.user.repository.UserRepositoryImpl;
import com.example.main.core.domain.user.usecase.GetCurrentUserUseCase;
import com.example.main.core.utils.Constant;
import com.example.main.pagination.PaginationScrollListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import retrofit2.Retrofit;

public class HomeActivity extends DaggerAppCompatActivity
        implements SwipeRefreshLayout.OnRefreshListener, FeedAdapter.ClickListener, HomeContract.View {
    
    @Inject
    FeedAdapter feedAdapter;
    @Inject
    HomePresenterImpl homePresenter;
    
    RecyclerView recycler_feeds;
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
    SwipeRefreshLayout refreshLayout;
    FloatingActionButton buttonAdd;
    boolean isLoading = false, isLastPage = false;
    int totalPage = 0, currentPage = 1;
    
    public static Intent getIntent(Context context) {
        return new Intent(context, HomeActivity.class);
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Objects.requireNonNull(getSupportActionBar()).setElevation(0f);
        initView();
        initListener();
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
        recycler_feeds = findViewById(R.id.recycler_feeds);
        recycler_feeds.setHasFixedSize(true);
        recycler_feeds.setLayoutManager(linearLayoutManager);
        recycler_feeds.setAdapter(feedAdapter);
        
        refreshLayout = findViewById(R.id.layout_swipe);
        buttonAdd = findViewById(R.id.btn_add_feed);
    }
    
    private void initListener() {
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToAddFeed();
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
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
    
    @Override
    public void navigateToAddFeed() {
        startActivity(FeedCreateActivity.getIntent(this, 0, false, "", "", 0));
    }
    
    @Override
    public void navigateToEditFeed(int feedId, String feedPost, String feedImage, int position) {
        startActivity(FeedCreateActivity.getIntent(this, feedId, true, feedPost, feedImage, position));
    }
    
    @Override
    public void navigateToLikeList(int feedId) {
        startActivity(FeedLikeActivity.getIntent(HomeActivity.this, feedId));
        
    }
    
    @Override
    public void navigateToCommentList(int feedId, int position) {
        startActivity(FeedCommentActivity.getIntent(HomeActivity.this, feedId, position));
    }
    
    @Override
    public void navigateToLoginPage() {
        startActivity(LoginActivity.getIntent(this).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        
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
    public void onDeleteFeedSuccess(int position) {
        feedAdapter.remove(position);
    }
    
    @Override
    public void editFeed(int position, String newFeedPost, String newFeedImage) {
        feedAdapter.editFeed(position, newFeedPost, newFeedImage);
    }
    
    @Override
    public void addFeed(Feed newFeed) {
        feedAdapter.add(newFeed);
        recycler_feeds.scrollToPosition(0);
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
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }
    
    private void logout() {
        homePresenter.onClickLogout();
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
