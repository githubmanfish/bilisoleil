package com.yoyiyi.soleil.ui.adapter.home.section.live;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yoyiyi.soleil.R;
import com.yoyiyi.soleil.bean.live.LiveRecommend;
import com.yoyiyi.soleil.ui.widget.section.StatelessSection;
import com.yoyiyi.soleil.ui.widget.section.ViewHolder;
import com.yoyiyi.soleil.utils.image.ImageLoader;

import java.util.List;
import java.util.Random;

/**
 * @author zzq  作者 E-mail:   soleilyoyiyi@gmail.com
 * @date 创建时间：2017/5/21 11:57
 * 描述:首页直播推荐主播section
 */
public class LiveRecommendSection extends StatelessSection<LiveRecommend.RecommendDataBean.LivesBean> {
    private boolean mHasHead;
    private boolean mHasFooter;
    private String mUrl;
    private String mTitle;
    private Random mRandom;
    private String mCount;
    private LiveRecommend.RecommendDataBean.BannerDataBean mBannerDataBean;

    public LiveRecommendSection(boolean hasHead, boolean hasFooter, String title, String url, String count,
                                List<LiveRecommend.RecommendDataBean.LivesBean> data) {
        super(R.layout.layout_home_live_item_head, R.layout.layout_home_live_item_footer, R.layout.layout_home_live_item_body, data);
        this.mHasFooter = hasFooter;
        this.mHasHead = hasHead;
        this.mUrl = url;
        this.mTitle = title;
        this.mCount = count;
        this.mRandom = new Random();
    }

    public LiveRecommendSection(boolean hasHead, boolean hasFooter, String title, String url, String count,
                                List<LiveRecommend.RecommendDataBean.LivesBean> data, LiveRecommend.RecommendDataBean.BannerDataBean bannerDataBean) {

        this(hasHead, hasFooter, title, url, count, data);
        this.mBannerDataBean = bannerDataBean;
    }


    @Override
    public void onBindHeaderViewHolder(ViewHolder holder) {
        if (mHasHead) {
            holder.setVisible(R.id.cl_type_root, true);
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

            if (mBannerDataBean != null) {
                holder.setVisible(R.id.cl_video_root, true);
                ImageLoader.load(mContext, mBannerDataBean.cover.src, R.drawable.bili_default_image_tv,
                        holder.getView(R.id.iv_video_preview));
                holder.setText(R.id.tv_video_live_up, mBannerDataBean.owner.name)//up
                        .setText(R.id.tv_video_online, mBannerDataBean.online + "");//在线人数;
                SpannableStringBuilder builder = new SpannableStringBuilder("#" + mBannerDataBean.area + "#" + mBannerDataBean.title);
                ForegroundColorSpan span = new ForegroundColorSpan(
                        mContext.getResources().getColor(R.color.pink_text_color));
                builder.setSpan(span, 0,
                        builder.length() - mBannerDataBean.title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                holder.setText(R.id.tv_video_title, builder);

            } else {
                holder.setVisible(R.id.card_view, false);
            }
        } else {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(0, 0);
            holder.itemView.setLayoutParams(params);
        }
    }

    @Override
    public void onBindItemViewHolder(ViewHolder holder, LiveRecommend.RecommendDataBean.LivesBean livesBean) {
        ImageLoader.load(mContext, livesBean.cover.src, R.drawable.bili_default_image_tv,
                holder.getView(R.id.iv_video_preview));
        holder.setText(R.id.tv_video_live_up, livesBean.owner.name)//up
                .setText(R.id.tv_video_online, livesBean.online + "");//在线人数;
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder("#" + livesBean.area + "#" + livesBean.title);
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(
                mContext.getResources().getColor(R.color.pink_text_color));
        stringBuilder.setSpan(foregroundColorSpan, 0,
                stringBuilder.length() - livesBean.title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.setText(R.id.tv_video_title, stringBuilder);

    }

    @Override
    public void onBindFooterViewHolder(ViewHolder holder) {
        if (mHasFooter) {
            holder.setVisible(R.id.bt_more, true)
                    .setText(R.id.tv_dynamic, String.valueOf(mRandom.nextInt(200) + "条新动态，点击这里刷新"));
            holder.getView(R.id.iv_refresh).setOnClickListener(view ->
                    view.animate()
                            .rotation(360)
                            .setInterpolator(new LinearInterpolator())
                            .setDuration(1000).start());
            holder.getView(R.id.iv_refresh).setOnClickListener(view ->
                    view.animate()
                            .rotation(360)
                            .setInterpolator(new LinearInterpolator())
                            .setDuration(1000).start());
        } else {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, 0);
            holder.itemView.setLayoutParams(params);
        }
    }
}
