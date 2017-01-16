package com.chinamade.hall.grilsandnews.fragment;

import android.os.Bundle;

import com.chinamade.hall.grilsandnews.Base.BaseFragment;
import com.chinamade.hall.grilsandnews.Base.BasePresenter;
import com.chinamade.hall.grilsandnews.R;

/**
 * Created by Administrator on 2017/1/13.
 */
public class MainFragment extends BaseFragment {

    @Override
    protected int setLayoutResource() {
        return R.layout.card_item;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @Override
    protected void init() {

    }
}
