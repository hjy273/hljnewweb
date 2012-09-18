package com.cabletech.commons.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;

public class StringUtils {
	/**
	 * List 转换为 String <br>
	 * 转换的string 类型主要用于sql中。
	 * @param strList
	 * @return
	 */
	public static String listStr4Sql(List<String> strList) {
		String listString = strList.toString();
		listString = listString.replace('[', '\'');
		listString = listString.replaceAll(", ", "','");
		listString = listString.replace(']', '\'');
		return listString;
	}
	public static String map2StrSql(Map<String,String> strmap){
		String mapstring = strmap.toString();
		mapstring = mapstring.replace('{', '\'');
		mapstring = mapstring.replaceAll(", ", "','");
		mapstring = mapstring.replaceAll("=", "','");
		mapstring = mapstring.replace('}', '\'');
		return mapstring+",''";
	}
	
	public static String array2String(String [] array){
		return list2StringComma(Arrays.asList(array));
		
	}
	 /**
     * 将数值型字符串转换成Integer型
     *
     * @since 1.0
     * @param str
     *            需要转换的字符型字符串
     * @param ret
     *            转换失败时返回的值
     * @return 成功则返回转换后的Integer型值；失败则返回ret
     */
    public static Integer String2Integer(String str, Integer ret) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return ret;
        }
    }
    /**
     * 将list数组转换为以逗号分隔的字符串
     * 例如：sohu,sina,google,baidu,
     * @param list<String>
     * @return
     */
    public static String list2StringComma(List<String> list) {
		return list2String(list,",");
	}
    /**
     * 将list以#（pound）进行分隔
     * 例如：sohu#sina#google#baidu#
     * @param list
     * @return
     */
    public static String list2StringPound(List<String> list) {
		return list2String(list,"#");
	}
    /**
     * 将数组切割成以指定分隔符分隔的字符串
     * @param list  数组
     * @param separator 分隔符
     * @return
     */
    public static String list2String(List<String> list,String separator){
    	StringBuilder strs = new StringBuilder();
    	int i=1;
    	int size = list.size();
    	for (String str : list) {
			strs.append(str);
			if(i<size){
				strs.append(separator);
			}
			i++;
		}
		return strs.toString();
    }
    /**
     * 将以指定分隔符分隔的字符串，重组为数组
     * @param str  以指定分隔符分隔的字符串
     * @param separator 分隔符
     * @return
     */
    public static List<String> string2List(String str,String separator){
    	List<String> list = new ArrayList<String>();
    	if(str != null){
	    	StringTokenizer st = new StringTokenizer(str, separator);
			while (st.hasMoreTokens()) {
				list.add(st.nextToken());
			}
    	}
		return list;
    	
    }
    /**
     * 将以#号分隔的字符串，重组成数组
     * @param str
     * @return
     */
	public static List string2ListPound(String str) {
		return StringUtils.string2List(str, "#");
	}
	
	public static String getCharacterAndNumber(int length) {
		String val = "";
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; // 输出字母还是数字

			if ("char".equalsIgnoreCase(charOrNum)){ // 字符串
				int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; //取得大写字母还是小写字母
				val += (char) (choice + random.nextInt(26));
			} else if ("num".equalsIgnoreCase(charOrNum)){ // 数字
				val += String.valueOf(random.nextInt(10));
			}
		}

		return val;
	}
	
	public static void main(String []args){
		ArrayList a = new ArrayList();
		a.add("sohu");
		a.add("sina");
		a.add("google");
		a.add("baidu");
		System.out.println(StringUtils.listStr4Sql(a));
		System.out.println(StringUtils.list2StringComma(a));
		System.out.println(StringUtils.list2StringPound(a));
		System.out.println(StringUtils.getCharacterAndNumber(6));
		String s = "'sohu','sina'";
		List list = StringUtils.string2List(s,",");
		System.out.println("size :"+list.size());
		List templist = new ArrayList();
		StringTokenizer st = new StringTokenizer(s, ",");
		while (st.hasMoreTokens()) {
			templist.add(st.nextToken());
			 System.out.println("1");
		}
		System.out.println(templist.size());
		
		
		Map map = new HashMap();
		map.put("1", "直埋");
		map.put("2","架空");
		map.put("3","管道");
		System.out.println("map:"+map.toString());
		System.out.println("map:"+StringUtils.map2StrSql(map));
		List alllist = new ArrayList();
		//alllist.add("'163'");
		alllist.addAll(list);
		System.out.println(alllist.toString());
	}
	
}
