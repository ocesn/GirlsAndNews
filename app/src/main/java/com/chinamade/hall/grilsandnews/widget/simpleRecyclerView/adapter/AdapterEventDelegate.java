package com.chinamade.hall.grilsandnews.widget.simpleRecyclerView.adapter;

import android.view.View;

/**
 * Created by dayaa on 15/10/5.
 */
public interface AdapterEventDelegate {

    void onDataAdded(int length);

    void onDataCleared();

    void stopLoadMore();

    void pauseLoadMore();

    void resumeLoadMore();

    void onMoreViewShow();

    void onErrorViewShow();

    void onNoMoreViewShow();

    void addOnLoadMoreListener(SimpleRecyclerViewAdapter.OnLoadMoreListener listener);

    void addOnNoMoreListener(SimpleRecyclerViewAdapter.OnNoMoreListener listener);

    void addOnErrorListener(SimpleRecyclerViewAdapter.OnErrorListener listener);

    void setMoreView(View view);

    void setNoMoreView(View view);

    void setErrorMoreView(View view);
}
