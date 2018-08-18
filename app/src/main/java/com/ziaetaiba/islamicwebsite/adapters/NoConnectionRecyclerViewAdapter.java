package com.ziaetaiba.islamicwebsite.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.ziaetaiba.islamicwebsite.R;
import com.ziaetaiba.islamicwebsite.callbacks.NoConnectionRecyclerViewCallbacks;
import com.ziaetaiba.islamicwebsite.datamodels.NoConnectionModel;

import java.util.List;

public class NoConnectionRecyclerViewAdapter extends RecyclerView.Adapter<NoConnectionRecyclerViewAdapter.ViewHolder>{

    private NoConnectionRecyclerViewCallbacks mCallbacks ;
    private final TypedValue mTypedValue = new TypedValue();
    private int mBackground;
    private Context mContext;
    private List<NoConnectionModel> mValues;
    private int mColor ;

    // Allows to remember the last item shown on screen
    private int lastPosition = -1;

    public NoConnectionRecyclerViewAdapter(Context context, NoConnectionRecyclerViewCallbacks callbacks,
                                           List<NoConnectionModel> items, int color) {

        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        mCallbacks = callbacks ;
        mBackground = mTypedValue.resourceId;
        mContext = context ;
        mValues = items;
        mColor = color ;
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public NoConnectionModel getValueAt(int position) {
        return mValues.get(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_no_connection, parent, false);

        view.setBackgroundResource(mBackground);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.mBoundNoConnectionModel = mValues.get(position);

        String title = holder.mBoundNoConnectionModel.getTitle();
        String detail = holder.mBoundNoConnectionModel.getDetail();
        int iconId = holder.mBoundNoConnectionModel.getIconId();

        Drawable drawable = mContext.getResources().getDrawable(iconId);

        holder.mTextViewTitle.setText(title);
        holder.mTextViewDetail.setText(detail);
        holder.mImageViewIcon.setImageDrawable(drawable);

        holder.mCardView.setCardBackgroundColor(mContext.getResources().getColor(mColor));

        holder.mClickView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallbacks.onHandleNoConnectionItemAction(holder.mView);
            }
        });
/*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            row.setOnTouchListener(new View.OnTouchListener() {
                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    v
                            .findViewById(R.id.row_content)
                            .getBackground()
                            .setHotspot(event.getX(), event.getY());

                    return(false);
                }
            });
        }
*/
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

        public NoConnectionModel mBoundNoConnectionModel;
        public final View mView;
        public final View mClickView;
        public final CardView mCardView ;
        public final TextView mTextViewTitle;
        public final TextView mTextViewDetail;
        public final ImageView mImageViewIcon ;

        public ViewHolder(View view) {

            super(view);
            mView = view;
            mClickView = view.findViewById(R.id.viewClickItem);
            mCardView = (CardView) view.findViewById(R.id.cardViewNoConnectionItem);
            mTextViewTitle = (TextView) view.findViewById(R.id.textViewNoConnectionItemTitle);
            mTextViewDetail = (TextView) view.findViewById(R.id.textViewNoConnectionItemDetail);
            mImageViewIcon = (ImageView) view.findViewById(R.id.imageViewNoConnectionItemIcon);
        }

        @Override
        public String toString() {

            return super.toString() + ", " + mTextViewTitle.getText() ;
        }
    }
}
