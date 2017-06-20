package com.app.easyspeak.serviceImpl;

import android.app.Application;
import android.content.Context;
import android.database.SQLException;
import android.util.Log;

import com.app.easyspeak.activity.LoginUserActivity;
import com.app.easyspeak.db.DatabaseHandler;
import com.app.easyspeak.model.User;
import com.app.easyspeak.service.UserLoginService;

/**
 * Created by user1 on 6/15/2017.
 */

public class UserLoginServiceImpl implements UserLoginService {
    DatabaseHandler db =null;
    @Override
    public User authenticateUser(User user, Context context) {
        try {
            db = new DatabaseHandler(context);

            Log.v("user  is ",user.toString());
            user = db.getContactByUserName(user);

            if (user.getId().equals("0")) {
                Log.v("user not already in ",user.toString());
                long id = db.addUserFromLogin(user);
                user = db.getContact(id);
                db.addUserSession(user);
                Log.v("user  created  ",user.toString());
            }
        }catch (SQLException e){
            Log.v("while creating user ",e.getStackTrace().toString());

        }finally {
            db.close();
        }
        Log.v("user reutrning   ",user.toString());
        return user;
    }

}
