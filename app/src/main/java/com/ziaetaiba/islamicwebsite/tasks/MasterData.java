package com.ziaetaiba.islamicwebsite.tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.ziaetaiba.islamicwebsite.constants.Constants;
import com.ziaetaiba.islamicwebsite.constants.ZiaeTaibaData;
import com.ziaetaiba.islamicwebsite.datamodels.BookModel;
import com.ziaetaiba.islamicwebsite.datamodels.MasterModel;
import com.ziaetaiba.islamicwebsite.datamodels.ServiceModel;
import com.ziaetaiba.islamicwebsite.datamodels.LanguageModel;
import com.ziaetaiba.islamicwebsite.callbacks.AppDataCallbacks;
import com.ziaetaiba.islamicwebsite.components.SDCardManager;
import com.ziaetaiba.islamicwebsite.database.DBAdapter;
import com.ziaetaiba.islamicwebsite.datamodels.SubMasterModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class MasterData extends AsyncTask<Void, Integer, Void> {

    private static final boolean DEBUG = false;

    private static final String ROOT_APP_NAME = "ziaetaiba";

    private static final String STR_ROOT_APP = "app";
    private static final String STR_ROOT_LANGUAGES = "languages";
    private static final String STR_ROOT_SERVICES = "services";
    private static final String STR_ROOT_MASTER = "master";

    private static final String TAG_LANGUAGE_ID = "id";
    private static final String TAG_LANGUAGE_TITLE = "title";
    private static final String TAG_LANGUAGE_SHORT_NAME = "shortName";

    private static final String TAG_SERVICE_ID = "id";
    private static final String TAG_SERVICE_TITLE = "name";
    private static final String TAG_SERVICE_LANGUAGE_ID = "languageId";
    private static final String TAG_SERVICE_THUMBNAIL_URL = "thumbnailUrl";

    private static final String TAG_MASTER_SERVICE_ID = "serviceId";
    private static final String TAG_MASTER_ID = "id";
    private static final String TAG_MASTER_TITLE = "title";
    private static final String TAG_MASTER_TYPE_ID = "type_id";
    private static final String TAG_MASTER_LANGUAGE_ID = "languageId";
    private static final String TAG_MASTER_THUMBNAIL_URL = "thumbnailUrl";
    private static final String TAG_MASTER_SUB_CATEGORIES = "subCategories";

    private static final String TAG_SUB_MASTER_ID = "id";
    private static final String TAG_SUB_MASTER_TITLE = "title";

    private Activity mActivity;
    private AppDataCallbacks mCallbacks;
    private JSONObject jsonObject;

    private boolean errorFlag;

    private int newLanguages, updatedLanguages, newServices, updatedServices, newMasters, updatedMasters,
            newSubMasters, updatedSubMasters;

    public MasterData(Activity mActivity, AppDataCallbacks mCallbacks, JSONObject jsonObject) {

        this.mActivity = mActivity;
        this.mCallbacks = mCallbacks;
        this.jsonObject = jsonObject;

        errorFlag = false;
    }

    @Override
    protected Void doInBackground(Void... arg0) {

        if (!isCancelled()) {

            if (jsonObject == null || jsonObject.length() < 4) {

                publishProgress(-1);

            } else {

                if (!isCancelled()) {

                    try {

                        if (DEBUG) {
                            Log.d("MasterData", "JsonArray > Array Size: " + jsonObject.length());
                        }

                        String appString = jsonObject.getString(STR_ROOT_APP);
                        JSONArray languageArray = jsonObject.getJSONArray(STR_ROOT_LANGUAGES);
                        JSONArray serviceArray = jsonObject.getJSONArray(STR_ROOT_SERVICES);
                        JSONArray masterArray = jsonObject.getJSONArray(STR_ROOT_MASTER);

                        if (appString == null || !appString.equals(ROOT_APP_NAME) ||
                                languageArray == null || languageArray.length() < 1 ||
                                serviceArray == null || serviceArray.length() < 1 ||
                                masterArray == null || masterArray.length() < 1) {

                            // do nothing...
                        } else {

                            for (int i = 0; i < languageArray.length(); i++) {

                                JSONObject languageObject = languageArray.getJSONObject(i);

                                if (DEBUG) {
                                    int no = i + 1;
                                    Log.d("MasterData", "JsonObject > Language # " + no + ": "
                                            + languageObject.length() + ", " + languageObject.toString());
                                }

                                if (isCancelled()) {

                                    break;

                                } else if (languageObject == null || languageObject.length() < 3) {
                                    // do nothing...
                                } else {

                                    String mLanguageId = languageObject.getString(TAG_LANGUAGE_ID);
                                    String mLanguageTitle = languageObject.getString(TAG_LANGUAGE_TITLE);
                                    String mLanguageShortName = languageObject.getString(TAG_LANGUAGE_SHORT_NAME);

                                    if (mLanguageId == null || mLanguageId.equals("") ||
                                            mLanguageTitle == null || mLanguageTitle.equals("") ||
                                            mLanguageShortName == null || mLanguageShortName.equals("")) {

                                        // do nothing...
                                    } else {

                                        int languageId = addLanguageData(mLanguageId, mLanguageTitle, mLanguageShortName);
                                    }
                                }
                            }

                            for (int i = 0; i < serviceArray.length(); i++) {

                                JSONObject serviceObject = serviceArray.getJSONObject(i);

                                if (DEBUG) {
                                    int no = i + 1;
                                    Log.d("MasterData", "JsonObject > Service # " + no + ": "
                                            + serviceObject.length() + ", " + serviceObject.toString());
                                }

                                if (isCancelled()) {

                                    break;

                                } else if (serviceObject == null || serviceObject.length() < 4) {
                                    // do nothing...
                                } else {

                                    String mServiceId = serviceObject.getString(TAG_SERVICE_ID);
                                    String mLanguageId = serviceObject.getString(TAG_SERVICE_LANGUAGE_ID);
                                    String mServiceTitle = serviceObject.getString(TAG_SERVICE_TITLE);
                                    String mThumbnailUrl = serviceObject.getString(TAG_SERVICE_THUMBNAIL_URL);
                                    String mStatus = "1";

                                    if (mServiceId == null || mServiceId.equals("") ||
                                            mLanguageId == null || mLanguageId.equals("") ||
                                            mServiceTitle == null || mServiceTitle.equals("") ||
                                            mThumbnailUrl == null || mThumbnailUrl.equals("-")) {

                                        // do nothing...
                                    } else {
                                        System.out.println("checkk Thumbnail Url from server" + mThumbnailUrl);
                                        String decodedThumbnailUrl = decodeUrl(mThumbnailUrl);
                                        System.out.println("checkk Thumbnail Url from server decoded" + decodedThumbnailUrl);
                                        int categoryId = addServiceData(mServiceId, mLanguageId, mServiceTitle, decodedThumbnailUrl, mStatus);

                                    }
                                }
                            }

                            for (int i = 0; i < masterArray.length(); i++) {

                                JSONObject masterObjectTop = masterArray.getJSONObject(i);

                                if (DEBUG) {
                                    int no = i + 1;
                                    Log.d("MasterData", "JsonObject > Master # " + no + ": "
                                            + masterObjectTop.length() + ", " + masterObjectTop.toString());
                                }

                                if (isCancelled()) {

                                    break;

                                } else if (masterObjectTop == null || masterObjectTop.length() < 1) {
                                    // do nothing...
                                } else {

                                    String parent = masterObjectTop.names().getString(0);

                                    JSONArray jsonArray = masterObjectTop.getJSONArray(parent);

                                    for (int j = 0; j < jsonArray.length(); j++) {

                                        JSONObject masterObject = jsonArray.getJSONObject(j);

                                        if (isCancelled()) {

                                            break;

                                        } else if (masterObject == null || masterObject.length() < 4) {
                                            // do nothing...
                                        } else {

                                            String mMasterId = masterObject.getString(TAG_MASTER_ID);
                                            String mMasterTitle = masterObject.getString(TAG_MASTER_TITLE);
                                            String mTypeId = masterObject.getString(TAG_MASTER_TYPE_ID);
                                            String mLanguageId = masterObject.getString(TAG_MASTER_LANGUAGE_ID);
                                            JSONArray mSubCategories = null;

                                            String mThumbnailUrl = "";

                                            try {
                                                mThumbnailUrl = masterObject.getString(TAG_MASTER_THUMBNAIL_URL);
                                            } catch (JSONException ex) {
                                                mThumbnailUrl = "-";
                                            }

                                            try {
                                                mSubCategories = masterObject.getJSONArray(TAG_MASTER_SUB_CATEGORIES);
                                            } catch (JSONException ex) {
                                                mSubCategories = new JSONArray();
                                            }

                                            String mStatus = "1";

                                            if (parent == null || parent.equals("") ||
                                                    mMasterId == null || mMasterId.equals("") ||
                                                    mMasterTitle == null || mMasterTitle.equals("") ||
                                                    mTypeId == null || mTypeId.equals("") ||
                                                    mLanguageId == null || mLanguageId.equals("") ||
                                                    mThumbnailUrl == null || mThumbnailUrl.equals("") ||
//                                                mSubCategories == null || mSubCategories.length() < 1 ||  // Master may not contain any sub-category
                                                    mStatus == null || mStatus.equals("")) {

                                                // do nothing...
                                            } else {

                                    /*
                                    switch (i) {

                                        case 3: int categoryId = addCategoryData(mMasterId, mMasterTitle, mTypeId); break ;
                                        case 5: int authorId = addAuthorData(mMasterId, mMasterTitle, mTypeId); break ;
                                        case 7: int publisherId = addDataTablesData(mMasterId, mMasterTitle, mTypeId); break ;
                                    }
                                    */

                                                String decodedThumbnailUrl = decodeUrl(mThumbnailUrl);

                                                int masterId = addMasterData(mTypeId, mLanguageId, mMasterId, parent,
                                                        mMasterTitle, decodedThumbnailUrl, mStatus);


                                                for (int k = 0; k < mSubCategories.length(); k++) {

                                                    JSONObject subMasterObject = mSubCategories.getJSONObject(k);

                                                    if (isCancelled()) {

                                                        break;

                                                    } else if (subMasterObject == null || subMasterObject.length() < 2) {
                                                        // do nothing...
                                                    } else {

                                                        String mSubMasterId = subMasterObject.getString(TAG_SUB_MASTER_ID);
                                                        String mSubMasterTitle = subMasterObject.getString(TAG_SUB_MASTER_TITLE);

                                                        if (mSubMasterId == null || mSubMasterId.equals("") ||
                                                                mSubMasterTitle == null || mSubMasterTitle.equals("")) {

                                                            // do nothing...
                                                        } else {

                                                            int subMasterId = addSubMasterData(mSubMasterId, mMasterId,
                                                                    mTypeId, mLanguageId, parent, mSubMasterTitle);
                                                        }
                                                    }
                                                }
                                            }
                                        }
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

        newLanguages = 0;
        updatedLanguages = 0;
        newServices = 0;
        updatedServices = 0;
        newMasters = 0;
        updatedMasters = 0;
        newSubMasters = 0;
        updatedSubMasters = 0;

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

                    String msg = "Languages: " + newLanguages + " : " + updatedLanguages
                            + ", Services: " + newServices + " : " + updatedServices
                            + ", Masters: " + newMasters + " : " + updatedMasters
                            + ", SubMasters: " + newSubMasters + " : " + updatedSubMasters;

                    Log.d("MasterData", msg);
                }

                if (newLanguages > 0 || updatedLanguages > 0 ||
                        newMasters > 0 || updatedMasters > 0 ||
                        newServices > 0 || updatedServices > 0 ||
                        newSubMasters > 0 || updatedSubMasters > 0) {

                    mCallbacks.onPostUpdate("Updated Successfully.", true, false);
                } else {
                    mCallbacks.onPostUpdate("No updates at this time.", false, false);
                }
            }
        }
    }

    private int addLanguageData(String strLanguageId, String title, String shortName) {

        int languageNo = Integer.parseInt(strLanguageId);

        LanguageModel languageModel = DBAdapter.getLanguageModelByNo(languageNo);

        int languageId = languageModel.getId();

        if (languageId > 0) {

            int oldCategoryNumber = languageModel.getNo();
            String oldTitle = languageModel.getTitle();
            String oldShortName = languageModel.getShortName();

            if (oldCategoryNumber == languageNo
                    && oldTitle.equals(title)
                    && oldShortName.equals(shortName)) {

                // do nothing....
            } else {

                languageModel.setNo(languageNo);
                languageModel.setTitle(title);
                languageModel.setShortName(shortName);

                DBAdapter.updateLanguage(languageModel);

                updatedLanguages++;
            }

        } else {

            LanguageModel tempLanguageModel = new LanguageModel();

            tempLanguageModel.setId(0);
            tempLanguageModel.setNo(languageNo);
            tempLanguageModel.setTitle(title);
            tempLanguageModel.setShortName(shortName);

            long rowId = DBAdapter.addLanguageData(tempLanguageModel);

            if (rowId > -1) {
                languageId = DBAdapter.getLanguageModelByNo(languageNo).getId();
                newLanguages++;
            } else {
                languageId = 0;
            }
        }

        return languageId;
    }

    private int addServiceData(String strServiceNumber, String strLanguageNo, String title, String thumbnailUrl, String strStatus) {

        int serviceNo = Integer.parseInt(strServiceNumber);
        int languageNo = Integer.parseInt(strLanguageNo);

        int intStatus = Integer.parseInt(strStatus);

        int languageId = DBAdapter.getLanguageModelByNo(languageNo).getId();

        String status = "";

        if (intStatus == 1) {
            status = Constants.STATUS_VISIBLE;
        } else {
            status = Constants.STATUS_GONE;
        }

        ServiceModel serviceModel = DBAdapter.getServiceModel(serviceNo, languageId);

        int serviceId = serviceModel.getId();

        if (serviceId > 0) {

            int oldServiceNumber = serviceModel.getNumber();
            int oldLanguageId = serviceModel.getLanguageId();
            String oldTitle = serviceModel.getTitle();
            String oldThumbnailUrl = serviceModel.getThumbnailUrl();
            String oldStatus = serviceModel.getStatus();

            if (oldServiceNumber == serviceNo
                    && oldLanguageId == languageId
                    && oldTitle.equals(title)
                    && oldThumbnailUrl.equals(thumbnailUrl)
                    && oldStatus.equals(status)) {

                // do nothing....
            } else {

                serviceModel.setNumber(serviceNo);
                serviceModel.setLanguageId(languageId);
                serviceModel.setTitle(title);
                serviceModel.setThumbnailUrl(thumbnailUrl);
                serviceModel.setStatus(status);

                DBAdapter.updateService(serviceModel);

                updatedServices++;

                if (!TextUtils.equals(oldThumbnailUrl, thumbnailUrl)) {

                    String imageTitle = "l" + String.valueOf(languageNo) + "s";
                    String thumbnailNo = imageTitle + String.valueOf(serviceNo);

                    SDCardManager.deleteThumbnail(mActivity, thumbnailNo);
                }
            }

        } else {

            ServiceModel tempServiceModel = new ServiceModel();

            tempServiceModel.setId(0);
            tempServiceModel.setNumber(serviceNo);
            tempServiceModel.setLanguageId(languageId);
            tempServiceModel.setTitle(title);
            tempServiceModel.setThumbnailUrl(thumbnailUrl);
            tempServiceModel.setStatus(status);

            long rowId = DBAdapter.addServiceData(tempServiceModel);

            if (rowId > -1) {
                serviceId = DBAdapter.getServiceModel(serviceNo, languageId).getId();
                newServices++;
            } else {
                serviceId = 0;
            }
        }

        return serviceId;
    }

    private int addMasterData(String strServiceNumber, String strLanguageNumber, String strMasterNo, String parent,
                              String title, String thumbnailUrl, String strStatus) {

        int serviceNo = Integer.parseInt(strServiceNumber);
        int languageNo = Integer.parseInt(strLanguageNumber);
        int masterNo = Integer.parseInt(strMasterNo);
        int intStatus = Integer.parseInt(strStatus);

        int languageId = DBAdapter.getLanguageModelByNo(languageNo).getId();

        int serviceId = DBAdapter.getServiceModel(serviceNo, languageId).getId();

        String status = "";

        if (intStatus == 1) {
            status = Constants.STATUS_VISIBLE;
        } else {
            status = Constants.STATUS_GONE;
        }

        MasterModel masterModel = DBAdapter.getMasterModel(masterNo, serviceId);

        int masterId = masterModel.getId();

        if (masterId > 0) {

            int oldServiceId = masterModel.getServiceId();
            String oldParent = masterModel.getParent();
            String oldTitle = masterModel.getTitle();
            String oldThumbnailUrl = masterModel.getThumbnailUrl();
            String oldStatus = masterModel.getStatus();

            if (oldServiceId == serviceId
                    && oldParent.equals(parent)
                    && oldTitle.equals(title)
                    && oldThumbnailUrl.equals(thumbnailUrl)
                    && oldStatus.equals(status)) {

                // do nothing....
            } else {

                //        if (oldServiceId != serviceId) { Log.d("MasterData", "serviceId - " + oldServiceId + " : " + serviceId); }
                //        if (!oldParent.equals(parent)) { Log.d("MasterData", "parent - " + oldParent + " : " + parent); }
                //        if (!oldTitle.equals(title)) { Log.d("MasterData", "title - " + oldTitle + " : " + title); }
                //        if (!oldThumbnailUrl.equals(thumbnailUrl)) { Log.d("MasterData", "thumbnailUrl - " + oldThumbnailUrl + " : " + thumbnailUrl); }
                //        if (!oldStatus.equals(status)) { Log.d("MasterData", "status - " + oldStatus + " : " + status); }

                masterModel.setNumber(masterNo);
                masterModel.setServiceId(serviceId);
                masterModel.setParent(parent);
                masterModel.setTitle(title);
                masterModel.setThumbnailUrl(thumbnailUrl);
                masterModel.setStatus(status);

                DBAdapter.updateMaster(masterModel);

                updatedMasters++;

                //            if (!TextUtils.equals(oldThumbnailUrl, thumbnailUrl)) {

                //                String imageTitle = "l" + String.valueOf(languageNo) + "s" ;
                //                String thumbnailNo = imageTitle + String.valueOf(serviceNo);

                //                SDCardManager.deleteThumbnail(mActivity, thumbnailNo);
                //            }
            }

        } else {

            MasterModel tempMasterModel = new MasterModel();

            tempMasterModel.setId(0);
            tempMasterModel.setNumber(masterNo);
            tempMasterModel.setServiceId(serviceId);
            tempMasterModel.setParent(parent);
            tempMasterModel.setTitle(title);
            tempMasterModel.setThumbnailUrl(thumbnailUrl);
            tempMasterModel.setStatus(status);

            long rowId = DBAdapter.addMasterData(tempMasterModel);

            if (rowId > -1) {
                masterId = DBAdapter.getMasterModel(masterNo, languageId).getId();
                newMasters++;
            } else {
                masterId = 0;
            }
        }

        return masterId;
    }

    private int addSubMasterData(String strSubMasterNumber, String strMasterNumber, String strServiceNumber,
                                 String strLanguageNumber, String parent, String title) {

        int serviceNo = Integer.parseInt(strServiceNumber);
        int languageNo = Integer.parseInt(strLanguageNumber);
        int masterNo = Integer.parseInt(strMasterNumber);
        int subMasterNo = Integer.parseInt(strSubMasterNumber);

        int languageId = DBAdapter.getLanguageModelByNo(languageNo).getId();

        int serviceId = DBAdapter.getServiceModel(serviceNo, languageId).getId();

        int masterId = DBAdapter.getMasterModel(masterNo, languageId).getId();

        SubMasterModel subMasterModel = DBAdapter.getSubMasterModel(subMasterNo, serviceId);

        int subMasterId = subMasterModel.getId();

//        Log.d("Master Data", "master: " + serviceNo + " - " + serviceId + " - " + masterId + " - " + subMasterNo + " - " + subMasterId) ;

        if (subMasterId > 0) {

            int oldMasterId = subMasterModel.getMasterId();
            int oldServiceId = subMasterModel.getServiceId();
            String oldParent = subMasterModel.getParent();
            String oldTitle = subMasterModel.getTitle();

            if (oldMasterId == masterId
                    && oldServiceId == serviceId
                    && oldParent.equals(parent)
                    && oldTitle.equals(title)) {

                // do nothing....
            } else {

                //    if (oldMasterId != masterId) { Log.d("SubMasterData", "masterId - " + oldMasterId + " : " + masterId); }
                //    if (oldServiceId != serviceId) { Log.d("SubMasterData", "serviceId - " + oldServiceId + " : " + serviceId); }
                //    if (!oldParent.equals(parent)) { Log.d("SubMasterData", "parent - " + oldParent + " : " + parent); }
                //    if (!oldTitle.equals(title)) { Log.d("SubMasterData", "title - " + oldTitle + " : " + title); }

                subMasterModel.setNumber(subMasterNo);
                subMasterModel.setMasterId(masterId);
                subMasterModel.setServiceId(serviceId);
                subMasterModel.setParent(parent);
                subMasterModel.setTitle(title);

                DBAdapter.updateSubMaster(subMasterModel);

                updatedSubMasters++;
            }

        } else {

            SubMasterModel tempSubMasterModel = new SubMasterModel();

            tempSubMasterModel.setId(0);
            tempSubMasterModel.setNumber(subMasterNo);
            tempSubMasterModel.setMasterId(masterId);
            tempSubMasterModel.setServiceId(serviceId);
            tempSubMasterModel.setParent(parent);
            tempSubMasterModel.setTitle(title);

            long rowId = DBAdapter.addSubMasterData(tempSubMasterModel);

            if (rowId > -1) {
                subMasterId = DBAdapter.getSubMasterModel(subMasterNo, serviceId).getId();
                newSubMasters++;
            } else {
                subMasterId = 0;
            }
        }

        return subMasterId;
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