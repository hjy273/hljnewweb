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
	 * List ת��Ϊ String <br>
	 * ת����string ������Ҫ����sql�С�
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
     * ����ֵ���ַ���ת����Integer��
     *
     * @since 1.0
     * @param str
     *            ��Ҫת�����ַ����ַ���
     * @param ret
     *            ת��ʧ��ʱ���ص�ֵ
     * @return �ɹ��򷵻�ת�����Integer��ֵ��ʧ���򷵻�ret
     */
    public static Integer String2Integer(String str, Integer ret) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return ret;
        }
    }
    /**
     * ��list����ת��Ϊ�Զ��ŷָ����ַ���
     * ���磺sohu,sina,google,baidu,
     * @param list<String>
     * @return
     */
    public static String list2StringComma(List<String> list) {
		return list2String(list,",");
	}
    /**
     * ��list��#��pound�����зָ�
     * ���磺sohu#sina#google#baidu#
     * @param list
     * @return
     */
    public static String list2StringPound(List<String> list) {
		return list2String(list,"#");
	}
    /**
     * �������и����ָ���ָ����ָ����ַ���
     * @param list  ����
     * @param separator �ָ���
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
     * ����ָ���ָ����ָ����ַ���������Ϊ����
     * @param str  ��ָ���ָ����ָ����ַ���
     * @param separator �ָ���
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
     * ����#�ŷָ����ַ��������������
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
			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; // �����ĸ��������

			if ("char".equalsIgnoreCase(charOrNum)){ // �ַ���
				int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; //ȡ�ô�д��ĸ����Сд��ĸ
				val += (char) (choice + random.nextInt(26));
			} else if ("num".equalsIgnoreCase(charOrNum)){ // ����
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
		map.put("1", "ֱ��");
		map.put("2","�ܿ�");
		map.put("3","�ܵ�");
		System.out.println("map:"+map.toString());
		System.out.println("map:"+StringUtils.map2StrSql(map));
		List alllist = new ArrayList();
		//alllist.add("'163'");
		alllist.addAll(list);
		System.out.println(alllist.toString());
	}
	
}
