package com.example.featurefeed.presentation.adapter;

import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
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
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.example.main.R;
import com.example.featurefeed.data.source.model.local.Feed;
import com.example.main.core.utils.Constant;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 1;
    private static final int LOADING = 2;

    private List<Feed> feedList;
    private ClickListener clickListener;

    private boolean isLoadingAdded = false;
    private boolean retryPageLoad = false;

    private String errorMsg;

    public FeedAdapter(ClickListener clickListener) {
        this.clickListener = clickListener;
        feedList = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        if (viewType == LOADING) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            holder = new LoadingVH(view);
        } else if (viewType == ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_layout, parent, false);
            holder = new FeedViewHolder(view);
        }
        return holder;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == feedList.size() - 1 && isLoadingAdded) {
            return LOADING;
        } else {
            return ITEM;
        }
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
        }

        else if (holder.getItemViewType() == ITEM) {
            FeedViewHolder feedViewHolder = (FeedViewHolder) holder;
            Glide.with(feedViewHolder.iv_pp.getContext())
                    .load(Constant.getImageAssetPath(Constant.IMAGE_TYPE_EMPLOYEE,
                            Objects.requireNonNull(feedList.get(position).getMakerImagePath())))
                    .into(feedViewHolder.iv_pp);

            CircularProgressDrawable circularProgressDrawable =
                    new CircularProgressDrawable(feedViewHolder.iv_image.getContext());
            circularProgressDrawable.setStrokeWidth(5f);
            circularProgressDrawable.setCenterRadius(30f);
            circularProgressDrawable.start();

            if (Objects.requireNonNull(feedList.get(position).getPostImagePath()).isEmpty()) {
                feedViewHolder.iv_image.setVisibility(View.GONE);
            } else {
                feedViewHolder.iv_image.setVisibility(View.VISIBLE);
                Glide.with(feedViewHolder.iv_image.getContext())
                        .load(Constant.getImageAssetPath(Constant.IMAGE_TYPE_FEED,
                                Objects.requireNonNull(feedList.get(position).getPostImagePath())))
                        .placeholder(circularProgressDrawable)
                        .into(feedViewHolder.iv_image);
            }

            feedViewHolder.tv_name.setText(feedList.get(position).getMakerName());
            feedViewHolder.tv_date.setText(
                    Constant.convertDateTimeToFullDateDay(Objects.requireNonNull(feedList.get(position).getUpdatedDate()))
            );

            if (Objects.requireNonNull(feedList.get(position).getPost()).isEmpty()) {
                feedViewHolder.tv_caption.setVisibility(View.GONE);
            } else {
                feedViewHolder.tv_caption.setVisibility(View.VISIBLE);
                feedViewHolder.tv_caption.setText(feedList.get(position).getPost());
            }

            if (feedList.get(position).getTotalLike() == 0 && feedList.get(position).getTotalComment() == 0) {
                feedViewHolder.layout_total.setVisibility(View.GONE);
            } else {
                feedViewHolder.layout_total.setVisibility(View.VISIBLE);

                feedViewHolder.tv_likes.setText(
                        setTotalLikeString(feedList.get(position).getTotalLike())
                );
                feedViewHolder.tv_comments.setText(
                        setTotalCommentString(feedList.get(position).getTotalComment())
                );
            }

            if (feedList.get(position).getIsMine() == 1) {
//                feedViewHolder.btn_option.setVisibility(View.VISIBLE);
            } else {
//                feedViewHolder.btn_option.setVisibility(View.GONE);
            }

            if (feedList.get(position).getIsLiked() == 1) {
                feedViewHolder.tv_likes.setTextColor(
                        ContextCompat.getColor(
                                feedViewHolder.tv_likes.getContext(),
                                R.color.colorAccent)
                );
                feedViewHolder.btn_like_before.setVisibility(View.GONE);
                feedViewHolder.btn_like_after.setVisibility(View.VISIBLE);
                setTextViewDrawableColor(feedViewHolder.tv_likes,R.color.colorAccent);
            } else {
                feedViewHolder.tv_likes.setTextColor(
                        ContextCompat.getColor(
                                feedViewHolder.tv_likes.getContext(),
                                R.color.gray)
                );
                feedViewHolder.btn_like_before.setVisibility(View.VISIBLE);
                feedViewHolder.btn_like_after.setVisibility(View.GONE);
                setTextViewDrawableColor(feedViewHolder.tv_likes,R.color.gray);
            }

            ((FeedViewHolder) holder).setOnBtnCommentClickListener(
                    new IItemClickListener() {
                        @Override
                        public void onClick() {
                            clickListener.onClickBtnComment(
                                    feedList.get(holder.getAdapterPosition()).getFeedId(),
                                    holder.getAdapterPosition());
                        }
                    }
            );

            ((FeedViewHolder) holder).setOnBtnEmpClickListener(
                    new IItemClickListener() {
                        @Override
                        public void onClick() {
                            clickListener.onClickEmp(
                                    feedList.get(holder.getAdapterPosition()).getMakerId());
                        }
                    }
            );

            ((FeedViewHolder) holder).setOnBtnImagePostClickListener(
                    new IItemClickListener() {
                        @Override
                        public void onClick() {
                            clickListener.onClickImagePost(
                                    feedList.get(holder.getAdapterPosition()).getPostImagePath());
                        }
                    }
            );

            ((FeedViewHolder) holder).setOnBtnLikeClickListener(
                    new IItemClickListener() {
                        @Override
                        public void onClick() {
                            clickListener.onClickBtnLike(
                                    feedList.get(holder.getAdapterPosition()).getFeedId(),
                                    feedList.get(holder.getAdapterPosition()).getIsLiked(),
                                    holder.getAdapterPosition()
                            );
                        }
                    }
            );
/*
            ((FeedViewHolder) holder).setOnBtnOptionClickListener(
                    () -> {
                        PopupMenu popup = new PopupMenu(
                                feedViewHolder.iv_ic_profile.getContext(),
                                ((FeedViewHolder) holder).btn_option
                        );
                        popup.inflate(R.menu.item_option_menu);
                        popup.setOnMenuItemClickListener(item -> {
                            int itemId = item.getItemId();
                            if (itemId == R.id.menu_option_edit) {
                                clickListener.onClickEditFeed(
                                        feedList.get(holder.getAdapterPosition()).getFeedId(),
                                        feedList.get(holder.getAdapterPosition()).getPost(),
                                        feedList.get(holder.getAdapterPosition()).getPostImagePath(),
                                        holder.getAdapterPosition()
                                );
                            } else if (itemId == R.id.menu_option_delete) {
                                clickListener.onClickDeleteFeed(
                                        feedList.get(holder.getAdapterPosition()).getFeedId(),
                                        holder.getAdapterPosition()
                                );
                            }
                            return false;
                        });
                        popup.show();
                    }
            );

 */

            ((FeedViewHolder) holder).setOnBtnPostClickListener(
                    new IItemClickListener() {
                        @Override
                        public void onClick() {
                            clickListener.onClickPost(
                                    feedList.get(holder.getAdapterPosition()));
                        }
                    }
            );

            ((FeedViewHolder) holder).setOnBtnTotalCommentClickListener(
                    new IItemClickListener() {
                        @Override
                        public void onClick() {
                            clickListener.onClickTotalComment(
                                    feedList.get(holder.getAdapterPosition()).getFeedId(),
                                    holder.getAdapterPosition());
                        }
                    }
            );

            ((FeedViewHolder) holder).setOnBtnTotalLikeClickListener(
                    new IItemClickListener() {
                        @Override
                        public void onClick() {
                            clickListener.onClickTotalLike(
                                    feedList.get(holder.getAdapterPosition()).getFeedId());
                        }
                    }
            );
        }
    }

    @Override
    public int getItemCount() {
        return feedList.size();
    }

    class FeedViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_pp, iv_image;
        TextView tv_name, tv_date, tv_caption, tv_likes, tv_comments;
        TextView btn_like_before, btn_like_after;
        LinearLayout btn_like, btn_comment, layout_total;

        IItemClickListener onBtnTotalLikeClickListener;
        IItemClickListener onBtnTotalCommentClickListener;
        IItemClickListener onBtnLikeClickListener;
        IItemClickListener onBtnCommentClickListener;
        IItemClickListener onBtnEmpClickListener;
        IItemClickListener onBtnImagePostClickListener;
        IItemClickListener onBtnPostClickListener;
        IItemClickListener onBtnOptionClickListener;

        public void setOnBtnTotalLikeClickListener(IItemClickListener onBtnTotalLikeClickListener) {
            this.onBtnTotalLikeClickListener = onBtnTotalLikeClickListener;
        }

        public void setOnBtnTotalCommentClickListener(IItemClickListener onBtnTotalCommentClickListener) {
            this.onBtnTotalCommentClickListener = onBtnTotalCommentClickListener;
        }

        public void setOnBtnLikeClickListener(IItemClickListener onBtnLikeClickListener) {
            this.onBtnLikeClickListener = onBtnLikeClickListener;
        }

        public void setOnBtnCommentClickListener(IItemClickListener onBtnCommentClickListener) {
            this.onBtnCommentClickListener = onBtnCommentClickListener;
        }

        public void setOnBtnEmpClickListener(IItemClickListener onBtnEmpClickListener) {
            this.onBtnEmpClickListener = onBtnEmpClickListener;
        }

        public void setOnBtnImagePostClickListener(IItemClickListener onBtnImagePostClickListener) {
            this.onBtnImagePostClickListener = onBtnImagePostClickListener;
        }

        public void setOnBtnPostClickListener(IItemClickListener onBtnPostClickListener) {
            this.onBtnPostClickListener = onBtnPostClickListener;
        }

        public void setOnBtnOptionClickListener(IItemClickListener onBtnOptionClickListener) {
            this.onBtnOptionClickListener = onBtnOptionClickListener;
        }

        FeedViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_pp = (ImageView) itemView.findViewById(R.id.iv_pp);
            iv_image = (ImageView) itemView.findViewById(R.id.iv_image);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_date = (TextView) itemView.findViewById(R.id.tv_date);
            tv_caption = (TextView) itemView.findViewById(R.id.tv_caption);
            tv_likes = (TextView) itemView.findViewById(R.id.tv_likes);
            tv_comments = (TextView) itemView.findViewById(R.id.tv_comments);
            btn_like = (LinearLayout) itemView.findViewById(R.id.btn_like);
            btn_like_before = (TextView) itemView.findViewById(R.id.btn_like_before);
            btn_like_after = (TextView) itemView.findViewById(R.id.btn_like_after);
            btn_comment = (LinearLayout) itemView.findViewById(R.id.btn_comment);
            layout_total = (LinearLayout) itemView.findViewById(R.id.layout_total);

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
            tv_caption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBtnPostClickListener.onClick();
                }
            });
            iv_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBtnImagePostClickListener.onClick();
                }
            });
//            btn_option.setOnClickListener(v -> onBtnOptionClickListener.onClick());
            tv_likes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBtnTotalLikeClickListener.onClick();
                }
            });
            tv_comments.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBtnTotalCommentClickListener.onClick();
                }
            });
            btn_like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBtnLikeClickListener.onClick();
                }
            });
            btn_comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBtnCommentClickListener.onClick();
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
        void onClickTotalLike(int feedId);

        void onClickTotalComment(int feedId, int position);

        void onClickBtnLike(int feedId, int isLiked, int position);

        void onClickBtnComment(int feedId, int position);

        void onClickEmp(int empId);

        void onClickImagePost(String imageName);

        void onClickPost(Feed feed);

        void onClickEditFeed(int feedId, String feedPost, String feedImage, int position);

        void onClickDeleteFeed(int feedId, int position);

        void retryPageLoad();
    }
    
    private void setTextViewDrawableColor(TextView textView, int color) {
        for (Drawable drawable : textView.getCompoundDrawables()) {
            if (drawable != null) {
                drawable.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(textView.getContext(), color), PorterDuff.Mode.SRC_IN));
            }
        }
    }


    private String setTotalLikeString(int total) {
        if (total <= 0) return "";
        else if (total <= 1) return total + " Like";
        else return total + " Likes";
    }

    private String setTotalCommentString(int total) {
        if (total <= 0) return "";
        else if (total <= 1) return total + " Comment";
        else return total + " Comments";
    }

    public void showRetry(boolean show, @Nullable String errorMsg) {
        retryPageLoad = show;
        notifyItemChanged(feedList.size() - 1);

        if (errorMsg != null) this.errorMsg = errorMsg;
    }

    public List<Feed> getFeedList() {
        return feedList;
    }

    public void addAtFirst(Feed feed) {
        feedList.add(0, feed);
        notifyItemInserted(0);
    }

    public void add(Feed feed) {
        feedList.add(feed);
        notifyItemInserted(feedList.size() - 1);
    }

    public void addAll(List<Feed> feedList) {
        for (Feed feed : feedList) {
            add(feed);
        }
    }

    public void remove(int position) {
        if (position > -1) {
            feedList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new Feed());
    }

    public void resetIsLoadingAdded() {
        isLoadingAdded = false;
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = feedList.size() - 1;
        Feed feed = getItem(position);

        if (feed != null) {
            feedList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public Feed getItem(int position) {
        return feedList.get(position);
    }

    public void setLikeFeed(int position) {
        Feed feed = getItem(position);

        if (feed != null) {
            feedList.get(position).setIsLiked(1);
            feedList.get(position).setTotalLike(feedList.get(position).getTotalLike() + 1);
            notifyItemChanged(position);
        }
    }

    public void setDisLikeFeed(int position) {
        Feed feed = getItem(position);

        if (feed != null) {
            feedList.get(position).setIsLiked(0);
            feedList.get(position).setTotalLike(feedList.get(position).getTotalLike() - 1);
            notifyItemChanged(position);
        }
    }

    public void setTotalComment(int position, int totalComment) {
        Feed feed = getItem(position);

        if (feed != null) {
            feedList.get(position).setTotalComment(totalComment);
            notifyItemChanged(position);
        }
    }

    public void editFeed(int position, String newFeedPost, String newFeedImage) {
        Feed feed = getItem(position);

        if (feed != null) {
            feedList.get(position).setPost(newFeedPost);
            feedList.get(position).setPostImagePath(newFeedImage);
            notifyItemChanged(position);
        }
    }
}
