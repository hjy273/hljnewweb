<%@include file="/common/header.jsp"%>

<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />

<template:titile value="Ѳ����ϸ"/>
<display:table name="sessionScope.QueryResult" requestURI="${ctx}/StatisticAction.do?method=getPatrolDetail" pagesize="18" >
	<logic:equal value="group" name="PMType">
    		<display:column property="patrolname" title="Ѳ��ά����" sortable="true"/>
	</logic:equal>
    <logic:notEqual value="group" name="PMType">
    		<display:column property="patrolname" title="Ѳ��ά����" sortable="true"/>
    </logic:notEqual>

    <display:column property="contractorname" title="Ѳ�쵥λ" sortable="true"/>
    <display:column property="sublinename" title="Ѳ���߶�" sortable="true"/>

    <display:column property="pointname" title="Ѳ�������" sortable="true"/>

    <display:column property="position" title="Ѳ���λ��" sortable="true"/>
    <display:column property="executetime" title="Ѳ��ʱ��" sortable="true"/>
</display:table>
<br>
<html:link action="/export2Excel.do?reportType=patrolDetail">����ΪExcel�ļ�</html:link>
