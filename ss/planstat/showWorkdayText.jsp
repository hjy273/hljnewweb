<%@include file="/common/header.jsp"%>
<%@page import="org.apache.commons.beanutils.BasicDynaBean"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
	media="screen, print" />
<script language="javascript">
function toGetBack(){
        var url = "${ctx}/planstat/showWorkdayInfo.jsp";
        self.location.replace(url);
}
</script>
<body style="width: 95%">
	<template:titile value="�ƻ������ڵ���������Ѳ�����" />
	<display:table name="sessionScope.workdaytextinfo"
		id="currentRowObject" pagesize="18">
		<display:column property="patroldate" title="Ѳ��ʱ��" sortable="true" />
		<display:column property="pointname" title="Ѳ�������" sortable="true" />
		<display:column property="addressinfo" title="Ѳ���λ��" sortable="true" />
		<display:column property="sublinename" title="����Ѳ���" sortable="true" />
	</display:table>
	<p>
	<center>
		<input type="button" class="button_length" onclick="toGetBack()" value="���ع������б�ҳ��">
	</center>
	<p>
</body>
