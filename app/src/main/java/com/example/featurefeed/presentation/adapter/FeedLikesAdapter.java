package com.example.featurefeed.presentation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.main.R;
import com.example.featurefeed.data.source.model.local.FeedLike;
import com.example.main.core.utils.Constant;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FeedLikesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 1;
    private static final int LOADING = 2;

    private List<FeedLike> feedLikeList;
    private ClickListener clickListener;

    private boolean isLoadingAdded = false;
    private boolean retryPageLoad = false;

    private String errorMsg;

    public FeedLikesAdapter(ClickListener clickListener) {
        this.clickListener = clickListener;
        feedLikeList = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        if (viewType == LOADING) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            holder = new LoadingVH(view);
        } else if (viewType == ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.likes_layout, parent, false);
            holder = new FeedLikePaginationViewHolder(view);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == LOADING) {
            LoadingVH loadingVH = (LoadingVH) holder;

            if (retryPageLoad) {
                loadingVH.mErrorLayout.setVisibility(View.VISIBLE);
                loadingVH.mProgressBar.setVisibility(View.GONE);

                loadingVH.mErrorTxt.setText(
                        errorMsg != null ?
                                errorMsg :
                                "An unexpected error occurred");

            } else {
                loadingVH.mErrorLayout.setVisibility(View.GONE);
                loadingVH.mProgressBar.setVisibility(View.VISIBLE);
            }
        } else if (holder.getItemViewType() == ITEM) {
            FeedLikePaginationViewHolder feedLikePaginationViewHolder = (FeedLikePaginationViewHolder) holder;

            Glide.with(feedLikePaginationViewHolder.iv_pp.getContext())
                    .load(Constant.getImageAssetPath(Constant.IMAGE_TYPE_EMPLOYEE,
                            Objects.requireNonNull(feedLikeList.get(position).getMakerImagePath())))
                    .into(feedLikePaginationViewHolder.iv_pp);

            feedLikePaginationViewHolder.tv_name.setText(feedLikeList.get(position).getMakerName());
            feedLikePaginationViewHolder.tv_updated_date.setText(
                    Constant.convertDateTimeToFullDateDay(Objects.requireNonNull(feedLikeList.get(position).getUpdatedDate()))
            );
            /*
            ((FeedLikePaginationViewHolder) holder).setOnBtnEmpClickListener(
                    new IItemClickListener() {
                        @Override
                        public void onClick() {
                            clickListener.onClickEmp(
                                    feedLikeList.get(holder.getAdapterPosition()).getEmployeeId());
                        }
                    }
            );

             */
        }
    }

    @Override
    public int getItemCount() {
        return feedLikeList.size();
    }

    public interface ClickListener {
        //void onClickEmp(int empId);

        void retryPageLoad();
    }
    @Override
    public int getItemViewType(int position) {
        if (position == feedLikeList.size() - 1 && isLoadingAdded) {
            return LOADING;
        } else {
            return ITEM;
        }
    }

    class FeedLikePaginationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//        CircularImageView iv_pp;
        ImageView iv_pp;
        TextView tv_name, tv_updated_date;

        IItemClickListener onBtnEmpClickListener;

        public void setOnBtnEmpClickListener(IItemClickListener onBtnEmpClickListener) {
            this.onBtnEmpClickListener = onBtnEmpClickListener;
        }

        FeedLikePaginationViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_pp = itemView.findViewById(R.id.iv_pp);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_updated_date = itemView.findViewById(R.id.tv_updated_date);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onBtnEmpClickListener.onClick();
        }
    }

    class LoadingVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        ProgressBar mProgressBar;
        ImageButton mRetryBtn;
        TextView mErrorTxt;
        LinearLayout mErrorLayout;

        LoadingVH(View itemView) {
            super(itemView);

            mProgressBar = itemView.findViewById(R.id.loadmore_progress);
            mRetryBtn = itemView.findViewById(R.id.loadmore_retry);
            mErrorTxt = itemView.findViewById(R.id.loadmore_errortxt);
            mErrorLayout = itemView.findViewById(R.id.loadmore_errorlayout);

            mRetryBtn.setOnClickListener(this);
            mErrorLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int id = view.getId();
            if (id == R.id.loadmore_retry || id == R.id.loadmore_errorlayout) {
                showRetry(false, null);
                clickListener.retryPageLoad();
            }
        }
    }


    public List<FeedLike> getNotificationList() {
        return feedLikeList;
    }

    public void add(FeedLike feedLike) {
        feedLikeList.add(feedLike);
        notifyItemInserted(feedLikeList.size() - 1);
    }

    public void addAll(List<FeedLike> feedLikeList) {
        for (FeedLike feedLike : feedLikeList) {
            add(feedLike);
        }
    }

    public void remove(FeedLike feedLike) {
        int position = feedLikeList.indexOf(feedLike);
        if (position > -1) {
            feedLikeList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new FeedLike());
    }

    public void resetIsLoadingAdded() {
        isLoadingAdded = false;
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = feedLikeList.size() - 1;
        FeedLike feedLike = getItem(position);

        if (feedLike != null) {
            feedLikeList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public FeedLike getItem(int position) {
        return feedLikeList.get(position);
    }

    public void showRetry(boolean show, @Nullable String errorMsg) {
        retryPageLoad = show;
        notifyItemChanged(feedLikeList.size() - 1);

        if (errorMsg != null) this.errorMsg = errorMsg;
    }
}
