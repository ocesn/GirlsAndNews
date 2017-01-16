package com.chinamade.hall.grilsandnews.mvp.view;


import com.chinamade.hall.grilsandnews.Base.IBaseView;
import com.chinamade.hall.grilsandnews.data.entity.ImageListInfo;

import java.util.List;

/**
 * Created by ivan on 2016/8/10.
 */
@SuppressWarnings("ALL")
public interface ImageListView extends IBaseView {

    void setImageListInfo(List<ImageListInfo> imageListInfo);

    void netWorkError();

    void hideProgress();
    
    void showProgress();

    void showFoot();

    void hideFoot();
}
