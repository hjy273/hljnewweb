<%@include file="/common/header.jsp"%>
<html>
<head>
<title>
sendtask
</title>
<script type="" language="javascript">

</script>
</head>
<body >
	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
    <!--盯防短信日志信息一览表-->
      	<br>
		<template:titile value="盯防短信日志信息一览表"/>
		<font face="宋体" size="2" color="red" >
		<bean:write name="bean" property="watchname"/>
		<logic:notEmpty name="bean" property="begindate">
			从<bean:write name="bean" property="begindate"/> 到
		</logic:notEmpty>
		<logic:empty name="bean" property="begindate">
			从开始 到
		</logic:empty>
		<logic:notEmpty name="bean" property="enddate">
			<bean:write name="bean" property="enddate"/>
		</logic:notEmpty>
		<logic:empty name="bean" property="enddate">
			结束
		</logic:empty>盯防短信日志：		
		</font>	
        <display:table name="sessionScope.msginfo" id="currentRowObject" pagesize="20" style="width:100%">
            <display:column property="simid" title="SIM卡"   style="width:140px" maxLength="11" sortable="true"/>
            <display:column property="xx" title="经度"  style="width:140px" maxLength="14" sortable="true"/> 
            <display:column property="yy" title="纬度"  style="width:140px" maxLength="14" sortable="true"/>
            <display:column property="executetime" title="发送时间"  style="width:180px" maxLength="20" sortable="true"/>
		</display:table>
   <!--<html:link action="/SendTaskAction.do?method=exportSendTaskResult">导出为Excel文件</html:link>--> 
</body>
</html>
