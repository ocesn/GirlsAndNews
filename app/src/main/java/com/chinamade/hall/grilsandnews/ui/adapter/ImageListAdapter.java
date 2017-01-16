package com.chinamade.hall.grilsandnews.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chinamade.hall.grilsandnews.R;
import com.chinamade.hall.grilsandnews.data.entity.ImageListInfo;
import com.chinamade.hall.grilsandnews.http.Api;
import com.chinamade.hall.grilsandnews.utils.ImageLoaderUtils;
import com.chinamade.hall.grilsandnews.utils.UIUtils;

import java.util.List;

import butterknife.Bind;

/**
 * Created by ivan on 2016/8/4.
 */
public class ImageListAdapter extends BaseRecyclerViewAdapter<ImageListInfo> {

    public ImageListAdapter(Context context, List<ImageListInfo> datas) {
        super(datas);
        this.mContext = context;
    }

    @Override
    protected void onBind(RecyclerView.ViewHolder holder, final int position, final ImageListInfo data) {
        if (holder instanceof ViewHolder) {
            final ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.tvTitle.setText(data.getTitle());
            viewHolder.tvSize.setText(data.getSize() + UIUtils.getString(R.string.list_adapter_number));
            viewHolder.tvCount.setText(UIUtils.getString(R.string.list_adapter_views) + data.getCount());
            ImageLoaderUtils.display(mContext, viewHolder.iv, Api.IMAGER_URL + data.getImg());
        }
    }

    @Override
    protected BaseRecyclerViewHolder onCreate(ViewGroup parent, int viewType) {

        if (viewType == TYPE_ITEM) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_list_item, parent, false);
            return new ViewHolder(view);

        } else if (viewType == TYPE_FOOTER) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_footer, parent, false);
            return new BaseRecyclerViewHolder(view);
        }

        return null;
    }

    class ViewHolder extends BaseRecyclerViewHolder {

        @SuppressWarnings("unused")
        @Bind(R.id.image)
        ImageView iv;
        @SuppressWarnings("unused")
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @SuppressWarnings("unused")
        @Bind(R.id.tv_time)
        TextView tvSize;
        @SuppressWarnings("unused")
        @Bind(R.id.tv_count)
        TextView tvCount;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
