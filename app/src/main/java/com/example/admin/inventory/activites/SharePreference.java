package com.example.admin.inventory.activites;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.admin.inventory.model.Admin;

public class SharePreference {
//the constants
    private static final String shared_prefer = "mypreference";
    private static final String key_username = "keyusername";
    private static final String key_password = "keypassword";
    private static final String key_id = "keyid";



    private static SharePreference mInstance;
    private static Context mCtx;

    private SharePreference(Context context) {
        mCtx = context;
    }

    public static synchronized SharePreference getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharePreference(context);
        }
        return mInstance;
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(shared_prefer, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key_username, null) != null;
    }

    //method to let the user login
    //this method will store the user data in shared preferences
    public void userLogin(Admin admin) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(shared_prefer, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(key_username, admin.getUsername());
        editor.putString(key_password, admin.getPassword());

        editor.apply();
    }

    //this method will give the logged in user
    public Admin login() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(shared_prefer, Context.MODE_PRIVATE);
        return new Admin(
                sharedPreferences.getString(key_id,null),
                sharedPreferences.getString(key_username, null),
                sharedPreferences.getString(key_password, null)

        );
    }

    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(shared_prefer, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, MainActivity.class));
    }


}
