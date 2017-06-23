package com.app.easyspeak.service;

import android.content.Context;

import com.app.easyspeak.model.User;

/**
 * Created by user1 on 6/20/2017.
 */

public interface UserHomeService {
    public User getUserByUserName(User user, Context context);

}
