<%@include file="/wap/header.jsp"%>
<!--%@include file="/common/listhander.jsp"%-->
<%@ page language="java" contentType="text/html; charset=GBK"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
	media="screen, print" />
<html>
	<head>
		<title>显示割接申请列表</title>
		<META HTTP-EQUIV="expires" CONTENT="Wed, 26 Feb 1997 08:21:57 GMT">
		<META HTTP-EQUIV="expires" CONTENT="0">
		<script type="text/javascript">
		goBack=function(){
			var url="${ctx}/wap/login.do?method=index&&env=wap";
			location=url+"&&env=wap&&rnd="+Math.random();
		}
	toViewReadCutForm = function(cutId, isread) {
		window.location.href = "${ctx}/wap/cutAction.do?method=viewCut&&env=wap&cutId="
				+ cutId + "&isread=" + isread;
	}
	toViewReadCutFeedback = function(cutId, isread) {
		window.location.href = "${ctx}/wap/cutFeedbackAction.do?method=viewCutFeedback&&env=wap&cutId="
				+ cutId + "&isread=" + isread;
	}
	toViewReadCutAcceptance = function(cutId, isread) {
		window.location.href = "${ctx}/wap/cutAcceptanceAction.do?method=viewCutAcceptance&&env=wap&cutId="
				+ cutId + "&isread=" + isread;
	}
	toApproveCutForm = function(cutId, operator) {
		window.location.href = "${ctx}/wap/cutAction.do?method=approveCutApplyForm&&env=wap&cutId="
				+ cutId + "&operator=" + operator;
	}
	toApproveCutFeedbackForm = function(cutId, operator) {
		window.location.href = "${ctx}/wap/cutFeedbackAction.do?method=addCutFeedbackApproveForm&&env=wap&cutId="
				+ cutId + "&operater=" + operator;
	}
	toApproveCutAcceptanceForm = function(cutId, operator) {
		window.location.href = "${ctx}/wap/cutAcceptanceAction.do?method=cutAcceptanceApproveForm&&env=wap&cutId="
				+ cutId + "&operater=" + operator;
	}
	toTransferApproveCutForm = function(cutId, operator) {
		var actionUrl="${ctx}/wap/cutAction.do?method=approveCutApplyForm&&env=wap";
		var queryString="cutId=" + cutId + "&operator=" + operator;
		var form1=document.forms["toApproverUrlForm"];
		form1.action_url.value=actionUrl+"&"+queryString+"&rnd="+Math.random();
		form1.object_id.value=cutId;
		form1.object_type.value="LP_CUT";
		form1.submit();
	}
	toTransferApproveCutFeedbackForm = function(cutId, operator) {
		var actionUrl="${ctx}/wap/cutFeedbackAction.do?method=addCutFeedbackApproveForm&&env=wap";
		var queryString="cutId=" + cutId + "&operater=" + operator;
		var form1=document.forms["toApproverUrlForm"];
		form1.action_url.value=actionUrl+"&"+queryString+"&rnd="+Math.random();
		form1.object_id.value=cutId;
		form1.approver_input_name.value="approvers,mobiles";
		form1.object_type.value="LP_CUT_FEEDBACK";
		form1.submit();
	}
	toTransferApproveCutAcceptanceForm = function(cutId, operator) {
		var actionUrl="${ctx}/wap/cutAcceptanceAction.do?method=cutAcceptanceApproveForm&&env=wap";
		var queryString="cutId=" + cutId + "&operater=" + operator;
		var form1=document.forms["toApproverUrlForm"];
		form1.action_url.value=actionUrl+"&"+queryString+"&rnd="+Math.random();
		form1.object_id.value=cutId;
		form1.object_type.value="LP_CUT_ACCEPTANCE";
		form1.submit();
	}
</script>
	</head>
	<body>
		<template:titile value="待办工作 (<font color='red'>共${num }条待办</font>)" />
		<p>
			<input type="button" value="返回首页" onclick="goBack();">
		</p>
		<logic:notEmpty name="list">
			<%
				List list = (List) session.getAttribute("list");
					DynaBean bean;
					for (int i = 0; list != null && i < list.size(); i++) {
						bean = (DynaBean) list.get(i);
			%>
			<p>
				<%=(String) bean.get("cut_name")%>&nbsp;&nbsp;<%=(String) bean.get("cut_level")%><br/>
				<%=(String) bean.get("apply_date")%>&nbsp;&nbsp;<%=(String) bean.get("username")%><br/>
				<%=(String) bean.get("cut_state")%><br/>
				<%
					if ("申请待审批".equals(bean.get("cut_state"))) {
						if ("true".equals(bean.get("isread"))) {
				%>
				<a
					href="javascript:toViewReadCutForm('<%=(String)bean.get("id") %>','isread')">查看</a>
				<%
						} else {
				%>
				<a
					href="javascript:toApproveCutForm('<%=(String)bean.get("id") %>','approve')"
					title="割接申请审批">审批</a>
				<a
					href="javascript:toTransferApproveCutForm('<%=(String)bean.get("id") %>','transfer')">转审</a>
				<%
						}
					}
					if ("申请反馈待审批".equals(bean.get("cut_state"))) {
						if ("true".equals(bean.get("isread"))) {
				%>
				<a
					href="javascript:toViewReadCutFeedback('<%=(String)bean.get("id") %>','isread')">查看</a>
				<%
						} else {
				%>
				<a
					href="javascript:toApproveCutFeedbackForm('<%=(String)bean.get("id") %>','approve')"
					title="割接反馈审批">审批</a> |
				<a
					href="javascript:toTransferApproveCutFeedbackForm('<%=(String)bean.get("id") %>','transfer')">转审</a>
				<%
						}
					}
					if ("验收结算待审批".equals(bean.get("cut_state"))) {
						if ("true".equals(bean.get("isread"))) {
				%>
				<%
						} else {
				%>
				<%
						}
					}
				%>
			</p>
			<%
				}
			%>
		</logic:notEmpty>
		<p>
			<input type="button" value="返回首页" onclick="goBack();">
		</p>
		<div style="display:none;">
			<form id="toApproverUrlForm" method="post"
				action="${ctx}/wap/load_approvers.do?method=loadWapApprovers">
				<input name="display_type" value="mobile" type="hidden" />
				<input name="exist_value" value="" type="hidden" />
				<input name="object_id" value="" type="hidden" />
				<input name="object_type" value="LP_CUT" type="hidden" />
				<input name="except_self" value="true" type="hidden" />
				<input name="action_url" value="" type="hidden" />
				<input name="depart_id" value="" type="hidden" />
				<input name="approver_type" value="transfer" type="hidden" />
				<input name="approver_input_name" value="approvers,mobile" type="hidden" />
				<input name="reader_input_name" value="" type="hidden" />
			</form>
		</div>
	</body>
</html>
