package com.app.easyspeak.serviceImpl;

import android.content.Context;
import android.database.SQLException;
import android.util.Log;

import com.app.easyspeak.db.DatabaseHandler;
import com.app.easyspeak.model.User;
import com.app.easyspeak.model.UserSession;
import com.app.easyspeak.service.SplashService;

/**
 * Created by user1 on 6/16/2017.
 */

public class SplashServiceImpl implements SplashService {
    DatabaseHandler db =null;

    @Override
    public UserSession getUserSession(Context context) {
        UserSession userSession =null;
        try {
            db = new DatabaseHandler(context);
            userSession = db.getUserSession();
        }catch (SQLException e){
            Log.v("while getUserSession  ",e.getStackTrace().toString());

        }finally {
            db.close();
        }

        return userSession;
    }
    @Override
    public User getUserByUserName(User user, Context context) {
        try {
            db = new DatabaseHandler(context);
            Log.v("userName  is ",user.toString());
            user = db.getContactByUserName(user);

        }catch (SQLException e){
            Log.v("getUserByUserName ",e.getStackTrace().toString());

        }finally {
            db.close();
        }
        Log.v("user reutrning   ",user.toString());
        return user;
    }

}
