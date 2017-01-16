package com.chinamade.hall.grilsandnews;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.chinamade.hall.grilsandnews.Base.BaseActivity;
import com.chinamade.hall.grilsandnews.Base.BasePresenter;
import com.chinamade.hall.grilsandnews.utils.CommonUtils;

import butterknife.Bind;

/**
 * Created by ivan on 2016/8/10.
 */
public class WebViewActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.webView)
    WebView webView;
    @Bind(R.id.toolBar)
    Toolbar toolBar;
    @Bind(R.id.srf_layout)
    SwipeRefreshLayout srfLayout;

    private String url;
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

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void init() {
        getBundle();
        toolBar.setTitle(title);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setUseWideViewPort(true);

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                srfLayout.setRefreshing(false);
            }
        });

        srfLayout.setOnRefreshListener(this);

        srfLayout.post(new Runnable() {
            @Override
            public void run() {
                srfLayout.setRefreshing(true);
                onRefresh();
            }
        });


    }

    private void getBundle() {
        Bundle bundle = getIntent().getExtras();
        if (!bundle.isEmpty()) {
            url = bundle.getString("url");
            title = bundle.getString("title");
        }
    }

    public static void startIntent(String url, String title) {
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        bundle.putString("title", title);
        CommonUtils.startActivity(WebViewActivity.class, bundle);
    }

    @Override
    public void onRefresh() {
        webView.loadUrl(url);
    }
}
