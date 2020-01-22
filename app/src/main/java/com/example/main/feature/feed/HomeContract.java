package com.example.main.feature.feed;

import com.example.featurefeed.data.source.model.local.Feed;

import java.util.List;

public interface HomeContract {

    interface View {
        void onStartRefresh();

        void onStopRefresh();

        void onAcceptLoadFeedFirstPage(List<Feed> feedList, int total_page);

        void onAcceptLoadFeedNextPage(List<Feed> feed_list, int total_page);

        void onErrorLoadNextPage(String message);

        void showMessage(String message);

        void navigateToAddFeed();

        void navigateToEditFeed(int feedId, String feedPost, String feedImage, int position);

        void navigateToLikeList(int feedId);

        void navigateToCommentList(int feedId, int position);

        void setLikeFeed(int position);

        void setDislikeFeed(int position);

        void setTotalComment(int position, int totalComment);

        void onDeleteFeedSuccess(int position);

        void editFeed(int position, String newFeedPost, String newFeedImage);

        void addFeed(Feed newFeed);


    }

    interface Presenter {
        void onCreate();

        void loadFirstPageFromServer(int currentPage);

        void loadNextPageFromServer(int currentPage);

        void onAddFeedButtonClick();

        void onClickTotalLike(int feedId);

        void onClickTotalComment(int feedId, int position);

        void onClickBtnLike(int feedId, int isLiked, int position);

        void onClickBtnComment(int feedId, int position);

        void onClickEmp(int empId);

        void onClickImagePost(String imageName);

        void onClickPost(Feed feed);

        void onClickEditFeed(int feedId, String feedPost, String feedImage, int position);

        void onClickDeleteFeed(int feedId, int position);
    }
}
