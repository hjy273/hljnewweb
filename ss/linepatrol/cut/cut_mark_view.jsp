<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.linepatrol.cut.module.*" %>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>割接验收</title>
		<script type="text/javascript">
			function showAndHideCutFeedback(){
				var showCutFeedback = document.getElementById("showCutFeedback");
				var cutFeedbackDetailInfo = document.getElementById("cutFeedbackDetailInfo");
				if(showCutFeedback.innerText=="显示反馈信息"){
					cutFeedbackDetailInfo.style.display="block";
					showCutFeedback.innerText="隐藏反馈信息";
				}else{
					cutFeedbackDetailInfo.style.display="none";
					showCutFeedback.innerText="显示反馈信息";
				}
			}
			function hiddenAttch(){
				var attchShow=document.getElementById("attchShow");
				var isUpdateYes=document.getElementById("isUpdateYes");
				if(isUpdateYes.checked==true){
					attchShow.style.display="block";
				}else{
					attchShow.style.display="none";
				}
			}
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
		CutAcceptance cutAcceptance = (CutAcceptance)map.get("cutAcceptance");
		String sublineIds = (String)map.get("sublineIds");
		List approve_info_list = (List)map.get("approve_info_list");
		request.setAttribute("cut",cutBean);
		request.setAttribute("cutFeedback",cutFeedback);
		request.setAttribute("cutAcceptance",cutAcceptance);
		request.setAttribute("sublineIds",sublineIds);
		request.setAttribute("approve_info_list", approve_info_list);
	%>
	<body>
		<template:titile value="查看割接信息"/>
		<jsp:include page="cut_apply_base.jsp"/>
		<jsp:include page="cut_feedback_base.jsp"/>
		<jsp:include page="cut_acceptance_base.jsp"/>
		<table cellspacing="0" cellpadding="1" style="width:90%;border-top:0px;" align="center" class="tabout">
			<tr class="trcolor">
				<td align="center">
					<html:button property="button" styleClass="button" onclick="toViewFinishCut('${cut.id}')">查看流程历史</html:button>&nbsp;&nbsp;
					<html:button property="button" styleClass="button" onclick="javascript:history.go(-1);">返回</html:button>
				</td>
			</tr>
		</table>
	</body>
</html>
