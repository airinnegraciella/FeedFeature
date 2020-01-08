package com.example.feedfeature;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText edtEmail;
    EditText edtPassword;

    Button btnSignin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initListener();
    }

    private void initListener() {
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();
                validateLogin(username, password);

            }
        });
    }

    private void validateLogin(String username, String password) {
        if(!username.isEmpty() && !password.isEmpty()){
            if(username.equals("admin") && password.equals("admin")){
                Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show();

                /* this is what passed through intent
                * Intent is used for relay bundle(s) through activities
                Intent homeIntent = new Intent(this,HomeActivity.class);
                homeIntent.putExtra("user_key", username);
                homeIntent.putExtra("password_key", password);
                startActivity(homeIntent);
                 */

                startActivity(HomeActivity.getIntent(this,username,password));
            }
            else{
                Toast.makeText(this, "Invalid username or password!", Toast.LENGTH_LONG).show();
            }

        }
        else{
            Toast.makeText(this, "Please enter username or password!", Toast.LENGTH_LONG).show();
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
