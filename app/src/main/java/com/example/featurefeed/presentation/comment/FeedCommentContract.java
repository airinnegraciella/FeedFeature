package com.example.featurefeed.presentation.comment;

import com.example.featurefeed.data.source.model.local.FeedComment;

import java.util.List;

public interface FeedCommentContract {

    interface View{
        void onStartLoad();
        void onStopLoad();
        void onAcceptLoadFeedCommentFirstPage(List<FeedComment> feedCommentList, int total_page);
        void onAcceptLoadFeedCommentNextPage(List<FeedComment> feedCommentList, int total_page);
        void onErrorLoadNextPage(String message);
        void onErrorLoad(String errorMessage);
        void onCommentSuccess(FeedComment feedComment);
        void onCommentError(String error);
        void onDeleteCommentSuccess(int position);
        void showMessage(String message);
    }

    interface Presenter{
        void onCreate(int feedId, int position);
        void loadFirstPageFromServer(int currentPage);
        void loadNextPageFromServer(int currentPage);

    }
}
