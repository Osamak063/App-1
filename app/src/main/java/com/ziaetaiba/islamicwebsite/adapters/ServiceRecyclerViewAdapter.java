package com.ziaetaiba.islamicwebsite.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.ziaetaiba.islamicwebsite.R;
import com.ziaetaiba.islamicwebsite.datamodels.ServiceModel;
import com.ziaetaiba.islamicwebsite.callbacks.ServiceRecyclerViewCallbacks;
import com.ziaetaiba.islamicwebsite.components.Thumbnail;

import java.util.List;

public class ServiceRecyclerViewAdapter extends RecyclerView.Adapter<ServiceRecyclerViewAdapter.ViewHolder> {

    private ServiceRecyclerViewCallbacks mCallbacks;
    private final TypedValue mTypedValue = new TypedValue();
    private int mBackground;
    private Context mContext;
    private String mImageTitle;
    private List<ServiceModel> mValues;
    private int mColor;
    private String mLangShortName;
//    private Typeface arabicFont;
//    private Typeface urduFont;

    // Allows to remember the last item shown on screen
    private int lastPosition = -1;

    public ServiceRecyclerViewAdapter(Context context, ServiceRecyclerViewCallbacks callbacks, String imageTitle,
                                      List<ServiceModel> items, String langShortName, int color) {

        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        mCallbacks = callbacks;
        mBackground = mTypedValue.resourceId;
        mContext = context;
        mImageTitle = imageTitle;
        mValues = items;
        mLangShortName = langShortName;
        mColor = color;

//        this.arabicFont = Typeface.createFromAsset(mContext.getAssets(), "Trad_Arabic_Bold.ttf");
//        this.urduFont = Typeface.createFromAsset(mContext.getAssets(), "Jameel_Noori_Nastaleeq.ttf");
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public ServiceModel getValueAt(int position) {
        return mValues.get(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_service, parent, false);

        view.setBackgroundResource(mBackground);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.mBoundServiceModel = mValues.get(position);

        int serviceNo = holder.mBoundServiceModel.getNumber();
        String title = holder.mBoundServiceModel.getTitle();
        String thumbnailUrl = holder.mBoundServiceModel.getThumbnailUrl();
/*
        if (TextUtils.equals(mLangShortName, "ur")) {

            holder.mTextViewTitle.setTypeface(urduFont);
            holder.mTextViewTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

        } else if (TextUtils.equals(mLangShortName, "ar")) {

            holder.mTextViewTitle.setTypeface(arabicFont);
            holder.mTextViewTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

        } else {

            holder.mTextViewTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        }
*/

        holder.mTextViewTitle.setText(title);
//        holder.mTextViewDetail.setText(detail);

        String thumbnailNo = mImageTitle + String.valueOf(serviceNo);

        if (TextUtils.isEmpty(thumbnailUrl)) {
            holder.mProgressBar.setVisibility(View.GONE);
            holder.mImageViewIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.thumbnail));
        } else if (serviceNo == -1) {
            holder.mProgressBar.setVisibility(View.GONE);
            holder.mImageViewIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.about));
        } else {
            holder.mProgressBar.setVisibility(View.VISIBLE);
            System.out.println("checkk Thumbnail Url" + thumbnailUrl);
            Thumbnail.loadThumbnailViaPicasso(mContext, holder.mImageViewIcon, holder.mProgressBar, thumbnailNo, thumbnailUrl);
        }

        holder.mCardViewItem.setCardBackgroundColor(mContext.getResources().getColor(mColor));

        holder.mClickView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mCallbacks.onHandleCategoryItemAction(holder.mClickView, holder.mBoundServiceModel);
            }
        });
        setAnimation(holder.mView, position);
    }

    /**
     * Here is the key method to apply the animation
     */
    private void setAnimation(View viewToAnimate, int position) {

        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {

            YoYo.with(Techniques.RubberBand)
                    .duration(500)
                    .playOn(viewToAnimate);

            lastPosition = position;

            /*
            Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
            */
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ServiceModel mBoundServiceModel;
        public final View mView;
        public final View mClickView;
        public final CardView mCardViewItem;
        public final TextView mTextViewTitle;
        public final ImageView mImageViewIcon;
        public final ProgressBar mProgressBar;

        public ViewHolder(View view) {

            super(view);
            mView = view;
            mClickView = view.findViewById(R.id.viewClickItem);
            mCardViewItem = (CardView) view.findViewById(R.id.cardViewServicetItem);
            mTextViewTitle = (TextView) view.findViewById(R.id.textViewCategoryTitle);
            mImageViewIcon = (ImageView) view.findViewById(R.id.imageViewCategoryThumbnail);
            mProgressBar = (ProgressBar) view.findViewById(R.id.progressBarCategoryThumbnail);
        }

        @Override
        public String toString() {

            return super.toString() + ", " + mTextViewTitle.getText();
        }
    }
}
