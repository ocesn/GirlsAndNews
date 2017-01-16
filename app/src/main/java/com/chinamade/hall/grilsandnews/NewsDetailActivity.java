package com.chinamade.hall.grilsandnews;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chinamade.hall.grilsandnews.Base.BaseActivity;
import com.chinamade.hall.grilsandnews.Base.BasePresenter;
import com.chinamade.hall.grilsandnews.data.entity.NewsListInfo;
import com.chinamade.hall.grilsandnews.http.Api;
import com.chinamade.hall.grilsandnews.utils.CommonUtils;
import com.chinamade.hall.grilsandnews.utils.ImageLoaderUtils;
import com.chinamade.hall.grilsandnews.utils.UIUtils;

/**
 * Created by Administrator on 2016/12/22.
 */
public class NewsDetailActivity extends BaseActivity {

    private String fromname;
    private String title;
    private String image;
    private String description;
    private ImageView imageView;

    private TextView textview;
    private Toolbar toolbar;
    private TextView texttitle;
    private TextView textContent;
    private String keywords;


    public static void startIntent(NewsListInfo info) {
        Bundle bundle = new Bundle();
        bundle.putString("fromname",info.getFromname());
        bundle.putString("title",info.getTitle());
        bundle.putString("img",info.getImg());
        bundle.putString("keywords",info.getKeywords());
        bundle.putString("description",info.getDescription());
        CommonUtils.startActivity(NewsDetailActivity.class, bundle);
    }

    @Override
    protected int setLayoutResource() {
        return R.layout.newsdetailactivity;
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
        imageView = (ImageView) findViewById(R.id.title_imageView);
         toolbar = (Toolbar) findViewById(R.id.toolbar);
         texttitle = (TextView) findViewById(R.id.news_detail_from_tv);
         textContent = (TextView) findViewById(R.id.news_detail_body_tv);


         toolbar.setTitle(keywords);

         texttitle.setText(fromname);
         textContent.setText("\u3000\u3000"+description);
        ImageLoaderUtils.display(UIUtils.getContext(), imageView, Api.IMAGER_URL +image);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void getBundle() {
        Bundle bundle = getIntent().getExtras();
        if (!bundle.isEmpty()) {
            fromname = bundle.getString("fromname");
            title = bundle.getString("title");
            image = bundle.getString("img");
            keywords = bundle.getString("keywords");
            description = bundle.getString("description");
        }
    }
}
