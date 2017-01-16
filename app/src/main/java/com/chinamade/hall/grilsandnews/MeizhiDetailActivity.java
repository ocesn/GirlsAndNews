package com.chinamade.hall.grilsandnews;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;

import com.chinamade.hall.grilsandnews.Base.BaseActivity;
import com.chinamade.hall.grilsandnews.Base.BasePresenter;
import com.chinamade.hall.grilsandnews.data.bean.ContentBean;
import com.chinamade.hall.grilsandnews.ui.adapter.MeizhiDetailAdapter;
import com.chinamade.hall.grilsandnews.utils.CommonUtils;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/1/4.
 */
public class MeizhiDetailActivity extends BaseActivity {

    private String url;
    private List<ContentBean> content;
    @Bind(R.id.viewPager)
    ViewPager viewPager;


    private String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36";

    @Override
    protected int setLayoutResource() {
        return R.layout.activity_image_detail;
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
        getBundle();
        new Thread(runnable).start();
    }

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    //收到消息后执行handler
                    show();
                    break;
            }

        }
    };

    private void show() {
        if(content!=null){
            MeizhiDetailAdapter meizhiDetailAdapter = new MeizhiDetailAdapter(mContext, content);
            viewPager.setAdapter(meizhiDetailAdapter);
        }
    }

    Runnable runnable = new Runnable(){
        @Override
        public void run() {
            content = getContent(url);
            //执行完毕后给handler发送一个空消息
            mHandler.sendEmptyMessage(0);
        }
    };

    private List<ContentBean> getContent(String url) {
        List<ContentBean> ContentBeanlist = new ArrayList<>();
        if(url!=null){
            int mcount =   getCount(url); //获取有多少页
            for (int i=1;i<mcount+1;i++){
                ContentBean content = null;
                content = fetchContent(url+"/"+i); //抓取每一张图片
                if(content !=null){
                    ContentBeanlist.add(content);
                }
            }

        }
        return ContentBeanlist;
    }

    private ContentBean fetchContent(String s) {
        ContentBean contentBean = new ContentBean();
        if(s !=null){
            Connection conn = Jsoup.connect(s);
            // 修改http包中的header,伪装成浏览器进行抓取
            conn.header("User-Agent",userAgent);
            Document doc = null;
            try{
                doc =conn.get();
            }catch (IOException e){
                e.printStackTrace();
            }
            Elements links =  doc.select("img[src~=(?i)\\.(png|jpe?g)]");
            if(links.size() ==0){
                return null;
            }
            Element element = links.get(0).getElementsByTag("img").first();
            contentBean.setUrl(element.attr("src"));
            contentBean.setTitle(element.attr("alt"));
        }
        return contentBean;
    }

    //获得span里的浏览量<span>64<span>
    private int getCount(String url) {
        Connection conn = Jsoup.connect(url);
        // 修改http包中的header,伪装成浏览器进行抓取
        conn.header("User-Agent",userAgent);
        Document doc = null;
        try{
            doc =conn.get();
        }catch (IOException e){
            e.printStackTrace();
        }

        Elements pages = doc.select("span");
        Element page = pages.get(10);

        Pattern p = Pattern.compile("[\\d*]");
        Matcher m = p.matcher(page.toString());
        StringBuffer stringBuffer = new StringBuffer();
        while (m.find()) {
            stringBuffer.append(m.group());
        }
        return Integer.parseInt(stringBuffer.toString());
    }




    public static void startIntent(String url) {
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        CommonUtils.startActivity(MeizhiDetailActivity.class, bundle);
    }

    public void getBundle() {
        Bundle bundle = getIntent().getExtras();
        if (!bundle.isEmpty()) {
            url = bundle.getString("url");
        }
    }
}
