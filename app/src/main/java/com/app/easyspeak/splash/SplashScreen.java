package com.app.easyspeak.splash;

/**
 * Created by user1 on 6/15/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.app.easyspeak.activity.LoginUserActivity;
import com.app.easyspeak.activity.R;
import com.app.easyspeak.activity.UserHomeActivity;
import com.app.easyspeak.activity.WelcomeActivity;
import com.app.easyspeak.model.User;
import com.app.easyspeak.model.UserSession;
import com.app.easyspeak.serviceImpl.SplashServiceImpl;
import com.app.easyspeak.serviceImpl.UserLoginServiceImpl;
import com.app.easyspeak.utils.PrefManager;

import javax.inject.Inject;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SplashScreen extends Activity {
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
        context =  getApplicationContext();
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {

//                SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
//                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
//                pDialog.setTitleText("Please Wait..");
//                pDialog.setCancelable(false);
//                pDialog.show();

                // This method will be executed once the timer is over
                // Start your app main activity

//                String spalshToastMessageFirst = "Hello There!!";
//                Toast.makeText(context, spalshToastMessageFirst, Toast.LENGTH_SHORT).show();
//
//
//
//                UserSession userSession =splashService.getUserSession(context);
//
//                String spalshToastMessageSecond = "Welcome to EasyEnglishSpeak!!";
//                Toast.makeText(context, spalshToastMessageSecond, Toast.LENGTH_SHORT).show();
//
//                if( userSession != null && userSession.getIsActive() == 1){
//                    user = new User(userSession.getUserName());
//                    user = splashService.getUserByUserName(user,context);
//                    Intent i = new Intent(SplashScreen.this, UserHomeActivity.class);
//                    i.putExtra("user",user);
//                    startActivity(i);
//                } else {
//                    Intent i = new Intent(SplashScreen.this, LoginUserActivity.class);
//                    startActivity(i);
//                }
                // close this activity



                Intent i = new Intent(SplashScreen.this, WelcomeActivity.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

}
