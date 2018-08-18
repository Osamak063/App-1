package com.ziaetaiba.islamicwebsite.components;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.view.View;

import com.ziaetaiba.islamicwebsite.R;

public class ActivityAnimManager {

    public static void startActivityWithAnimation(Activity activity, Intent intent, int requestCode, View view) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {

            ActivityOptions options = ActivityOptions.makeScaleUpAnimation(view,
                    view.getWidth() / 2, view.getHeight() / 2, 0, 0);

            if (requestCode > 0) {
                activity.startActivityForResult(intent, requestCode, options.toBundle());
            } else {
                activity.startActivity(intent, options.toBundle());
            }

        } else {

            if (requestCode > 0) {
                activity.startActivityForResult(intent, requestCode);
            } else {
                activity.startActivity(intent);
            }
        }
    }

    public static void startLeftActivity(Activity activity, Intent intent, int requestCode) {

        if (requestCode > 0) {
            activity.startActivityForResult(intent, requestCode);
        } else {
            activity.startActivity(intent);
        }

        activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }

    public static void startRightActivity(Activity activity, Intent intent, int requestCode) {

        if (requestCode > 0) {
            activity.startActivityForResult(intent, requestCode);
        } else {
            activity.startActivity(intent);
        }

        activity.overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_right);
    }
}
