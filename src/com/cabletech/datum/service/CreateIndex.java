package com.cabletech.datum.service;


import org.apache.commons.httpclient.NameValuePair;

import com.cabletech.commons.config.GisConInfo;
import com.cabletech.commons.util.BaseHttpClient;

public class CreateIndex extends BaseHttpClient {
	
	public CreateIndex(){
        //����HttpClient��ʵ��
		
	}
	
	public String createIndexClient(){
		  GisConInfo gisip = GisConInfo.newInstance();
		  //����POST������ʵ��
		  String url = "http://" + gisip.getSearchip() + ":" + gisip.getSearchport() + "/"+gisip.getSearchdir()+"/createindex.htm";
		  //String url = "http://" + gisip.getServerip() + ":" + gisip.getServerport() + "/WebApp/HttpServer";
		  // ��������ֵ(pointID)
		  NameValuePair[] data = {
		      new NameValuePair("fullIndex", "true")};
//		  ���صĽ��Ϊ�ַ���,��������,��","����,��һ������Ϊsim����,�ڶ���ΪѲ��ʱ��(ʱ����)
		  String param = SendHttpClient(url,data); 
		  return param;
	}
	
}
