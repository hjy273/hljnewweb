<%@ include file="/common/header.jsp" %>

<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />
<template:titile value="�ƻ�Ѳ����"/>
<display:table name="sessionScope.QueryResult" pagesize="18">
  <logic:equal value="group" name="PMType">
    		<display:column property="patrolname" title="Ѳ��ά����"/>
	</logic:equal>
    <logic:notEqual value="group" name="PMType">
    		<display:column property="patrolname" title="Ѳ��Ա"/>
    </logic:notEqual>

  <display:column property="contractorname" title="��ά��λ"/>
  <display:column property="plancount" title="Ӧ������"/>
  <display:column property="losscount" title="©������"/>
  <display:column property="patrolrate" title="Ѳ����"/>
  <display:column property="begindate" title="��ʼʱ��"/>
  <display:column property="enddate" title="��ֹʱ��"/>

</display:table>
<br>
<html:link action="/StatisticAction.do?method=ExportPlanAppRate">����ΪExcel�ļ�</html:link>

