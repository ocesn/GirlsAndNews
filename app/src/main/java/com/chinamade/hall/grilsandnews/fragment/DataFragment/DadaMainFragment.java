package com.chinamade.hall.grilsandnews.fragment.DataFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

import com.chinamade.hall.grilsandnews.Base.BaseFragment;
import com.chinamade.hall.grilsandnews.Base.BasePresenter;
import com.chinamade.hall.grilsandnews.R;
import com.chinamade.hall.grilsandnews.constant.Constant;
import com.chinamade.hall.grilsandnews.data.bean.XinWenDao;
import com.chinamade.hall.grilsandnews.ui.adapter.BaseRecyclerViewAdapter;
import com.chinamade.hall.grilsandnews.ui.adapter.DadaAdapter;
import com.chinamade.hall.grilsandnews.widget.MyRecyclerView;

import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/1/12.
 */
public class DadaMainFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,
        MyRecyclerView.LoadingData,BaseRecyclerViewAdapter.OnItemClickListener<XinWenDao>{


    @Bind(R.id.srf_layout)
    SwipeRefreshLayout srfLayout;

    @Bind(R.id.recyclerView)
    MyRecyclerView recyclerView;
    private DadaAdapter dadaAdapter;

    @Override
    protected int setLayoutResource() {
        return R.layout.activity_meizhiview;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @Override
    protected void init() {


    }

    public static Fragment newInstance(int index) {

        Bundle bundle = new Bundle();
        DadaMainFragment fragment = new DadaMainFragment();
        bundle.putInt(FRAGMENT_INDEX, index);
        Log.e("ocean---->",index+"");
        fragment.setArguments(bundle);
        return fragment;
    }


    public void putData(List<XinWenDao> xinwenList) {
        if(xinwenList!=null){
            dadaAdapter = new DadaAdapter(mContext, xinwenList);
            dadaAdapter.setOnItemClickListener(this);
            dadaAdapter.openLoadAnimation(BaseRecyclerViewAdapter.SCALEIN);
             srfLayout.setOnRefreshListener(this);
             recyclerView.setHasFixedSize(true);
             recyclerView.setLoadingData(this);
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(Constant.RECYCLERVIEW_GRIDVIEW,
                    LinearLayoutManager.VERTICAL));
            // recyclerView.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
            recyclerView.setAdapter(dadaAdapter);
        }
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onItemClick(View view, int position, XinWenDao info) {

    }

    @Override
    public void onItemLongClick(View view, int position, XinWenDao info) {

    }

    @Override
    public void onLoadMore() {

    }
}
