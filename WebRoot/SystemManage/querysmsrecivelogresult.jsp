<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<template:titile value="��ѯ���ն�����־���"/>
<display:table name="sessionScope.queryresult"  pagesize="18" >
	<display:column property="receivetime" title="����ʱ��" sortable="true" />
	
	<display:column property="session_key" title="�ỰID"  sortable="true" />
	<display:column property="deviceid" title="�豸���"   sortable="true"  />
	<display:column property="patrolname" title="Ѳ����"  sortable="true"  />
	<display:column property="content" title="��������" maxLength="80" sortable="true"  />
	
</display:table>
<html:link action="/PatrolOpAction.do?method=exportSmsReceiveLog">����ΪExcel�ļ�</html:link>
