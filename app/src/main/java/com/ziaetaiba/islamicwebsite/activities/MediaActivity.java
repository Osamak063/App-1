package com.ziaetaiba.islamicwebsite.activities;

import android.Manifest;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.ziaetaiba.islamicwebsite.R;
import com.ziaetaiba.islamicwebsite.adapters.MediaRecyclerViewAdapter;
import com.ziaetaiba.islamicwebsite.adapters.NoConnectionRecyclerViewAdapter;
import com.ziaetaiba.islamicwebsite.callbacks.MediaRecyclerViewCallbacks;
import com.ziaetaiba.islamicwebsite.callbacks.NoConnectionRecyclerViewCallbacks;
import com.ziaetaiba.islamicwebsite.components.ActivityAnimManager;
import com.ziaetaiba.islamicwebsite.components.Connectivity;
import com.ziaetaiba.islamicwebsite.components.MediaDownloadManager;
import com.ziaetaiba.islamicwebsite.components.SDCardManager;
import com.ziaetaiba.islamicwebsite.constants.Constants;
import com.ziaetaiba.islamicwebsite.database.DBAdapter;
import com.ziaetaiba.islamicwebsite.datamodels.AudioVideoHeaderModel;
import com.ziaetaiba.islamicwebsite.datamodels.AudioVideoModel;
import com.ziaetaiba.islamicwebsite.datamodels.MediaModel;
import com.ziaetaiba.islamicwebsite.datamodels.NoConnectionModel;
import com.ziaetaiba.islamicwebsite.datamodels.SettingsModel;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

public class MediaActivity extends AppCompatActivity
        implements MediaRecyclerViewCallbacks,
        NoConnectionRecyclerViewCallbacks {

    private static final String TAG = MediaActivity.class.getSimpleName();

    private CoordinatorLayout coordinatorLayout;
    private RecyclerView recyclerView;

    private MediaModel mediaModel;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setTheme(R.style.MyThemeDeepPurple);
        setContentView(R.layout.activity_list_simple);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.main_content);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        System.out.println("checkk Media Activity");
        if (DBAdapter.isDatabaseAvailable()) {
            // do nothing...
        } else {
            DBAdapter.init(this);
        }

        MediaDownloadManager.init(this);

        IntentFilter downloadFilter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        IntentFilter notificationFilter = new IntentFilter(DownloadManager.ACTION_NOTIFICATION_CLICKED);

        registerReceiver(onDownloadComplete, downloadFilter);
        registerReceiver(onNotificationClick, notificationFilter);

        Intent intent = getIntent();

        int mediaId = intent.getIntExtra(Constants.ARG_MEDIA_ID, 0);

        mediaModel = DBAdapter.getMediaModelFromId(mediaId);
        setTitle(mediaModel.getTitle());

        updateRecyclerView(mediaId);

        applySettings();
    }

    @Override
    public void onHandleNoConnectionItemAction(View view) {
        showSnackBar(Constants.CONTAINS_NO_DATA);
    }

    @Override
    public void onHandleMediaItemAction(View view, AudioVideoModel audioVideoModel) {
        handleAudioVideoAction(view, audioVideoModel);
    }

    @Override
    public void onHandleDownloadAction(View view, AudioVideoModel audioVideoModel, ImageView imageView) {
        handleDownloadAction(audioVideoModel, imageView);
    }

    @Override
    public void onUpdateDownloadStatus(AudioVideoModel audioVideoModel, ImageView imageView) {
        updateDownloadStatus(audioVideoModel, imageView);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            // Log.e(TAG,"Permission: " + permissions[0]+ "was "+ grantResults[0]);

            showSnackBar("Tap on Download to start downloading!");
            // handleStartDownload();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        ActivityCompat.finishAfterTransition(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {

        super.onDestroy();

        unregisterReceiver(onDownloadComplete);
        unregisterReceiver(onNotificationClick);
    }

    private void updateRecyclerView(int mediaId) {

        int color = R.color.colorPrimaryLight_4;

        ArrayList<Object> detailList = new ArrayList<>();

        ArrayList<AudioVideoModel> audioList = DBAdapter.getAllAudioVideoModel(mediaId, Constants.ARG_TYPE_AUDIO);
        ArrayList<AudioVideoModel> videoList = DBAdapter.getAllAudioVideoModel(mediaId, Constants.ARG_TYPE_VIDEO);

        if (!audioList.isEmpty()) {

            detailList.add(new AudioVideoHeaderModel(1, Constants.ARG_TYPE_AUDIO));
            detailList.addAll(audioList);
        }

        if (!videoList.isEmpty()) {

            detailList.add(new AudioVideoHeaderModel(2, Constants.ARG_TYPE_VIDEO));
            detailList.addAll(videoList);
        }

        if (detailList.isEmpty()) {

            ArrayList<NoConnectionModel> list = new ArrayList<>();

            list.add(new NoConnectionModel(1, Constants.CONTAINS_NO_DATA, "", R.drawable.ic_android_green));

            NoConnectionRecyclerViewAdapter adapter = new NoConnectionRecyclerViewAdapter(this, this, list, color);

            LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext());

            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

            recyclerView.setLayoutManager(layoutManager);

            recyclerView.setAdapter(adapter);

        } else {

            MediaRecyclerViewAdapter adapter = new MediaRecyclerViewAdapter(this, this, detailList);

            LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

            recyclerView.setLayoutManager(layoutManager);

            recyclerView.setAdapter(adapter);

            recyclerView.setHasFixedSize(true);
        }
    }

    private void handleAudioVideoAction(View view, AudioVideoModel audioVideoModel) {

        int no = mediaModel.getNo();
        String title = mediaModel.getTitle();
        String type = audioVideoModel.getType();
        String url = audioVideoModel.getUrl();

        String itemNo = type + "_" + no;

        boolean isDownloading = isMediaDownloading(itemNo);

        if (isDownloading) {

            showMessageDialog("Message!", "Please wait, while the media is downloading.");

        } else {

            if (TextUtils.equals(type, Constants.ARG_TYPE_AUDIO)) {

                playAudio(title, url);

            } else {

                handleVideoAction(view, title, url);
            }
        }
        // showSnackBar(audioVideoModel.toString());
    }

    private void playAudio(String title, String url) {

        final String path = SDCardManager.getMediaPath(title, ".mp3");

        File file = new File(path);

        if (file.isFile()) {

            Intent intent = new Intent();
            intent.setAction(android.content.Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), "audio/*");
            startActivity(intent);

        } else {

            if (Connectivity.isInternetOn(this, false, false)) {

                Uri audio = Uri.parse(url);

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setDataAndType(audio, "audio/mp3");
                startActivity(intent);
/*
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setDataAndType(audio, "audio");

                try {
                    startActivity(intent);  // crash issue found here on some Galaxy devices
                } catch (Exception ex) {
                    showMessageDialog("Message!", "Please download this audio first.");
                }
                */

            } else {
                showMessageDialog("Message!", "No Internet Connection.");
            }
        }
    }

    private void handleVideoAction(View view, String title, String url) {

        final String path = SDCardManager.getMediaPath(title, ".mp4");

        String videoStatus = SDCardManager.getMediaStatus(title, ".mp4");

        if (videoStatus.equals(Constants.PHOTO_AVAILABLE)) {

            startVideoPlayerActivity(view, path);

        } else {

            if (Connectivity.isInternetOn(this, false, false)) {

                if (url.contains(".mp4")) {
                    startVideoPlayerActivity(view, url);
                } else {

                    Uri video = Uri.parse(url);

                    Intent intent = new Intent(Intent.ACTION_VIEW, video);
                    startActivity(intent);
                }

            } else {

                showMessageDialog("Message!", "No Internet Connection.");
            }
        }
    }

    private void startVideoPlayerActivity(View view, String path) {

        Intent intent = new Intent(this, VideoPlayerActivity.class);

        intent.putExtra(Constants.ARG_VIDEO_PATH, path);

        ActivityAnimManager.startActivityWithAnimation(this, intent, 0, view);
    }

    private void handleDownloadAction(AudioVideoModel audioVideoModel, ImageView imageView) {

        int no = mediaModel.getNo();
        int id = mediaModel.getId();
        String title = mediaModel.getTitle();
        String type = audioVideoModel.getType();
        String extension = ".mp3";

        if (TextUtils.equals(type, Constants.ARG_TYPE_VIDEO)) {
            extension = ".mp4";
        }

        String itemNo = type + "_" + no;

        String mediaStatus = SDCardManager.getMediaStatus(title, extension);

        boolean isDownloading = isMediaDownloading(itemNo);

        if (isDownloading) {

            showCancelDownloadDialog(audioVideoModel, imageView);

        } else {

            if (mediaStatus.equals(Constants.SD_CARD_NOT_AVAILABLE)) {

                showMessageDialog("Error!", "SD card is not available.");

            } else if (mediaStatus.equals(Constants.PHOTO_AVAILABLE)) {

                showDeleteDialog(audioVideoModel, imageView);

            } else {

                if (Connectivity.isInternetOn(this, false, false)) {

                    boolean isGranted = isStoragePermissionGranted();

                    if (isGranted) {
                        handleStartDownload(audioVideoModel, imageView);
                    }

                } else {

                    showMessageDialog("Message!", "No Internet Connection.");
                }
            }
        }
    }

    private void handleStartDownload(AudioVideoModel audioVideoModel, ImageView imageView) {

        int no = mediaModel.getNo();
        int id = mediaModel.getId();
        String title = mediaModel.getTitle();
        String type = audioVideoModel.getType();
        String url = audioVideoModel.getUrl();
        String extension = ".mp3";

        if (TextUtils.equals(type, Constants.ARG_TYPE_VIDEO)) {
            extension = ".mp4";
        }

        String itemNo = type + "_" + no;

        String path = SDCardManager.getMediaPath(title, extension);

        long downloadReference = MediaDownloadManager.startDownload(title, url, path);
        sharedPreferences.edit().putLong(itemNo, downloadReference).commit();

        updateDownloadStatus(audioVideoModel, imageView);
    }

    private void updateDownloadStatus(AudioVideoModel audioVideoModel, ImageView imageView) {

        int no = mediaModel.getNo();
        int id = mediaModel.getId();
        String title = mediaModel.getTitle();
        String type = audioVideoModel.getType();
        String url = audioVideoModel.getUrl();
        String extension = ".mp3";

        if (TextUtils.equals(type, Constants.ARG_TYPE_VIDEO)) {
            extension = ".mp4";
        }

        String itemNo = type + "_" + no;

        boolean isDownloading = isMediaDownloading(itemNo);

        if (isDownloading) {

            imageView.setImageDrawable(this.getResources().getDrawable(R.mipmap.ic_clear_black_48dp));

        } else {

            String videoStatus = SDCardManager.getMediaStatus(title, extension);

            if (videoStatus.equals(Constants.PHOTO_AVAILABLE)) {
                imageView.setImageDrawable(this.getResources().getDrawable(R.mipmap.ic_delete_black_48dp));
            } else {
                imageView.setImageDrawable(this.getResources().getDrawable(R.mipmap.ic_file_download_black_48dp));
            }
        }
    }

    public boolean isMediaDownloading(String itemNo) {

        boolean isDownloading = false;

        long downloadReference = sharedPreferences.getLong(itemNo, -1);

        if (downloadReference > -1) {

            String status = MediaDownloadManager.checkStatus(downloadReference);

            if (status.equals(Constants.STATUS_EMPTY) ||
                    status.equals(Constants.STATUS_FAILED) ||
                    status.equals(Constants.STATUS_SUCCESSFUL)) {

                isDownloading = false;

            } else if (status.equals(Constants.STATUS_PAUSED) ||
                    status.equals(Constants.STATUS_PENDING) ||
                    status.equals(Constants.STATUS_RUNNING)) {

                isDownloading = true;

            } else {

                isDownloading = false;
            }

            if (isDownloading) {
                // do nothing...
            } else {
                sharedPreferences.edit().remove(itemNo).commit();
            }

        } else {
            isDownloading = false;
        }

        return isDownloading;
    }

    private void showSnackBar(String msg) {

//        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        Snackbar.make(coordinatorLayout, msg, Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show();
    }

    private void showCancelDownloadDialog(final AudioVideoModel audioVideoModel, final ImageView imageView) {

        int no = mediaModel.getNo();
        String title = mediaModel.getTitle();
        String type = audioVideoModel.getType();
        String extension = ".mp3";

        if (TextUtils.equals(type, Constants.ARG_TYPE_VIDEO)) {
            extension = ".mp4";
        }

        final String itemNo = type + "_" + no;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        String cTitle = "Stop Download!";
        String msg = "Stop the download of media '" + title + "." + extension + "'?";

        builder.setTitle(cTitle);
        builder.setMessage(msg);
        builder.setCancelable(false);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (isMediaDownloading(itemNo)) {

                    String strMediaNo = itemNo + "";

                    long downloadReference = sharedPreferences.getLong(strMediaNo, -1);

                    if (downloadReference > -1) {

                        MediaDownloadManager.cancelDownload(downloadReference);

                        sharedPreferences.edit().remove(strMediaNo).commit();

                        imageView.setImageDrawable(getResources().getDrawable(R.mipmap.ic_file_download_black_48dp));

                    } else {
                        // do nothing...
                    }
                } else {
                    updateDownloadStatus(audioVideoModel, imageView);
                }

                dialog.dismiss();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        AppCompatDialog dialog = builder.create();

        dialog.show();
    }

    private void showDeleteDialog(AudioVideoModel audioVideoModel, final ImageView imageView) {

        int no = mediaModel.getNo();
        int id = mediaModel.getId();
        final String title = mediaModel.getTitle();
        String type = audioVideoModel.getType();
        String url = audioVideoModel.getUrl();
        String extension = ".mp3";

        if (TextUtils.equals(type, Constants.ARG_TYPE_VIDEO)) {
            extension = ".mp4";
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        String delTitle = "Delete Data!";
        String msg = "The media '" + title + "." + extension + "' will be deleted.";

        builder.setTitle(delTitle);
        builder.setMessage(msg);
        builder.setCancelable(false);

        final String finalExtension = extension;

        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String msg = "";

                String path = SDCardManager.getMediaPath(title, finalExtension);

                if (SDCardManager.deleteDirs(path)) {

                    msg = "Deleted Successfully.";

                    imageView.setImageDrawable(getResources().getDrawable(R.mipmap.ic_file_download_black_48dp));

                } else {
                    msg = "OOPS! the media was not deleted.";
                }

                showSnackBar(msg);
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        AppCompatDialog dialog = builder.create();

        dialog.show();
    }

    private void showMessageDialog(String title, String msg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setCancelable(false);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        AppCompatDialog dialog = builder.create();

        dialog.show();
    }

    public boolean isStoragePermissionGranted() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {

                return true;

            } else {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }

        } else { //permission is automatically granted on sdk<23 upon installation

            return true;
        }
    }

    private BroadcastReceiver onDownloadComplete = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);

            Map<String, ?> map = sharedPreferences.getAll();

            boolean isContain = map.containsValue(referenceId);

            if (isContain) {

                int mediaId = mediaModel.getId();
                updateRecyclerView(mediaId);
            }
        }
    };

    BroadcastReceiver onNotificationClick = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {

            Intent intents = new Intent();
            intents.setAction(DownloadManager.ACTION_VIEW_DOWNLOADS);
            context.startActivity(intents);
        }
    };

    private void applySettings() {

        SettingsModel setBean = DBAdapter.getSettingsData();

        String awake = setBean.getStayAwake();

        if (awake.equals(Constants.AWAKE_YES)) {
            getWindow()
                    .addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } else {
            getWindow().clearFlags(
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }
}