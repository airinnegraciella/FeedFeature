package com.example.featurefeed.presentation.edit_comment;

import com.example.featurefeed.data.source.model.remote.response.ResponseEditFeedComment;
import com.example.featurefeed.domain.model.EditFeedComment;
import com.example.featurefeed.domain.usecase.EditFeedCommentUseCase;
import com.example.main.core.base.ICallback;
import com.example.main.core.domain.user.model.CurrentUser;
import com.example.main.core.domain.user.usecase.GetCurrentUserUseCase;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class EditFeedCommentPresenterImpl implements EditFeedCommentContract.Presenter {

    private EditFeedCommentContract.View view;
    private CompositeDisposable compositeDisposable;

    private GetCurrentUserUseCase getCurrentUserUseCase;
    private EditFeedCommentUseCase editFeedCommentUseCase;

    private int currentEmployeeId = 0;
    private int feedCommentId = 0;

    public EditFeedCommentPresenterImpl(EditFeedCommentContract.View view, GetCurrentUserUseCase getCurrentUserUseCase, EditFeedCommentUseCase editFeedCommentUseCase) {
        this.view = view;
        this.getCurrentUserUseCase = getCurrentUserUseCase;
        this.editFeedCommentUseCase = editFeedCommentUseCase;
    }

    @Override
    public void onCreate(int feedCommentId, String oldComment) {
        compositeDisposable = new CompositeDisposable();
        this.feedCommentId = feedCommentId;

        view.setFeedComment(oldComment);
        getCurrentUser();
    }

    @Override
    public void onButtonUpdateClick(final String newComment) {
        view.onStartLoading();
        editFeedCommentUseCase.execute(new EditFeedComment(feedCommentId, currentEmployeeId, newComment, ""), new ICallback<ResponseEditFeedComment>() {
            @Override
            public void onDisposableAcquired(Disposable disposable) {
                compositeDisposable.add(disposable);
            }

            @Override
            public void onSuccess(ResponseEditFeedComment result) {
                view.onSuccessEditComment(newComment);
                view.onStopLoading();
            }

            @Override
            public void onError(String error) {
                view.failMessage(error);
            }

            @Override
            public void onInputEmpty() {
                view.failMessage("Please fill note.");
            }
        });

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
                view.onStopLoading();
            }

            @Override
            public void onError(String error) {
                view.failMessage(error);
            }
            
            @Override
            public void onInputEmpty() {

            }
        });
    }
}
