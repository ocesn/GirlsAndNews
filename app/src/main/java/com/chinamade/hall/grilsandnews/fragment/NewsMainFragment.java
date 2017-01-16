package com.chinamade.hall.grilsandnews.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.chinamade.hall.grilsandnews.Base.BaseFragment;
import com.chinamade.hall.grilsandnews.R;
import com.chinamade.hall.grilsandnews.constant.Constant;
import com.chinamade.hall.grilsandnews.data.entity.NewsListInfo;
import com.chinamade.hall.grilsandnews.mvp.presenter.NewsListPresenter;
import com.chinamade.hall.grilsandnews.mvp.view.NewsListView;
import com.chinamade.hall.grilsandnews.ui.adapter.BaseRecyclerViewAdapter;
import com.chinamade.hall.grilsandnews.ui.adapter.NewsListAdapter;
import com.chinamade.hall.grilsandnews.utils.T;
import com.chinamade.hall.grilsandnews.utils.UIUtils;
import com.chinamade.hall.grilsandnews.widget.MyRecyclerView;

import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by ivan on 2016/8/4.
 */
public class NewsMainFragment extends BaseFragment<NewsListPresenter> implements NewsListView,
        SwipeRefreshLayout.OnRefreshListener, MyRecyclerView.LoadingData,
        BaseRecyclerViewAdapter.OnItemClickListener<NewsListInfo> {

    private boolean isLoad;

    private LinkedList<NewsListInfo> list;
    private NewsListAdapter adapter;

    private static int page = 1;

    public static Fragment newInstance(int index) {
        Bundle bundle = new Bundle();
        NewsMainFragment fragment = new NewsMainFragment();
        bundle.putInt(FRAGMENT_INDEX, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Bind(R.id.srf_layout)
    SwipeRefreshLayout srfLayout;

    @Bind(R.id.recyclerView)
    MyRecyclerView recyclerView;

    @Override
    protected int setLayoutResource() {
        return R.layout.fragment_news;
    }

    @Override
    protected NewsListPresenter initPresenter() {
        return new NewsListPresenter(mContext);
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @Override
    protected void init() {
        if (!isCreated || !isVisible || isLoad) {
            return;
        }

        list = new LinkedList<>();
        adapter = new NewsListAdapter(mContext, list);

        adapter.setOnItemClickListener(this);
        adapter.openLoadAnimation(BaseRecyclerViewAdapter.SCALEIN);

        srfLayout.setOnRefreshListener(this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLoadingData(this);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(Constant.RECYCLERVIEW_LINEAR,
                LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);

        srfLayout.post(new Runnable() {
            @Override
            public void run() {
                onRefresh();
            }
        });
        isLoad = true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isLoad = false;
    }

    @Override
    public void setNewsListInfo(List<NewsListInfo> newsListInfo) {
        if (!newsListInfo.isEmpty()) {
            list.addAll(newsListInfo);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRefresh() {
        page = 1;
        adapter.removeAll();
        mPresenter.requestNewsList(index + 1, page);
    }

    @Override
    public void onLoadMore() {
        if (!srfLayout.isRefreshing()) {
            ++page;
            mPresenter.requestNewsList(index + 1, page);
        }
    }


    @Override
    public void netWorkError() {
        T.showShort(mContext, UIUtils.getString(R.string.network_error));
    }

    @Override
    public void hideProgress() {
        srfLayout.setRefreshing(false);
    }

    @Override
    public void showProgress() {
        srfLayout.setRefreshing(true);
    }

    @Override
    public void showFoot() {
        adapter.isShowFooter(true);
    }

    public void hideFoot() {
        adapter.isShowFooter(false);
    }

    @Override
    public void onItemClick(View view, int position, NewsListInfo info) {
        mPresenter.onClick(info);
    }

    @Override
    public void onItemLongClick(View view, int position, NewsListInfo info) {

    }

}
