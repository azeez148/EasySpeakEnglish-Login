package com.app.easyspeak.activity;

import android.content.Context;
import android.content.Intent;
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

import com.app.easyspeak.model.User;
import com.app.easyspeak.service.UserHomeService;
import com.app.easyspeak.serviceImpl.SplashServiceImpl;
import com.app.easyspeak.serviceImpl.UserHomeServiceImpl;
import com.app.easyspeak.serviceImpl.UserLoginServiceImpl;
import com.app.easyspeak.splash.SplashScreen;

import javax.inject.Inject;

public class UserHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ProfileFragment.OnFragmentInteractionListener,
        SpeechToTextFragment.OnFragmentInteractionListener,AboutFragment.OnFragmentInteractionListener,TextToSpeechFragment.OnFragmentInteractionListener,
        SettingsFragment.OnFragmentInteractionListener{

    User user=null;
    Context context = null;

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
        context = this.getApplicationContext();
        Intent i = getIntent();
        user = (User)i.getSerializableExtra("user");
        Log.v("user in home activity",user.toString());

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
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        drawer.openDrawer(GravityCompat.START);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        View header=navigationView.getHeaderView(0);
/*View view=navigationView.inflateHeaderView(R.layout.nav_header_main);*/
        TextView userName = (TextView)header.findViewById(R.id.userName);
        TextView userEmail = (TextView)header.findViewById(R.id.userEmail);
        if(user.getFirstName()!= null && !user.getFirstName().equals(null)){
            userName.setText(user.getFirstName() +" "+ user.getSecondName());
        }else{
            userName.setText("Complete Your Profile");
        }

        userEmail.setText(user.getEmail());

        navigationView.getMenu().getItem(0).setChecked(true);

        //add this line to display menu1 when the activity is loaded
       displaySelectedScreen(R.id.nav_profile);
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
        if (id == R.id.action_about) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {


        // Handle navigation view item clicks here.
        int id = item.getItemId();

          if (id == R.id.nav_logout) {
            boolean isUpdated = userService.logoutUser(user,context);
            if(isUpdated){
                Intent i = new Intent(UserHomeActivity.this, LoginUserActivity.class);
                startActivity(i);
                finish();
                return true;
            }else{
                return false;
            }

        }
        else{
              displaySelectedScreen(item.getItemId());
          }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void displaySelectedScreen(int id) {

        Fragment fragment = null;
        Class fragmentClass = null;

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
}
