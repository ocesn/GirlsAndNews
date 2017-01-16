package com.chinamade.hall.grilsandnews.data.bean;

/**
 * Created by Administrator on 2016/12/30.
 */
public class TitleBean {
    private String title;
    private String pageurl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPageurl() {
        return pageurl;
    }

    public void setPageurl(String pageurl) {
        this.pageurl = pageurl;
    }

    public TitleBean(String title, String pageurl) {
        this.title = title;
        this.pageurl = pageurl;
    }

    public TitleBean() {
    }
}
