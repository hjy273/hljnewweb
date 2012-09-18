<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<%@include file="/mam/concordatJS.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />
<template:titile value="��ͬ��Ϣ�б�" />
<display:table name="sessionScope.concordats" id="currentRowObject" pagesize="18" export="true">
	<display:column property="cno" title="��ͬ���" maxLength="10" />
	<display:column property="patrolregion" title="ά������" maxLength="10" />
	<display:column property="cname" title="��ͬ����" maxLength="10" />
	<display:column property="deptname" title="�׷�" maxLength="10" />
	<display:column property="contractorname" title="�ҷ���ά��˾" />
	<display:column property="ctypename" title="��ͬ����" maxLength="10" />
	<display:column property="bookdate" title="ǩ������" maxLength="10" />
	<display:column property="perioddate" title="��ͬ��Ч��" maxLength="10" />
	<display:column property="regionname" title="��������" maxLength="10" />
	<display:column media="html" title="����">
		<%
		DynaBean  object = (DynaBean) pageContext.findAttribute("currentRowObject");
		String id = object.get("id").toString();
		String perioddate = object.get("perioddate").toString();
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy/MM/dd");
		String newdate = format.format(new java.util.Date());
		int period = Integer.parseInt(perioddate.substring(0,4)+perioddate.substring(5,7)+perioddate.substring(8,10));
		int now = Integer.parseInt(newdate.substring(0,4)+newdate.substring(5,7)+newdate.substring(8,10)); 
		%>
		<a style="text-decoration:underline;color: blue;" href="javascript:;" onclick="javascript:toView('<%=id%>');return false;">�鿴</a>
		<apptag:checkpower thirdmould="60604" ishead="0">
		|&nbsp;<a style="text-decoration:underline;color: blue;" href="javascript:;" onclick="javascript:toEdit('<%=id%>');return false;">�޸�</a>
		</apptag:checkpower>
		<apptag:checkpower thirdmould="60603" ishead="0">
		<%
			if(period<now){
		%>
		|&nbsp;<a style="text-decoration:underline;color: blue;" href="javascript:;" onclick="javascript:toDelete('<%=id%>');return false;">ɾ��</a>
		<%} %>
		</apptag:checkpower>
	</display:column>
</display:table>