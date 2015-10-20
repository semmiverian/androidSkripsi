package com.skripsi.semmi.restget3.activity;

import android.content.IntentSender;
import android.location.Location;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.skripsi.semmi.restget3.R;


/**
 * Created by semmi on 19/10/2015.
 */
public class AroundMeActivity extends FragmentActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    // Variabel ini buat berinteraksi dengan google play services
    private GoogleApiClient mGoogleApiClient;
    private GoogleMap mMap;
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private LocationRequest mLocationRequest;
    private double currentLatitude;
    private  double currentLongitude;
    private Location mLocation;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_around_me);
        displayInitialFragment();
        // initialize google Api ditambah beberapa callback
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        requestLocation();
    }

    private void requestLocation() {
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds
    }

    private void displayInitialFragment() {
//        Default Fragment when user open the App
      if(mMap==null){
            SupportMapFragment supportMapFragment =(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mMap=supportMapFragment.getMap();
          mMap.setMyLocationEnabled(true);
      }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            // remove location services ketika pause
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient,this);
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // koneksi ke services ketika resume aplikasi
        mGoogleApiClient.connect();
    }
    @Override
    protected void onPause() {
        super.onPause();
        // Diskonek services ketika pause applikasi
        if (mGoogleApiClient.isConnected()) {
            // remove location services ketika pause
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient,this);
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        // ketika konek services maka kita akan mengambil lokasi terakhir
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

         // Kalau ga ada lokasi terakhir
        if(location==null){
            // Bakal nge define lokasi user sekarang
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest,this);
        }else{

            connnectTOMap(location);

        }
    }

    private void connnectTOMap(Location location) {
        Log.d("map",location.toString());
        Log.d("map",""+location.getLatitude());
        // Ambil lattitude dan longtitude user
        currentLatitude = location.getLatitude();
        currentLongitude = location.getLongitude();
        LatLng latLng = new LatLng(currentLatitude, currentLongitude);
        MarkerOptions options = new MarkerOptions()
                .position(latLng)
                .title("I am here!");
        mMap.addMarker(options);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.moveCamera(CameraUpdateFactory.zoomBy(50f));
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // Handling Error
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            Log.i("Error", "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        // ketika lokasi user berubah isi fungsi disini
        connnectTOMap(location);
    }



}
