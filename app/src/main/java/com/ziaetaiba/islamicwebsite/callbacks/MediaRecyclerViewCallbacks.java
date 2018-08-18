package com.ziaetaiba.islamicwebsite.callbacks;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ziaetaiba.islamicwebsite.datamodels.AudioVideoModel;

public interface MediaRecyclerViewCallbacks {

   void onHandleMediaItemAction(View view, AudioVideoModel audioVideoModel) ;

   void onHandleDownloadAction(View view, AudioVideoModel audioVideoModel, ImageView imageView) ;

   void onUpdateDownloadStatus(AudioVideoModel audioVideoModel, ImageView imageView) ;
}
