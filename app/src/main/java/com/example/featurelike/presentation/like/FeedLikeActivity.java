package com.example.featurelike.presentation.like;

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
import com.example.feedfeature.core.data.retrofit.IMyAPI;
import com.example.feedfeature.core.data.retrofit.RetrofitClient;
import com.example.featurelike.data.source.model.local.FeedLike;
import com.example.featurelike.data.source.model.remote.response.like.ResponseFeedLikesPagination;
import com.example.featurelike.presentation.adapter.FeedLikesAdapter;
import com.example.feedfeature.pagination.PaginationScrollListener;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class FeedLikeActivity extends AppCompatActivity
        implements SwipeRefreshLayout.OnRefreshListener, FeedLikesAdapter.ClickListener, FeedLikeContract.View {

    private final int PAGE_START = 1;
    private static final String FEED_ID = "feedId";


    public static Intent getIntent(Context context, int feedId){
        return new Intent(context, FeedLikeActivity.class)
                    .putExtra(FEED_ID, feedId);
    }

    private LinearLayoutManager linearLayoutManager;

    RecyclerView recycler_feed_like;
    SwipeRefreshLayout swipe_to_refresh;
    FeedLikesAdapter feedLikesAdapter;
    LinearLayout layout_no_like;
    TextView btn_back;
    FeedLikePresenterImpl feedLikePresenter;

    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int currentPage = PAGE_START;
    private int totalPage = 0;




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_likes);
        feedLikePresenter = new FeedLikePresenterImpl(this);
        feedLikePresenter.onCreate(getIntent().getIntExtra(FEED_ID, 0));
        initAdapter();
        initView();
        initListener();
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

        swipe_to_refresh = (SwipeRefreshLayout)findViewById(R.id.swipe_to_refresh_likes);

        btn_back = (TextView)findViewById(R.id.btn_back);

        layout_no_like = (LinearLayout)findViewById(R.id.layout_no_like);
    }

    private void initAdapter() {
        feedLikesAdapter = new FeedLikesAdapter(FeedLikeActivity.this);
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


    @Override
    public void onStartLoad() {
        swipe_to_refresh.setRefreshing(true);
    }

    @Override
    public void onStopLoad() {
        swipe_to_refresh.setRefreshing(false);
    }

    @Override
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

    @Override
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

    @Override
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
                feedLikePresenter.loadFirstPageFromServer(currentPage);
            }
        });
    }




    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message,Toast.LENGTH_LONG).show();
    }

    private void loadNextPage() {
        feedLikePresenter.loadNextPageFromServer(currentPage);
    }


    @Override
    public void onRefresh() {
        loadFirstPage();
    }


}
