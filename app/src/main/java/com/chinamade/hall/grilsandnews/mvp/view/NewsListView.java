package com.chinamade.hall.grilsandnews.mvp.view;

import com.chinamade.hall.grilsandnews.Base.IBaseView;
import com.chinamade.hall.grilsandnews.data.entity.NewsListInfo;

import java.util.List;

/**
 * Created by ivan on 2016/8/4.
 */
public interface NewsListView extends IBaseView {

    void setNewsListInfo(List<NewsListInfo> newsListInfo);

    void netWorkError();

    void hideProgress();
    
    void showProgress();

    void showFoot();

    void hideFoot();
}
