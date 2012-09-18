package com.cabletech.commons.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonUtil {
	public static JSONObject createLeafObject(String id, String lableName, boolean flag) {
		JSONObject jo = new JSONObject();
		jo.put("id", id);
		jo.put("text", lableName);
		jo.put("checked", flag);
		return jo;
	}

	public static JSONObject createBranchObject(String id, String lableName, JSONArray children) {
		JSONObject jo = new JSONObject();
		jo.put("id", id);
		if (lableName != null) {
			jo.put("text", lableName);
		}
		jo.put("item", children);
		return jo;
	}

}
