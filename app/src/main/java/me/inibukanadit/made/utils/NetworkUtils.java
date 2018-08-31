package me.inibukanadit.made.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

// Inspired by https://github.com/MindorksOpenSource/android-mvp-architecture
public class NetworkUtils {

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

}
