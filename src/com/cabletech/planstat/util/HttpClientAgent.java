package com.cabletech.planstat.util;

import java.io.IOException;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.cabletech.commons.config.GisConInfo;
import com.cabletech.commons.util.BaseHttpClient;

public class HttpClientAgent extends BaseHttpClient {
	
	public HttpClientAgent(){
        //����HttpClient��ʵ��
	
	}
	
	public String SendPointHttpClient(String pointID){
		  GisConInfo gisip = GisConInfo.newInstance();;
		  //����POST������ʵ��
		  String url = "http://" + gisip.getStatip() + ":" + gisip.getStatport() + "/statconsole/process?param=point";
		  //String url = "http://" + gisip.getServerip() + ":" + gisip.getServerport() + "/WebApp/HttpServer";
		  // ��������ֵ(pointID)
		  NameValuePair[] data = {
		      new NameValuePair("pointid", pointID)};
//		  ���صĽ��Ϊ�ַ���,��������,��","����,��һ������Ϊsim����,�ڶ���ΪѲ��ʱ��(ʱ����)
		  String param = SendHttpClient(url,data); 
		  return param;
	}
	public String SendSublineHttpClient(String requestID,HttpServletRequest request) throws Exception {
		  //request.setCharacterEncoding("UTF-8");
		  GisConInfo gisip = GisConInfo.newInstance();
		  //����POST������ʵ��
		  String url = "http://" + gisip.getStatip() + ":" + gisip.getStatport() + "/statconsole/process?param=subline";
		  //String url = "http://" + gisip.getServerip() + ":" + gisip.getServerport() + "/WebApp/HttpServer";
		  // ��������ֵ(����ID)
		  NameValuePair[] data = {
		      new NameValuePair("requestid", requestID)};
		  return SendHttpClient(url,data);
          
	}
	
}
