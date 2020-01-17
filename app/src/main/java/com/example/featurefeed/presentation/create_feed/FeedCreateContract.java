package com.example.featurefeed.presentation.create_feed;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

public interface FeedCreateContract {

    interface View{
        void setTitleActionBar(String title);

        void setPostFeed(String post);

        void setPostImage(String imageName );

        void onStartLoading();

        void onStopLoading();

        void failMessage(String message );

        void onSuccessEditPost(String feedPost , String feedImage );

        void onSuccessAddPost(int feedId, String feedPost , String feedImage );

        void pickFromGallery();

        void launchintentTakePhoto(String imageName );

        void onStartUploadPhoto();

        void onStopUploadPhoto();

        void onSuccessUploadPhoto(String imageName );

        void onErrorUploadPhoto(String message );

        void navigateToImageSlider(ArrayList<String> photoList , int index);

        void setImagePostGone();

        void setImagePostVisible();

        void setLayoutImagePostGone();

        void setLayoutImagePostVisible();

        void setPostReady();

        void setPostNotReady();
    }

    interface Presenter{
        void onCreate(int feedId , boolean isCreated, String feedPost , String feedImage );

//        void onActivityResult(int requestCode , int resultCode , Intent data, TakePhoto takePhoto , Context context );

        void onButtonPostClick(String post);

        void onButtonDeleteImageClick();

        void onButtonCameraClick();

        void onButtonGalleryClick();

        void isPostReady(String post );
    }
}
