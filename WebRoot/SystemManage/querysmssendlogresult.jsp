<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<template:titile value="��ѯ���Ͷ�����־���"/>
<display:table name="sessionScope.queryresult"   pagesize="18" >
	<display:column property="sendtime" title="����ʱ��" sortable="true" />
	<display:column property="username" title="������"  sortable="true" />
	<display:column property="simid" title="���պ���"  sortable="true"  />
	<display:column property="type" title="��������"  sortable="true"  />
	<display:column property="content" title="��������"  sortable="true"  />
	<display:column property="handlestate" title="״̬"  sortable="true"  />
</display:table>
<html:link action="/PatrolOpAction.do?method=exportSmsSendLog">����ΪExcel�ļ�</html:link>
