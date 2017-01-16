package com.chinamade.hall.grilsandnews.widget.simpleRecyclerView.adapter;

import android.support.annotation.LayoutRes;
import android.view.View;

/**
 * Created by dayaa on 15/9/26.
 */
public interface SimpleRecyclerVuModel<T> {

    @LayoutRes
    int getLayoutResId();

    /**
     * 初始化Views
     *
     * @param root
     */
    void bindViews(final View root);

    /**
     * 设置view的参数
     */
    void setViews();

    /**
     * 根据数据来设置item的内部views
     *
     * @param model    数据list内部的model
     * @param position 当前adapter调用item的位置
     */
    void updateViews(T model, int position);
}
