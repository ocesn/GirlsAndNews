package com.chinamade.hall.grilsandnews;

import android.app.Application;
import android.content.Context;

import com.chinamade.hall.grilsandnews.constant.Constant;
import com.chinamade.hall.grilsandnews.utils.CrashHandler;
import com.chinamade.hall.grilsandnews.utils.FileUtils;
import com.chinamade.hall.grilsandnews.utils.L;

import java.io.File;

/**
 * Created by ivan on 2016/8/4.
 */
public class MainApplication extends Application {
    private static Context mContext;
    private static MainApplication instance = null;

    public static Object intentData;

    public static String ROOT;
    public static String DIR;
    public static String DIR_CACHE;
    public static String DIR_FILE;
    public static String DIR_IMAGE;

    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

        L.isDebug = true;

        createFilePath();

        //设置异常捕获
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());
    }

    /**
     * 创建文件存储目录
     * */
    private void createFilePath() {
        if (null == ROOT) {

            // 获取SD卡的根目录路径,如果不存在就返回Null
            if (FileUtils.isSdcardExist()) {
                ROOT = FileUtils.getSDPath();
            }else {
                // 获取内置存储区的地址
                ROOT = mContext.getFilesDir().toString();
            }
        }

        if (ROOT != null){
            DIR = ROOT + File.separator + Constant.DIR;
            DIR_FILE = ROOT + File.separator + Constant.DIR_FILE;
            DIR_IMAGE = ROOT + File.separator + Constant.DIR_IMAGE;
            DIR_CACHE = ROOT + File.separator + Constant.DIR_CACHE;

            // 如果目录不存在则创建目录
            if (!FileUtils.isFileExists(DIR))
                FileUtils.createDirFile(MainApplication.DIR);
            if (!FileUtils.isFileExists(DIR_FILE))
                FileUtils.createDirFile(MainApplication.DIR_FILE);
            if (!FileUtils.isFileExists(DIR_IMAGE))
                FileUtils.createDirFile(MainApplication.DIR_IMAGE);
            if (!FileUtils.isFileExists(DIR_CACHE))
                FileUtils.createDirFile(MainApplication.DIR_CACHE);
        }
    }

    /**
     * 设置Intent数据
     * */
    public static void setIntentData(Object data){
        intentData = data;
    }

    /**
     * 获取Intent数据
     * */
    public static Object getIntentData(){
        Object data = intentData;
        intentData = null;
        return data;
    }
}
