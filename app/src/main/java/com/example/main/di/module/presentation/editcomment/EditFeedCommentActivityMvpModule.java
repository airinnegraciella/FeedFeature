package com.example.main.di.module.presentation.editcomment;

import com.example.featurefeed.presentation.edit_comment.EditFeedCommentActivity;
import com.example.featurefeed.presentation.edit_comment.EditFeedCommentContract;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class EditFeedCommentActivityMvpModule {
    @Binds
    abstract EditFeedCommentContract.View provideView(EditFeedCommentActivity editFeedCommentActivity);
}
