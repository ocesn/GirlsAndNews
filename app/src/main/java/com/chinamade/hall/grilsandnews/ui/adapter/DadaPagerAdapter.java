package com.chinamade.hall.grilsandnews.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.chinamade.hall.grilsandnews.Base.BaseFragment;
import com.chinamade.hall.grilsandnews.data.bean.Dada;
import com.chinamade.hall.grilsandnews.fragment.DataFragment.BaFragment;
import com.chinamade.hall.grilsandnews.fragment.DataFragment.HaiFragment;
import com.chinamade.hall.grilsandnews.fragment.DataFragment.KuFragment;
import com.chinamade.hall.grilsandnews.fragment.DataFragment.MeiFragment;
import com.chinamade.hall.grilsandnews.fragment.DataFragment.MengFragment;
import com.chinamade.hall.grilsandnews.fragment.DataFragment.NuanFragment;
import com.chinamade.hall.grilsandnews.fragment.DataFragment.QiFragment;
import com.chinamade.hall.grilsandnews.fragment.DataFragment.XinFragment;
import com.chinamade.hall.grilsandnews.fragment.DataFragment.XiuFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/12.
 */
public class DadaPagerAdapter extends FragmentPagerAdapter {
    private List<Dada> datatitle;
    List<BaseFragment> mFragmentTab = new ArrayList<>();
    private XinFragment xinFragment;
    private BaFragment baFragment;
    private MengFragment mengFragment;
    private QiFragment qiFragment;
    private HaiFragment haiFragment;
    private KuFragment kuFragment;
    private MeiFragment meiFragment;
    private XiuFragment xiuFragment;
    private NuanFragment nuanFragment;

    public DadaPagerAdapter(FragmentManager childFragmentManager, List<Dada> dadatitle) {
        super(childFragmentManager);
        this.datatitle = dadatitle;
        initFragmentTab();
    }

    private void initFragmentTab() {
        xinFragment = new XinFragment();
        baFragment = new BaFragment();
        mengFragment = new MengFragment();
        qiFragment = new QiFragment();
        haiFragment = new HaiFragment();
        kuFragment = new KuFragment();
        meiFragment = new MeiFragment();
        xiuFragment = new XiuFragment();
        nuanFragment = new NuanFragment();

        mFragmentTab.add(xinFragment);
        mFragmentTab.add(baFragment);
        mFragmentTab.add(mengFragment);
        mFragmentTab.add(qiFragment);
        mFragmentTab.add(haiFragment);
        mFragmentTab.add(kuFragment);
        mFragmentTab.add(meiFragment);
        mFragmentTab.add(xiuFragment);
        mFragmentTab.add(nuanFragment);
    }
 

    @Override
    public Fragment getItem(int position) {
        return mFragmentTab.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentTab.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return datatitle.get(position).getTitle();
    }
}
