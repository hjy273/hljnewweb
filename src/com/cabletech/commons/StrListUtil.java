package com.cabletech.commons;

import java.util.ArrayList;
import java.util.List;

/**
 * 负责字符串到数组
 * 数组到字符串的转换
 * @author zhj
 *
 */
public class StrListUtil {
	public static String listToString(List list) {
		String str = "";
		for (int i = 0; i < list.size(); i++) {
			str += (String) list.get(i) + "#";
		}
		return str;
	}

	public static List stringToList(String str) {
		String[] strArray = str.split("#");
		List list = new ArrayList();
		for (String element : strArray) {
			list.add(element);
		}
		return list;
	}

	public static void main(String[] args) {
		List list = new ArrayList();
		list.add("00001");
		list.add("00002");
		list.add("00003");
		StrListUtil stl = new StrListUtil();
		String s = StrListUtil.listToString(list);
		System.out.println("s:" + s);
		List slist = StrListUtil.stringToList(s);
		System.out.println(slist.size());

	}
}
