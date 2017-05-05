//package com.example.indianic.baseproject.common;
//
//import android.location.Location;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//
//import com.dyrct.app.DyrctApplication;
//import com.google.android.gms.analytics.HitBuilders;
//import com.google.android.gms.analytics.Tracker;
//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.common.api.PendingResult;
//import com.google.android.gms.common.api.Status;
//import com.google.android.gms.location.LocationRequest;
//import com.google.android.gms.location.LocationServices;
//
//import java.text.DateFormat;
//import java.util.Date;
//
///****************************************************************************
// * @ClassdName:BaseActivity
// * @CreatedDate:
// * @ModifiedBy: not yet
// * @ModifiedDate: not yet
// * @purpose:This Class is BaseActivity.
// ***************************************************************************/
//
//public abstract class ReferenceBaseActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {
//
//    private DyrctApplication dyrctApplication;
//    private static final long INTERVAL = 1000 * 10;
//    private static final long FASTEST_INTERVAL = 1000 * 5;
//
//    private LocationRequest mLocationRequest;
//    private GoogleApiClient mGoogleApiClient;
//    private Tracker mTracker;
//
//    private Location mCurrentLocation;
//    private String CurrentLocation;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        //  setContentView(R.layout.activity_base);
//        createLocationRequest();
//        mGoogleApiClient = new GoogleApiClient.Builder(BaseActivity.this).addApi(LocationServices.API).addConnectionCallbacks(this).addOnConnectionFailedListener(this).build();
//        dyrctApplication = (DyrctApplication) this.getApplication();
//        mTracker = dyrctApplication.getDefaultTracker();
//
//    }
//
//    @Override
//    protected void onDestroy() {
//
//        super.onDestroy();
//            }
//
//    protected void createLocationRequest() {
//        mLocationRequest = new LocationRequest();
//        mLocationRequest.setInterval(INTERVAL);
//        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
//        // mLocationRequest.setNumUpdates(2);
//        // mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//
//    }
//
//    @Override
//    public void onConnected(Bundle bundle)
//    {
//        startLocationUpdates();
//    }
//
//    protected void startLocationUpdates()
//    {
//        PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, BaseActivity.this);
//
//        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
//
//        if (mLastLocation != null)
//        {
//            double latitude = mLastLocation.getLatitude();
//            double longitude = mLastLocation.getLongitude();
//
//            setCurrentLocation(String.valueOf(latitude + "," + longitude));
//            setmCurrentLocation(mLastLocation);
//
//        }
//
//    }
//
//
//
//    @Override
//    public void onConnectionSuspended(int i) {
//    }
//
//    @Override
//    public void onConnectionFailed(ConnectionResult connectionResult) {
//    }
//
//    @Override
//    public void onLocationChanged(Location location) {
//
//        mCurrentLocation = location;
//        DateFormat.getTimeInstance().format(new Date());
//        updateUI();
//    }
//
//    private void updateUI() {
//
//        if (null != mCurrentLocation) {
//            Double lat = mCurrentLocation.getLatitude();
//            Double lng = mCurrentLocation.getLongitude();
//            setCurrentLocation(String.valueOf(lat + "," + lng));
//            setmCurrentLocation(mCurrentLocation);
//        }
//
//    }
//
//
//    protected void stopLocationUpdates() {
//        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, (com.google.android.gms.location.LocationListener) this);
//    }
//
//    public String getCurrentLocation() {
//        return CurrentLocation;
//    }
//
//    public void setCurrentLocation(String currentLocation) {
//        CurrentLocation = currentLocation;
//    }
//
//    public Location getmCurrentLocation() {
//        return mCurrentLocation;
//    }
//
//    public void setmCurrentLocation(Location mCurrentLocation) {
//        this.mCurrentLocation = mCurrentLocation;
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//
//        if (mGoogleApiClient.isConnected()) {
//            startLocationUpdates();
//        }
//
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        mGoogleApiClient.connect();
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//
//        if (mGoogleApiClient.isConnected()) {
//            stopLocationUpdates();
//        }
//
//    }
//    public void sendActionEventsTracker(String category, String action, String label) {
//        mTracker.send(new HitBuilders.EventBuilder()
//                .setCategory("" + category)
//                .setLabel("" + label)
//                .setAction("" + action)
//                .build());
//    }
//
//
//    public void sendActionScreenTracker(String screenName)
//    {
//        mTracker.setScreenName(screenName);
//        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
//    }
//
//}
