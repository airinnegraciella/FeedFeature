package com.example.featurefeed.presentation.create_feed;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.main.R;
import com.example.main.core.utils.TakePhoto;

import java.util.ArrayList;

public class FeedCreateActivity extends AppCompatActivity
        implements FeedCreateContract.View {

    private final static String FEED_ID = "feedId";
    private final static String IS_CREATED = "isCreated";
    private final static String FEED_POST = "feedPost";
    private final static String FEED_IMAGE = "feedImage";
    private final static String POSITION_FEED = "positionFeed";

    public static Intent getIntent(Context context, int feedId, boolean isCreated,
                                   String feedPost, String feedImage,
                                   int positionFeed) {
        return new Intent(context, FeedCreateActivity.class)
                .putExtra(FEED_ID, feedId)
                .putExtra(IS_CREATED, isCreated)
                .putExtra(FEED_POST, feedPost)
                .putExtra(FEED_IMAGE, feedImage)
                .putExtra(POSITION_FEED, positionFeed);
    }

    TakePhoto takePhoto;
    AlertDialog waitingDialog;
    FeedCreatePresenterImpl feedCreatePresenter;
    RelativeLayout btn_camera, btn_gallery, btn_delete_image;
    EditText edt_post;
    private boolean isPostReady = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        iniListener();
        btn_camera = findViewById(R.id.btn_camera);
        btn_gallery = findViewById(R.id.btn_gallery);
        btn_delete_image = findViewById(R.id.btn_delete_image);
        edt_post = findViewById(R.id.edt_post);
    }

    private void iniListener() {
        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedCreatePresenter.onButtonCameraClick();
            }
        });

        btn_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedCreatePresenter.onButtonGalleryClick();
            }
        });

        btn_delete_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedCreatePresenter.onButtonDeleteImageClick();
            }
        });

        edt_post.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                feedCreatePresenter.isPostReady(s.toString().trim());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public void setTitleActionBar(String title) {

    }

    @Override
    public void setPostFeed(String post) {

    }

    @Override
    public void setPostImage(String imageName) {

    }

    @Override
    public void onStartLoading() {
        waitingDialog.setCancelable(false);
        waitingDialog.show();


    }

    @Override
    public void onStopLoading() {

    }

    @Override
    public void failMessage(String message) {

    }

    @Override
    public void onSuccessEditPost(String feedPost, String feedImage) {

    }

    @Override
    public void onSuccessAddPost(int feedId, String feedPost, String feedImage) {

    }

    @Override
    public void pickFromGallery() {

    }

    @Override
    public void launchIntentTakePhoto(String imageName) {

    }

    @Override
    public void onStartUploadPhoto() {

    }

    @Override
    public void onStopUploadPhoto() {

    }

    @Override
    public void onSuccessUploadPhoto(String imageName) {

    }

    @Override
    public void onErrorUploadPhoto(String message) {

    }

    @Override
    public void navigateToImageSlider(ArrayList<String> photoList, int index) {

    }

    @Override
    public void setImagePostGone() {

    }

    @Override
    public void setImagePostVisible() {

    }

    @Override
    public void setLayoutImagePostGone() {

    }

    @Override
    public void setLayoutImagePostVisible() {

    }

    @Override
    public void setPostReady() {
        isPostReady = true;
        invalidateOptionsMenu();
    }

    @Override
    public void setPostNotReady() {
        isPostReady = false;
        invalidateOptionsMenu();
    }
}
