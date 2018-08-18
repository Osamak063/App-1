package com.ziaetaiba.islamicwebsite.tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.ziaetaiba.islamicwebsite.callbacks.AppDataCallbacks;
import com.ziaetaiba.islamicwebsite.components.SDCardManager;
import com.ziaetaiba.islamicwebsite.constants.Constants;
import com.ziaetaiba.islamicwebsite.constants.ZiaeTaibaData;
import com.ziaetaiba.islamicwebsite.database.DBAdapter;
import com.ziaetaiba.islamicwebsite.datamodels.AppModel;
import com.ziaetaiba.islamicwebsite.datamodels.ArticleModel;
import com.ziaetaiba.islamicwebsite.datamodels.AudioVideoModel;
import com.ziaetaiba.islamicwebsite.datamodels.BlogModel;
import com.ziaetaiba.islamicwebsite.datamodels.BookModel;
import com.ziaetaiba.islamicwebsite.datamodels.DayModel;
import com.ziaetaiba.islamicwebsite.datamodels.DepartmentModel;
import com.ziaetaiba.islamicwebsite.datamodels.GalleryModel;
import com.ziaetaiba.islamicwebsite.datamodels.HistoryModel;
import com.ziaetaiba.islamicwebsite.datamodels.MediaModel;
import com.ziaetaiba.islamicwebsite.datamodels.NewsModel;
import com.ziaetaiba.islamicwebsite.datamodels.PlaceModel;
import com.ziaetaiba.islamicwebsite.datamodels.PoetryModel;
import com.ziaetaiba.islamicwebsite.datamodels.ScholarModel;
import com.ziaetaiba.islamicwebsite.datamodels.ServiceModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class DetailData extends AsyncTask<Void, Integer, Void> {

    private static final String TAG = DetailData.class.getSimpleName();

    private static final boolean DEBUG = false;

    private static final String ROOT_DATA = "data";

    private static final String ROOT_PRODUCTS = "products";

    private static final String TAG_ITEM_ID             = "id";
    private static final String TAG_ITEM_DESCRIPTION    = "Description";
    private static final String TAG_ITEM_WEBSITE        = "website";
    private static final String TAG_ITEM_UPDATE_ON      = "Updated Date";
    private static final String TAG_ITEM_MOST_VIEW      = "most-view";
    private static final String TAG_ITEM_MOST_DOWNLOAD  = "most-download";
    private static final String TAG_ITEM_THUMBNAIL_URL  = "thumbnailUrl";

    private static final String TAG_ITEM_PDF_URL        = "Download Pdf" ;
    private static final String TAG_ITEM_AUDIO          = "audio" ;
    private static final String TAG_ITEM_VIDEO          = "video" ;

    private static final String TAG_ITEM_BIRTH_DATE     = "Date of Birth" ;
    private static final String TAG_ITEM_URS_DATE       = "Date of Death" ;

    private static final String TAG_ITEM_DOWNLOAD_IMAGE = "Download This Image" ;

    private static final String TAG_ITEM_DOWNLOAD_APP   = "Download App" ;

    private Activity mActivity ;
    private AppDataCallbacks mCallbacks;
    private JSONObject jsonObject;
    private int languageId ;
    private int serviceId;

    private boolean errorFlag;

    private int newDetails, updatedDetails;

    public DetailData(Activity mActivity, AppDataCallbacks mCallbacks, int languageId, int serviceId, JSONObject jsonObject) {

        this.mActivity = mActivity ;
        this.mCallbacks = mCallbacks;
        this.languageId = languageId ;
        this.serviceId = serviceId;
        this.jsonObject = jsonObject;

        errorFlag = false;
    }

    @Override
    protected Void doInBackground(Void... arg0) {

        if (!isCancelled()) {

            if (jsonObject == null || jsonObject.length() < 1) {

                publishProgress(-1);

            } else {

                if (!isCancelled()) {

                    try {

                        if (DEBUG) {
                            Log.e(TAG, "JsonArray > Array Size: " + jsonObject.length());
                        }

                        JSONArray dataArray = jsonObject.getJSONArray(ROOT_DATA);

                        if (dataArray == null || dataArray.length() < 1) {

                            // do nothing...
                        } else {

                            JSONObject dataObject = dataArray.getJSONObject(0);

                            if (dataObject == null || dataObject.length() < 1) {

                                // do nothing...
                            } else {

                                JSONArray productArray = dataObject.getJSONArray(ROOT_PRODUCTS);

                                if (productArray == null || productArray.length() < 1) {

                                    // do nothing...
                                } else {

                                    ServiceModel serviceModel = DBAdapter.getServiceModelById(serviceId);

                                    int number = serviceModel.getNumber();

                                    switch (number) {

                                        case ZiaeTaibaData.SERVICE_NO_BOOKS:        retrieveBookData(serviceId, productArray);         break;
                                        case ZiaeTaibaData.SERVICE_NO_MEDIA:        retrieveMediaData(serviceId, productArray);        break;
                                        case ZiaeTaibaData.SERVICE_NO_SCHOLAR:      retrieveScholarData(serviceId, productArray);      break;
                                        case ZiaeTaibaData.SERVICE_NO_GALLERY:      retrieveGalleryData(serviceId, productArray);      break;
                                        case ZiaeTaibaData.SERVICE_NO_NEWS:         retrieveNewsData(serviceId, productArray);         break;
                                        case ZiaeTaibaData.SERVICE_NO_PLACES:       retrievePlaceData(serviceId, productArray);        break;
                                        case ZiaeTaibaData.SERVICE_NO_HISTORY:      retrieveHistoryData(serviceId, productArray);      break;
                                        case ZiaeTaibaData.SERVICE_NO_DAYS:         retrieveDayData(serviceId, productArray);          break;
                                        case ZiaeTaibaData.SERVICE_NO_DEPARTMENTS:  retrieveDepartmentData(serviceId, productArray);   break;
                                        case ZiaeTaibaData.SERVICE_NO_APPS:         retrieveAppData(serviceId, productArray);          break;
                                        case ZiaeTaibaData.SERVICE_NO_ARTICLES:     retrieveArticleData(serviceId, productArray);      break;
                                        case ZiaeTaibaData.SERVICE_NO_BLOGS:        retrieveBlogData(serviceId, productArray);         break;
                                        case ZiaeTaibaData.SERVICE_NO_POETRY:       retrievePoetryData(serviceId, productArray);       break;
                                    }
                                }
                            }
                        }

                    } catch (JSONException e) {
                        Log.e("JSONException: ", e.toString());
                    } catch (NumberFormatException e) {
                        Log.e("NumberFormatException: ", e.toString());
                    }

                    publishProgress(0);
                }
            }
        }

        return null;
    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();

        newDetails = 0;
        updatedDetails = 0;

        if (mCallbacks != null) {
            mCallbacks.onPreUpdate("Connecting to server...");
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {

        super.onProgressUpdate(values);

        if (mCallbacks != null) {

            int num = values[0];

            if (num <= -1) {

                errorFlag = true;
                mCallbacks.onProgressUpdate("Could not connect to server.", 100);
            } else if (num == 0) {

                errorFlag = false;
                mCallbacks.onProgressUpdate("Checking for updates...", 0);
            } else {

                int total = values[1];
                float fPercent = ((float) num / total) * 100;
                int percent = (int) fPercent;
                errorFlag = false;
                mCallbacks.onProgressUpdate("Retrieving data...", percent);
            }
        }
    }

    @Override
    protected void onPostExecute(Void result) {

        super.onPostExecute(result);

        if (mCallbacks != null) {

            if (errorFlag) {
                mCallbacks.onPostUpdate("Could not connect to server.", false, true);
            } else {

                if (DEBUG) {

                    String msg = "Products: " + newDetails + " : " + updatedDetails;
                    Log.d("DetailData", msg);
                }

                if (newDetails > 0 || updatedDetails > 0) {

                    mCallbacks.onPostUpdate("Updated Successfully.", true, false);
                } else {
                    mCallbacks.onPostUpdate("No updates at this time.", false, false);
                }
            }
        }
    }

    private void retrieveBookData(int serviceId, JSONArray productArray) throws JSONException {

        for (int i = 0; i < productArray.length(); i++) {

            JSONObject productObject = productArray.getJSONObject(i);

            if (DEBUG) {
                int no = i + 1;
                Log.d(TAG, "JsonObject > Book # " + no + ": "
                        + productObject.length() + ", " + productObject.toString());
            }

            if (isCancelled()) {

                break;

            } else if (productObject == null || productObject.length() < 8) {
                // do nothing...
            } else {

                String mId = productObject.getString(TAG_ITEM_ID);
                String mDescription = productObject.getString(TAG_ITEM_DESCRIPTION);
                String mWebsite = productObject.getString(TAG_ITEM_WEBSITE);
                String mUpdateOn = productObject.getString(TAG_ITEM_UPDATE_ON);
                String mMostView = productObject.getString(TAG_ITEM_MOST_VIEW);
                String mMostDownload = productObject.getString(TAG_ITEM_MOST_DOWNLOAD);
                String mPdfUrl = productObject.getString(TAG_ITEM_PDF_URL);
                String mPhotoUrl = productObject.getString(TAG_ITEM_THUMBNAIL_URL);

                if (mId == null || mId.equals("") ||
                        mDescription == null || // mDescription.equals("") || // may be empty
                        mWebsite == null || mWebsite.equals("") ||
                        mUpdateOn == null || mUpdateOn.equals("") ||
                        mMostView == null || mMostView.equals("") ||
                        mMostDownload == null || mMostDownload.equals("")  ||
                        mPhotoUrl == null || mPhotoUrl.equals("")||
                        mPdfUrl == null || mPdfUrl.equals("")) {

                    // do nothing...
                } else {


                    String decodedWebsite = decodeUrl(mWebsite);
                    String decodedPhotoUrl = decodeUrl(mPhotoUrl);
                    String decodedPdfUrl = decodeUrl(mPdfUrl);

                    int id = addBookData(serviceId, mId, mDescription, decodedWebsite, mUpdateOn,
                            mMostView, mMostDownload, decodedPhotoUrl, decodedPdfUrl) ;
                }
            }
        }
    }

    private int addBookData(int serviceId, String strItemNo, String description, String website, String updateOn,
                            String strMostView, String strMostDownload, String photoUrl,
                            String pdfUrl) {

        int no = Integer.parseInt(strItemNo);
        int mostView = Integer.parseInt(strMostView);
        int mostDownload = Integer.parseInt(strMostDownload);

        BookModel bookModel = DBAdapter.getBookModel(no, serviceId);

        int id = bookModel.getId();

        if (id > 0) {

            String oldDescription = bookModel.getDescription();
            String oldWebsite = bookModel.getWebsite();
            String oldUpdateOn = bookModel.getUpdatedOn();
            int oldMostView = bookModel.getMostView();
            int oldMostDownload = bookModel.getMostDownload();
            String oldPdfUrl = bookModel.getPdfUrl();
            String oldPhotoUrl = bookModel.getPhotoUrl();

            if (TextUtils.equals(oldDescription, description)
                    && TextUtils.equals(oldWebsite, website)
                    && TextUtils.equals(oldUpdateOn, updateOn)
                    && oldMostView == mostView
                    && oldMostDownload == mostDownload
                    && TextUtils.equals(oldPdfUrl, pdfUrl)
                    && TextUtils.equals(oldPhotoUrl, photoUrl)) {

                // do nothing....
            } else {

                bookModel.setDescription(description);
                bookModel.setWebsite(website);
                bookModel.setUpdatedOn(updateOn);
                bookModel.setMostView(mostView);
                bookModel.setMostDownload(mostDownload);
                bookModel.setPdfUrl(pdfUrl);
                bookModel.setPhotoUrl(photoUrl);

                DBAdapter.updateBookData(bookModel);

                updatedDetails++;

                updateImage(oldPhotoUrl, photoUrl, serviceId, no);
            }

//            if (!TextUtils.equals(oldPdfUrl, pdfUrl)) {
//                SDCardManager.deleteThumbnail(mActivity, photoId);
//            }

        } else {
            // do nothing...
        }

        return id;
    }

    private void retrieveMediaData(int serviceId, JSONArray productArray) throws JSONException {

        for (int i = 0; i < productArray.length(); i++) {

            JSONObject productObject = productArray.getJSONObject(i);

            if (DEBUG) {
                int no = i + 1;
                Log.d(TAG, "JsonObject > Media # " + no + ": "
                        + productObject.length() + ", " + productObject.toString());
            }

            if (isCancelled()) {

                break;

            } else if (productObject == null || productObject.length() < 9) {
                // do nothing...
            } else {

                String mId = productObject.getString(TAG_ITEM_ID);
                String mDescription = productObject.getString(TAG_ITEM_DESCRIPTION);
                String mWebsite = productObject.getString(TAG_ITEM_WEBSITE);
                String mUpdateOn = productObject.getString(TAG_ITEM_UPDATE_ON);
                String mMostView = productObject.getString(TAG_ITEM_MOST_VIEW);
                String mMostDownload = productObject.getString(TAG_ITEM_MOST_DOWNLOAD);
                String mPhotoUrl = productObject.getString(TAG_ITEM_THUMBNAIL_URL);
                JSONArray mAudioArray = productObject.getJSONArray(TAG_ITEM_AUDIO);
                JSONArray mVideoArray = productObject.getJSONArray(TAG_ITEM_VIDEO);

                if (mId == null || mId.equals("") ||
                        mDescription == null || // mDescription.equals("") || // may be empty
                        mWebsite == null || mWebsite.equals("") ||
                        mUpdateOn == null || mUpdateOn.equals("") ||
                        mMostView == null || mMostView.equals("") ||
                        mMostDownload == null || mMostDownload.equals("") ||
                        mPhotoUrl == null || mPhotoUrl.equals("") ||
                        mAudioArray == null || mAudioArray.length() < 0 || // may be empty
                        mVideoArray == null || mVideoArray.length() < 0 ) { // may be empty

                    // do nothing...
                } else {


                    String decodedWebsite = decodeUrl(mWebsite);
                    String decodedPhotoUrl = decodeUrl(mPhotoUrl);

                    int id = addMediaData(serviceId, mId, mDescription, decodedWebsite, mUpdateOn,
                            mMostView, mMostDownload, decodedPhotoUrl) ;

                    if (id > 0) {

                        retrieveAudioVideoData(id, mAudioArray, Constants.ARG_TYPE_AUDIO) ;
                        retrieveAudioVideoData(id, mVideoArray, Constants.ARG_TYPE_VIDEO) ;
                    }
                }
            }
        }
    }

    private int addMediaData(int serviceId, String strItemNo, String description, String website, String updateOn,
                             String strMostView, String strMostDownload, String photoUrl) {

        int no = Integer.parseInt(strItemNo);
        int mostView = Integer.parseInt(strMostView);
        int mostDownload = Integer.parseInt(strMostDownload);

        MediaModel mediaModel = DBAdapter.getMediaModel(no, serviceId);

        int id = mediaModel.getId();

        if (id > 0) {

            String oldDescription = mediaModel.getDescription();
            String oldWebsite = mediaModel.getWebsite();
            String oldUpdateOn = mediaModel.getUpdatedOn();
            int oldMostView = mediaModel.getMostView();
            int oldMostDownload = mediaModel.getMostDownload();
            String oldPhotoUrl = mediaModel.getPhotoUrl();

            if (TextUtils.equals(oldDescription, description)
                    && TextUtils.equals(oldWebsite, website)
                    && TextUtils.equals(oldUpdateOn, updateOn)
                    && oldMostView == mostView
                    && oldMostDownload == mostDownload
                    && TextUtils.equals(oldPhotoUrl, photoUrl)) {

                // do nothing....
            } else {

                mediaModel.setDescription(description);
                mediaModel.setWebsite(website);
                mediaModel.setUpdatedOn(updateOn);
                mediaModel.setMostView(mostView);
                mediaModel.setMostDownload(mostDownload);
                mediaModel.setPhotoUrl(photoUrl);

                DBAdapter.updateMediaData(mediaModel);

                updatedDetails++;

                updateImage(oldPhotoUrl, photoUrl, serviceId, no);
            }

        } else {
            // do nothing...
        }

        return id;
    }

    private void retrieveAudioVideoData(int mediaId, JSONArray jsonArray, String type) throws JSONException {

        for (int i = 0; i < jsonArray.length(); i++) {

            JSONObject jsonObject = jsonArray.getJSONObject(i);

            if (DEBUG) {
                int no = i + 1;
                Log.d(TAG, "JsonObject > AudioVideo - " + type + " # " + no + ": "
                        + jsonObject.length() + ", " + jsonObject.toString());
            }

            if (isCancelled()) {

                break;

            } else if (jsonObject == null || jsonObject.length() < 1) {
                // do nothing...
            } else {

                String mTitle = jsonObject.names().getString(0);

                if (mTitle == null || mTitle.equals("")) {
                    // do nothing...
                } else {

                    String mUrl = jsonObject.getString(mTitle);

                    if (mUrl == null || mUrl.equals("")) {

                        // do nothing...
                    } else {

                        String decodedUrl = decodeUrl(mUrl);

                        int audioVideoId = addAudioVideoData(mediaId, mTitle, decodedUrl, type);
                    }
                }
            }
        }
    }

    private int addAudioVideoData(int mediaId, String title, String url, String type) {

        AudioVideoModel audioVideoModel = DBAdapter.getAudioVideoModel(mediaId, type);

        int audioVideoModelId = audioVideoModel.getId();

        if (audioVideoModelId > 0) {

            String oldTitle = audioVideoModel.getTitle();
            String oldUrl = audioVideoModel.getUrl();
            String oldType = audioVideoModel.getType();

            if (TextUtils.equals(oldTitle, title)
                    && TextUtils.equals(oldUrl, url)
                    && TextUtils.equals(oldType, type)) {

                // do nothing....
            } else {

                audioVideoModel.setTitle(title);
                audioVideoModel.setUrl(url);
                audioVideoModel.setType(type);

                DBAdapter.updateAudioVideoData(audioVideoModel);

                updatedDetails++;
            }

        } else {

            AudioVideoModel tempAudioVideoModel = new AudioVideoModel();

            tempAudioVideoModel.setId(0);
            tempAudioVideoModel.setMediaId(mediaId);
            tempAudioVideoModel.setTitle(title);
            tempAudioVideoModel.setUrl(url);
            tempAudioVideoModel.setType(type);

            long rowId = DBAdapter.addAudioVideoData(tempAudioVideoModel);

            if (rowId > -1) {
                audioVideoModelId = DBAdapter.getAudioVideoModel(mediaId, type).getId();
                updatedDetails++;
            } else {
                audioVideoModelId = 0;
            }
        }

        return audioVideoModelId;
    }

    private void retrieveScholarData(int serviceId, JSONArray productArray) throws JSONException {

        for (int i = 0; i < productArray.length(); i++) {

            JSONObject productObject = productArray.getJSONObject(i);

            if (DEBUG) {
                int no = i + 1;
                Log.d(TAG, "JsonObject > Scholar # " + no + ": "
                        + productObject.length() + ", " + productObject.toString());
            }

            if (isCancelled()) {

                break;

            } else if (productObject == null || productObject.length() < 7) {
                // do nothing...
            } else {

                String mId = productObject.getString(TAG_ITEM_ID);
                String mDescription = productObject.getString(TAG_ITEM_DESCRIPTION);
                String mWebsite = productObject.getString(TAG_ITEM_WEBSITE);
                String mMostView = productObject.getString(TAG_ITEM_MOST_VIEW);
                String mPhotoUrl = productObject.getString(TAG_ITEM_THUMBNAIL_URL);

                String mUpdateOn = "" ;

                try {
                    mUpdateOn = productObject.getString(TAG_ITEM_UPDATE_ON);
                } catch (JSONException ex) {
                    mUpdateOn = "-";
                }

                String mBirthDate = "" ;

                try {
                    productObject.getString(TAG_ITEM_BIRTH_DATE);
                } catch (JSONException ex) {
                    mBirthDate = "-";
                }

                String mUrsDate = "" ;

                try {
                    mUrsDate = productObject.getString(TAG_ITEM_URS_DATE);
                } catch (JSONException ex) {
                    mUrsDate = "-";
                }

                if (mId == null || mId.equals("") ||
                        mDescription == null || // mDescription.equals("") || // may be empty
                        mWebsite == null || mWebsite.equals("") ||
                        mUpdateOn == null || mUpdateOn.equals("") ||
                        mMostView == null || mMostView.equals("") ||
                        mBirthDate == null || mBirthDate.equals("") ||
                        mUrsDate == null || mUrsDate.equals("") ||
                        mPhotoUrl == null || mPhotoUrl.equals("")) {

                    // do nothing...
                } else {


                    String decodedWebsite = decodeUrl(mWebsite);
                    String decodedPhotoUrl = decodeUrl(mPhotoUrl);

                    int id = addScholarData(serviceId, mId, mDescription, decodedWebsite, mUpdateOn, mMostView, decodedPhotoUrl,
                            mBirthDate, mUrsDate) ;
                }
            }
        }
    }

    private int addScholarData(int serviceId, String strItemNo, String description, String website, String updateOn, String strMostView, String photoUrl,
                               String birthDate, String ursDate) {

        int no = Integer.parseInt(strItemNo);
        int mostView = Integer.parseInt(strMostView);
        
        ScholarModel scholarModel = DBAdapter.getScholarModel(no, serviceId);

        int id = scholarModel.getId();

        if (id > 0) {

            String oldDescription = scholarModel.getDescription();
            String oldWebsite = scholarModel.getWebsite();
            String oldUpdateOn = scholarModel.getUpdatedOn();
            int oldMostView = scholarModel.getMostView();
            String oldPhotoUrl = scholarModel.getPhotoUrl();
            String oldBirthDate = scholarModel.getBirthDate();
            String oldUrsDate = scholarModel.getUrsDate();

            if (TextUtils.equals(oldDescription, description)
                    && TextUtils.equals(oldWebsite, website)
                    && TextUtils.equals(oldUpdateOn, updateOn)
                    && oldMostView == mostView
                    && TextUtils.equals(oldPhotoUrl, photoUrl)
                    && TextUtils.equals(oldBirthDate, birthDate)
                    && TextUtils.equals(oldUrsDate, ursDate)) {

                // do nothing....
            } else {

                scholarModel.setDescription(description);
                scholarModel.setWebsite(website);
                scholarModel.setUpdatedOn(updateOn);
                scholarModel.setMostView(mostView);
                scholarModel.setMostDownload(0);
                scholarModel.setPhotoUrl(photoUrl);
                scholarModel.setBirthDate(birthDate);
                scholarModel.setUrsDate(ursDate);

                DBAdapter.updateScholarData(scholarModel);

                updatedDetails++;

                updateImage(oldPhotoUrl, photoUrl, serviceId, no);
            }

        } else {
            // do nothing...
        }

        return id;
    }

    private void retrieveGalleryData(int serviceId, JSONArray productArray) throws JSONException {

        for (int i = 0; i < productArray.length(); i++) {

            JSONObject productObject = productArray.getJSONObject(i);

            if (DEBUG) {
                int no = i + 1;
                Log.d(TAG, "JsonObject > Gallery # " + no + ": "
                        + productObject.length() + ", " + productObject.toString());
            }

            if (isCancelled()) {

                break;

            } else if (productObject == null || productObject.length() < 8) {
                // do nothing...
            } else {

                String mId = productObject.getString(TAG_ITEM_ID);
                String mDescription = productObject.getString(TAG_ITEM_DESCRIPTION);
                String mWebsite = productObject.getString(TAG_ITEM_WEBSITE);
                String mUpdateOn = productObject.getString(TAG_ITEM_UPDATE_ON);
                String mMostView = productObject.getString(TAG_ITEM_MOST_VIEW);
                String mMostDownload = productObject.getString(TAG_ITEM_MOST_DOWNLOAD);
                String mDownloadImage = productObject.getString(TAG_ITEM_DOWNLOAD_IMAGE);
                String mPhotoUrl = productObject.getString(TAG_ITEM_THUMBNAIL_URL);

                if (mId == null || mId.equals("") ||
                        mDescription == null || // mDescription.equals("") || // may be empty
                        mWebsite == null || mWebsite.equals("") ||
                        mUpdateOn == null || mUpdateOn.equals("") ||
                        mMostView == null || mMostView.equals("") ||
                        mMostDownload == null || mMostDownload.equals("") ||
                        mDownloadImage == null || mDownloadImage.equals("") ||
                        mPhotoUrl == null || mPhotoUrl.equals("")) {

                    // do nothing...
                } else {


                    String decodedWebsite = decodeUrl(mWebsite);
                    String decodedPhotoUrl = decodeUrl(mPhotoUrl);
                    String decodedDownloadImage = decodeUrl(mDownloadImage);

                    int id = addGalleryData(serviceId, mId, mDescription, decodedWebsite, mUpdateOn,
                            mMostView, mMostDownload, decodedPhotoUrl, decodedDownloadImage) ;
                }
            }
        }
    }

    private int addGalleryData(int serviceId, String strItemNo, String description, String website, String updateOn,
                               String strMostView, String strMostDownload, String photoUrl,
                               String downloadImage) {

        int no = Integer.parseInt(strItemNo);
        int mostView = Integer.parseInt(strMostView);
        int mostDownload = Integer.parseInt(strMostDownload);

        GalleryModel galleryModel = DBAdapter.getGalleryModel(no, serviceId);

        int id = galleryModel.getId();

        if (id > 0) {

            String oldDescription = galleryModel.getDescription();
            String oldWebsite = galleryModel.getWebsite();
            String oldUpdateOn = galleryModel.getUpdatedOn();
            int oldMostView = galleryModel.getMostView();
            int oldMostDownload = galleryModel.getMostDownload();
            String oldDownloadImage = galleryModel.getDownloadImage();
            String oldPhotoUrl = galleryModel.getPhotoUrl();

            if (TextUtils.equals(oldDescription, description)
                    && TextUtils.equals(oldWebsite, website)
                    && TextUtils.equals(oldUpdateOn, updateOn)
                    && oldMostView == mostView
                    && oldMostDownload == mostDownload
                    && TextUtils.equals(oldDownloadImage, downloadImage)
                    && TextUtils.equals(oldPhotoUrl, photoUrl)) {

                // do nothing....
            } else {

                galleryModel.setDescription(description);
                galleryModel.setWebsite(website);
                galleryModel.setUpdatedOn(updateOn);
                galleryModel.setMostView(mostView);
                galleryModel.setMostDownload(mostDownload);
                galleryModel.setDownloadImage(downloadImage);
                galleryModel.setPhotoUrl(photoUrl);

                DBAdapter.updateGalleryData(galleryModel);

                updatedDetails++;

                updateImage(oldPhotoUrl, photoUrl, serviceId, no);
            }

        } else {
            // do nothing...
        }

        return id;
    }

    private void retrieveNewsData(int serviceId, JSONArray productArray) throws JSONException {

        for (int i = 0; i < productArray.length(); i++) {

            JSONObject productObject = productArray.getJSONObject(i);

            if (DEBUG) {
                int no = i + 1;
                Log.d(TAG, "JsonObject > News # " + no + ": "
                        + productObject.length() + ", " + productObject.toString());
            }

            if (isCancelled()) {

                break;

            } else if (productObject == null || productObject.length() < 7) {
                // do nothing...
            } else {

                String mId = productObject.getString(TAG_ITEM_ID);
                String mDescription = productObject.getString(TAG_ITEM_DESCRIPTION);
                String mWebsite = productObject.getString(TAG_ITEM_WEBSITE);
                String mUpdateOn = productObject.getString(TAG_ITEM_UPDATE_ON);
                String mMostView = productObject.getString(TAG_ITEM_MOST_VIEW);
                String mMostDownload = productObject.getString(TAG_ITEM_MOST_DOWNLOAD);
                String mPhotoUrl = productObject.getString(TAG_ITEM_THUMBNAIL_URL);

                if (mId == null || mId.equals("") ||
                        mDescription == null || // mDescription.equals("") || // may be empty
                        mWebsite == null || mWebsite.equals("") ||
                        mUpdateOn == null || mUpdateOn.equals("") ||
                        mMostView == null || mMostView.equals("") ||
                        mMostDownload == null || mMostDownload.equals("") ||
                        mPhotoUrl == null || mPhotoUrl.equals("")) {

                    // do nothing...
                } else {


                    String decodedWebsite = decodeUrl(mWebsite);
                    String decodedPhotoUrl = decodeUrl(mPhotoUrl);

                    int id = addNewsData(serviceId, mId, mDescription, decodedWebsite, mUpdateOn,
                            mMostView, mMostDownload, decodedPhotoUrl) ;
                }
            }
        }
    }

    private int addNewsData(int serviceId, String strItemNo, String description, String website, String updateOn,
                            String strMostView, String strMostDownload, String photoUrl) {

        int no = Integer.parseInt(strItemNo);
        int mostView = Integer.parseInt(strMostView);
        int mostDownload = Integer.parseInt(strMostDownload);

        NewsModel newsModel = DBAdapter.getNewsModel(no, serviceId);

        int id = newsModel.getId();

        if (id > 0) {

            String oldDescription = newsModel.getDescription();
            String oldWebsite = newsModel.getWebsite();
            String oldUpdateOn = newsModel.getUpdatedOn();
            int oldMostView = newsModel.getMostView();
            int oldMostDownload = newsModel.getMostDownload();
            String oldPhotoUrl = newsModel.getPhotoUrl();

            if (TextUtils.equals(oldDescription, description)
                    && TextUtils.equals(oldWebsite, website)
                    && TextUtils.equals(oldUpdateOn, updateOn)
                    && oldMostView == mostView
                    && oldMostDownload == mostDownload
                    && TextUtils.equals(oldPhotoUrl, photoUrl)) {

                // do nothing....
            } else {

                newsModel.setDescription(description);
                newsModel.setWebsite(website);
                newsModel.setUpdatedOn(updateOn);
                newsModel.setMostView(mostView);
                newsModel.setMostDownload(mostDownload);
                newsModel.setPhotoUrl(photoUrl);

                DBAdapter.updateNewsData(newsModel);

                updatedDetails++;

                updateImage(oldPhotoUrl, photoUrl, serviceId, no);
            }

        } else {
            // do nothing...
        }

        return id;
    }

    private void retrievePlaceData(int serviceId, JSONArray productArray) throws JSONException {

        for (int i = 0; i < productArray.length(); i++) {

            JSONObject productObject = productArray.getJSONObject(i);

            if (DEBUG) {
                int no = i + 1;
                Log.d(TAG, "JsonObject > Place # " + no + ": "
                        + productObject.length() + ", " + productObject.toString());
            }

            if (isCancelled()) {

                break;

            } else if (productObject == null || productObject.length() < 6) {
                // do nothing...
            } else {

                String mId = productObject.getString(TAG_ITEM_ID);
                String mDescription = productObject.getString(TAG_ITEM_DESCRIPTION);
                String mWebsite = productObject.getString(TAG_ITEM_WEBSITE);
                String mUpdateOn = productObject.getString(TAG_ITEM_UPDATE_ON);
                String mMostView = productObject.getString(TAG_ITEM_MOST_VIEW);
                String mPhotoUrl = productObject.getString(TAG_ITEM_THUMBNAIL_URL);

                if (mId == null || mId.equals("") ||
                        mDescription == null || // mDescription.equals("") || // may be empty
                        mWebsite == null || mWebsite.equals("") ||
                        mUpdateOn == null || mUpdateOn.equals("") ||
                        mMostView == null || mMostView.equals("") ||
                        mPhotoUrl == null || mPhotoUrl.equals("")) {

                    // do nothing...
                } else {


                    String decodedWebsite = decodeUrl(mWebsite);
                    String decodedPhotoUrl = decodeUrl(mPhotoUrl);

                    int id = addPlaceData(serviceId, mId, mDescription, decodedWebsite, mUpdateOn,
                            mMostView, decodedPhotoUrl) ;
                }
            }
        }
    }

    private int addPlaceData(int serviceId, String strItemNo, String description, String website, String updateOn,
                             String strMostView, String photoUrl) {

        int no = Integer.parseInt(strItemNo);
        int mostView = Integer.parseInt(strMostView);

        PlaceModel placeModel = DBAdapter.getPlaceModel(no, serviceId);

        int id = placeModel.getId();

        if (id > 0) {

            String oldDescription = placeModel.getDescription();
            String oldWebsite = placeModel.getWebsite();
            String oldUpdateOn = placeModel.getUpdatedOn();
            int oldMostView = placeModel.getMostView();
            String oldPhotoUrl = placeModel.getPhotoUrl();

            if (TextUtils.equals(oldDescription, description)
                    && TextUtils.equals(oldWebsite, website)
                    && TextUtils.equals(oldUpdateOn, updateOn)
                    && oldMostView == mostView
                    && TextUtils.equals(oldPhotoUrl, photoUrl)) {

                // do nothing....
            } else {

                placeModel.setDescription(description);
                placeModel.setWebsite(website);
                placeModel.setUpdatedOn(updateOn);
                placeModel.setMostView(mostView);
                placeModel.setMostDownload(0);
                placeModel.setPhotoUrl(photoUrl);

                DBAdapter.updatePlaceData(placeModel);

                updatedDetails++;

                updateImage(oldPhotoUrl, photoUrl, serviceId, no);
            }

        } else {
            // do nothing...
        }

        return id;
    }

    private void retrieveHistoryData(int serviceId, JSONArray productArray) throws JSONException {

        for (int i = 0; i < productArray.length(); i++) {

            JSONObject productObject = productArray.getJSONObject(i);

            if (DEBUG) {
                int no = i + 1;
                Log.d(TAG, "JsonObject > History # " + no + ": "
                        + productObject.length() + ", " + productObject.toString());
            }

            if (isCancelled()) {

                break;

            } else if (productObject == null || productObject.length() < 6) {
                // do nothing...
            } else {

                String mId = productObject.getString(TAG_ITEM_ID);
                String mDescription = productObject.getString(TAG_ITEM_DESCRIPTION);
                String mWebsite = productObject.getString(TAG_ITEM_WEBSITE);
                String mUpdateOn = productObject.getString(TAG_ITEM_UPDATE_ON);
                String mMostView = productObject.getString(TAG_ITEM_MOST_VIEW);
                String mPhotoUrl = productObject.getString(TAG_ITEM_THUMBNAIL_URL);

                if (mId == null || mId.equals("") ||
                        mDescription == null || // mDescription.equals("") || // may be empty
                        mWebsite == null || mWebsite.equals("") ||
                        mUpdateOn == null || mUpdateOn.equals("") ||
                        mMostView == null || mMostView.equals("") ||
                        mPhotoUrl == null || mPhotoUrl.equals("")) {

                    // do nothing...
                } else {


                    String decodedWebsite = decodeUrl(mWebsite);
                    String decodedPhotoUrl = decodeUrl(mPhotoUrl);

                    int id = addHistoryData(serviceId, mId, mDescription, decodedWebsite, mUpdateOn,
                            mMostView, decodedPhotoUrl) ;
                }
            }
        }
    }

    private int addHistoryData(int serviceId, String strItemNo, String description, String website, String updateOn,
                               String strMostView,  String photoUrl) {

        int no = Integer.parseInt(strItemNo);
        int mostView = Integer.parseInt(strMostView);

        HistoryModel historyModel = DBAdapter.getHistoryModel(no, serviceId);

        int id = historyModel.getId();

        if (id > 0) {

            String oldDescription = historyModel.getDescription();
            String oldWebsite = historyModel.getWebsite();
            String oldUpdateOn = historyModel.getUpdatedOn();
            int oldMostView = historyModel.getMostView();
            String oldPhotoUrl = historyModel.getPhotoUrl();

            if (TextUtils.equals(oldDescription, description)
                    && TextUtils.equals(oldWebsite, website)
                    && TextUtils.equals(oldUpdateOn, updateOn)
                    && oldMostView == mostView
                    && TextUtils.equals(oldPhotoUrl, photoUrl)) {

                // do nothing....
            } else {

                historyModel.setDescription(description);
                historyModel.setWebsite(website);
                historyModel.setUpdatedOn(updateOn);
                historyModel.setMostView(mostView);
                historyModel.setMostDownload(0);
                historyModel.setPhotoUrl(photoUrl);

                DBAdapter.updateHistoryData(historyModel);

                updatedDetails++;

                updateImage(oldPhotoUrl, photoUrl, serviceId, no);
            }

        } else {
            // do nothing...
        }

        return id;
    }

    private void retrieveDayData(int serviceId, JSONArray productArray) throws JSONException {

        for (int i = 0; i < productArray.length(); i++) {

            JSONObject productObject = productArray.getJSONObject(i);

            if (DEBUG) {
                int no = i + 1;
                Log.d(TAG, "JsonObject > Day # " + no + ": "
                        + productObject.length() + ", " + productObject.toString());
            }

            if (isCancelled()) {

                break;

            } else if (productObject == null || productObject.length() < 6) {
                // do nothing...
            } else {

                String mId = productObject.getString(TAG_ITEM_ID);
                String mDescription = productObject.getString(TAG_ITEM_DESCRIPTION);
                String mWebsite = productObject.getString(TAG_ITEM_WEBSITE);
                String mUpdateOn = productObject.getString(TAG_ITEM_UPDATE_ON);
                String mMostView = productObject.getString(TAG_ITEM_MOST_VIEW);
                String mPhotoUrl = productObject.getString(TAG_ITEM_THUMBNAIL_URL);

                if (mId == null || mId.equals("") ||
                        mDescription == null || // mDescription.equals("") || // may be empty
                        mWebsite == null || mWebsite.equals("") ||
                        mUpdateOn == null || mUpdateOn.equals("") ||
                        mMostView == null || mMostView.equals("") ||
                        mPhotoUrl == null || mPhotoUrl.equals("")) {

                    // do nothing...
                } else {


                    String decodedWebsite = decodeUrl(mWebsite);
                    String decodedPhotoUrl = decodeUrl(mPhotoUrl);

                    int id = addDayData(serviceId, mId, mDescription, decodedWebsite, mUpdateOn,
                            mMostView, decodedPhotoUrl) ;
                }
            }
        }
    }

    private int addDayData(int serviceId, String strItemNo, String description, String website, String updateOn,
                           String strMostView, String photoUrl) {

        int no = Integer.parseInt(strItemNo);
        int mostView = Integer.parseInt(strMostView);

        DayModel daysModel = DBAdapter.getDaysModel(no, serviceId);

        int id = daysModel.getId();

        if (id > 0) {

            String oldDescription = daysModel.getDescription();
            String oldWebsite = daysModel.getWebsite();
            String oldUpdateOn = daysModel.getUpdatedOn();
            int oldMostView = daysModel.getMostView();
            String oldPhotoUrl = daysModel.getPhotoUrl();

            if (TextUtils.equals(oldDescription, description)
                    && TextUtils.equals(oldWebsite, website)
                    && TextUtils.equals(oldUpdateOn, updateOn)
                    && oldMostView == mostView
                    && TextUtils.equals(oldPhotoUrl, photoUrl)) {

                // do nothing....
            } else {

                daysModel.setDescription(description);
                daysModel.setWebsite(website);
                daysModel.setUpdatedOn(updateOn);
                daysModel.setMostView(mostView);
                daysModel.setMostDownload(0);
                daysModel.setPhotoUrl(photoUrl);

                DBAdapter.updateDaysData(daysModel);

                updatedDetails++;

                updateImage(oldPhotoUrl, photoUrl, serviceId, no);
            }

        } else {
            // do nothing...
        }

        return id;
    }

    private void retrieveDepartmentData(int serviceId, JSONArray productArray) throws JSONException {

        for (int i = 0; i < productArray.length(); i++) {

            JSONObject productObject = productArray.getJSONObject(i);

            if (DEBUG) {
                int no = i + 1;
                Log.d(TAG, "JsonObject > Department # " + no + ": "
                        + productObject.length() + ", " + productObject.toString());
            }

            if (isCancelled()) {

                break;

            } else if (productObject == null || productObject.length() < 6) {
                // do nothing...
            } else {

                String mId = productObject.getString(TAG_ITEM_ID);
                String mDescription = productObject.getString(TAG_ITEM_DESCRIPTION);
                String mWebsite = productObject.getString(TAG_ITEM_WEBSITE);
                String mUpdateOn = productObject.getString(TAG_ITEM_UPDATE_ON);
                String mMostView = productObject.getString(TAG_ITEM_MOST_VIEW);
                String mPhotoUrl = productObject.getString(TAG_ITEM_THUMBNAIL_URL);

                if (mId == null || mId.equals("") ||
                        mDescription == null || // mDescription.equals("") || // may be empty
                        mWebsite == null || mWebsite.equals("") ||
                        mUpdateOn == null || mUpdateOn.equals("") ||
                        mMostView == null || mMostView.equals("") ||
                        mPhotoUrl == null || mPhotoUrl.equals("")) {

                    // do nothing...
                } else {


                    String decodedWebsite = decodeUrl(mWebsite);
                    String decodedPhotoUrl = decodeUrl(mPhotoUrl);

                    int id = addDepartmentData(serviceId, mId, mDescription, decodedWebsite, mUpdateOn,
                            mMostView, decodedPhotoUrl) ;
                }
            }
        }
    }

    private int addDepartmentData(int serviceId, String strItemNo, String description, String website, String updateOn,
                                  String strMostView, String photoUrl) {

        int no = Integer.parseInt(strItemNo);
        int mostView = Integer.parseInt(strMostView);

        DepartmentModel departmentsModel = DBAdapter.getDepartmentsModel(no, serviceId);

        int id = departmentsModel.getId();

        if (id > 0) {

            String oldDescription = departmentsModel.getDescription();
            String oldWebsite = departmentsModel.getWebsite();
            String oldUpdateOn = departmentsModel.getUpdatedOn();
            int oldMostView = departmentsModel.getMostView();
            String oldPhotoUrl = departmentsModel.getPhotoUrl();

            if (TextUtils.equals(oldDescription, description)
                    && TextUtils.equals(oldWebsite, website)
                    && TextUtils.equals(oldUpdateOn, updateOn)
                    && oldMostView == mostView
                    && TextUtils.equals(oldPhotoUrl, photoUrl)) {

                // do nothing....
            } else {

                departmentsModel.setDescription(description);
                departmentsModel.setWebsite(website);
                departmentsModel.setUpdatedOn(updateOn);
                departmentsModel.setMostView(mostView);
                departmentsModel.setMostDownload(0);
                departmentsModel.setPhotoUrl(photoUrl);

                DBAdapter.updateDepartmentsData(departmentsModel);

                updatedDetails++;

                updateImage(oldPhotoUrl, photoUrl, serviceId, no);
            }

        } else {
            // do nothing...
        }

        return id;
    }

    private void retrieveAppData(int serviceId, JSONArray productArray) throws JSONException {

        for (int i = 0; i < productArray.length(); i++) {

            JSONObject productObject = productArray.getJSONObject(i);

            if (DEBUG) {
                int no = i + 1;
                Log.d(TAG, "JsonObject > App # " + no + ": "
                        + productObject.length() + ", " + productObject.toString());
            }

            if (isCancelled()) {

                break;

            } else if (productObject == null || productObject.length() < 7) {
                // do nothing...
            } else {

                String mId = productObject.getString(TAG_ITEM_ID);
                String mDescription = productObject.getString(TAG_ITEM_DESCRIPTION);
                String mWebsite = productObject.getString(TAG_ITEM_WEBSITE);
                String mUpdateOn = productObject.getString(TAG_ITEM_UPDATE_ON);
                String mMostView = productObject.getString(TAG_ITEM_MOST_VIEW);
                String mDownloadApp = productObject.getString(TAG_ITEM_DOWNLOAD_APP);
                String mPhotoUrl = productObject.getString(TAG_ITEM_THUMBNAIL_URL);

                if (mId == null || mId.equals("") ||
                        mDescription == null || // mDescription.equals("") || // may be empty
                        mWebsite == null || mWebsite.equals("") ||
                        mUpdateOn == null || mUpdateOn.equals("") ||
                        mMostView == null || mMostView.equals("") ||
                        mDownloadApp == null || // mDownloadApp.equals("") || // it may be empty
                        mPhotoUrl == null || mPhotoUrl.equals("")) {

                    // do nothing...
                } else {


                    String decodedWebsite = decodeUrl(mWebsite);
                    String decodedDownloadApp = decodeUrl(mDownloadApp);
                    String decodedPhotoUrl = decodeUrl(mPhotoUrl);

                    int id = addAppData(serviceId, mId, mDescription, decodedWebsite, mUpdateOn,
                            mMostView, decodedPhotoUrl, decodedDownloadApp) ;
                }
            }
        }
    }

    private int addAppData(int serviceId, String strItemNo, String description, String website, String updateOn,
                           String strMostView, String photoUrl, String downloadApp) {

        int no = Integer.parseInt(strItemNo);
        int mostView = Integer.parseInt(strMostView);

        AppModel appModel = DBAdapter.getAppsModel(no, serviceId);

        int id = appModel.getId();

        if (id > 0) {

            String oldDescription = appModel.getDescription();
            String oldWebsite = appModel.getWebsite();
            String oldUpdateOn = appModel.getUpdatedOn();
            int oldMostView = appModel.getMostView();
            String oldDownloadApp = appModel.getDownloadApp();
            String oldPhotoUrl = appModel.getPhotoUrl();

            if (TextUtils.equals(oldDescription, description)
                    && TextUtils.equals(oldWebsite, website)
                    && TextUtils.equals(oldUpdateOn, updateOn)
                    && oldMostView == mostView
                    && TextUtils.equals(oldDownloadApp, downloadApp)
                    && TextUtils.equals(oldPhotoUrl, photoUrl)) {

                // do nothing....
            } else {

                appModel.setDescription(description);
                appModel.setWebsite(website);
                appModel.setUpdatedOn(updateOn);
                appModel.setMostView(mostView);
                appModel.setMostDownload(0);
                appModel.setDownloadApp(downloadApp);
                appModel.setPhotoUrl(photoUrl);

                DBAdapter.updateAppsData(appModel);

                updatedDetails++;

                updateImage(oldPhotoUrl, photoUrl, serviceId, no);
            }

        } else {
            // do nothing...
        }

        return id;
    }

    private void retrieveArticleData(int serviceId, JSONArray productArray) throws JSONException {

        for (int i = 0; i < productArray.length(); i++) {

            JSONObject productObject = productArray.getJSONObject(i);

            if (DEBUG) {
                int no = i + 1;
                Log.d(TAG, "JsonObject > Article # " + no + ": "
                        + productObject.length() + ", " + productObject.toString());
            }

            if (isCancelled()) {

                break;

            } else if (productObject == null || productObject.length() < 6) {
                // do nothing...
            } else {

                String mId = productObject.getString(TAG_ITEM_ID);
                String mDescription = productObject.getString(TAG_ITEM_DESCRIPTION);
                String mWebsite = productObject.getString(TAG_ITEM_WEBSITE);
                String mUpdateOn = productObject.getString(TAG_ITEM_UPDATE_ON);
                String mMostView = productObject.getString(TAG_ITEM_MOST_VIEW);
                String mPhotoUrl = productObject.getString(TAG_ITEM_THUMBNAIL_URL);

                if (mId == null || mId.equals("") ||
                        mDescription == null || // mDescription.equals("") || // may be empty
                        mWebsite == null || mWebsite.equals("") ||
                        mUpdateOn == null || mUpdateOn.equals("") ||
                        mMostView == null || mMostView.equals("") ||
                        mPhotoUrl == null || mPhotoUrl.equals("")) {

                    // do nothing...
                } else {


                    String decodedWebsite = decodeUrl(mWebsite);
                    String decodedPhotoUrl = decodeUrl(mPhotoUrl);

                    int id = addArticleData(serviceId, mId, mDescription, decodedWebsite, mUpdateOn,
                            mMostView, decodedPhotoUrl) ;
                }
            }
        }
    }

    private int addArticleData(int serviceId, String strItemNo, String description, String website, String updateOn,
                               String strMostView, String photoUrl) {

        int no = Integer.parseInt(strItemNo);
        int mostView = Integer.parseInt(strMostView);

        ArticleModel articleModel = DBAdapter.getArticleModel(no, serviceId);

        int id = articleModel.getId();

        if (id > 0) {

            String oldDescription = articleModel.getDescription();
            String oldWebsite = articleModel.getWebsite();
            String oldUpdateOn = articleModel.getUpdatedOn();
            int oldMostView = articleModel.getMostView();
            String oldPhotoUrl = articleModel.getPhotoUrl();

            if (TextUtils.equals(oldDescription, description)
                    && TextUtils.equals(oldWebsite, website)
                    && TextUtils.equals(oldUpdateOn, updateOn)
                    && oldMostView == mostView
                    && TextUtils.equals(oldPhotoUrl, photoUrl)) {

                // do nothing....
            } else {

                articleModel.setDescription(description);
                articleModel.setWebsite(website);
                articleModel.setUpdatedOn(updateOn);
                articleModel.setMostView(mostView);
                articleModel.setMostDownload(0);
                articleModel.setPhotoUrl(photoUrl);

                DBAdapter.updateArticleData(articleModel);

                updatedDetails++;

                updateImage(oldPhotoUrl, photoUrl, serviceId, no);
            }

        } else {
            // do nothing...
        }

        return id;
    }

    private void retrieveBlogData(int serviceId, JSONArray productArray) throws JSONException {

        for (int i = 0; i < productArray.length(); i++) {

            JSONObject productObject = productArray.getJSONObject(i);

            if (DEBUG) {
                int no = i + 1;
                Log.d(TAG, "JsonObject > Blog # " + no + ": "
                        + productObject.length() + ", " + productObject.toString());
            }

            if (isCancelled()) {

                break;

            } else if (productObject == null || productObject.length() < 6) {
                // do nothing...
            } else {

                String mId = productObject.getString(TAG_ITEM_ID);
                String mDescription = productObject.getString(TAG_ITEM_DESCRIPTION);
                String mWebsite = productObject.getString(TAG_ITEM_WEBSITE);
                String mUpdateOn = productObject.getString(TAG_ITEM_UPDATE_ON);
                String mMostView = productObject.getString(TAG_ITEM_MOST_VIEW);
                String mPhotoUrl = productObject.getString(TAG_ITEM_THUMBNAIL_URL);

                if (mId == null || mId.equals("") ||
                        mDescription == null || // mDescription.equals("") || // may be empty
                        mWebsite == null || mWebsite.equals("") ||
                        mUpdateOn == null || mUpdateOn.equals("") ||
                        mMostView == null || mMostView.equals("") ||
                        mPhotoUrl == null || mPhotoUrl.equals("")) {

                    // do nothing...
                } else {


                    String decodedWebsite = decodeUrl(mWebsite);
                    String decodedPhotoUrl = decodeUrl(mPhotoUrl);

                    int id = addBlogData(serviceId, mId, mDescription, decodedWebsite, mUpdateOn,
                            mMostView, decodedPhotoUrl) ;
                }
            }
        }
    }

    private int addBlogData(int serviceId, String strItemNo, String description, String website, String updateOn,
                            String strMostView, String photoUrl) {

        int no = Integer.parseInt(strItemNo);
        int mostView = Integer.parseInt(strMostView);

        BlogModel blogModel = DBAdapter.getBlogModel(no, serviceId);

        int id = blogModel.getId();

        if (id > 0) {

            String oldDescription = blogModel.getDescription();
            String oldWebsite = blogModel.getWebsite();
            String oldUpdateOn = blogModel.getUpdatedOn();
            int oldMostView = blogModel.getMostView();
            String oldPhotoUrl = blogModel.getPhotoUrl();

            if (TextUtils.equals(oldDescription, description)
                    && TextUtils.equals(oldWebsite, website)
                    && TextUtils.equals(oldUpdateOn, updateOn)
                    && oldMostView == mostView
                    && TextUtils.equals(oldPhotoUrl, photoUrl)) {

                // do nothing....
            } else {

                blogModel.setDescription(description);
                blogModel.setWebsite(website);
                blogModel.setUpdatedOn(updateOn);
                blogModel.setMostView(mostView);
                blogModel.setMostDownload(0);
                blogModel.setPhotoUrl(photoUrl);

                DBAdapter.updateBlogData(blogModel);

                updatedDetails++;

                updateImage(oldPhotoUrl, photoUrl, serviceId, no);
            }

        } else {
            // do nothing...
        }

        return id;
    }

    private void retrievePoetryData(int serviceId, JSONArray productArray) throws JSONException {

        for (int i = 0; i < productArray.length(); i++) {

            JSONObject productObject = productArray.getJSONObject(i);

            if (DEBUG) {
                int no = i + 1;
                Log.d(TAG, "JsonObject > Poetry # " + no + ": "
                        + productObject.length() + ", " + productObject.toString());
            }

            if (isCancelled()) {

                break;

            } else if (productObject == null || productObject.length() < 6) {
                // do nothing...
            } else {

                String mId = productObject.getString(TAG_ITEM_ID);
                String mDescription = productObject.getString(TAG_ITEM_DESCRIPTION);
                String mWebsite = productObject.getString(TAG_ITEM_WEBSITE);
                String mUpdateOn = productObject.getString(TAG_ITEM_UPDATE_ON);
                String mMostView = productObject.getString(TAG_ITEM_MOST_VIEW);
                String mPhotoUrl = productObject.getString(TAG_ITEM_THUMBNAIL_URL);

                if (mId == null || mId.equals("") ||
                        mDescription == null || // mDescription.equals("") || // may be empty
                        mWebsite == null || mWebsite.equals("") ||
                        mUpdateOn == null || mUpdateOn.equals("") ||
                        mMostView == null || mMostView.equals("") ||
                        mPhotoUrl == null || mPhotoUrl.equals("")) {

                    // do nothing...
                } else {


                    String decodedWebsite = decodeUrl(mWebsite);
                    String decodedPhotoUrl = decodeUrl(mPhotoUrl);

                    int id = addPoetryData(serviceId, mId, mDescription, decodedWebsite, mUpdateOn,
                            mMostView, decodedPhotoUrl) ;
                }
            }
        }
    }

    private int addPoetryData(int serviceId, String strItemNo, String description, String website, String updateOn,
                              String strMostView, String photoUrl) {

        int no = Integer.parseInt(strItemNo);
        int mostView = Integer.parseInt(strMostView);

        PoetryModel poetryModel = DBAdapter.getPoetryModel(no, serviceId);

        int id = poetryModel.getId();

        if (id > 0) {

            String oldDescription = poetryModel.getDescription();
            String oldWebsite = poetryModel.getWebsite();
            String oldUpdateOn = poetryModel.getUpdatedOn();
            int oldMostView = poetryModel.getMostView();
            String oldPhotoUrl = poetryModel.getPhotoUrl();

            if (TextUtils.equals(oldDescription, description)
                    && TextUtils.equals(oldWebsite, website)
                    && TextUtils.equals(oldUpdateOn, updateOn)
                    && oldMostView == mostView
                    && TextUtils.equals(oldPhotoUrl, photoUrl)) {

                // do nothing....
            } else {

                poetryModel.setDescription(description);
                poetryModel.setWebsite(website);
                poetryModel.setUpdatedOn(updateOn);
                poetryModel.setMostView(mostView);
                poetryModel.setMostDownload(0);
                poetryModel.setPhotoUrl(photoUrl);

                DBAdapter.updatePoetryData(poetryModel);

                updatedDetails++;

                updateImage(oldPhotoUrl, photoUrl, serviceId, no);
            }

        } else {
            // do nothing...
        }

        return id;
    }


    private void updateImage (String oldPhotoUrl, String photoUrl, int serviceId, int productNo) {

        if (!TextUtils.equals(oldPhotoUrl, photoUrl)) {

            int languageNo = DBAdapter.getLanguageModelById(languageId).getNo();
            int serviceNo = DBAdapter.getServiceModelById(serviceId).getNumber();
            String shortName = DBAdapter.getDataTablesModelFromNo(serviceNo).getShortName();

            String mImageTitle = "l" + languageNo + "s" + serviceNo + "b" + shortName.substring(0, 2) ;

            String photoId = mImageTitle + String.valueOf(productNo) ;

            SDCardManager.deleteThumbnail(mActivity, photoId);
        }
    }

    private String decodeUrl(String url) {

        String decodeUrl = "";

        try {

            decodeUrl = URLDecoder.decode(url, "UTF-8");

        } catch (UnsupportedEncodingException e) {

            Log.e("Error while decoding", e.toString());
            decodeUrl = url;
        }

        return decodeUrl;
    }
}