<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<!--%@include file="/common/listhander.jsp"%-->
<SCRIPT language=JavaScript src="${ctx}/common/PopupDlg.js" type=""></SCRIPT>
<SCRIPT language=javascript src="${ctx}/common/Comm.js" type=""></SCRIPT>
<html>
	<head>
		<title>sendtask</title>
		<script type="" language="javascript">
		toViewForm=function(dispatchId){
			var url="${ctx}/dispatchtask/dispatch_task.do?method=viewDispatchTask";
			var queryString="dispatch_id="+dispatchId;
			location=url+"&"+queryString+"&rnd="+Math.random();
		}
		toCancelForm=function(dispatchId){
			var url;
			if(confirm("确定要取消派单吗？")){
				url="${ctx}/dispatchtask/dispatch_task.do?method=cancelDispatchTaskForm";
				var queryString="dispatch_id="+dispatchId;
				//location=url+"&"+queryString+"&rnd="+Math.random();
				window.open(url+"&"+queryString+"&rnd="+Math.random(),'','width=300,height=200,top=0,left=0,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no');
			}
		}
		</script>
	</head>
	<body>
		<!--显示所有派单页面-->
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
		<br>
		<template:titile value="任务派单列表" />
		<logic:notEmpty name="DISPATCH_TASK_LIST">
			<display:table name="sessionScope.DISPATCH_TASK_LIST"
				requestURI="${ctx}/dispatchtask/dispatch_task.do"
				id="currentRowObject" pagesize="20" style="width:100%">
				<bean:define id="dispatchId" name="currentRowObject" property="id" />
				<bean:define id="sendUserId" name="currentRowObject"
					property="senduserid" />
				<bean:define id="serialNumber" name="currentRowObject"
					property="serialnumber" />
				<bean:define id="sendTopic" name="currentRowObject"
					property="sendtopic" />
				<bean:define id="isOutTime" name="currentRowObject"
					property="is_out_time" />
				<bean:define id="processTerm" name="currentRowObject"
					property="processterm" />
				<bean:define id="workState" name="currentRowObject"
					property="workstate" />
				<display:column media="html" title="流水号" sortable="true"
					style="width:90px">
					<a href="javascript:toViewForm('${dispatchId }')">${serialNumber}</a>
				</display:column>
				<display:column media="html" title="主题" sortable="true">
					<a href="javascript:toViewForm('${dispatchId }')">${sendTopic }</a>
				</display:column>
				<display:column property="sendtypelabel" title="类型"
					style="width:80px" maxLength="5" sortable="true" />
				<display:column property="senddeptname" title="派单单位"
					style="width:80px" maxLength="5" sortable="true" />
				<display:column property="processterm" title="处理期限"
					style="width:140px" sortable="true" />
				<display:column property="sendusername" title="派发人"
					style="width:60px" maxLength="4" sortable="true" />
				<display:column media="html" title="操作" style="width:50px">
					<c:if
						test="${sessionScope.LOGIN_USER.deptype=='1'&&workState!='999'}">
						<a href="javascript:toCancelForm('${dispatchId }')">取消</a>
					</c:if>
				</display:column>
			</display:table>
			<html:link
				action="/dispatchtask/dispatch_task.do?method=exportDispatchTaskResult">导出为Excel文件</html:link>
		</logic:notEmpty>
	</body>
</html>
