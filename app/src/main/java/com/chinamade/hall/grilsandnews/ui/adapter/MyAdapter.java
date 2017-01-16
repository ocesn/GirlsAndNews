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
import com.chinamade.hall.grilsandnews.data.bean.RadiumBean;
import com.chinamade.hall.grilsandnews.utils.UIUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/12/29.
 */
public class MyAdapter extends BaseAdapter {
    private Context context;
    private List<RadiumBean> list;

    public MyAdapter(List<RadiumBean> list, Context mContext) {
        this.list = list;
        this.context = mContext;
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
            convertView =  LayoutInflater.from(context).inflate(R.layout.adapter_layout,parent,false);
            viewholer = new ViewHolder(convertView);
            convertView.setTag(viewholer);
        }else{
            viewholer = (ViewHolder) convertView.getTag();
        }
        RadiumBean radiumBean = list.get(position);
        Glide.with(UIUtils.getContext()).load(radiumBean.getImg()).into(viewholer.ivRadiumLogo);
        viewholer.tvRadiumName.setText(radiumBean.getName());
        viewholer.tvRadiumAddress.setText(radiumBean.getAddress());
        return convertView;
    }

    class  ViewHolder{
        ImageView ivRadiumLogo;
        TextView tvRadiumName;
        TextView tvRadiumAddress;
        public ViewHolder(View convertView) {
             ivRadiumLogo = (ImageView) convertView.findViewById(R.id.ivRadiumLogo);
             tvRadiumName = (TextView) convertView.findViewById(R.id.tvRadiumName);
             tvRadiumAddress = (TextView) convertView.findViewById(R.id.tvRadiumAddress);
        }
    }
}
