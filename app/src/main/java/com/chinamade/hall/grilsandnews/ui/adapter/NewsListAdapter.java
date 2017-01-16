package com.chinamade.hall.grilsandnews.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chinamade.hall.grilsandnews.R;
import com.chinamade.hall.grilsandnews.WebViewActivity;
import com.chinamade.hall.grilsandnews.data.entity.NewsListInfo;
import com.chinamade.hall.grilsandnews.http.Api;
import com.chinamade.hall.grilsandnews.utils.ImageLoaderUtils;
import com.chinamade.hall.grilsandnews.utils.TimeUtils;
import com.chinamade.hall.grilsandnews.utils.UIUtils;

import java.util.List;

import butterknife.Bind;

/**
 * Created by ivan on 2016/8/10.
 */
public class NewsListAdapter extends BaseRecyclerViewAdapter<NewsListInfo> {
    public NewsListAdapter(Context context, List<NewsListInfo> datas) {
        super(datas);
        mContext = context;
    }

    @Override
    protected void onBind(RecyclerView.ViewHolder holder, int position, final NewsListInfo data) {
        if (holder instanceof ViewHolder) {
            final ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.tvFromname.setText(UIUtils.getString(R.string.news_fromname) + data.getFromname());
            viewHolder.tvTime.setText(UIUtils.getString(R.string.news_time) + TimeUtils.timeFormat(data.getTime()));
            viewHolder.tvTitle.setText(data.getTitle());
            viewHolder.tvUrl.setText(data.getFromurl());
            ImageLoaderUtils.display(mContext, viewHolder.image, Api.IMAGER_URL + data.getImg());

            viewHolder.tvUrl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    WebViewActivity.startIntent(data.getFromurl(), data.getTitle());
                }
            });
        }
    }

    @Override
    protected BaseRecyclerViewHolder onCreate(ViewGroup parent, int viewType) {

        if (viewType == TYPE_ITEM) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_list_item, parent, false);
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
        ImageView image;
        @SuppressWarnings("unused")
        @Bind(R.id.tv_fromname)
        TextView tvFromname;
        @SuppressWarnings("unused")
        @Bind(R.id.tv_time)
        TextView tvTime;
        @SuppressWarnings("unused")
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @SuppressWarnings("unused")
        @Bind(R.id.tv_url)
        TextView tvUrl;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
