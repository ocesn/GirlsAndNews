package com.chinamade.hall.grilsandnews.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chinamade.hall.grilsandnews.R;
import com.chinamade.hall.grilsandnews.data.bean.MeiziBean;
import com.chinamade.hall.grilsandnews.ui.adapter.utils.ImageLoaderUtils;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/1/5.
 */
public class MeizhiRecyclerAdapter extends BaseRecyclerViewAdapter<MeiziBean> {

    public MeizhiRecyclerAdapter(Context context, ArrayList<MeiziBean> meiziBeens) {
        super(meiziBeens);
        mContext= context;
    }

    @Override
    protected void onBind(RecyclerView.ViewHolder holder, int position, MeiziBean data) {
        if(holder instanceof ViewHolder ){
            final  ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.tv_title.setText(data.getTitle());
            viewHolder.tv_time.setText(data.getTime());
            viewHolder.tv_count.setText(data.getCount());
            ImageLoaderUtils.display(mContext, viewHolder.iv_meizi, data.getImageurl());
        }
    }

    @Override
    protected BaseRecyclerViewHolder onCreate(ViewGroup parent, int viewType) {
        if(viewType ==TYPE_ITEM){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_list_item, parent, false);
            return new ViewHolder(view);
        }
        if(viewType ==TYPE_FOOTER){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_footer, parent, false);
            return new BaseRecyclerViewHolder(view);
        }
        return null;
    }

      class ViewHolder extends BaseRecyclerViewHolder {

        @Bind(R.id.image)
        ImageView iv_meizi;
        @Bind(R.id.tv_title)
        TextView tv_title;
        @Bind(R.id.tv_time)
        TextView tv_time;
        @Bind(R.id.tv_count)
        TextView tv_count;

        public ViewHolder(View view) {
            super(view);
        }
    }
}
