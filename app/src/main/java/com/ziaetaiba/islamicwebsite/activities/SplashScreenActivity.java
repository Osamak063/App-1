package com.ziaetaiba.islamicwebsite.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.felipecsl.gifimageview.library.GifImageView;
import com.ziaetaiba.islamicwebsite.R;
import com.ziaetaiba.islamicwebsite.components.SDCardManager;
import com.ziaetaiba.islamicwebsite.constants.Constants;
import com.ziaetaiba.islamicwebsite.constants.ZiaeTaibaData;
import com.ziaetaiba.islamicwebsite.database.DBAdapter;

import org.apache.commons.io.IOUtils;

import java.io.InputStream;

public class SplashScreenActivity extends AppCompatActivity {

    private static final int SPLASH_TIME = 4000;
    private String msgError;
    private boolean isError;
    private GifImageView gif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

//        Transparent Status Bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        setContentView(R.layout.activity_splash_screen);
        gif = findViewById(R.id.splash_gif);
        isError = false;
        msgError = "";
        try {
            InputStream inputStream = getAssets().open("splash.gif");
            byte[] bytes = IOUtils.toByteArray(inputStream);
            gif.setBytes(bytes);
            gif.startAnimation();
        } catch (Exception e) {

        }
        new BackgroundTask().execute();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }

    @Override
    public void onConfigurationChanged(Configuration config) {
        super.onConfigurationChanged(config);
    }

    private class BackgroundTask extends AsyncTask {

        @Override
        protected void onPreExecute() {

            super.onPreExecute();

            isError = false;
            msgError = "";
        }

        @Override
        protected Object doInBackground(Object[] params) {

            /*  Use this method to load background
            * data that your app needs. */

            try {

                DBAdapter.init(getApplicationContext());

                int noOriginalSettings = ZiaeTaibaData.getNoSettings();
                int noOriginalLanguages = ZiaeTaibaData.getNoLanguages();
                int noOriginalServices = ZiaeTaibaData.getNoServices();
                int noOriginalMasters = ZiaeTaibaData.getNoMasters();
                int noOriginalSubMasters = ZiaeTaibaData.getNoSubMasters();
                int noOriginalDataTables = ZiaeTaibaData.getNoDataTables();
                int noOriginalBooks = ZiaeTaibaData.getNoProducts(ZiaeTaibaData.SERVICE_NO_BOOKS);
                int noOriginalMedia = ZiaeTaibaData.getNoProducts(ZiaeTaibaData.SERVICE_NO_MEDIA);
                int noOriginalAudioVideos = ZiaeTaibaData.getNoAudioVideo();
                int noOriginalScholar = ZiaeTaibaData.getNoProducts(ZiaeTaibaData.SERVICE_NO_SCHOLAR);
                int noOriginalGallery = ZiaeTaibaData.getNoProducts(ZiaeTaibaData.SERVICE_NO_GALLERY);
                int noOriginalNews = ZiaeTaibaData.getNoProducts(ZiaeTaibaData.SERVICE_NO_NEWS);
                int noOriginalPlaces = ZiaeTaibaData.getNoProducts(ZiaeTaibaData.SERVICE_NO_PLACES);
                int noOriginalHistory = ZiaeTaibaData.getNoProducts(ZiaeTaibaData.SERVICE_NO_HISTORY);
                int noOriginalDays = ZiaeTaibaData.getNoProducts(ZiaeTaibaData.SERVICE_NO_DAYS);
                int noOriginalDepartments = ZiaeTaibaData.getNoProducts(ZiaeTaibaData.SERVICE_NO_DEPARTMENTS);
                int noOriginalApps = ZiaeTaibaData.getNoProducts(ZiaeTaibaData.SERVICE_NO_APPS);
                int noOriginalArticles = ZiaeTaibaData.getNoProducts(ZiaeTaibaData.SERVICE_NO_ARTICLES);
                int noOriginalBlogs = ZiaeTaibaData.getNoProducts(ZiaeTaibaData.SERVICE_NO_BLOGS);
                int noOriginalPoetry = ZiaeTaibaData.getNoProducts(ZiaeTaibaData.SERVICE_NO_POETRY);

                int noDbSettings = DBAdapter.getSettingsCount();
                int noDbLanguages = DBAdapter.getLanguageCount();
                int noDbServices = DBAdapter.getServiceCount();
                int noDbMasters = DBAdapter.getMasterCount();
                int noDbSubMasters = DBAdapter.getSubMasterCount();
                int noDbDataTables = DBAdapter.getDataTablesCount();
                int noDbBooks = DBAdapter.getProductCount(DBAdapter.TABLE_BOOKS);
                int noDbMedia = DBAdapter.getProductCount(DBAdapter.TABLE_MEDIA);
                int noDbAudioVideo = DBAdapter.getProductCount(DBAdapter.TABLE_AUDIO_VIDEO);
                int noDbScholars = DBAdapter.getProductCount(DBAdapter.TABLE_SCHOLAR);
                int noDbGallery = DBAdapter.getProductCount(DBAdapter.TABLE_GALLERY);
                int noDbNews = DBAdapter.getProductCount(DBAdapter.TABLE_NEWS);
                int noDbPlaces = DBAdapter.getProductCount(DBAdapter.TABLE_PLACES);
                int noDbHistory = DBAdapter.getProductCount(DBAdapter.TABLE_HISTORY);
                int noDbDays = DBAdapter.getProductCount(DBAdapter.TABLE_DAYS);
                int noDbDepartment = DBAdapter.getProductCount(DBAdapter.TABLE_DEPARTMENTS);
                int noDbApps = DBAdapter.getProductCount(DBAdapter.TABLE_APPS);
                int noDbArticles = DBAdapter.getProductCount(DBAdapter.TABLE_ARTICLES);
                int noDbBlogs = DBAdapter.getProductCount(DBAdapter.TABLE_BLOGS);
                int noDbPoetry = DBAdapter.getProductCount(DBAdapter.TABLE_POETRY);
/*
                Log.e("SplashScreenActivity: ", "noDBSettings: "    + noDbSettings) ;
                Log.e("SplashScreenActivity: ", "noDBLanguages: "   + noDbLanguages) ;
                Log.e("SplashScreenActivity: ", "noDBServices: "    + noDbServices) ;
                Log.e("SplashScreenActivity: ", "noDBMasters: "     + noDbMasters) ;
                Log.e("SplashScreenActivity: ", "noDBSubMasters: "  + noDbSubMasters) ;
                Log.e("SplashScreenActivity: ", "noDBDataTables: "  + noDbDataTables) ;
                Log.e("SplashScreenActivity: ", "noDBBooks: "       + noDbBooks) ;
                Log.e("SplashScreenActivity: ", "noDBMedia: "       + noDbMedia) ;
                Log.e("SplashScreenActivity: ", "noDBAudioVideo: "  + noDbAudioVideo) ;
                Log.e("SplashScreenActivity: ", "noDBScholar: "     + noDbScholars) ;
                Log.e("SplashScreenActivity: ", "noDBGallery: "     + noDbGallery) ;
                Log.e("SplashScreenActivity: ", "noDBNews: "        + noDbNews) ;
                Log.e("SplashScreenActivity: ", "noDBPlaces: "      + noDbPlaces) ;
                Log.e("SplashScreenActivity: ", "noDBHistory: "     + noDbHistory) ;
                Log.e("SplashScreenActivity: ", "noDBDays: "        + noDbDays) ;
                Log.e("SplashScreenActivity: ", "noDBDepartments: " + noDbDepartment) ;
                Log.e("SplashScreenActivity: ", "noDBApps: "        + noDbApps) ;
                Log.e("SplashScreenActivity: ", "noDBArticles: "    + noDbArticles) ;
                Log.e("SplashScreenActivity: ", "noDBBlogs: "       + noDbBlogs) ;
                Log.e("SplashScreenActivity: ", "noDBPoetry: "      + noDbPoetry) ;
*/
                if (noDbSettings == noOriginalSettings
                        && noDbLanguages >= noOriginalLanguages
                        && noDbServices >= noOriginalServices
                        && noDbMasters >= noOriginalMasters
                        && noDbSubMasters >= noOriginalSubMasters
                        && noDbDataTables >= noOriginalDataTables
                        && noDbBooks >= noOriginalBooks
                        && noDbMedia >= noOriginalMedia
                        && noDbAudioVideo >= noOriginalAudioVideos
                        && noDbScholars >= noOriginalScholar
                        && noDbGallery >= noOriginalGallery
                        && noDbNews >= noOriginalNews
                        && noDbPlaces >= noOriginalPlaces
                        && noDbHistory >= noOriginalHistory
                        && noDbDays >= noOriginalDays
                        && noDbDepartment >= noOriginalDepartments
                        && noDbApps >= noOriginalApps
                        && noDbArticles >= noOriginalArticles
                        && noDbBlogs >= noOriginalBlogs
                        && noDbPoetry >= noOriginalPoetry) {

                    isError = false;
                    msgError = "";

                } else {

                    DBAdapter.deleteAllSettingsData();
                    DBAdapter.deleteAllLanguages();
                    DBAdapter.deleteAllServices();
                    DBAdapter.deleteAllMasters();
                    DBAdapter.deleteAllSubMasters();
                    DBAdapter.deleteAllDataTables();
                    DBAdapter.deleteAllProductData(DBAdapter.TABLE_BOOKS);
                    DBAdapter.deleteAllProductData(DBAdapter.TABLE_MEDIA);
                    DBAdapter.deleteAllProductData(DBAdapter.TABLE_AUDIO_VIDEO);
                    DBAdapter.deleteAllProductData(DBAdapter.TABLE_SCHOLAR);
                    DBAdapter.deleteAllProductData(DBAdapter.TABLE_GALLERY);
                    DBAdapter.deleteAllProductData(DBAdapter.TABLE_NEWS);
                    DBAdapter.deleteAllProductData(DBAdapter.TABLE_PLACES);
                    DBAdapter.deleteAllProductData(DBAdapter.TABLE_HISTORY);
                    DBAdapter.deleteAllProductData(DBAdapter.TABLE_DAYS);
                    DBAdapter.deleteAllProductData(DBAdapter.TABLE_DEPARTMENTS);
                    DBAdapter.deleteAllProductData(DBAdapter.TABLE_APPS);
                    DBAdapter.deleteAllProductData(DBAdapter.TABLE_ARTICLES);
                    DBAdapter.deleteAllProductData(DBAdapter.TABLE_BLOGS);
                    DBAdapter.deleteAllProductData(DBAdapter.TABLE_POETRY);

                    SDCardManager.deleteDirs(Constants.MEDIA_DATA_PATH);

                    ZiaeTaibaData.addSettingsData();
                    ZiaeTaibaData.addDataTablesData();

                    noDbSettings = DBAdapter.getSettingsCount();
                    noDbLanguages = DBAdapter.getLanguageCount();
                    noDbServices = DBAdapter.getServiceCount();
                    noDbMasters = DBAdapter.getMasterCount();
                    noDbSubMasters = DBAdapter.getSubMasterCount();
                    noDbDataTables = DBAdapter.getDataTablesCount();
                    noDbBooks = DBAdapter.getProductCount(DBAdapter.TABLE_BOOKS);
                    noDbMedia = DBAdapter.getProductCount(DBAdapter.TABLE_MEDIA);
                    noDbAudioVideo = DBAdapter.getProductCount(DBAdapter.TABLE_AUDIO_VIDEO);
                    noDbScholars = DBAdapter.getProductCount(DBAdapter.TABLE_SCHOLAR);
                    noDbGallery = DBAdapter.getProductCount(DBAdapter.TABLE_GALLERY);
                    noDbNews = DBAdapter.getProductCount(DBAdapter.TABLE_NEWS);
                    noDbPlaces = DBAdapter.getProductCount(DBAdapter.TABLE_PLACES);
                    noDbHistory = DBAdapter.getProductCount(DBAdapter.TABLE_HISTORY);
                    noDbDays = DBAdapter.getProductCount(DBAdapter.TABLE_DAYS);
                    noDbDepartment = DBAdapter.getProductCount(DBAdapter.TABLE_DEPARTMENTS);
                    noDbApps = DBAdapter.getProductCount(DBAdapter.TABLE_APPS);
                    noDbArticles = DBAdapter.getProductCount(DBAdapter.TABLE_ARTICLES);
                    noDbBlogs = DBAdapter.getProductCount(DBAdapter.TABLE_BLOGS);
                    noDbPoetry = DBAdapter.getProductCount(DBAdapter.TABLE_POETRY);

                    if (noDbSettings == noOriginalSettings
                            && noDbLanguages == noOriginalLanguages
                            && noDbServices == noOriginalServices
                            && noDbMasters == noOriginalMasters
                            && noDbSubMasters == noOriginalSubMasters
                            && noDbDataTables == noOriginalDataTables
                            && noDbBooks == noOriginalBooks
                            && noDbMedia == noOriginalMedia
                            && noDbAudioVideo == noOriginalAudioVideos
                            && noDbScholars == noOriginalScholar
                            && noDbGallery == noOriginalGallery
                            && noDbNews == noOriginalNews
                            && noDbPlaces == noOriginalPlaces
                            && noDbHistory == noOriginalHistory
                            && noDbDays == noOriginalDays
                            && noDbDepartment == noOriginalDepartments
                            && noDbApps == noOriginalApps
                            && noDbArticles == noOriginalArticles
                            && noDbBlogs == noOriginalBlogs
                            && noDbPoetry == noOriginalPoetry) {

                        isError = false;
                        msgError = "";

                    } else {

                        isError = true;
                        msgError = "Error:1! Database is not available";
                    }
                }

                // Thread will sleep for 3 second
                try {
                    Thread.sleep(SPLASH_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {

                Log.e("SplashScreenActivity", "BackgroundTask > Exception: " + e.toString());
                isError = true;
                msgError = "Error:2! Database is not available";
            }

            return null;
        }

        @Override
        protected void onPostExecute(Object object) {

            super.onPostExecute(object);

            if (isError) {
                showErrorMessage(msgError);
            } else {
                startMainActivity();
            }
        }

        private void startMainActivity() {

            Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
            // ActivityAnimManager.startActivityWithAnimation(this, intent, 0, linearLayout);
            startActivity(intent);
            finish();
        }

        private void showErrorMessage(final String msg) {

            showMessage(msg);
            finish();
        }

        private void showMessage(final String msg) {

            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}