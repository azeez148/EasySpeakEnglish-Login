package com.app.easyspeak.service;

import android.content.Context;

import com.app.easyspeak.model.User;
import com.app.easyspeak.model.UserSession;

/**
 * Created by user1 on 6/16/2017.
 */

public interface SplashService {
    public UserSession getUserSession(Context context);
    public User getUserByUserName(User user, Context context);
}
