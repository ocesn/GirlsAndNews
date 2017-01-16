package com.chinamade.hall.grilsandnews.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chinamade.hall.grilsandnews.R;
import com.chinamade.hall.grilsandnews.data.bean.MeiziBean;
import com.chinamade.hall.grilsandnews.utils.UIUtils;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/30.
 */
public class MeizhiAdapter extends BaseAdapter {


    private Context mcontext;
    private ArrayList<MeiziBean> list;

    public MeizhiAdapter(ArrayList<MeiziBean> meiziBeens, Context mContext) {
        this.list = meiziBeens;
        this.mcontext = mContext;
    }

    @Override
    public int getCount() {
        if(list.size()==0 || list==null){
            return 0;
        }else{
            return list.size();
        }
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
         ViewHolder viewholer = null;
        if(convertView ==null){
            convertView =  LayoutInflater.from(mcontext).inflate(R.layout.card_item,parent,false);
            viewholer = new  ViewHolder(convertView);
            convertView.setTag(viewholer);
        }else{
            viewholer = ( ViewHolder) convertView.getTag();
        }
        MeiziBean radiumBean = list.get(position);
        Glide.with(UIUtils.getContext()).load(radiumBean.getImageurl()).into(viewholer.iv_meizi);
        viewholer.favorite.setText(radiumBean.getTitle());
        return convertView;
    }

    class  ViewHolder{
        ImageView iv_meizi;
        TextView favorite;
        public ViewHolder(View convertView) {
            iv_meizi = (ImageView) convertView.findViewById(R.id.iv_meizi);
            favorite = (TextView) convertView.findViewById(R.id.favorite);

        }
    }
}
