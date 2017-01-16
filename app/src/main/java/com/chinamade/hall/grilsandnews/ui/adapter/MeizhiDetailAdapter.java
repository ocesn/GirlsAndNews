package com.chinamade.hall.grilsandnews.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chinamade.hall.grilsandnews.data.bean.ContentBean;
import com.chinamade.hall.grilsandnews.ui.adapter.utils.ImageLoaderUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/1/4.
 */
public class MeizhiDetailAdapter extends PagerAdapter {

    private List<ContentBean> content;
    private Context mContext;

    public MeizhiDetailAdapter(Context mContext, List<ContentBean> content) {
        this.mContext = mContext;
        this.content = content;

    }

    @Override
    public int getCount() {
        return content.size();
    }



    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        ImageView imageView = new ImageView(mContext);
        //Glide.with(UIUtils.getContext()).load(content.get(position).getUrl()).into(imageView);
        ImageLoaderUtils.display(mContext, imageView, content.get(position).getUrl());
        container.addView(imageView);
        return imageView;
    }
}
