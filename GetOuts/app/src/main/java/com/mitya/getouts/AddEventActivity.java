package com.mitya.getouts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.RealmResultTask;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;
import io.realm.mongodb.mongo.iterable.MongoCursor;
import io.realm.mongodb.sync.SyncConfiguration;

import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class AddEventActivity extends AppCompatActivity {

    private EditText latEditText;
    private EditText lngEditText;
    private EditText eventNameEditText;
    private EditText timeEditText;
    private Button createButton;
    private Button setLocationButton;

    Realm uiThreadRealm;
    App app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        //Realm.init(this);

        String appID = "get-outs-dsllf";
        app = new App(new AppConfiguration.Builder(appID).build());


        CodecRegistry pojoCodecRegistry = fromRegistries(AppConfiguration.DEFAULT_BSON_CODEC_REGISTRY,
                CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        User user = app.currentUser();
        MongoClient mongoClient = user.getMongoClient("mongodb-atlas");
        MongoDatabase mongoDatabase =
                mongoClient.getDatabase("getoutsmain");


        // Removed Event.class from the second parameter of .getCollection(..., Event.class)
        MongoCollection<Document> mongoCollection =
                mongoDatabase.getCollection(
                        "events").withCodecRegistry(pojoCodecRegistry);

        latEditText = findViewById(R.id.latEditText);
        lngEditText = findViewById(R.id.lngEditText);
        eventNameEditText = findViewById(R.id.eventNameEditText);
        timeEditText = findViewById(R.id.timeEditText);
        createButton = findViewById(R.id.createButton);
        setLocationButton = findViewById(R.id.setLocationButton);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (latEditText.getText().toString().isEmpty()
                        || lngEditText.getText().toString().isEmpty()
                        || eventNameEditText.getText().toString().isEmpty()
                        || timeEditText.getText().toString().isEmpty()) {
                    Toast.makeText(AddEventActivity.this, "Please don't leave empty fields", Toast.LENGTH_LONG).show();
                }
                else {
                    //Get the location of the event
                    double lat = Double.parseDouble(latEditText.getText().toString());
                    double lng = Double.parseDouble(lngEditText.getText().toString());

                    //Add the location to the collection

                    Event event = new Event(new ObjectId(),
                            eventNameEditText.getText().toString(),
                            EventStatus.Created.displayName,
                            user.getId(),
                            lat,
                            lng,
                            new Date(),
                            timeEditText.getText().toString());


                    mongoCollection.insertOne(event.eventToDoc()).getAsync(task -> {
                        if (task.isSuccess()) {
                            Log.v("EXAMPLE", "successfully inserted a document with id: " + task.get().getInsertedId());
                        } else {
                            Log.e("EXAMPLE", "failed to insert documents with: " + task.getError().getErrorMessage());
                        }
                    });

                    G.markers.add(new MarkerInfo(lat, lng, eventNameEditText.getText().toString(), timeEditText.getText().toString()));

                    Intent mapsActivityIntent = new Intent(AddEventActivity.this, MapsActivity.class);

                    startActivity(mapsActivityIntent);
                }
            }
        });

        setLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                latEditText.setText("42.455414");
                lngEditText.setText("-83.057074");
            }
        });


    }
}