package com.bozy.cloud.utils;

import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil extends StringUtils {
	private static final String TIME_FORMAT_SHORT = "yyyyMMddHHmmss";
	private static final String TIME_FORMAT_NORMAL = "yyyy-MM-dd HH:mm:ss";
	private static final String TIME_FORMAT_ENGLISH = "MM/dd/yyyy HH:mm:ss";
	private static final String TIME_FORMAT_CHINA = "yyyy年MM月dd日 HH时mm分ss秒";

	private static final String DATE_FORMAT_SHORT = "yyyyMMdd";
	private static final String DATE_FORMAT_NORMAL = "yyyy-MM-dd";
	private static final String DATE_FORMAT_ENGLISH = "MM/dd/yyyy";
	private static final String DATE_FORMAT_CHINA = "yyyy年MM月dd日";

	/**
	 * 判断给定的字符串是否为空,以及空字符串
	 * 
	 * @param input
	 *            输入字符串
	 * @return 是否为空,空则返回true，反之亦反
	 * @since 0.1
	 */
	public static boolean blank(String input) {
		return input == null || "".equals(input) || input.length() == 0
				|| input.trim().length() == 0;
	}

	/**
	 * 判断给定的字符串不为空
	 * 
	 * @param input
	 *            输入字符串
	 * @return 是否不为空，不为空返回true，反之亦反.
	 * @since 0.1
	 */
	public static boolean notBlank(String input) {
		return !blank(input);
	}

	/**
	 * 求字符串中某个子串的位置（自字符串orig的i位开始将orig按组长度len分隔为若干段，求第一个indexOf(段,sub)=0
	 * 的段首字符出现的位置）
	 * 
	 * @param orig
	 *            原字符串
	 * @param sub
	 *            查找的子串
	 * @param len
	 *            每组长度
	 * @param i
	 *            开始查找位置
	 * @return
	 * @since 0.1
	 */
	public static int indexOf(String orig, String sub, int len, int i) {
		if (orig == null)
			return -1;
		int idx = orig.indexOf(sub, i);
		if (idx == -1)
			return idx;
		if (idx % len == 0)
			return idx;
		i = (idx / len + 1) * len;
		int tmp = -1;
		if ((tmp = indexOf(orig, sub, len, i)) > -1) {
			return tmp;
		} else {
			return -1;
		}
	}

	/**
	 * 求字符串中某个子串的位置（将字符串按组长度len分隔为若干段，求第一个indexOf(段,sub)=0的段首字符出现的位置）
	 * 
	 * @param orig
	 *            原字符串
	 * @param sub
	 *            查找的子串
	 * @param len
	 *            每组长度
	 * @return
	 * @since 0.1
	 */
	public static int indexOf(String orig, String sub, int len) {
		return indexOf(orig, sub, len, 0);
	}

	/**
	 * 求字符串中某个子串的位置（将字符串按子串长度分隔为若干段，求第一个同子串相同的段 首字符出现的位置）
	 * 
	 * @param orig
	 *            原字符串
	 * @param sub
	 *            查找的子串
	 * @return
	 * @since 0.1
	 */
	public static int indexOf(String orig, String sub) {
		return indexOf(orig, sub, sub.length(), 0);
	}

	/**
	 * 截取字符串
	 * 
	 * @param orig
	 *            源字符串
	 * @param length
	 *            字符串长度
	 * @return
	 * @since 0.1
	 */
	public static String substr(String orig, int length) {
		if (orig == null)
			return "";
		if (orig.length() <= length)
			return orig;
		return orig.substring(0, length - 1) + "...";
	}

	/**
	 * 首字母大写
	 * 
	 * @param input
	 *            输入字符串
	 * @return
	 * @since 0.1
	 */
	public static String toFirstUpperCase(String input) {
		return StringUtil.blank(input) ? input : input.substring(0, 1)
				.toUpperCase()
				+ input.substring(1);
	}

	/**
	 * 填充字符串到固定长度
	 * 
	 * @param orig
	 *            源字符串
	 * @param num
	 *            填充后字符串长度几位
	 * @param fillCharacter
	 *            要填充的字符
	 * @param location
	 *            位置 true:后 false:前
	 * @return
	 */
	public static String fillCharacter(String orig, int num,
                                       String fillCharacter, boolean location) {
		if (orig == null)
			return null;
		if (orig.length() >= num)
			return orig;
		StringBuffer sb = new StringBuffer("");
		for (int i = 0;; i++) {
			if (orig.length() + i * fillCharacter.length() >= num) {
				break;
			}
			sb.append(fillCharacter);
		}
		if (location) {
			orig = orig + sb.substring(0, num - orig.length());
		} else {
			orig = sb.substring(0, num - orig.length()) + orig;
		}
		return orig;
	}

	/**
	 * 字符转换方法
	 * 
	 * @param source
	 *            原字符串
	 * @param clazz
	 *            转换类型
	 * @return
	 * @throws ParseException
	 */
	public static Object convert(String orig, Class<?> clazz) {
		if (orig == null) {
			return null;
		}
		if (clazz == String.class) {
			return orig;
		}
		if (clazz == short.class) {
			return Short.parseShort(orig);
		}
		if (clazz == Short.class) {
			return new Short(orig);
		}
		if (clazz == int.class) {
			return Integer.parseInt(orig);
		}
		if (clazz == Integer.class) {
			return new Integer(orig);
		}
		if (clazz == long.class) {
			return Long.parseLong(orig);
		}
		if (clazz == Long.class) {
			return new Long(orig);
		}
		if (clazz == float.class) {
			return Float.parseFloat(orig);
		}
		if (clazz == Float.class) {
			return new Float(orig);
		}
		if (clazz == double.class) {
			return Double.parseDouble(orig);
		}
		if (clazz == Double.class) {
			return new Double(orig);
		}

		if (orig.equalsIgnoreCase("t") || orig.equalsIgnoreCase("ture")
				|| orig.equalsIgnoreCase("y") || orig.equalsIgnoreCase("yes")) {
			if (clazz == boolean.class) {
				return true;
			}
			if (clazz == Boolean.class) {
				return Boolean.TRUE;
			}
		} else {
			if (clazz == boolean.class) {
				return false;
			}
			if (clazz == Boolean.class) {
				return Boolean.FALSE;
			}
		}

		try {
			if (clazz == java.util.Date.class) {
				DateFormat fmt = null;
				if (orig.matches("\\d{14}")) {
					fmt = new SimpleDateFormat(TIME_FORMAT_SHORT);
				} else if (orig
						.matches("\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}")) {
					fmt = new SimpleDateFormat(TIME_FORMAT_NORMAL);
				} else if (orig
						.matches("\\d{1,2}/\\d{1,2}/\\d{4} \\d{1,2}:\\d{1,2}:\\d{1,2}")) {
					fmt = new SimpleDateFormat(TIME_FORMAT_ENGLISH);
				} else if (orig
						.matches("\\d{4}年\\d{1,2}月\\d{1,2}日 \\d{1,2}时\\d{1,2}分\\d{1,2}秒")) {
					fmt = new SimpleDateFormat(TIME_FORMAT_CHINA);
				} else if (orig.matches("\\d{8}")) {
					fmt = new SimpleDateFormat(DATE_FORMAT_SHORT);
				} else if (orig.matches("\\d{4}-\\d{1,2}-\\d{1,2}")) {
					fmt = new SimpleDateFormat(DATE_FORMAT_NORMAL);
				} else if (orig.matches("\\d{1,2}/\\d{1,2}/\\d{4}")) {
					fmt = new SimpleDateFormat(DATE_FORMAT_ENGLISH);
				} else if (orig.matches("\\d{4}年\\d{1,2}月\\d{1,2}日")) {
					fmt = new SimpleDateFormat(DATE_FORMAT_CHINA);
				} else{
					fmt = new SimpleDateFormat(DATE_FORMAT_NORMAL);
				}
				return fmt.parse(orig);
			}
			if (clazz == java.util.Calendar.class) {
				Calendar cal = Calendar.getInstance();
				DateFormat fmt = null;
				if (orig.matches("\\d{14}")) {
					fmt = new SimpleDateFormat(TIME_FORMAT_SHORT);
				} else if (orig
						.matches("\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}")) {
					fmt = new SimpleDateFormat(TIME_FORMAT_NORMAL);
				} else if (orig
						.matches("\\d{1,2}/\\d{1,2}/\\d{4} \\d{1,2}:\\d{1,2}:\\d{1,2}")) {
					fmt = new SimpleDateFormat(TIME_FORMAT_ENGLISH);
				} else if (orig
						.matches("\\d{4}年\\d{1,2}月\\d{1,2}日 \\d{1,2}时\\d{1,2}分\\d{1,2}秒")) {
					fmt = new SimpleDateFormat(TIME_FORMAT_CHINA);
				} else if (orig.matches("\\d{8}")) {
					fmt = new SimpleDateFormat(DATE_FORMAT_SHORT);
				} else if (orig.matches("\\d{4}-\\d{1,2}-\\d{1,2}")) {
					fmt = new SimpleDateFormat(DATE_FORMAT_NORMAL);
				} else if (orig.matches("\\d{1,2}/\\d{1,2}/\\d{4}")) {
					fmt = new SimpleDateFormat(DATE_FORMAT_ENGLISH);
				} else if (orig.matches("\\d{4}年\\d{1,2}月\\d{1,2}日")) {
					fmt = new SimpleDateFormat(DATE_FORMAT_CHINA);
				} else {
					fmt = new SimpleDateFormat(DATE_FORMAT_NORMAL);
				}
				cal.setTime(fmt.parse(orig));
				return cal;
			}
		} catch (ParseException e) {
			throw new IllegalArgumentException("字符串不能转换为" + clazz.getName()
					+ "类型.");
		}
		throw new IllegalArgumentException("字符串不能转换为" + clazz.getName() + "类型.");
	}

	/**
	 * 字符转换方法
	 *
	 * @param source
	 *            原字符串
	 * @param clazz
	 *            转换类型
	 * @return
	 * @throws ParseException
	 */
	public static String convert(Object orig) {
		if (orig == null) {
			return null;
		}
		if (orig instanceof String) {
			return (String) orig;
		}
		if (orig instanceof Short) {
			return Short.toString((Short) orig);
		}
		if (orig instanceof Integer) {
			return Integer.toString((Integer) orig);
		}
		if (orig instanceof Long) {
			return Long.toString((Long) orig);
		}
		if (orig instanceof Float) {
			return Float.toString((Float) orig);
		}
		if (orig instanceof Double) {
			return Double.toString((Double) orig);
		}
		if (orig instanceof Boolean) {
			return Boolean.toString((Boolean) orig);
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		if (orig instanceof java.util.Date) {
			return format.format((java.util.Date) orig);
		}
		if (orig instanceof java.sql.Date) {
			return format.format((java.sql.Date) orig);
		}
		if (orig instanceof java.util.Calendar) {
			return format.format(((java.util.Calendar) orig).getTime());
		}
		throw new java.lang.IllegalArgumentException("参数类型不支持.");
	}

	/**
	 * 从map字符串获取value值
	 * 
	 * @param orig
	 *            字符串 例:a=1&b=2&c=3
	 * @param param
	 *            key 例:a
	 * @param split
	 *            例:&
	 * @return 例:1
	 * @since 0.1
	 */
	public static String[] getValueFromString(String orig, String param,
                                              String split) {
		String[] result = new String[] {};
		if (StringUtils.isBlank(orig)) {
			return result;
		}
		List<String> list = new ArrayList<String>();
		String[] values = orig.split(split);
		for (String tmp : values) {
			String key = StringUtils.substringBefore(tmp, "=");
			String value = StringUtils.substringAfter(tmp, "=");
			if (param.equals(key)) {
				list.add(value);
			}
		}
		return list.toArray(result);
	}

	/**
	 * 
	 * @param s
	 * @return
	 */
	public static String null2String(String s) {
		return s == null ? "" : s;
	}

	/**
	 * 
	 * @param s
	 * @param s1
	 * @return
	 */
	public static String null2String(String s, String s1) {
		return s == null ? s1 : s;
	}

	/**
	 * 反射根据Method获得类名、方法名
	 * 
	 * @param method
	 * @return
	 */
	public static String methodToClass(Method method) {
		String methodName = method.getName();
		String className = method.getDeclaringClass().getName();
		Class<?>[] typeNames = method.getParameterTypes();
		StringBuffer buffer = new StringBuffer();
		for (Class<?> type : typeNames) {
			buffer.append(type.getName()).append(",");
		}
		buffer.deleteCharAt(buffer.length() - 1);
		return className + "." + methodName + "(" + buffer.toString() + ")";
	}

	/**
	 * 根据起始时间、类型、间隔算出结束时间
	 * 
	 * @param startDate
	 * @param type
	 * @param interval
	 * @return
	 */
	public static String getEndDate(String startDate, String type, int interval) {
		if (StringUtil.isBlank(startDate) || StringUtil.isBlank(type))
			return null;
		Calendar c1 = Calendar.getInstance(Locale.CHINA);
		SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT_NORMAL);
		try {
			Date d1 = format.parse(startDate);
			c1.setTime(d1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if ("1".equals(type)) {// 间隔为天
			c1.add(Calendar.DAY_OF_MONTH, interval);
		} else if ("2".equals(type)) {// 间隔为整月
			c1.add(Calendar.MONTH, interval);
		} else if ("3".equals(type)) {// 间隔为自然月
			c1.set(Calendar.DAY_OF_MONTH, 1);
			c1.add(Calendar.MONTH, interval);
			c1.add(Calendar.DAY_OF_MONTH, -1);
		}
		return format.format(c1.getTime());
	}
	/**
	 * 
	 * Description: 字符串小写转大写
	 *
	 * @param normal
	 * @return
	 * @Author yubin
	 * @Create Date: 2013-9-3 下午02:11:23
	 */
	public static String cnToNumber(String normal) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("一", 1);
		map.put("二", 2);
		map.put("三", 3);
		map.put("四", 4);
		map.put("五", 5);
		map.put("六", 6);
		map.put("七", 7);
		map.put("八", 8);
		map.put("九", 9);
		map.put("十", 10);
		char[] charArray = normal.toCharArray();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < charArray.length; i++) {
			char t = normal.charAt(i);
			if (map.containsKey(String.valueOf(t))) {
				sb.append(map.get(String.valueOf(t)));
			} else {
				sb.append(String.valueOf(t));
			}
		}
		return sb.toString();
	}

	/**
	 * 
	 * Description: 过滤js标签，防止js注入漏洞
	 * 
	 * @param str
	 * @return
	 * @Author yubin Create Date: Dec 21, 2011 5:55:54 PM
	 */
	public static String replaceJsTag(String str) {
		if(blank(str)){
			return "";
		}
		str = str.replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll(
				"\\(", "（").replaceAll("\\)", "）").replaceAll("eval\\((.*)\\)",
				"").replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']",
				"\"\"");
		return str;
	}

	/**
	 * 
	 * Description: BigDecimal转为字符串
	 * 
	 * @param dec
	 * @return
	 * @Author yubin Create Date: Jan 11, 2012 1:44:46 PM
	 */
	public static String bigDecimalToString(BigDecimal dec) {
		String str = "";
		if (dec != null) {
			str = dec.toString();
		}
		return str;
	}
	/**
	 * 
	 * Description: 判断输入字符串是否是数字
	 *
	 * @param str
	 * @return
	 * @Author yubin
	 * @Create Date: 2013-7-15 下午01:52:07
	 */
	public static boolean isNumeric(String str) {
		if(blank(str)){
			return false;
		}
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}
	
	/**
	 * 
	 * 判断是否包含空格 包含返回true,否则返回false
	 * Description: 
	 * 1).null 返回false
	 * 2).包含中英文空格或者Tab制表符 返回true--[　][	][ ]	
	 * @param value
	 * @return
	 * @Author ouyangjin
	 * @Create Date: 2013-7-27 下午02:36:55
	 */
	public static boolean isContainSpace(String value){
		if(value == null ){
			return false; 
		}
		if(value.indexOf("　")>-1 || value.indexOf("	")>-1 || value.indexOf(" ")>-1){
			return true;
		}
		return false; 
	}
	
	/**
	 * 验证用户隐藏手机信息
	 * @param mobile
	 * @return
	 */
	public static String getHideMobile(String mobile){
		if(mobile == null || mobile.length() < 11) return mobile;
		StringBuffer sb = new StringBuffer();
		sb.append(mobile.substring(0, 3) + "*****" + mobile.substring(8,11));
		return sb.toString();
	}
	
	/**
	 * 隐藏邮箱信息
	 * @param email
	 * @return
	 */
	public static String getHideEmail(String email, int showPoint){
		if(email == null || email.indexOf("@") == -1 || email.indexOf(".") == -1) return email;
		StringBuffer sb = new StringBuffer();
		String indexStr = email.substring(0, showPoint);
		String endStr = email.substring(email.indexOf("@"), email.length());
		String replaceStr = email.substring(showPoint , email.indexOf("@"));
		sb.append(indexStr);
		for(int i=0;i<replaceStr.length();i++){
			sb.append("*");
		}
		sb.append(endStr);
		return sb.toString();
	}
	/**
	 * 隐藏邮箱信息
	 * @param email
	 * @return
	 */
	public static String getHideEmail(String email){
		if(email == null || email.trim().length() <= 0) return email;
		StringBuffer sb = new StringBuffer();
		String head = email.substring(0, email.indexOf("@"));
		if(head.length() >= 3){
			int i = 0;
			String charStr = "";
			while(i < email.substring(1, email.indexOf("@")-1).length()){
				charStr += "*";
				i++;
			}
			sb.append(email.charAt(0) + charStr +email.charAt(email.indexOf("@")-1));
		}else if(head.length() == 2){
			sb.append(email.charAt(0) + "*");
		}else{
			sb.append("*");
		}
		sb.append(email.substring(email.indexOf("@"), email.length()));
		return sb.toString();
	}
	
	/**
	 * 
	 * Description: 返回隐藏姓名
	 *
	 * @return String
	 * @Author lanzhongliang
	 * @Create 2013-8-21 下午01:36:13
	 */
	public static String getHideRealName(String realName){
		if(isBlank(realName)){
			return realName;
		}
		StringBuffer sb = new StringBuffer();
		for(int i = 0;i<realName.length();i++){
			sb.append((i==0||(i==realName.length()-1&&realName.length()>2))?realName.charAt(i):"*");
		}
		return sb.toString();
	}
	
	/**
	 * Description: 只显示名称的第一位,其余都用*代替;
	 *
	 * @param realName
	 * @return
	 * @Author tanyuming
	 * @Create Date: 2015年3月31日 下午2:52:11
	 */
	public static String getHideRealNameSuffix(String realName){
		if(isBlank(realName)){
			return realName;
		}
		StringBuffer sb = new StringBuffer();
		for(int i = 0;i<realName.length();i++){
			sb.append(i==0?realName.charAt(i):"*");
		}
		return sb.toString();
	}
	
	/**
	 * 
	 * Description: 返回隐藏身份证号
	 *
	 * @return String
	 * @Author lanzhongliang
	 * @Create 2013-8-21 下午01:37:15
	 */
	public static String getHideIdCardNo(String idCardNo, int indexStart, int indexEnd){
		if(isBlank(idCardNo)){
			return idCardNo;
		}
		StringBuffer sb = new StringBuffer();
		if (indexStart < 0 || indexEnd < 0 || indexStart > idCardNo.length() - 1 || indexEnd > idCardNo.length() - 1) {
			return idCardNo;
		}
		for(int i = 0;i<idCardNo.length();i++){
			sb.append(i>=indexStart&&i<indexEnd?"*":idCardNo.charAt(i));
		}
		return sb.toString();
	}
	
	/**
	 * 
	 * Description: 返回隐藏银行卡号
	 *
	 * @return String
	 * @author JiangLong Cui
	 */
	public static String getHideBankCardNo(String bankCard){
		if(isBlank(bankCard)){
			return bankCard;
		}
		StringBuffer sb = new StringBuffer();
		if(16 == bankCard.length()){
			for(int i = 0;i<bankCard.length();i++){
				sb.append(i >=6 && i < 12 ? "*" : bankCard.charAt(i));
			}
		}
		if(19 == bankCard.length()){
			for(int i = 0;i<bankCard.length();i++){
				sb.append(i >=6 && i < 15 ? "*" : bankCard.charAt(i));
			}
		}
		return sb.toString();
	}
	
	public static String getHideBankCardNoEnd4(Object bankCard){
		if(bankCard==null) {
			return "";
		}
		if(isBlank(bankCard.toString())){
			return bankCard.toString();
		}
		StringBuffer sb = new StringBuffer();
		for(int i = 0;i<bankCard.toString().length();i++){
			if(i%4==0 && i!=0 && i!=(bankCard.toString().length()-1)) {
				sb.append(" ");
			}
			sb.append(i < bankCard.toString().length()-4 ? "*" : bankCard.toString().charAt(i));
		}
		return sb.toString();
	}
	
	/**
	 * Description: 取得微信端信用卡展示字符 
	 *
	 * @param strCreditCardNo 信用卡卡号
	 * @return 处理后的展示字符
	 * @Author JungLone
	 * @Create Date: 2015年5月13日 下午3:00:48
	 */
	public static String getHiddenCreditCardNo(String strCreditCardNo) {
		if(isBlank(strCreditCardNo)){
			return strCreditCardNo;
		}
		// 截取后7位
		String strSub = strCreditCardNo.substring(strCreditCardNo.length() - 4 , strCreditCardNo.length());
		return strSub;
	}
	
	/**
	 * 
	 * Description: 返回银行卡号后四位
	 *
	 * @param bankCard
	 * @return String
	 * @author JiangLong Cui
	 */
	public static String getLastBankCardNo(String bankCard){
		String lastBankCardNo = null;
		if(isBlank(bankCard)){
			return bankCard;
		}
		lastBankCardNo = bankCard.substring((bankCard.length()-4), bankCard.length());
		return lastBankCardNo;
	}
	
	public static String deleteEndString(String str, String suffix) {
		if(StringUtil.isBlank(str)) {
			return null;
		}
		if(StringUtil.isBlank(suffix)) {
			return str;
		}
		if(str.endsWith(suffix)) {
			return str.substring(0, str.length()-suffix.length());
		}
		return str;
	}
	
	public static boolean isBlank(String str) {
		if(blank(str)) {
			return true;
		}
		if(str.equals("null")) {
			return true;
		}
		return false;
	}
	
	/**
	 * Description: 显示前 length1位 + codeNum个*号  + 显示后length2位
	 *
	 * @param length1 前N1个字符
	 * @param length2 后N2个字符
	 * @param codeNum 中间N个*号
	 * @param replaceCode
	 * @return
	 * @Author tanyuming
	 * @Create Date: 2015年4月28日 下午12:27:56
	 */
	public static String getHideMiddleChar(int length1, int length2, int codeNum, String replaceCode, String srcCode){
		if(isBlank(srcCode)){
			return srcCode;
		}
		StringBuffer sb = new StringBuffer(srcCode.substring(0, length1));
		for(int i = 0; i<codeNum; i++){
			sb.append(replaceCode);
		}
		sb.append(srcCode.substring(srcCode.length() - length2));
		return sb.toString();
	}
	
	/**
	 * Description: 移除掉一串数字前面的0字符
	 *
	 * @param srcStr
	 * @return
	 * @Author tanyuming
	 * @Create Date: 2015年10月27日 下午12:15:44
	 */
	public static String removeZeroBeforeStr(String srcStr){
		if(StringUtils.isNotBlank(srcStr) && srcStr.length() > 1){
			return srcStr.replaceFirst("^0*", "");
//			return srcStr.replaceAll("^(0+)", "");
		}
		return srcStr;
	}

	/**
	 * Description: 显示银行卡的前6后4为数字
	 * @Author tym
	 * @Create Date: 2018/6/29/0029 下午 4:49
	 * @param bankCard
	 * @return
     */
	public static String getHideBankCardMiddleChar(Object bankCard){
		if(bankCard==null) {
			return "";
		}
		if(isBlank(bankCard.toString())){
			return bankCard.toString();
		}
		StringBuffer sb = new StringBuffer();
		for(int i = 0;i<bankCard.toString().length();i++){
			if(i%4==0 && i!=0 && i!=(bankCard.toString().length()-1)) {
				sb.append(" ");
			}
			if(i < 6){
				sb.append(bankCard.toString().charAt(i));
				continue;
			}
			sb.append(i < bankCard.toString().length()-4 ? "*" : bankCard.toString().charAt(i));
		}
		return sb.toString();
	}

	public static void main(String arg[]) {
		
		String strCreditCardNo = "1234567890123456";
		System.out.println(StringUtil.getHiddenCreditCardNo(strCreditCardNo));

		// System.out.println(getEndDate("2010-2-15 12:00:00", "1", 15));
		//System.out.println(getEndDate("2010-12-15 12:00:00", "3", 1));
		// System.out.println(getEndDate("2010-01-20 12:00:00", "3", 3));
		// System.out.println(getEndDate("2011-02-16 14:14:13", "3", 3));
		// System.out.println(getEndDate("2011-02-16 14:14:13", "3", 6));
		// List list = new ArrayList();
		// list.add("投资理财三十五部");
		// list.add("投资理财一部");
		// list.add("投资理财二部");
		// list.add("投资理财十一部");
		// list.add("投资理财十二部");
		// list.add("投资理财十部");
		// list.add("投资理财三部");
		// list.add("星火部门");
		// List rList = new ArrayList(list);
		// Collections.sort(rList, new CnStringComparator());
		// System.out.println(rList);
		//System.out.println(getHideRealName(""));
		//System.out.println(getHideRealName("张"));
		//System.out.println(getHideRealName("张三"));
		//System.out.println(getHideRealName("张小三"));
		//System.out.println(getHideRealName("张小大三"));
//		System.out.println(getHideIdCardNo("132456789012345678",6,14));
//		System.out.println(getHideIdCardNo("132456789012345",6,14));
//		System.out.println(getHideBankCardNo("1234567890123456"));
//		System.out.println(getHideBankCardNo("1234567890123456789"));
//		System.out.println(getLastBankCardNo("1234567890123456"));
//		System.out.println(getLastBankCardNo("1234567890123456789"));
//		System.out.println(getBidSuccessSMSContent("73"));
//		System.out.println(getRepaymentSuccessSMSContent("zhangsan", "87.54", "73"));
//		System.out.println(convert("2013-01-10",Date.class));
		System.out.println(getHideMiddleChar(2,2,2,"#","李彦.完颜洪烈.情格"));
		System.out.println(getHideMiddleChar(3,4,4,"*","18310364698"));

	}
}
