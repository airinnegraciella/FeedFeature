package com.example.feedfeature;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.feedfeature.core.data.retrofit.IMyAPI;
import com.example.feedfeature.core.data.retrofit.RetrofitClient;
import com.example.feedfeature.core.data.sharedPreference.SharedPreferenceManager;
import com.example.featurelike.data.source.model.remote.response.ResponseLogin;
import com.example.feedfeature.feature.feed.HomeActivity;
import com.example.feedfeature.core.utils.Constant;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    public static Intent getIntent(Context context){
        return new Intent(context,LoginActivity.class);
    }

    IMyAPI myAPI;

    EditText edtEmail;
    EditText edtPassword;

    Button btnSignin;
    SharedPreferenceManager spm;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initListener();
        initRetrofit();
        initSP();
    }

    @SuppressLint("CommitPrefEdits")
    private void initSP() {
        SharedPreferences sp = getSharedPreferences(Constant.SP_APP, Context.MODE_PRIVATE);
        spm = new SharedPreferenceManager(sp,sp.edit());
    }

    private void initRetrofit() {
        //Init API
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(IMyAPI.class);

    }

    private void initListener() {
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();
                validateLogin(email, password);

            }
        });
    }

    private void validateLogin(final String email, String password) {
        if(!email.isEmpty() && !password.isEmpty()){
            myAPI.login(email, password)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<ResponseLogin>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            compositeDisposable.add(d);
                        }

                        @Override
                        public void onSuccess(ResponseLogin responseLogin) {
                            if(responseLogin.getStatus().equalsIgnoreCase("Success")){
                                spm.saveSPString(Constant.SP_EMAIL,email);
                                spm.saveSPInt(Constant.SP_EMPLOYEE_ID,responseLogin.getEmployee().getEmployeeId());
                                spm.saveSPBoolean(Constant.SP_IS_LOGGED_IN,true);
                                startActivity(HomeActivity.getIntent(LoginActivity.this));
                            }
                            else{
                                Toast.makeText(LoginActivity.this,responseLogin.getMessage(),Toast.LENGTH_LONG).show();
                            }

                        }

                        @Override
                        public void onError(Throwable e) {
                            Toast.makeText(LoginActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });

        }
        else{
            Toast.makeText(this, "Please enter email or password!", Toast.LENGTH_LONG).show();
        }
    }


    private void initView() {
        edtEmail = findViewById(R.id.et_email);
        edtPassword = findViewById(R.id.et_password);
        btnSignin = findViewById(R.id.btn_login);
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
