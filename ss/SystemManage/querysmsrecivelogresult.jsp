<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<template:titile value="��ѯ���ն�����־���"/>
<display:table name="requestScope.queryresultpage"  pagesize="18" requestURI="/smsLogAction.do?method=querySMS_SendLog" sort="list"  id="diaplaytable" defaultsort="1">
	<display:column property="arrivetime" title="����ʱ��" sortable="true" defaultorder="descending" sortName="arrivetime"/>
	<display:column property="sim" title="���ͺ���"  sortable="true"  />
	<display:column property="region" title="��������"   sortable="true"  />
	<display:column property="content" title="��������" maxLength="20" sortable="true"  />
	<display:column property="handlestate" title="״̬"  sortable="true"  />
</display:table>
<html:link action="/PatrolOpAction.do?method=exportSmsReceiveLog">����ΪExcel�ļ�</html:link>
