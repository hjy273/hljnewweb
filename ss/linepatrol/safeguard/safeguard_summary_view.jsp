<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
<html>
	<head>
		<title>�鿴�����ܽ�</title>
		<script type="text/javascript">
			//�鿴����
			toViewFinishSafeguard=function(conId){
            	var url = "${ctx}/process_history.do?method=showProcessHistoryList&&object_type=safeguard&&is_close=0&&object_id="+conId;
            	processWin = new Ext.Window({
				layout : 'fit',
				width:750,height:400, 
				resizable:true,
				closeAction : 'close', 
				modal:true,
				autoScroll:true,
				autoLoad:{url: url,scripts:true}, 
				plain: true,
				title:"�鿴�������̴�����Ϣ" 
			});
			processWin.show(Ext.getBody());
			}
			closeProcessWin=function(){
				processWin.close();
			}
		</script>
	</head>
	<body>
		<template:titile value="�鿴�����ܽ�"/>
		<html:form action="/safeguardSummaryAction.do?method=readReply" enctype="multipart/form-data">
			<jsp:include page="safeguard_task_base.jsp"></jsp:include>
			<jsp:include page="safeguard_plan_base.jsp"></jsp:include>
			<jsp:include page="safeguard_summary_base.jsp"></jsp:include>
			<div align="center" style="height:40px">
				<c:if test="${not empty isread}">
					<input type="hidden" name="summaryId" value="${safeguardSummary.id }"/>
					<html:submit property="button" styleClass="button">����</html:submit>&nbsp;&nbsp;
				</c:if>
				<html:button property="button" styleClass="lbutton" onclick="toViewFinishSafeguard('${conId}')">�鿴������ʷ</html:button>&nbsp;&nbsp;
				<html:button property="button" styleClass="button" onclick="javascript:history.go(-1);">����</html:button>
			</div>
		</html:form>
	</body>
</html>
