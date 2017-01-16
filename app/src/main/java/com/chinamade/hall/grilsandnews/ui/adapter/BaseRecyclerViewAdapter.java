package com.chinamade.hall.grilsandnews.ui.adapter;

import android.animation.Animator;
import android.content.Context;
import android.support.annotation.IntDef;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.chinamade.hall.grilsandnews.ui.adapter.animation.AlphaInAnimation;
import com.chinamade.hall.grilsandnews.ui.adapter.animation.BaseAnimation;
import com.chinamade.hall.grilsandnews.ui.adapter.animation.CustomAnimation;
import com.chinamade.hall.grilsandnews.ui.adapter.animation.ScaleInAnimation;
import com.chinamade.hall.grilsandnews.ui.adapter.animation.SlideInBottomAnimation;
import com.chinamade.hall.grilsandnews.ui.adapter.animation.SlideInLeftAnimation;
import com.chinamade.hall.grilsandnews.ui.adapter.animation.SlideInRightAnimation;
import com.chinamade.hall.grilsandnews.utils.UIUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.LinkedList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * by y on 2016/4/28.
 */
@SuppressWarnings("ALL")
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected Context mContext;
    protected static final int TYPE_ITEM = 0;
    protected static final int TYPE_FOOTER = 1;
    private List<T> mDatas = new LinkedList<>();
    private OnItemClickListener<T> mOnItemClickListener;
    private boolean showFoot = false;
    /**
     * 加载动画
     */
    private boolean mOpenAnimationEnable = false;
    private boolean mFirstOnlyEnable = true;
    private int mLastPosition = -1;
    private int mDuration = 300;
    private BaseAnimation mCustomAnimation;
    private BaseAnimation mSelectAnimation = new AlphaInAnimation();
    private Interpolator mInterpolator = new LinearInterpolator();
    @IntDef({ALPHAIN, SCALEIN, SLIDEIN_BOTTOM, SLIDEIN_LEFT, SLIDEIN_RIGHT,SLIDEIN_CUSTOM})
    @Retention(RetentionPolicy.SOURCE)
    public @interface AnimationType {
    }

    /**
     * Use with {@link #openLoadAnimation}
     */
    public static final int ALPHAIN = 0x00000001;
    /**
     * Use with {@link #openLoadAnimation}
     */
    public static final int SCALEIN = 0x00000002;
    /**
     * Use with {@link #openLoadAnimation}
     */
    public static final int SLIDEIN_BOTTOM = 0x00000003;
    /**
     * Use with {@link #openLoadAnimation}
     */
    public static final int SLIDEIN_LEFT = 0x00000004;
    /**
     * Use with {@link #openLoadAnimation}
     */
    public static final int SLIDEIN_RIGHT = 0x00000005;

    public static final int SLIDEIN_CUSTOM=0x00000006;


    public BaseRecyclerViewAdapter(List<T> datas) {
        if (datas != null) {
            mDatas = datas;
        }
    }


    public void addItemLast(List<T> datas) {
        mDatas.addAll(datas);
    }

    public void addItemTop(List<T> datas) {
        mDatas = datas;
    }

    public void remove(int position) {
        mDatas.remove(position);
        notifyDataSetChanged();
    }

    public void removeAll() {
        if (mDatas.size() != 0) {
            mDatas.clear();
        }
        this.notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        int type = showFoot ? 1 : 0;
        if (mDatas == null) {
            return type;
        }
        return mDatas.size() + type;
    }

    @Override
    public int getItemViewType(int position) {
        if (!showFoot) {
            return TYPE_ITEM;
        }
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    public void isShowFooter(boolean showFoot) {
        this.showFoot = showFoot;
    }

    public boolean isShowFooter() {
        return this.showFoot;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    public List<T> getDatas() {
        return mDatas;
    }

    public interface OnItemClickListener<T> {

        void onItemClick(View view, int position, T info);

        void onItemLongClick(View view, int position, T info);

    }

    public void setOnItemClickListener(OnItemClickListener<T> listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return onCreate(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (getItemViewType(position) == TYPE_FOOTER) {
            return;
        }
        addAnimation(holder);
        final int pos = getRealPosition(holder);
        final T data = mDatas.get(position);
        if (data == null) {
            return;
        }
        onBind(holder, position, data);

        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(v, pos, data);
                    mOnItemClickListener.onItemLongClick(v, pos, data);
                }
            });
        }

    }


    private void addAnimation(RecyclerView.ViewHolder holder) {
        if (mOpenAnimationEnable) {
            if (!mFirstOnlyEnable || holder.getLayoutPosition() > mLastPosition) {
                BaseAnimation animation = null;
                if (mCustomAnimation != null) {
                    animation = mCustomAnimation;
                } else {
                    animation = mSelectAnimation;
                }
                for (Animator anim : animation.getAnimators(holder.itemView)) {
                    startAnim(anim, holder.getLayoutPosition());
                }
                mLastPosition = holder.getLayoutPosition();
            }
        }
    }

    private void startAnim(Animator anim, int layoutPosition) {
        anim.setDuration(mDuration).start();
        anim.setInterpolator(mInterpolator);
    }


    private int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return LayoutInflater.from(UIUtils.getContext()) == null ? position : position - 1;
    }

    protected abstract void onBind(RecyclerView.ViewHolder holder, int position, T data);

    protected abstract BaseRecyclerViewHolder onCreate(ViewGroup parent, int viewType);


    class BaseRecyclerViewHolder extends RecyclerView.ViewHolder {

        public BaseRecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /**
     * 加载动画
     */

    public void openLoadAnimation(@AnimationType int animationType) {
        this.mOpenAnimationEnable = true;
        mCustomAnimation = null;
        switch (animationType) {
            case ALPHAIN:
                mSelectAnimation = new AlphaInAnimation();
                break;
            case SCALEIN:
                mSelectAnimation = new ScaleInAnimation();
                break;
            case SLIDEIN_BOTTOM:
                mSelectAnimation = new SlideInBottomAnimation();
                break;
            case SLIDEIN_LEFT:
                mSelectAnimation = new SlideInLeftAnimation();
                break;
            case SLIDEIN_RIGHT:
                mSelectAnimation = new SlideInRightAnimation();
                break;
            case SLIDEIN_CUSTOM:
                mSelectAnimation = new CustomAnimation();
                break;
            default:
                break;
        }
    }
    /**
     * Set Custom ObjectAnimator
     *
     * @param animation ObjectAnimator
     */
    public void openLoadAnimation(BaseAnimation animation) {
        this.mOpenAnimationEnable = true;
        this.mCustomAnimation = animation;
    }

    public void openLoadAnimation() {
        this.mOpenAnimationEnable = true;
    }


}

