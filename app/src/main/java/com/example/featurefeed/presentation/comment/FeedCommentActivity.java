package com.example.featurefeed.presentation.comment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.featurefeed.data.source.model.local.FeedComment;
import com.example.featurefeed.data.source.repository.FeedRepositoryImpl;
import com.example.featurefeed.domain.model.EditFeedComment;
import com.example.featurefeed.domain.usecase.CreateFeedCommentUseCase;
import com.example.featurefeed.domain.usecase.GetFeedCommentPaginationUseCase;
import com.example.featurefeed.presentation.adapter.FeedCommentsAdapter;
import com.example.featurefeed.presentation.edit_comment.EditFeedCommentActivity;
import com.example.main.R;
import com.example.main.core.data.retrofit.IMyAPI;
import com.example.main.core.data.retrofit.RetrofitClient;
import com.example.main.core.data.sharedPreference.SharedPreferenceManager;
import com.example.main.core.domain.user.repository.UserRepositoryImpl;
import com.example.main.core.domain.user.usecase.GetCurrentUserUseCase;
import com.example.main.core.utils.Constant;
import com.example.main.pagination.PaginationScrollListener;

import java.util.List;
import java.util.Objects;

import retrofit2.Retrofit;

import static com.example.main.R.*;
import static com.example.main.R.layout.*;
import static com.example.main.R.layout.comments_layout;

public class FeedCommentActivity extends AppCompatActivity
        implements SwipeRefreshLayout.OnRefreshListener, FeedCommentsAdapter.ClickListener, FeedCommentContract.View {

    private final int PAGE_START = 1;
    private static final String FEED_ID = "feedId";
    private static final String POSITION_FEED = "positionFeed";
    private ViewGroupOverlay overlay;

    public void applyDim(ViewGroup parent, float dimAmount) {
        ColorDrawable dim = new ColorDrawable(Color.BLACK);
        dim.setBounds(0, 0, parent.getWidth(), parent.getHeight());
        dim.setAlpha((int) (255 * dimAmount));

        overlay = parent.getOverlay();
        overlay.add(dim);
    }

    public void clearDim(ViewGroup parent) {
        overlay = parent.getOverlay();
        overlay.clear();
    }

    public static Intent getIntent(Context context, int feedId, int positionFeed) {
        return new Intent(context, FeedCommentActivity.class)
                .putExtra(FEED_ID, feedId)
                .putExtra(POSITION_FEED, positionFeed);
    }

    private LinearLayoutManager linearLayoutManager;
    private SharedPreferenceManager spm;

    RecyclerView recycler_feed_comment;
    IMyAPI myAPI;
    SwipeRefreshLayout swipe_to_refresh;
    FeedCommentsAdapter feedCommentsAdapter;
    LinearLayout layout_no_comment, layout_feed_comment;
    ImageView btn_comment;
    EditText edt_comment;
    FeedCommentPresenterImpl feedCommentPresenter;

    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int currentPage = PAGE_START;
    private int totalPage = 0;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_comments);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        initAPI();
        initSP();
        initAdapter();
        initView();
        initListener();
        feedCommentPresenter = new FeedCommentPresenterImpl(this,
                new GetFeedCommentPaginationUseCase(new FeedRepositoryImpl(myAPI)),
                new GetCurrentUserUseCase(new UserRepositoryImpl(spm)),
                new CreateFeedCommentUseCase(new FeedRepositoryImpl(myAPI)));
        feedCommentPresenter.onCreate(getIntent().getIntExtra(FEED_ID, 0), getIntent().getIntExtra(POSITION_FEED, 0));
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
        recycler_feed_comment = (RecyclerView) findViewById(id.recycler_feed_comments);
        recycler_feed_comment.setLayoutManager(linearLayoutManager);
        recycler_feed_comment.setHasFixedSize(true);
        recycler_feed_comment.setAdapter(feedCommentsAdapter);

        swipe_to_refresh = (SwipeRefreshLayout) findViewById(id.swipe_to_refresh_comments);
        swipe_to_refresh.setOnRefreshListener(this);

        edt_comment = (EditText) findViewById(R.id.edt_comment);
        btn_comment = (ImageView) findViewById(R.id.btn_add_comment);

        layout_no_comment = (LinearLayout) findViewById(R.id.layout_no_comment);
        layout_feed_comment = findViewById(R.id.layout_feed_comment);

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
        swipe_to_refresh.setOnRefreshListener(this);
        recycler_feed_comment.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
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

        edt_comment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty()) {
                    btn_comment.setVisibility(View.VISIBLE);
                } else {
                    btn_comment.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btn_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedCommentPresenter.onBtnCommentClick(edt_comment.getText().toString().trim());
            }
        });



    }

    public int getLayout() {
        return activity_comments;
    }

    public boolean onSupportNavigateUp() {
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
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }


    @SuppressLint("ResourceType")
    public void onClickLayoutComment(final int feedCommentId, final String feedComment, final String feedCommentImage, final int position) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View customView = inflater.inflate(popup_menu_feed_comment, null);

        final PopupWindow mPopupWindow = new PopupWindow(
                customView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        LinearLayout btnEdit = customView.findViewById(id.btn_edit);
        LinearLayout btnDelete = customView.findViewById(id.btn_delete);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
                feedCommentPresenter.onClickEditComment(
                        feedCommentId,
                        feedComment,
                        feedCommentImage,
                        position
                );
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
                feedCommentPresenter.onClickDeleteComment(
                        feedCommentId,
                        position
                );
            }
        });


        final ViewGroup root = (ViewGroup) getWindow().getDecorView().getRootView();
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                clearDim(root);
            }
        });

        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(true);

        applyDim(root, 0.5f);
        mPopupWindow.showAtLocation(layout_feed_comment, Gravity.CENTER, 0, 0);
    }


    @Override
    public void onDeleteCommentSuccess(int position) {
        feedCommentsAdapter.remove(position);
    }

    @Override
    public void editComment(int position, String newFeedComment, String newFeedCommentImage) {
        feedCommentsAdapter.editComment(position, newFeedComment, newFeedCommentImage);
    }

    @Override
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

    @Override
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

    @Override
    public void onErrorLoadNextPage(String message) {
        feedCommentsAdapter.showRetry(true, message);
    }

    @Override
    public void onErrorLoad(String errorMessage) {
        swipe_to_refresh.setRefreshing(false);
        showMessage(errorMessage);
    }

    @Override
    public void navigateToEditFeed(int feedCommentId, String feedComment, int position) {
        startActivity(EditFeedCommentActivity.getIntent(this, feedCommentId, feedComment, position));

    }

    @Override
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
                feedCommentPresenter.loadFirstPageFromServer(currentPage);
            }
        });
    }


    private void loadNextPage() {
        feedCommentPresenter.loadNextPageFromServer(currentPage);
    }


    @Override
    public void onCommentSuccess(FeedComment feedComment) {
        edt_comment.setText("");
        layout_no_comment.setVisibility(View.GONE);
        feedCommentsAdapter.addAtFirst(feedComment);
        recycler_feed_comment.scrollToPosition(0);
    }

    @Override
    public void onCommentError(String error) {
        swipe_to_refresh.setRefreshing(false);
        showMessage(error);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        feedCommentPresenter.onActivityResult(requestCode, resultCode, data);
    }
}
