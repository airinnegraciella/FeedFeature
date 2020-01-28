package com.example.featurefeed.data.source.repository;

import com.example.featurefeed.data.source.model.remote.response.ResponseCreateFeed;
import com.example.featurefeed.data.source.model.remote.response.ResponseCreateFeedComment;
import com.example.featurefeed.data.source.model.remote.response.ResponseDeleteFeed;
import com.example.featurefeed.data.source.model.remote.response.ResponseDeleteFeedComment;
import com.example.featurefeed.data.source.model.remote.response.ResponseDislikeFeed;
import com.example.featurefeed.data.source.model.remote.response.ResponseEditFeed;
import com.example.featurefeed.data.source.model.remote.response.ResponseEditFeedComment;
import com.example.featurefeed.data.source.model.remote.response.ResponseGetFeed;
import com.example.featurefeed.data.source.model.remote.response.ResponseLikeFeed;
import com.example.featurefeed.data.source.model.remote.response.ResponseTotalFeedComment;
import com.example.featurefeed.data.source.model.remote.response.comment.ResponseFeedCommentPagination;
import com.example.featurefeed.data.source.model.remote.response.feed.ResponseFeedPagination;
import com.example.featurefeed.data.source.model.remote.response.like.ResponseFeedLikesPagination;
import com.example.featurefeed.domain.repository.FeedRepository;
import com.example.main.core.base.ICallback;
import com.example.main.core.data.retrofit.IMyAPI;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FeedRepositoryImpl implements FeedRepository {

    private IMyAPI myAPI;

    public FeedRepositoryImpl(IMyAPI myAPI) {
        this.myAPI = myAPI;
    }

    @Override
    public void createFeed(int makerId, String post, String image, final ICallback<ResponseCreateFeed> callback) {
        myAPI.createFeed(makerId, post, image)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ResponseCreateFeed>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        callback.onDisposableAcquired(d);
                    }

                    @Override
                    public void onSuccess(ResponseCreateFeed response) {
                        callback.onSuccess(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }
                });
    }

    @Override
    public void editFeed(int feedId, int makerId, String post, String image, final ICallback<ResponseEditFeed> callback) {
        myAPI.editFeed(feedId, makerId, post, image)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ResponseEditFeed>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        callback.onDisposableAcquired(d);
                    }

                    @Override
                    public void onSuccess(ResponseEditFeed response) {
                        callback.onSuccess(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }
                });
    }

    @Override
    public void getFeedPagination(int empId, int page, int limit, final ICallback<ResponseFeedPagination> callback) {
        myAPI.feed(page, limit, empId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ResponseFeedPagination>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        callback.onDisposableAcquired(d);
                    }

                    @Override
                    public void onSuccess(ResponseFeedPagination response) {
                        callback.onSuccess(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }
                });
    }

    @Override
    public void createFeedComment(int feedId, int makerId, String comment, String image, final ICallback<ResponseCreateFeedComment> callback) {
        myAPI.createFeedComment(feedId, makerId, comment, image)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ResponseCreateFeedComment>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        callback.onDisposableAcquired(d);
                    }

                    @Override
                    public void onSuccess(ResponseCreateFeedComment response) {
                        callback.onSuccess(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }
                });
    }

    @Override
    public void editFeedComment(int feedCommentId, int makerId, String comment, String image, final ICallback<ResponseEditFeedComment> callback) {
        myAPI.editFeedComment(feedCommentId, makerId, comment, image)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ResponseEditFeedComment>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        callback.onDisposableAcquired(d);
                    }

                    @Override
                    public void onSuccess(ResponseEditFeedComment response) {
                        callback.onSuccess(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }
                });
    }

    @Override
    public void getFeedCommentPagination(int employeeId, int feedId, int page, int limit, final ICallback<ResponseFeedCommentPagination> callback) {
        myAPI.getFeedCommentPagination(employeeId, feedId, page, limit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ResponseFeedCommentPagination>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        callback.onDisposableAcquired(d);
                    }

                    @Override
                    public void onSuccess(ResponseFeedCommentPagination response) {
                        callback.onSuccess(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }
                });
    }

    @Override
    public void likeFeed(int feedId, int employeeId, final ICallback<ResponseLikeFeed> callback) {
        myAPI.likeFeed(feedId, employeeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ResponseLikeFeed>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        callback.onDisposableAcquired(d);
                    }

                    @Override
                    public void onSuccess(ResponseLikeFeed response) {
                        callback.onSuccess(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }
                });
    }

    @Override
    public void dislikeFeed(int feedId, int employeeId, final ICallback<ResponseDislikeFeed> callback) {
        myAPI.dislikeFeed(feedId, employeeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ResponseDislikeFeed>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        callback.onDisposableAcquired(d);
                    }

                    @Override
                    public void onSuccess(ResponseDislikeFeed response) {
                        callback.onSuccess(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }
                });
    }

    @Override
    public void getFeedLikePagination(int feedId, int page, int limit, final ICallback<ResponseFeedLikesPagination> callback) {
        myAPI.getFeedLikePagination(feedId, page, limit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ResponseFeedLikesPagination>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        callback.onDisposableAcquired(d);
                    }

                    @Override
                    public void onSuccess(ResponseFeedLikesPagination response) {
                        callback.onSuccess(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }
                });
    }
    
    @Override
    public void getTotalFeedComment(int feedId, final ICallback<ResponseTotalFeedComment> callback) {
        myAPI.getTotalFeedComment(feedId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ResponseTotalFeedComment>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        callback.onDisposableAcquired(d);
                    }
    
                    @Override
                    public void onSuccess(ResponseTotalFeedComment response) {
                        callback.onSuccess(response);
                    }
    
                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }
                });
        
    }
    
    @Override
    public void deleteFeed(int feedId, final ICallback<ResponseDeleteFeed> callback) {
        myAPI.deleteFeed(feedId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ResponseDeleteFeed>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        callback.onDisposableAcquired(d);
                    }

                    @Override
                    public void onSuccess(ResponseDeleteFeed response) {
                        callback.onSuccess(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }
                });
    }
    
    @Override
    public void deleteFeedComment(int feedCommentId, final ICallback<ResponseDeleteFeedComment> callback) {
        myAPI.deleteFeedComment(feedCommentId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ResponseDeleteFeedComment>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        callback.onDisposableAcquired(d);
                    }
    
                    @Override
                    public void onSuccess(ResponseDeleteFeedComment response) {
                        callback.onSuccess(response);
                    }
    
                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }
                });
        
    }
    
    @Override
    public void getFeed(int feedId, int employeeId, final ICallback<ResponseGetFeed> callback) {
        myAPI.getFeed(feedId, employeeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ResponseGetFeed>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        callback.onDisposableAcquired(d);
                    }
    
                    @Override
                    public void onSuccess(ResponseGetFeed response) {
                        callback.onSuccess(response);
                    }
    
                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }
                });
        
    }
}
