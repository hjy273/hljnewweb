package com.cabletech.commons.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cabletech.menu.domainobjects.FirstMenu;

public class CollectionUtil {
	@SuppressWarnings("unused")
	public static Map<String,Object> array2Map(List<Object> v) {
		FirstMenu menu = null;
		Map<String,Object> firstMenuMap = new HashMap<String,Object>();
		for (int i = 0; i < v.size(); i++) {
			menu = (FirstMenu) v.get(i);
			firstMenuMap.put(menu.getId(), menu);
		}
		return firstMenuMap;
	}
}
