package com.chinamade.hall.grilsandnews.data.bean;

/**
 * Created by Administrator on 2016/12/12.
 */

public class Category {
    public static final int SECOND_TYPE = 0;
    public static final int THIRD_TYPE = 1;

    private String categoryName;
    private int type;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Category(String categoryName, int type) {
        this.categoryName = categoryName;
        this.type = type;
    }
}
