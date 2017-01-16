package com.chinamade.hall.grilsandnews;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;

import com.chinamade.hall.grilsandnews.Base.BaseActivity;
import com.chinamade.hall.grilsandnews.data.entity.ImageDetailInfo;
import com.chinamade.hall.grilsandnews.mvp.presenter.ImageDetailPresenter;
import com.chinamade.hall.grilsandnews.mvp.view.ImageDetailView;
import com.chinamade.hall.grilsandnews.ui.adapter.ImageDetailAdapter;
import com.chinamade.hall.grilsandnews.utils.CommonUtils;
import com.chinamade.hall.grilsandnews.utils.T;
import com.chinamade.hall.grilsandnews.utils.UIUtils;

import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by ivan on 2016/8/10.
 */
public class ImageDetailActivity extends BaseActivity<ImageDetailPresenter> implements ImageDetailView
 {

    @Bind(R.id.viewPager)
    ViewPager viewPager;
   /* @Bind(R.id.rlview)
    RecyclerView recyclerView;*/


    private int id;
    private LinkedList<ImageDetailInfo> list;
    private ImageDetailAdapter bigImageAdapter;

    public static void startIntent(int id, String title) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        bundle.putString("title", title);
        CommonUtils.startActivity(ImageDetailActivity.class, bundle);
    }

    @Override
    protected int setLayoutResource() {
        return R.layout.activity_image_detail;
    }

    @Override
    protected ImageDetailPresenter initPresenter() {
        return new ImageDetailPresenter(mContext);
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }



    @Override
    protected void init() {
        getBundle();
        list = new LinkedList<>();
        mPresenter.requestNetWork(id);
        bigImageAdapter = new ImageDetailAdapter(mContext, list);
        viewPager.setAdapter(bigImageAdapter);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mPresenter.competence(requestCode, grantResults);
    }

    private void getBundle() {
        Bundle bundle = getIntent().getExtras();
        if (!bundle.isEmpty()) {
            id = bundle.getInt("id");
        }
    }

    @Override
    public void setImageDetailInfo(List<ImageDetailInfo> imageDetailInfo) {
         if (!imageDetailInfo.isEmpty()) {
            list.addAll(imageDetailInfo);
              viewPager.setAdapter(bigImageAdapter);
        }

    }

    @Override
    public void netWorkError() {
        T.showShort(mContext, UIUtils.getString(R.string.network_error));
    }


    }



