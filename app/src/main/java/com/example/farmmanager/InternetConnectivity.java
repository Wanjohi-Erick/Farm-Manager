package com.example.farmmanager;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class InternetConnectivity {
    public boolean isConnected(Context context){
        ConnectivityManager connectivityManager;
        connectivityManager = (ConnectivityManager)  context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()){
            android.net.NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            android.net.NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

            return (mobile != null && mobile.isConnectedOrConnecting()) || wifi != null && wifi.isConnectedOrConnecting();
        }else {
            return false;
        }

    }
}
