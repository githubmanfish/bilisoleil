package com.yoyiyi.soleil.ui.adapter.home.section.live;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yoyiyi.soleil.R;
import com.yoyiyi.soleil.bean.live.LivePartition;
import com.yoyiyi.soleil.ui.widget.section.StatelessSection;
import com.yoyiyi.soleil.ui.widget.section.ViewHolder;
import com.yoyiyi.soleil.utils.image.ImageLoader;

import java.util.List;
import java.util.Random;

/**
 * @author zzq  作者 E-mail:   soleilyoyiyi@gmail.com
 * @date 创建时间：2017/5/21 11:57
 * 描述:首页直播分区ection
 */
public class LiveRecommendPartitionSection extends StatelessSection<LivePartition.PartitionsBean.LivesBean> {
    private String mUrl;
    private String mTitle;
    private Random mRandom;
    private String mCount;
    private boolean mhasMore = false;

    public LiveRecommendPartitionSection(String title, String url, String count,
                                         List<LivePartition.PartitionsBean.LivesBean> data) {
        super(R.layout.layout_home_live_item_head, R.layout.layout_home_live_item_footer, R.layout.layout_home_live_item_body, data);
        this.mUrl = url;
        this.mTitle = title;
        this.mCount = count;
        this.mRandom = new Random();
    }

    public LiveRecommendPartitionSection(boolean hasMore, String title, String url, String count,
                                         List<LivePartition.PartitionsBean.LivesBean> data) {
        this(title, url, count, data);
        this.mhasMore = hasMore;
    }

    @Override
    public void onBindHeaderViewHolder(ViewHolder holder) {
        Glide.with(mContext)
                .load(mUrl)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .into((ImageView) holder.getView(R.id.iv_icon));
        holder.setText(R.id.tv_title, mTitle);
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder("当前" + mCount + "个直播");
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(
                mContext.getResources().getColor(R.color.pink_text_color));
        stringBuilder.setSpan(foregroundColorSpan, 2,
                stringBuilder.length() - 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.setText(R.id.tv_online, stringBuilder);

    }

    @Override
    public void onBindItemViewHolder(ViewHolder holder, LivePartition.PartitionsBean.LivesBean livesBean) {
        ImageLoader.load(mContext, livesBean.cover.src, R.drawable.bili_default_image_tv,
                holder.getView(R.id.iv_video_preview));
        holder.setText(R.id.tv_video_live_up, livesBean.owner.name)//up
                .setText(R.id.tv_video_online, livesBean.online + "");//在线人数;
        holder.setText(R.id.tv_video_title, livesBean.title);

    }

    @Override
    public void onBindFooterViewHolder(ViewHolder holder) {
        if (mhasMore) {
            holder.setVisible(R.id.bt_more_live, true);

        } else {
            holder.setVisible(R.id.bt_more_live, false);
            holder.getView(R.id.bt_more_live).setOnClickListener(view -> {

            });

        }

        holder.setText(R.id.tv_dynamic, String.valueOf(mRandom.nextInt(200) + "条新动态，点击这里刷新"));
        holder.getView(R.id.iv_refresh).setOnClickListener(view ->
                view.animate()
                        .rotation(360)
                        .setInterpolator(new LinearInterpolator())
                        .setDuration(1000).start());
    }
}
