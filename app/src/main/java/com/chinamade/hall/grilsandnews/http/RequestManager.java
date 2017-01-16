package com.chinamade.hall.grilsandnews.http;

import android.content.Context;

import com.chinamade.hall.grilsandnews.data.bean.ImageDetailBean;
import com.chinamade.hall.grilsandnews.data.bean.ImageListBean;
import com.chinamade.hall.grilsandnews.data.bean.ImageNewBean;
import com.chinamade.hall.grilsandnews.data.bean.NewsDetailBean;
import com.chinamade.hall.grilsandnews.data.bean.NewsListBean;
import com.chinamade.hall.grilsandnews.data.bean.TabNameBean;
import com.chinamade.hall.grilsandnews.data.bean.TabNewsBean;
import com.chinamade.hall.grilsandnews.http.retrofit.ApiService;
import com.chinamade.hall.grilsandnews.utils.RetrofitUtils;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ivan on 2016/7/11.
 */
public class RequestManager {
    private static RequestManager instance;
    private static ApiService apiService;

    public RequestManager(Context context){
        apiService = RetrofitUtils.getInstance(context).create(ApiService.class);
    }

    public static RequestManager getInstance(final Context context){
        if (instance == null){
            synchronized (RequestManager.class){
                if (instance == null){
                    instance = new RequestManager(context);
                }
            }
        }

        return instance;
    }

    public Subscription newsTabName(Subscriber<TabNewsBean> subscriber) {
        return apiService.getNewsTabName()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

                .subscribe(subscriber);
    }

    public Subscription newsList(int id, int page, Subscriber<NewsListBean> subscriber) {
        return apiService.getNewsList(id, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public Subscription newsDetail(int id, Subscriber<NewsDetailBean> subscriber) {
        return apiService.getNewsDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public Subscription imageTabName(Subscriber<TabNameBean> subscriber) {
        return apiService.getImageTabName()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public Subscription imageList(int id, int page, Subscriber<ImageListBean> subscriber) {
        return apiService.getImageList(id, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public Subscription imageDetail(int id, Subscriber<ImageDetailBean> subscriber) {
        return apiService.getImageDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public Subscription imageNew(int id, int rows, Subscriber<ImageNewBean> subscriber) {
        return apiService.getImageNews(id, rows)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }



}
