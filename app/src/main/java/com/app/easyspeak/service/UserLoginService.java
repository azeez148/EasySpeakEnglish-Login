package com.app.easyspeak.service;

import android.content.Context;

import com.app.easyspeak.model.User;

/**
 * Created by user1 on 6/15/2017.
 */

public interface UserLoginService {
    public User authenticateUser(User user, Context context);
}
