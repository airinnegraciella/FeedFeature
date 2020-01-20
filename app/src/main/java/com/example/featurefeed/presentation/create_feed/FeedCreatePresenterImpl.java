package com.example.featurefeed.presentation.create_feed;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.example.featurefeed.domain.usecase.CreateFeedUseCase;
import com.example.main.core.base.ICallback;
import com.example.main.core.domain.user.model.CurrentUser;
import com.example.main.core.domain.user.usecase.GetCurrentUserUseCase;
import com.example.main.core.utils.Constant;
import com.example.main.core.utils.TakePhoto;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class FeedCreatePresenterImpl implements FeedCreateContract.Presenter {

    private CreateFeedUseCase createFeedUseCase;
    private FeedCreateContract.View view;
    private CompositeDisposable compositeDisposable;
    private GetCurrentUserUseCase getCurrentUserUseCase;
    private int currentEmployeeId;
    private int feedId;
    private boolean isCreated;
    private String postEdt;
    private String imageName;
    private String imageFileName;

    public FeedCreatePresenterImpl( FeedCreateContract.View view, CreateFeedUseCase createFeedUseCase, GetCurrentUserUseCase getCurrentUserUseCase) {
        this.view = view;
        this.createFeedUseCase = createFeedUseCase;
        this.getCurrentUserUseCase = getCurrentUserUseCase;
    }

    @Override
    public void onCreate(int feedId, boolean isCreated, String feedPost, String feedImage) {
        compositeDisposable = new CompositeDisposable();
        this.feedId = feedId;
        this.isCreated = isCreated;
        postEdt = feedPost;

        if (isCreated) {
            view.setTitleActionBar("Edit Post");
            view.setPostFeed(postEdt);
            imageName = feedImage;
            checkPostReady();

            if (!imageName.isEmpty()) {
                view.setLayoutImagePostVisible();
                view.setImagePostVisible();
                view.setPostImage(imageName);

            } else {
                view.setTitleActionBar("Create Post");
            }

            getCurrentUser();
        }

    }

    private void getCurrentUser() {
        view.onStartLoading();
        getCurrentUserUseCase.execute("", new ICallback<CurrentUser>() {
            @Override
            public void onDisposableAcquired(Disposable disposable) {

            }

            @Override
            public void onSuccess(CurrentUser result) {
                currentEmployeeId = result.getEmployeeId();
            }

            @Override
            public void onError(String error) {

            }

            @Override
            public void onInputEmpty() {

            }
        });
    }

    private void checkPostReady() {
        if (postEdt.isEmpty() && imageName.isEmpty()) {
            view.setPostNotReady();
        } else {
            view.setPostReady();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data, TakePhoto takePhoto, Context context) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Constant.GALLERY_REQUEST_CODE) {
                Uri selectedImageUri;
                if (data != null) {
                    selectedImageUri = data.getData();
//                    saveImageToDirectory(context, selectedImageUri, "$imageFileName.jpg");
                }
            }
            else if (requestCode == Constant.CAMERA_REQUEST_CODE) {
//                saveImageToDirectory(context,
//                        takePhoto.getmCurrentPhotoUri()!!
//                        , takePhoto.getmCurrentPhotoName()!!
//                )
            }
        }
    }

    @Override
    public void onButtonPostClick(String post) {
        view.onStartLoading();
        if (isCreated) {

        }
    }

    @Override
    public void onButtonDeleteImageClick() {
        imageName = "";
        checkPostReady();
        view.setLayoutImagePostGone();
        view.setImagePostGone();
    }

    @Override
    public void onButtonCameraClick() {
        view.launchIntentTakePhoto(imageFileName);
    }

    @Override
    public void onButtonGalleryClick() {
        view.pickFromGallery();
    }

    @Override
    public void isPostReady(String post) {
        postEdt = post;
        checkPostReady();

    }


}
