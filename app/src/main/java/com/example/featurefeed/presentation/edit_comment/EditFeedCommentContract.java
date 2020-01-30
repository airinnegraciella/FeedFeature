package com.example.featurefeed.presentation.edit_comment;

public interface EditFeedCommentContract {
    interface View {
        void setFeedComment(String oldComment);
        
        void onStartLoading();
        
        void onStopLoading();
        
        void failMessage(String message);
        
        void onSuccessEditComment(String newComment);
    }
    
    interface Presenter {
        void onCreate(int feedCommentId, String oldComment);
        
        void onButtonUpdateClick(String newComment);
    }
}
