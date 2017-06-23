package com.app.easyspeak.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
 
    // shared pref mode
    int PRIVATE_MODE = 0;
 
    // Shared preferences file name
    private static final String PREF_NAME = "easyspeak-welcome";
 
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private static final String SESSION_USERNAME = "userName";
    private static final String USER_IS_ACTIVE = "IsActive";


    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }
 
    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }
 
    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void setSessionUsername(String userName) {
        editor.putString(SESSION_USERNAME, userName);
        editor.commit();
    }
    public void setUserIsActive(boolean IsActive) {
        editor.putBoolean(USER_IS_ACTIVE, IsActive);
        editor.commit();
    }

    public  String getSessionUsername() {
        return pref.getString(SESSION_USERNAME ,"0");
    }

    public  boolean getUserIsActive() {
            return pref.getBoolean(USER_IS_ACTIVE, false);

    }
}