package com.chinamade.hall.grilsandnews.widget.simpleRecyclerView.adapter;

import android.view.View;

/**
 * Created by dayaa on 15/10/5.
 */
public interface EventFooter extends SimpleRecyclerViewAdapter.SpecialItemVu {

    int VIEW_FLAG_SHOW_MORE = 1;
    int VIEW_FLAG_SHOW_ERROR = 2;
    int VIEW_FLAG_SHOW_NO_MORE = 3;

    void showView(View view);

    void showError();

    void showMore();

    void showNoMore();

    void hideAll();

    void setMoreView(View moreView);

    void setNoMoreView(View noMoreView);

    void setErrorView(View errorView);
}