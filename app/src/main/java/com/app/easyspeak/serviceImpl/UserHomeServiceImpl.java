package com.app.easyspeak.serviceImpl;

import android.content.Context;
import android.database.SQLException;
import android.util.Log;

import com.app.easyspeak.db.DatabaseHandler;
import com.app.easyspeak.model.User;
import com.app.easyspeak.service.UserHomeService;

/**
 * Created by user1 on 6/20/2017.
 */

public class UserHomeServiceImpl implements UserHomeService {
    DatabaseHandler db =null;

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
