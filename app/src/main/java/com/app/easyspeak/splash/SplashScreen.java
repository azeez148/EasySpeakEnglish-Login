package com.app.easyspeak.splash;

/**
 * Created by user1 on 6/15/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.app.easyspeak.activity.R;
import com.app.easyspeak.activity.WelcomeActivity;
import com.app.easyspeak.model.User;
import com.app.easyspeak.serviceImpl.SplashServiceImpl;
import com.app.easyspeak.serviceImpl.UserLoginServiceImpl;

import javax.inject.Inject;

public class SplashScreen extends AppCompatActivity {
    User user = null;
    @Inject
    private UserLoginServiceImpl userLoginService;
    Context context = null;
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
    @Inject
    private SplashServiceImpl splashService;
    public SplashScreen() {
        super();
        splashService = new SplashServiceImpl();
        userLoginService = new UserLoginServiceImpl();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        context =  getApplicationContext();
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
            Intent i = new Intent(SplashScreen.this, WelcomeActivity.class);
            startActivity(i);
            finish();
            }
        }, SPLASH_TIME_OUT);
    }

}
