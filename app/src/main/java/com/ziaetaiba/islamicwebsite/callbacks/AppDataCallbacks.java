package com.ziaetaiba.islamicwebsite.callbacks;

public interface AppDataCallbacks {

    void onPreUpdate(String msg);

    void onProgressUpdate(String msg, int percent);

    void onPostUpdate(String msg, boolean isNewUpdate, boolean isError);
}
