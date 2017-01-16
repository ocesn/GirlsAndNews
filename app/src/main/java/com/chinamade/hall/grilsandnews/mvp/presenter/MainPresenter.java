package com.chinamade.hall.grilsandnews.mvp.presenter;

import android.app.Activity;

import com.chinamade.hall.grilsandnews.Base.BasePresenter;
import com.chinamade.hall.grilsandnews.mvp.view.MainView;


/**
 * Created by ivan on 2016/7/6.
 */
public class MainPresenter extends BasePresenter<MainView> {

    public MainPresenter(Activity activity) {
        super(activity);
    }

    public void switchId(int id) {
        /*switch (id) {
            case R.id.ke_36:
                mView.switch36ke();
                break;
            case R.id.navigation_item_news:
                mView.switchNews();
                break;
            case R.id.navigation_item_image_classification:
                mView.switchImageClassification();
                break;
            case R.id.navigation_about:
                mView.switchAbout();
                break;
            case R.id.navigation_Test:
                mView.switchTest();
        }*/
    }
}
