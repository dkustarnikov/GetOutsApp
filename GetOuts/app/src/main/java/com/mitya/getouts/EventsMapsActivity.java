package com.mitya.getouts;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class EventsMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

        Log.d("TAG", "Entered OnMapReady") ;

        G.markers.add(new MarkerInfo(42.725025, -83.457601, "Test1, Watterford area", "2pm"));
        G.markers.add(new MarkerInfo(42.054210, -85.047432, "Test1.1, Somewhere", "3pm"));
        G.markers.add(new MarkerInfo(42.257015, -83.460116, "Test2, Dom's coords", "9pm"));
        for (int i = 0; i < G.markers.size(); i++) {
            LatLng coords = new LatLng(G.markers.get(i).getLat(), G.markers.get(i).getLng());

            mMap.addMarker(new MarkerOptions().position(coords).title(G.markers.get(i).getName() + " @" + (G.markers.get(i).getTime())));
        }
    }
}