package com.example.farmmanager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class LauncherActivity extends AppCompatActivity {
    private static final String TAG = "LauncherActivity";
    public AdView adView;
    public AdRequest adRequest;
    public String username, farmName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        username = bundle.getString("firstName");
        farmName = bundle.getString("farmName");
        Log.i(TAG, "onCreate: " + username);


        setContentView(R.layout.activity_launcher);
        Objects.requireNonNull(getSupportActionBar()).hide();
        MobileAds.initialize(this, initializationStatus -> {

        });
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        List<String> testDeviceIds = Collections.singletonList("439D669505DCB584952F323881EC8CD2");
        RequestConfiguration configuration =
                new RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build();
        MobileAds.setRequestConfiguration(configuration);

        adView = findViewById(R.id.adView);
        adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_resources, R.id.navigation_reports, R.id.navigation_plans)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_launcher);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    public void toNewsActivity(View view) {
        startActivity(new Intent(this, News.class));
    }

    public void toNavDrawer(View view) {
        Intent intent = new Intent(this, NavigationDrawerActivity.class);
        startActivity(intent);
    }
}