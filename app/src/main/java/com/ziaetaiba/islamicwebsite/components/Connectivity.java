package com.ziaetaiba.islamicwebsite.components;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Vibrator;

public class Connectivity {

    @SuppressLint("NewApi")
    public static boolean isInternetOn(Context context, boolean isVibrate, boolean isRingtone) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Activity.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();

        if (isConnected) {
            // do nothing...
        } else {

            if (isVibrate) {

                int currentApiVersion = Build.VERSION.SDK_INT;

                if (currentApiVersion >= Build.VERSION_CODES.HONEYCOMB) {

                    Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

                    if (vibrator.hasVibrator()) {
                        vibrator.vibrate(125);
                    }
                } else {
                    // do nothing...
                }
            }
/*
            if (isRingtone) {

                Uri uriPath = RingtoneManager
                        .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                if (uriPath == null) {
                    // do nothing....
                } else {

                    Ringtone ringtone = RingtoneManager.getRingtone(activity, uriPath);
                    ringtone.play();
                }
            }
*/
        }

        return isConnected;
    }

    @SuppressLint("NewApi")
    public static boolean isInternetOn(Activity activity, boolean isVibrate, boolean isRingtone) {

        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Activity.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();

        if (isConnected) {
            // do nothing...
        } else {

            if (isVibrate) {

                int currentApiVersion = Build.VERSION.SDK_INT;

                if (currentApiVersion >= Build.VERSION_CODES.HONEYCOMB) {

                    Vibrator vibrator = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);

                    if (vibrator.hasVibrator()) {
                        vibrator.vibrate(125);
                    }
                } else {
                    // do nothing...
                }
            }
/*
            if (isRingtone) {

                Uri uriPath = RingtoneManager
                        .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                if (uriPath == null) {
                    // do nothing....
                } else {

                    Ringtone ringtone = RingtoneManager.getRingtone(activity, uriPath);
                    ringtone.play();
                }
            }
*/
        }

        return isConnected;
    }
}
