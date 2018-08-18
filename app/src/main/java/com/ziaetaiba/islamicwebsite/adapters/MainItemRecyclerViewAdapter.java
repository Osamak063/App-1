package com.ziaetaiba.islamicwebsite.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.ziaetaiba.islamicwebsite.R;
import com.ziaetaiba.islamicwebsite.callbacks.MainItemRecyclerViewCallbacks;
import com.ziaetaiba.islamicwebsite.datamodels.MainItemModel;

import java.util.List;

public class MainItemRecyclerViewAdapter extends RecyclerView.Adapter<MainItemRecyclerViewAdapter.ViewHolder> {

    private MainItemRecyclerViewCallbacks mCallbacks;
    private final TypedValue mTypedValue = new TypedValue();
    private int mBackground;
    private Context mContext;
    private List<MainItemModel> mValues;
    protected Typeface mCustomFont;
    // Allows to remember the last item shown on screen
    private int lastPosition = -1;

    public MainItemRecyclerViewAdapter(Context context, MainItemRecyclerViewCallbacks mCallbacks, List<MainItemModel> items) {

        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        this.mCallbacks = mCallbacks;
        mBackground = mTypedValue.resourceId;
        mContext = context;
        mValues = items;
        mCustomFont = Typeface.createFromAsset(context.getAssets(), "fonts/ntailu.ttf");
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public MainItemModel getValueAt(int position) {
        return mValues.get(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_main, parent, false);

        view.setBackgroundResource(mBackground);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.mBoundMainItemModel = mValues.get(position);

        int id = holder.mBoundMainItemModel.getId();
        String header = holder.mBoundMainItemModel.getHeader();
        String title = holder.mBoundMainItemModel.getTitle();
        int iconId = holder.mBoundMainItemModel.getIconId();
        holder.mImageViewIcon.setVisibility(View.VISIBLE);
        holder.mCardViewHeader.setCardBackgroundColor(mContext.getResources().getColor(R.color.ThemeBlue));
        holder.mCardViewBody.setCardBackgroundColor(mContext.getResources().getColor(R.color.ThemeGrey));
        if (TextUtils.isEmpty(header)) {
            holder.mCardViewHeader.setVisibility(View.GONE);
        } else {
            holder.mCardViewHeader.setVisibility(View.VISIBLE);
        }
        holder.mTextViewHeader.setTypeface(mCustomFont);
        holder.mTextViewTitle.setTypeface(mCustomFont);
        holder.mTextViewHeader.setText(header);
        holder.mTextViewTitle.setText(title);
        holder.mImageViewIcon.setImageDrawable(mContext.getResources().getDrawable(iconId));
        holder.titleLayout.setBackgroundColor(mContext.getResources().getColor(R.color.ThemeGrey));
        holder.mTextViewTitle.setTextColor(mContext.getResources().getColor(R.color.ThemeBlue));
        holder.itemArrow.setImageResource(R.drawable.arrow_head_blue);
        holder.mClickView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.titleLayout.setBackgroundColor(mContext.getResources().getColor(R.color.ThemeBlue));
                holder.mTextViewTitle.setTextColor(mContext.getResources().getColor(R.color.ThemeGrey));
                holder.itemArrow.setImageResource(R.drawable.arrow_head_grey);
                mCallbacks.onHandleMainItemAction(holder.mClickView, holder.mBoundMainItemModel);
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

        public MainItemModel mBoundMainItemModel;
        public final View mView;
        public final View mClickView;
        public final CardView mCardViewHeader;
        public final CardView mCardViewBody;
        public final TextView mTextViewHeader;
        public final TextView mTextViewTitle;
        public final ImageView mImageViewIcon;
        public final LinearLayout titleLayout;
        public final ImageView itemArrow;

        public ViewHolder(View view) {

            super(view);
            mView = view;
            mClickView = view.findViewById(R.id.viewClickItem);
            mCardViewHeader = view.findViewById(R.id.cardViewMainItemHeader);
            mCardViewBody = view.findViewById(R.id.cardViewMainItemBody);
            mTextViewHeader = view.findViewById(R.id.textViewMainItemHeader);
            mTextViewTitle = view.findViewById(R.id.textViewMainItemTitle);
            mImageViewIcon = view.findViewById(R.id.imageViewMainItemIcon);
            titleLayout = view.findViewById(R.id.title_Layout);
            itemArrow = view.findViewById(R.id.item_arrow);
        }

        @Override
        public String toString() {

            return super.toString() + ", " + mTextViewTitle.getText();
        }
    }
}
