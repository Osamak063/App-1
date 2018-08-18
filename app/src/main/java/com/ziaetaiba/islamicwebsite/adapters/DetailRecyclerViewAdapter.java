package com.ziaetaiba.islamicwebsite.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.ziaetaiba.islamicwebsite.R;
import com.ziaetaiba.islamicwebsite.callbacks.DetailRecyclerViewCallbacks;
import com.ziaetaiba.islamicwebsite.components.Thumbnail;
import com.ziaetaiba.islamicwebsite.database.DBAdapter;
import com.ziaetaiba.islamicwebsite.datamodels.DetailHeaderModel;
import com.ziaetaiba.islamicwebsite.datamodels.DetailItemModel;

import java.util.List;

public class DetailRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private DetailRecyclerViewCallbacks mCallbacks ;
    private final TypedValue mTypedValue = new TypedValue();
    private int mBackground;
    private Context mContext;
    private String mImageTitle;
    private List<Object> mValues;
    private String mLangShortName;
//    private Typeface arabicFont;
//    private Typeface urduFont;

    // Allows to remember the last item shown on screen
    private int lastPosition = -1;

    public DetailRecyclerViewAdapter(Context context, DetailRecyclerViewCallbacks callbacks,
                                     String imageTitle, List<Object> items, String langShortName) {

        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        mCallbacks = callbacks ;
        mBackground = mTypedValue.resourceId;
        mContext = context ;
        mImageTitle = imageTitle;
        mValues = items;
        mLangShortName = langShortName;

//        this.arabicFont = Typeface.createFromAsset(mContext.getAssets(), "Trad_Arabic_Bold.ttf");
//        this.urduFont = Typeface.createFromAsset(mContext.getAssets(), "Jameel_Noori_Nastaleeq.ttf");

        if (!DBAdapter.isDatabaseAvailable()) {
            DBAdapter.init(mContext);
        }
    }

    @Override
    public int getItemViewType(int position) {

        if(isPositionHeader(position))
            return TYPE_HEADER;

        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public Object getValueAt(int position) {
        return mValues.get(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType == TYPE_HEADER) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_header_detail, parent, false);
            return  new HeaderItemViewHolder(v);

        } else if(viewType == TYPE_ITEM) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_detail, parent, false);
            return new DetailItemViewHolder(v);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        if(holder instanceof HeaderItemViewHolder) {

            final HeaderItemViewHolder headerItemViewHolder = (HeaderItemViewHolder) holder ;

            updateHeaderItem(headerItemViewHolder, position);

        } else if(holder instanceof DetailItemViewHolder) {

            final DetailItemViewHolder detailItemViewHolder = (DetailItemViewHolder) holder ;

            updateDetailItem(detailItemViewHolder, position);
        }
    }

    private void updateHeaderItem (final HeaderItemViewHolder headerItemViewHolder, int position) {

        headerItemViewHolder.mBoundDetailHeaderItemModel = (DetailHeaderModel) mValues.get(position);

        int no = headerItemViewHolder.mBoundDetailHeaderItemModel.getNo();
        boolean isShare = headerItemViewHolder.mBoundDetailHeaderItemModel.isShare();
        boolean isWeb = headerItemViewHolder.mBoundDetailHeaderItemModel.isShare();
        boolean isDownload = headerItemViewHolder.mBoundDetailHeaderItemModel.isDownload();
        boolean isView = headerItemViewHolder.mBoundDetailHeaderItemModel.isView();
        String imageUrl = headerItemViewHolder.mBoundDetailHeaderItemModel.getPhotoUrl();

        if (isShare) {
            headerItemViewHolder.mLinearLayoutShare.setVisibility(View.VISIBLE);
        } else {
            headerItemViewHolder.mLinearLayoutShare.setVisibility(View.GONE);
        }

        if (isWeb) {
            headerItemViewHolder.mLinearLayoutWeb.setVisibility(View.VISIBLE);
        } else {
            headerItemViewHolder.mLinearLayoutWeb.setVisibility(View.GONE);
        }

        if (isDownload) {
            headerItemViewHolder.mLinearLayoutDownload.setVisibility(View.VISIBLE);
        } else {
            headerItemViewHolder.mLinearLayoutDownload.setVisibility(View.GONE);
        }

        if (isView) {
            headerItemViewHolder.mLinearLayoutView.setVisibility(View.VISIBLE);
        } else {
            headerItemViewHolder.mLinearLayoutView.setVisibility(View.GONE);
        }

        if (!isShare && !isWeb && !isDownload && !isView) {
            headerItemViewHolder.mCardViewIcons.setVisibility(View.GONE);
        } else {
            headerItemViewHolder.mCardViewIcons.setVisibility(View.VISIBLE);
        }

        if (!isShare && !isWeb && !isDownload) {
            headerItemViewHolder.mViewDivider.setVisibility(View.GONE);
        } else {
            headerItemViewHolder.mViewDivider.setVisibility(View.VISIBLE);
        }

        String imageNo = mImageTitle + String.valueOf(no) ;
        System.out.println("checkk product image url"+imageUrl);
        if (TextUtils.isEmpty(imageUrl)) {

            headerItemViewHolder.mProgressBar.setVisibility(View.GONE);
            headerItemViewHolder.mImageViewLarge.setImageDrawable(mContext.getResources().getDrawable(R.drawable.thumbnail));

        } else {

            headerItemViewHolder.mProgressBar.setVisibility(View.VISIBLE);
            Thumbnail.loadThumbnailViaPicasso(mContext, headerItemViewHolder.mImageViewLarge,
                    headerItemViewHolder.mProgressBar, imageNo, imageUrl);
        }

        mCallbacks.onUpdateDownloadStatus(headerItemViewHolder.mTextViewDownload, headerItemViewHolder.mImageViewDownload);

        headerItemViewHolder.mCardViewImage.setCardBackgroundColor(mContext.getResources().getColor(R.color.fab_material_light_green_500));
        headerItemViewHolder.mCardViewIcons.setCardBackgroundColor(mContext.getResources().getColor(R.color.fab_material_light_green_300));

        headerItemViewHolder.mClickViewShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallbacks.onHandleShareHeaderItemAction(view);
            }
        });

        headerItemViewHolder.mClickViewWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallbacks.onHandleWebHeaderItemAction(view);
            }
        });

        headerItemViewHolder.mClickViewDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallbacks.onHandleDownloadHeaderItemAction(headerItemViewHolder.mTextViewDownload, headerItemViewHolder.mImageViewDownload);
            }
        });

        headerItemViewHolder.mClickViewView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallbacks.onHandleViewHeaderItemAction(view);
            }
        });

        setAnimation(headerItemViewHolder.mView, position);
    }

    private void updateDetailItem (final DetailItemViewHolder detailItemViewHolder, int position) {

        detailItemViewHolder.mBoundDetailItemModel = (DetailItemModel) mValues.get(position);

        final String title = detailItemViewHolder.mBoundDetailItemModel.getTitle();
        String detail = detailItemViewHolder.mBoundDetailItemModel.getDetail();
/*
        if (TextUtils.equals(mLangShortName, "ur")) {

            detailItemViewHolder.mTextViewDetail.setTypeface(urduFont);
            detailItemViewHolder.mTextViewDetail.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

        } else if (TextUtils.equals(mLangShortName, "ar")) {

            detailItemViewHolder.mTextViewDetail.setTypeface(arabicFont);
            detailItemViewHolder.mTextViewDetail.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

        } else {

            detailItemViewHolder.mTextViewDetail.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        }
*/
        Spanned spanned = Html.fromHtml(detail);

        detailItemViewHolder.mTextViewTitle.setText(title);
        detailItemViewHolder.mTextViewDetail.setText(spanned);

        detailItemViewHolder.mViewClickItem.setVisibility(View.GONE);

        detailItemViewHolder.mCardViewBody.setCardBackgroundColor(mContext.getResources().getColor(R.color.fab_material_amber_200));

        detailItemViewHolder.mViewClickItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Context context = view.getContext();
                mCallbacks.onHandleDetailViewItemAction(view, detailItemViewHolder.mBoundDetailItemModel);
            }
        });

        setAnimation(detailItemViewHolder.mView, position);
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

    public static class HeaderItemViewHolder extends RecyclerView.ViewHolder {

        public DetailHeaderModel mBoundDetailHeaderItemModel;
        public final View mView;
        public final CardView mCardViewImage;
        public final CardView mCardViewIcons;
        public final ImageView mImageViewLarge;
        public final ProgressBar mProgressBar ;
        public final View mClickViewShare;
        public final View mClickViewWeb;
        public final View mClickViewDownload;
        public final View mClickViewView;
        public final View mViewDivider;
        public final LinearLayout mLinearLayoutShare;
        public final LinearLayout mLinearLayoutWeb;
        public final LinearLayout mLinearLayoutDownload;
        public final LinearLayout mLinearLayoutView;
        public final ImageView mImageViewShare;
        public final ImageView mImageViewWeb;
        public final ImageView mImageViewDownload;
        public final TextView mTextViewShare;
        public final TextView mTextViewWeb;
        public final TextView mTextViewDownload;

        public HeaderItemViewHolder(View view) {

            super(view);
            mView = view;
            mCardViewImage = (CardView) view.findViewById(R.id.cardViewDetailHeaderImage);
            mCardViewIcons = (CardView) view.findViewById(R.id.cardViewDetailHeaderIcons);
            mImageViewLarge = (ImageView) view.findViewById(R.id.imageViewDetailHeaderLarge);
            mProgressBar = (ProgressBar) view.findViewById(R.id.progressBarDetailHeaderLarge);
            mClickViewShare = view.findViewById(R.id.viewClickItemDetailShare);
            mClickViewWeb = view.findViewById(R.id.viewClickItemDetailWeb);
            mClickViewDownload = view.findViewById(R.id.viewClickItemDetailDownload);
            mClickViewView = view.findViewById(R.id.viewClickItemDetailView);
            mViewDivider = view.findViewById(R.id.viewDivider3);
            mLinearLayoutShare = (LinearLayout) view.findViewById(R.id.linearLayoutHeaderDetailShare);
            mLinearLayoutWeb = (LinearLayout) view.findViewById(R.id.linearLayoutHeaderDetailWeb);
            mLinearLayoutDownload = (LinearLayout) view.findViewById(R.id.linearLayoutHeaderDetailDownload);
            mLinearLayoutView = (LinearLayout) view.findViewById(R.id.linearLayoutHeaderDetailView);
            mImageViewShare = (ImageView)view.findViewById(R.id.imageViewHeaderDetailShare);
            mImageViewWeb = (ImageView)view.findViewById(R.id.imageViewHeaderDetailWeb);
            mImageViewDownload = (ImageView)view.findViewById(R.id.imageViewHeaderDetailDownload);
            mTextViewShare = (TextView) view.findViewById(R.id.textViewHeaderDetailShare);
            mTextViewWeb = (TextView) view.findViewById(R.id.textViewHeaderDetailWeb);
            mTextViewDownload = (TextView) view.findViewById(R.id.textViewHeaderDetailDownload);
        }

        @Override
        public String toString() {

            return super.toString() + ", " + mTextViewShare.getText() + ", " + mTextViewDownload.getText() ;
        }
    }

    public static class DetailItemViewHolder extends RecyclerView.ViewHolder {

        public DetailItemModel mBoundDetailItemModel;
        public final View mView;
        public final View mViewClickItem;
        public final CardView mCardViewBody;
        public final TextView mTextViewTitle;
        public final TextView mTextViewDetail;

        public DetailItemViewHolder(View view) {

            super(view);
            mView = view;
            mViewClickItem = view.findViewById(R.id.viewClickItem);
            mCardViewBody = (CardView) view.findViewById(R.id.cardViewDetailItem);
            mTextViewTitle = (TextView) view.findViewById(R.id.textViewDetailItemTitle);
            mTextViewDetail = (TextView) view.findViewById(R.id.textViewDetailItemDetail);
        }

        @Override
        public String toString() {

            return super.toString() + ", " + mTextViewTitle.getText() ;
        }
    }
}
