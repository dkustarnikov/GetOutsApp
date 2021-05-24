package com.mitya.getouts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddEventActivity extends AppCompatActivity {

    private EditText latEditText;
    private EditText lngEditText;
    private EditText eventNameEditText;
    private EditText timeEditText;
    private Button createButton;
    private Button setLocationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        latEditText = findViewById(R.id.latEditText);
        lngEditText = findViewById(R.id.lngEditText);
        eventNameEditText = findViewById(R.id.eventNameEditText);
        timeEditText = findViewById(R.id.timeEditText);
        createButton = findViewById(R.id.createButton);
        setLocationButton = findViewById(R.id.setLocationButton);


        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double lat = Double.parseDouble(latEditText.getText().toString());
                double lng = Double.parseDouble(lngEditText.getText().toString());
                G.markers.add(new MarkerInfo(lat, lng, eventNameEditText.getText().toString(), timeEditText.getText().toString()));

                Intent mapsActivityIntent = new Intent(AddEventActivity.this, MapsActivity.class);

                startActivity(mapsActivityIntent);
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