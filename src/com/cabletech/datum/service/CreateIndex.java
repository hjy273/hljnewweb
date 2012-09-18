package com.cabletech.datum.service;


import org.apache.commons.httpclient.NameValuePair;

import com.cabletech.commons.config.GisConInfo;
import com.cabletech.commons.util.BaseHttpClient;

public class CreateIndex extends BaseHttpClient {
	
	public CreateIndex(){
        //构造HttpClient的实例
		
	}
	
	public String createIndexClient(){
		  GisConInfo gisip = GisConInfo.newInstance();
		  //创建POST方法的实例
		  String url = "http://" + gisip.getSearchip() + ":" + gisip.getSearchport() + "/"+gisip.getSearchdir()+"/createindex.htm";
		  //String url = "http://" + gisip.getServerip() + ":" + gisip.getServerport() + "/WebApp/HttpServer";
		  // 填入表单域的值(pointID)
		  NameValuePair[] data = {
		      new NameValuePair("fullIndex", "true")};
//		  返回的结果为字符串,两个参数,以","隔开,第一个参数为sim卡号,第二个为巡检时间(时分秒)
		  String param = SendHttpClient(url,data); 
		  return param;
	}
	
}
