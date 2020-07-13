package com.uas.restaurantsearch.models;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Utility {

    public static boolean isInternetConnected(Context context)
    {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return (activeNetwork != null && activeNetwork.isConnected());
    }

    public static boolean isValidStr(String s)
    {
        return s != null && s.length() > 0;
    }
}
