<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<br>
<script type="text/javascript" language="javascript">
		 goBack=function(idValue){
            window.location.href = "${ctx}/smsLogAction.do?method=querySMS_SendLogForm";
		}
				
</script>
<template:titile value="��ѯ���Ͷ�����־���"/>
<display:table name="requestScope.queryresultpage" sort="list" requestURI="/smsLogAction.do?method=querySMS_ReciveLog"  pagesize="12" >
	<display:column property="data_time" title="ʱ��" sortable="true" />
	<display:column property="mobiles" title="�ֻ���" sortable="true"  maxLength="18"/>
	<display:column property="content" title="����" maxLength="55" sortable="true" />
	<display:column property="handlestate" title="״̬"  sortable="true"/>
	<!-- display:column property="need_report" title="�Ƿ���Ҫ����" sortable="true" /-->
	<!-- 
	<-display:column property="sendtime" title="����ʱ��" sortable="true" /->
	<-display:column property="username" title="������"  sortable="true" /->
	<-display:column property="simid" title="���պ���"  sortable="true"  /->
	<-display:column property="type" title="��������"  sortable="true"  /->
	<-display:column property="content" title="��������"  sortable="true"  /->
	<-display:column property="handlestate" title="״̬"  sortable="true"  /->
	 -->
</display:table>
<div align="center">
<input type="button" value="����" class="button" onclick="goBack();"></input>
</div>
<!-- 
<html:link action="/PatrolOpAction.do?method=exportSmsSendLog">����ΪExcel�ļ�</html:link> -->
