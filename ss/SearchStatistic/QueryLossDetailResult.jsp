<%@include file="/common/header.jsp"%>
<META content="text/html;charset=GB2312" http-equiv="Content-Type">

<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />

<template:titile value="©����ϸ"/>

<display:table name="sessionScope.QueryResult" pagesize="18" >
	<logic:equal value="group" name="PMType">
    		<display:column property="patrolname" title="Ѳ��ά����"/>
	</logic:equal>
    <logic:notEqual value="group" name="PMType">
    		<display:column property="patrolname" title="Ѳ��ά����"/>
    </logic:notEqual>
	<display:column property="linename" title="Ѳ����·"/>
    <display:column property="sublinename" title="Ѳ���߶�"/>
    <display:column property="pointname" title="Ѳ�������"/>
    <display:column property="addressinfo" title="Ѳ���λ��"/>
    <display:column property="isfocus" title="�ؼ���"/>
    <display:column property="planexe" title="�ƻ�����"/>
	<display:column property="losstime" title="©�����"/>
</display:table>
<br>

<html:link action="/exportLoss.do?reportType=lossDetail">����ΪExcel�ļ�</html:link>

