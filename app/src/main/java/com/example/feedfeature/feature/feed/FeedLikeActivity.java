package com.example.feedfeature.feature.feed;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.feedfeature.R;
import com.example.feedfeature.data.retrofit.IMyAPI;
import com.example.feedfeature.data.retrofit.RetrofitClient;
import com.example.feedfeature.data.source.local.FeedLike;
import com.example.feedfeature.data.source.remote.response.ResponseFeedLikes;
import com.example.feedfeature.feature.feed.adapter.FeedLikesAdapter;
import com.example.feedfeature.pagination.PaginationScrollListener;

import java.util.List;
import java.util.Objects;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class FeedLikeActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, FeedLikesAdapter.ClickListener {

    private final int PAGE_START = 1;
    private static final String FEED_ID = "feedId";

    private int feedID;

    public static Intent getIntent(Context context, int feedId){
        return new Intent(context, FeedLikeActivity.class)
                    .putExtra(FEED_ID, feedId);
    }

    private LinearLayoutManager linearLayoutManager;

    RecyclerView recycler_feed_like;
    IMyAPI myAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    SwipeRefreshLayout swipe_to_refresh;
    FeedLikesAdapter feedLikesAdapter;
    LinearLayout layout_no_like;
    TextView btn_back;
    private final int LIMIT = 5;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int currentPage = PAGE_START;
    private int totalPage = 0;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_likes);
        feedID = getIntent().getIntExtra(FEED_ID, 0);
        initAdapter();
        initView();
        initListener();
        initAPI();
        swipe_to_refresh.post(new Runnable() {
            @Override
            public void run() {
                loadFirstPage();
            }
        });
    }

    private void initView() {

        linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recycler_feed_like = (RecyclerView)findViewById(R.id.recycler_feed_likes);
        recycler_feed_like.setLayoutManager(linearLayoutManager);
        recycler_feed_like.setHasFixedSize(true);
        recycler_feed_like.setAdapter(feedLikesAdapter);

        swipe_to_refresh = (SwipeRefreshLayout)findViewById(R.id.swipe_to_refresh);

        btn_back = (TextView)findViewById(R.id.btn_back);

        layout_no_like = (LinearLayout)findViewById(R.id.layout_no_like);
    }

    private void initAdapter() {
        feedLikesAdapter = new FeedLikesAdapter(FeedLikeActivity.this);
    }


    private void initAPI() {
        //Init API
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(IMyAPI.class);
    }

    private void initListener() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        swipe_to_refresh.setOnRefreshListener(this);
        recycler_feed_like.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
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

    public void retryPageLoad() {
        loadNextPage();
    }

    public void onStartLoad() {
        swipe_to_refresh.setRefreshing(true);
    }

    public void onStopLoad() {
        swipe_to_refresh.setRefreshing(false);
    }

    public void onAcceptLoadFeedLikeFirstPage(List<FeedLike> feedLikeList, int total_page) {
        if (feedLikeList.isEmpty()) {
            layout_no_like.setVisibility(View.VISIBLE);
        } else {
            layout_no_like.setVisibility(View.GONE);
        }

        feedLikesAdapter.addAll(feedLikeList);

        totalPage = total_page;

        if (currentPage < totalPage) {
            feedLikesAdapter.addLoadingFooter();
            isLastPage = false;
        } else {
            isLastPage = true;
        }
    }

    public void onAcceptLoadFeedLikeNextPage(List<FeedLike> feedLikeList, int total_page) {
        feedLikesAdapter.removeLoadingFooter();
        isLoading = false;

        feedLikesAdapter.addAll(feedLikeList);

        totalPage = total_page;

        if (currentPage != totalPage) {
            feedLikesAdapter.addLoadingFooter();
            isLastPage = false;
        } else {
            isLastPage = true;
        }
    }

    public void onErrorLoadNextPage(String message) {
        feedLikesAdapter.showRetry(true, message);
    }

    private void loadFirstPage() {
        feedLikesAdapter.resetIsLoadingAdded();
        feedLikesAdapter.getNotificationList().clear();
        feedLikesAdapter.notifyDataSetChanged();
        currentPage = PAGE_START;

        swipe_to_refresh.post(new Runnable() {
            @Override
            public void run() {
                loadFirstPageFromServer(currentPage);
            }
        });
    }

    private void loadFirstPageFromServer(int currentPage) {
        onStartLoad();
        compositeDisposable.clear();
        myAPI.feed_likes(feedID,currentPage,LIMIT)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ResponseFeedLikes>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(ResponseFeedLikes responseFeedLikes) {
                        onAcceptLoadFeedLikeFirstPage(responseFeedLikes.getFeedLikePagination().getFeed_like_list(),responseFeedLikes.getFeedLikePagination().getTotal_page());
                        onStopLoad();
                    }

                    @Override
                    public void onError(Throwable e) {
                        showMessage(e.getMessage());
                        onStopLoad();
                    }
                });
    }




    public void showMessage(String message) {
        Toast.makeText(this, message,Toast.LENGTH_LONG).show();
    }

    private void loadNextPage() {
        loadNextPageFromServer(currentPage);
    }

    private void loadNextPageFromServer(int currentPage) {
        compositeDisposable.clear();
        myAPI.feed_likes(feedID,currentPage,LIMIT)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new SingleObserver<ResponseFeedLikes>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onSuccess(ResponseFeedLikes responseFeedLikes) {
                onAcceptLoadFeedLikeNextPage(responseFeedLikes.getFeedLikePagination().getFeed_like_list(),responseFeedLikes.getFeedLikePagination().getTotal_page());
            }

            @Override
            public void onError(Throwable e) {
                onErrorLoadNextPage(e.getMessage());
            }
        });
    }


    @Override
    public void onRefresh() {
        loadFirstPage();
    }


}
