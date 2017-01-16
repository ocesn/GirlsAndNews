package com.chinamade.hall.grilsandnews.mvp.presenter;

import android.content.Context;

import com.chinamade.hall.grilsandnews.Base.BasePresenter;
import com.chinamade.hall.grilsandnews.ImageDetailActivity;
import com.chinamade.hall.grilsandnews.data.bean.ImageListBean;
import com.chinamade.hall.grilsandnews.data.entity.ImageListInfo;
import com.chinamade.hall.grilsandnews.http.RequestManager;
import com.chinamade.hall.grilsandnews.http.retrofit.MySubscriber;
import com.chinamade.hall.grilsandnews.mvp.view.ImageListView;

import rx.Subscription;

/**
 * Created by ivan on 2016/8/4.
 */
public class ImageListPresenter extends BasePresenter<ImageListView> {

    public ImageListPresenter(Context context) {
        super(context);
    }

    public void requestImageList(int id, int page) {
        if (page == 1) {
            mView.showProgress();
        } else {
            mView.showFoot();
        }
        Subscription subscription = RequestManager.getInstance(mContext).imageList(id, page, new MySubscriber<ImageListBean>() {
            @Override
            public void onError(Throwable e) {
                mView.hideFoot();
                mView.hideProgress();
                mView.netWorkError();
            }

            @Override
            public void onNext(ImageListBean imageListBean) {
                mView.setImageListInfo(imageListBean.getTngou());
                mView.hideFoot();
                mView.hideProgress();
            }
        });
        mSubscriptions.add(subscription);
    }

    public void onClick(ImageListInfo info) {
        ImageDetailActivity.startIntent(info.getId(), info.getTitle());
    }

}
