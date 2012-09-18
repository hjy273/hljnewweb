
<%@page import="com.cabletech.linepatrol.commons.module.Evaluate"%><%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.linepatrol.cut.module.*" %>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
	<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
	<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
<html>
	<head>
		<title>查看详细信息</title>
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
			var win;
			function toViewSublineData(id){
				var url = "${ctx}/trunkAction.do?method=updateTrunkView&id="+id+"&type=cable";
				win = new Ext.Window({
					layout : 'fit',
					width:500,
					height:300, 
					resizable:true,
					closeAction : 'close',
					modal:true,
					html:'<iframe scrolling=auto frameborder=0 width=100% height=100% src="'+url+'" />',
					plain: true
				});
				win.show(Ext.getBody());
			}
		</script>
	</head>
	<%
		Map map = (Map)request.getAttribute("map");
		Cut cut = (Cut)map.get("cut");
		CutFeedback cutFeedback = (CutFeedback)map.get("cutFeedback");
		CutAcceptance cutAcceptance = (CutAcceptance)map.get("cutAcceptance");
		String sublineIds = (String)map.get("sublineIds");
		List subline = (List)map.get("subline");
		Evaluate evaluate = (Evaluate)map.get("evaluate");
		List approve_info_list = (List)map.get("approve_info_list");
		String contractorId = (String)map.get("contractorId");
		request.setAttribute("cut",cut);
		request.setAttribute("cutFeedback",cutFeedback);
		request.setAttribute("cutAcceptance",cutAcceptance);
		request.setAttribute("sublineIds",sublineIds);
		request.setAttribute("subline",subline);
		request.setAttribute("evaluate",evaluate);
		request.setAttribute("approve_info_list",approve_info_list);
		request.setAttribute("contractorId", contractorId);
	%>
	<body>
		<template:titile value="查看详细信息"/>
		<c:if test="${not empty cut}">
			<jsp:include page="cut_apply_base.jsp"/>
		</c:if>
		<c:if test="${not empty cutFeedback}">
			<jsp:include page="cut_feedback_base.jsp"/>
		</c:if>
		<c:if test="${not empty cutAcceptance}">
			<jsp:include page="cut_acceptance_base.jsp"/>
		</c:if>
		<table cellspacing="0" cellpadding="1" align="center" style="width:90%;border-bottom:0px; border-top: 0px;" class="tabout">
			<tr class="trcolor">
				<td class="tdulleft" style="width:20%;">维护图纸：</td>
				<td class="tdulright" colspan="3">
					<c:if test="${not empty subline}">
						<table>
							<c:forEach items="${subline}" var="sub">
								<tr>
									<td>
										<c:out value="${sub[1]}"></c:out>&nbsp;&nbsp;&nbsp;&nbsp;
										<a onclick="toViewSublineData('${sub[0] }')" style="cursor: pointer;color: blue;">查看</a>
									</td>
								</tr>
							</c:forEach>
						</table>
					</c:if>
					<c:if test="${empty subline}">
						无资料更新
					</c:if>
				</td>
			</tr>
		</table>
		<apptag:appraiseDailyDaily businessId="${cutFeedback.id}" contractorId="${contractorId}" businessModule="lineCut" displayType="view" tableClass="tabout" tableStyle="width:90%;"></apptag:appraiseDailyDaily>
		<apptag:appraiseDailySpecial businessId="${cutFeedback.id}" contractorId="${contractorId}" businessModule="lineCut" displayType="view" tableClass="tabout" tableStyle="width:90%;"></apptag:appraiseDailySpecial>
		<table cellspacing="0" cellpadding="1" align="center" style="width:90%;border-bottom:0px; border-top: 0px;" class="tabout">
			<tr class="trcolor">
				<td align="center" colspan="4">
					<c:if test="${type=='win'}">
						<html:button property="button" styleClass="button" onclick="parent.close()">关闭</html:button>
					</c:if>
					<c:if test="${type=='view'}">
						<html:button property="button" styleClass="button" onclick="javascript:history.go(-1);">返回</html:button>
					</c:if>
					<c:if test="${empty type}">
						<html:button property="button" styleClass="button" onclick="toViewFinishCut('${cut.id}')">查看流程历史</html:button>&nbsp;&nbsp;
						<html:button property="button" styleClass="button" onclick="javascript:history.go(-1);">返回</html:button>
					</c:if>
				</td>
			</tr>
		</table>
	</body>
</html>
