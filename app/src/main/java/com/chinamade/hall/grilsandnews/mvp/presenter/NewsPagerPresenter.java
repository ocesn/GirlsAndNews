package com.chinamade.hall.grilsandnews.mvp.presenter;

import android.content.Context;

import com.chinamade.hall.grilsandnews.Base.BasePresenter;
import com.chinamade.hall.grilsandnews.data.bean.TabNewsBean;
import com.chinamade.hall.grilsandnews.http.RequestManager;
import com.chinamade.hall.grilsandnews.http.retrofit.MySubscriber;
import com.chinamade.hall.grilsandnews.mvp.view.TabNewsView;

import rx.Subscription;

/**
 * Created by ivan on 2016/8/4.
 * Contact with tzarrb@gmail.com.
 */
public class NewsPagerPresenter extends BasePresenter<TabNewsView> {
    public NewsPagerPresenter(Context context) {
        super(context);
    }

    public void requestTabName() {
        Subscription subscription = RequestManager.getInstance(mContext).newsTabName(new MySubscriber<TabNewsBean>() {
            @Override
            public void onError(Throwable e) {
                mView.netWorkError();
            }

            @Override
            public void onNext(TabNewsBean tabNameBean) {
                mView.addTabName(tabNameBean.getNewsInfo());
            }
        });
        mSubscriptions.add(subscription);
    }
}