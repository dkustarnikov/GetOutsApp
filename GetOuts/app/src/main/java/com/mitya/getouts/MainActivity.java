package com.mitya.getouts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import io.realm.Realm;


public class MainActivity extends AppCompatActivity {

    private TextView addEventTextView;
    private TextView lookAtEventsTextView;
    private TextView messagePeopleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Realm.init(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //MongoDB connection
        

        addEventTextView = findViewById(R.id.addEventTextView);
        lookAtEventsTextView = findViewById(R.id.lookAtEventsTextView);
        messagePeopleTextView = findViewById(R.id.messagePeopleTextView);

        addEventTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddEventActivity.class));
            }
        });

        lookAtEventsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MapsActivity.class));
            }
        });


        lookAtEventsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MessageActivity.class));

            }
        });
    }
}
