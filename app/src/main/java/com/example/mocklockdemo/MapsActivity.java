package com.example.mocklockdemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Locale;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private Fragment mSelectOnMap    = new SelectOnMapFragment();
    private Fragment mCustomLocation = new CustomLocationFragment();
    private FragmentManager mManager = getSupportFragmentManager();

    private static final String TAG = MapsActivity.class.getSimpleName();

    private static final int REQUEST_LOCATION_PERMISSION = 1;
    public static final float INITIAL_ZOOM = 12f;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    private void setMapLongClick(final GoogleMap map) {
        map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {

                map.addMarker(new MarkerOptions().position(latLng));

            }
        });
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        /*LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));*/
        LatLng home = new LatLng(37.421982, -122.085109);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(home, INITIAL_ZOOM));
        setMapLongClick(mMap);
        enableMyLocation(mMap);
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(-34, 151)));
    }


    private void enableMyLocation(GoogleMap map) {
        if (
                ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
        } else {
            ActivityCompat.requestPermissions(this, new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        }
    }
    public void onZoom(View view){
        if(view.getId() == R.id.zoomout){
        mMap.animateCamera(CameraUpdateFactory.zoomOut());
        }
       if(view.getId() == R.id.zoomin){
            mMap.animateCamera(CameraUpdateFactory.zoomIn());
        }
    }
}
