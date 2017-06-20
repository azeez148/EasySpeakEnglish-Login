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
    public boolean logoutUser(User user, Context context) {
        boolean isUpdated =false;
        try {
            db = new DatabaseHandler(context);
            isUpdated = db.logoutUser(user);

        }catch (SQLException e){
            Log.v("logoutUser ",e.getStackTrace().toString());
            return isUpdated;

        }finally {
            db.close();
        }
        Log.v("isUpdated ",String.valueOf(isUpdated));
        return isUpdated;
    }
}
