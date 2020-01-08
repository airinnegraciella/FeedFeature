package com.example.feedfeature;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    private static final String USER_EXTRA = "user_key";
    private static final String PASSWORD_EXTRA = "password_key";

    public static Intent getIntent(Context context, String username, String password){
        Intent homeIntent = new Intent(context,HomeActivity.class);
        homeIntent.putExtra(USER_EXTRA, username);
        homeIntent.putExtra(PASSWORD_EXTRA, password);
        return homeIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String username = getIntent().getStringExtra(USER_EXTRA);
        String password = getIntent().getStringExtra(PASSWORD_EXTRA);
        Toast.makeText(this,username+" #"+password,Toast.LENGTH_LONG).show();
    }
}
