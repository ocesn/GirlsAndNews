package com.chinamade.hall.grilsandnews.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chinamade.hall.grilsandnews.data.entity.ImageDetailInfo;
import com.chinamade.hall.grilsandnews.http.Api;
import com.chinamade.hall.grilsandnews.utils.UIUtils;

import java.util.List;

/**
 * Created by ivan on 2016/8/10.
 */
public class ImageDetailAdapter extends PagerAdapter {
    private Context context;
    private final List<ImageDetailInfo> info;

    public ImageDetailAdapter(Context context, List<ImageDetailInfo> info) {
        this.context = context;
        this.info = info;
    }

    @Override
    public int getCount() {
        return info.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        final ImageView imageView = new ImageView(context);
        //ImageLoaderUtils.display(UIUtils.getContext(), imageView, Api.IMAGER_URL + info.get(position).getSrc());
         Glide.with(UIUtils.getContext()).load(Api.IMAGER_URL + info.get(position).getSrc()).into(imageView);
        container.addView(imageView);
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //DiaLogUtils.iamgeViewDialog(UIUtils.getActivity(), imageView, position);
                return true;
            }
        });
        return imageView;
    }

}
