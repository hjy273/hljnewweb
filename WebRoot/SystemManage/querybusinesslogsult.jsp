<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<template:titile value="��ѯϵͳ������־��Ϣ���"/>
<display:table name="sessionScope.queryresult"   pagesize="18" >
	<display:column property="logdate" title="��½ʱ��" sortable="true" />
	<display:column property="userid" title="�û�ID"  sortable="true" />
	<display:column property="username" title="�û�����"  sortable="true" />
	<display:column property="ip" title="��½IP"  sortable="true"  />
	<display:column property="module" title="����ģ��"  sortable="true"  />
	<display:column property="msg" title="������Ϣ"  sortable="true"  maxLength="25"/>
</display:table>
<html:link action="/PatrolOpAction.do?method=exportSysLog">����ΪExcel�ļ�</html:link>
