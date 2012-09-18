package com.cabletech.commons.util;

import java.io.IOException;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class BaseHttpClient {
	HttpClient httpClient = new HttpClient();
	public String SendHttpClient(String url,NameValuePair[] data){
		  PostMethod postMethod = new PostMethod(url);
		  String str ="";
		  // 将表单的值放入postMethod中
		  postMethod.setRequestBody(data);
		  //使用系统提供的默认的恢复策略
		  postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
		    new DefaultHttpMethodRetryHandler());

		   // 执行postMethod
		  int statusCode = 0;
		  try {
		    statusCode = httpClient.executeMethod(postMethod);
		    if (statusCode != HttpStatus.SC_OK) {
		    	  System.err.println("HttpClient方法调用失败: " + postMethod.getStatusLine());
		    }else{
		    	System.out.println(postMethod.getStatusLine());
		        try {
		          str = postMethod.getResponseBodyAsString();
		        }
		        catch (IOException e) {
		          // TODO Auto-generated catch block
		          e.printStackTrace();
		        }
		        System.out.println("返回的值为:" + str);
		      }
		  } catch (HttpException e) {
			//发生致命的异常，可能是协议不对或者返回的内容有问题
		    System.out.println("请检查URL地址!");
		    e.printStackTrace();
		  } catch (IOException e) {
		    //发生网络异常
		    e.printStackTrace();
		  } finally {
		    //释放连接
		    postMethod.releaseConnection();
		  }	
		  return str;
	}
}
