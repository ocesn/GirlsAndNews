package com.chinamade.hall.grilsandnews.mvp.presenter;

import android.content.Context;

import com.chinamade.hall.grilsandnews.Base.BasePresenter;
import com.chinamade.hall.grilsandnews.mvp.view.MeizhiView;


/**
 * Created by Administrator on 2016/12/12.
 */
public class RecyclerPresenter extends BasePresenter<MeizhiView> {
    public RecyclerPresenter(Context mContext) {
        super(mContext);
    }

    public void requestMeiZhi(int page) {
      /*  Subscription subscription = RequestManager.getInstance(mContext).MeiZhi(page,new MySubscriber<GanIo>() {
            @Override
            public void onError(Throwable e) {
                mView.netWorkError();
            }

            @Override
            public void onNext(GanIo ganio) {
                mView.getMeizhi(ganio.getResults());
            }
        });
        mSubscriptions.add(subscription);*/
    }
}
