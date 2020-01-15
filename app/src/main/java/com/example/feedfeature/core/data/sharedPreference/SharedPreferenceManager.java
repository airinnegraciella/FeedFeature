package com.example.feedfeature.core.data.sharedPreference;

import android.content.SharedPreferences;

import com.example.feedfeature.utils.Constant;

public class SharedPreferenceManager {

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    public SharedPreferenceManager(SharedPreferences sp, SharedPreferences.Editor editor) {
        this.sp = sp;
        this.editor = editor;
    }

    public void saveSPString(String keySP,String value) {
        editor.putString(keySP, value);
        editor.commit();
    }

    public void  saveSPInt(String keySP,int value) {
        editor.putInt(keySP, value);
        editor.commit();
    }

    public void  saveSPBoolean(String keySP,boolean value) {
        editor.putBoolean(keySP, value);
        editor.commit();
    }

    public String getSPEmail() {
        return sp.getString(Constant.SP_EMAIL, "");
    }

    public int getSPEmployeeId() {
        return sp.getInt(Constant.SP_EMPLOYEE_ID, 0);
    }

    public boolean isLoggedIn() {
        return sp.getBoolean(Constant.SP_IS_LOGGED_IN, false);
    }
}
