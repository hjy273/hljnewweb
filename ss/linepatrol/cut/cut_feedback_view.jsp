<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.linepatrol.cut.module.*" %>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>查看反馈</title>
		<script type="text/javascript">
			//查看申请
			toViewFinishCut=function(cutId){
            	var url = "${ctx}/process_history.do?method=showProcessHistoryList&&object_type=lineCut&&is_close=0&&object_id="+cutId;
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
	<%
		Map map = (Map)request.getAttribute("map");
		Cut cutBean = (Cut)map.get("cut");
		CutFeedback cutFeedback = (CutFeedback)map.get("cutFeedback");
		String sublineIds = (String)map.get("sublineIds");
		List approve_info_list = (List)map.get("approve_info_list");
		request.setAttribute("cut",cutBean);
		request.setAttribute("cutFeedback",cutFeedback);
		request.setAttribute("sublineIds",sublineIds);
		request.setAttribute("approve_info_list", approve_info_list);
	%>
	<body>
		<template:titile value="查看反馈信息"/>
		<html:form action="/cutFeedbackAction.do?method=readReply" styleId="addCutFeedback" enctype="multipart/form-data">
			<jsp:include page="cut_apply_base.jsp"/>
			<c:if test="${cutFeedback.feedbackType=='0'}">
				<jsp:include page="cut_feedback_base.jsp"/>	
			</c:if>
			<c:if test="${cutFeedback.feedbackType=='1'}">
				<table cellspacing="0" cellpadding="1" align="center" style="width:90%; border-top:0px;" class="tabout">
					<tr class="trcolor">
						<td class="tdulleft" style="width:20%;">取消原因：</td>
						<td class="tdulright" colspan="3">&nbsp;<c:out value="${cutFeedback.cancelCause}"/></td>
					</tr>
				</table>
			</c:if>	
			<table cellspacing="0" cellpadding="1" style="width:90%;border-top:0px;" align="center" class="tabout">
				<tr class="trcolor">
					<td align="center">
						<c:if test="${not empty isread}">
							<input type="hidden" name="cutFeedbackId" value="${cutFeedback.id }"/>
							<html:submit property="button" styleClass="button">已阅</html:submit>&nbsp;&nbsp;
						</c:if>
						<html:button property="button" styleClass="button" onclick="toViewFinishCut('${cut.id}')">查看流程历史</html:button>&nbsp;&nbsp;
						<html:button property="button" styleClass="button" onclick="javascript:history.go(-1);">返回</html:button>
					</td>
				</tr>
			</table>
		</html:form>
	</body>
</html>
