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
        //构造HttpClient的实例
	
	}
	
	public String SendPointHttpClient(String pointID){
		  GisConInfo gisip = GisConInfo.newInstance();;
		  //创建POST方法的实例
		  String url = "http://" + gisip.getStatip() + ":" + gisip.getStatport() + "/statconsole/process?param=point";
		  //String url = "http://" + gisip.getServerip() + ":" + gisip.getServerport() + "/WebApp/HttpServer";
		  // 填入表单域的值(pointID)
		  NameValuePair[] data = {
		      new NameValuePair("pointid", pointID)};
//		  返回的结果为字符串,两个参数,以","隔开,第一个参数为sim卡号,第二个为巡检时间(时分秒)
		  String param = SendHttpClient(url,data); 
		  return param;
	}
	public String SendSublineHttpClient(String requestID,HttpServletRequest request) throws Exception {
		  //request.setCharacterEncoding("UTF-8");
		  GisConInfo gisip = GisConInfo.newInstance();
		  //创建POST方法的实例
		  String url = "http://" + gisip.getStatip() + ":" + gisip.getStatport() + "/statconsole/process?param=subline";
		  //String url = "http://" + gisip.getServerip() + ":" + gisip.getServerport() + "/WebApp/HttpServer";
		  // 填入表单域的值(请求ID)
		  NameValuePair[] data = {
		      new NameValuePair("requestid", requestID)};
		  return SendHttpClient(url,data);
          
	}
	
}
