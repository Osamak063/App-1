package com.ziaetaiba.islamicwebsite.activities;

import android.Manifest;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
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
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ziaetaiba.islamicwebsite.R;
import com.ziaetaiba.islamicwebsite.adapters.DetailRecyclerViewAdapter;
import com.ziaetaiba.islamicwebsite.adapters.NoConnectionRecyclerViewAdapter;
import com.ziaetaiba.islamicwebsite.callbacks.AppDataCallbacks;
import com.ziaetaiba.islamicwebsite.callbacks.DetailDataCallbacks;
import com.ziaetaiba.islamicwebsite.callbacks.DetailRecyclerViewCallbacks;
import com.ziaetaiba.islamicwebsite.callbacks.NoConnectionRecyclerViewCallbacks;
import com.ziaetaiba.islamicwebsite.components.ActivityAnimManager;
import com.ziaetaiba.islamicwebsite.components.Connectivity;
import com.ziaetaiba.islamicwebsite.components.DataUrls;
import com.ziaetaiba.islamicwebsite.components.MediaDownloadManager;
import com.ziaetaiba.islamicwebsite.components.SDCardManager;
import com.ziaetaiba.islamicwebsite.components.VolleySingleton;
import com.ziaetaiba.islamicwebsite.constants.Constants;
import com.ziaetaiba.islamicwebsite.constants.ZiaeTaibaData;
import com.ziaetaiba.islamicwebsite.database.DBAdapter;
import com.ziaetaiba.islamicwebsite.datamodels.AppModel;
import com.ziaetaiba.islamicwebsite.datamodels.ArticleModel;
import com.ziaetaiba.islamicwebsite.datamodels.BlogModel;
import com.ziaetaiba.islamicwebsite.datamodels.BookModel;
import com.ziaetaiba.islamicwebsite.datamodels.DayModel;
import com.ziaetaiba.islamicwebsite.datamodels.DepartmentModel;
import com.ziaetaiba.islamicwebsite.datamodels.DetailHeaderModel;
import com.ziaetaiba.islamicwebsite.datamodels.DetailItemModel;
import com.ziaetaiba.islamicwebsite.datamodels.GalleryModel;
import com.ziaetaiba.islamicwebsite.datamodels.HistoryModel;
import com.ziaetaiba.islamicwebsite.datamodels.LanguageModel;
import com.ziaetaiba.islamicwebsite.datamodels.MediaModel;
import com.ziaetaiba.islamicwebsite.datamodels.NewsModel;
import com.ziaetaiba.islamicwebsite.datamodels.NoConnectionModel;
import com.ziaetaiba.islamicwebsite.datamodels.PlaceModel;
import com.ziaetaiba.islamicwebsite.datamodels.PoetryModel;
import com.ziaetaiba.islamicwebsite.datamodels.ProductItemModel;
import com.ziaetaiba.islamicwebsite.datamodels.ProductModel;
import com.ziaetaiba.islamicwebsite.datamodels.ScholarModel;
import com.ziaetaiba.islamicwebsite.datamodels.ServiceModel;
import com.ziaetaiba.islamicwebsite.datamodels.SettingsModel;
import com.ziaetaiba.islamicwebsite.tasks.DetailData;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.StringTokenizer;

public class DetailActivity extends AppCompatActivity
    implements DetailRecyclerViewCallbacks,
        NoConnectionRecyclerViewCallbacks,
        AppDataCallbacks, DetailDataCallbacks {

    private static final String TAG = DetailActivity.class.getSimpleName();

    private CoordinatorLayout coordinatorLayout ;
    private RecyclerView recyclerView;

    private FloatingActionButton fabPlay ;

    private DetailData detailData;

    private boolean isCancel, isUpdating;

    private AppCompatDialog dialog ;
    private ImageView imgViewIcon ;
    private TextView textViewUpdateMessage;
    private RotateAnimation rotateAnimation ;

    private SharedPreferences sharedPreferences;
    private ServiceModel serviceModel;
    private ProductItemModel productItemModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setTheme(R.style.MyThemeOrange);
        setContentView(R.layout.activity_list_fab);

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

        fabPlay = (FloatingActionButton) findViewById(R.id.fabPlay);
        System.out.println("checkk Detail Activity");
        fabPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showUpdateDialog(true, false);
            }
        });


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

        Bundle bundle = intent.getExtras();

        int serviceId = bundle.getInt(Constants.ARG_SERVICE_ID);
        productItemModel = (ProductItemModel) bundle.getSerializable(Constants.ARG_PRODUCT_ITEM_MODEL);

        serviceModel = DBAdapter.getServiceModelById(serviceId);

        setTitle(serviceModel.getTitle());

        updateRecyclerView();

        applySettings();
    }

    @Override
    public void onDetailPostUpdate(String result, int serviceId, boolean isError) {

        /*
        try {

            JSONObject jsonObject = new JSONObject(result);
            detailData = new DetailData(DetailActivity.this, serviceId, jsonObject);
            detailData.execute();

        } catch (JSONException error) {

            onPostUpdate("Could not connect to server", false, true);
            Log.e(TAG, "JSONObject Error: " + error.toString()) ;
        }
        */
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
    public void onHandleNoConnectionItemAction(View view) {
        showSnackBar(Constants.CONTAINS_NO_DATA);
    }

    @Override
    public void onHandleDetailViewItemAction(View view, DetailItemModel detailItemBean) {
        handleDetailItemAction(view, detailItemBean);
    }

    @Override
    public void onHandleShareHeaderItemAction(View view) {
        handleShareAction();
    }

    @Override
    public void onHandleWebHeaderItemAction(View view) {
        handleWebAction();
    }

    @Override
    public void onHandleLikeHeaderItemAction(View view, ImageView imageView) {
        // handleLikeAction(view, imageView);
    }

    @Override
    public void onHandleDownloadHeaderItemAction(TextView textView, ImageView imageView) {
        handleDownloadAction(textView, imageView);
    }

    @Override
    public void onHandleViewHeaderItemAction(View view) {
        handleViewAction(view);
    }

    @Override
    public void onUpdateDownloadStatus(TextView textView, ImageView imageView) {
        updateDownloadStatus(textView, imageView);
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
        
        if (isUpdating) {

            if (detailData != null && !detailData.isCancelled()) {
                detailData.cancel(true);
            }

            isUpdating = false;
        }

        if (dialog != null && dialog.isShowing()) {

            dialog.dismiss();
            dialog = null;
        }

        unregisterReceiver(onDownloadComplete);
        unregisterReceiver(onNotificationClick);
    }

    private void updateRecyclerView() {

        int serviceNo = serviceModel.getNumber();
        int color = R.color.colorPrimaryLight_15 ;

        ArrayList<Object> detailList = new ArrayList<>();

        boolean isCancel = false;

        switch (serviceNo) {

            case ZiaeTaibaData.SERVICE_NO_BOOKS:        detailList = getBooksData();        break;
            case ZiaeTaibaData.SERVICE_NO_MEDIA:        detailList = getMediaData();        break;
            case ZiaeTaibaData.SERVICE_NO_SCHOLAR:      detailList = getScholarsData();     break;
            case ZiaeTaibaData.SERVICE_NO_GALLERY:      detailList = getGalleryData();      break;
            case ZiaeTaibaData.SERVICE_NO_NEWS:         detailList = getNewsData();         break;
            case ZiaeTaibaData.SERVICE_NO_PLACES:       detailList = getPlacesData();       break;
            case ZiaeTaibaData.SERVICE_NO_HISTORY:      detailList = getHistoryData();      break;
            case ZiaeTaibaData.SERVICE_NO_DAYS:         detailList = getDaysData();         break;
            case ZiaeTaibaData.SERVICE_NO_DEPARTMENTS:  detailList = getDepartmentsData();  break;
            case ZiaeTaibaData.SERVICE_NO_APPS:         detailList = getAppsData();         break;
            case ZiaeTaibaData.SERVICE_NO_ARTICLES:     detailList = getArticlesData();     break;
            case ZiaeTaibaData.SERVICE_NO_BLOGS:        detailList = getBlogsData();        break;
            case ZiaeTaibaData.SERVICE_NO_POETRY:       detailList = getPoetryData();       break;

            default:    isCancel = true;
        }

        if (isCancel) {

            ArrayList<NoConnectionModel> list = new ArrayList<>();

            list.add(new NoConnectionModel(1, Constants.CONTAINS_NO_DATA,"", R.drawable.ic_android_green));

            NoConnectionRecyclerViewAdapter adapter = new NoConnectionRecyclerViewAdapter(this, this, list, color);

            LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext());

            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

            recyclerView.setLayoutManager(layoutManager);

            recyclerView.setAdapter(adapter);

        } else {

            int languageId = serviceModel.getLanguageId();

            LanguageModel languageModel = DBAdapter.getLanguageModelById(languageId);

            int languageNo = languageModel.getNo();
            String langShortName = languageModel.getShortName();

            ProductModel productModel = DBAdapter.getDataTablesModelFromServiceId(serviceNo);

            String shortName = productModel.getShortName().substring(0, 2);

            String imageTitle = "l" + languageNo + "s" + serviceNo + "b" + shortName;

            DetailRecyclerViewAdapter adapter = new DetailRecyclerViewAdapter(this, this, imageTitle, detailList, langShortName);

            LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

            recyclerView.setLayoutManager(layoutManager);

            recyclerView.setAdapter(adapter);

            recyclerView.setHasFixedSize(true);
        }
    }

    private ArrayList<Object> getBooksData() {

        ArrayList<Object> dataList = new ArrayList<>() ;

        int productId = productItemModel.getId();

        BookModel bookModel = DBAdapter.getBookModelFromId(productId);

        int no = bookModel.getNo();

        String photoUrl = bookModel.getPhotoUrl();

        String website = bookModel.getWebsite();
        String pdfUrl = bookModel.getPdfUrl();
        String status = bookModel.getStatus();

        productItemModel.setWebsite(website);
        productItemModel.setDownloadUrl(pdfUrl);
        productItemModel.setExtension("pdf");

        boolean isWeb = false ;
        boolean isDownload = false ;
        boolean isView = false ;

        if (!TextUtils.isEmpty(website)) {
            isWeb = true ;
        }

        if (TextUtils.equals(status, Constants.STATUS_DOWNLOAD_YES)
                && !TextUtils.isEmpty(pdfUrl)) {

            isDownload = true ;
            isView = true ;
        }

        dataList.add(new DetailHeaderModel(1, no, isWeb, isWeb, isDownload, isView, photoUrl));

        dataList.add(new DetailItemModel(1, "Title:", bookModel.getTitle()));
        dataList.add(new DetailItemModel(2, "Category:", bookModel.getCategory()));
        dataList.add(new DetailItemModel(3, "Author:", bookModel.getAuthor()));
        dataList.add(new DetailItemModel(4, "Publisher:", bookModel.getPublisher()));

        String description = bookModel.getDescription();
        int mostView = bookModel.getMostView() ;
        int mostDownload = bookModel.getMostDownload();

        if (!TextUtils.isEmpty(description))
            dataList.add(new DetailItemModel(5, "Description:", description));

        if (mostView > 0)
            dataList.add(new DetailItemModel(6, "Viewed:", String.valueOf(mostView)));

        if (mostDownload > 0)
            dataList.add(new DetailItemModel(7, "Downloaded:", String.valueOf(mostDownload)));


        return dataList ;
    }

    private ArrayList<Object> getMediaData() {

        ArrayList<Object> dataList = new ArrayList<>() ;

        int productId = productItemModel.getId();

        MediaModel mediaModel = DBAdapter.getMediaModelFromId(productId);

        int no = mediaModel.getNo();

        String photoUrl = mediaModel.getPhotoUrl();

        String website = mediaModel.getWebsite();
        String status = mediaModel.getStatus();

        productItemModel.setWebsite(website);
        productItemModel.setExtension("pdf");

        boolean isWeb = false ;
        boolean isDownload = false ;
        boolean isView = false ;

        if (!TextUtils.isEmpty(website)) {
            isWeb = true ;
        }

        if (TextUtils.equals(status, Constants.STATUS_DOWNLOAD_YES)) {
            isDownload = false ;
            isView = true ;
        }

        dataList.add(new DetailHeaderModel(1, no, isWeb, isWeb, isDownload, isView, photoUrl));

        dataList.add(new DetailItemModel(1, "Title:", mediaModel.getTitle()));
        dataList.add(new DetailItemModel(2, "Category:", mediaModel.getCategory()));
        dataList.add(new DetailItemModel(3, "Vocalist:", mediaModel.getVocalist()));
        dataList.add(new DetailItemModel(4, "Attribute:", mediaModel.getAttribute()));
        dataList.add(new DetailItemModel(5, "Language:", mediaModel.getLanguage()));

        String description = mediaModel.getDescription();
        int mostView = mediaModel.getMostView() ;
        int mostDownload = mediaModel.getMostDownload();

        if (!TextUtils.isEmpty(description))
            dataList.add(new DetailItemModel(6, "Description:", description));

        if (mostView > 0)
            dataList.add(new DetailItemModel(7, "Viewed:", String.valueOf(mostView)));

        if (mostDownload > 0)
            dataList.add(new DetailItemModel(8, "Downloaded:", String.valueOf(mostDownload)));

        return dataList ;
    }

    private ArrayList<Object> getScholarsData() {

        ArrayList<Object> dataList = new ArrayList<>() ;

        int productId = productItemModel.getId();

        ScholarModel scholarModel = DBAdapter.getScholarModelFromId(productId);

        String website = scholarModel.getWebsite();
        String status = scholarModel.getStatus();

        productItemModel.setWebsite(website);
        productItemModel.setExtension("");

        boolean isWeb = false ;
        boolean isDownload = false ;
        boolean isView = false;

        if (!TextUtils.isEmpty(website)) {
            isWeb = true ;
        }

        if (TextUtils.equals(status, Constants.STATUS_DOWNLOAD_YES)) {
            isDownload = true ;
            isView = true ;
        }

        int scholarNo = scholarModel.getNo();

        dataList.add(new DetailHeaderModel(1, scholarNo, isWeb, isWeb, isDownload, isView, scholarModel.getPhotoUrl()));

        dataList.add(new DetailItemModel(1, "Title:", scholarModel.getTitle()));
        dataList.add(new DetailItemModel(2, "Category:", scholarModel.getUrsDate()));

        String birthDate = scholarModel.getBirthDate();
        String ursDate = scholarModel.getUrsDate();
        String description = scholarModel.getDescription();
        int mostView = scholarModel.getMostView() ;

        if (!TextUtils.isEmpty(description))
            dataList.add(new DetailItemModel(3, "Date of Birth:", birthDate));

        if (!TextUtils.isEmpty(description))
            dataList.add(new DetailItemModel(4, "Date of Death (Urs):", ursDate));

        if (!TextUtils.isEmpty(description))
            dataList.add(new DetailItemModel(5, "Description:", description));

        if (mostView > 0)
            dataList.add(new DetailItemModel(6, "Viewed:", String.valueOf(mostView)));


        return dataList ;
    }

    private ArrayList<Object> getGalleryData() {

        ArrayList<Object> dataList = new ArrayList<>() ;

        int productId = productItemModel.getId();

        GalleryModel galleryModel = DBAdapter.getGalleryModelFromId(productId);

        String website = galleryModel.getWebsite();
        String imageUrl = galleryModel.getDownloadImage();
        String status = galleryModel.getStatus();

        productItemModel.setWebsite(website);
        productItemModel.setDownloadUrl(imageUrl);
        productItemModel.setExtension("png");

        boolean isWeb = false ;
        boolean isDownload = false ;
        boolean isView = false ;

        if (!TextUtils.isEmpty(website)) {
            isWeb = true ;
        }

        if (TextUtils.equals(status, Constants.STATUS_DOWNLOAD_YES)) {
            isDownload = true ;
            isView = true ;
        }

        int no = galleryModel.getNo();
        int mostView = galleryModel.getMostView() ;
        int mostDownload = galleryModel.getMostDownload();

        dataList.add(new DetailHeaderModel(1, no, isWeb, isWeb, isDownload, isView, galleryModel.getPhotoUrl()));

        dataList.add(new DetailItemModel(1, "Title:", galleryModel.getTitle()));
        dataList.add(new DetailItemModel(2, "Category:", galleryModel.getCategory()));

        String description = galleryModel.getDescription();

        if (!TextUtils.isEmpty(description))
            dataList.add(new DetailItemModel(3, "Description:", description));

        if (mostView > 0)
            dataList.add(new DetailItemModel(7, "Viewed:", String.valueOf(mostView)));

        if (mostDownload > 0)
            dataList.add(new DetailItemModel(8, "Downloaded:", String.valueOf(mostDownload)));

        return dataList ;
    }

    private ArrayList<Object> getNewsData() {

        ArrayList<Object> dataList = new ArrayList<>() ;

        int productId = productItemModel.getId();

        NewsModel newsModel = DBAdapter.getNewsModelFromId(productId);

        int no = newsModel.getNo();

        String website = newsModel.getWebsite();
        String status = newsModel.getStatus();

        productItemModel.setWebsite(website);
        productItemModel.setExtension("");

        boolean isWeb = false ;
        boolean isDownload = false ;
        boolean isView = false ;

        if (!TextUtils.isEmpty(website)) {
            isWeb = true ;
        }

        if (TextUtils.equals(status, Constants.STATUS_DOWNLOAD_YES)) {
            isDownload = true ;
            isView = true ;
        }

        dataList.add(new DetailHeaderModel(1, no, isWeb, isWeb, isDownload, isView, newsModel.getPhotoUrl()));

        dataList.add(new DetailItemModel(1, "Title:", newsModel.getTitle()));

        String organization = newsModel.getOrganization();

        if (!TextUtils.isEmpty(organization))
            dataList.add(new DetailItemModel(2, "Organization:", organization));

        String country = newsModel.getCountry();

        if (!TextUtils.isEmpty(country))
            dataList.add(new DetailItemModel(3, "Country:", country));

        String newspaper = newsModel.getNewsPaper();

        if (!TextUtils.isEmpty(newspaper))
            dataList.add(new DetailItemModel(4, "News Paper:", newspaper));

        String description = newsModel.getDescription();

        if (!TextUtils.isEmpty(description))
            dataList.add(new DetailItemModel(5, "Description:", description));

        int mostView = newsModel.getMostView();

        if (mostView > 0)
            dataList.add(new DetailItemModel(6, "Viewed:", String.valueOf(mostView)));

        return dataList ;
    }

    private ArrayList<Object> getPlacesData() {

        ArrayList<Object> dataList = new ArrayList<>() ;

        int productId = productItemModel.getId();

        PlaceModel placeModel = DBAdapter.getPlaceModelFromId(productId);

        int no = placeModel.getNo();

        String website = placeModel.getWebsite();
        String status = placeModel.getStatus();

        productItemModel.setWebsite(website);
        productItemModel.setExtension("");

        boolean isWeb = false ;
        boolean isDownload = false ;
        boolean isView = false ;

        if (!TextUtils.isEmpty(website)) {
            isWeb = true ;
        }

        if (TextUtils.equals(status, Constants.STATUS_DOWNLOAD_YES)) {
            isDownload = true ;
            isView = true ;
        }
        dataList.add(new DetailHeaderModel(1, no, isWeb, isWeb, isDownload, isView, placeModel.getPhotoUrl()));

        dataList.add(new DetailItemModel(1, "Title:", placeModel.getTitle()));
        dataList.add(new DetailItemModel(2, "Category:", placeModel.getCategory()));

        String description = placeModel.getDescription();

        if (!TextUtils.isEmpty(description))
            dataList.add(new DetailItemModel(3, "Description:", description));

        int mostView = placeModel.getMostView();

        if (mostView > 0)
            dataList.add(new DetailItemModel(4, "Viewed:", String.valueOf(mostView)));

        return dataList ;
    }

    private ArrayList<Object> getHistoryData() {

        ArrayList<Object> dataList = new ArrayList<>() ;

        int productId = productItemModel.getId();

        HistoryModel historyModel = DBAdapter.getHistoryModelFromId(productId);

        int no = historyModel.getNo();

        String website = historyModel.getWebsite();
        String status = historyModel.getStatus();

        productItemModel.setWebsite(website);
        productItemModel.setExtension("");

        boolean isWeb = false ;
        boolean isDownload = false ;
        boolean isView = false ;

        if (!TextUtils.isEmpty(website)) {
            isWeb = true ;
        }

        if (TextUtils.equals(status, Constants.STATUS_DOWNLOAD_YES)) {
            isDownload = true ;
            isView = true ;
        }

        dataList.add(new DetailHeaderModel(1, no, isWeb, isWeb, isDownload, isView, historyModel.getPhotoUrl()));

        dataList.add(new DetailItemModel(1, "Title:", historyModel.getTitle()));
        dataList.add(new DetailItemModel(2, "Category:", historyModel.getCategory()));

        String description = historyModel.getDescription();

        if (!TextUtils.isEmpty(description))
            dataList.add(new DetailItemModel(3, "Description:", description));

        int mostView = historyModel.getMostView();

        if (mostView > 0)
            dataList.add(new DetailItemModel(4, "Viewed:", String.valueOf(mostView)));

        return dataList ;
    }

    private ArrayList<Object> getDaysData() {

        ArrayList<Object> dataList = new ArrayList<>() ;

        int productId = productItemModel.getId();

        DayModel dayModel = DBAdapter.getDayModelFromId(productId);

        int no = dayModel.getNo();
        String website = dayModel.getWebsite();
        String status = dayModel.getStatus();

        productItemModel.setWebsite(website);
        productItemModel.setExtension("");

        boolean isWeb = false ;
        boolean isDownload = false ;
        boolean isView = false ;

        if (!TextUtils.isEmpty(website)) {
            isWeb = true ;
        }

        if (TextUtils.equals(status, Constants.STATUS_DOWNLOAD_YES)) {
            isDownload = true ;
            isView = true ;
        }

        dataList.add(new DetailHeaderModel(1, no, isWeb, isWeb, isDownload, isView, dayModel.getPhotoUrl()));

        dataList.add(new DetailItemModel(1, "Title:", dayModel.getTitle()));

        String description = dayModel.getDescription();

        if (!TextUtils.isEmpty(description))
            dataList.add(new DetailItemModel(2, "Description:", description));

        int mostView = dayModel.getMostView();

        if (mostView > 0)
            dataList.add(new DetailItemModel(3, "Viewed:", String.valueOf(mostView)));

        return dataList ;
    }

    private ArrayList<Object> getDepartmentsData() {

        ArrayList<Object> dataList = new ArrayList<>() ;

        int productId = productItemModel.getId();

        DepartmentModel departmentModel = DBAdapter.getDepartmentModelFromId(productId);

        int no = departmentModel.getNo();
        String website = departmentModel.getWebsite();
        String status = departmentModel.getStatus();

        productItemModel.setWebsite(website);
        productItemModel.setExtension("");

        boolean isWeb = false ;
        boolean isDownload = false ;
        boolean isView = false ;

        if (!TextUtils.isEmpty(website)) {
            isWeb = true ;
        }

        if (TextUtils.equals(status, Constants.STATUS_DOWNLOAD_YES)) {
            isDownload = true ;
            isView = true ;
        }

        dataList.add(new DetailHeaderModel(1, no, isWeb, isWeb, isDownload, isView, departmentModel.getPhotoUrl()));

        dataList.add(new DetailItemModel(1, "Title:", departmentModel.getTitle()));

        String description = departmentModel.getDescription();

        if (!TextUtils.isEmpty(description))
            dataList.add(new DetailItemModel(2, "Description:", description));

        int mostView = departmentModel.getMostView();

        if (mostView > 0)
            dataList.add(new DetailItemModel(3, "Viewed:", String.valueOf(mostView)));

        return dataList ;
    }

    private ArrayList<Object> getAppsData() {

        ArrayList<Object> dataList = new ArrayList<>() ;

        int productId = productItemModel.getId();

        AppModel appModel = DBAdapter.getAppsModelFromId(productId);

        int no = appModel.getNo();
        String website = appModel.getWebsite();
        String url = appModel.getDownloadApp();
        String status = appModel.getStatus();

        productItemModel.setWebsite(website);
        productItemModel.setDownloadUrl(url);
        productItemModel.setExtension("");

        boolean isWeb = false ;
        boolean isDownload = false ;
        boolean isView = false ;

        if (!TextUtils.isEmpty(website)) {
            isWeb = true ;
        }

        if (TextUtils.equals(status, Constants.STATUS_DOWNLOAD_YES)) {
            isDownload = true ;
            isView = true ;
        }

        dataList.add(new DetailHeaderModel(1, no, isWeb, isWeb, isDownload, isView, appModel.getPhotoUrl()));

        dataList.add(new DetailItemModel(1, "Title:", appModel.getTitle()));

        String description = appModel.getDescription();

        if (!TextUtils.isEmpty(description))
            dataList.add(new DetailItemModel(2, "Description:", description));

        int mostView = appModel.getMostView();

        if (mostView > 0)
            dataList.add(new DetailItemModel(3, "Viewed:", String.valueOf(mostView)));

        return dataList ;
    }

    private ArrayList<Object> getArticlesData() {

        ArrayList<Object> dataList = new ArrayList<>() ;

        int productId = productItemModel.getId();

        ArticleModel articleModel = DBAdapter.getArticleModelFromId(productId);

        int no = articleModel.getNo();
        String website = articleModel.getWebsite();
        String status = articleModel.getStatus();

        productItemModel.setWebsite(website);
        productItemModel.setExtension("");

        boolean isWeb = false ;
        boolean isDownload = false ;
        boolean isView = false ;

        if (!TextUtils.isEmpty(website)) {
            isWeb = true ;
        }

        if (TextUtils.equals(status, Constants.STATUS_DOWNLOAD_YES)) {
            isDownload = true ;
            isView = true ;
        }

        dataList.add(new DetailHeaderModel(1, no, isWeb, isWeb, isDownload, isView, articleModel.getPhotoUrl()));

        dataList.add(new DetailItemModel(1, "Title:", articleModel.getTitle()));
        dataList.add(new DetailItemModel(2, "Category:", articleModel.getCategory()));
        dataList.add(new DetailItemModel(3, "Writer:", articleModel.getWriter()));

        String description = articleModel.getDescription();

        if (!TextUtils.isEmpty(description))
            dataList.add(new DetailItemModel(4, "Description:", description));

        int mostView = articleModel.getMostView();

        if (mostView > 0)
            dataList.add(new DetailItemModel(5, "Viewed:", String.valueOf(mostView)));


        return dataList ;
    }

    private ArrayList<Object> getBlogsData() {

        ArrayList<Object> dataList = new ArrayList<>() ;

        int productId = productItemModel.getId();

        BlogModel blogModel = DBAdapter.getBlogModelFromId(productId);

        int no = blogModel.getNo();
        String website = blogModel.getWebsite();
        String status = blogModel.getStatus();

        productItemModel.setWebsite(website);
        productItemModel.setExtension("");

        boolean isWeb = false ;
        boolean isDownload = false ;
        boolean isView = false ;

        if (!TextUtils.isEmpty(website)) {
            isWeb = true ;
        }

        if (TextUtils.equals(status, Constants.STATUS_DOWNLOAD_YES)) {
            isDownload = true ;
            isView = true ;
        }

        dataList.add(new DetailHeaderModel(1, no, isWeb, isWeb, isDownload, isView, blogModel.getPhotoUrl()));

        dataList.add(new DetailItemModel(1, "Title:", blogModel.getTitle()));
        dataList.add(new DetailItemModel(2, "Category:", blogModel.getCategory()));
        dataList.add(new DetailItemModel(3, "Writer:", blogModel.getWriter()));

        String description = blogModel.getDescription();

        if (!TextUtils.isEmpty(description))
            dataList.add(new DetailItemModel(4, "Description:", description));

        int mostView = blogModel.getMostView();

        if (mostView > 0)
            dataList.add(new DetailItemModel(5, "Viewed:", String.valueOf(mostView)));

        return dataList ;
    }

    private ArrayList<Object> getPoetryData() {

        ArrayList<Object> dataList = new ArrayList<>() ;

        int productId = productItemModel.getId();

        PoetryModel poetryModel = DBAdapter.getPoetryModelFromId(productId);

        int no = poetryModel.getNo();
        String website = poetryModel.getWebsite();
        String status = poetryModel.getStatus();

        productItemModel.setWebsite(website);
        productItemModel.setExtension("");

        boolean isWeb = false ;
        boolean isDownload = false ;
        boolean isView = false ;

        if (!TextUtils.isEmpty(website)) {
            isWeb = true ;
        }

        if (TextUtils.equals(status, Constants.STATUS_DOWNLOAD_YES)) {
            isDownload = true ;
            isView = true ;
        }

        dataList.add(new DetailHeaderModel(1, no, isWeb, isWeb, isDownload, isView, poetryModel.getPhotoUrl()));

        dataList.add(new DetailItemModel(1, "Title:", poetryModel.getTitle()));
        dataList.add(new DetailItemModel(2, "Category:", poetryModel.getCategory()));
        dataList.add(new DetailItemModel(3, "Writer:", poetryModel.getWriter()));

        String description = poetryModel.getDescription();

        if (!TextUtils.isEmpty(description))
            dataList.add(new DetailItemModel(4, "Description:", description));

        int mostView = poetryModel.getMostView();

        if (mostView > 0)
            dataList.add(new DetailItemModel(5, "Viewed:", String.valueOf(mostView)));

        return dataList ;
    }

    private void handleShareAction() {

        String title = productItemModel.getTitle();
        String website = productItemModel.getWebsite();

        String msg = "Zia e Taiba - " + serviceModel.getTitle() ;
        msg += "\nTitle: " + title;

        if (TextUtils.isEmpty(website)) {
            // do nothin....
        } else {
            msg += "\nUrl: " + website;
        }

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, msg);
        startActivity(Intent.createChooser(intent, "Share with"));
    }

    private void handleWebAction() {

        if (Connectivity.isInternetOn(this, false, false)) {

            String url = productItemModel.getWebsite();

            if (TextUtils.isEmpty(url)) {
                url = "http://www.ziaetaiba.com" ;
            }

            // Open the app page in Google Play store:
            final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

            startActivity(intent);

        } else {

            showSnackBar("No Internet Connection.");
        }
    }

    private void handleDownloadAction (TextView textView, ImageView imageView) {

        int itemNo = productItemModel.getNo();
        String title = productItemModel.getTitle();
        String extension = productItemModel.getExtension();

        boolean isDownloading = isMediaDownloading(itemNo);

        if (isDownloading) {

            showCancelDownloadDialog(textView, imageView);

        } else {

            String videoStatus = SDCardManager.getMediaStatus(title, extension);

            if (videoStatus.equals(Constants.SD_CARD_NOT_AVAILABLE)) {

                showMessageDialog("Error!", "SD card is not available.");

            } else if (videoStatus.equals(Constants.PHOTO_AVAILABLE)) {

                showDeleteDialog(textView, imageView);

            } else {

                if (Connectivity.isInternetOn(this, false, false)) {

                    boolean isGranted = isStoragePermissionGranted();

                    if (isGranted) {
                        handleStartDownload(textView, imageView);
                    }

                } else {

                    showMessageDialog("Message!", "No Internet Connection.");
                }
            }
        }
    }

    private void handleViewAction (View view) {

        int number = serviceModel.getNumber();

        if (number == ZiaeTaibaData.SERVICE_NO_MEDIA) {

            int mediaId = productItemModel.getId();

            Intent intent = new Intent(this, MediaActivity.class);

            intent.putExtra(Constants.ARG_MEDIA_ID, mediaId);

            ActivityAnimManager.startActivityWithAnimation(this, intent, 0, view);

        } else {

            String title = productItemModel.getTitle();
            String extension = productItemModel.getExtension();

            String path = SDCardManager.getMediaPath(title, extension);

            File file = new File(path);

            if (file.exists()) {

                Uri uri = Uri.fromFile(file);
                Intent intent = new Intent(Intent.ACTION_VIEW);

                if (TextUtils.equals(extension, "pdf"))
                    intent.setDataAndType(uri, "application/" + extension);
                else if (TextUtils.equals(extension, "png"))
                    intent.setDataAndType(uri, "image/" + extension);
                else
                    intent.setDataAndType(uri, "*/" + extension);

                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                try {

                    startActivity(intent);

                } catch (ActivityNotFoundException e) {

                    showMessageDialog("Message!", "No application is found to view it.");
                }

            } else {
                showMessageDialog("Message!", "Please download it first.");
            }

        }
    }

    private void handleStartDownload(TextView textView, ImageView imageView) {

        String url = productItemModel.getDownloadUrl();

        int itemNo = productItemModel.getNo();
        String title = productItemModel.getTitle();
        String extension = productItemModel.getExtension();

        String strMediaNo = String.valueOf(itemNo);

        String path = SDCardManager.getMediaPath(title, extension);

        long downloadReference = MediaDownloadManager.startDownload(title, url, path);
        sharedPreferences.edit().putLong(strMediaNo, downloadReference).commit();

        updateDownloadStatus(textView, imageView);
    }

    private void updateDownloadStatus(TextView textView, ImageView imageView) {

        int itemNo = productItemModel.getNo();
        String title = productItemModel.getTitle();
        String extension = productItemModel.getExtension();

        boolean isDownloading = isMediaDownloading(itemNo);

        if (isDownloading) {

            imageView.setImageDrawable(this.getResources().getDrawable(R.mipmap.ic_clear_black_48dp));
            textView.setText("Cancel");

        } else {

            String videoStatus = SDCardManager.getMediaStatus(title, extension);

            if (videoStatus.equals(Constants.PHOTO_AVAILABLE)) {

                imageView.setImageDrawable(this.getResources().getDrawable(R.mipmap.ic_delete_black_48dp));
                textView.setText("Delete");

            } else {

                imageView.setImageDrawable(this.getResources().getDrawable(R.mipmap.ic_file_download_black_48dp));
                textView.setText("Download");
            }
        }
    }

    private void handleDetailItemAction (View view, DetailItemModel detailItemModel) {

        String title = detailItemModel.getTitle();
        String detail = detailItemModel.getDetail();

        showSnackBar(title + ": " + detail);
    }

    private void startPlayActivity(View view, String path) {


    }

    public boolean isMediaDownloading(int itemNo) {

        String strItemNo = String.valueOf(itemNo);
        boolean isDownloading = false;

        long downloadReference = sharedPreferences.getLong(strItemNo, -1);

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
                sharedPreferences.edit().remove(strItemNo).commit();
            }

        } else {
            isDownloading = false;
        }

        return isDownloading;
    }

    private String getDurationString(long duration) {

        int minutes = (int) (duration / 60);
        int seconds = (int) (duration - (minutes * 60));

        int hours = minutes / 60;
        minutes -= hours * 60;

        return formatNumber(hours) + ":" + formatNumber(minutes) + ":" + formatNumber(seconds) ;
    }

    private String formatNumber(int num) {

        String text = "";
        String strNum = num + "";

        if (strNum.length() < 2) {
            text = "0" + strNum;
        } else {
            text = strNum;
        }

        return text;
    }

    private String getSizeString(float roundKbNum) {

        float KILO_BYTE = 1024.0f;
        String text = "";

        String strKbNum = roundKbNum + "" ;

        StringTokenizer token = new StringTokenizer(strKbNum, ".");

        String strNum = token.nextToken();

        int length = strNum.length();

        if (length <= 0) {

            text = "";

        } else if (length <= 3) {

            float roundFloat = (float) Math.round(roundKbNum * 100) / 100;
            text = getFormattedStringOfNam(roundFloat) + " KB";

        } else if (length <= 6) {

            float sizeFloat = roundKbNum / KILO_BYTE;
            float roundFloat = (float) Math.round(sizeFloat * 100) / 100;
            text = getFormattedStringOfNam(roundFloat) + " MB";

        } else if (length <= 9) {

            float sizeFloat = roundKbNum / (KILO_BYTE * KILO_BYTE);
            float roundFloat = (float) Math.round(sizeFloat * 100) / 100;
            text = getFormattedStringOfNam(roundFloat) + " GB";
        }

        return text;
    }

    private String getFormattedStringOfNam(float num) {

        String text = "";
        String strNum = num + "";

        StringTokenizer token = new StringTokenizer(strNum, ".");

        token.nextToken(); // first token

        String next = token.nextToken();

        if (next.length() < 2) {
            text = strNum + "0";
        } else {
            text = strNum;
        }

        return text;
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

                            if (detailData != null && !detailData.isCancelled()) {
                                detailData.cancel(true);
                            }

                            isUpdating = false;
                        }
                    }
                });

                VolleySingleton.getInstance(this).getRequestQueue().getCache().clear();

                int serviceId = serviceModel.getId();
                int serviceNo = serviceModel.getNumber();
                int languageId = serviceModel.getLanguageId();

                String langShortName = DBAdapter.getLanguageModelById(languageId).getShortName() ;

                int productNo = productItemModel.getNo();

                String url = DataUrls.getDetailDataUrl(langShortName, serviceNo, productNo);

                retrieveData(languageId, serviceId, url);

            //    new GetDetailJsonData(this, url, serviceId).execute() ;

                dialog = updateBuilder.create();
                dialog.show();

            } else {

                showSnackBar("No Internet Connection!");
            }
        }
    }
    private void retrieveData (final int languageId, final int serviceId, final String url) {

        final JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        if (isCancel) {
                            // do nothing...
                        } else {

                            textViewUpdateMessage.setText("Checking for updates");

                            detailData = new DetailData(DetailActivity.this, DetailActivity.this, languageId, serviceId, response);
                            detailData.execute();

                        //    showMessageDialog("Message!", response.toString());
                        //    onPostUpdate("", false, false);
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

        VolleySingleton.getInstance(this).addToRequestQueue(jsObjRequest);

    }

    private void showSnackBar(String msg) {

//        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        Snackbar.make(coordinatorLayout, msg, Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show();
    }

    private void showCancelDownloadDialog(final TextView textView, final ImageView imageView) {

        final int itemNo = productItemModel.getNo();
        String title = productItemModel.getTitle();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        String cTitle = "Stop Download!";
        String msg = "Stop the download of media '" + title + "'?";

        builder.setTitle(cTitle);
        builder.setMessage(msg);
        builder.setCancelable(false);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(isMediaDownloading(itemNo)) {

                    String strMediaNo = itemNo + "";

                    long downloadReference = sharedPreferences.getLong(strMediaNo, -1);

                    if (downloadReference > -1) {

                        MediaDownloadManager.cancelDownload(downloadReference);

                        sharedPreferences.edit().remove(strMediaNo).commit();

                        imageView.setImageDrawable(getResources().getDrawable(R.mipmap.ic_file_download_black_48dp));
                        textView.setText("Download");

                    } else {
                        // do nothing...
                    }
                } else {
                    updateDownloadStatus(textView, imageView);
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

    private void showDeleteDialog(final TextView textView, final ImageView imageView) {

        int itemNo = productItemModel.getNo();
        final String title = productItemModel.getTitle();
        final String extension = productItemModel.getExtension();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        String delTitle = "Delete Data!";
        String msg = "The media '" + title + "' will be deleted.";

        builder.setTitle(delTitle);
        builder.setMessage(msg);
        builder.setCancelable(false);

        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String msg = "";

                String path = SDCardManager.getMediaPath(title, extension);

                if (SDCardManager.deleteDirs(path)) {

                    msg = "Deleted Successfully.";

                    imageView.setImageDrawable(getResources().getDrawable(R.mipmap.ic_file_download_black_48dp));
                    textView.setText("Download");

                } else {
                    msg = "OOPS! this video clip was not deleted.";
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

    private void showMessageDialog (String title, String msg) {

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

                updateRecyclerView();
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