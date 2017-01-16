package com.chinamade.hall.grilsandnews.fragment.meizhi;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.chinamade.hall.grilsandnews.Base.BaseFragment;
import com.chinamade.hall.grilsandnews.Base.BasePresenter;
import com.chinamade.hall.grilsandnews.MeizhiDetailActivity;
import com.chinamade.hall.grilsandnews.R;
import com.chinamade.hall.grilsandnews.constant.Constant;
import com.chinamade.hall.grilsandnews.data.bean.MeiziBean;
import com.chinamade.hall.grilsandnews.ui.adapter.BaseRecyclerViewAdapter;
import com.chinamade.hall.grilsandnews.ui.adapter.MeizhiRecyclerAdapter;
import com.chinamade.hall.grilsandnews.widget.MyRecyclerView;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/1/5.
 */
public class JapanMeizhiFragment extends BaseFragment implements  SwipeRefreshLayout.OnRefreshListener,
        MyRecyclerView.LoadingData,BaseRecyclerViewAdapter.OnItemClickListener<MeiziBean>{
    @Bind(R.id.srf_layout)
    SwipeRefreshLayout srfLayout;

    @Bind(R.id.recyclerView)
    MyRecyclerView recyclerView;


    private boolean isLoad;
    private String url = "http://www.mzitu.com/japan/page/1";
    private String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36";
    private ArrayList<MeiziBean> meiziBeens = new ArrayList<>();
    private MeizhiRecyclerAdapter myAdapter;
    private String curPage ="1";



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

        // meizhitv.setText("我是首页！！！");
        srfLayout.setRefreshing(true);
        new Thread(runnable).start();

    }

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    //收到消息后执行handler
                    srfLayout.setRefreshing(false);
                    show();
                    break;
            }
        }
    };

    private void show() {
        if(meiziBeens!=null){
            myAdapter = new MeizhiRecyclerAdapter( mContext,meiziBeens);
            myAdapter.setOnItemClickListener(this);
            myAdapter.openLoadAnimation(BaseRecyclerViewAdapter.SCALEIN);
            srfLayout.setOnRefreshListener(this);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLoadingData(this);
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(Constant.RECYCLERVIEW_GRIDVIEW,
                    LinearLayoutManager.VERTICAL));
            //recyclerView.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
            recyclerView.setAdapter(myAdapter);
        }
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Connection conn = Jsoup.connect(url);
            // 修改http包中的header,伪装成浏览器进行抓取
            conn.header("User-Agent",userAgent);
            Document doc = null;
            try{
                doc =conn.get();
            }catch (IOException e){
                e.printStackTrace();
            }
            //获取首页妹纸图片的数据
            Element imgelement;
            Element aelement;
            Elements links = doc.select("li");

            for (int i=7;i<links.size();i++){
                imgelement = links.get(i).select("img").first();
                aelement = links.get(i).select("a").first();
                Element spantime = links.get(i).select("span.time").first();
                Element spancount = links.get(i).select("span.view").first();

                MeiziBean meiziBean = new MeiziBean();

                meiziBean.setTitle(imgelement.attr("alt").toString());
                meiziBean.setTime(spantime.text());
                meiziBean.setCount(spancount.text());
                meiziBean.setImageurl(imgelement.attr("data-original"));//本张图片的url
                meiziBean.setUrl(aelement.attr("href"));//本张图片以及以下所有小图片
                meiziBeens.add(meiziBean);
            }
            //执行完毕后给handler发送一个空消息
            mHandler.sendEmptyMessage(0);

        }
    };




    @Override
    public void onRefresh() {
        meiziBeens.clear();
        url = "http://www.mzitu.com/japan/page/1";
        new Thread(runnable).start(); //子线程
    }

    @Override
    public void onItemClick(View view, int position, MeiziBean info) {
        String url = info.getUrl();
        //Toast.makeText(mContext,"我下面的链接地址是:"+url, Toast.LENGTH_SHORT).show();
        MeizhiDetailActivity.startIntent(url);
    }

    @Override
    public void onItemLongClick(View view, int position, MeiziBean info) {

    }

    @Override
    public void onLoadMore() {
        if (!srfLayout.isRefreshing()) {
            curPage =""+(Integer.parseInt(curPage)+1);
            url = url+curPage;
            new Thread(runnable).start(); //子线程
        }
    }
}
