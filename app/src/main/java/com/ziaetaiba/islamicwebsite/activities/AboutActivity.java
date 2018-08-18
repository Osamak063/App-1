package com.ziaetaiba.islamicwebsite.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ziaetaiba.islamicwebsite.R;

public class AboutActivity extends AppCompatActivity {
    TextView about, submitReview, share, moreApps, version;
    LinearLayout submitLayout, shareLayout, moreAppsLayout, versionLayout;
    ImageView submitArrow, shareArrow, moreAppsArrow, versionArrow;
    ImageView submitImg, shareImg, moreAppsImg, versionImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        setTitle("Zia-e-Taiba");
        about = findViewById(R.id.about);
        submitReview = findViewById(R.id.submit_review_text);
        share = findViewById(R.id.share_text);
        moreApps = findViewById(R.id.more_apps_text);
        version = findViewById(R.id.version_text);
        submitLayout = findViewById(R.id.submit_review_layout);
        shareLayout = findViewById(R.id.share_layout);
        moreAppsLayout = findViewById(R.id.more_apps_layout);
        versionLayout = findViewById(R.id.version_layout);
        submitArrow = findViewById(R.id.submit_arrow);
        shareArrow = findViewById(R.id.share_arrow);
        moreAppsArrow = findViewById(R.id.more_apps_arrow);
        versionArrow = findViewById(R.id.version_arrow);
        submitLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitReview.setTextColor(getResources().getColor(R.color.ThemeGrey));
                submitArrow.setImageResource(R.drawable.arrow_head_grey);
                submitLayout.setBackgroundColor(getResources().getColor(R.color.ThemeBlue));
                handleRateAction();
            }
        });
        shareLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                share.setTextColor(getResources().getColor(R.color.ThemeGrey));
                shareArrow.setImageResource(R.drawable.arrow_head_grey);
                shareLayout.setBackgroundColor(getResources().getColor(R.color.ThemeBlue));
                handleShareAction();
            }
        });
        moreAppsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moreApps.setTextColor(getResources().getColor(R.color.ThemeGrey));
                moreAppsArrow.setImageResource(R.drawable.arrow_head_grey);
                moreAppsLayout.setBackgroundColor(getResources().getColor(R.color.ThemeBlue));
                handleMoreAppsAction();
            }
        });
        versionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                version.setTextColor(getResources().getColor(R.color.ThemeGrey));
                versionArrow.setImageResource(R.drawable.arrow_head_grey);
                versionLayout.setBackgroundColor(getResources().getColor(R.color.ThemeBlue));
                showAboutDialog();
            }
        });

    }

    private void handleRateAction() {

        String url = "";
        final String appPackage = this.getPackageName();

        try {
            // Check whether Google Play store is installed or not:
            this.getPackageManager().getPackageInfo("com.android.vending", 0);

            url = "market://details?id=" + appPackage;

        } catch (Exception e) {
            url = "http://play.google.com/store/apps/details?id=" + appPackage;
        }

        openPlayStore(url);
    }

    private void handleShareAction() {

        String app = getResources().getString(R.string.app_name);

        String msg = "'" + app + "' an Android App!"
                + "\nPlease visit it on Play Store;"
                + "\nhttp://play.google.com/store/apps/details?id="
                + this.getPackageName();

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, msg);
        startActivity(Intent.createChooser(intent, "Share with"));

    }

    private void handleMoreAppsAction() {

        // https://play.google.com/store/apps/developer?id=Zia%20e%20taiba
        String ziaetaiba = "Zia e Taiba";

        String url = "";

        url = "https://play.google.com/store/apps/dev?id=7848049246569116556";
/*
        try {
            // Check whether Google Play store is installed or not:
            this.getPackageManager().getPackageInfo("com.android.vending", 0);

            url = "market://search?q=pub:" + imranApps;

        } catch (Exception e) {
            url = "http://play.google.com/store/search?q=pub:" + imranApps;
        }
*/

/*
        try {
            // Check whether Google Play store is installed or not:
            this.getPackageManager().getPackageInfo("com.android.vending", 0);

            url = "market://dev?id=6531372441973390387";

        } catch (Exception e) {
            url = "https://play.google.com/store/apps/dev?id=6531372441973390387" ;
        }

        openPlayStore(url);
*/
        openPlayStore(url);
    }

    private void showAboutDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final View view = LayoutInflater.from(this).inflate(R.layout.dialog_about, null);

        builder.setView(view);

        builder.setCancelable(false);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
                version.setTextColor(getResources().getColor(R.color.ThemeBlue));
                versionArrow.setImageResource(R.drawable.arrow_head_blue);
                versionLayout.setBackgroundColor(getResources().getColor(R.color.ThemeGrey));
            }
        });

        AppCompatDialog dialog = builder.create();

        dialog.show();
    }

    private void openPlayStore(String url) {

        // Open the app page in Google Play store:
        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        startActivity(intent);

    }

    @Override
    protected void onResume() {
        submitReview.setTextColor(getResources().getColor(R.color.ThemeBlue));
        submitArrow.setImageResource(R.drawable.arrow_head_blue);
        submitLayout.setBackgroundColor(getResources().getColor(R.color.ThemeGrey));
        moreApps.setTextColor(getResources().getColor(R.color.ThemeBlue));
        moreAppsArrow.setImageResource(R.drawable.arrow_head_blue);
        moreAppsLayout.setBackgroundColor(getResources().getColor(R.color.ThemeGrey));
        share.setTextColor(getResources().getColor(R.color.ThemeBlue));
        shareArrow.setImageResource(R.drawable.arrow_head_blue);
        shareLayout.setBackgroundColor(getResources().getColor(R.color.ThemeGrey));
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();

        if (itemId == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityCompat.finishAfterTransition(this);
    }
}
