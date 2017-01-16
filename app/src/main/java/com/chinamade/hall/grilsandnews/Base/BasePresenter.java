package com.chinamade.hall.grilsandnews.Base;

import android.content.Context;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by ivan on 2016/7/11 .
 */
public abstract class BasePresenter<V extends IBaseView> {
    public V mView;
    protected Context mContext;

    protected CompositeSubscription mSubscriptions;

    public BasePresenter(Context context) {
        mContext = context;
    }

    public void onAttach(V mView){
        this.mView = mView;
    }

    public void onCreate(){
        mSubscriptions = new CompositeSubscription();
    }

    public  void onDestroy(){
        mSubscriptions.clear();
        mView = null;
    }

}
