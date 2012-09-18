<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<%@include file="/mam/concordatJS.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />
<template:titile value="合同信息列表" />
<display:table name="sessionScope.concordats" id="currentRowObject" pagesize="18" export="true">
	<display:column property="cno" title="合同编号" maxLength="10" />
	<display:column property="patrolregion" title="维护区域" maxLength="10" />
	<display:column property="cname" title="合同名称" maxLength="10" />
	<display:column property="deptname" title="甲方" maxLength="10" />
	<display:column property="contractorname" title="乙方代维公司" />
	<display:column property="ctypename" title="合同类型" maxLength="10" />
	<display:column property="bookdate" title="签订日期" maxLength="10" />
	<display:column property="perioddate" title="合同有效期" maxLength="10" />
	<display:column property="regionname" title="行政区域" maxLength="10" />
	<display:column media="html" title="操作">
		<%
		DynaBean  object = (DynaBean) pageContext.findAttribute("currentRowObject");
		String id = object.get("id").toString();
		String perioddate = object.get("perioddate").toString();
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy/MM/dd");
		String newdate = format.format(new java.util.Date());
		int period = Integer.parseInt(perioddate.substring(0,4)+perioddate.substring(5,7)+perioddate.substring(8,10));
		int now = Integer.parseInt(newdate.substring(0,4)+newdate.substring(5,7)+newdate.substring(8,10)); 
		%>
		<a style="text-decoration:underline;color: blue;" href="javascript:;" onclick="javascript:toView('<%=id%>');return false;">查看</a>
		<apptag:checkpower thirdmould="60604" ishead="0">
		|&nbsp;<a style="text-decoration:underline;color: blue;" href="javascript:;" onclick="javascript:toEdit('<%=id%>');return false;">修改</a>
		</apptag:checkpower>
		<apptag:checkpower thirdmould="60603" ishead="0">
		<%
			if(period<now){
		%>
		|&nbsp;<a style="text-decoration:underline;color: blue;" href="javascript:;" onclick="javascript:toDelete('<%=id%>');return false;">删除</a>
		<%} %>
		</apptag:checkpower>
	</display:column>
</display:table>