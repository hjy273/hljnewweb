<%@include file="/common/header.jsp"%>
<!--%@include file="/common/listhander.jsp"%-->
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<script type="text/javascript" language="javascript">
	toViewApplyForm = function(idValue) {
		location.href = "${ctx}/material_apply.do?method=viewMaterialApply&&apply_id="
				+ idValue;
	}
		//取消流程
		toCancelForm=function(applyId){
			var url;
			if(confirm("确定要取消该材料申请流程吗？")){
				url="${ctx}/material_apply.do?method=cancelMaterialApplyForm";
				var queryString="apply_id="+applyId;
				//location=url+"&"+queryString+"&rnd="+Math.random();
				window.open(url+"&"+queryString+"&rnd="+Math.random(),'','width=300,height=200,top=0,left=0,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no');
			}
		}
</script>
		<title>材料类型</title>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
		<style type="text/css">
.subject {
	text-align: center;
}
</style>
	</head>
	<body>
		<br />
		<template:titile value="材料申请已办任务查询一览表" />
		<div style="text-align: center;">
			<!-- 
			<iframe
				src="${ctx}/material_apply.do?method=viewMaterialApplyHistoryProcess&&task_name=${param.task_name }&&task_out_come=${param.task_out_come}"
				frameborder="0" id="processGraphFrame" height="100" scrolling="no"
				width="95%"></iframe>
			 -->
		</div>
		<div style="text-align: center;">
		<form action="${ctx}/material_apply.do?method=queryFinishHandledMaterialApplyList" method="post">
		 	<table border="0" cellpadding="0" cellspacing="0" style="border:0;height:25px;width:550px;">
		 		<tr>
					<td class="tdr">
						查询开始时间：
					</td>
					<td class="tdl">
						<input type="text" id="protimeid" name="begintime" value="${begin_time }"
							readonly="readonly" class="Wdate"
							onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',minDate:'2000/1/1'})"
							style="width: 100px;" />
					</td>
					<td class="tdr">
						查询结束时间：
					</td>
					<td class="tdl">
						<input type="text" id="protimeid" name="endtime" value="${end_time }"
							readonly="readonly" class="Wdate"
							onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',minDate:'#F{$dp.$D(\'begintime\')}'})"
							style="width: 100px;" />
					</td>
					<td class="tdc">
						<input name="btnSubmit" value="查询" class="button" type="submit" />
					</td>
		 		</tr>
		 	</table>
		</form>
		</div>
		<logic:notEmpty name="DATA_LIST">
			<display:table name="sessionScope.DATA_LIST" id="currentRowObject"
				pagesize="18">
				<bean:define id="id" name="currentRowObject" property="id"></bean:define>
				<bean:define name="currentRowObject" property="creator" id="sendUserId" />
				<bean:define id="applyState" name="currentRowObject" property="state"></bean:define>
				<bean:define id="storageId" name="currentRowObject"
					property="storage_id"></bean:define>
				<display:column property="title" sortable="true" title="标题"
					headerClass="subject" />
				<display:column property="createtime" title="申请时间"
					headerClass="subject" sortable="true" />
				<display:column property="contractorname" sortable="true"
					title="代维公司" headerClass="subject" maxLength="10" />
				<display:column property="username" sortable="true" title="申请人"
					headerClass="subject" maxLength="10" />
				<display:column media="html" title="操作">
					<a href="javascript:toViewApplyForm('${id}');">查看</a>
				</display:column>
			</display:table>
		</logic:notEmpty>
	</body>
</html>
