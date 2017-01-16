/*
 *  Copyright (C) 2015 GuDong <gudong.name@gmail.com>
 *
 *  This file is part of GdTranslate
 *
 *  GdTranslate is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  GdTranslate is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with GdTranslate.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.chinamade.hall.grilsandnews.Base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.WindowManager;

import java.lang.reflect.Method;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by ivan on 2016/7/11.
 */
public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements IBaseView{

    protected static Context mContext;
    protected String activityName;

    protected P mPresenter;

    protected boolean runTasksFist;
    protected boolean isCreated;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutResource());

        mContext = this;
        isCreated = true;
        activityName = this.getClass().getName();

        mPresenter = initPresenter();
        if (mPresenter != null){
            mPresenter.onAttach(this);
            mPresenter.onCreate();
        }

        init(savedInstanceState);
        init();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        if (containEventBusMethod()) {
            EventBus.getDefault().register(this);
        }
    }

    protected abstract int setLayoutResource();

    protected abstract P initPresenter();

    protected abstract void init(Bundle savedInstanceState);

    protected abstract void init();

    @Override
    protected void onResume() {
        super.onResume();
        runTasksFist = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        runTasksFist = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isCreated = false;
        ButterKnife.unbind(this);
        if (containEventBusMethod()) {
            EventBus.getDefault().unregister(this);
        }
        if (mPresenter != null){
            mPresenter.onDestroy();
        }
    }

    private boolean containEventBusMethod() {
        Method[] methods = this.getClass().getMethods();
        for (Method method : methods) {
            if (method.getName().startsWith("onEvent"))
                return true;
        }
        return false;
    }

    protected void initActionBar(boolean homeButtonEnable,String title){
        getSupportActionBar().setHomeButtonEnabled(homeButtonEnable);
        getSupportActionBar().setDisplayHomeAsUpEnabled(homeButtonEnable);
        getSupportActionBar().setTitle(title);
    }

    //隐藏状态栏
    protected void hideStatusBar() {
        WindowManager.LayoutParams attrs = getWindow().getAttributes();
        attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setAttributes(attrs);
    }

    //显示状态栏
    protected void showStatusBar() {
        WindowManager.LayoutParams attrs = getWindow().getAttributes();
        attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setAttributes(attrs);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public static Context getContext() {
        return mContext;
    }

}
