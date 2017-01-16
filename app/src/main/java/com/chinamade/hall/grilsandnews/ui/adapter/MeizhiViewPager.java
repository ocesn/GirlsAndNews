package com.chinamade.hall.grilsandnews.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.chinamade.hall.grilsandnews.Base.BaseFragment;
import com.chinamade.hall.grilsandnews.data.bean.TitleBean;
import com.chinamade.hall.grilsandnews.fragment.meizhi.JapanMeizhiFragment;
import com.chinamade.hall.grilsandnews.fragment.meizhi.MeizhiMainFragment;
import com.chinamade.hall.grilsandnews.fragment.meizhi.TaiWanMeizhiFragment;
import com.chinamade.hall.grilsandnews.fragment.meizhi.ZiPaiFragment;
import com.chinamade.hall.grilsandnews.fragment.meizhi.qingchunMeizhiFragment;
import com.chinamade.hall.grilsandnews.fragment.meizhi.xingganFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/4.
 */
public class MeizhiViewPager extends FragmentPagerAdapter {
    private List<TitleBean> titleList;
       List<BaseFragment> mFragmentTab = new ArrayList<>();

    private xingganFragment xingganfragment;
    private JapanMeizhiFragment japanMeizhiFragment;
    private TaiWanMeizhiFragment taiWanMeizhiFragment;
    private qingchunMeizhiFragment qingchunmeizhifragment;
    private ZiPaiFragment ziPaiFragment;
    private MeizhiMainFragment meizhiMainFragment;

    public MeizhiViewPager(FragmentManager fm, List<TitleBean> titleList) {
        super(fm);
        this.titleList = titleList;
        initFragmentTab();
    }

     private void initFragmentTab() {
         meizhiMainFragment = new MeizhiMainFragment();
        xingganfragment = new xingganFragment();
        japanMeizhiFragment = new JapanMeizhiFragment();
        taiWanMeizhiFragment = new TaiWanMeizhiFragment();
        qingchunmeizhifragment = new qingchunMeizhiFragment();
        ziPaiFragment = new ZiPaiFragment();

        mFragmentTab.add(meizhiMainFragment);
        mFragmentTab.add(xingganfragment);
        mFragmentTab.add(japanMeizhiFragment);
        mFragmentTab.add(taiWanMeizhiFragment);
        mFragmentTab.add(qingchunmeizhifragment);
        mFragmentTab.add(ziPaiFragment);

    }

    @Override
    public Fragment getItem(int position) {
        return  mFragmentTab.get(position);
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position).getTitle();
    }

    @Override
    public int getCount() {
        return mFragmentTab.size();
    }
}
