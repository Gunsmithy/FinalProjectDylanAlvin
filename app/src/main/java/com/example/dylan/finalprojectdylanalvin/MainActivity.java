package com.example.dylan.finalprojectdylanalvin;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.youtube.player.YouTubeApiServiceUtil;
import com.google.android.youtube.player.YouTubeInitializationResult;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;

import java.util.Arrays;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        CountdownFragment.OnFragmentInteractionListener,
        TwitterFragment.OnFragmentInteractionListener,
        YouTubeFragment.OnFragmentInteractionListener,
        SettingsFragment.OnFragmentInteractionListener,
        ConnectionCallbacks, OnConnectionFailedListener,
        OnMapReadyCallback, PlacesListener {

    private GoogleMap mMap;

    protected static final String TAG = "MainActivity";

    /**
     * Provides the entry point to Google Play services.
     */
    protected GoogleApiClient mGoogleApiClient;

    /**
     * Represents a geographical location.
     */
    protected Location mLastLocation;

    private String baseURL = "https://maps.googleapis.com/maps/api/place/nearbysearch/xml?";

    private final Handler h = new Handler();
    private final int delay = 500; //milliseconds
    //Runnable to check if the map fragment is loaded to avoid null pointer exception
    private Runnable mapReadyCheck = new Runnable() {
        public void run() {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentByTag(getString(R.string.theatreFinder));
            if (mapFragment != null) {
                mapFragment.getMapAsync(MainActivity.this);
                h.removeCallbacks(mapReadyCheck);
            }
            h.postDelayed(this, delay);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        final YouTubeInitializationResult result = YouTubeApiServiceUtil.isYouTubeApiServiceAvailable(this);

        if (result != YouTubeInitializationResult.SUCCESS) {
            result.getErrorDialog(this, 0).show();
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

    public final class Config {
        private Config() {
            final String YOUTUBE_API_KEY = " AIzaSyDr-JHyYE1xGBlkxgbJz0hBHqu5l7swx80";
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
        SupportMapFragment mMapFragment = null;
        String title = getString(R.string.app_name);

        switch (viewId) {
            case R.id.nav_countdown:
                fragment = new CountdownFragment();
                title = getString(R.string.countdownTimer);
                break;
            case R.id.nav_finder:
                fragment = null;
                mMapFragment = SupportMapFragment.newInstance();
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
                Intent Email = new Intent(Intent.ACTION_SEND);
                Email.setType("text/email");
                Email.putExtra(Intent.EXTRA_EMAIL, new String[]{getString(R.string.dylanEmail)});
                Email.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.emailSubject));
                Email.putExtra(Intent.EXTRA_TEXT, getString(R.string.emailBody) + "\n\n");
                startActivity(Intent.createChooser(Email, getString(R.string.feedback)));
                break;
        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment, title);
            ft.commit();
        } else {
            if (mMapFragment != null) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, mMapFragment, title);
                ft.commit();
                h.postDelayed(mapReadyCheck, delay);
                buildGoogleApiClient();
            }
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

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMyLocationEnabled(true);

        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                updateLastLocation();
                Log.e("Inside", "Click part");
                LatLng ll = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
                CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, 14);
                mMap.animateCamera(update);
                return true;
            }
        });

        mGoogleApiClient.connect();
    }

    public void updateLastLocation(){
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            Toast.makeText(this, String.valueOf(mLastLocation.getLatitude()) + String.valueOf(mLastLocation.getLongitude()), Toast.LENGTH_LONG).show();
            String url = baseURL + "location=" + String.valueOf(mLastLocation.getLatitude()) + "," + String.valueOf(mLastLocation.getLongitude()) + "&radius=10000&types=movie_theater&key=" + getString(R.string.google_web_key);
            DownloadPlacesTask downloadPlacesTask = new DownloadPlacesTask(this);
            Log.d("FinalProject", "running task: " + url);
            downloadPlacesTask.execute(url);
        } else {
            Toast.makeText(this, R.string.no_location_detected, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Runs when a GoogleApiClient object successfully connects.
     */
    @Override
    public void onConnected(Bundle connectionHint) {
        // Provides a simple way of getting a device's location and is well suited for
        // applications that do not require a fine-grained location and that do not need location
        // updates. Gets the best and most recent location currently available, which may be null
        // in rare cases when a location is not available.
        updateLastLocation();
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
        // onConnectionFailed.
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }


    @Override
    public void onConnectionSuspended(int cause) {
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }

    public void showPlaces(String[] places, String[] addresses, double[] lats, double[] longs) {
        Log.i(TAG, "THESE PLACES: " + Arrays.toString(places));
        Log.i(TAG, "THESE ADDRESSES: " + Arrays.toString(addresses));
        Log.i(TAG, "THESE LATS: " + Arrays.toString(lats));
        Log.i(TAG, "THESE LONGS: " + Arrays.toString(longs));
    }
}
