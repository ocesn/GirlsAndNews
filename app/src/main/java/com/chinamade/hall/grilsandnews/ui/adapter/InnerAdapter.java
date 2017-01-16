package com.chinamade.hall.grilsandnews.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;

import com.chinamade.hall.grilsandnews.R;
import com.chinamade.hall.grilsandnews.data.entity.ImageDetailInfo;
import com.chinamade.hall.grilsandnews.http.Api;
import com.chinamade.hall.grilsandnews.utils.ImageLoaderUtils;
import com.chinamade.hall.grilsandnews.utils.UIUtils;

import java.util.LinkedList;

/**
 * Created by Administrator on 2016/12/27.
 */
public class InnerAdapter extends BaseAdapter {
    private LinkedList<ImageDetailInfo> list;
    private Context context;
    private  int cardWith ;
    private  int cardHeight;

    public InnerAdapter(Context mContext, LinkedList<ImageDetailInfo> list, int cardWidth, int cardHeight) {
        this.context = mContext;
        this.list = list;
        this.cardWith =cardWidth;
        this.cardHeight = cardHeight;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        if(list==null ||list.size()==0) return null;
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_new_item, parent, false);
            holder = new ViewHolder();
            convertView.setTag(holder);
             holder.portraitView.getLayoutParams().width = cardWith;
             holder.portraitView.getLayoutParams().height = cardHeight;
            holder.portraitView = (ImageView) convertView.findViewById(R.id.portrait);
            holder.nameView = (TextView) convertView.findViewById(R.id.name);
            holder.collectView = (CheckedTextView) convertView.findViewById(R.id.favorite);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        ImageLoaderUtils.display(UIUtils.getContext(),  holder.portraitView, Api.IMAGER_URL + list.get(position).getSrc());
        return convertView;
    }

    public void remove(int index) {
        if(index > -1 && index < list.size()){
            list.remove(index);
            notifyDataSetChanged();
        }
    }


    private static class ViewHolder {
        ImageView portraitView;
        CheckedTextView collectView;
        TextView nameView;
    }
}
