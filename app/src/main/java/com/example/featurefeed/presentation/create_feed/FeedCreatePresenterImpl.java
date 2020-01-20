package com.example.featurefeed.presentation.create_feed;

import android.content.Context;
import android.content.Intent;

import com.example.featurefeed.domain.usecase.CreateFeedUseCase;
import com.example.main.core.utils.TakePhoto;

public class FeedCreatePresenterImpl implements FeedCreateContract.Presenter {

    private CreateFeedUseCase createFeedUseCase;
    private FeedCreateContract.View view;

    public FeedCreatePresenterImpl(CreateFeedUseCase createFeedUseCase, FeedCreateContract.View view) {
        this.createFeedUseCase = createFeedUseCase;
        this.view = view;
    }

    @Override
    public void onCreate(int feedId, boolean isCreated, String feedPost, String feedImage) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data, TakePhoto takePhoto, Context context) {

    }

    @Override
    public void onButtonPostClick(String post) {
//        createFeedUseCase.execute(new CreateFeed());
    }

    @Override
    public void onButtonDeleteImageClick() {

    }

    @Override
    public void onButtonCameraClick() {

    }

    @Override
    public void onButtonGalleryClick() {

    }

    @Override
    public void isPostReady(String post) {

    }
}
