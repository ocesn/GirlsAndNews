package com.chinamade.hall.grilsandnews;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.chinamade.hall.grilsandnews.Base.BaseActivity;
import com.chinamade.hall.grilsandnews.Base.BasePresenter;
import com.chinamade.hall.grilsandnews.utils.CommonUtils;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/1/13.
 */
public class DadaDetailActivity extends BaseActivity {

     String url;
    @Bind(R.id.webView)
    WebView webView;


    private String title;

    @Override
    protected int setLayoutResource() {
        return R.layout.activity_webview;
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

        initWebview(url,title);
    }

    private void initWebview(String url, String title) {
        if(null !=url && title !=null){
            webView.setWebViewClient(new WebViewClient());
            //webView.getSettings().setJavaScriptEnabled(true);
            webView.loadUrl(url);

        }
    }

    public static void startIntent(String url, String title) {
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        bundle.putString("title",title);
        CommonUtils.startActivity(DadaDetailActivity.class, bundle);
    }

    public void getBundle() {
        Bundle bundle = getIntent().getExtras();
        if (!bundle.isEmpty()) {
            title = bundle.getString("title");
            url ="http://d.news.163.com"+bundle.getString("url");
        }
    }
}
