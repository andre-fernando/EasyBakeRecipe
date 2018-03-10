package com.andre_fernando.easybakerecipes.utils;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/** This allows us to check if the network is connected, reference link:
    https://stackoverflow.com/questions/15866035/android-show-a-message-if-no-internet-connection-and-continue-to-check

    Make sure to add the following permission in the manifest
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" /> */

public class NetworkCheck {

    public static boolean isConnected(Context context){
        NetworkInfo activenetworkinfo = ((ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return activenetworkinfo!=null && activenetworkinfo.isConnected();
    }

}
