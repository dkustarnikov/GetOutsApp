package com.mitya.getouts;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;


public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButon;

    App app;
    static User user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //MongoDB stuff
        String appID = "get-outs-dsllf"; // replace this with your App ID
        Realm.init(this); // `this` is a Context, typically an Application or Activity
        app = new App(new AppConfiguration.Builder(appID).build());

        emailEditText = findViewById(R.id.emailLoginEditText);
        passwordEditText = findViewById(R.id.passwordLoginEditText);
        loginButon = findViewById(R.id.loginButton);

        emailEditText.setText("dmitryk@umich.edu");
        passwordEditText.setText("1234567890");


        createNotificationChannel();



        loginButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                login(email, password);
                Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(mainIntent);
            }
        });

    }

    private void login(String email, String password) {
        final String TAG = "LOGIN";

        Credentials emailPasswordCredentials = Credentials.emailPassword(email, password);

        app.loginAsync(emailPasswordCredentials, new App.Callback<User>() {
            @Override
            public void onResult(App.Result<User> it) {
                if (it.isSuccess()) {
                    Log.v(TAG, "Successfully authenticated using an email and password.");
                    user = app.currentUser();
                } else {
                    Log.e(TAG, it.getError().toString());
                }
            }
        });


    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("penis", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}
