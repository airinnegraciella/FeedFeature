package com.example.feedfeature.feature.feed.adapter;

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
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.example.feedfeature.R;
import com.example.featurelike.data.source.local.FeedComment;
import com.example.feedfeature.utils.Constant;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FeedCommentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final int ITEM = 1;
    private static final int LOADING = 2;

    private List<FeedComment> feedCommentList;
    private ClickListener clickListener;

    private boolean isLoadingAdded = false;
    private boolean retryPageLoad = false;

    private String errorMsg;


    public FeedCommentsAdapter(ClickListener clickListener) {
        this.clickListener = clickListener;
        feedCommentList = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        if (viewType == LOADING) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            holder = new LoadingVH(view);
        } else if (viewType == ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comments_layout, parent, false);
            holder = new FeedCommentPaginationViewHolder(view);
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
            FeedCommentPaginationViewHolder feedCommentPaginationViewHolder = (FeedCommentPaginationViewHolder) holder;

            Glide.with(feedCommentPaginationViewHolder.iv_pp.getContext())
                    .load(Constant.getImageAssetPath(Constant.IMAGE_TYPE_EMPLOYEE,
                            Objects.requireNonNull(feedCommentList.get(position).getMakerImagePath())))
                    .into(feedCommentPaginationViewHolder.iv_pp);

            CircularProgressDrawable circularProgressDrawable =
                    new CircularProgressDrawable(feedCommentPaginationViewHolder.iv_image_comment.getContext());
            circularProgressDrawable.setStrokeWidth(5f);
            circularProgressDrawable.setCenterRadius(30f);
            circularProgressDrawable.start();

            if (Objects.requireNonNull(feedCommentList.get(position).getCommentImagePath()).equalsIgnoreCase("")) {
                feedCommentPaginationViewHolder.iv_image_comment.setVisibility(View.GONE);
            } else {
                feedCommentPaginationViewHolder.iv_image_comment.setVisibility(View.VISIBLE);
                Glide.with(feedCommentPaginationViewHolder.iv_image_comment.getContext())
                        .load(Constant.getImageAssetPath(Constant.IMAGE_TYPE_FEED,
                                Objects.requireNonNull(feedCommentList.get(position).getCommentImagePath())))
                        .placeholder(circularProgressDrawable)
                        .into(feedCommentPaginationViewHolder.iv_image_comment);
            }

            feedCommentPaginationViewHolder.tv_name.setText(feedCommentList.get(position).getMakerName());
            feedCommentPaginationViewHolder.tv_updated_date.setText(
                    Constant.convertDateTimeToFullDateDay(Objects.requireNonNull(feedCommentList.get(position).getUpdatedDate()))
            );

            feedCommentPaginationViewHolder.tv_comment.setText(feedCommentList.get(position).getComment());

            /*
            ((FeedCommentPaginationViewHolder) holder).setOnBtnEmpClickListener(
                    new IItemClickListener() {
                        @Override
                        public void onClick() {
                            clickListener.onClickEmp(
                                    feedCommentList.get(holder.getAdapterPosition()).getMakerId());
                        }
                    }
            );

            ((FeedCommentPaginationViewHolder) holder).setOnBtnImagePostClickListener(
                    new IItemClickListener() {
                        @Override
                        public void onClick() {
                            clickListener.onClickEmp(
                                    feedCommentList.get(holder.getAdapterPosition()).getMakerId());
                        }
                    }
            );

            ((FeedCommentPaginationViewHolder) holder).setOnLayoutCommentClickListener(
                    new IItemClickListener() {
                        @Override
                        public void onClick() {
                            if (feedCommentList.get(holder.getAdapterPosition()).getIsMine() == 1) {
                                clickListener.onClickLayoutComment(
                                        feedCommentList.get(holder.getAdapterPosition()).getFeedCommentId(),
                                        feedCommentList.get(holder.getAdapterPosition()).getComment(),
                                        feedCommentList.get(holder.getAdapterPosition()).getCommentImagePath(),
                                        holder.getAdapterPosition()
                                );
                            }
                        }
                    }
            );

             */

        }
    }

    @Override
    public int getItemCount() {
        return feedCommentList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == feedCommentList.size() - 1 && isLoadingAdded) {
            return LOADING;
        } else {
            return ITEM;
        }
    }

    class FeedCommentPaginationViewHolder extends RecyclerView.ViewHolder {
//        CircularImageView iv_ic_profile;
        ImageView iv_pp, iv_image_comment;
        TextView tv_name, tv_updated_date, tv_comment;

        LinearLayout layout_comment;

        IItemClickListener onBtnEmpClickListener;
        IItemClickListener onBtnImagePostClickListener;

        IItemClickListener onLayoutCommentClickListener;

        public void setOnBtnEmpClickListener(IItemClickListener onBtnEmpClickListener) {
            this.onBtnEmpClickListener = onBtnEmpClickListener;
        }

        public void setOnBtnImagePostClickListener(IItemClickListener onBtnImagePostClickListener) {
            this.onBtnImagePostClickListener = onBtnImagePostClickListener;
        }

        public void setOnLayoutCommentClickListener(IItemClickListener onLayoutCommentClickListener) {
            this.onLayoutCommentClickListener = onLayoutCommentClickListener;
        }

        FeedCommentPaginationViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_pp = itemView.findViewById(R.id.iv_pp);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_updated_date = itemView.findViewById(R.id.tv_updated_date);
            tv_comment = itemView.findViewById(R.id.tv_comment);
            iv_image_comment = itemView.findViewById(R.id.iv_image_comment);

            layout_comment = itemView.findViewById(R.id.layout_comment);

            iv_pp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBtnEmpClickListener.onClick();
                }
            });
            tv_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBtnEmpClickListener.onClick();
                }
            });

            layout_comment.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onLayoutCommentClickListener.onClick();
                    return true;
                }
            });
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

    public interface ClickListener {
//        void onClickEmp(int empId);

//        void onClickLayoutComment(int feedCommentId, String feedComment, String feedCommentImage, int position);

        void retryPageLoad();
    }

    public List<FeedComment> getNotificationList() {
        return feedCommentList;
    }

    public void addAtFirst(FeedComment feedComment) {
        feedCommentList.add(0, feedComment);
        notifyItemInserted(0);
    }

    public void add(FeedComment feedComment) {
        feedCommentList.add(feedComment);
        notifyItemInserted(feedCommentList.size() - 1);
    }

    public void addAll(List<FeedComment> feedCommentList) {
        for (FeedComment feedComment : feedCommentList) {
            add(feedComment);
        }
    }

    public void remove(int position) {
        if (position > -1) {
            feedCommentList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new FeedComment());
    }

    public void resetIsLoadingAdded() {
        isLoadingAdded = false;
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = feedCommentList.size() - 1;
        FeedComment feedComment = getItem(position);

        if (feedComment != null) {
            feedCommentList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public FeedComment getItem(int position) {
        return feedCommentList.get(position);
    }

    public void showRetry(boolean show, @Nullable String errorMsg) {
        retryPageLoad = show;
        notifyItemChanged(feedCommentList.size() - 1);

        if (errorMsg != null) this.errorMsg = errorMsg;
    }

    public void editComment(int position, String newFeedComment, String newFeedCommentImage) {
        FeedComment feedComment = getItem(position);

        if (feedComment != null) {
            feedCommentList.get(position).setComment(newFeedComment);
            feedCommentList.get(position).setCommentImagePath(newFeedCommentImage);
            notifyItemChanged(position);
        }
    }
}
