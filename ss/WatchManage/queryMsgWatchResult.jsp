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
    <!--����������־��Ϣһ����-->
      	<br>
		<template:titile value="����������־��Ϣһ����"/>
		<font face="����" size="2" color="red" >
		<bean:write name="bean" property="watchname"/>
		<logic:notEmpty name="bean" property="begindate">
			��<bean:write name="bean" property="begindate"/> ��
		</logic:notEmpty>
		<logic:empty name="bean" property="begindate">
			�ӿ�ʼ ��
		</logic:empty>
		<logic:notEmpty name="bean" property="enddate">
			<bean:write name="bean" property="enddate"/>
		</logic:notEmpty>
		<logic:empty name="bean" property="enddate">
			����
		</logic:empty>����������־��		
		</font>	
        <display:table name="sessionScope.msginfo" id="currentRowObject" pagesize="20" style="width:100%">
            <display:column property="simid" title="SIM��"   style="width:140px" maxLength="11" sortable="true"/>
            <display:column property="xx" title="����"  style="width:140px" maxLength="14" sortable="true"/> 
            <display:column property="yy" title="γ��"  style="width:140px" maxLength="14" sortable="true"/>
            <display:column property="executetime" title="����ʱ��"  style="width:180px" maxLength="20" sortable="true"/>
		</display:table>
   <!--<html:link action="/SendTaskAction.do?method=exportSendTaskResult">����ΪExcel�ļ�</html:link>--> 
</body>
</html>
