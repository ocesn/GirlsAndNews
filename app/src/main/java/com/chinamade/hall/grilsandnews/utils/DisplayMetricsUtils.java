package com.chinamade.hall.grilsandnews.utils;

import android.content.Context;
import android.util.DisplayMetrics;

import com.chinamade.hall.grilsandnews.MainApplication;


public class DisplayMetricsUtils
{
	private static final Context mContext;
	private static DisplayMetrics dm;

	static
	{
		mContext = MainApplication.getContext();
		dm = mContext.getResources().getDisplayMetrics();
	}

	/**
	 * 获取屏幕高度（px像素）
	 * */
	public static float getHeight()
	{
		return dm.heightPixels;
	}

	/**
	 * 获取屏幕宽度（px像素）
	 * */
	public static float getWidth()
	{
		return dm.widthPixels;
	}

	/**
	 * 获取屏幕密度
	 * */
	public static float getDensity()
	{
		return dm.density;
	}

	/**
	 * Dip转换为px
	 * */
	public static float dp2px(float dpValue)
	{
		return dpValue * dm.density + 0.5f;
	}

	/**
	 * px转换为Dip
	 * */
	public static float px2dp(float pxValue)
	{
		return pxValue / dm.density + 0.5f;
	}
	
	public static int getStatusHeight() {
		 
	    int statusHeight = -1;
	    try {
	        Class<?> clazz = Class.forName("com.android.internal.R$dimen");
	        Object object = clazz.newInstance();
	        int height = Integer.parseInt(clazz.getField("status_bar_height")
	                .get(object).toString());
	        statusHeight = mContext.getResources().getDimensionPixelSize(height);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return statusHeight;
	}
}
