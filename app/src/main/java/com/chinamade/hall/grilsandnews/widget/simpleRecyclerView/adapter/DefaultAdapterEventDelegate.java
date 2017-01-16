package com.chinamade.hall.grilsandnews.widget.simpleRecyclerView.adapter;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ivan on 15/10/5.
 */
public class DefaultAdapterEventDelegate implements AdapterEventDelegate {

    private List<SimpleRecyclerViewAdapter.OnLoadMoreListener> loadMoreListeners;
    private List<SimpleRecyclerViewAdapter.OnErrorListener> errorListeners;
    private List<SimpleRecyclerViewAdapter.OnNoMoreListener> noMoreListeners;

    private EventFooter footer;

    private boolean hasData = false;
    private boolean isLoadingMore = false;

    private int status = STATUS_INITIAL;
    private static final int STATUS_INITIAL = 291;
    private static final int STATUS_MORE = 260;
    private static final int STATUS_NO_MORE = 408;
    private static final int STATUS_ERROR = 732;

    public DefaultAdapterEventDelegate(SimpleRecyclerViewAdapter adapter) {
        footer = new DefaultEventFooter(this);
        adapter.addFooterView(footer);
    }

    @Override
    public void onDataAdded(int length) {
        if (length == 0) {
            //当添加0个时，认为已结束加载到底
            if (status == STATUS_INITIAL || status == STATUS_MORE) {
                footer.showNoMore();
            }
        } else {
            //当Error时。再次添加则还原。
            if (status == STATUS_INITIAL || status == STATUS_ERROR) {
                footer.showMore();
            }
            hasData = true;
        }
    }

    @Override
    public void onDataCleared() {
        hasData = false;
        status = STATUS_INITIAL;
        footer.hideAll();
    }

    @Override
    public void stopLoadMore() {
        footer.showNoMore();
        status = STATUS_NO_MORE;
        onNoMoreViewShow();
    }

    @Override
    public void pauseLoadMore() {
        footer.showError();
        status = STATUS_ERROR;
        onErrorViewShow();
    }

    @Override
    public void resumeLoadMore() {
        footer.showMore();
        onMoreViewShow();
    }

    @Override
    public void onMoreViewShow() {
        if (loadMoreListeners != null && loadMoreListeners.size() > 0) {
            for (SimpleRecyclerViewAdapter.OnLoadMoreListener listener : loadMoreListeners) {
                listener.onLoadMore();
            }
        }
    }

    @Override
    public void onErrorViewShow() {
        if (errorListeners != null && errorListeners.size() > 0) {
            for (SimpleRecyclerViewAdapter.OnErrorListener listener : errorListeners) {
                listener.onError();
            }
        }
    }

    @Override
    public void onNoMoreViewShow() {
        if (noMoreListeners != null && noMoreListeners.size() > 0) {
            for (SimpleRecyclerViewAdapter.OnNoMoreListener listener : noMoreListeners) {
                listener.onNoMore();
            }
        }
    }

    @Override
    public void addOnLoadMoreListener(SimpleRecyclerViewAdapter.OnLoadMoreListener listener) {
        if (loadMoreListeners == null) {
            loadMoreListeners = new ArrayList<>();
        }
        loadMoreListeners.add(listener);
    }

    @Override
    public void addOnNoMoreListener(SimpleRecyclerViewAdapter.OnNoMoreListener listener) {
        if (noMoreListeners == null) {
            noMoreListeners = new ArrayList<>();
        }
        noMoreListeners.add(listener);
    }

    @Override
    public void addOnErrorListener(SimpleRecyclerViewAdapter.OnErrorListener listener) {
        if (errorListeners == null) {
            errorListeners = new ArrayList<>();
        }
        errorListeners.add(listener);
    }

    @Override
    public void setMoreView(View view) {
        footer.setMoreView(view);
    }

    @Override
    public void setNoMoreView(View view) {
        footer.setNoMoreView(view);
    }

    @Override
    public void setErrorMoreView(View view) {
        footer.setErrorView(view);
    }

}
