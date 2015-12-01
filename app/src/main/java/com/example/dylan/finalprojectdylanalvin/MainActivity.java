package com.example.dylan.finalprojectdylanalvin;

//By Dylan, some additions by Alvin

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.android.gms.maps.model.MarkerOptions;

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
        YouTubeFragment.OnFragmentInteractionListener,
        SettingsFragment.OnFragmentInteractionListener,
        ConnectionCallbacks, OnConnectionFailedListener,
        OnMapReadyCallback, PlacesListener {

    private GoogleMap mMap;

    //Radius in meters to search for theatres
    private int radiusSetting;

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

        //Load the saved app state from preferences, if none, default to 0
        String name = "myAppPrefs";
        SharedPreferences prefs = getSharedPreferences(name, Activity.MODE_PRIVATE);
        radiusSetting = prefs.getInt("radiusSetting", 10000);

        //Load the navigation drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Countdown is the default view
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

    //When settings is pressed in options menu, load settings
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

    //When a nav tray item is pressed, call display to figure out which one
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

        //Load the fragment which was pressed
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
            case R.id.nav_youtube:
                fragment = new YouTubeFragment();
                title = getString(R.string.youTubeFeed);
                break;
            case R.id.nav_trivia:
                fragment = new TriviaFragment();
                title = getString(R.string.triviapage);
                break;
            case R.id.nav_settings:
                fragment = new SettingsFragment();
                SettingsFragment sFragment = (SettingsFragment) fragment;
                sFragment.setmListener(this);
                sFragment.setRadius(radiusSetting);
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

        //As long as the fragment isn't null, replace the placeholder one with one selected
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment, title);
            ft.commit();
        }
        //If the fragment is null it could have been the map selected, if it was, load that
        else {
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

        //Close the drawer when done
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public void onYouTubeFragmentInteraction(String string) {
        // Do stuff
    }

    //When the settings fragment stops, update the radius setting
    @Override
    public void onSettingsFragmentInteraction(String string) {
        // Do stuff
        radiusSetting = Integer.valueOf(string);
        String name = "myAppPrefs";
        SharedPreferences prefs = getSharedPreferences(name, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("radiusSetting", radiusSetting);
        editor.apply();
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    //When map is ready, set the map object, load the current location marker, and configure the button
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMyLocationEnabled(true);

        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                updateLastLocation();
                return true;
            }
        });

        mGoogleApiClient.connect();
    }

    //Checks for the devices last known location
    public void updateLastLocation(){
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            //Toast.makeText(this, String.valueOf(mLastLocation.getLatitude()) + String.valueOf(mLastLocation.getLongitude()), Toast.LENGTH_LONG).show();
            LatLng ll = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, 14);
            mMap.animateCamera(update);
            String url = baseURL + "location=" + String.valueOf(mLastLocation.getLatitude()) + "," + String.valueOf(mLastLocation.getLongitude()) + "&radius=" + String.valueOf(radiusSetting) +"&types=movie_theater&key=" + getString(R.string.google_web_key);
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

    //Clears the map of theatre locations and displays the new set, notifying of the result
    public void showPlaces(String[] places, String[] addresses, double[] lats, double[] longs) {
        Log.i(TAG, "THESE PLACES: " + Arrays.toString(places));
        Log.i(TAG, "THESE ADDRESSES: " + Arrays.toString(addresses));
        Log.i(TAG, "THESE LATS: " + Arrays.toString(lats));
        Log.i(TAG, "THESE LONGS: " + Arrays.toString(longs));

        if (places.length > 0){
            Toast.makeText(this, R.string.placesFound, Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, R.string.noPlacesFound, Toast.LENGTH_LONG).show();
        }

        mMap.clear();
        for (int i = 0; i < places.length; i++){
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(lats[i],longs[i]))
                    .title(places[i])
                    .snippet(addresses[i]));
        }
    }
}
