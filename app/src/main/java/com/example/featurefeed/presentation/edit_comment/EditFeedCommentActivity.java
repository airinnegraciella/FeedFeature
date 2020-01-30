package com.example.featurefeed.presentation.edit_comment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.main.R;

import java.util.Objects;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class EditFeedCommentActivity extends DaggerAppCompatActivity implements EditFeedCommentContract.View {

    private static final String FEED_COMMENT_ID = "feedCommentId";
    private static final String FEED_COMMENT = "feedComment";
    private static final String POSITION_FEED_COMMENT = "positionFeedComment";

    public static Intent getIntent(Context context, int feedCommentId,
                                   String feedComment, int positionFeedComment) {
        return new Intent(context, EditFeedCommentActivity.class)
                .putExtra(FEED_COMMENT_ID, feedCommentId)
                .putExtra(FEED_COMMENT, feedComment)
                .putExtra(POSITION_FEED_COMMENT, positionFeedComment);
    }

    ProgressDialog waitingDialog;
    @Inject
    EditFeedCommentPresenterImpl editFeedCommentPresenter;

    EditText edt_comment;
    Button btn_update;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_feed_comment);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        initView();
        iniListener();
        editFeedCommentPresenter.onCreate(
                getIntent().getIntExtra(FEED_COMMENT_ID, 0),
                getIntent().getStringExtra(FEED_COMMENT)
        );
    }

    private void initView() {
        edt_comment = findViewById(R.id.edt_comment);
        btn_update = findViewById(R.id.btn_update);
    }

    private void iniListener() {
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editFeedCommentPresenter.onButtonUpdateClick(edt_comment.getText().toString().trim());
            }
        });

        edt_comment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!(s.toString().isEmpty())) {
                    btn_update.setVisibility(View.VISIBLE);
                    btn_update.setEnabled(true);
                } else {
                    btn_update.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void setFeedComment(String oldComment) {
        edt_comment.setText(oldComment);

    }

    @Override
    public void onStartLoading() {
        waitingDialog = new ProgressDialog(this);
        waitingDialog.setCancelable(false);
        waitingDialog.setMessage("Waiting...");
        waitingDialog.show();
    }

    @Override
    public void onStopLoading() {
        if (waitingDialog.isShowing()) {
            waitingDialog.dismiss();
        }
    }

    @Override
    public void failMessage(String message) {
        onStopLoading();
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private Intent getReturnIntent(int positionKey, int feedCommentId, String newComment) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("positionKey", positionKey);
        returnIntent.putExtra("feedCommentId", feedCommentId);
        returnIntent.putExtra("newComment", newComment);
        return returnIntent;
    }

    @Override
    public void onSuccessEditComment(String newComment) {
        if (waitingDialog.isShowing()) {
            waitingDialog.dismiss();
        }
        Toast.makeText(this, "Comment successfully edited.", Toast.LENGTH_LONG).show();
        setResult(Activity.RESULT_OK, getReturnIntent(
                getIntent().getIntExtra(POSITION_FEED_COMMENT, 0),
                getIntent().getIntExtra(FEED_COMMENT_ID, 0),
                newComment
        ));

        finish();
    }
}
