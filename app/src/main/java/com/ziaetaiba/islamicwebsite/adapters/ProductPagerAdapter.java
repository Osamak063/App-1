package com.ziaetaiba.islamicwebsite.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ziaetaiba.islamicwebsite.constants.Constants;
import com.ziaetaiba.islamicwebsite.fragments.FragmentProduct;

import java.util.ArrayList;

public class ProductPagerAdapter extends FragmentStatePagerAdapter {

    private int serviceId;
    private int color ;
    private String parent;
    private ArrayList<String> list;

    public ProductPagerAdapter(FragmentManager fm, int serviceId, int color, String parent, ArrayList<String> list) {

        super(fm);
        this.serviceId = serviceId;
        this.color = color;
        this.parent = parent;
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).

        FragmentProduct fragmentIntentions = new FragmentProduct();

        String title = list.get(position);

        Bundle args = new Bundle();

        args.putInt(Constants.ARG_SERVICE_ID, serviceId);
        args.putInt(Constants.ARG_STYLE_COLOR, color);
        args.putString(Constants.ARG_FRAGMENT_PARENT, parent);
        args.putString(Constants.ARG_FRAGMENT_TITLE, title);

        fragmentIntentions.setArguments(args);

        return fragmentIntentions;
    }

    @Override
    public int getCount() {
        return list.size() ;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position) ;
    }
}