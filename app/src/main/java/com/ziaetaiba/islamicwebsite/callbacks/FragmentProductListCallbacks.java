package com.ziaetaiba.islamicwebsite.callbacks;


import android.view.View;

import com.ziaetaiba.islamicwebsite.datamodels.ProductItemModel;
import com.ziaetaiba.islamicwebsite.datamodels.ServiceModel;

public interface FragmentProductListCallbacks {

   void onHandleProductItemModelAction(View view, ServiceModel serviceModel, ProductItemModel productItemModel) ;

   void onShowSnackBarAction(String msg) ;
}
