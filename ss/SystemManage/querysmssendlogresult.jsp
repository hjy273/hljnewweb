<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<br>
<script type="text/javascript" language="javascript">
		 goBack=function(idValue){
            window.location.href = "${ctx}/smsLogAction.do?method=querySMS_SendLogForm";
		}
				
</script>
<template:titile value="查询发送短信日志结果"/>
<display:table name="requestScope.queryresultpage" sort="list" requestURI="/smsLogAction.do?method=querySMS_ReciveLog"  pagesize="12" >
	<display:column property="data_time" title="时间" sortable="true" />
	<display:column property="mobiles" title="手机号" sortable="true"  maxLength="18"/>
	<display:column property="content" title="内容" maxLength="55" sortable="true" />
	<display:column property="handlestate" title="状态"  sortable="true"/>
	<!-- display:column property="need_report" title="是否需要报告" sortable="true" /-->
	<!-- 
	<-display:column property="sendtime" title="发送时间" sortable="true" /->
	<-display:column property="username" title="发送者"  sortable="true" /->
	<-display:column property="simid" title="接收号码"  sortable="true"  /->
	<-display:column property="type" title="短信类型"  sortable="true"  /->
	<-display:column property="content" title="短信内容"  sortable="true"  /->
	<-display:column property="handlestate" title="状态"  sortable="true"  /->
	 -->
</display:table>
<div align="center">
<input type="button" value="返回" class="button" onclick="goBack();"></input>
</div>
<!-- 
<html:link action="/PatrolOpAction.do?method=exportSmsSendLog">导出为Excel文件</html:link> -->
