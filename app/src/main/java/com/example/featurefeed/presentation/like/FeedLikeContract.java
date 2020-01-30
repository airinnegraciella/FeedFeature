package com.example.featurefeed.presentation.like;

import com.example.featurefeed.data.source.model.local.FeedLike;

import java.util.List;

public interface FeedLikeContract {
    
    interface View {
        void onStartLoad();
        
        void onStopLoad();
        
        void onAcceptLoadFeedLikeFirstPage(List<FeedLike> feedLikeList, int total_page);
        
        void onAcceptLoadFeedLikeNextPage(List<FeedLike> feedLikeList, int total_page);
        
        void onErrorLoadNextPage(String message);
        
        void showMessage(String message);
    }
    
    interface Presenter {
        void onCreate(int feedId);
        
        void loadFirstPageFromServer(int currentPage);
        
        void loadNextPageFromServer(int currentPage);
        
    }
}
