package com.cabletech.commons.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BasicDynaBean;

public class Conversion {
	/**
	 * ��listת��Ϊmap��list�б����Ǻ���BasicDynaBean�Ķ�̬����
	 * @param original ��Ҫת���Ķ�̬����
	 * @param field1 ����Ϊmap��keyֵ
	 * @param field1 ����Ϊmap��valueֵ
	 * @return map 
	 */
	public static Map listToMap(List original,String field1,String field2){
		if(original == null){
			return null;
		}
		int size = original.size();
		Map map = new HashMap();
		for (int i = 0; i < size; i++) {
			BasicDynaBean bean = (BasicDynaBean) original.get(i);
			int value = Integer.parseInt(bean.get(field2).toString());
			map.put(bean.get(field1), new Integer(value));
		}
		return map;
		
	}
	
}
