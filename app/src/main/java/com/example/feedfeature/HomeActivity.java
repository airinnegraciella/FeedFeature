package com.example.feedfeature;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.feedfeature.data.sharedPreference.SharedPreferenceManager;
import com.example.feedfeature.utils.Constant;

public class HomeActivity extends AppCompatActivity {

    SharedPreferenceManager spm;

    public static Intent getIntent(Context context){
        return new Intent(context,HomeActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initSP();
        checkLogIn();
    }

    private void checkLogIn() {
        if(!spm.isLoggedIn()){
            logout();
        }
    }

    @SuppressLint("CommitPrefEdits")
    private void initSP() {
        SharedPreferences sp = getSharedPreferences(Constant.SP_APP, Context.MODE_PRIVATE);
        spm = new SharedPreferenceManager(sp,sp.edit());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home,menu);
        return true;
    }

    private void logout(){
        spm.saveSPString(Constant.SP_EMAIL,"");
        spm.saveSPInt(Constant.SP_EMPLOYEE_ID,0);
        spm.saveSPBoolean(Constant.SP_IS_LOGGED_IN,false);
        Intent loginIntent = LoginActivity.getIntent(this);
        loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.btn_logout){
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
