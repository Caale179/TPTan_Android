package fr.nantes.iut.tptan.presentation;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;

import com.google.android.gms.location.LocationRequest;

import java.util.ArrayList;
import java.util.List;

import fr.nantes.iut.tptan.BuildConfig;
import fr.nantes.iut.tptan.R;
import fr.nantes.iut.tptan.data.entity.ListArret;
import fr.nantes.iut.tptan.data.repository.tan.OpenDataTanRepo;

public class HomeActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 1234;

    private TabsAdapter mTabsAdapter;

    private boolean mRequestingLocationUpdates = false;

    private LocationRequest mLocationRequest;

    private List<ProximityStopListener> mProximityStopListeners = new ArrayList<>();

    private boolean mAskingPermission = false;

    private Location mCurrentLocation ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ViewPager viewPager = this.findViewById(R.id.home_viewpager);

        mTabsAdapter = new TabsAdapter(this, this.getSupportFragmentManager());
        viewPager.setAdapter(mTabsAdapter);

        TabLayout tabLayout = findViewById(R.id.home_tablayout);
        TabLayout.Tab listTab = tabLayout.newTab();
        listTab.setText("Liste");
        TabLayout.Tab mapTab = tabLayout.newTab();
        mapTab.setText("Map");
        tabLayout.addTab(listTab, 0);
        tabLayout.addTab(mapTab, 1);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {  // setOnTabSelectedListener
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        this.initLocationService();
    }

    /**
     * Init location service and locationRequest object.
     */
    private void initLocationService() {

        //TODO T202: Initialise le provider de localisation et son callback
        //TODO T203: Instancier et configurer la variable mLocationRequest.

    }

    @Override
    public void onResume() {
        super.onResume();
        startLocationUpdatesWithPermissionCheck();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    @SuppressWarnings({"MissingPermission"})
    private void startLocationUpdatesWithPermissionCheck() {

        if (!mAskingPermission) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                mAskingPermission = true;
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                startLocationUpdates();
            }
        }
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startLocationUpdates();
                }
                return;
            }
        }
        mAskingPermission = false;
    }

    @SuppressWarnings({"MissingPermission"})
    private void startLocationUpdates() {
        if (!mRequestingLocationUpdates) {
            mRequestingLocationUpdates = true;

            //TODO T204
        }
    }

    public void onLocationChanged(Location location) {
        ProximityStopAsyncTask proximityStopAsyncTask = new ProximityStopAsyncTask();
        proximityStopAsyncTask.execute(location);
    }

    /**
     * Unregister for location updates
     */
    private void stopLocationUpdates() {
        //TODO T205
        mRequestingLocationUpdates = false;
    }

    public void registerProximityListener( ProximityStopListener proximityStopListener ) {
        this.mProximityStopListeners.add(proximityStopListener);
    }

    public void unregisterProximityStopListener( ProximityStopListener proximityStopListener ) {
        this.mProximityStopListeners.remove(proximityStopListener);
    }

    private void notifyListeners( ListArret arrets, Location location ) {
        for( ProximityStopListener proximityStopListener : mProximityStopListeners ) {
            proximityStopListener.onProximityStopChanged( arrets, location );
        }
    }

    public interface ProximityStopListener {

        void onProximityStopChanged( ListArret arrets, Location location );
    }

    /**
     * AsyncTask.
     */
    public class ProximityStopAsyncTask extends AsyncTask<Location,Void,ListArret> {

        private Location mLocation;

        @Override
        protected ListArret doInBackground(Location... location) {
            ListArret listArrets = null ;
            try {
                this.mLocation = location[0];

                // Query tan webservice to retrieve near stops.
                OpenDataTanRepo openDataTanRepo = new OpenDataTanRepo();
                listArrets = openDataTanRepo.getListArretProche(
                        location[0].getLatitude(), location[0].getLongitude(),
                        HomeActivity.this.getApplicationContext());

                //TODO T102

            } catch( Exception exception ) {
                Log.e(BuildConfig.LOG_TAG, "Error", exception);
            }
            return listArrets ;
        }

        @Override
        protected void onPostExecute(ListArret listArrets) {
            HomeActivity.this.notifyListeners( listArrets, this.mLocation);
        }
    }
}
