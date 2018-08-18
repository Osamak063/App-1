package com.ziaetaiba.islamicwebsite.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.ziaetaiba.islamicwebsite.R;
import com.ziaetaiba.islamicwebsite.callbacks.MediaRecyclerViewCallbacks;
import com.ziaetaiba.islamicwebsite.constants.Constants;
import com.ziaetaiba.islamicwebsite.datamodels.AudioVideoModel;
import com.ziaetaiba.islamicwebsite.datamodels.AudioVideoHeaderModel;

import java.util.List;

public class MediaRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private MediaRecyclerViewCallbacks mCallbacks ;
    private final TypedValue mTypedValue = new TypedValue();
    private int mBackground;
    private Context mContext;
    private List<Object> mValues;

    // Allows to remember the last item shown on screen
    private int lastPosition = -1;

    public MediaRecyclerViewAdapter(Context context, MediaRecyclerViewCallbacks callbacks, List<Object> items) {

        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        mCallbacks = callbacks ;
        mBackground = mTypedValue.resourceId;
        mContext = context ;
        mValues = items;
    }

    @Override
    public int getItemViewType(int position) {

        if (mValues.get(position) instanceof AudioVideoHeaderModel) {
            return TYPE_HEADER;
        } else if (mValues.get(position) instanceof AudioVideoModel) {
            return TYPE_ITEM;
        }
        return -1;
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

        if (viewType == TYPE_HEADER) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_header_media, parent, false);
            v.setBackgroundResource(mBackground);

            return new HeaderViewHolder(v);

        } else if (viewType == TYPE_ITEM) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_media, parent, false);
            view.setBackgroundResource(mBackground);

            return new ItemViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof HeaderViewHolder) {

            final HeaderViewHolder postViewHolder = (HeaderViewHolder) holder;

            updateHeaderView(postViewHolder, position);

        } else {

            final ItemViewHolder commentViewHolder = (ItemViewHolder) holder;

            updateItemView(commentViewHolder, position);
        }
    }

    private void updateHeaderView(final HeaderViewHolder holder, final int position) {

        holder.mBoundAudioVideoHeaderModel = (AudioVideoHeaderModel) mValues.get(position);

        String header = holder.mBoundAudioVideoHeaderModel.getHeader();

        String title = "" ;
        int color = 0 ;

        if (TextUtils.equals(header, Constants.ARG_TYPE_VIDEO)) {

            title = "Video(s)" ;
            color = R.color.fab_material_light_green_500 ;

        } else {

            title = "Audio(s)" ;
            color = R.color.fab_material_amber_500 ;
        }

        holder.mTextViewHeader.setText(title);
        holder.mCardViewHeader.setCardBackgroundColor(mContext.getResources().getColor(color));

        setAnimation(holder.mView, position);
    }

    private void updateItemView(final ItemViewHolder holder, final int position) {

        holder.mBoundAudioVideoModel = (AudioVideoModel) mValues.get(position);

        String title = holder.mBoundAudioVideoModel.getTitle();
        String type = holder.mBoundAudioVideoModel.getType();
        String url = holder.mBoundAudioVideoModel.getUrl();

        holder.mTextViewTitle.setText(title);

        int color = 0 ;

        if (TextUtils.equals(type, Constants.ARG_TYPE_VIDEO)) {
            color = R.color.fab_material_light_green_300 ;
        } else {
            color = R.color.fab_material_amber_200 ;
        }

        if (url.contains(".mp3") || url.contains(".mp4")) {
            holder.mFrameLayoutDownload.setVisibility(View.VISIBLE);
        } else {
            holder.mFrameLayoutDownload.setVisibility(View.GONE);
        }

        holder.mCardViewItem.setCardBackgroundColor(mContext.getResources().getColor(color));

        mCallbacks.onUpdateDownloadStatus(holder.mBoundAudioVideoModel, holder.mImageViewDownload);

        holder.mClickView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallbacks.onHandleMediaItemAction(v, holder.mBoundAudioVideoModel);
            }
        });

        holder.mDownloadView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallbacks.onHandleDownloadAction(v, holder.mBoundAudioVideoModel, holder.mImageViewDownload);
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

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {

        public AudioVideoHeaderModel mBoundAudioVideoHeaderModel;
        public final View mView;
        public final CardView mCardViewHeader;
        public final TextView mTextViewHeader;

        public HeaderViewHolder(View view) {

            super(view);
            mView = view;
            mCardViewHeader = (CardView) view.findViewById(R.id.cardViewMediaHeader);
            mTextViewHeader = (TextView) view.findViewById(R.id.textViewMediaItemHeader);
        }

        @Override
        public String toString() {

            return super.toString() + ", " + mTextViewHeader.getText() ;
        }
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        public AudioVideoModel mBoundAudioVideoModel;
        public final View mView;
        public final View mClickView;
        public final View mDownloadView;
        public final FrameLayout mFrameLayoutDownload ;
        public final CardView mCardViewItem;
        public final TextView mTextViewTitle;
        public final ImageView mImageViewDownload;

        public ItemViewHolder(View view) {

            super(view);
            mView = view;
            mClickView = view.findViewById(R.id.viewMediaItemClick);
            mDownloadView = view.findViewById(R.id.viewMediaDownloadClick);
            mFrameLayoutDownload = (FrameLayout) view.findViewById(R.id.frameLayoutMediaDownload);
            mCardViewItem = (CardView) view.findViewById(R.id.cardViewMediaItem);
            mTextViewTitle = (TextView) view.findViewById(R.id.textViewMediaItemTitle);
            mImageViewDownload = (ImageView) view.findViewById(R.id.imageViewMediaItem);
        }

        @Override
        public String toString() {

            return super.toString() + ", " + mTextViewTitle.getText() ;
        }
    }
}
