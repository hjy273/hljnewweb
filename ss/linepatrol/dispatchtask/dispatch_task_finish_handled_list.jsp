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
			if(confirm("ȷ��Ҫȡ���ɵ���")){
				url="${ctx}/dispatchtask/dispatch_task.do?method=cancelDispatchTaskForm";
				var queryString="dispatch_id="+dispatchId;
				//location=url+"&"+queryString+"&rnd="+Math.random();
				window.open(url+"&"+queryString+"&rnd="+Math.random(),'','width=300,height=200,top=0,left=0,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no');
			}
		}
		</script>
	</head>
	<body>
		<!--��ʾ�����ɵ�ҳ��-->
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
		<br>
		<template:titile value="�Ѱ������ɵ��б�" />
		<div style="text-align: center;">
			<!-- 
			<iframe
				src="${ctx}/dispatchtask/dispatch_task.do?method=viewDispatchTaskHistoryProcess&&task_name=${param.task_name }&&task_out_come=${param.task_out_come}"
				frameborder="0" id="processGraphFrame" height="90" scrolling="no"
				width="95%"></iframe>
			 -->
		</div>
		<div style="text-align: center;">
		<form action="${ctx}/dispatchtask/dispatch_task.do?method=queryForFinishHandledDispatchTask" method="post">
		 	<table border="0" cellpadding="0" cellspacing="0" style="border:0;height:25px;width:550px;">
		 		<tr>
					<td class="tdr">
						��ѯ��ʼʱ�䣺
					</td>
					<td class="tdl">
						<input type="text" id="protimeid" name="begintime" value="${begin_time }"
							readonly="readonly" class="Wdate"
							onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',minDate:'2000/1/1'})"
							style="width: 100px;" />
					</td>
					<td class="tdr">
						��ѯ����ʱ�䣺
					</td>
					<td class="tdl">
						<input type="text" id="protimeid" name="endtime" value="${end_time }"
							readonly="readonly" class="Wdate"
							onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',minDate:'2000/1/1'})"
							style="width: 100px;" />
					</td>
					<td class="tdc">
						<input name="btnSubmit" value="��ѯ" class="button" type="submit" />
					</td>
		 		</tr>
		 	</table>
		</form>
		</div>
		<logic:notEmpty name="DISPATCH_TASK_LIST">
			<display:table name="sessionScope.DISPATCH_TASK_LIST"
				requestURI="${ctx}/dispatchtask/dispatch_task.do"
				id="currentRowObject" pagesize="15" style="width:100%">
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
				<display:column media="html" title="��ˮ��" sortable="true"
					style="width:90px">
					<a href="javascript:toViewForm('${dispatchId }')">${serialNumber}</a>
				</display:column>
				<display:column media="html" title="����" sortable="true">
					<a href="javascript:toViewForm('${dispatchId }')">${sendTopic }</a>
				</display:column>
				<display:column property="sendtypelabel" title="����"
					style="width:80px" maxLength="5" sortable="true" />
				<display:column property="senddeptname" title="�ɵ���λ"
					style="width:80px" maxLength="5" sortable="true" />
				<display:column property="processterm" title="��������"
					style="width:140px" sortable="true" />
				<display:column property="sendusername" title="�ɷ���"
					style="width:60px" maxLength="4" sortable="true" />
				<display:column media="html" title="����" style="width:50px">
					<c:if
						test="${sessionScope.LOGIN_USER.deptype=='1' &&workState!='999'&&workState!='888'}">
						<a href="javascript:toCancelForm('${dispatchId }')">ȡ��</a>
					</c:if>
				</display:column>
			</display:table>
			<html:link
				action="/dispatchtask/dispatch_task.do?method=exportDispatchTaskResult">����ΪExcel�ļ�</html:link>
		</logic:notEmpty>
	</body>
</html>
