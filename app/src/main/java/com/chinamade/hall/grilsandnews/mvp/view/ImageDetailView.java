package com.chinamade.hall.grilsandnews.mvp.view;


import com.chinamade.hall.grilsandnews.Base.IBaseView;
import com.chinamade.hall.grilsandnews.data.entity.ImageDetailInfo;

import java.util.List;

/**
 * Created by ivan on 2016/8/10.
 */
public interface ImageDetailView extends IBaseView {

    void setImageDetailInfo(List<ImageDetailInfo> imageDetailInfo);

    void netWorkError();

}
