package com.ridoy.xmnotice;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {

    private static SharedPrefManager mInstance;
    private static Context mCtx;
    int score;
    String name;

    private static final String SHARED_PREF_NAME = "mysharedpref12";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_USER_PHONE = "userphone";
    private static final String KEY_USER_ID = "userid";
    private static final String KEY_USER_SSCPOINT = "sscpoint";
    private static final String KEY_USER_SSCYEAR = "sscyear";
    private static final String KEY_USER_HSCPOINT = "hscpoint";
    private static final String KEY_USER_HSCYEAR = "hscyear";
    private static final String KEY_USER_SCORE = "score";


    private SharedPrefManager(Context context) {
        mCtx = context;

    }
    public SharedPrefManager(Context context,int score) {
        mCtx = context;
        this.score=score;
    }

    public SharedPrefManager(Context context,String name) {
        mCtx = context;
        this.name=name;
    }


    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public boolean userLogin(int id, String name, String phone, String sscpoint, String sscyear, String hscpoint, String hscyear, int score){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(KEY_USER_ID, id);
        editor.putString(KEY_USER_PHONE, phone);
        editor.putString(KEY_USERNAME, name);
        editor.putString(KEY_USER_SSCPOINT, sscpoint);
        editor.putString(KEY_USER_SSCYEAR, sscyear);
        editor.putString(KEY_USER_HSCPOINT, hscpoint);
        editor.putString(KEY_USER_HSCYEAR, hscyear);
        editor.putInt(KEY_USER_SCORE, score);

        editor.apply();

        return true;
    }

    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if(sharedPreferences.getString(KEY_USERNAME, null) != null){
            return true;
        }
        return false;
    }

    public boolean logout(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }


    public String getUsername(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null);
    }

    public String getUserphone(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_PHONE, null);
    }

    public String getUsersscpoint(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_SSCPOINT, null);
    }
    public String getUserhscpoint(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_HSCPOINT, null);
    }
    public String getUsersscyear(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_SSCYEAR, null);
    }
    public String getUserhscyear(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_HSCYEAR, null);
    }

    public int getUserId(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_USER_ID, 1);
    }
    public int getUserScore(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_USER_SCORE, 0);
    }

    public void updateUserScore(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_USER_SCORE, score);

        editor.apply();

    }
    public void updateUserName(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USERNAME, name);

        editor.apply();


    }
}
