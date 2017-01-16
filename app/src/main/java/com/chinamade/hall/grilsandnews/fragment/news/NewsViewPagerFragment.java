package com.chinamade.hall.grilsandnews.fragment.news;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.chinamade.hall.grilsandnews.Base.BaseFragment;
import com.chinamade.hall.grilsandnews.R;
import com.chinamade.hall.grilsandnews.data.entity.TabNameInfo;
import com.chinamade.hall.grilsandnews.mvp.presenter.NewsPagerPresenter;
import com.chinamade.hall.grilsandnews.mvp.view.TabNewsView;
import com.chinamade.hall.grilsandnews.ui.adapter.NewsPagerAdapter;
import com.chinamade.hall.grilsandnews.utils.T;
import com.chinamade.hall.grilsandnews.utils.UIUtils;

import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by ivan on 2016/8/10.
 */
public class NewsViewPagerFragment extends BaseFragment<NewsPagerPresenter> implements TabNewsView {

    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.viewPager)
    ViewPager viewPager;

    private List<TabNameInfo> data;
    private NewsPagerAdapter newsPagerAdapter;

    @Override
    protected int setLayoutResource() {
        return R.layout.fragment_viewpager;
    }

    @Override
    protected NewsPagerPresenter initPresenter() {
        return new NewsPagerPresenter(mContext);
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @Override
    protected void init() {
        mPresenter.requestTabName();

        data = new LinkedList<>();
        newsPagerAdapter = new NewsPagerAdapter(getChildFragmentManager(), data);
    }

    @Override
    public void addTabName(List<TabNameInfo> tabNewsInfo) {
        if (tabNewsInfo != null && !tabNewsInfo.isEmpty()) {
            data.addAll(tabNewsInfo);
            viewPager.setAdapter(newsPagerAdapter);
            tabLayout.setupWithViewPager(viewPager);
        }
    }

    @Override
    public void netWorkError() {
        T.showShort(mContext, UIUtils.getContext().getResources().getString(R.string.network_error));
    }
}
