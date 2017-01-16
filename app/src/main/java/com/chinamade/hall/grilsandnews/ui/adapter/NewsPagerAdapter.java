package com.chinamade.hall.grilsandnews.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.chinamade.hall.grilsandnews.data.entity.TabNameInfo;
import com.chinamade.hall.grilsandnews.fragment.NewsMainFragment;

import java.util.List;

/**
 * Created by ivan on 2016/8/10.
 */
public class NewsPagerAdapter extends FragmentPagerAdapter {

    private final List<TabNameInfo> mData;

    public NewsPagerAdapter(FragmentManager fm, List<TabNameInfo> mDatas) {
        super(fm);
        this.mData = mDatas;
    }

    @Override
    public Fragment getItem(int position) {
        return NewsMainFragment.newInstance(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mData.get(position).getName();
    }

    @Override
    public int getCount() {
        return mData.size();
    }
}
