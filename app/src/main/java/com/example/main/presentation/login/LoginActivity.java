package com.example.main.presentation.login;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.main.R;
import com.example.main.core.data.retrofit.IMyAPI;
import com.example.main.core.data.retrofit.RetrofitClient;
import com.example.main.core.data.sharedPreference.SharedPreferenceManager;
import com.example.main.core.data.source.user.repository.LoginRepositoryImpl;
import com.example.main.core.utils.Constant;
import com.example.main.domain.usecase.LoginUseCase;
import com.example.main.presentation.home.HomeActivity;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import retrofit2.Retrofit;

public class LoginActivity extends DaggerAppCompatActivity implements LoginContract.View {
    
    public static Intent getIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }
    
    @Inject
    LoginPresenterImpl loginPresenter;
    
    EditText edtEmail;
    EditText edtPassword;
    Button btnSignin;
    ProgressDialog waitingDialog;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initListener();
        loginPresenter.onCreate();
    }
    
    private void initListener() {
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();
                loginPresenter.onButtonLoginClick(email, password);
            }
        });
    }
    
    
    private void initView() {
        edtEmail = findViewById(R.id.et_email);
        edtPassword = findViewById(R.id.et_password);
        btnSignin = findViewById(R.id.btn_login);
    }
    
    
    @Override
    public void onStartLogin() {
        btnSignin.setEnabled(false);
        
        waitingDialog = new ProgressDialog(this);
        waitingDialog.setCancelable(false);
        waitingDialog.setMessage("Logging In...");
        waitingDialog.show();
        
    }
    
    @Override
    public void onInputEmpty() {
        if (waitingDialog.isShowing()) {
            waitingDialog.dismiss();
        }
        
        btnSignin.setEnabled(true);
        Toast.makeText(this, "Please enter your email and password.", Toast.LENGTH_LONG).show();
        
    }
    
    @Override
    public void failLogin(String message) {
        if (waitingDialog.isShowing()) {
            waitingDialog.dismiss();
        }
        
        btnSignin.setEnabled(true);
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
    
    @Override
    public void successLogin() {
        if (waitingDialog.isShowing()) {
            waitingDialog.dismiss();
        }
        
        btnSignin.setEnabled(true);
        startActivity(HomeActivity.getIntent(this).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        
    }
    
    @Override
    protected void onStart() {
        super.onStart();
        //ketika user interface terlihat oleh user
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        //ketika di foreground
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        //ketika kembali ke background
    }
    
    @Override
    protected void onStop() {
        super.onStop();
        //ketika user interface tidak terlihat oleh user
        //i.e : pindah activity
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //destroy instance activity
    }
    
    @Override
    protected void onRestart() {
        super.onRestart();
        //ketika user balik lagi ke activity ini, dari activity lain
    }
}
