package com.chinamade.hall.grilsandnews.mvp.presenter;

import android.content.Context;

import com.chinamade.hall.grilsandnews.Base.BasePresenter;
import com.chinamade.hall.grilsandnews.NewsDetailActivity;
import com.chinamade.hall.grilsandnews.data.bean.NewsListBean;
import com.chinamade.hall.grilsandnews.data.entity.NewsListInfo;
import com.chinamade.hall.grilsandnews.http.RequestManager;
import com.chinamade.hall.grilsandnews.http.retrofit.MySubscriber;
import com.chinamade.hall.grilsandnews.mvp.view.NewsListView;

import rx.Subscription;

/**
 * Created by ivan on 2016/8/4.
 */
public class NewsListPresenter extends BasePresenter<NewsListView> {
    public NewsListPresenter(Context context) {
        super(context);
    }

    public void requestNewsList(int id, int page) {
        if (page == 1) {
            mView.showProgress();
        } else {
            mView.showFoot();
        }
        Subscription subscription = RequestManager.getInstance(mContext).newsList(id, page,
                new MySubscriber<NewsListBean>() {
            @Override
            public void onError(Throwable e) {
                mView.hideFoot();
                mView.hideProgress();
                mView.netWorkError();
            }

            @Override
            public void onNext(NewsListBean newsListBean) {
                mView.setNewsListInfo(newsListBean.getTngou());
                mView.hideFoot();
                mView.hideProgress();
            }
        });
        mSubscriptions.add(subscription);
    }

    public void onClick(NewsListInfo info) {
        NewsDetailActivity.startIntent(info);
    }
}
