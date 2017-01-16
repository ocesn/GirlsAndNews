package com.chinamade.hall.grilsandnews.widget.simpleRecyclerView.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by dayaa on 15/10/5.
 */
public class DefaultEventFooter implements EventFooter {
    private AdapterEventDelegate eventDelegate;

    private FrameLayout container;
    private View moreView;
    private View noMoreView;
    private View errorView;

    private int viewFlag = 0;


    public DefaultEventFooter(AdapterEventDelegate eventDelegate) {
        this.eventDelegate = eventDelegate;
    }

    @Override
    public View onCreateVu(ViewGroup parent) {
        container = new FrameLayout(parent.getContext());
        container.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return container;
    }

    @Override
    public void onVuBind(View itemVu, int position) {
        switch (viewFlag) {
            case VIEW_FLAG_SHOW_MORE:
                eventDelegate.onMoreViewShow();
                break;
            case VIEW_FLAG_SHOW_ERROR:
                eventDelegate.onErrorViewShow();
                break;
            case VIEW_FLAG_SHOW_NO_MORE:
                eventDelegate.onNoMoreViewShow();
                break;
        }
    }

    @Override
    public void showView(View view) {
        if (container != null){
            if (view != null) {
                if (container.getVisibility() != View.VISIBLE) container.setVisibility(View.VISIBLE);
                if (view.getParent() == null) container.addView(view);

                for (int i = 0; i < container.getChildCount(); i++) {
                    if (container.getChildAt(i) == view) view.setVisibility(View.VISIBLE);
                    else container.getChildAt(i).setVisibility(View.GONE);
                }
            } else {
                container.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void showError() {
        showView(this.errorView);
        viewFlag = VIEW_FLAG_SHOW_ERROR;
    }

    @Override
    public void showMore() {
        showView(this.moreView);
        viewFlag = VIEW_FLAG_SHOW_MORE;
    }

    @Override
    public void showNoMore() {
        showView(this.noMoreView);
        viewFlag = VIEW_FLAG_SHOW_NO_MORE;
    }

    @Override
    public void hideAll() {
        container.setVisibility(View.GONE);
    }

    @Override
    public void setMoreView(View moreView) {
        this.moreView = moreView;
    }

    @Override
    public void setNoMoreView(View noMoreView) {
        this.noMoreView = noMoreView;
    }

    @Override
    public void setErrorView(View errorView) {
        this.errorView = errorView;
    }
}
