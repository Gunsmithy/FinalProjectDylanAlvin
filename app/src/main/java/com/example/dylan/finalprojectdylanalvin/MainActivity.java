package com.example.dylan.finalprojectdylanalvin;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import com.google.android.youtube.player.YouTubeApiServiceUtil;
import com.google.android.youtube.player.YouTubeInitializationResult;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        CountdownFragment.OnFragmentInteractionListener,
        FinderFragment.OnFragmentInteractionListener,
        TwitterFragment.OnFragmentInteractionListener,
        YouTubeFragment.OnFragmentInteractionListener,
        SettingsFragment.OnFragmentInteractionListener,
        FeedbackFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        final YouTubeInitializationResult result = YouTubeApiServiceUtil.isYouTubeApiServiceAvailable(this);

        if (result != YouTubeInitializationResult.SUCCESS) {
            result.getErrorDialog(this,0).show();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        displayView(R.id.nav_countdown);
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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public final class Config
    {
        private Config()
        {
            final String YOUTUBE_API_KEY=" AIzaSyDr-JHyYE1xGBlkxgbJz0hBHqu5l7swx80";
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //TODO: Make settings button highlight settings option in nav drawer
        if (id == R.id.action_settings) {
            displayView(R.id.nav_settings);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        displayView(item.getItemId());
        return true;
    }

    public void displayView(int viewId) {

        Fragment fragment = null;
        String title = getString(R.string.app_name);

        switch (viewId) {
            case R.id.nav_countdown:
                fragment = new CountdownFragment();
                title  = getString(R.string.countdownTimer);
                break;
            case R.id.nav_finder:
                fragment = new FinderFragment();
                title = getString(R.string.theatreFinder);
                break;
            case R.id.nav_twitter:
                fragment = new TwitterFragment();
                title = getString(R.string.twitterFeed);
                break;
            case R.id.nav_youtube:
                fragment = new YouTubeFragment();
                title = getString(R.string.youTubeFeed);
                break;
            case R.id.nav_settings:
                fragment = new SettingsFragment();
                title = getString(R.string.settings);
                break;
            case R.id.nav_feedback:
                fragment = new FeedbackFragment();
                title = getString(R.string.feedback);
                break;
        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        // set the toolbar title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public void onCountdownFragmentInteraction(String string) {
        // Do stuff
    }

    @Override
    public void onFinderFragmentInteraction(String string) {
        // Do stuff
    }

    @Override
    public void onTwitterFragmentInteraction(String string) {
        // Do stuff
    }

    @Override
    public void onYouTubeFragmentInteraction(String string) {
        // Do stuff
    }

    @Override
    public void onSettingsFragmentInteraction(String string) {
        // Do stuff
    }

    @Override
    public void onFeedbackFragmentInteraction(String string) {
        // Do stuff
    }
}
