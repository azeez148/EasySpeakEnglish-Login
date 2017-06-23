package com.app.easyspeak.serviceImpl;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
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
    long id = 0;
    @Override
    public User authenticateUser(User user, Context context) {
        try {
            db = new DatabaseHandler(context);
            user = db.getContactByUserName(user);
            Log.v("user  is ",user.toString());
            if(user.getId().equals("0")){
                 id = db.addUserFromLogin(user);
            }else{
                id = Long.valueOf(user.getId());
            }
            user = db.getContact(id);
            Log.v("user  created  ",user.toString());

        }catch (SQLException e){
            Log.v("while creating user ",e.getStackTrace().toString());
            return null;
        }finally {
            db.close();
        }
        Log.v("user reutrning   ",user.toString());
        return user;
    }

}
