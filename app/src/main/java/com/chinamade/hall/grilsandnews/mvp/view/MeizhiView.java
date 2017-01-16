package com.chinamade.hall.grilsandnews.mvp.view;


import com.chinamade.hall.grilsandnews.Base.IBaseView;
import com.chinamade.hall.grilsandnews.data.bean.Meizhi;

import java.util.List;

/**
 * Created by Administrator on 2016/12/12.
 */
public interface MeizhiView extends IBaseView {
    public void netWorkError();
    public List<Meizhi> getMeizhi(List<Meizhi> results);

}
