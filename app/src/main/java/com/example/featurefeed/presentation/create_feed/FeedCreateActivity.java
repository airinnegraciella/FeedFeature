package com.example.featurefeed.presentation.create_feed;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.featurefeed.data.source.repository.FeedRepositoryImpl;
import com.example.featurefeed.domain.usecase.CreateFeedUseCase;
import com.example.main.R;
import com.example.main.core.data.retrofit.IMyAPI;
import com.example.main.core.data.retrofit.RetrofitClient;
import com.example.main.core.data.sharedPreference.SharedPreferenceManager;
import com.example.main.core.domain.user.repository.UserRepositoryImpl;
import com.example.main.core.domain.user.usecase.GetCurrentUserUseCase;
import com.example.main.core.utils.Constant;
import com.example.main.core.utils.TakePhoto;

import java.util.ArrayList;

import retrofit2.Retrofit;

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

    IMyAPI myAPI;
    TakePhoto takePhoto;
    AlertDialog waitingDialog;
    ProgressBar progress_bar_photo;
    FeedCreatePresenterImpl feedCreatePresenter;
    SharedPreferenceManager spm;

    RelativeLayout btn_camera, btn_gallery, btn_delete_image, layout_image_post;
    ImageView iv_image_post;
    EditText edt_post;
    TextView btn_back;

//    private int feedId;
//    private boolean isCreated;
//    private String feedPost;
//    private String feedImage;
//    private int positionFeed;
    private boolean isPostReady = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_feed);
        initAPI();
        initSP();
        initView();
        iniListener();
        feedCreatePresenter = new FeedCreatePresenterImpl(this,
                new CreateFeedUseCase(new FeedRepositoryImpl(myAPI)),
                new GetCurrentUserUseCase(new UserRepositoryImpl(spm)));
        feedCreatePresenter.onCreate(
                getIntent().getIntExtra(FEED_ID, 0),
                getIntent().getBooleanExtra(IS_CREATED, false),
                getIntent().getStringExtra(FEED_POST),
                getIntent().getStringExtra(FEED_IMAGE));
    }


    private void initAPI() {
        //Init API
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(IMyAPI.class);
    }

    @SuppressLint("CommitPrefEdits")
    private void initSP() {
        SharedPreferences sp = getSharedPreferences(Constant.SP_APP, Context.MODE_PRIVATE);
        spm = new SharedPreferenceManager(sp, sp.edit());
    }


    private void initView() {
        btn_camera = findViewById(R.id.btn_camera);
        btn_gallery = findViewById(R.id.btn_gallery);
        btn_delete_image = findViewById(R.id.btn_delete_image);
        edt_post = findViewById(R.id.edt_post);
        progress_bar_photo = findViewById(R.id.progress_bar_photo);
        iv_image_post = findViewById(R.id.iv_image_post);
        layout_image_post = findViewById(R.id.layout_image_post);
        btn_back = findViewById(R.id.btn_back);
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

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void setTitleActionBar(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void setPostFeed(String post) {
        edt_post.setText(post);
    }

    @Override
    public void setPostImage(String imageName) {
        Glide.with(this)
                .load(Constant.getImageAssetPath(Constant.IMAGE_TYPE_FEED,
                        imageName))
                .placeholder(R.drawable.ic_image_gray_24dp)
                .into(iv_image_post);
    }

    @Override
    public void onStartLoading() {
        waitingDialog.setCancelable(false);
        waitingDialog.show();
        waitingDialog.setMessage("Waiting...");

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

    private Intent getReturnIntent(int positionKey, int feedId, String feedPost, String feedImage) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("positionKey", positionKey);
        returnIntent.putExtra("feedId", feedId);
        returnIntent.putExtra("feedPost", feedPost);
        returnIntent.putExtra("feedImage", feedImage);
        return returnIntent;
    }

    @Override
    public void onSuccessEditPost(String feedPost, String feedImage) {
        onStopLoading();
        Toast.makeText(this, "Post successfully edited", Toast.LENGTH_LONG).show();
        setResult(Activity.RESULT_OK, getReturnIntent(getIntent().getIntExtra(POSITION_FEED, 0), getIntent().getIntExtra(FEED_ID, 0), feedPost, feedImage));
        finish();

    }

    @Override
    public void onSuccessAddPost(int feedId, String feedPost, String feedImage) {
        onStopLoading();
        Toast.makeText(this, "Post successfully created", Toast.LENGTH_LONG).show();
        setResult(Activity.RESULT_OK, getReturnIntent(getIntent().getIntExtra(POSITION_FEED, 0), feedId, feedPost, feedImage));
        finish();
    }

    @Override
    public void pickFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        ArrayList<String> mimeTypes = new ArrayList<>();
        mimeTypes.add("image/jpg");
        mimeTypes.add("image/png");
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        startActivityForResult(intent, Constant.GALLERY_REQUEST_CODE);

    }

    @Override
    public void launchIntentTakePhoto(String imageName) {
        takePhoto.dispatchTakePictureIntent(this, this, Constant.CAMERA_REQUEST_CODE, imageName);
    }

    @Override
    public void onStartUploadPhoto() {
        if (progress_bar_photo.getVisibility() != View.VISIBLE) {
            progress_bar_photo.setVisibility(View.VISIBLE);
        }

        btn_camera.setEnabled(false);
        btn_gallery.setEnabled(false);
    }

    @Override
    public void onStopUploadPhoto() {
        if (progress_bar_photo.getVisibility() != View.GONE) {
            progress_bar_photo.setVisibility(View.GONE);
        }

        btn_camera.setEnabled(true);
        btn_gallery.setEnabled(true);
    }

    @Override
    public void onSuccessUploadPhoto(String imageName) {
        Glide.with(this)
                .load(Constant.getImageAssetPath(Constant.IMAGE_TYPE_FEED, imageName))
                .placeholder(R.drawable.ic_image_gray_24dp)
                .into(iv_image_post);

    }

    @Override
    public void onErrorUploadPhoto(String message) {
        onStopUploadPhoto();
        Toast.makeText(this, message, Toast.LENGTH_LONG);
    }

    @Override
    public void navigateToImageSlider(ArrayList<String> photoList, int index) {

    }

    @Override
    public void setImagePostGone() {
        iv_image_post.setVisibility(View.GONE);
        btn_delete_image.setVisibility(View.GONE);

    }

    @Override
    public void setImagePostVisible() {
        iv_image_post.setVisibility(View.VISIBLE);
        btn_delete_image.setVisibility(View.VISIBLE);

    }

    @Override
    public void setLayoutImagePostGone() {
        layout_image_post.setVisibility(View.GONE);

    }

    @Override
    public void setLayoutImagePostVisible() {
        layout_image_post.setVisibility(View.VISIBLE);

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
