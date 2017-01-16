package com.chinamade.hall.grilsandnews.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VerificateUtils {
	
	/**
	 * 手机号码验证
	 * */
	public static boolean isMobileNO(String mobiles){
		String regExp = "^[1][\\d]{10}$";
		
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(mobiles);
		return m.matches();  

	}
	
		/**
	    * 判断邮编
	    * @param zipString
	    * @return
	    */
	   public static boolean isZipNO(String zipString){
	      String str = "^[1-9][0-9]{5}$";
	      return Pattern.compile(str).matcher(zipString).matches();
	   }
	   
	   /**
	    * 判断邮箱是否合法
	    * @param email
	    * @return
	    */
	   public static boolean isEmail(String email){
	     if (null==email || "".equals(email)) return false;	
	     //Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配  
	     Pattern p =  Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
	     Matcher m = p.matcher(email);
	     return m.matches();  
	   }
	   
	   
	   public static boolean isNickName(String targetString, boolean allowNull) {
		   if (StringUtils.isNullOrEmpty(targetString)) {
			   return allowNull;
		   }
		   Pattern pattern =  Pattern.compile("^[\\w\\d_]{2,12}$");
		   Matcher matcher = pattern.matcher(targetString);
		   return matcher.matches();
	   }
	   
	   public static boolean isUserSignature(String targetString) {
		   if(StringUtils.isNullOrEmpty(targetString)) {
			   return true;
		   }
		   return targetString.length() <= 20;
	   }
	   
	   public static boolean isPassword(String targetString) {
		   if (StringUtils.isNullOrEmpty(targetString) || targetString.length() < 6 || targetString.length() > 16) {
			   return false;
		   }
		   return true;
	   }
	   
	   public static boolean isVerifyCode(String targetString) {
		   if (StringUtils.isNullOrEmpty(targetString) || targetString.length() != 6 || !isNumeric(targetString)) {
			   return false;
		   }
		   return true;
	   }
	   
	   public static boolean isNumeric(String targetString) {
		   Pattern pattern = Pattern.compile("[0-9]*");
		   Matcher isNum = pattern.matcher(targetString);
		   if(!isNum.matches()) {
		       return false; 
		   } 
		   return true; 
	   }
	   
	   
}
