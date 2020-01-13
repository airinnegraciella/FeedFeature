package com.example.feedfeature.feature.feed;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import android.text.TextWatcher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.feedfeature.R;
import com.example.feedfeature.data.retrofit.IMyAPI;
import com.example.feedfeature.data.retrofit.RetrofitClient;
import com.example.feedfeature.data.sharedPreference.SharedPreferenceManager;
import com.example.feedfeature.data.source.remote.response.ResponseFeedComments;
import com.example.feedfeature.data.source.remote.response.comment.FeedComment;
import com.example.feedfeature.feature.feed.adapter.FeedCommentsAdapter;
import com.example.feedfeature.pagination.PaginationScrollListener;
import com.example.feedfeature.utils.Constant;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class FeedCommentActivity  extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, FeedCommentsAdapter.ClickListener {
    
    private final int PAGE_START = 1;
    private static final String FEED_ID = "feedId";
    private static final String POSITION_FEED = "positionFeed";
    private ViewGroupOverlay overlay;

    private int feedID;
    private int positionFeed;

    public void applyDim(ViewGroup parent, float dimAmount) {
        ColorDrawable dim = new ColorDrawable(Color.BLACK);
        dim.setBounds(0, 0, parent.getWidth(), parent.getHeight());
        dim.setAlpha((int)(255 * dimAmount));

        overlay = parent.getOverlay();
        overlay.add(dim);
    }

    public void clearDim(ViewGroup parent) {
        overlay = parent.getOverlay();
        overlay.clear();
    }

    public static Intent getIntent(Context context, int feedId, int positionFeed){
        return new Intent(context, FeedCommentActivity.class)
                    .putExtra(FEED_ID, feedId)
                .putExtra(POSITION_FEED, positionFeed);
    }

    private LinearLayoutManager linearLayoutManager;

    RecyclerView recycler_feed_comment;
    IMyAPI myAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    SharedPreferenceManager spm;
    SwipeRefreshLayout swipe_to_refresh;
    FeedCommentsAdapter feedCommentsAdapter;
    LinearLayout layout_no_comment;
    TextView btn_back;
    ImageView btn_comment;
    EditText edt_comment;
    private final int LIMIT = 5;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int currentPage = PAGE_START;
    private int totalPage = 0;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        feedID = getIntent().getIntExtra(FEED_ID, 0);
        positionFeed = getIntent().getIntExtra(POSITION_FEED, 0);
        initSP();
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

    @SuppressLint("CommitPrefEdits")
    private void initSP() {
        SharedPreferences sp = getSharedPreferences(Constant.SP_APP, Context.MODE_PRIVATE);
        spm = new SharedPreferenceManager(sp, sp.edit());
    }

    private void initView() {

        linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recycler_feed_comment = (RecyclerView)findViewById(R.id.recycler_feed_comments);
        recycler_feed_comment.setLayoutManager(linearLayoutManager);
        recycler_feed_comment.setHasFixedSize(true);
        recycler_feed_comment.setAdapter(feedCommentsAdapter);

        swipe_to_refresh = (SwipeRefreshLayout)findViewById(R.id.swipe_to_refresh_comments);
        swipe_to_refresh.setOnRefreshListener(this);

        btn_back = (TextView)findViewById(R.id.btn_back);
        edt_comment = (EditText)findViewById(R.id.edt_comment);
        btn_comment = (ImageView)findViewById(R.id.btn_comment);

        layout_no_comment = (LinearLayout)findViewById(R.id.layout_no_comment);

    }

    private void initAdapter() {
        feedCommentsAdapter = new FeedCommentsAdapter(FeedCommentActivity.this);
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
        recycler_feed_comment.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage ++;

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

        edt_comment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().isEmpty()) {
                    btn_comment.setVisibility(View.VISIBLE);
                } else {
                    btn_comment.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
/*
        btn_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedCommentPresenter.onBtnCommentClick(edt_comment.text.toString().trim());
            }
        });

 */

    }

    public int getLayout(){
        return R.layout.activity_comments;
    }

    public boolean onSupportNavigateUp(){
        Intent returnIntent = new Intent();
        returnIntent.putExtra("positionKey", getIntent().getIntExtra(POSITION_FEED, 0));
        returnIntent.putExtra("feedId", getIntent().getIntExtra(FEED_ID, 0));
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
        return true;
    }

    public void onBackPressed() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("positionKey", getIntent().getIntExtra(POSITION_FEED, 0));
        returnIntent.putExtra("feedId", getIntent().getIntExtra(FEED_ID, 0));
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    public void showMessage(String message) {
        Toast.makeText(this, message,Toast.LENGTH_LONG).show();
    }

/*
    public void onClickLayoutComment(int feedCommentId, String feedComment, String feedCommentImage, int position) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View customView = inflater.inflate(R.layout.popup_menu_feed_comment, null);

        PopupWindow mPopupWindow = new PopupWindow(
                customView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        /*
        LinearLayout btnEdit = customView.findViewById(R.id.btn_edit);
        LinearLayout btnDelete = customView.findViewById(R.id.btn_delete);

        btnEdit.setOnClickListener {
            mPopupWindow.dismiss()
            feedCommentPresenter.onClickEditComment(
                    feedCommentId,
                    feedComment!!,
                    feedCommentImage!!,
                    position
            )
        }

        btnDelete.setOnClickListener {
            mPopupWindow.dismiss()
            feedCommentPresenter.onClickDeleteComment(
                    feedCommentId,
                    position
            )
        }


        ViewGroup root = window.decorView.rootView;

//        mPopupWindow.setOnDismissListener();
        clearDim(root);
//        mPopupWindow.setOnDismissListener { clearDim(root) }

        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(true);

        applyDim(root, 0.5f);
        mPopupWindow.showAtLocation(layout_feed_comments, Gravity.CENTER, 0, 0);
    }

 */


    public void onDeleteCommentSuccess(int position) {
        feedCommentsAdapter.remove(position);
    }

    public void editComment(int position, String newFeedComment, String newFeedCommentImage) {
        feedCommentsAdapter.editComment(position, newFeedComment, newFeedCommentImage);
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

    public void onAcceptLoadFeedCommentFirstPage(List<FeedComment> feedCommentList, int total_page) {
        if (feedCommentList.isEmpty()) {
            layout_no_comment.setVisibility(View.VISIBLE);
        } else {
            layout_no_comment.setVisibility(View.GONE);
        }

        feedCommentsAdapter.addAll(feedCommentList);

        totalPage = total_page;

        if (currentPage < totalPage) {
            feedCommentsAdapter.addLoadingFooter();
            isLastPage = false;
        } else {
            isLastPage = true;
        }
    }

    public void onAcceptLoadFeedCommentNextPage(List<FeedComment> feedCommentList, int total_page) {
        feedCommentsAdapter.removeLoadingFooter();
        isLoading = false;

        feedCommentsAdapter.addAll(feedCommentList);

        totalPage = total_page;

        if (currentPage != totalPage) {
            feedCommentsAdapter.addLoadingFooter();
            isLastPage = false;
        } else {
            isLastPage = true;
        }
    }

    public void onErrorLoadNextPage(String message) {
        feedCommentsAdapter.showRetry(true, message);
    }

    public void onErrorLoad(String errorMessage) {
        swipe_to_refresh.setRefreshing(false);
        showMessage(errorMessage);
    }

    public void onRefresh() {
        loadFirstPage();
    }

    private void loadFirstPage() {
        feedCommentsAdapter.resetIsLoadingAdded();
        feedCommentsAdapter.getNotificationList().clear();
        feedCommentsAdapter.notifyDataSetChanged();
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
        myAPI.feed_comments(spm.getSPEmployeeId(),feedID,currentPage,LIMIT)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ResponseFeedComments>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(ResponseFeedComments responseFeedComments) {
                        onAcceptLoadFeedCommentFirstPage(responseFeedComments.getFeedCommentPagination().getFeed_comment_list(),responseFeedComments.getFeedCommentPagination().getTotal_page());
                        onStopLoad();

                    }

                    @Override
                    public void onError(Throwable e) {
                        onErrorLoad(e.getMessage());
                        onStopLoad();
                    }
                });
    }


    private void loadNextPage() {
        loadNextPageFromServer(currentPage);
    }

    private void loadNextPageFromServer(int currentPage) {
        compositeDisposable.clear();
        myAPI.feed_comments(spm.getSPEmployeeId(),feedID,currentPage,LIMIT)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ResponseFeedComments>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(ResponseFeedComments responseFeedComments) {
                        onAcceptLoadFeedCommentNextPage(responseFeedComments.getFeedCommentPagination().getFeed_comment_list(),responseFeedComments.getFeedCommentPagination().getTotal_page());
                    }

                    @Override
                    public void onError(Throwable e) {
                        onErrorLoadNextPage(e.getMessage());
                    }
                });

    }

    private void onCommentSuccess(FeedComment feedComment) {
        edt_comment.setText("");
        layout_no_comment.setVisibility(View.GONE);
        feedCommentsAdapter.addAtFirst(feedComment);
        recycler_feed_comment.scrollToPosition(0);
    }

    public void onCommentError(String error) {
        swipe_to_refresh.setRefreshing(false);
        showMessage(error);
    }

    /*
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        feedCommentPresenter.onActivityResult(requestCode, resultCode, data);
    }

     */
}
