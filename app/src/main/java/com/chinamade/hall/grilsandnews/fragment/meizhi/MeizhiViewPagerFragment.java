package com.chinamade.hall.grilsandnews.fragment.meizhi;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.chinamade.hall.grilsandnews.Base.BaseFragment;
import com.chinamade.hall.grilsandnews.Base.BasePresenter;
import com.chinamade.hall.grilsandnews.R;
import com.chinamade.hall.grilsandnews.data.bean.TitleBean;
import com.chinamade.hall.grilsandnews.ui.adapter.MeizhiViewPager;

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
 * Created by Administrator on 2017/1/4.
 */
public class MeizhiViewPagerFragment extends BaseFragment {

    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.viewPager)
    ViewPager viewPager;



    private String url = "http://www.mzitu.com/page/1";
    private String userAgent = "Mozilla/5.0 (Linux; Android 4.0.4; Galaxy Nexus Build/IMM76B)\n" +
            "AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.133 Mobile\n" +
            "Safari/535.19";
    private List<TitleBean> titleList = new ArrayList<>();
    private MeizhiViewPager meizhiViewPager;

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

        meizhiViewPager = new MeizhiViewPager(getChildFragmentManager(), titleList);
        viewPager.setAdapter(meizhiViewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    Runnable runnable  = new Runnable() {
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


            Elements ahref = doc.select("a");
            for (int i=1;i<7;i++){
                TitleBean titleBean = new TitleBean();
                titleBean.setTitle(ahref.get(i).attr("title").toString());
                titleBean.setPageurl(ahref.get(i).attr("href").toString());
                titleList.add(titleBean);
            }
            handler.sendEmptyMessage(0);
        }
    };

}
