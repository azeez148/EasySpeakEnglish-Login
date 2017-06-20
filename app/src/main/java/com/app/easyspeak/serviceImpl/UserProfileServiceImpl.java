package com.app.easyspeak.serviceImpl;

import android.content.Context;
import android.database.SQLException;
import android.util.Log;

import com.app.easyspeak.db.DatabaseHandler;
import com.app.easyspeak.model.User;
import com.app.easyspeak.service.UserProfileService;

/**
 * Created by user1 on 6/20/2017.
 */

public class UserProfileServiceImpl implements UserProfileService {
    DatabaseHandler db =null;

    @Override
    public User updateUserProfile(User user, Context context) {
        try {
            db = new DatabaseHandler(context);

            Log.v("user  is ",user.toString());
            user = db.updateUserProfile(user);
            Log.v("user  created  ",user.toString());

        }catch (SQLException e){
            Log.v("while creating user ",e.getStackTrace().toString());

        }finally {
            db.close();
        }
        Log.v("user reutrning   ",user.toString());
        return user;
    }
}
