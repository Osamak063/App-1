package com.ziaetaiba.islamicwebsite.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ziaetaiba.islamicwebsite.R;
import com.ziaetaiba.islamicwebsite.adapters.ProductPagerAdapter;
import com.ziaetaiba.islamicwebsite.callbacks.AppDataCallbacks;
import com.ziaetaiba.islamicwebsite.callbacks.FragmentProductListCallbacks;
import com.ziaetaiba.islamicwebsite.components.ActivityAnimManager;
import com.ziaetaiba.islamicwebsite.components.Connectivity;
import com.ziaetaiba.islamicwebsite.components.DataUrls;
import com.ziaetaiba.islamicwebsite.components.VolleySingleton;
import com.ziaetaiba.islamicwebsite.constants.Constants;
import com.ziaetaiba.islamicwebsite.constants.ZiaeTaibaData;
import com.ziaetaiba.islamicwebsite.database.DBAdapter;
import com.ziaetaiba.islamicwebsite.datamodels.ProductItemModel;
import com.ziaetaiba.islamicwebsite.datamodels.ProductModel;
import com.ziaetaiba.islamicwebsite.datamodels.ServiceModel;
import com.ziaetaiba.islamicwebsite.datamodels.SettingsModel;
import com.ziaetaiba.islamicwebsite.tasks.ProductData;

import org.json.JSONObject;

import java.util.ArrayList;


public class ProductActivity extends AppCompatActivity
implements FragmentProductListCallbacks,
        AppDataCallbacks {

    private static final String TAG = ProductActivity.class.getSimpleName();

    private CoordinatorLayout coordinatorLayout ;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationView navigationView ;
    private TextView textViewNavigationTitle ;
    private TabLayout tabLayout ;
    private ViewPager viewPager;

    private NestedScrollView mNestedScrollView;
    private View mClickView;
    private CardView mCardView ;
    private TextView mTextViewTitle;
    private TextView mTextViewDetail;
    private ImageView mImageViewIcon ;

    private ServiceModel serviceModel;

    private ProductData productData;

    private boolean isCancel, isUpdating;

    private AppCompatDialog dialog ;
    private ImageView imgViewIcon ;
    private TextView textViewUpdateMessage;
    private RotateAnimation rotateAnimation ;

    private static final String PREFERENCES_FILE         = "data_models_activity_settings";
    private static final String PREF_USER_LEARNED_DRAWER = "data_models_navigation_drawer_learned";
    private static final long DRAWER_CLOSE_DELAY_MS = 250;
    private boolean mUserLearnedDrawer;

    private SharedPreferences sharedPreferences;
    private int colorPrimaryLight ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    //   sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

    //    int style = sharedPreferences.getInt(Constants.ARG_THEME_COLOR, Constants.COLOR_GREEN);

//        setTheme(style);

        setTheme(R.style.MyThemePurple);

        setContentView(R.layout.activity_drawer);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {

            actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.main_content);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        textViewNavigationTitle = (TextView) findViewById(R.id.textViewNavigationHeaderTitle);

        viewPager = (ViewPager) findViewById(R.id.viewpager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);

        mNestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);
        mClickView = findViewById(R.id.viewClickItem);
        mCardView = (CardView) findViewById(R.id.cardViewNoConnectionItem);
        mTextViewTitle = (TextView) findViewById(R.id.textViewNoConnectionItemTitle);
        mTextViewDetail = (TextView) findViewById(R.id.textViewNoConnectionItemDetail);
        mImageViewIcon = (ImageView) findViewById(R.id.imageViewNoConnectionItemIcon);
        System.out.println("checkk Product Activity");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                showUpdateDialog(true, false);
            }
        });

        mUserLearnedDrawer = Boolean.valueOf(readSharedSetting(this, PREF_USER_LEARNED_DRAWER, "false"));

        mDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close);

        // Tie DrawerLayout events to the ActionBarToggle
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        mTextViewTitle.setText(Constants.CONTAINS_NO_DATA);
        mTextViewDetail.setText("Try again?");
        mImageViewIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_android_orange));

        mClickView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdateDialog(true, false);
            }
        });

        if (DBAdapter.isDatabaseAvailable()) {
            // do nothing...
        } else {
            DBAdapter.init(this);
        }

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//            viewPager.setPageTransformer(true, new TabletTransformer());
//        }

        Intent intent = getIntent();

        int id = intent.getIntExtra(Constants.ARG_SERVICE_ID, 0);

        serviceModel = DBAdapter.getServiceModelById(id);

        setTitle(serviceModel.getTitle());

        setupDrawerContent();

        colorPrimaryLight = R.color.fab_material_lime_500;

        mCardView.setCardBackgroundColor(getResources().getColor(colorPrimaryLight));

        setupViewPager(Constants.TEXT_ALL);

        applySettings();
    }

    @Override
    public void onPreUpdate(String msg) {
        isUpdating = true ;
    }

    @Override
    public void onProgressUpdate(String msg, int percent) {
        // do nothing...
    }

    @Override
    public void onPostUpdate(String msg, boolean isNewUpdate, boolean isError) {

        isUpdating = false ;

        if (isCancel) {
            // do nothing...
        } else {

            if (rotateAnimation != null) {
                rotateAnimation.cancel();
            }

            if (!isError) {

                if (isNewUpdate) {
                    setupViewPager(Constants.TEXT_ALL);
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
    public void onHandleProductItemModelAction(View view, ServiceModel serviceModel, ProductItemModel productItemModel) {

        handleProductItemModelAction(view, serviceModel, productItemModel);
    }

    @Override
    public void onShowSnackBarAction(String msg) {
        showSnackBar(msg);
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

        if (mDrawerLayout.isDrawerOpen(navigationView)) {

            mDrawerLayout.closeDrawers();

        } else {

            super.onBackPressed();
            ActivityCompat.finishAfterTransition(this);
        }
    }

    protected void onDestroy() {

        super.onDestroy();

        if (isUpdating) {

            if (productData != null && !productData.isCancelled()) {
                productData.cancel(true);
            }

            isUpdating = false;
        }

        if (dialog != null && dialog.isShowing()) {

            dialog.dismiss();
            dialog = null;
        }
    }

    private void setupDrawerContent() {

        if (!mUserLearnedDrawer) {

            mDrawerLayout.openDrawer(GravityCompat.START);
            mUserLearnedDrawer = true;
            saveSharedSetting(this, PREF_USER_LEARNED_DRAWER, "true");
        }

//        textViewNavigationTitle.setText(serviceModel.getTitle());

        int serviceId = serviceModel.getId();

        ArrayList<String> titleList = new ArrayList<>();

        titleList.add(Constants.TEXT_ALL);
        titleList.addAll(DBAdapter.getAllMasterParents(serviceId));

        if (titleList.size() > 0) {

            Menu mDrawerMenu = navigationView.getMenu();

            for (String title: titleList) {

                mDrawerMenu.add(title);
/*
                if (!TextUtils.equals(parent, Constants.TEXT_ALL)) {

                    ArrayList<String> childList = DBAdapter.getAllMastersTitle(serviceId, parent);

  //                  SubMenu subMenu = mDrawerMenu.addSubMenu("Sub-" + parent);

                    for (String child: childList) {

//                        subMenu.add(child);
                        mDrawerMenu.add("     " + child);
                    }
                }
*/
            }

            MenuItem menuItem = mDrawerMenu.getItem(mDrawerMenu.size() - 1);
            menuItem.setTitle(menuItem.getTitle());
        }

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(final MenuItem menuItem) {

                        menuItem.setChecked(true);

                        final String title = menuItem.getTitle().toString();

                        // allow some time after closing the drawer before performing real navigation
                        // so the user can see what is happening
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        mDrawerLayout.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                setupViewPager(title);

                                // showSnackBar(title);
                                // int pageNo = albumTitleList.indexOf(title);
                                // viewPager.setCurrentItem(pageNo);

                            }
                        }, DRAWER_CLOSE_DELAY_MS);

                        return true;
                    }
                }
        );
    }

    private void setupViewPager(String parent) {

        int serviceId = serviceModel.getId();
        int serviceNo = serviceModel.getNumber();

        ProductModel productModel = DBAdapter.getDataTablesModelFromServiceId(serviceNo);

        String table = productModel.getTitle();
        String shortName = productModel.getShortName();

        int count = DBAdapter.getProductCount(table);

        if (count <= 0) {

            hideViewPager();

        } else {

            ArrayList<String> titleList = new ArrayList<>();

            if (TextUtils.equals(parent, Constants.TEXT_ALL)) {

                titleList.add(Constants.TEXT_ALL);
                titleList.add(Constants.TEXT_FEATURED);
                titleList.add(Constants.TEXT_MOST_VIEW);
/*
                boolean isDownload = ZiaeTaibaData.isServiceDownloadable(serviceNo);

                if (isDownload) {
                    titleList.add(Constants.TEXT_MOST_DOWNLOAD);
                }
*/
            } else {
                // title.replaceAll("[^a-zA-Z0-9. ]", "");
                String column = shortName + "_" + parent.replace(" ", "_").toLowerCase();

                titleList = DBAdapter.getAllProductTitle(serviceId, table, column);
            }

            if (titleList.isEmpty()) {

                hideViewPager();

            } else {

                ProductPagerAdapter pagerAdapter = new ProductPagerAdapter(getSupportFragmentManager(),
                        serviceId, colorPrimaryLight, parent, titleList);

                viewPager.setAdapter(pagerAdapter);

                tabLayout.setupWithViewPager(viewPager);
                tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

                viewPager.setVisibility(View.VISIBLE);
                tabLayout.setVisibility(View.VISIBLE);
                mNestedScrollView.setVisibility(View.GONE);
            }
        }
    }

    private void hideViewPager () {

        viewPager.setVisibility(View.GONE);
        tabLayout.setVisibility(View.GONE);
        mNestedScrollView.setVisibility(View.VISIBLE);
    }

    private void handleProductItemModelAction(View view, ServiceModel serviceModel, ProductItemModel productItemModel) {

        int serviceId = serviceModel.getId();

        Intent intent = new Intent(this, DetailActivity.class);

        Bundle bundle = new Bundle();

        bundle.putInt(Constants.ARG_SERVICE_ID, serviceId);
        bundle.putSerializable(Constants.ARG_PRODUCT_ITEM_MODEL, productItemModel);

        intent.putExtras(bundle);

        ActivityAnimManager.startActivityWithAnimation(this, intent, 0, view);
    }

    public static void saveSharedSetting(Context ctx, String settingName, String settingValue) {

        SharedPreferences sharedPref = ctx.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(settingName, settingValue);
        editor.apply();
    }

    public static String readSharedSetting(Context ctx, String settingName, String defaultValue) {

        SharedPreferences sharedPref = ctx.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        return sharedPref.getString(settingName, defaultValue);
    }

    private void showUpdateDialog (boolean isVibrate, boolean isPlayNotification) {

        if (isUpdating) {
            // do nothing...
        } else {

            if (Connectivity.isInternetOn(this, isVibrate, isPlayNotification)) {

                isUpdating = false;
                isCancel = false;

                final View view = LayoutInflater.from(this).inflate(R.layout.dialog_update, null);

                imgViewIcon = (ImageView) view.findViewById(R.id.imageViewUpdateIcon);

                textViewUpdateMessage = (TextView) view.findViewById(R.id.txtViewUpdateMessage);

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

                        isCancel = true;

                        if (isUpdating) {

                            if (productData != null && !productData.isCancelled()) {
                                productData.cancel(true);
                            }

                            isUpdating = false;
                        }
                    }
                });

                VolleySingleton.getInstance(this).getRequestQueue().getCache().clear();

                int serviceNo = serviceModel.getNumber();
                int languageId = serviceModel.getLanguageId();

                String langShortName = DBAdapter.getLanguageModelById(languageId).getShortName() ;

                String url = DataUrls.getProductDataUrl(langShortName, serviceNo);

                retrieveData(languageId, url);

                dialog = updateBuilder.create();
                dialog.show();

            } else {

                showSnackBar("No Internet Connection!");
            }
        }
    }

    private void retrieveData (final int languageId, final String url) {

        final JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        if (isCancel) {
                            // do nothing...
                        } else {

                            textViewUpdateMessage.setText("Checking for updates");

                            productData = new ProductData(ProductActivity.this, ProductActivity.this, languageId, response);
                            productData.execute();

                    //        showMessageDialog(response.toString());
                    //        onPostUpdate("", false, false);
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                        if (isCancel) {
                            // do nothing...
                        } else {
                            onPostUpdate("Could not connect to server", false, true);
                            Log.e(TAG, "JSONObject Error: " + error.toString()) ;
                        }
                    }
                });

        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getInstance(this).addToRequestQueue(jsObjRequest);

    }

    private void showSnackBar(String msg) {

//        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        Snackbar.make(coordinatorLayout, msg, Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show();
    }

    private void showMessageDialog (String msg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Message!");
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

    private void applySettings() {

        SettingsModel setBean = DBAdapter.getSettingsData();

        String awake = setBean.getStayAwake();
        String update = setBean.getAutoUpdate();

        if (awake.equals(Constants.AWAKE_YES)) {
            getWindow()
                    .addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } else {
            getWindow().clearFlags(
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }

        if (update.equals(Constants.AUTO_UPDATE_YES)) {
            showUpdateDialog(false, false);
        }
    }
}