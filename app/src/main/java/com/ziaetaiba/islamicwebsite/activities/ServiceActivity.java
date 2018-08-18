package com.ziaetaiba.islamicwebsite.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.ziaetaiba.islamicwebsite.R;
import com.ziaetaiba.islamicwebsite.adapters.ServiceRecyclerViewAdapter;
import com.ziaetaiba.islamicwebsite.adapters.NoConnectionRecyclerViewAdapter;
import com.ziaetaiba.islamicwebsite.callbacks.ServiceRecyclerViewCallbacks;
import com.ziaetaiba.islamicwebsite.callbacks.NoConnectionRecyclerViewCallbacks;
import com.ziaetaiba.islamicwebsite.components.ActivityAnimManager;
import com.ziaetaiba.islamicwebsite.constants.Constants;
import com.ziaetaiba.islamicwebsite.constants.ZiaeTaibaData;
import com.ziaetaiba.islamicwebsite.database.DBAdapter;
import com.ziaetaiba.islamicwebsite.datamodels.LanguageModel;
import com.ziaetaiba.islamicwebsite.datamodels.NoConnectionModel;
import com.ziaetaiba.islamicwebsite.datamodels.ServiceModel;
import com.ziaetaiba.islamicwebsite.datamodels.SettingsModel;

import java.util.ArrayList;

public class ServiceActivity extends AppCompatActivity
        implements ServiceRecyclerViewCallbacks,
        NoConnectionRecyclerViewCallbacks {

    private static final String TAG = ServiceActivity.class.getSimpleName();

    private CoordinatorLayout coordinatorLayout;
    private RecyclerView recyclerView;

    private LanguageModel languageModel;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setTheme(R.style.MyThemeGreen);
        setContentView(R.layout.activity_list_simple);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.main_content);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        System.out.println("checkk Service Activity");
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        if (DBAdapter.isDatabaseAvailable()) {
            // do nothing...
        } else {
            DBAdapter.init(this);
        }

        Intent intent = getIntent();

        int languageId = intent.getIntExtra(Constants.ARG_LANGUAGE_ID, 0);

        languageModel = DBAdapter.getLanguageModelById(languageId);

        String language = languageModel.getTitle();

        if (!TextUtils.isEmpty(language)) {
            setTitle(getResources().getString(R.string.app_name) + " - " + language);
        }

        updateRecyclerView();

        applySettings();
    }

    @Override
    public void onHandleCategoryItemAction(View view, ServiceModel serviceModel) {
        if (serviceModel.getNumber() == -1) {
            startActivity(new Intent(ServiceActivity.this, AboutActivity.class));
        } else {
            handleServiceItemAction(view, serviceModel);
        }
    }

    @Override
    public void onHandleNoConnectionItemAction(View view) {
        showSnackBar(Constants.CONTAINS_NO_DATA);
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
        System.out.println("checkk on back pressed");
        ActivityCompat.finishAfterTransition(this);
    }

    private void updateRecyclerView() {

        int languageId = languageModel.getId();
        int languageNo = languageModel.getNo();
        String shortName = languageModel.getShortName();
        int color = R.color.fab_material_amber_200;

        ArrayList<ServiceModel> categoryList = DBAdapter.getAllServices(languageId);
        categoryList.add(new ServiceModel(-1, -1, languageId, "About"
                , String.valueOf(R.drawable.about), "1"));
        if (categoryList.isEmpty()) {

            ArrayList<NoConnectionModel> list = new ArrayList<>();

            list.add(new NoConnectionModel(1, Constants.CONTAINS_NO_DATA, "", R.drawable.ic_android_blue));

            NoConnectionRecyclerViewAdapter adapter = new NoConnectionRecyclerViewAdapter(this, this, list, color);

            LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext());

            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

            recyclerView.setLayoutManager(layoutManager);

            recyclerView.setAdapter(adapter);

        } else {

            String imageTitle = "l" + String.valueOf(languageNo) + "s";

            ServiceRecyclerViewAdapter adapter = new ServiceRecyclerViewAdapter(this, this, imageTitle,
                    categoryList, shortName, color);

            int numColumns = getResources().getInteger(R.integer.no_columns);

            GridLayoutManager layoutManager = new GridLayoutManager(recyclerView.getContext(), numColumns);

            recyclerView.setLayoutManager(layoutManager);

            recyclerView.setAdapter(adapter);
        }
    }

    private void handleServiceItemAction(View view, ServiceModel serviceModel) {

        int id = serviceModel.getId();
        //      int no = serviceModel.getNumber();

//        int color = ZiaeTaibaData.getServiceColor(no);

//        sharedPreferences.edit().putInt(Constants.ARG_THEME_COLOR, color).commit();

        startProductActivity(view, id);
    }

    private void startProductActivity(View view, int serviceId) {

        Intent intent = new Intent(this, ProductActivity.class);

        intent.putExtra(Constants.ARG_SERVICE_ID, serviceId);

        ActivityAnimManager.startActivityWithAnimation(this, intent, 0, view);
    }

    private void showMessageDialog(String title, String msg, final boolean isQuote) {

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
        Snackbar.make(coordinatorLayout, msg, Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show();
    }

    private void applySettings() {

        SettingsModel setBean = DBAdapter.getSettingsData();

        String awake = setBean.getStayAwake();

        if (awake.equals(Constants.AWAKE_YES)) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }
}
