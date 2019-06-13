package com.example.ageblock.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.ageblock.R;
import com.example.ageblock.model.User;
import com.example.ageblock.view.utils.BtnPress;
import com.example.ageblock.view.utils.YesNoAD;

public class VolunteerDashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView usernameTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        registerComponents();

        usernameTV.setText(User.getLoggedUser(this).getName());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.vol_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.elder_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void registerComponents() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.elder_nav_view);
        usernameTV = (TextView) navigationView.getHeaderView(0).findViewById(R.id.volunteer_usernameTV);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.vol_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.volunteer_dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.vol_menu_requests) {

        } else if (id == R.id.vol_menu_history) {

        } else if (id == R.id.vol_menu_current) {

        } else if (id == R.id.vol_menu_profile) {

        } else if (id == R.id.vol_menu_logout) {
            logout();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.vol_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logout()
    {
        YesNoAD.get().init(this, "Are you sure you want to logout?", new BtnPress() {
            @Override
            public void yes() {
                SharedPreferences sp = getSharedPreferences("app", 0);
                SharedPreferences.Editor editor = sp.edit();
                editor.clear();
                editor.commit();

                Intent i = new Intent(VolunteerDashboard.this, LoginActivity.class);
                startActivity(i);
                finish();
            }

            @Override
            public void no() {

            }
        });
    }
}
