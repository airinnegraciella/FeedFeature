package com.example.featurefeed.presentation.comment;

import android.content.Intent;

import com.example.featurefeed.data.source.model.local.FeedComment;

import java.util.List;

public interface FeedCommentContract {

    interface View {
        void onStartLoad();

        void onStopLoad();

        void onAcceptLoadFeedCommentFirstPage(List<FeedComment> feedCommentList, int total_page);

        void onAcceptLoadFeedCommentNextPage(List<FeedComment> feedCommentList, int total_page);

        void onErrorLoadNextPage(String message);

        void onErrorLoad(String errorMessage);

        void navigateToEditFeed(int feedCommentId, String feedComment, int position);

        void onCommentSuccess(FeedComment feedComment);

        void onCommentError(String error);

        void onDeleteCommentSuccess(int position);

        void showMessage(String message);

        void editComment(int position, String newFeedComment, String newFeedCommentImage);
    }

    interface Presenter {
        void onCreate(int feedId, int position);

        void loadFirstPageFromServer(int currentPage);

        void loadNextPageFromServer(int currentPage);

        void onBtnCommentClick(String comment);

        void onClickEditComment(int feedCommentId, String feedComment, String feedCommentImage, int position);

        void onClickDeleteComment(int feedCommentId, int position);

        void onActivityResult(int requestCode, int resultCode, Intent data);

    }
}
