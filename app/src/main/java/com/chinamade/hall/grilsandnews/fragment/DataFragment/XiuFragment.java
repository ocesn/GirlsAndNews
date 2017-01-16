package com.chinamade.hall.grilsandnews.fragment.DataFragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.chinamade.hall.grilsandnews.Base.BaseFragment;
import com.chinamade.hall.grilsandnews.Base.BasePresenter;
import com.chinamade.hall.grilsandnews.DadaDetailActivity;
import com.chinamade.hall.grilsandnews.R;
import com.chinamade.hall.grilsandnews.data.bean.XinWenDao;
import com.chinamade.hall.grilsandnews.ui.adapter.BaseRecyclerViewAdapter;
import com.chinamade.hall.grilsandnews.ui.adapter.DadaAdapter;
import com.chinamade.hall.grilsandnews.widget.MyRecyclerView;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/1/13.
 */
public class XiuFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,
        MyRecyclerView.LoadingData,BaseRecyclerViewAdapter.OnItemClickListener<XinWenDao>{

    private String url = "http://d.news.163.com/articlesPage/shy";
    private String userAgent = "Mozilla/5.0 (Linux; Android 4.0.4; Galaxy Nexus Build/IMM76B)\n" +
            "AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.133 Mobile\n" +
            "Safari/535.19";

    List<XinWenDao> xinwenList = new ArrayList<>( );
    private DadaAdapter dadaAdapter;
    private boolean isLoad;

    @Bind(R.id.srf_layout)
    SwipeRefreshLayout srfLayout;

    @Bind(R.id.recyclerView)
    MyRecyclerView recyclerView;

    @Override
    protected int setLayoutResource() {
        return  R.layout.activity_meizhiview;
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
        if (!isCreated || !isVisible || isLoad) {
            return;
        }
        srfLayout.setRefreshing(true);
        new Thread(runnable).start();
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
                //获取新闻内容
                Elements selects = doc.select("div.takh_flow_box");
                for (int i=0;i<selects.size();i++){
                    XinWenDao xinWenDao = new XinWenDao();
                    Element ahrefs = selects.get(i).select("a").first();
                    Element img = selects.get(i).select("img").first();


                    String url = ahrefs.attr("href");
                    String image = img.attr("data-original");
                    String title;
                    if(i==0){
                        title = selects.get(i).select("p").text();
                    }else{
                        title = selects.get(i).select("h3").text();
                    }

                    xinWenDao.setUrl(url);
                    xinWenDao.setImg(image);
                    xinWenDao.setTitle(title);

                    xinwenList.add(xinWenDao);
                }

                handler.sendEmptyMessage(0);

            }catch (IOException e){
                e.printStackTrace();
            }
        }
    };

    Handler handler = new Handler(){
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
        if(xinwenList!=null) {
            dadaAdapter = new DadaAdapter(mContext, xinwenList);
            dadaAdapter.setOnItemClickListener(this);
            dadaAdapter.openLoadAnimation(BaseRecyclerViewAdapter.SCALEIN);
            srfLayout.setOnRefreshListener(this);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLoadingData(this);
            /*recyclerView.setLayoutManager(new StaggeredGridLayoutManager(Constant.RECYCLERVIEW_GRIDVIEW,
                    LinearLayoutManager.VERTICAL));*/
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL,false));
            recyclerView.setAdapter(dadaAdapter);
        }
    }


    @Override
    public void onRefresh() {
        xinwenList.clear();
        url ="http://d.news.163.com/articlesPage/shy";
        new Thread(runnable).start();
    }


    @Override
    public void onItemClick(View view, int position, XinWenDao info) {
        String url = info.getUrl();
        String title = info.getTitle();
        DadaDetailActivity.startIntent(url,title);
    }

    @Override
    public void onItemLongClick(View view, int position, XinWenDao info) {

    }

    @Override
    public void onLoadMore() {

    }
}
