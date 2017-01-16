package com.chinamade.hall.grilsandnews.mvp.presenter;

import android.content.Context;

import com.chinamade.hall.grilsandnews.Base.BasePresenter;
import com.chinamade.hall.grilsandnews.data.bean.TabNameBean;
import com.chinamade.hall.grilsandnews.http.RequestManager;
import com.chinamade.hall.grilsandnews.http.retrofit.MySubscriber;
import com.chinamade.hall.grilsandnews.mvp.view.TabNameView;

import rx.Subscription;

/**
 * Created by ivan on 2016/8/4.
 * Contact with tzarrb@gmail.com.
 */
public class ImagePagerPresenter extends BasePresenter<TabNameView> {
    public ImagePagerPresenter(Context context) {
        super(context);
    }

    public void requestTabName() {
        Subscription subscription = RequestManager.getInstance(mContext).imageTabName(new MySubscriber<TabNameBean>() {
            @Override
            public void onError(Throwable e) {
                mView.netWorkError();
            }

            @Override
            public void onNext(TabNameBean tabNameBean) {
                mView.addTabName(tabNameBean.getTngou());
            }
        });
        mSubscriptions.add(subscription);
    }
}