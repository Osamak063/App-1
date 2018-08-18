package com.ziaetaiba.islamicwebsite.components;

import android.app.DownloadManager;
import android.app.DownloadManager.Query;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;

import com.ziaetaiba.islamicwebsite.constants.Constants;

import java.io.File;

public class MediaDownloadManager {

    private static DownloadManager downloadManager;

    public static void init (Context context) {

        downloadManager = (DownloadManager) context.getSystemService(context.DOWNLOAD_SERVICE);
    }

    public static long startDownload(String title ,String url, String path) {

        long downloadReference = -1 ;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {

            Uri source = Uri.parse(url);
            Uri destination = Uri.fromFile(new File(path));

            DownloadManager.Request request = new DownloadManager.Request(source);

            //Restrict the types of networks over which this download may proceed.
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);

            //Set whether this download may proceed over a roaming connection.
            request.setAllowedOverRoaming(false);

            request.setVisibleInDownloadsUi(true);

            //Set the title of this download, to be displayed in notifications (if enabled).
            request.setTitle(title);

            //Set a description of this download, to be displayed in notifications (if enabled)
            //       request.setDescription("Android Data download using DownloadManager.");

            //Set the local destination for the downloaded file to a path within the application's external files directory
//        request.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS,"CountryList.json");
            request.setDestinationUri(destination);

            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

            //Enqueue a new download and same the referenceId
            downloadReference = downloadManager.enqueue(request);

            //     sharedPreferences.edit().putLong(title, downloadReference).commit();
        }

        return downloadReference;
    }

    public static void displayAllDownloads () {
/*
        Intent intent = new Intent();
        intent.setAction(DownloadManager.ACTION_VIEW_DOWNLOADS);
        context.startActivity(intent);
*/
    }

    public static String checkStatus (long downloadReference) {

        String status = Constants.STATUS_EMPTY ;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {

            Query myDownloadQuery = new Query();
            //set the query filter to our previously Enqueued download
            myDownloadQuery.setFilterById(downloadReference);

            //Query the download manager about downloads that have been requested.
            Cursor cursor = downloadManager.query(myDownloadQuery);

            if (cursor != null && cursor.moveToFirst()) {
                status = checkStatus(cursor);
            }

            if (cursor != null) {
                cursor.close();
            }
        }

        return status ;
    }

    public static void cancelDownload (long downloadReference) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {

            downloadManager.remove(downloadReference);
            //      sharedPreferences.edit().remove(title).commit();
        }
    }

    private static String checkStatus(Cursor cursor){

        //column for status
        int columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
        int status = cursor.getInt(columnIndex);
        //column for reason code if the download failed or paused
        int columnReason = cursor.getColumnIndex(DownloadManager.COLUMN_REASON);
        int reason = cursor.getInt(columnReason);
        //get the download filename
        int filenameIndex = cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME);
        String filename = cursor.getString(filenameIndex);

        String statusText = "";
        String reasonText = "";

        switch(status){
            case DownloadManager.STATUS_FAILED:
                statusText = Constants.STATUS_FAILED;
                switch(reason){
                    case DownloadManager.ERROR_CANNOT_RESUME:
                        reasonText = "ERROR_CANNOT_RESUME";
                        break;
                    case DownloadManager.ERROR_DEVICE_NOT_FOUND:
                        reasonText = "ERROR_DEVICE_NOT_FOUND";
                        break;
                    case DownloadManager.ERROR_FILE_ALREADY_EXISTS:
                        reasonText = "ERROR_FILE_ALREADY_EXISTS";
                        break;
                    case DownloadManager.ERROR_FILE_ERROR:
                        reasonText = "ERROR_FILE_ERROR";
                        break;
                    case DownloadManager.ERROR_HTTP_DATA_ERROR:
                        reasonText = "ERROR_HTTP_DATA_ERROR";
                        break;
                    case DownloadManager.ERROR_INSUFFICIENT_SPACE:
                        reasonText = "ERROR_INSUFFICIENT_SPACE";
                        break;
                    case DownloadManager.ERROR_TOO_MANY_REDIRECTS:
                        reasonText = "ERROR_TOO_MANY_REDIRECTS";
                        break;
                    case DownloadManager.ERROR_UNHANDLED_HTTP_CODE:
                        reasonText = "ERROR_UNHANDLED_HTTP_CODE";
                        break;
                    case DownloadManager.ERROR_UNKNOWN:
                        reasonText = "ERROR_UNKNOWN";
                        break;
                }
                break;
            case DownloadManager.STATUS_PAUSED:
                statusText = Constants.STATUS_PAUSED;
                switch(reason){
                    case DownloadManager.PAUSED_QUEUED_FOR_WIFI:
                        reasonText = "PAUSED_QUEUED_FOR_WIFI";
                        break;
                    case DownloadManager.PAUSED_UNKNOWN:
                        reasonText = "PAUSED_UNKNOWN";
                        break;
                    case DownloadManager.PAUSED_WAITING_FOR_NETWORK:
                        reasonText = "PAUSED_WAITING_FOR_NETWORK";
                        break;
                    case DownloadManager.PAUSED_WAITING_TO_RETRY:
                        reasonText = "PAUSED_WAITING_TO_RETRY";
                        break;
                }
                break;
            case DownloadManager.STATUS_PENDING:
                statusText = Constants.STATUS_PENDING;
                break;
            case DownloadManager.STATUS_RUNNING:
                statusText = Constants.STATUS_RUNNING;
                break;
            case DownloadManager.STATUS_SUCCESSFUL:
                statusText = Constants.STATUS_SUCCESSFUL;
                reasonText = "Filename:\n" + filename;
                break;
        }
/*
        Toast toast = Toast.makeText(context, statusText + "\n" + reasonText, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP, 25, 400);
        toast.show();
*/
        return statusText ;
    }
/*
    private static BroadcastReceiver downloadReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            //check if the broadcast message is for our Enqueued download
            long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);

        //    if (downloadReference == referenceId){


        //    }
        }
    };
    */
}