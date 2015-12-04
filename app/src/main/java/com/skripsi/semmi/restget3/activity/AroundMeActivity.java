package com.skripsi.semmi.restget3.activity;

import android.content.Intent;
import android.content.IntentSender;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.skripsi.semmi.restget3.Interface.SaveUserLocationInterface;
import com.skripsi.semmi.restget3.Interface.ShowAllUserLocationInterface;
import com.skripsi.semmi.restget3.Interface.UserProfileFromAroundMe;
import com.skripsi.semmi.restget3.Model.AllUser;
import com.skripsi.semmi.restget3.Model.SaveUserLocation;
import com.skripsi.semmi.restget3.Model.ShowAllUserLocation;
import com.skripsi.semmi.restget3.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * Created by semmi on 19/10/2015.
 */
public class AroundMeActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener, View.OnClickListener {

    // Variabel ini buat berinteraksi dengan google play services
    private GoogleApiClient mGoogleApiClient;
    private GoogleMap mMap;
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private LocationRequest mLocationRequest;
    private double currentLatitude;
    private  double currentLongitude;
    public static final String  username="";
    private String usernameFromHome;
    private CoordinatorLayout coordinatorLayout;
    private EditText searchLocation;
    private Button searchLocationButton;

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
        if(getIntent()!= null && getIntent().getExtras()!=null){
            if(getIntent().getExtras().containsKey(username)){
                usernameFromHome=getIntent().getExtras().getString(username);
            }
        }

        coordinatorLayout= (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        searchLocation = (EditText) findViewById(R.id.searchLocation);
        searchLocationButton = (Button) findViewById(R.id.searchLocationButton);
        searchLocationButton.setOnClickListener(this);
    }
    // fungsi untuk ambil lokasi High accuracy
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
        updateDataLocation();
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
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
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
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            updateDataLocation();
            putAllUserMarker();

        }else{
            connnectTOMap(location);
            updateDataLocation();
            putAllUserMarker();
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



       // mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        // set camera pake zoom

        // kaya kalau setiap connect bakal set zoom terus kayak pas lagi di pinch out
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
    }

    private void putAllUserMarker() {
        // tampilin semua user lokasi di map yang ada
        RestAdapter restAdapter=new RestAdapter.Builder()
                .setEndpoint(getString(R.string.api))
                .build();
         ShowAllUserLocationInterface showAllUserLocation=restAdapter.create(ShowAllUserLocationInterface.class);
        showAllUserLocation.getLocation(new Callback<List<ShowAllUserLocation>>() {
            @Override
            public void success(List<ShowAllUserLocation> showAllUserLocations, Response response) {
                List<Marker> markers = new ArrayList<Marker>();
                for (ShowAllUserLocation showAllUserLocation1 : showAllUserLocations) {
                    // muncul di map kalau lokasi nya ga 0 atau null
                    if (!usernameFromHome.equals(showAllUserLocation1.getUsername()) && showAllUserLocation1.getLatitude() != 0 && showAllUserLocation1.getLongitude() != 0) {
                        MarkerOptions options = new MarkerOptions()
                                .position(new LatLng(showAllUserLocation1.getLatitude(), showAllUserLocation1.getLongitude()))
                                .title(showAllUserLocation1.getUsername())
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_user));

                        Marker marker = mMap.addMarker(options);
                        markers.add(marker);
                    }
                    mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(Marker marker) {
                            //  Start snackbar with the detail of user
                            // Show user name and image when snackbar shown
                            // TODO Right now only show the username and the action to go to their representative profile
                            final String usernameMarker = marker.getTitle();
                            Snackbar snackbar = Snackbar.make(coordinatorLayout, marker.getTitle(), Snackbar.LENGTH_INDEFINITE)
                                    .setAction("Profile", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {


                                            RestAdapter restAdapter = new RestAdapter.Builder()
                                                    .setEndpoint(getString(R.string.api))
                                                    .build();
                                            UserProfileFromAroundMe upfa = restAdapter.create(UserProfileFromAroundMe.class);
                                            upfa.fetchUser(usernameMarker, new Callback<AllUser>() {
                                                @Override
                                                public void success(AllUser allUser, Response response) {
                                                    //  start intent to user profile here
                                                    Log.d("retrofit", allUser.getUsername());
                                                    Intent userProfileIntent = new Intent(AroundMeActivity.this, UserProfileFromAroundMeActivity.class);
                                                    userProfileIntent.putExtra(UserProfileFromAroundMeActivity.extraUsername, allUser.getUsername());
                                                    userProfileIntent.putExtra(UserProfileFromAroundMeActivity.extraImage, allUser.getImage());
                                                    userProfileIntent.putExtra(UserProfileFromAroundMeActivity.extraStatus, allUser.getStatus());
                                                    userProfileIntent.putExtra("IDValue", allUser.getId());
                                                    startActivity(userProfileIntent);

                                                }

                                                @Override
                                                public void failure(RetrofitError error) {
                                                    Log.d("marker Error", error.getMessage());
                                                }
                                            });
                                        }
                                    });
                            snackbar.show();
//                            Log.d("marker",marker.getTitle());
                            return true;
                        }
                    });
                    Log.d("get Marker", "berhasil GET");

                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("get Error", "from Retrofit" + error.getMessage());
            }
        });
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
        updateDataLocation();
        putAllUserMarker();
    }

    private void updateDataLocation() {
        // fungsi ketika user pindah lokasi data lokasi user (long dan lat) di database akan dipindahkan
        RestAdapter restAdapter=new RestAdapter.Builder()
                .setEndpoint(getString(R.string.api))
                .build();
        SaveUserLocationInterface saveUserLocationInterface=restAdapter.create(SaveUserLocationInterface.class);
        saveUserLocationInterface.saveLocation(currentLatitude, currentLongitude, usernameFromHome, new Callback<SaveUserLocation>() {
            @Override
            public void success(SaveUserLocation saveUserLocation, Response response) {
                Log.d("save", saveUserLocation.getKode());
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("post Error", "from Retrofit" + error.getMessage());
            }
        });
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.searchLocationButton:
                 String searchResult = searchLocation.getText().toString();
                try {
                    findSearchRequest(searchResult);
                    hideKeyboard(v);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void findSearchRequest(String searchResult) throws IOException {
        if(searchResult.equals("")){
            Toast.makeText(AroundMeActivity.this, "Null Search", Toast.LENGTH_SHORT).show();
            return;
        }

        Geocoder geocoder = new Geocoder(this);
        List<Address> list = geocoder.getFromLocationName(searchResult, 1);

        if(list.size() >0){
            Address address = list.get(0);
//            String locality = address.getLocality();
            double lat = address.getLatitude();
            double lng = address.getLongitude();
            changeMapFromSearch(lat,lng,15);
        }

    }

    private void hideKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(),0);
    }

    private void changeMapFromSearch(double lat, double lng, float zoom) {
        LatLng latLng = new LatLng(lat,lng);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng,zoom);
        mMap.moveCamera(cameraUpdate);
    }
}
