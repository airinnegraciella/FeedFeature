package com.example.featurefeed.domain.usecase;

import com.example.featurefeed.data.source.model.remote.response.ResponseGetFeed;
import com.example.featurefeed.domain.model.GetFeed;
import com.example.featurefeed.domain.repository.FeedRepository;
import com.example.main.core.base.BaseUseCase;
import com.example.main.core.base.ICallback;

import io.reactivex.disposables.Disposable;

public class GetFeedUseCase extends BaseUseCase<GetFeed, ICallback<ResponseGetFeed>> {
    
    private FeedRepository feedRepository;
    
    public GetFeedUseCase(FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }
    
    @Override
    public void execute(GetFeed getFeed, final ICallback<ResponseGetFeed> callback) {
        feedRepository.getFeed(getFeed.getFeedId(), getFeed.getEmployeeId(), new ICallback<ResponseGetFeed>() {
            @Override
            public void onDisposableAcquired(Disposable disposable) {
                callback.onDisposableAcquired(disposable);
            }
    
            @Override
            public void onSuccess(ResponseGetFeed result) {
                if(result.getStatus().equalsIgnoreCase("Success")){
                    callback.onSuccess(result);
                }
                else{
                    callback.onError(result.getMessage());
                }
            }
    
            @Override
            public void onError(String error) {
                callback.onError(error);
            }
    
            @Override
            public void onInputEmpty() {
                callback.onInputEmpty();
            }
        });
    }
}
