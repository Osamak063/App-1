package com.ziaetaiba.islamicwebsite.components;

import android.content.Context;
import android.os.Build;
import android.os.Environment;

import com.ziaetaiba.islamicwebsite.constants.Constants;

import java.io.File;
import java.util.ArrayList;

public class SDCardManager {

    public static boolean isSdCardReadable() {

        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public static boolean isSdCardWritable() {

        boolean mExternalStorageAvailable = false;
        boolean mExternalStorageWritable = false;
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {

            // We can read and write the media
            mExternalStorageAvailable = true;
            mExternalStorageWritable = true;

        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {

            // We can only read the media
            mExternalStorageAvailable = true;
            mExternalStorageWritable = false;

        } else {

            // Something else is wrong. It may be one of many other states,
            // but all we need to know is we can neither read nor write
            mExternalStorageAvailable = false;
            mExternalStorageWritable = false;
        }

        if ((mExternalStorageAvailable) && (mExternalStorageWritable)) {
            return true;
        } else {
            return false;
        }
    }

    private static String getFormattedTitle (String title) {

//        return title.replaceAll("[^a-zA-Z0-9. ]", "");
        return title.replaceAll("[^a-zA-Z0-9.-]", "_");
    }

    public static String getThumbnailPath (Context context, String mediaId) {

        File file = context.getExternalFilesDir(null);

        String path = "" ;

        if (file != null) {

            String folder = file.getAbsolutePath() + "/" + Constants.THUMBNAIL_FOLDER;

            makeDirs(folder);

            path = folder + "/" + mediaId + ".thm" ;
        }

        return path;
    }
    public static String getMediaPath(String mediaId, String extension) {

        String folder = Constants.MEDIA_DATA_PATH;

        makeDirs(folder);

        String path = folder + "/" + mediaId + "." + extension ;

        return path;
    }

    public static String getMediaStatus(String title, String extension) {
        return getStatus(Constants.MEDIA_DATA_PATH + "/" + title + "." + extension) ;
    }

    public static ArrayList<String> getDownloadsList() {

        ArrayList<String> list = new ArrayList<>();

        String path = Constants.MEDIA_DATA_PATH;

        makeDirs(path);

        File folder = new File(path);

        String[] files = folder.list();

        if (files == null || files.length <= 0) {

            list.clear();

        } else {

            for (String fileName: files) {

                if (fileName.endsWith(".jpg")) {

                    list.add(fileName);

                } else {

                    deleteDirs(new String(folder + "/" + fileName));
                }
            }
        }

        return list ;
    }

    private static String getStatus (String path) {

        String status = "" ;

        if (isSdCardWritable()) {

            File file = new File(path);

            if (file.isFile()) {
                status = Constants.PHOTO_AVAILABLE;
            } else  {
                status = Constants.PHOTO_NOT_AVAILABLE;
            }

        } else {
            status = Constants.SD_CARD_NOT_AVAILABLE;
        }

        return status ;
    }



    public static boolean updatePearlFile(String oldTitle, String newTitle, String extension) {

        boolean success = false;

        String oldPath = getMediaPath(oldTitle, extension);

        File oldFile = new File(oldPath);

        if (oldFile.isFile()) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                oldFile.setWritable(true);
            }

            String newPath = getMediaPath(newTitle, extension);
            File newFile = new File(newPath);
            success = oldFile.renameTo(newFile);
        }

        return success;
    }

    private static void makeDirs (String path) {

        File folder = new File(path);

        if (folder.isDirectory()) {
            // do nothing...
        } else {
            folder.mkdirs() ;
        }
    }

    public static boolean deleteThumbnail(Context context, String mediaId) {

        File filesDir = context.getExternalFilesDir(null);

        String path = "" ;

        if (filesDir != null) {

            String folder = filesDir.getAbsolutePath() + "/" + Constants.THUMBNAIL_FOLDER;
            path = folder + "/" + mediaId + ".thm" ;

            File file = new File(path);

            if (file.isFile()) {

                return file.delete();
            }
        }

        return false;
    }

    public static boolean deleteImage(int imageNo) {

        String mediaId = "img" + String.valueOf(imageNo);

        String path = Constants.MEDIA_DATA_PATH + "/" + mediaId + ".jpg" ;

        File file = new File(path);

        if (file.isFile()) {

            return file.delete();
        }

        return false;
    }

    public static boolean deleteDirs(String path) {

        File file = new File(path);

        if (file.isDirectory()) {

            String[] children = file.list();

            for (int i = 0; i < children.length; i++) {

                boolean success = deleteDirs(new String(file + "/" + children[i]));

                if (!success) {
                    return false;
                }
            }
        }

        // The directory is now empty so delete it
        return file.delete();
    }
}
