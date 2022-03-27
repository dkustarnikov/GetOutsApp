package com.mitya.getouts;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.FragmentActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Document;

import java.lang.annotation.Documented;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoDatabase;

import static com.mitya.getouts.LoginActivity.app;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String CHANNEL_ID = "penis";
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("TAG", "Entered Oncreate") ;
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        String appID = "get-outs-dsllf";
        App app = new App(new AppConfiguration.Builder(appID).build());
        User user = app.currentUser();
        MongoClient mongoClient = user.getMongoClient("mongodb-atlas");
        MongoDatabase mongoDatabase =
                mongoClient.getDatabase("getoutsmain");


        //Wait for 1 second
        //Send a notification

//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
//                .setContentTitle("Dominic Steel")
//                .setContentText("Hey, I am interested. How should I dress?")
//                .setSmallIcon(R.drawable.ic_launcher_foreground)
//                .setPriority(NotificationCompat.PRIORITY_MAX);
//
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

// notificationId is a unique int for each notification that you must define
//        notificationManager.notify(1, builder.build());

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
        //Getting events from the collection

        RealmConfiguration config = new RealmConfiguration.Builder()
                .allowQueriesOnUiThread(true)
                .allowWritesOnUiThread(true)
                .build();
        Realm realm = Realm.getInstance(config);

        //TODO: Test this
        RealmQuery<Event> eventsQuery = realm.where(Event.class);

        //First I update events to "Mapped"
        RealmResults<Event> createdResults = eventsQuery.equalTo("status", EventStatus.Created.displayName).findAll();

        for (Event e: createdResults) {
            e.setStatus(EventStatus.Mapped.displayName);
        }

        //Then it gets showed on the map
        RealmResults<Event> mappedResults = eventsQuery.equalTo("status", EventStatus.Mapped.displayName).findAll();

        for (Event e: mappedResults) {
            G.markers.add(
                    new MarkerInfo(
                            e.getLatitude(),
                            e.getLongitude(),
                            e.getName(),
                            e.getEvent_date()));
        }

        //TODO: Test stuff above^^^
//        G.markers.add(new MarkerInfo(42.725025, -83.457601, "Test1, Watterford area", "2pm"));
//        G.markers.add(new MarkerInfo(42.054210, -85.047432, "Test1.1, Somewhere", "3pm"));
//        G.markers.add(new MarkerInfo(42.257015, -83.460116, "Test2, Dom's coords", "9pm"));
        for (int i = 0; i < G.markers.size(); i++) {
            LatLng coords = new LatLng(G.markers.get(i).getLat(), G.markers.get(i).getLng());

            mMap.addMarker(new MarkerOptions().position(coords).title(G.markers.get(i).getName() + " @" + (G.markers.get(i).getTime())));
        }
    }
}