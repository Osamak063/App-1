package com.ziaetaiba.islamicwebsite.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.ziaetaiba.islamicwebsite.R;
import com.ziaetaiba.islamicwebsite.adapters.MainItemRecyclerViewAdapter;
import com.ziaetaiba.islamicwebsite.adapters.NoConnectionRecyclerViewAdapter;
import com.ziaetaiba.islamicwebsite.callbacks.MainItemRecyclerViewCallbacks;
import com.ziaetaiba.islamicwebsite.callbacks.NoConnectionRecyclerViewCallbacks;
import com.ziaetaiba.islamicwebsite.components.ActivityAnimManager;
import com.ziaetaiba.islamicwebsite.datamodels.LanguageModel;
import com.ziaetaiba.islamicwebsite.datamodels.MainItemModel;
import com.ziaetaiba.islamicwebsite.datamodels.NoConnectionModel;
import com.ziaetaiba.islamicwebsite.datamodels.SettingsModel;
import com.ziaetaiba.islamicwebsite.callbacks.AppDataCallbacks;
import com.ziaetaiba.islamicwebsite.components.Connectivity;
import com.ziaetaiba.islamicwebsite.components.VolleySingleton;
import com.ziaetaiba.islamicwebsite.constants.Constants;
import com.ziaetaiba.islamicwebsite.database.DBAdapter;
import com.ziaetaiba.islamicwebsite.tasks.MasterData;

import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements MainItemRecyclerViewCallbacks,
        NoConnectionRecyclerViewCallbacks,
        AppDataCallbacks,
        AppBarLayout.OnOffsetChangedListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private CoordinatorLayout coordinatorLayout;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private AppBarLayout appBarLayout;
    private MasterData masterData;
    private LinearLayout mRootMain;
    private boolean isCancel, isUpdating;

    private AppCompatDialog dialog;
    private ImageView imgViewIcon;
    private TextView textViewUpdateMessage;
    private RotateAnimation rotateAnimation;
    private ArrayList<MainItemModel> mainItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_list_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Constants.IS_MAIN_ACTIVITY_RUNNING = true;
//
//        coordinatorLayout = findViewById(R.id.main_content);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        mRootMain = findViewById(R.id.root_main);
//        appBarLayout = findViewById(R.id.appbar);
//        appBarLayout.setEnabled(true);
//        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_toolbar);
//        collapsingToolbar.setTitle(getTitle());

        final ImageView imageView = findViewById(R.id.backdrop);
        Glide.with(this).load(R.drawable.splash).centerCrop().into(imageView);

        recyclerView = findViewById(R.id.recyclerView);
        // FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (Connectivity.isInternetOn(MainActivity.this, true, false)) {
                    VolleySingleton.getInstance(MainActivity.this).getRequestQueue().getCache().clear();

                    String url = DBAdapter.getSettingsData().getDataPath();

                    retrieveData(url);
                } else {
                    swipeRefreshLayout.setRefreshing(false);
                    showSnackBar("No Internet Connection!");
                }
            }
        });

//        fab.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//
//                showUpdateDialog(true, false);
//            }
//        });

        if (DBAdapter.isDatabaseAvailable()) {
            // do nothing...
        } else {
            DBAdapter.init(this);
        }

        updateRecyclerView();

        applySettings(true);
    }

    @Override
    public void onHandleMainItemAction(View view, MainItemModel mainItemModel) {
        handleMainItemAction(view, mainItemModel);
    }

    @Override
    public void onHandleNoConnectionItemAction(View view) {
        showUpdateDialog(true, false);
    }

    @Override
    public void onPreUpdate(String msg) {

        isUpdating = true;
    }

    @Override
    public void onProgressUpdate(String msg, int percent) {

        // do nothing...
    }

    @Override
    public void onPostUpdate(String msg, boolean isNewUpdate, boolean isError) {
        swipeRefreshLayout.setRefreshing(false);
        showSnackBar(msg);
        isUpdating = false;

        if (isCancel) {
            // do nothing...
        } else {

            if (rotateAnimation != null) {
                rotateAnimation.cancel();
            }

            if (!isError) {

                if (isNewUpdate) {
                    updateRecyclerView();
                }
            }

            textViewUpdateMessage.setText(msg);

            CountDownTimer countTimer = new CountDownTimer(2000, 1000) {

                public void onTick(long millisUntilFinished) {
                    // do nothing...
                }

                public void onFinish() {

                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            };

            countTimer.start();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        MainItemRecyclerViewAdapter adapter = new MainItemRecyclerViewAdapter(this, this, mainItemList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        super.onResume();

//        appBarLayout.addOnOffsetChangedListener(this);

        //     LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
        //           new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));
    }

    @Override
    protected void onPause() {

        //   LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
//        appBarLayout.removeOnOffsetChangedListener(this);
    }

    protected void onDestroy() {

        super.onDestroy();

        if (isUpdating) {

            if (masterData != null && !masterData.isCancelled()) {
                masterData.cancel(true);
            }

            isUpdating = false;
        }

        if (dialog != null && dialog.isShowing()) {

            dialog.dismiss();
            dialog = null;
        }
    }

    @Override
    public void onBackPressed() {

        VolleySingleton.getInstance(this).getRequestQueue().getCache().clear();
        DBAdapter.closeDatabase();

        Constants.IS_MAIN_ACTIVITY_RUNNING = false;

        super.onBackPressed();
        ActivityCompat.finishAfterTransition(this);
    }

    private void updateRecyclerView() {

        ArrayList<LanguageModel> languageList = DBAdapter.getAllLanguages();

        LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        if (languageList.isEmpty()) {

            ArrayList<NoConnectionModel> list = new ArrayList<>();

            list.add(new NoConnectionModel(1, "Not updated yet!", "Try again?", R.drawable.ic_android_green));

            int color = R.color.fab_material_amber_200;

            NoConnectionRecyclerViewAdapter adapter = new NoConnectionRecyclerViewAdapter(this, this, list, color);

            recyclerView.setLayoutManager(layoutManager);

            recyclerView.setAdapter(adapter);

        } else {

            mainItemList = new ArrayList<>();

            String header = "Languages";

            int size = languageList.size();

            for (int i = 0; i < size; i++) {

                if (i != 0) {
                    header = "";
                }

                LanguageModel languageModel = languageList.get(i);
                MainItemModel mainItemModel;
                int languageId = languageModel.getId();
                if (languageModel.getTitle().equals("English")) {
                    mainItemModel = new MainItemModel(languageId, header, languageModel.getTitle(), R.drawable.eng_logo);
                } else if (languageModel.getTitle().equals("Arabic")) {
                    mainItemModel = new MainItemModel(languageId, header, languageModel.getTitle(), R.drawable.arabi_logo);
                } else {
                    mainItemModel = new MainItemModel(languageId, header, languageModel.getTitle(), R.drawable.urdu_logo);
                }

                mainItemList.add(mainItemModel);
            }

//            mainItemList.add(new MainItemModel(-1, "About", "Submit Review", R.drawable.review));
//            mainItemList.add(new MainItemModel(-2, "", "Share", R.drawable.share));
//            mainItemList.add(new MainItemModel(-3, "", "More Apps", R.drawable.more_apps));
//            mainItemList.add(new MainItemModel(-4, "", "Version", R.drawable.version));

            MainItemRecyclerViewAdapter adapter = new MainItemRecyclerViewAdapter(this, this, mainItemList);

            recyclerView.setLayoutManager(layoutManager);

            recyclerView.setAdapter(adapter);
        }
    }

    private void handleMainItemAction(View view, MainItemModel mainItemModel) {

        int languageId = mainItemModel.getId();

//        switch (languageId) {
//
//            case -1:
//                handleRateAction();
//                break;
//            case -2:
//                handleShareAction();
//                break;
//            case -3:
//                handleMoreAppsAction();
//                break;
//            case -4:
//                showAboutDialog();
//                break;
//
//            default:
        startServiceActivity(view, languageId);
        //   break;
    }

    private void startServiceActivity(View view, int languageId) {

        Intent intent = new Intent(this, ServiceActivity.class);
        intent.putExtra(Constants.ARG_LANGUAGE_ID, languageId);
        // ActivityAnimManager.startActivityWithAnimation(this, intent, 0, view);
        ActivityAnimManager.startRightActivity(this, intent, 0);
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

    private void openPlayStore(String url) {

        // Open the app page in Google Play store:
        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        startActivity(intent);
    }

    private void showUpdateDialog(boolean isVibrate, boolean isPlayNotification) {

        if (isUpdating) {
            // do nothing...
        } else {

            if (Connectivity.isInternetOn(this, isVibrate, isPlayNotification)) {

                isUpdating = false;
                isCancel = false;

                final View view = LayoutInflater.from(this).inflate(R.layout.dialog_update, null);

                imgViewIcon = view.findViewById(R.id.imageViewUpdateIcon);

                textViewUpdateMessage = view.findViewById(R.id.txtViewUpdateMessage);

                textViewUpdateMessage.setText("Connecting to Server...");

                rotateAnimation = new RotateAnimation(0, 360,
                        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotateAnimation.setRepeatCount(Animation.INFINITE);
                rotateAnimation.setRepeatMode(Animation.RESTART);
                rotateAnimation.setInterpolator(new LinearInterpolator());
                rotateAnimation.setDuration(1000);
                rotateAnimation.setFillEnabled(true);
                rotateAnimation.setFillAfter(true);

                imgViewIcon.startAnimation(rotateAnimation);

                AlertDialog.Builder updateBuilder = new AlertDialog.Builder(this);

                updateBuilder.setView(view);

                updateBuilder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        System.out.println("checkk On Cancel called");
                        isCancel = true;

                        if (isUpdating) {

                            if (masterData != null && !masterData.isCancelled()) {
                                masterData.cancel(true);
                            }

                            isUpdating = false;
                        }
                    }
                });

                VolleySingleton.getInstance(this).getRequestQueue().getCache().clear();

                String url = DBAdapter.getSettingsData().getDataPath();

                retrieveData(url);
                /*
                JsonObjectRequest jsObjRequest = new JsonObjectRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {

                                if (isCancel) {
                                    // do nothing...
                                } else {

                                    textViewUpdateMessage.setText("Checking for updates");

                                    masterData = new MasterData(MainActivity.this, response);
                                    masterData.execute();
                                }
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {

                                if (isCancel) {
                                    // do nothing...
                                } else {
                                    onPostUpdate("Could not connect to server", false, true);
                                }
                            }
                        });

                VolleySingleton.getInstance(this).addToRequestQueue(jsObjRequest);
            */
                // dialog = updateBuilder.create();
                //  dialog.show();

            } else {

                showSnackBar("No Internet Connection!");
            }
        }
    }

    private void retrieveData(String url) {
/*
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.POST, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {

                if (isCancel) {
                    // do nothing...
                } else {

                    textViewUpdateMessage.setText("Checking for updates");

                    masterData = new MasterData(MainActivity.this, jsonArray);
                    masterData.execute();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                if (isCancel) {
                    // do nothing...
                } else {
                    onPostUpdate("Could not connect to server", false, true);

                    Log.e(TAG, "Error: " + error.toString()) ;
                }
            }
        });

        VolleySingleton.getInstance(this).addToRequestQueue(jsonArrayRequest);
*/

        final JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

//                            if (isCancel) {
//                                // do nothing...
//                            } else {

                        // textViewUpdateMessage.setText("Checking for updates");

                        masterData = new MasterData(MainActivity.this, MainActivity.this, response);
                        masterData.execute();

                        //                          showMessageDialog("Message", response.toString());
                        //                          onPostUpdate("", false, false);
                        //}
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

//                        if (isCancel) {
//                            // do nothing...
//                        } else {
                        onPostUpdate("Could not connect to server", false, true);
                        Log.e(TAG, "JSONObject Error: " + error.toString());
                        //  }
                    }
                });

        VolleySingleton.getInstance(this).addToRequestQueue(jsObjRequest);

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
        // dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        dialog.show();
    }


    private void showSnackBar(String msg) {

//        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        Snackbar.make(mRootMain, msg, Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show();
    }

    private void applySettings(boolean isCheckUpdate) {

        SettingsModel setBean = DBAdapter.getSettingsData();

        String awake = setBean.getStayAwake();
        String update = setBean.getAutoUpdate();

        if (awake.equals(Constants.AWAKE_YES)) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }

        if (isCheckUpdate) {

            if (update.equals(Constants.AUTO_UPDATE_YES)) {

                showUpdateDialog(false, false);
            }
        }
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        swipeRefreshLayout.setEnabled(verticalOffset == 0);
    }


}
