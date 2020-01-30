package com.example.main.presentation.login;

public interface LoginContract {
    
    interface View {
        void onStartLogin();
        
        void onInputEmpty();
        
        void failLogin(String message);
        
        void successLogin();
        
    }
    
    interface Presenter {
        void onButtonLoginClick(String userId, String password);
        
        void onCreate();
        
    }
}
