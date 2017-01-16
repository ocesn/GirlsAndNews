package com.chinamade.hall.grilsandnews.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.List;
import java.util.UUID;

public class StringUtils {

    /**MD5密匙*/
    public static String MD5_KEY = "%032xxnMGJ";

    /**
	 * 判断字符串是否为空
	 * */
    public static Boolean isNullOrEmpty(String target) {
        if (target == null || "".equals(target))
            return true;

        for (int i = 0; i < target.length(); i++) {
            char c = target.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
	}
	
	/**
	 * 把列表已制定字符隔开拼成字符串
	 * */
	public static String join(List<String> stringList, String symbol){
        if (stringList==null) {
            return null;
        }
        StringBuilder result=new StringBuilder();
        boolean flag=false;
        for (String string : stringList) {
            if (flag) {
                result.append(symbol);
            }else {
                flag=true;
            }
            result.append(string);
        }
        return result.toString();
    }

    public static String getMd5(String input) {
        String output = null;
        if (input != null && input.length() > 0)
            try {
                MessageDigest messagedigest = MessageDigest.getInstance("MD5");
                messagedigest.update(input.getBytes(), 0, input.length());
                output = String.format(MD5_KEY, new BigInteger(1,
                        messagedigest.digest()));
            } catch (Exception exception) {
            }
        return output;
    }

    public static String getUUID() {
        String str = UUID.randomUUID().toString();
        return str;
    }

    public static String removeEnterAndSpace(String targetString) {
    	
    	if(isNullOrEmpty(targetString)) {
    		return "";
    	}
    	targetString = targetString.trim();
    	targetString = targetString.replaceAll("(\r\n|\r|\n|\n\r)", "");
    	return targetString;
    }

}
