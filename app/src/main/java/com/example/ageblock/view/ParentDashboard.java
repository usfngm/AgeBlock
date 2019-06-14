package com.example.ageblock.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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

public class ParentDashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView usernameTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_dashboard);
        registerComponents();

        usernameTV.setText(User.getLoggedUser(this).getName());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.parent_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.elder_nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.getMenu().getItem(0).setChecked(true);
        displaySelectedScreen(R.id.parent_menu_requests);
    }

    private void registerComponents() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.elder_nav_view);
        usernameTV = (TextView) navigationView.getHeaderView(0).findViewById(R.id.parent_userNameTV);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.parent_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.parent_dashboard, menu);
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
        //calling the method displayselectedscreen and passing the id of selected menu
        displaySelectedScreen(item.getItemId());

        return true;
    }

    private void displaySelectedScreen(int id) {
        //creating fragment object
        Fragment fragment = null;

        if (id == R.id.parent_menu_requests) {
            fragment = new ParentRequestsFragment();
            getSupportActionBar().setTitle("Pending Requests");
        } else if (id == R.id.parent_menu_history) {
            fragment = new ParentHistoryFragment();
            getSupportActionBar().setTitle("Historical Requests");
        } else if (id == R.id.parent_menu_current) {
            fragment = new ParentCurrentFragment();
            getSupportActionBar().setTitle("Current Requests");
        } else if (id == R.id.parent_menu_profile) {

        } else if (id == R.id.parent_menu_elders) {
            fragment = new ParentEldersFragment();
            getSupportActionBar().setTitle("Elders");
        } else if (id == R.id.parent_menu_logout) {
            logout();
        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.parent_content_frame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.parent_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    private void logout() {
        YesNoAD.get().init(this, "Are you sure you want to logout?", new BtnPress() {
            @Override
            public void yes() {
                SharedPreferences sp = getSharedPreferences("app", 0);
                SharedPreferences.Editor editor = sp.edit();
                editor.clear();
                editor.commit();

                Intent i = new Intent(ParentDashboard.this, LoginActivity.class);
                startActivity(i);
                finish();
            }

            @Override
            public void no() {

            }
        });
    }
}
