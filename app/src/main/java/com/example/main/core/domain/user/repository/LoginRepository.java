package com.example.main.core.domain.user.repository;

import com.example.main.core.data.source.user.model.remote.ResponseLogin;
import com.example.main.core.base.ICallback;
import com.example.main.core.domain.user.model.CurrentUser;

public interface LoginRepository {
    void validateLogin(final String email, String password, ICallback<ResponseLogin> callback);
    
    void setCurrentUserLogin(CurrentUser currentUser);
}
