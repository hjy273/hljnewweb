<%@ page language="java" contentType="text/html; charset=GBK"%><%@ include file="/common/header.jsp"%>
<%@ page import = "com.cabletech.baseinfo.util.UseTerminalChart,com.cabletech.baseinfo.beans.*" %>
<%@ page import = "com.cabletech.commons.chart.ChartSize" %>
<%@ page import = "java.io.PrintWriter" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.util.Iterator" %>
<META http-equiv="Content-Type" content="text/html; charset=GB2312">
<%
	UseTerminalBean useTerminal = (UseTerminalBean)session.getAttribute("query");
 %>
<template:titile value="${sessionScope.query.year}��${sessionScope.query.month}�·��豸ʹ�����" />
<%
	UseTerminalChart chart = new UseTerminalChart();
	String filename ="";
	filename = chart.generateChart(session,new PrintWriter(out));
	String graphURL = request.getContextPath() + "/servlet/DisplayChart?filename=" + filename;
	if(filename.indexOf("_nodata") != -1 || filename.indexOf("_error") != -1){
%>
<div align="center">
<img src="${ctx}/images/public_nodata_500x300.png" width=500 height=300 border=0>
</div>
<%
	}else{
	
	%>
	<div align="center">
<img src="<%= graphURL %>" width=<%=ChartSize.WIDTH%> height=<%=ChartSize.HEIGHT %> border=0 usemap="#<%= filename %>">
</div>
<%
	}
%>
<%
/*	Map map = (Map)session.getAttribute("utMap");
	Iterator it = map.keySet().iterator();
		while (it.hasNext()) {
            String key = it.next().toString();
            Integer value =  (Integer) map.get(key);
            System.out.println("key :"+key +" value :"+value);
            }
            */
%>

