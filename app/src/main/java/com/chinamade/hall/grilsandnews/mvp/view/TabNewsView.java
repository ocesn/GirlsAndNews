package com.chinamade.hall.grilsandnews.mvp.view;


import com.chinamade.hall.grilsandnews.Base.IBaseView;
import com.chinamade.hall.grilsandnews.data.entity.TabNameInfo;

import java.util.List;

/**
 * Created by ivan on 2016/8/4 .
 */
public interface TabNewsView extends IBaseView {

    void addTabName(List<TabNameInfo> tabNameInfo);

    void netWorkError();

}
