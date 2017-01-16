package com.chinamade.hall.grilsandnews.utils;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Log统一管理类
 * 
 * @author way
 * 
 */
public class L {
	public static boolean isDebug = true;// 是否需要打印bug，可以在application的onCreate函数里面初始化
	private static final String TAG = "way";
	private static L inst;
	
	public static synchronized L getLog() {
		if (inst == null) {
			inst = new L();
		}

		return inst;
	}
	
	// 下面四个是默认tag的函数
	public static void i(String msg) {
		if (isDebug)
			Log.i(TAG, msg);
	}

	public static void d(String msg) {
		if (isDebug)
			Log.d(TAG, msg);
	}

	public static void e(String msg) {
		if (isDebug)
			Log.e(TAG, msg);
	}

	public static void v(String msg) {
		if (isDebug)
			Log.v(TAG, msg);
	}
	
	
	//下面是传入类名打印log
	public static void i(Class<?> _class, String msg){
		if (isDebug)
			Log.i(_class.getName(), msg);
	}
	
	public static void d(Class<?> _class, String msg){
		if (isDebug)
			Log.i(_class.getName(), msg);
	}
	
	public static void e(Class<?> _class, String msg){
		if (isDebug)
			Log.i(_class.getName(), msg);
	}
	
	public static void v(Class<?> _class, String msg){
		if (isDebug)
			Log.i(_class.getName(), msg);
	}
	
	
	// 下面是传入自定义tag的函数
	public static void i(String tag, String msg) {
		if (isDebug)
			Log.i(tag, msg);
	}

	public static void d(String tag, String msg) {
		if (isDebug)
			Log.i(tag, msg);
	}

	public static void e(String tag, String msg) {
		if (isDebug)
			Log.i(tag, msg);
	}

	public static void v(String tag, String msg) {
		if (isDebug)
			Log.i(tag, msg);
	}
	
	
	// 下面是默认tag日志信息自定义转化的函数
	/**
	 * log.i
	 */
	public void i(String format, Object... args) {
		if (isDebug){
			String message = createMessage(getInputString(format, args));
			Log.i(TAG, message);
		}
			
	}

	/**
	 * log.v
	 */
	public void v(String format, Object... args) {
		if (isDebug){
			String message = createMessage(getInputString(format, args));
			Log.v(TAG, message);
		}
	}

	/**
	 * log.d
	 */
	public void d(String format, Object... args) {
		if (isDebug){
			String message = createMessage(getInputString(format, args));
			Log.d(TAG, message);
		}
	}

	/**
	 * log.e
	 */
	public void e(String format, Object... args) {
		if (isDebug){
			String message = createMessage(getInputString(format, args));
			Log.e(TAG, message);
		}
	}
	private static String getInputString(String format, Object... args) {
		if (format == null) {
			return "null log format";
		}

		return String.format(format, args);
	}

	/**
	 * log.error
	 */
	public void error(Exception e) {
		if (isDebug){
			StringBuffer sb = new StringBuffer();
			String name = getFunctionName();
			StackTraceElement[] sts = e.getStackTrace();

			if (name != null) {
				sb.append(name + " - " + e + "\r\n");
			} else {
				sb.append(e + "\r\n");
			}
			if (sts != null && sts.length > 0) {
				for (StackTraceElement st : sts) {
					if (st != null) {
						sb.append("[ " + st.getFileName() + ":"
								+ st.getLineNumber() + " ]\r\n");
					}
				}
			}
			Log.e(TAG, sb.toString());
		}
	}
	/**
	 * log.d
	 */
	public void w(String format, Object... args) {
		if (isDebug){
			String message = createMessage(getInputString(format, args));
			Log.w(TAG, message);
		}
	}


	private String createMessage(String msg) {
		String functionName = getFunctionName();
		long threadId = Thread.currentThread().getId();
		String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date());
		String message = (functionName == null ? msg : (functionName + " - "
				+ String.valueOf(threadId) + " - " + msg));
		return currentTime + " - " + message;
	}

	private String getFunctionName() {
		StackTraceElement[] sts = Thread.currentThread().getStackTrace();

		if (sts == null) {
			return null;
		}

		for (StackTraceElement st : sts) {
			if (st.isNativeMethod()) {
				continue;
			}

			if (st.getClassName().equals(Thread.class.getName())) {
				continue;
			}

			if (st.getClassName().equals(this.getClass().getName())) {
				continue;
			}

			return "[" + st.getFileName() + ":" + st.getLineNumber() + "]";
		}

		return null;
	}

}
