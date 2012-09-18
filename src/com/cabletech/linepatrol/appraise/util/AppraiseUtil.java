package com.cabletech.linepatrol.appraise.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.cabletech.baseinfo.services.ContractorBO;
import com.cabletech.commons.util.VelocityUtil;

public class AppraiseUtil {
	/**
	 * 通过模板输出
	 * @param response
	 * @param context
	 */
	public static void PrintVM(HttpServletResponse response,Map context,String vmPath) {
		response.setCharacterEncoding("UTF-8");
		try {
			VelocityUtil.rendWebResponse(vmPath, context, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * ajax输出string
	 * @param response
	 * @param html 格式为“a,b,c,d”
	 * @throws IOException
	 */
	public static void AjaxOutHtml(HttpServletResponse response, String html) throws IOException {
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(html);
		out.flush();
	}
	
	/**
	 * 初始化response
	 * @param response
	 * @param fileName
	 * @throws UnsupportedEncodingException
	 */
	public static void InitResponse(HttpServletResponse response, String fileName,String contentType) throws UnsupportedEncodingException {
		response.reset();
		response.setContentType(contentType);
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String(fileName.getBytes(), "iso-8859-1"));
	}
	/**
	 * 通过contractorId获得相对的名称
	 * @param contractorId
	 * @return
	 * @throws Exception
	 */
	public static String GetContractorName(String contractorId) throws Exception{
		ContractorBO contractorBO = new ContractorBO();
		String contractorName = contractorBO.getContractorNameByContractorById(contractorId);
		return contractorName;
	}
}
