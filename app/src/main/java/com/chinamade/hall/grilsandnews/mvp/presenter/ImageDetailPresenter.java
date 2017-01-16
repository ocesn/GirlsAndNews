package com.chinamade.hall.grilsandnews.mvp.presenter;

import android.content.Context;
import android.content.pm.PackageManager;

import com.chinamade.hall.grilsandnews.Base.BasePresenter;
import com.chinamade.hall.grilsandnews.R;
import com.chinamade.hall.grilsandnews.constant.Constant;
import com.chinamade.hall.grilsandnews.data.bean.ImageDetailBean;
import com.chinamade.hall.grilsandnews.http.RequestManager;
import com.chinamade.hall.grilsandnews.http.retrofit.MySubscriber;
import com.chinamade.hall.grilsandnews.mvp.view.ImageDetailView;
import com.chinamade.hall.grilsandnews.utils.T;
import com.chinamade.hall.grilsandnews.utils.UIUtils;

import rx.Subscription;

/**
 * Created by ivan on 2016/8/10.
 */
public class ImageDetailPresenter extends BasePresenter<ImageDetailView> {

    public ImageDetailPresenter(Context context) {
        super(context);
    }

    public void requestNetWork(int id) {

        Subscription subscription = RequestManager.getInstance(mContext).imageDetail(id, new MySubscriber<ImageDetailBean>() {
            @Override
            public void onError(Throwable e) {
                mView.netWorkError();
            }

            @Override
            public void onNext(ImageDetailBean imageDetailBean) {
                mView.setImageDetailInfo(imageDetailBean.getList());
            }
        });
        mSubscriptions.add(subscription);
    }

    public void competence(int requestCode, int[] grantResults) {
        if (requestCode == Constant.WRITE_EXTERNAL_STORAGE_REQUEST_CODE) {
            //noinspection StatementWithEmptyBody
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                T.showShort(mContext, UIUtils.getString(R.string.competence_error));
            }
        }
    }
}
