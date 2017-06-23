package com.app.easyspeak.service;

import android.content.Context;

import com.app.easyspeak.model.User;

/**
 * Created by user1 on 6/16/2017.
 */

public interface SplashService {
    public User getUserByUserName(User user, Context context);
}
