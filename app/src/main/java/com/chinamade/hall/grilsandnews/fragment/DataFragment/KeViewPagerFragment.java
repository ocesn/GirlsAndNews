package com.chinamade.hall.grilsandnews.fragment.DataFragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.chinamade.hall.grilsandnews.Base.BaseFragment;
import com.chinamade.hall.grilsandnews.Base.BasePresenter;
import com.chinamade.hall.grilsandnews.R;
import com.chinamade.hall.grilsandnews.data.bean.Dada;
import com.chinamade.hall.grilsandnews.data.bean.XinWenDao;
import com.chinamade.hall.grilsandnews.ui.adapter.DadaPagerAdapter;

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
 * Created by Administrator on 2017/1/6.
 */
public class KeViewPagerFragment extends BaseFragment {

    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.viewPager)
    ViewPager viewPager;

    List<Dada> dadatitle = new ArrayList<>();
    List<XinWenDao> xinwenList = new ArrayList<>( );
    private String url = "http://d.news.163.com/articlesPage/new";
    private String userAgent = "Mozilla/5.0 (Linux; Android 4.0.4; Galaxy Nexus Build/IMM76B)\n" +
            "AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.133 Mobile\n" +
            "Safari/535.19";


    @Override
    protected int setLayoutResource() {
        return R.layout.fragment_viewpager;
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
       new Thread(runnable).start();
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
             switch (msg.what){
                 case 0:
                     show();
                     break;
             }
        }
    };

    private void show() {
        DadaPagerAdapter dadaPagerAdapter = new DadaPagerAdapter(getChildFragmentManager(), dadatitle);
        viewPager.setAdapter(dadaPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
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
                //获取新闻title
                Elements links = doc.select("li");
                for (int j=0;j<links.size();j++){
                    Dada dada = new Dada();
                    String title = links.get(j).select("a").text();
                    dada.setTitle(title);
                    dadatitle.add(dada);
                }

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


}
