package com.ziaetaiba.islamicwebsite.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ziaetaiba.islamicwebsite.R;
import com.ziaetaiba.islamicwebsite.adapters.ProductRecyclerViewAdapter;
import com.ziaetaiba.islamicwebsite.adapters.NoConnectionRecyclerViewAdapter;
import com.ziaetaiba.islamicwebsite.callbacks.ProductRecyclerViewCallbacks;
import com.ziaetaiba.islamicwebsite.callbacks.FragmentProductListCallbacks;
import com.ziaetaiba.islamicwebsite.callbacks.NoConnectionRecyclerViewCallbacks;
import com.ziaetaiba.islamicwebsite.constants.Constants;
import com.ziaetaiba.islamicwebsite.database.DBAdapter;
import com.ziaetaiba.islamicwebsite.datamodels.LanguageModel;
import com.ziaetaiba.islamicwebsite.datamodels.NoConnectionModel;
import com.ziaetaiba.islamicwebsite.datamodels.ProductItemModel;
import com.ziaetaiba.islamicwebsite.datamodels.ProductModel;
import com.ziaetaiba.islamicwebsite.datamodels.ServiceModel;

import java.util.ArrayList;

public class FragmentProduct extends Fragment
        implements ProductRecyclerViewCallbacks,
        NoConnectionRecyclerViewCallbacks {

    private static final String TAG = FragmentProduct.class.getSimpleName();

    private RecyclerView recyclerView;

    private FragmentProductListCallbacks mCallbacks ;

    private ServiceModel serviceModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.include_recycler_view, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        Bundle bundle = getArguments();

        int serviceId = bundle.getInt(Constants.ARG_SERVICE_ID);
        int color = bundle.getInt(Constants.ARG_STYLE_COLOR);
        String parent = bundle.getString(Constants.ARG_FRAGMENT_PARENT);
        String title = bundle.getString(Constants.ARG_FRAGMENT_TITLE);

        serviceModel = DBAdapter.getServiceModelById(serviceId);

        updateRecyclerView(parent, title, color);

        return rootView;
    }

    private void updateRecyclerView(String parent, String title, int color) {

        int serviceId = serviceModel.getId();
        int serviceNo = serviceModel.getNumber();
        int languageId = serviceModel.getLanguageId();

        ProductModel productModel = DBAdapter.getDataTablesModelFromServiceId(serviceNo);

        String table = productModel.getTitle() ;
        String shortName = productModel.getShortName();

        String column = shortName + "_" + parent.replace(" ", "_").toLowerCase();

        ArrayList<ProductItemModel> itemList = DBAdapter.getAllProducts(table, shortName, column, serviceId, title);

        if (itemList.isEmpty()) {

            ArrayList<NoConnectionModel> list = new ArrayList<>();

            list.add(new NoConnectionModel(1, Constants.CONTAINS_NO_DATA, "", R.drawable.ic_android_orange));

            NoConnectionRecyclerViewAdapter adapter = new NoConnectionRecyclerViewAdapter(getActivity(), this, list, color);

            LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext());

            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

            recyclerView.setLayoutManager(layoutManager);

            recyclerView.setAdapter(adapter);

        } else {

            LanguageModel languageModel = DBAdapter.getLanguageModelById(languageId);

            int languageNo = languageModel.getNo();
            String langShortName = languageModel.getShortName();

            String mImageTitle = "l" + languageNo + "s" + serviceNo + "s" + shortName.substring(0, 2) ;

            ProductRecyclerViewAdapter adapter = new ProductRecyclerViewAdapter(getActivity(), this, mImageTitle,
                    itemList, langShortName, color);

            int numColumns = getResources().getInteger(R.integer.no_columns);

            StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(numColumns, StaggeredGridLayoutManager.VERTICAL);

        //    GridLayoutManager layoutManager = new GridLayoutManager(recyclerView.getContext(), numColumns);

            recyclerView.setLayoutManager(layoutManager);

            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallbacks = (FragmentProductListCallbacks) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement FragmentProductListCallbacks.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onHandleNoConnectionItemAction(View view) {

        if (mCallbacks != null) {
            mCallbacks.onShowSnackBarAction(Constants.CONTAINS_NO_DATA);
        }
    }

    @Override
    public void onHandleProductItemModelAction(View view, ProductItemModel productItemModel) {

        if (mCallbacks != null) {
            mCallbacks.onHandleProductItemModelAction(view, serviceModel, productItemModel);
        }
    }
}
