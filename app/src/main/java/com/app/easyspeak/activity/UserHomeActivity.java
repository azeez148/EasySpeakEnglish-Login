package com.app.easyspeak.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.app.easyspeak.model.User;
import com.app.easyspeak.service.UserHomeService;
import com.app.easyspeak.serviceImpl.SplashServiceImpl;
import com.app.easyspeak.serviceImpl.UserHomeServiceImpl;
import com.app.easyspeak.serviceImpl.UserLoginServiceImpl;
import com.app.easyspeak.splash.SplashScreen;
import com.app.easyspeak.utils.PrefManager;

import javax.inject.Inject;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class UserHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ProfileFragment.OnFragmentInteractionListener,
        SpeechToTextFragment.OnFragmentInteractionListener,AboutFragment.OnFragmentInteractionListener,TextToSpeechFragment.OnFragmentInteractionListener,
        SettingsFragment.OnFragmentInteractionListener, HomeFragment.OnFragmentInteractionListener{

    User user=null;
    Context context = null;
    private PrefManager prefManager;
    NavigationView navigationView = null;

//    SweetAlertDialog pDialog =null;

    @Inject
    private UserHomeService userService;
    public UserHomeActivity() {
        super();
        userService = new UserHomeServiceImpl();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = getApplicationContext();
        Intent i = getIntent();
        user = (User)i.getSerializableExtra("user");
        Log.v("user in home activity",user.toString());
        prefManager = new PrefManager(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
//                setHeaderUserName();
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                setHeaderUserName();
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        drawer.openDrawer(GravityCompat.START);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        setHeaderUserName();



        navigationView.getMenu().getItem(0).setChecked(true);
        //add this line to display menu1 when the activity is loaded
        displaySelectedScreen(R.id.nav_home);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_about) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {


        // Handle navigation view item clicks here.
        int id = item.getItemId();

          if (id == R.id.nav_logout) {
              try{
                  prefManager.setUserIsActive(false);
                  prefManager.setFirstTimeLaunch(false);
              }catch(Exception e){
                  String logoutErrorToastMessage = "Oops.. Something went wrong";
                  Toast.makeText(context, logoutErrorToastMessage, Toast.LENGTH_SHORT).show();
                  return false;
              }
                String logoutSuccessToastMessage = "Logged out Succesfully";
                Toast.makeText(context, logoutSuccessToastMessage, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(UserHomeActivity.this, LoginUserActivity.class);
                startActivity(i);
                finish();
                return true;
        }
        else{
              displaySelectedScreen(item.getItemId());
          }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void displaySelectedScreen(int id) {
//        setHeaderUserName();
        Fragment fragment = null;
        Class fragmentClass = null;

        if (id == R.id.nav_home) {
            fragmentClass = HomeFragment.class;
        }
        if (id == R.id.nav_profile) {
            fragmentClass = ProfileFragment.class;
        } else if (id == R.id.nav_record) {
            fragmentClass = SpeechToTextFragment.class;
        } else if (id == R.id.nav_text) {
            fragmentClass = TextToSpeechFragment.class;
        } else if (id == R.id.nav_manage) {
            fragmentClass = SettingsFragment.class;
        }else if (id == R.id.nav_about) {
            fragmentClass = AboutFragment.class;
        }
        try {
            fragment = (Fragment) fragmentClass.newInstance();
            Bundle bundle = new Bundle();
            bundle.putSerializable("user",user);
            fragment.setArguments(bundle);
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    public void setHeaderUserName(){

        View header=navigationView.getHeaderView(0);
        TextView userName = (TextView)header.findViewById(R.id.userName);
        TextView userEmail = (TextView)header.findViewById(R.id.userEmail);
        user = userService.getUserByUserName(user,context);
        if(user.getFirstName()!= null){
            userName.setText(user.getFirstName() +" "+ user.getSecondName());
        }else{
            userName.setText("Complete Your Profile");
        }
        userEmail.setText(user.getEmail());
    }
}
