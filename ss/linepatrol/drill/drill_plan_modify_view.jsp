<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@page import="com.cabletech.linepatrol.drill.module.*" %>
<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
<html>
	<head>
		<title>查看演练方案变更</title>
		<script type="text/javascript">
			//查看申请
			toViewFinishCut=function(cutId){
            	var url = "${ctx}/process_history.do?method=showProcessHistoryList&&object_type=drill&&is_close=0&&object_id="+cutId;
            	processWin = new Ext.Window({
				layout : 'fit',
				width:750,height:400, 
				resizable:true,
				closeAction : 'close', 
				modal:true,
				autoScroll:true,
				autoLoad:{url: url,scripts:true}, 
				plain: true,
				title:"查看割接流程处理信息" 
			});
			processWin.show(Ext.getBody());
			}
			closeProcessWin=function(){
				processWin.close();
			}
		</script>
	</head>
	<body>
		<template:titile value="查看演练方案变更"/>
		<html:form action="/drillPlanModifyAction.do?method=readReply" enctype="multipart/form-data">
			<jsp:include page="drill_plan_base.jsp"/>
			<jsp:include page="drill_plan_modify_base.jsp"/>
			<div align="center" style="height:40px">
				<c:if test="${not empty isread}">
					<input type="hidden" name="modifyId" value="${drillPlanModify.id }"/>
					<html:submit property="button" styleClass="button">已阅</html:submit>&nbsp;&nbsp;
				</c:if>
				<html:button property="button" styleClass="button" onclick="toViewFinishCut('${conId}')">查看流程历史</html:button>&nbsp;&nbsp;
				<html:button property="button" styleClass="button" onclick="javascript:history.go(-1);">返回</html:button>
			</div>
		</html:form>
	</body>
</html>
