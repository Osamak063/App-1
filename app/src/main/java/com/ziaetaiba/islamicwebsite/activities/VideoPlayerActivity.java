package com.ziaetaiba.islamicwebsite.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialog;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.VideoView;

import com.ziaetaiba.islamicwebsite.R;
import com.ziaetaiba.islamicwebsite.constants.Constants;
import com.ziaetaiba.islamicwebsite.database.DBAdapter;


public class VideoPlayerActivity extends Activity {

    private LinearLayout linearLayoutProgress;
    private VideoView videoView;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_video_player);

        if (DBAdapter.isDatabaseAvailable()) {
            // do nothing....
        } else {
            DBAdapter.init(this);
        }

        videoView = (VideoView) findViewById(R.id.videoView);
        linearLayoutProgress = (LinearLayout) findViewById(R.id.linearLayoutVideoProgress);
        System.out.println("checkk VideoPlayer Activity");
        showProgressDialog();

        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        mediaController.setMediaPlayer(videoView);

        videoView.setMediaController(mediaController);

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            public void onPrepared(final MediaPlayer mp) {

                //          mp.setLooping(true);
                dismissProgressDialog();
/*
                    mp.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                        @Override
                        public void onBufferingUpdate(MediaPlayer mp, int percent) {

                            int curPosition = mp.getCurrentPosition();

                            Log.e("Video Player Activity", "Percentage: " + percent);

                            if (curPosition > 0) {

                                int currentPercent = (curPosition * 100) / mp.getAudioDuration();

//                                    txtViewRetrieve.setText(currentPercent + "% : " + percent + "%");
//                                    txtViewRetrieve.setVisibility(View.VISIBLE);

                                if (currentPercent <= percent) {
                                    dismissProgressDialog();
                                } else {
                                    showProgressDialog();
                                }
                            }
                        }
                    });
                    */
            }
        });

        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {

            @Override
            public boolean onError(MediaPlayer arg0, int arg1, int arg2) {

                dismissProgressDialog();
                showMessageDialog("Error!", "This video cannot be played.");

                return true;
            }
        });

        int currentApiVersion = Build.VERSION.SDK_INT;

        if (currentApiVersion >= Build.VERSION_CODES.JELLY_BEAN_MR1) {

            videoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {

                @Override
                public boolean onInfo(MediaPlayer mp, int what, int extra) {

                    if (MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START == what) {
                        dismissProgressDialog();
                    }
                    if (MediaPlayer.MEDIA_INFO_BUFFERING_START == what) {
                        showProgressDialog();
                    }
                    if (MediaPlayer.MEDIA_INFO_BUFFERING_END == what) {
                        dismissProgressDialog();
                    }
                    return false;
                }
            });

        } else {
            // do nothing.
        }

        playVideo();

    }

    @Override
    public void onBackPressed() {

        if (videoView != null) {
            videoView.stopPlayback();
        }

        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }

    @Override
    public void onConfigurationChanged(Configuration config) {
        super.onConfigurationChanged(config);
    }

    private void playVideo() {

        String videoUrl = getIntent().getStringExtra(Constants.ARG_VIDEO_PATH);

        if (videoUrl == null || videoUrl.equals("")) {

            dismissProgressDialog();
            showMessageDialog("Error!", "This video cannot be played.");
        } else {

            Uri video = Uri.parse(videoUrl);

            videoView.setVideoURI(video);

            runOnUiThread(new Runnable() {

                public void run() {

                    videoView.start();
                    videoView.requestFocus();
                }
            });
/*
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    int duration = videoView.getCurrentPosition();

                    Log.e("Video Player Activity", "Retrieving data..." + duration);

                    if (old_duration == duration && videoView.isPlaying()) {
                        showProgressDialog();
                    } else {
                        dismissProgressDialog();
                    }
                    old_duration = duration;
                }
            }, 1000);
*/
        }
    }

    private void showProgressDialog() {
        linearLayoutProgress.setVisibility(View.VISIBLE);
    }

    private void dismissProgressDialog() {
        linearLayoutProgress.setVisibility(View.GONE);
    }

    private void showMessageDialog(final String title, final String msg) {

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
}