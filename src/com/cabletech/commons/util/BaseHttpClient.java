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
		  // ������ֵ����postMethod��
		  postMethod.setRequestBody(data);
		  //ʹ��ϵͳ�ṩ��Ĭ�ϵĻָ�����
		  postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
		    new DefaultHttpMethodRetryHandler());

		   // ִ��postMethod
		  int statusCode = 0;
		  try {
		    statusCode = httpClient.executeMethod(postMethod);
		    if (statusCode != HttpStatus.SC_OK) {
		    	  System.err.println("HttpClient��������ʧ��: " + postMethod.getStatusLine());
		    }else{
		    	System.out.println(postMethod.getStatusLine());
		        try {
		          str = postMethod.getResponseBodyAsString();
		        }
		        catch (IOException e) {
		          // TODO Auto-generated catch block
		          e.printStackTrace();
		        }
		        System.out.println("���ص�ֵΪ:" + str);
		      }
		  } catch (HttpException e) {
			//�����������쳣��������Э�鲻�Ի��߷��ص�����������
		    System.out.println("����URL��ַ!");
		    e.printStackTrace();
		  } catch (IOException e) {
		    //���������쳣
		    e.printStackTrace();
		  } finally {
		    //�ͷ�����
		    postMethod.releaseConnection();
		  }	
		  return str;
	}
}
