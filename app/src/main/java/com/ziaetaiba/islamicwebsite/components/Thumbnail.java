package com.ziaetaiba.islamicwebsite.components;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.ziaetaiba.islamicwebsite.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class Thumbnail {

    public static void loadThumbnailViaPicasso(final Context context, final ImageView imageView, final ProgressBar progressBar,
                                               final String photoId, final String thumbnailUrl) {

        final String path = SDCardManager.getThumbnailPath(context, photoId);

        if (null == path) {

            progressBar.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.thumbnail));

        } else {
            final File file = new File(path);
            if (Connectivity.isInternetOn(context, false, false)) {
                Picasso.with(context).load(thumbnailUrl).noFade().into(imageView, new Callback() {

                    @Override
                    public void onSuccess() {

                        progressBar.setVisibility(View.GONE);
                        imageView.setVisibility(View.VISIBLE);
                        Picasso.with(context).load(thumbnailUrl).noFade().into(

                                new Target() {

                                    @Override
                                    public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {

                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {

                                                try {

                                                    file.createNewFile();

                                                    FileOutputStream outStream = new FileOutputStream(file);

                                                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);

                                                    outStream.close();

                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }).start();
                                    }

                                    @Override
                                    public void onBitmapFailed(Drawable errorDrawable) {
                                    }

                                    @Override
                                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                                    }
                                }
                        );
                    }

                    @Override
                    public void onError() {

                        progressBar.setVisibility(View.GONE);
                        imageView.setVisibility(View.VISIBLE);
                        //    Log.e("PicassoHelper", "ImageLoadError - 2" + " - " + thumbnailUrl);
                        imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.thumbnail));
                    }
                });
            }
            else{
           // final File file = new File(path);

            Picasso.with(context).load(file)
                    .into(imageView, new Callback() {

                        @Override
                        public void onSuccess() {

                            progressBar.setVisibility(View.GONE);
                            imageView.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onError() {

                            // Log.e("PicassoHelper", "ImageLoadError - 1");
                            if (Connectivity.isInternetOn(context, false, false)) {

                                Picasso.with(context).load(thumbnailUrl).noFade().into(imageView, new Callback() {

                                    @Override
                                    public void onSuccess() {

                                        progressBar.setVisibility(View.GONE);
                                        imageView.setVisibility(View.VISIBLE);
                                        Picasso.with(context).load(thumbnailUrl).noFade().into(

                                                new Target() {

                                                    @Override
                                                    public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {

                                                        new Thread(new Runnable() {
                                                            @Override
                                                            public void run() {

                                                                try {

                                                                    file.createNewFile();

                                                                    FileOutputStream outStream = new FileOutputStream(file);

                                                                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);

                                                                    outStream.close();

                                                                } catch (Exception e) {
                                                                    e.printStackTrace();
                                                                }
                                                            }
                                                        }).start();
                                                    }

                                                    @Override
                                                    public void onBitmapFailed(Drawable errorDrawable) {
                                                    }

                                                    @Override
                                                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                                                    }
                                                }
                                        );
                                    }

                                    @Override
                                    public void onError() {

                                        progressBar.setVisibility(View.GONE);
                                        imageView.setVisibility(View.VISIBLE);
                                        //    Log.e("PicassoHelper", "ImageLoadError - 2" + " - " + thumbnailUrl);
                                        imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.thumbnail));
                                    }
                                });

                            } else {

                                progressBar.setVisibility(View.GONE);
                                imageView.setVisibility(View.VISIBLE);
//                            Log.e("PicassoHelper", "ImageLoadError - 3" + " - " + thumbnailUrl);
                                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.thumbnail));
                            }
                        }
                    });
        }
        }
    }

    public static int getNumOfColumns(Activity activity) {

        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int width = metrics.widthPixels;
//	    int height = metrics.heightPixels;

//		Toast.makeText(getActivity(), "Width: " + width + ", Height: " + height, Toast.LENGTH_LONG).show();

        int dp = pixelToDp(width);

        double itemWidth = activity.getResources().getDimension(R.dimen.main_item_width);
        // double itemHeight = activity.getResources().getDimension(R.dimen.main_item_height);

        int noColumns = (int) (dp / itemWidth); // 175 is the column width in dp

        if (noColumns < 2) {
            noColumns = 2 ;
        }
/*
        if (width < 600) {
            return 2;
        } else {
            return 3;
        }
        */
        return noColumns ;
    }

    public static int dpToPixel (int dp) {

        // Get the screen's density scale
        // final float scale = activity.getResources().getDisplayMetrics().density;
        final float scale = Resources.getSystem().getDisplayMetrics().density;

        int px = (int) ((dp * scale) + 0.5f);

        // DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
        // int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));

        return px;
    }

    public static int pixelToDp (int px) {

        //final float scale = activity.getResources().getDisplayMetrics().density;

        final float scale = Resources.getSystem().getDisplayMetrics().density;

        int dp = (int) ((px / scale) + 0.5f);

        // DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
        // int dp = Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return dp;
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static float convertDpToPixel(float dp, Context context){

        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    public static float convertPixelsToDp(float px, Context context){

        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return dp;
    }
}
