package com.ziaetaiba.islamicwebsite.tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.ziaetaiba.islamicwebsite.callbacks.AppDataCallbacks;
import com.ziaetaiba.islamicwebsite.components.SDCardManager;
import com.ziaetaiba.islamicwebsite.constants.ZiaeTaibaData;
import com.ziaetaiba.islamicwebsite.database.DBAdapter;
import com.ziaetaiba.islamicwebsite.datamodels.AppModel;
import com.ziaetaiba.islamicwebsite.datamodels.ArticleModel;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class ProductData extends AsyncTask<Void, Integer, Void> {

    private static final String TAG = ProductData.class.getSimpleName();

    private static final boolean DEBUG = false;

    private static final String ROOT_DATA = "data";

    private static final String ROOT_SERVICES_ID = "service_id";
    private static final String ROOT_PRODUCTS = "products";

    private static final String TAG_ITEM_ID = "id";
    private static final String TAG_ITEM_TITLE = "title";
    private static final String TAG_ITEM_THUMBNAIL_URL = "thumbnailUrl";
    private static final String TAG_ITEM_DESCRIPTION = "Description";
    private static final String TAG_ITEM_FEATURE = "featured";
    private static final String TAG_ITEM_MOST_VIEW = "most-view";
    private static final String TAG_ITEM_MOST_DOWNLOAD = "most-download";
    private static final String TAG_ITEM_WEBSITE = "website";
    private static final String TAG_ITEM_UPDATE_ON = "Updated Date";
    private static final String TAG_ITEM_STATUS = "status";

    private static final String TAG_ITEM_CATEGORY = "Category";
    private static final String TAG_ITEM_AUTHOR = "Author";
    private static final String TAG_ITEM_LANGUAGE = "Language";
    private static final String TAG_ITEM_PUBLISHER = "Publisher";
    private static final String TAG_ITEM_MONTH = "Month";
    private static final String TAG_ITEM_PDF_URL = "Download Pdf";
    //    private static final String TAG_ITEM_PHOTO_URL      = "photo_url";
    private static final String TAG_ITEM_PHOTO_URL = "Download This Image";

    private static final String TAG_ITEM_VOCALIST = "Vocalist";
    private static final String TAG_ITEM_ATTRIBUTE = "Attribue";
    private static final String TAG_ITEM_TYPE = "Type";

    private static final String TAG_ITEM_ORGANIZATION = "Organization";
    private static final String TAG_ITEM_COUNTRY = "Country";
    private static final String TAG_ITEM_NEWSPAPER = "News Paper";

    private static final String TAG_ITEM_WRITER = "Writter";

    private Activity mActivity;
    private AppDataCallbacks mCallbacks;
    private JSONObject jsonObject;
    private int languageId;

    private boolean errorFlag;

    private int newProducts, updatedProducts;

    public ProductData(Activity mActivity, AppDataCallbacks mCallbacks, int languageId, JSONObject jsonObject) {

        this.mActivity = mActivity;
        this.mCallbacks = mCallbacks;
        this.languageId = languageId;
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
                            Log.d(TAG, "JsonArray > Array Size: " + jsonObject.length());
                        }

                        JSONArray dataArray = jsonObject.getJSONArray(ROOT_DATA);

                        if (dataArray == null || dataArray.length() < 1) {

                            // do nothing...
                        } else {

                            JSONObject dataObject = dataArray.getJSONObject(0);

                            if (dataObject == null || dataObject.length() < 2) {

                                // do nothing...
                            } else {

                                String mServiceId = dataObject.getString(ROOT_SERVICES_ID);
                                JSONArray productArray = dataObject.getJSONArray(ROOT_PRODUCTS);

                                if (mServiceId == null || mServiceId.equals("") ||
                                        productArray == null || productArray.length() < 1) {

                                    // do nothing...
                                } else {

                                    int serviceNo = Integer.parseInt(mServiceId);

                                    int serviceId = DBAdapter.getServiceModel(serviceNo, languageId).getId();

                                    switch (serviceNo) {

                                        case ZiaeTaibaData.SERVICE_NO_BOOKS:
                                            retrieveBookData(serviceId, productArray);
                                            break;
                                        case ZiaeTaibaData.SERVICE_NO_MEDIA:
                                            retrieveMediaData(serviceId, productArray);
                                            break;
                                        case ZiaeTaibaData.SERVICE_NO_SCHOLAR:
                                            retrieveScholarData(serviceId, productArray);
                                            break;
                                        case ZiaeTaibaData.SERVICE_NO_GALLERY:
                                            retrieveGalleryData(serviceId, productArray);
                                            break;
                                        case ZiaeTaibaData.SERVICE_NO_NEWS:
                                            retrieveNewsData(serviceId, productArray);
                                            break;
                                        case ZiaeTaibaData.SERVICE_NO_PLACES:
                                            retrievePlaceData(serviceId, productArray);
                                            break;
                                        case ZiaeTaibaData.SERVICE_NO_HISTORY:
                                            retrieveHistoryData(serviceId, productArray);
                                            break;
                                        case ZiaeTaibaData.SERVICE_NO_DAYS:
                                            retrieveDaysData(serviceId, productArray);
                                            break;
                                        case ZiaeTaibaData.SERVICE_NO_DEPARTMENTS:
                                            retrieveDepartmentsData(serviceId, productArray);
                                            break;
                                        case ZiaeTaibaData.SERVICE_NO_APPS:
                                            retrieveAppsData(serviceId, productArray);
                                            break;
                                        case ZiaeTaibaData.SERVICE_NO_ARTICLES:
                                            retrieveArticlesData(serviceId, productArray);
                                            break;
                                        case ZiaeTaibaData.SERVICE_NO_BLOGS:
                                            retrieveBlogsData(serviceId, productArray);
                                            break;
                                        case ZiaeTaibaData.SERVICE_NO_POETRY:
                                            retrievePoetryData(serviceId, productArray);
                                            break;
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

        newProducts = 0;
        updatedProducts = 0;

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

                    String msg = "Products: " + newProducts + " : " + updatedProducts;
                    Log.d("ProductData", msg);
                }

                if (newProducts > 0 || updatedProducts > 0) {

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

            } else if (productObject == null || productObject.length() < 6) {
                // do nothing...
            } else {

                String mBookId = productObject.getString(TAG_ITEM_ID);
                String mBookTitle = productObject.getString(TAG_ITEM_TITLE);
                String mBookThumbnailUrl = productObject.getString(TAG_ITEM_THUMBNAIL_URL);
                String mBookFeature = productObject.getString(TAG_ITEM_FEATURE);
                String mBookMostView = productObject.getString(TAG_ITEM_MOST_VIEW);
                String mBookStatus = productObject.getString(TAG_ITEM_STATUS);

                String mBookCategory = "";

                try {
                    mBookCategory = productObject.getString(TAG_ITEM_CATEGORY);
                } catch (JSONException ex) {
                    mBookCategory = "-";
                }

                String mBookAuthor = "";

                try {
                    mBookAuthor = productObject.getString(TAG_ITEM_AUTHOR);
                } catch (JSONException ex) {
                    mBookAuthor = "-";
                }

                String mBookLanguage = "";

                try {
                    mBookLanguage = productObject.getString(TAG_ITEM_LANGUAGE);
                } catch (JSONException ex) {
                    mBookLanguage = "-";
                }

                String mBookPublisher = "";

                try {
                    mBookPublisher = productObject.getString(TAG_ITEM_PUBLISHER);
                } catch (JSONException ex) {
                    mBookPublisher = "-";
                }

                String mBookMonth = "";

                try {
                    mBookMonth = productObject.getString(TAG_ITEM_MONTH);
                } catch (JSONException ex) {
                    mBookMonth = "-";
                }

                if (mBookId == null || mBookId.equals("") ||
                        mBookTitle == null || mBookTitle.equals("") ||
                        mBookThumbnailUrl == null || mBookThumbnailUrl.equals("") ||
                        mBookFeature == null || mBookFeature.equals("") ||
                        mBookMostView == null || mBookMostView.equals("") ||
                        mBookStatus == null || mBookStatus.equals("") ||
                        mBookCategory == null || mBookCategory.equals("") ||
                        mBookAuthor == null || mBookAuthor.equals("") ||
                        mBookLanguage == null || mBookLanguage.equals("") ||
                        mBookPublisher == null || mBookPublisher.equals("") ||
                        mBookMonth == null || mBookMonth.equals("")) {

                    // do nothing...
                } else {

                    String decodedThumbnailUrl = decodeUrl(mBookThumbnailUrl);

                    int bookId = addBookData(serviceId, mBookId, mBookTitle, decodedThumbnailUrl, mBookFeature, mBookMostView, mBookStatus,
                            mBookCategory, mBookAuthor, mBookLanguage, mBookPublisher, mBookMonth);
                }
            }
        }
    }

    private int addBookData(int serviceId, String strBookNo, String title, String thumbnailUrl, String strFeature,
                            String strMostView, String status, String category, String author, String language,
                            String publisher, String month) {

        int bookNo = Integer.parseInt(strBookNo);
        int feature = Integer.parseInt(strFeature);
        int mostView = Integer.parseInt(strMostView);

        BookModel bookModel = DBAdapter.getBookModel(bookNo, serviceId);

        int bookId = bookModel.getId();

        if (bookId > 0) {

            String oldTitle = bookModel.getTitle();
            String oldThumbnailUrl = bookModel.getThumbnailUrl();
            int oldFeatured = bookModel.getFeatured();
            int oldMostView = bookModel.getMostView();
            String oldStatus = bookModel.getStatus();
            String oldCategory = bookModel.getCategory();
            String oldAuthor = bookModel.getAuthor();
            String oldLanguage = bookModel.getLanguage();
            String oldPublisher = bookModel.getPublisher();
            String oldMonth = bookModel.getMonth();

            if (TextUtils.equals(oldTitle, title)
                    && TextUtils.equals(oldThumbnailUrl, thumbnailUrl)
                    && oldFeatured == feature
                    && oldMostView == mostView
                    && TextUtils.equals(oldStatus, status)
                    && TextUtils.equals(oldCategory, category)
                    && TextUtils.equals(oldAuthor, author)
                    && TextUtils.equals(oldLanguage, language)
                    && TextUtils.equals(oldPublisher, publisher)
                    && TextUtils.equals(oldMonth, month)) {

                // do nothing....
            } else {

                bookModel.setNo(bookNo);
                bookModel.setServiceId(serviceId);
                bookModel.setTitle(title);
                bookModel.setThumbnailUrl(thumbnailUrl);
                bookModel.setFeatured(feature);
                bookModel.setMostView(mostView);
                bookModel.setStatus(status);
                bookModel.setCategory(category);
                bookModel.setAuthor(author);
                bookModel.setLanguage(language);
                bookModel.setPublisher(publisher);
                bookModel.setMonth(month);

                DBAdapter.updateBookData(bookModel);

                updatedProducts++;

                updateThumbnail(oldThumbnailUrl, thumbnailUrl, serviceId, bookNo);
            }

        } else {

            BookModel tempBookModel = new BookModel();

            tempBookModel.setId(0);
            tempBookModel.setNo(bookNo);
            tempBookModel.setServiceId(serviceId);
            tempBookModel.setTitle(title);
            tempBookModel.setDescription("");  // empty
            tempBookModel.setThumbnailUrl(thumbnailUrl);
            tempBookModel.setFeatured(feature);
            tempBookModel.setMostView(mostView);
            tempBookModel.setMostDownload(0);
            tempBookModel.setWebsite(""); // empty
            tempBookModel.setUpdatedOn(""); // empty
            tempBookModel.setStatus(status);
            tempBookModel.setCategory(category);
            tempBookModel.setAuthor(author);
            tempBookModel.setLanguage(language);
            tempBookModel.setPublisher(publisher);
            tempBookModel.setMonth(month);
            tempBookModel.setPdfUrl(""); // empty
            tempBookModel.setPhotoUrl(""); // empty

            long rowId = DBAdapter.addBookData(tempBookModel);

            if (rowId > -1) {
                bookId = DBAdapter.getBookModel(bookNo, serviceId).getId();
                newProducts++;
            } else {
                bookId = 0;
            }
        }

        return bookId;
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

            } else if (productObject == null || productObject.length() < 6) {
                // do nothing...
            } else {

                String mMediaId = productObject.getString(TAG_ITEM_ID);
                String mMediaTitle = productObject.getString(TAG_ITEM_TITLE);
                String mMediaThumbnailUrl = productObject.getString(TAG_ITEM_THUMBNAIL_URL);
                String mMediaFeature = productObject.getString(TAG_ITEM_FEATURE);
                String mMediaMostView = productObject.getString(TAG_ITEM_MOST_VIEW);
                String mMediaStatus = productObject.getString(TAG_ITEM_STATUS);

                String mMediaCategory = "";

                try {
                    mMediaCategory = productObject.getString(TAG_ITEM_CATEGORY);
                } catch (JSONException ex) {
                    mMediaCategory = "-";
                }

                String mMediaVocalist = "";

                try {
                    mMediaVocalist = productObject.getString(TAG_ITEM_VOCALIST);
                } catch (JSONException ex) {
                    mMediaVocalist = "-";
                }

                String mMediaAttribute = "";

                try {
                    mMediaAttribute = productObject.getString(TAG_ITEM_ATTRIBUTE);
                } catch (JSONException ex) {
                    mMediaAttribute = "-";
                }

                String mMediaLanguage = "";

                try {
                    mMediaLanguage = productObject.getString(TAG_ITEM_LANGUAGE);
                } catch (JSONException ex) {
                    mMediaLanguage = "-";
                }

                String mMediaType = "";

                try {
                    mMediaType = productObject.getString(TAG_ITEM_TYPE);
                } catch (JSONException ex) {
                    mMediaType = "-";
                }

                if (mMediaId == null || mMediaId.equals("") ||
                        mMediaTitle == null || mMediaTitle.equals("") ||
                        mMediaThumbnailUrl == null || mMediaThumbnailUrl.equals("") ||
                        mMediaFeature == null || mMediaFeature.equals("") ||
                        mMediaMostView == null || mMediaMostView.equals("") ||
                        mMediaStatus == null || mMediaStatus.equals("") ||
                        mMediaCategory == null || mMediaCategory.equals("") ||
                        mMediaVocalist == null || mMediaVocalist.equals("") ||
                        mMediaLanguage == null || mMediaLanguage.equals("") ||
                        mMediaAttribute == null || mMediaAttribute.equals("") ||
                        mMediaType == null || mMediaType.equals("")) {

                    // do nothing...
                } else {

                    String decodedThumbnailUrl = decodeUrl(mMediaThumbnailUrl);

                    int bookId = addMediaData(serviceId, mMediaId, mMediaTitle, decodedThumbnailUrl, mMediaFeature, mMediaMostView, mMediaStatus,
                            mMediaCategory, mMediaVocalist, mMediaLanguage, mMediaAttribute, mMediaType);
                }
            }
        }
    }

    private int addMediaData(int serviceId, String strMediaNo, String title, String thumbnailUrl, String strFeature,
                             String strMostView, String status, String category, String vocalist, String language,
                             String attribute, String mediaType) {

        int mediaNo = Integer.parseInt(strMediaNo);
        int feature = Integer.parseInt(strFeature);
        int mostView = Integer.parseInt(strMostView);

        MediaModel mediaModel = DBAdapter.getMediaModel(mediaNo, serviceId);

        int bookId = mediaModel.getId();

        if (bookId > 0) {

            String oldTitle = mediaModel.getTitle();
            String oldThumbnailUrl = mediaModel.getThumbnailUrl();
            int oldFeatured = mediaModel.getFeatured();
            int oldMostView = mediaModel.getMostView();
            String oldStatus = mediaModel.getStatus();
            String oldCategory = mediaModel.getCategory();
            String oldVocalist = mediaModel.getVocalist();
            String oldAttribute = mediaModel.getAttribute();
            String oldLanguage = mediaModel.getLanguage();
            String oldType = mediaModel.getType();

            if (TextUtils.equals(oldTitle, title)
                    && TextUtils.equals(oldThumbnailUrl, thumbnailUrl)
                    && oldFeatured == feature
                    && oldMostView == mostView
                    && TextUtils.equals(oldStatus, status)
                    && TextUtils.equals(oldCategory, category)
                    && TextUtils.equals(oldVocalist, vocalist)
                    && TextUtils.equals(oldAttribute, attribute)
                    && TextUtils.equals(oldLanguage, language)
                    && TextUtils.equals(oldType, mediaType)) {

                // do nothing....
            } else {

                mediaModel.setNo(mediaNo);
                mediaModel.setServiceId(serviceId);
                mediaModel.setTitle(title);
                mediaModel.setThumbnailUrl(thumbnailUrl);
                mediaModel.setFeatured(feature);
                mediaModel.setMostView(mostView);
                mediaModel.setStatus(status);
                mediaModel.setCategory(category);
                mediaModel.setVocalist(vocalist);
                mediaModel.setAttribute(attribute);
                mediaModel.setLanguage(language);
                mediaModel.setType(mediaType);

                DBAdapter.updateMediaData(mediaModel);

                updatedProducts++;

                updateThumbnail(oldThumbnailUrl, thumbnailUrl, serviceId, mediaNo);
            }

        } else {

            MediaModel tempMediaModel = new MediaModel();

            tempMediaModel.setId(0);
            tempMediaModel.setNo(mediaNo);
            tempMediaModel.setServiceId(serviceId);
            tempMediaModel.setTitle(title);
            tempMediaModel.setDescription("");  // empty
            tempMediaModel.setThumbnailUrl(thumbnailUrl);
            tempMediaModel.setFeatured(feature);
            tempMediaModel.setMostView(mostView);
            tempMediaModel.setMostDownload(0);
            tempMediaModel.setWebsite(""); // empty
            tempMediaModel.setUpdatedOn(""); // empty
            tempMediaModel.setStatus(status);
            tempMediaModel.setCategory(category);
            tempMediaModel.setVocalist(vocalist);
            tempMediaModel.setAttribute(attribute);
            tempMediaModel.setLanguage(language);
            tempMediaModel.setType(mediaType);
            tempMediaModel.setPhotoUrl(""); // empty

            long rowId = DBAdapter.addMediaData(tempMediaModel);

            if (rowId > -1) {
                bookId = DBAdapter.getMediaModel(mediaNo, serviceId).getId();
                newProducts++;
            } else {
                bookId = 0;
            }
        }

        return bookId;
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

            } else if (productObject == null || productObject.length() < 4) {
                // do nothing...
            } else {

                String mScholarId = productObject.getString(TAG_ITEM_ID);
                String mScholarTitle = productObject.getString(TAG_ITEM_TITLE);
                String mScholarThumbnailUrl = productObject.getString(TAG_ITEM_THUMBNAIL_URL);
                String mScholarFeature = productObject.getString(TAG_ITEM_FEATURE);
                String mScholarMostView = productObject.getString(TAG_ITEM_MOST_VIEW);
                String mScholarStatus = productObject.getString(TAG_ITEM_STATUS);

                if (mScholarId == null || mScholarId.equals("") ||
                        mScholarTitle == null || mScholarTitle.equals("") ||
                        mScholarThumbnailUrl == null || mScholarThumbnailUrl.equals("") ||
                        mScholarFeature == null || mScholarFeature.equals("") ||
                        mScholarMostView == null || mScholarMostView.equals("") ||
                        mScholarStatus == null || mScholarStatus.equals("")) {

                    // do nothing...
                } else {

                    String decodedThumbnailUrl = decodeUrl(mScholarThumbnailUrl);

                    int scholarId = addScholarData(serviceId, mScholarId, mScholarTitle, decodedThumbnailUrl, mScholarFeature,
                            mScholarMostView, mScholarStatus);
                }
            }
        }
    }

    private int addScholarData(int serviceId, String strScholarNo, String title, String thumbnailUrl,
                               String strFeature, String strMostView, String status) {

        int scholarNo = Integer.parseInt(strScholarNo);
        int feature = Integer.parseInt(strFeature);
        int mostView = Integer.parseInt(strMostView);

        ScholarModel scholarModel = DBAdapter.getScholarModel(scholarNo, serviceId);

        int galleryId = scholarModel.getId();

        if (galleryId > 0) {

            String oldTitle = scholarModel.getTitle();
            String oldThumbnailUrl = scholarModel.getThumbnailUrl();
            int oldFeatured = scholarModel.getFeatured();
            int oldMostView = scholarModel.getMostView();
            String oldStatus = scholarModel.getStatus();

            if (TextUtils.equals(oldTitle, title)
                    && TextUtils.equals(oldThumbnailUrl, thumbnailUrl)
                    && oldFeatured == feature
                    && oldMostView == mostView
                    && TextUtils.equals(oldStatus, status)) {

                // do nothing....
            } else {

                scholarModel.setNo(scholarNo);
                scholarModel.setServiceId(serviceId);
                scholarModel.setTitle(title);
                scholarModel.setThumbnailUrl(thumbnailUrl);
                scholarModel.setFeatured(feature);
                scholarModel.setMostView(mostView);
                scholarModel.setStatus(status);

                DBAdapter.updateScholarData(scholarModel);

                updatedProducts++;

                updateThumbnail(oldThumbnailUrl, thumbnailUrl, serviceId, scholarNo);
            }

        } else {

            ScholarModel tempScholarModel = new ScholarModel();

            tempScholarModel.setId(0);
            tempScholarModel.setNo(scholarNo);
            tempScholarModel.setServiceId(serviceId);
            tempScholarModel.setTitle(title);
            tempScholarModel.setDescription("");  // empty
            tempScholarModel.setThumbnailUrl(thumbnailUrl);
            tempScholarModel.setFeatured(feature);
            tempScholarModel.setMostView(mostView);
            tempScholarModel.setMostDownload(0);
            tempScholarModel.setWebsite("");  // empty
            tempScholarModel.setUpdatedOn("");  // empty
            tempScholarModel.setStatus(status);  // empty
            tempScholarModel.setBirthDate(""); // empty
            tempScholarModel.setUrsDate(""); // empty
            tempScholarModel.setPhotoUrl(""); // empty

            long rowId = DBAdapter.addScholarData(tempScholarModel);

            if (rowId > -1) {

                galleryId = DBAdapter.getScholarModel(scholarNo, serviceId).getId();
                newProducts++;

                //            Log.d(TAG, "JsonObject > Scholar # " + newProducts + ": "
                //                   + tempScholarModel.toString());

            } else {
                galleryId = 0;
            }
        }

        return galleryId;
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

            } else if (productObject == null || productObject.length() < 6) {
                // do nothing...
            } else {

                String mGalleryId = productObject.getString(TAG_ITEM_ID);
                String mGalleryTitle = productObject.getString(TAG_ITEM_TITLE);
                String mGalleryFeature = productObject.getString(TAG_ITEM_FEATURE);
                String mGalleryMostView = productObject.getString(TAG_ITEM_MOST_VIEW);
                String mGalleryThumbnailUrl = productObject.getString(TAG_ITEM_THUMBNAIL_URL);
                String mGalleryStatus = productObject.getString(TAG_ITEM_STATUS);

                String mGalleryCategory = "";

                try {
                    mGalleryCategory = productObject.getString(TAG_ITEM_CATEGORY);
                } catch (JSONException ex) {
                    mGalleryCategory = "-";
                }

                if (mGalleryId == null || mGalleryId.equals("") ||
                        mGalleryTitle == null || mGalleryTitle.equals("") ||
                        mGalleryThumbnailUrl == null || mGalleryThumbnailUrl.equals("") ||
                        mGalleryFeature == null || mGalleryFeature.equals("") ||
                        mGalleryMostView == null || mGalleryMostView.equals("") ||
                        mGalleryStatus == null || mGalleryStatus.equals("") ||
                        mGalleryCategory == null || mGalleryCategory.equals("")) {

                    // do nothing...
                } else {

                    String decodedThumbnailUrl = decodeUrl(mGalleryThumbnailUrl);

                    int galleryId = addGalleryData(serviceId, mGalleryId, mGalleryTitle, decodedThumbnailUrl, mGalleryFeature,
                            mGalleryMostView, mGalleryCategory, mGalleryStatus);
                }
            }
        }
    }

    private int addGalleryData(int serviceId, String strGalleryNo, String title, String thumbnailUrl, String strFeature,
                               String strMostView, String category, String status) {

        int galleryNo = Integer.parseInt(strGalleryNo);
        int feature = Integer.parseInt(strFeature);
        int mostView = Integer.parseInt(strMostView);

        GalleryModel galleryModel = DBAdapter.getGalleryModel(galleryNo, serviceId);

        int galleryId = galleryModel.getId();

        if (galleryId > 0) {

            String oldTitle = galleryModel.getTitle();
            String oldCategory = galleryModel.getCategory();
            int oldFeatured = galleryModel.getFeatured();
            int oldMostView = galleryModel.getMostView();
            String oldThumbnailUrl = galleryModel.getThumbnailUrl();
            String oldStatus = galleryModel.getStatus();

            if (TextUtils.equals(oldTitle, title)
                    && TextUtils.equals(oldCategory, category)
                    && oldFeatured == feature
                    && oldMostView == mostView
                    && TextUtils.equals(oldThumbnailUrl, thumbnailUrl)
                    && TextUtils.equals(oldStatus, status)) {

                // do nothing....
            } else {

                galleryModel.setNo(galleryNo);
                galleryModel.setServiceId(serviceId);
                galleryModel.setTitle(title);
                galleryModel.setThumbnailUrl(thumbnailUrl);
                galleryModel.setFeatured(feature);
                galleryModel.setMostView(mostView);
                galleryModel.setStatus(status);
                galleryModel.setCategory(category);

                DBAdapter.updateGalleryData(galleryModel);

                updatedProducts++;

                updateThumbnail(oldThumbnailUrl, thumbnailUrl, serviceId, galleryNo);
            }

        } else {

            GalleryModel tempGalleryModel = new GalleryModel();

            tempGalleryModel.setId(0);
            tempGalleryModel.setNo(galleryNo);
            tempGalleryModel.setServiceId(serviceId);
            tempGalleryModel.setTitle(title);
            tempGalleryModel.setDescription("");  // empty
            tempGalleryModel.setThumbnailUrl(thumbnailUrl);
            tempGalleryModel.setFeatured(feature);
            tempGalleryModel.setMostView(mostView);
            tempGalleryModel.setMostDownload(0);
            tempGalleryModel.setWebsite(""); // empty
            tempGalleryModel.setUpdatedOn(""); // empty
            tempGalleryModel.setStatus(status); // empty
            tempGalleryModel.setCategory(category);
            tempGalleryModel.setPhotoUrl(""); // empty

            long rowId = DBAdapter.addGalleryData(tempGalleryModel);

            if (rowId > -1) {

                galleryId = DBAdapter.getGalleryModel(galleryNo, serviceId).getId();
                newProducts++;

            } else {
                galleryId = 0;
            }
        }

        return galleryId;
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

            } else if (productObject == null || productObject.length() < 6) {
                // do nothing...
            } else {

                String mNewsId = productObject.getString(TAG_ITEM_ID);
                String mNewsTitle = productObject.getString(TAG_ITEM_TITLE);
                String mNewsThumbnailUrl = productObject.getString(TAG_ITEM_THUMBNAIL_URL);
                String mNewsFeature = productObject.getString(TAG_ITEM_FEATURE);
                String mNewsMostView = productObject.getString(TAG_ITEM_MOST_VIEW);
                String mNewsStatus = productObject.getString(TAG_ITEM_STATUS);

                String mNewsCountry = "";

                try {
                    mNewsCountry = productObject.getString(TAG_ITEM_COUNTRY);
                } catch (JSONException ex) {
                    mNewsCountry = "-";
                }

                String mNewsOrganization = "";

                try {
                    mNewsOrganization = productObject.getString(TAG_ITEM_ORGANIZATION);
                } catch (JSONException ex) {
                    mNewsOrganization = "-";
                }

                String mNewsNewsPaper = "";

                try {
                    mNewsNewsPaper = productObject.getString(TAG_ITEM_NEWSPAPER);
                } catch (JSONException ex) {
                    mNewsNewsPaper = "-";
                }

                if (mNewsId == null || mNewsId.equals("") ||
                        mNewsTitle == null || mNewsTitle.equals("") ||
                        mNewsOrganization == null || mNewsOrganization.equals("") ||
                        mNewsCountry == null || mNewsCountry.equals("") ||
                        mNewsFeature == null || mNewsFeature.equals("") ||
                        mNewsMostView == null || mNewsMostView.equals("") ||
                        mNewsThumbnailUrl == null || mNewsThumbnailUrl.equals("") ||
                        mNewsStatus == null || mNewsStatus.equals("")) {

                    // do nothing...
                } else {

                    String decodedThumbnailUrl = decodeUrl(mNewsThumbnailUrl);

                    int newsId = addNewsData(serviceId, mNewsId, mNewsTitle, mNewsOrganization, mNewsCountry, mNewsNewsPaper,
                            mNewsFeature, mNewsMostView, decodedThumbnailUrl, mNewsStatus);
                }
            }
        }
    }

    private int addNewsData(int serviceId, String strNewsNo, String title, String organization, String country, String newsPaper,
                            String strFeature, String strMostView, String thumbnailUrl, String status) {

        int newsNo = Integer.parseInt(strNewsNo);
        int feature = Integer.parseInt(strFeature);
        int mostView = Integer.parseInt(strMostView);

        NewsModel newsModel = DBAdapter.getNewsModel(newsNo, serviceId);

        int newsId = newsModel.getId();

        if (newsId > 0) {

            String oldTitle = newsModel.getTitle();
            String oldOrganization = newsModel.getOrganization();
            String oldCountry = newsModel.getCountry();
            String oldNewsPaper = newsModel.getNewsPaper();
            int oldFeatured = newsModel.getFeatured();
            int oldMostView = newsModel.getMostView();
            String oldThumbnailUrl = newsModel.getThumbnailUrl();
            String oldStatus = newsModel.getStatus();

            if (TextUtils.equals(oldTitle, title)
                    && TextUtils.equals(oldOrganization, organization)
                    && TextUtils.equals(oldCountry, country)
                    && TextUtils.equals(oldNewsPaper, newsPaper)
                    && oldFeatured == feature
                    && oldMostView == mostView
                    && TextUtils.equals(oldThumbnailUrl, thumbnailUrl)
                    && TextUtils.equals(oldStatus, status)) {

                // do nothing....
            } else {

                newsModel.setNo(newsNo);
                newsModel.setServiceId(serviceId);
                newsModel.setTitle(title);
                newsModel.setOrganization(oldOrganization);
                newsModel.setCountry(country);
                newsModel.setNewsPaper(newsPaper);
                newsModel.setFeatured(feature);
                newsModel.setMostView(mostView);
                newsModel.setThumbnailUrl(thumbnailUrl);
                newsModel.setStatus(status);

                DBAdapter.updateNewsData(newsModel);

                updatedProducts++;

                updateThumbnail(oldThumbnailUrl, thumbnailUrl, serviceId, newsNo);
            }

        } else {

            NewsModel tempNewsModel = new NewsModel();

            tempNewsModel.setId(0);
            tempNewsModel.setNo(newsNo);
            tempNewsModel.setServiceId(serviceId);
            tempNewsModel.setTitle(title);
            tempNewsModel.setDescription(""); // empty
            tempNewsModel.setThumbnailUrl(thumbnailUrl);
            tempNewsModel.setFeatured(feature);
            tempNewsModel.setMostView(mostView);
            tempNewsModel.setMostDownload(0);
            tempNewsModel.setWebsite(""); // empty
            tempNewsModel.setUpdatedOn(""); // empty
            tempNewsModel.setStatus(status);
            tempNewsModel.setOrganization(organization);
            tempNewsModel.setCountry(country);
            tempNewsModel.setNewsPaper(newsPaper);
            tempNewsModel.setPhotoUrl(""); // empty

            long rowId = DBAdapter.addNewsData(tempNewsModel);

            if (rowId > -1) {

                newsId = DBAdapter.getNewsModel(newsNo, serviceId).getId();
                newProducts++;

            } else {
                newsId = 0;
            }
        }

        return newsId;
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

                String mPlaceId = productObject.getString(TAG_ITEM_ID);
                String mPlaceTitle = productObject.getString(TAG_ITEM_TITLE);
                String mPlaceThumbnailUrl = productObject.getString(TAG_ITEM_THUMBNAIL_URL);
                String mPlaceFeature = productObject.getString(TAG_ITEM_FEATURE);
                String mPlaceMostView = productObject.getString(TAG_ITEM_MOST_VIEW);
                String mPlaceStatus = productObject.getString(TAG_ITEM_STATUS);


                String mPlaceCategory = "";

                try {
                    mPlaceCategory = productObject.getString(TAG_ITEM_CATEGORY);
                } catch (JSONException ex) {
                    mPlaceCategory = "-";
                }

                if (mPlaceId == null || mPlaceId.equals("") ||
                        mPlaceTitle == null || mPlaceTitle.equals("") ||
                        mPlaceThumbnailUrl == null || mPlaceThumbnailUrl.equals("") ||
                        mPlaceFeature == null || mPlaceFeature.equals("") ||
                        mPlaceMostView == null || mPlaceMostView.equals("") ||
                        mPlaceStatus == null || mPlaceStatus.equals("") ||
                        mPlaceCategory == null || mPlaceCategory.equals("")) {

                    // do nothing...
                } else {

                    String decodedThumbnailUrl = decodeUrl(mPlaceThumbnailUrl);

                    int galleryId = addPlaceData(serviceId, mPlaceId, mPlaceTitle, decodedThumbnailUrl, mPlaceFeature,
                            mPlaceMostView, mPlaceCategory, mPlaceStatus);
                }
            }
        }
    }

    private int addPlaceData(int serviceId, String strPlaceNo, String title, String thumbnailUrl, String strFeature,
                             String strMostView, String category, String status) {

        int placeNo = Integer.parseInt(strPlaceNo);
        int feature = Integer.parseInt(strFeature);
        int mostView = Integer.parseInt(strMostView);

        PlaceModel placeModel = DBAdapter.getPlaceModel(placeNo, serviceId);

        int placeId = placeModel.getId();

        if (placeId > 0) {

            String oldTitle = placeModel.getTitle();
            String oldCategory = placeModel.getCategory();
            int oldFeatured = placeModel.getFeatured();
            int oldMostView = placeModel.getMostView();
            String oldThumbnailUrl = placeModel.getThumbnailUrl();
            String oldStatus = placeModel.getStatus();

            if (TextUtils.equals(oldTitle, title)
                    && TextUtils.equals(oldThumbnailUrl, thumbnailUrl)
                    && oldFeatured == feature
                    && oldMostView == mostView
                    && TextUtils.equals(oldCategory, category)
                    && TextUtils.equals(oldStatus, status)) {

                // do nothing....
            } else {

                placeModel.setNo(placeNo);
                placeModel.setServiceId(serviceId);
                placeModel.setTitle(title);
                placeModel.setCategory(category);
                placeModel.setFeatured(feature);
                placeModel.setMostView(mostView);
                placeModel.setThumbnailUrl(thumbnailUrl);
                placeModel.setStatus(status);

                DBAdapter.updatePlaceData(placeModel);

                updatedProducts++;

                updateThumbnail(oldThumbnailUrl, thumbnailUrl, serviceId, placeNo);
            }

        } else {

            PlaceModel tempPlaceModel = new PlaceModel();

            tempPlaceModel.setId(0);
            tempPlaceModel.setNo(placeNo);
            tempPlaceModel.setServiceId(serviceId);
            tempPlaceModel.setTitle(title);
            tempPlaceModel.setDescription("");  // empty
            tempPlaceModel.setThumbnailUrl(thumbnailUrl);
            tempPlaceModel.setFeatured(feature);
            tempPlaceModel.setMostView(mostView);
            tempPlaceModel.setMostDownload(0);
            tempPlaceModel.setWebsite("");  // empty
            tempPlaceModel.setUpdatedOn("");  // empty
            tempPlaceModel.setStatus(status);
            tempPlaceModel.setCategory(category);
            tempPlaceModel.setPhotoUrl("");  // empty

            long rowId = DBAdapter.addPlaceData(tempPlaceModel);

            if (rowId > -1) {

                placeId = DBAdapter.getPlaceModel(placeNo, serviceId).getId();
                newProducts++;

            } else {
                placeId = 0;
            }
        }

        return placeId;
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

                String mHistoryId = productObject.getString(TAG_ITEM_ID);
                String mHistoryTitle = productObject.getString(TAG_ITEM_TITLE);
                String mHistoryThumbnailUrl = productObject.getString(TAG_ITEM_THUMBNAIL_URL);
                String mHistoryFeature = productObject.getString(TAG_ITEM_FEATURE);
                String mHistoryMostView = productObject.getString(TAG_ITEM_MOST_VIEW);
                String mHistoryStatus = productObject.getString(TAG_ITEM_STATUS);

                String mHistoryCategory = "";

                try {
                    mHistoryCategory = productObject.getString(TAG_ITEM_CATEGORY);
                } catch (JSONException ex) {
                    mHistoryCategory = "-";
                }

                if (mHistoryId == null || mHistoryId.equals("") ||
                        mHistoryTitle == null || mHistoryTitle.equals("") ||
                        mHistoryCategory == null || mHistoryCategory.equals("") ||
                        mHistoryFeature == null || mHistoryFeature.equals("") ||
                        mHistoryMostView == null || mHistoryMostView.equals("") ||
                        mHistoryThumbnailUrl == null || mHistoryThumbnailUrl.equals("") ||
                        mHistoryStatus == null || mHistoryStatus.equals("")) {

                    // do nothing...
                } else {

                    String decodedThumbnailUrl = decodeUrl(mHistoryThumbnailUrl);

                    int historyId = addHistoryData(serviceId, mHistoryId, mHistoryTitle, mHistoryCategory, mHistoryFeature,
                            mHistoryMostView, decodedThumbnailUrl, mHistoryStatus);
                }
            }
        }
    }

    private int addHistoryData(int serviceId, String strHistoryNo, String title, String category, String strFeature,
                               String strMostView, String thumbnailUrl, String status) {

        int historyNo = Integer.parseInt(strHistoryNo);
        int feature = Integer.parseInt(strFeature);
        int mostView = Integer.parseInt(strMostView);

        HistoryModel historyModel = DBAdapter.getHistoryModel(historyNo, serviceId);

        int historyId = historyModel.getId();

        if (historyId > 0) {

            String oldTitle = historyModel.getTitle();
            String oldCategory = historyModel.getCategory();
            int oldFeatured = historyModel.getFeatured();
            int oldMostView = historyModel.getMostView();
            String oldThumbnailUrl = historyModel.getThumbnailUrl();
            String oldStatus = historyModel.getStatus();

            if (TextUtils.equals(oldTitle, title)
                    && TextUtils.equals(oldCategory, category)
                    && oldFeatured == feature
                    && oldMostView == mostView
                    && TextUtils.equals(oldThumbnailUrl, thumbnailUrl)
                    && TextUtils.equals(oldStatus, status)) {

                // do nothing....
            } else {

                historyModel.setNo(historyNo);
                historyModel.setServiceId(serviceId);
                historyModel.setTitle(title);
                historyModel.setCategory(category);
                historyModel.setFeatured(feature);
                historyModel.setMostView(mostView);
                historyModel.setThumbnailUrl(thumbnailUrl);
                historyModel.setStatus(status);

                DBAdapter.updateHistoryData(historyModel);

                updatedProducts++;

                updateThumbnail(oldThumbnailUrl, thumbnailUrl, serviceId, historyNo);
            }

        } else {

            HistoryModel tempHistoryModel = new HistoryModel();

            tempHistoryModel.setId(0);
            tempHistoryModel.setNo(historyNo);
            tempHistoryModel.setServiceId(serviceId);
            tempHistoryModel.setTitle(title);
            tempHistoryModel.setDescription("");  // empty
            tempHistoryModel.setThumbnailUrl(thumbnailUrl);
            tempHistoryModel.setFeatured(feature);
            tempHistoryModel.setMostView(mostView);
            tempHistoryModel.setMostDownload(0);
            tempHistoryModel.setWebsite("");  // empty
            tempHistoryModel.setUpdatedOn("");  // empty
            tempHistoryModel.setStatus(status);
            tempHistoryModel.setCategory(category);
            tempHistoryModel.setPhotoUrl(""); // empty

            long rowId = DBAdapter.addHistoryData(tempHistoryModel);

            if (rowId > -1) {

                historyId = DBAdapter.getHistoryModel(historyNo, serviceId).getId();
                newProducts++;

            } else {
                historyId = 0;
            }
        }

        return historyId;
    }

    private void retrieveDaysData(int serviceId, JSONArray productArray) throws JSONException {

        for (int i = 0; i < productArray.length(); i++) {

            JSONObject productObject = productArray.getJSONObject(i);

            if (DEBUG) {
                int no = i + 1;
                Log.d(TAG, "JsonObject > Day # " + no + ": "
                        + productObject.length() + ", " + productObject.toString());
            }

            if (isCancelled()) {

                break;

            } else if (productObject == null || productObject.length() < 5) {
                // do nothing...
            } else {

                String mDayId = productObject.getString(TAG_ITEM_ID);
                String mDayTitle = productObject.getString(TAG_ITEM_TITLE);
                String mDayFeature = productObject.getString(TAG_ITEM_FEATURE);
                String mDayMostView = productObject.getString(TAG_ITEM_MOST_VIEW);
                String mDayStatus = productObject.getString(TAG_ITEM_STATUS);

                String mDayThumbnailUrl = "";

                try {
                    mDayThumbnailUrl = productObject.getString(TAG_ITEM_THUMBNAIL_URL);
                } catch (JSONException ex) {
                    mDayThumbnailUrl = "-";
                }

                if (mDayId == null || mDayId.equals("") ||
                        mDayTitle == null || mDayTitle.equals("") ||
                        mDayFeature == null || mDayFeature.equals("") ||
                        mDayMostView == null || mDayMostView.equals("") ||
                        mDayThumbnailUrl == null || mDayThumbnailUrl.equals("") ||
                        mDayStatus == null || mDayStatus.equals("")) {

                    // do nothing...
                } else {

                    String decodedThumbnailUrl = decodeUrl(mDayThumbnailUrl);

                    int dayId = addDaysData(serviceId, mDayId, mDayTitle, mDayFeature,
                            mDayMostView, decodedThumbnailUrl, mDayStatus);
                }
            }
        }
    }

    private int addDaysData(int serviceId, String strDayNo, String title, String strFeature,
                            String strMostView, String thumbnailUrl, String status) {

        int dayNo = Integer.parseInt(strDayNo);
        int feature = Integer.parseInt(strFeature);
        int mostView = Integer.parseInt(strMostView);

        DayModel dayModel = DBAdapter.getDaysModel(dayNo, serviceId);

        int galleryId = dayModel.getId();

        if (galleryId > 0) {

            String oldTitle = dayModel.getTitle();
            int oldFeatured = dayModel.getFeatured();
            int oldMostView = dayModel.getMostView();
            String oldThumbnailUrl = dayModel.getThumbnailUrl();
            String oldStatus = dayModel.getStatus();

            if (TextUtils.equals(oldTitle, title)
                    && oldFeatured == feature
                    && oldMostView == mostView
                    && TextUtils.equals(oldThumbnailUrl, thumbnailUrl)
                    && TextUtils.equals(oldStatus, status)) {

                // do nothing....
            } else {

                dayModel.setNo(dayNo);
                dayModel.setServiceId(serviceId);
                dayModel.setTitle(title);
                dayModel.setFeatured(feature);
                dayModel.setMostView(mostView);
                dayModel.setThumbnailUrl(thumbnailUrl);
                dayModel.setStatus(status);

                DBAdapter.updateDaysData(dayModel);

                updatedProducts++;

                updateThumbnail(oldThumbnailUrl, thumbnailUrl, serviceId, dayNo);
            }

        } else {

            DayModel tempDayModel = new DayModel();

            tempDayModel.setId(0);
            tempDayModel.setNo(dayNo);
            tempDayModel.setServiceId(serviceId);
            tempDayModel.setTitle(title);
            tempDayModel.setDescription("");  // empty
            tempDayModel.setFeatured(feature);
            tempDayModel.setMostView(mostView);
            tempDayModel.setMostDownload(0);
            tempDayModel.setThumbnailUrl(thumbnailUrl);
            tempDayModel.setStatus(status);
            tempDayModel.setWebsite("");  // empty
            tempDayModel.setUpdatedOn("");  // empty
            tempDayModel.setPhotoUrl(""); // empty

            long rowId = DBAdapter.addDaysData(tempDayModel);

            if (rowId > -1) {

                galleryId = DBAdapter.getDaysModel(dayNo, serviceId).getId();
                newProducts++;

            } else {
                galleryId = 0;
            }
        }

        return galleryId;
    }

    private void retrieveDepartmentsData(int serviceId, JSONArray productArray) throws JSONException {

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

                String mDepartmentId = productObject.getString(TAG_ITEM_ID);
                String mDepartmentTitle = productObject.getString(TAG_ITEM_TITLE);
                String mDepartmentFeature = productObject.getString(TAG_ITEM_FEATURE);
                String mDepartmentMostView = productObject.getString(TAG_ITEM_MOST_VIEW);
                String mDepartmentStatus = productObject.getString(TAG_ITEM_STATUS);
                String mDepartmentThumbnailUrl = productObject.getString(TAG_ITEM_THUMBNAIL_URL);


                if (mDepartmentId == null || mDepartmentId.equals("") ||
                        mDepartmentTitle == null || mDepartmentTitle.equals("") ||
                        mDepartmentFeature == null || mDepartmentFeature.equals("") ||
                        mDepartmentMostView == null || mDepartmentMostView.equals("") ||
                        mDepartmentThumbnailUrl == null || mDepartmentThumbnailUrl.equals("") ||
                        mDepartmentStatus == null || mDepartmentStatus.equals("")) {

                    // do nothing...
                } else {

                    String decodedThumbnailUrl = decodeUrl(mDepartmentThumbnailUrl);

                    int dayId = addDepartmentsData(serviceId, mDepartmentId, mDepartmentTitle, mDepartmentFeature,
                            mDepartmentMostView, decodedThumbnailUrl, mDepartmentStatus);
                }
            }
        }
    }

    private int addDepartmentsData(int serviceId, String strDepartmentNo, String title, String strFeature,
                                   String strMostView, String thumbnailUrl, String status) {

        int departmentNo = Integer.parseInt(strDepartmentNo);
        int feature = Integer.parseInt(strFeature);
        int mostView = Integer.parseInt(strMostView);

        DepartmentModel departmentsModel = DBAdapter.getDepartmentsModel(departmentNo, serviceId);

        int galleryId = departmentsModel.getId();

        if (galleryId > 0) {

            String oldTitle = departmentsModel.getTitle();
            String oldDescription = departmentsModel.getDescription();
            int oldFeatured = departmentsModel.getFeatured();
            int oldMostView = departmentsModel.getMostView();
            String oldThumbnailUrl = departmentsModel.getThumbnailUrl();
            String oldStatus = departmentsModel.getStatus();

            if (TextUtils.equals(oldTitle, title)
                    && oldFeatured == feature
                    && oldMostView == mostView
                    && TextUtils.equals(oldThumbnailUrl, thumbnailUrl)
                    && TextUtils.equals(oldStatus, status)) {

                // do nothing....
            } else {

                departmentsModel.setNo(departmentNo);
                departmentsModel.setServiceId(serviceId);
                departmentsModel.setTitle(title);
                departmentsModel.setFeatured(feature);
                departmentsModel.setMostView(mostView);
                departmentsModel.setThumbnailUrl(thumbnailUrl);
                departmentsModel.setStatus(status);

                DBAdapter.updateDepartmentsData(departmentsModel);

                updatedProducts++;

                updateThumbnail(oldThumbnailUrl, thumbnailUrl, serviceId, departmentNo);
            }

        } else {

            DepartmentModel tempDepartmentModel = new DepartmentModel();

            tempDepartmentModel.setId(0);
            tempDepartmentModel.setNo(departmentNo);
            tempDepartmentModel.setServiceId(serviceId);
            tempDepartmentModel.setTitle(title);
            tempDepartmentModel.setDescription("");  // empty
            tempDepartmentModel.setFeatured(feature);
            tempDepartmentModel.setMostView(mostView);
            tempDepartmentModel.setMostDownload(0);
            tempDepartmentModel.setThumbnailUrl(thumbnailUrl);
            tempDepartmentModel.setStatus(status); // empty
            tempDepartmentModel.setWebsite(""); // empty
            tempDepartmentModel.setUpdatedOn(""); // empty
            tempDepartmentModel.setPhotoUrl(""); // empty

            long rowId = DBAdapter.addDepartmentsData(tempDepartmentModel);

            if (rowId > -1) {

                galleryId = DBAdapter.getDepartmentsModel(departmentNo, serviceId).getId();
                newProducts++;

            } else {
                galleryId = 0;
            }
        }

        return galleryId;
    }

    private void retrieveAppsData(int serviceId, JSONArray productArray) throws JSONException {

        for (int i = 0; i < productArray.length(); i++) {

            JSONObject productObject = productArray.getJSONObject(i);

            if (DEBUG) {
                int no = i + 1;
                Log.d(TAG, "JsonObject > App # " + no + ": "
                        + productObject.length() + ", " + productObject.toString());
            }

            if (isCancelled()) {

                break;

            } else if (productObject == null || productObject.length() < 6) {
                // do nothing...
            } else {

                String mAppId = productObject.getString(TAG_ITEM_ID);
                String mAppTitle = productObject.getString(TAG_ITEM_TITLE);
                String mAppFeature = productObject.getString(TAG_ITEM_FEATURE);
                String mAppMostView = productObject.getString(TAG_ITEM_MOST_VIEW);
                String mAppThumbnailUrl = productObject.getString(TAG_ITEM_THUMBNAIL_URL);
                String mAppStatus = productObject.getString(TAG_ITEM_STATUS);


                if (mAppId == null || mAppId.equals("") ||
                        mAppTitle == null || mAppTitle.equals("") ||
                        mAppFeature == null || mAppFeature.equals("") ||
                        mAppMostView == null || mAppMostView.equals("") ||
                        mAppThumbnailUrl == null || mAppThumbnailUrl.equals("") ||
                        mAppStatus == null || mAppStatus.equals("")) {

                    // do nothing...
                } else {

                    String decodedThumbnailUrl = decodeUrl(mAppThumbnailUrl);

                    int appId = addAppsData(serviceId, mAppId, mAppTitle, mAppFeature,
                            mAppMostView, decodedThumbnailUrl, mAppStatus);
                }
            }
        }
    }

    private int addAppsData(int serviceId, String strAppNo, String title, String strFeature,
                            String strMostView, String thumbnailUrl, String status) {

        int appNo = Integer.parseInt(strAppNo);
        int feature = Integer.parseInt(strFeature);
        int mostView = Integer.parseInt(strMostView);

        AppModel appModel = DBAdapter.getAppsModel(appNo, serviceId);

        int galleryId = appModel.getId();

        if (galleryId > 0) {

            String oldTitle = appModel.getTitle();
            int oldFeatured = appModel.getFeatured();
            int oldMostView = appModel.getMostView();
            String oldThumbnailUrl = appModel.getThumbnailUrl();
            String oldStatus = appModel.getStatus();

            if (TextUtils.equals(oldTitle, title)
                    && oldFeatured == feature
                    && oldMostView == mostView
                    && TextUtils.equals(oldThumbnailUrl, thumbnailUrl)
                    && TextUtils.equals(oldStatus, status)) {

                // do nothing....
            } else {

                appModel.setNo(appNo);
                appModel.setServiceId(serviceId);
                appModel.setTitle(title);
                appModel.setFeatured(feature);
                appModel.setMostView(mostView);
                appModel.setThumbnailUrl(thumbnailUrl);
                appModel.setStatus(status);

                DBAdapter.updateAppsData(appModel);

                updatedProducts++;

                updateThumbnail(oldThumbnailUrl, thumbnailUrl, serviceId, appNo);
            }

        } else {

            AppModel tempAppModel = new AppModel();

            tempAppModel.setId(0);
            tempAppModel.setNo(appNo);
            tempAppModel.setServiceId(serviceId);
            tempAppModel.setTitle(title);
            tempAppModel.setDescription("");  // empty
            tempAppModel.setFeatured(feature);
            tempAppModel.setMostView(mostView);
            tempAppModel.setMostDownload(0);
            tempAppModel.setThumbnailUrl(thumbnailUrl);
            tempAppModel.setWebsite(""); // empty
            tempAppModel.setUpdatedOn(""); // empty
            tempAppModel.setStatus(status); // empty
            tempAppModel.setDownloadApp(""); // empty
            tempAppModel.setPhotoUrl(""); // empty

            long rowId = DBAdapter.addAppsData(tempAppModel);

            if (rowId > -1) {

                galleryId = DBAdapter.getAppsModel(appNo, serviceId).getId();
                newProducts++;

            } else {
                galleryId = 0;
            }
        }

        return galleryId;
    }

    private void retrieveArticlesData(int serviceId, JSONArray productArray) throws JSONException {

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

                String mArticleId = productObject.getString(TAG_ITEM_ID);
                String mArticleTitle = productObject.getString(TAG_ITEM_TITLE);
                String mArticleFeature = productObject.getString(TAG_ITEM_FEATURE);
                String mArticleMostView = productObject.getString(TAG_ITEM_MOST_VIEW);
                String mArticleStatus = productObject.getString(TAG_ITEM_STATUS);
                String mArticleThumbnailUrl = productObject.getString(TAG_ITEM_THUMBNAIL_URL);

                String mArticleCategory = "";

                try {
                    mArticleCategory = productObject.getString(TAG_ITEM_CATEGORY);
                } catch (JSONException ex) {
                    mArticleCategory = "-";
                }

                String mArticleWriter = "";

                try {
                    mArticleWriter = productObject.getString(TAG_ITEM_WRITER);
                } catch (JSONException ex) {
                    mArticleWriter = "-";
                }

                if (mArticleId == null || mArticleId.equals("") ||
                        mArticleTitle == null || mArticleTitle.equals("") ||
                        mArticleCategory == null || mArticleCategory.equals("") ||
                        mArticleWriter == null || mArticleWriter.equals("") ||
                        mArticleFeature == null || mArticleFeature.equals("") ||
                        mArticleMostView == null || mArticleMostView.equals("") ||
                        mArticleThumbnailUrl == null || mArticleThumbnailUrl.equals("") ||
                        mArticleStatus == null || mArticleStatus.equals("")) {

                    // do nothing...
                } else {

                    String decodedThumbnailUrl = decodeUrl(mArticleThumbnailUrl);

                    int articleId = addArticleData(serviceId, mArticleId, mArticleTitle, mArticleCategory, mArticleWriter,
                            mArticleFeature, mArticleMostView, decodedThumbnailUrl, mArticleStatus);
                }
            }
        }
    }

    private int addArticleData(int serviceId, String strArticleNo, String title, String category, String writer,
                               String strFeature, String strMostView, String thumbnailUrl, String status) {

        int articleNo = Integer.parseInt(strArticleNo);
        int feature = Integer.parseInt(strFeature);
        int mostView = Integer.parseInt(strMostView);

        ArticleModel articleModel = DBAdapter.getArticleModel(articleNo, serviceId);

        int bookId = articleModel.getId();

        if (bookId > 0) {

            String oldTitle = articleModel.getTitle();
            String oldCategory = articleModel.getCategory();
            String oldWriter = articleModel.getWriter();
            int oldFeatured = articleModel.getFeatured();
            int oldMostView = articleModel.getMostView();
            String oldThumbnailUrl = articleModel.getThumbnailUrl();
            String oldStatus = articleModel.getStatus();

            if (TextUtils.equals(oldTitle, title)
                    && TextUtils.equals(oldCategory, category)
                    && TextUtils.equals(oldWriter, writer)
                    && oldFeatured == feature
                    && oldMostView == mostView
                    && TextUtils.equals(oldThumbnailUrl, thumbnailUrl)
                    && TextUtils.equals(oldStatus, status)) {

                // do nothing....
            } else {

                articleModel.setNo(articleNo);
                articleModel.setServiceId(serviceId);
                articleModel.setTitle(title);
                articleModel.setCategory(category);
                articleModel.setWriter(writer);
                articleModel.setFeatured(feature);
                articleModel.setMostView(mostView);
                articleModel.setThumbnailUrl(thumbnailUrl);
                articleModel.setStatus(status);

                DBAdapter.updateArticleData(articleModel);

                updatedProducts++;

                updateThumbnail(oldThumbnailUrl, thumbnailUrl, serviceId, articleNo);
            }

        } else {

            ArticleModel tempArticleModel = new ArticleModel();

            tempArticleModel.setId(0);
            tempArticleModel.setNo(articleNo);
            tempArticleModel.setServiceId(serviceId);
            tempArticleModel.setTitle(title);
            tempArticleModel.setDescription("");  // empty
            tempArticleModel.setThumbnailUrl(thumbnailUrl);
            tempArticleModel.setFeatured(feature);
            tempArticleModel.setMostView(mostView);
            tempArticleModel.setMostDownload(0);
            tempArticleModel.setWebsite(""); // empty
            tempArticleModel.setUpdatedOn(""); // empty
            tempArticleModel.setStatus(status);
            tempArticleModel.setCategory(category);
            tempArticleModel.setWriter(writer);
            tempArticleModel.setPhotoUrl(""); // empty

            long rowId = DBAdapter.addArticleData(tempArticleModel);

            if (rowId > -1) {
                bookId = DBAdapter.getArticleModel(articleNo, serviceId).getId();
                newProducts++;
            } else {
                bookId = 0;
            }
        }

        return bookId;
    }

    private void retrieveBlogsData(int serviceId, JSONArray productArray) throws JSONException {

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

                String mBlogId = productObject.getString(TAG_ITEM_ID);
                String mBlogTitle = productObject.getString(TAG_ITEM_TITLE);
                String mBlogFeature = productObject.getString(TAG_ITEM_FEATURE);
                String mBlogMostView = productObject.getString(TAG_ITEM_MOST_VIEW);
                String mBlogStatus = productObject.getString(TAG_ITEM_STATUS);
                String mBlogThumbnailUrl = productObject.getString(TAG_ITEM_THUMBNAIL_URL);

                String mBlogCategory = "";

                try {
                    mBlogCategory = productObject.getString(TAG_ITEM_CATEGORY);
                } catch (JSONException ex) {
                    mBlogCategory = "-";
                }

                String mBlogWriter = "";

                try {
                    mBlogWriter = productObject.getString(TAG_ITEM_WRITER);
                } catch (JSONException ex) {
                    mBlogWriter = "-";
                }

                if (mBlogId == null || mBlogId.equals("") ||
                        mBlogTitle == null || mBlogTitle.equals("") ||
                        mBlogCategory == null || mBlogCategory.equals("") ||
                        mBlogWriter == null || mBlogWriter.equals("") ||
                        mBlogFeature == null || mBlogFeature.equals("") ||
                        mBlogMostView == null || mBlogMostView.equals("") ||
                        mBlogThumbnailUrl == null || mBlogThumbnailUrl.equals("") ||
                        mBlogStatus == null || mBlogStatus.equals("")) {

                    // do nothing...
                } else {

                    String decodedThumbnailUrl = decodeUrl(mBlogThumbnailUrl);

                    int articleId = addBlogData(serviceId, mBlogId, mBlogTitle, mBlogCategory, mBlogWriter,
                            mBlogFeature, mBlogMostView, decodedThumbnailUrl, mBlogStatus);
                }
            }
        }
    }

    private int addBlogData(int serviceId, String strBlogNo, String title, String category, String writer,
                            String strFeature, String strMostView, String thumbnailUrl, String status) {

        int blogNo = Integer.parseInt(strBlogNo);
        int feature = Integer.parseInt(strFeature);
        int mostView = Integer.parseInt(strMostView);

        BlogModel blogModel = DBAdapter.getBlogModel(blogNo, serviceId);

        int bookId = blogModel.getId();

        if (bookId > 0) {

            String oldTitle = blogModel.getTitle();
            String oldDescription = blogModel.getDescription();
            String oldCategory = blogModel.getCategory();
            String oldWriter = blogModel.getWriter();
            int oldFeatured = blogModel.getFeatured();
            int oldMostView = blogModel.getMostView();
            String oldThumbnailUrl = blogModel.getThumbnailUrl();
            String oldStatus = blogModel.getStatus();

            if (TextUtils.equals(oldTitle, title)
                    && TextUtils.equals(oldCategory, category)
                    && TextUtils.equals(oldWriter, writer)
                    && oldFeatured == feature
                    && oldMostView == mostView
                    && TextUtils.equals(oldThumbnailUrl, thumbnailUrl)
                    && TextUtils.equals(oldStatus, status)) {

                // do nothing....
            } else {

                blogModel.setNo(blogNo);
                blogModel.setServiceId(serviceId);
                blogModel.setTitle(title);
                blogModel.setDescription(oldDescription);
                blogModel.setCategory(category);
                blogModel.setWriter(writer);
                blogModel.setFeatured(feature);
                blogModel.setMostView(mostView);
                blogModel.setThumbnailUrl(thumbnailUrl);
                blogModel.setStatus(status);

                DBAdapter.updateBlogData(blogModel);

                updatedProducts++;

                updateThumbnail(oldThumbnailUrl, thumbnailUrl, serviceId, blogNo);
            }

        } else {

            BlogModel tempBlogModel = new BlogModel();

            tempBlogModel.setId(0);
            tempBlogModel.setNo(blogNo);
            tempBlogModel.setServiceId(serviceId);
            tempBlogModel.setTitle(title);
            tempBlogModel.setDescription("");  // empty
            tempBlogModel.setCategory(category);
            tempBlogModel.setWriter(writer);
            tempBlogModel.setFeatured(feature);
            tempBlogModel.setMostView(mostView);
            tempBlogModel.setMostDownload(0);
            tempBlogModel.setThumbnailUrl(thumbnailUrl);
            tempBlogModel.setStatus(status);
            tempBlogModel.setWebsite("");  // empty
            tempBlogModel.setUpdatedOn("");  // empty
            tempBlogModel.setPhotoUrl("");  // empty

            long rowId = DBAdapter.addBlogData(tempBlogModel);

            if (rowId > -1) {
                bookId = DBAdapter.getBlogModel(blogNo, serviceId).getId();
                newProducts++;
            } else {
                bookId = 0;
            }
        }

        return bookId;
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

                String mPoetryId = productObject.getString(TAG_ITEM_ID);
                String mPoetryTitle = productObject.getString(TAG_ITEM_TITLE);
                String mPoetryFeature = productObject.getString(TAG_ITEM_FEATURE);
                String mPoetryMostView = productObject.getString(TAG_ITEM_MOST_VIEW);
                String mPoetryStatus = productObject.getString(TAG_ITEM_STATUS);
                String mPoetryThumbnailUrl = productObject.getString(TAG_ITEM_THUMBNAIL_URL);

                String mPoetryCategory = "";

                try {
                    mPoetryCategory = productObject.getString(TAG_ITEM_CATEGORY);
                } catch (JSONException ex) {
                    mPoetryCategory = "-";
                }

                String mPoetryWriter = "";

                try {
                    mPoetryWriter = productObject.getString(TAG_ITEM_WRITER);
                } catch (JSONException ex) {
                    mPoetryWriter = "-";
                }

                if (mPoetryId == null || mPoetryId.equals("") ||
                        mPoetryTitle == null || mPoetryTitle.equals("") ||
                        mPoetryCategory == null || mPoetryCategory.equals("") ||
                        mPoetryWriter == null || mPoetryWriter.equals("") ||
                        mPoetryFeature == null || mPoetryFeature.equals("") ||
                        mPoetryMostView == null || mPoetryMostView.equals("") ||
                        mPoetryThumbnailUrl == null || mPoetryThumbnailUrl.equals("") ||
                        mPoetryStatus == null || mPoetryStatus.equals("")) {

                    // do nothing...
                } else {

                    String decodedThumbnailUrl = decodeUrl(mPoetryThumbnailUrl);

                    int articleId = addPoetryData(serviceId, mPoetryId, mPoetryTitle, mPoetryCategory, mPoetryWriter,
                            mPoetryFeature, mPoetryMostView, decodedThumbnailUrl, mPoetryStatus);
                }
            }
        }
    }

    private int addPoetryData(int serviceId, String strPoetryNo, String title, String category, String writer,
                              String strFeature, String strMostView, String thumbnailUrl, String status) {

        int poetryNo = Integer.parseInt(strPoetryNo);
        int feature = Integer.parseInt(strFeature);
        int mostView = Integer.parseInt(strMostView);

        PoetryModel poetryModel = DBAdapter.getPoetryModel(poetryNo, serviceId);

        int bookId = poetryModel.getId();

        if (bookId > 0) {

            String oldTitle = poetryModel.getTitle();
            String oldDescription = poetryModel.getDescription();
            String oldCategory = poetryModel.getCategory();
            String oldWriter = poetryModel.getWriter();
            int oldFeatured = poetryModel.getFeatured();
            int oldMostView = poetryModel.getMostView();
            String oldThumbnailUrl = poetryModel.getThumbnailUrl();
            String oldStatus = poetryModel.getStatus();

            if (TextUtils.equals(oldTitle, title)
                    && TextUtils.equals(oldCategory, category)
                    && TextUtils.equals(oldWriter, writer)
                    && oldFeatured == feature
                    && oldMostView == mostView
                    && TextUtils.equals(oldThumbnailUrl, thumbnailUrl)
                    && TextUtils.equals(oldStatus, status)) {

                // do nothing....
            } else {

                poetryModel.setNo(poetryNo);
                poetryModel.setServiceId(serviceId);
                poetryModel.setTitle(title);
                poetryModel.setDescription(oldDescription);
                poetryModel.setCategory(category);
                poetryModel.setWriter(writer);
                poetryModel.setFeatured(feature);
                poetryModel.setMostView(mostView);
                poetryModel.setThumbnailUrl(thumbnailUrl);
                poetryModel.setStatus(status);

                DBAdapter.updatePoetryData(poetryModel);

                updatedProducts++;

                updateThumbnail(oldThumbnailUrl, thumbnailUrl, serviceId, poetryNo);
            }

        } else {

            PoetryModel tempPoetryModel = new PoetryModel();

            tempPoetryModel.setId(0);
            tempPoetryModel.setNo(poetryNo);
            tempPoetryModel.setServiceId(serviceId);
            tempPoetryModel.setTitle(title);
            tempPoetryModel.setDescription("");  // empty
            tempPoetryModel.setThumbnailUrl(thumbnailUrl);
            tempPoetryModel.setFeatured(feature);
            tempPoetryModel.setMostView(mostView);
            tempPoetryModel.setMostDownload(0);
            tempPoetryModel.setWebsite("");  // empty
            tempPoetryModel.setUpdatedOn("");  // empty
            tempPoetryModel.setStatus(status);
            tempPoetryModel.setCategory(category);
            tempPoetryModel.setWriter(writer);
            tempPoetryModel.setPhotoUrl("");  // empty

            long rowId = DBAdapter.addPoetryData(tempPoetryModel);

            if (rowId > -1) {
                bookId = DBAdapter.getPoetryModel(poetryNo, serviceId).getId();
                newProducts++;
            } else {
                bookId = 0;
            }
        }

        return bookId;
    }

    private boolean updateMediaFile(String oldTitle, String newTitle, String extension) {

        boolean success = false;

        String oldPath = SDCardManager.getMediaPath(oldTitle, extension);

        File oldFile = new File(oldPath);

        if (oldFile.isFile()) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                oldFile.setWritable(true);
            }

            String newPath = SDCardManager.getMediaPath(newTitle, extension);
            File newFile = new File(newPath);
            success = oldFile.renameTo(newFile);
        }

        return success;
    }

    private void updateThumbnail(String oldThumbnailUrl, String thumbnailUrl, int serviceId, int productNo) {

        if (!TextUtils.equals(oldThumbnailUrl, thumbnailUrl)) {

            int languageNo = DBAdapter.getLanguageModelById(languageId).getNo();
            int serviceNo = DBAdapter.getServiceModelById(serviceId).getNumber();
            String shortName = DBAdapter.getDataTablesModelFromNo(serviceNo).getShortName();

            String mImageTitle = "l" + languageNo + "s" + serviceNo + "s" + shortName.substring(0, 2);

            String photoId = mImageTitle + String.valueOf(productNo);

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