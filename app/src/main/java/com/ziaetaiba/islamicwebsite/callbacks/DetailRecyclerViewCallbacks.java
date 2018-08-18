package com.ziaetaiba.islamicwebsite.callbacks;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ziaetaiba.islamicwebsite.datamodels.DetailItemModel;


public interface DetailRecyclerViewCallbacks {

    void onHandleDetailViewItemAction(View view, DetailItemModel detailItemBean) ;

    void onHandleShareHeaderItemAction(View view) ;

    void onHandleWebHeaderItemAction(View view) ;

    void onHandleLikeHeaderItemAction(View view, ImageView imageView) ;

    void onHandleDownloadHeaderItemAction(TextView textView, ImageView imageView) ;

    void onHandleViewHeaderItemAction(View view) ;

    void onUpdateDownloadStatus(TextView textView, ImageView imageView) ;
}
