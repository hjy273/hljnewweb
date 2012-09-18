<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />
<%@page import="com.cabletech.linepatrol.cut.dao.*" %>
<%@page import="com.cabletech.baseinfo.domainobjects.*" %>
<html>
	<head>
		<title>显示割接申请列表</title>
		<script type="text/javascript">
			//查看申请
			toViewCutForm=function(cutId){
            	window.location.href = "${ctx}/cutAction.do?method=viewCut&cutId="+cutId;
			}
			toViewReadCutForm=function(cutId,isread){
            	window.location.href = "${ctx}/cutAction.do?method=viewCut&cutId="+cutId+"&isread="+isread;
			}
			//割接申请审批与转审
			toApproveCutForm=function(cutId,operator){
            	window.location.href = "${ctx}/cutAction.do?method=approveCutApplyForm&cutId="+cutId+"&operator="+operator;
			}
			//编辑割接申请
			toEditForm=function(cutId){
            	window.location.href = "${ctx}/cutAction.do?method=editCutApplyForm&cutId="+cutId;
			}
			//填写割接申请反馈
			toAddFeedbackForm=function(cutId){
            	window.location.href = "${ctx}/cutFeedbackAction.do?method=addCutFeedbackForm&cutId="+cutId;
			}
			//查看反馈
			toViewCutFeedback=function(cutId){
            	window.location.href = "${ctx}/cutFeedbackAction.do?method=viewCutFeedback&cutId="+cutId;
			}
			toViewReadCutFeedback=function(cutId,isread){
            	window.location.href = "${ctx}/cutFeedbackAction.do?method=viewCutFeedback&cutId="+cutId+"&isread="+isread;
			}
			//割接申请反馈审批
			toApproveCutFeedbackForm=function(cutId,operator){
            	window.location.href = "${ctx}/cutFeedbackAction.do?method=addCutFeedbackApproveForm&cutId="+cutId+"&operater="+operator;
			}
			//编辑割接反馈
			toEditFeedbackForm=function(cutId){
            	window.location.href = "${ctx}/cutFeedbackAction.do?method=editCutFeedbackForm&cutId="+cutId;
			}
			//添加割接验收结算
			toAddAcceptanceForm=function(cutId){
            	window.location.href = "${ctx}/cutAcceptanceAction.do?method=addCutAcceptanceForm&cutId="+cutId;
			}
			//查看验收结算
			toViewCutAcceptance=function(cutId){
            	window.location.href = "${ctx}/cutAcceptanceAction.do?method=viewCutAcceptance&cutId="+cutId;
			}
			toViewReadCutAcceptance=function(cutId,isread){
            	window.location.href = "${ctx}/cutAcceptanceAction.do?method=viewCutAcceptance&cutId="+cutId+"&isread="+isread;
			}
			//验收结算审批
			toApproveCutAcceptanceForm=function(cutId,operator){
            	window.location.href = "${ctx}/cutAcceptanceAction.do?method=cutAcceptanceApproveForm&cutId="+cutId+"&operater="+operator;
			}
			//修改验收结算
			toEditAcceptanceForm=function(cutId){
            	window.location.href = "${ctx}/cutAcceptanceAction.do?method=editCutAcceptanceForm&cutId="+cutId;
			}
			//考核评分
			toCutRemarkForm=function(cutId){
            	window.location.href = "${ctx}/checkAndMarkAction.do?method=checkAndMarkFrom&cutId="+cutId;
			}
			//申请作废
			toInvalidCutForm=function(cutId){
				if(confirm("请确认是否作废该申请？")){
            		window.location.href = "${ctx}/cutAction.do?method=invalidCut&cutId="+cutId;
            	}
			}
		//取消割接流程
		toCancelForm=function(cutId){
			var url;
			if(confirm("确定要取消该割接流程吗？")){
				url="${ctx}/cutAction.do?method=cancelCutForm";
				var queryString="cutId="+cutId;
				//location=url+"&"+queryString+"&rnd="+Math.random();
				window.open(url+"&"+queryString+"&rnd="+Math.random(),'','width=300,height=200,top=0,left=0,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no');
			}
		}
		</script>
	</head>
	<%
		BasicDynaBean object=null;
		String proposer=null;
	%>
	<body>
		
		<c:if test="${empty type}">
			<template:titile value="全部待办工作 (<font color='red'>共${num }条待办</font>)"/>
		</c:if>
		<c:if test="${type=='apply'}">
			<template:titile value="割接申请待办工作 (<font color='red'>共${num }条待办</font>)"/>
		</c:if>
		<c:if test="${type=='feedback'}">
			<template:titile value="割接反馈待办工作 (<font color='red'>共${num }条待办</font>)"/>
		</c:if>
		<c:if test="${type=='acceptance'}">
			<template:titile value="验收结算待办工作 (<font color='red'>共${num }条待办</font>)"/>
		</c:if>
		<div style="text-align:center;">
			<iframe
				src="${ctx}/cutAction.do?method=viewCutProcess&&task_name=${param.task_name }"
				frameborder="0" id="processGraphFrame" height="75" scrolling="no"
				width="95%"></iframe>
		</div>
		<display:table name="sessionScope.list" id="cut" pagesize="18">
		<logic:notEmpty name="list">
			<bean:define id="sendUserId" name="cut" property="proposer" />
			<bean:define id="state" name="cut" property="state" />
			<display:column property="workorder_id" title="工单号" headerClass="subject"  sortable="true"/>
			<display:column property="cut_name" title="割接名称" headerClass="subject"  sortable="true"/>
			<display:column property="cut_level" title="割接级别" headerClass="subject"  sortable="true"/>
			<display:column property="contractorname" title="代维单位" headerClass="subject"  sortable="true"/>
			<display:column property="apply_date" title="申请时间" headerClass="subject"  sortable="true"/>
			<display:column property="username" title="申请人" headerClass="subject"  sortable="true"/>
			<display:column property="cut_state" title="割接状态" headerClass="subject"  sortable="true"/>
			<display:column media="html" title="操作" >
					<c:if test="${state eq '1' }">
						<logic:equal value="true" name="cut" property="isread">
							<a href="javascript:toViewReadCutForm('<bean:write name="cut" property="id"/>','isread')">查看</a>
						</logic:equal>
						<logic:equal value="false" name="cut" property="isread">
							<a href="javascript:toViewCutForm('<bean:write name="cut" property="id"/>')">查看</a> | 
							<a href="javascript:toApproveCutForm('<bean:write name="cut" property="id"/>','approve')" title="割接申请审批">审批</a> | 
							<a href="javascript:toApproveCutForm('<bean:write name="cut" property="id"/>','transfer')">转审</a>
						</logic:equal>
					</c:if>
					<c:if test="${state eq '2' }">
						<a href="javascript:toViewCutForm('<bean:write name="cut" property="id"/>')">查看</a> | 
						<a href="javascript:toEditForm('<bean:write name="cut" property="id"/>')">修改申请</a> | 
						<a href="javascript:toInvalidCutForm('<bean:write name="cut" property="id"/>')">作废</a>
					</c:if>
					<c:if test="${state eq '3' }">
						<a href="javascript:toViewCutForm('<bean:write name="cut" property="id"/>')">查看</a> | 
						<a href="javascript:toAddFeedbackForm('<bean:write name="cut" property="id"/>')">填写反馈</a>
					</c:if>
					<c:if test="${state eq '4' }">
						<logic:equal value="true" name="cut" property="isread">
							<a href="javascript:toViewReadCutFeedback('<bean:write name="cut" property="id"/>','isread')">查看</a>
						</logic:equal>
						<logic:equal value="false" name="cut" property="isread">
							<a href="javascript:toViewCutFeedback('<bean:write name="cut" property="id"/>')">查看</a> | 
							<a href="javascript:toApproveCutFeedbackForm('<bean:write name="cut" property="id"/>','approve')" title="割接反馈审批">审批</a> | 
							<a href="javascript:toApproveCutFeedbackForm('<bean:write name="cut" property="id"/>','transfer')">转审</a>
						</logic:equal>
					</c:if>
					<c:if test="${state eq '5' }">
						<a href="javascript:toViewCutFeedback('<bean:write name="cut" property="id"/>')">查看</a> | 
						<a href="javascript:toEditFeedbackForm('<bean:write name="cut" property="id"/>')">修改反馈</a>
					</c:if>
					<c:if test="${state eq '6' }">
						<a href="javascript:toViewCutFeedback('<bean:write name="cut" property="id"/>')">查看</a> | 
						<a href="javascript:toAddAcceptanceForm('<bean:write name="cut" property="id"/>')">填写结算</a>
					</c:if>
					<c:if test="${state eq '7' }">
						<logic:equal value="true" name="cut" property="isread">
							<a href="javascript:toViewReadCutAcceptance('<bean:write name="cut" property="id"/>','isread')">查看</a>
						</logic:equal>
						<logic:equal value="false" name="cut" property="isread">
							<a href="javascript:toViewCutAcceptance('<bean:write name="cut" property="id"/>')">查看</a> | 
							<a href="javascript:toApproveCutAcceptanceForm('<bean:write name="cut" property="id"/>','approve')" title="割接验收结算审批">审批</a> | 
							<a href="javascript:toApproveCutAcceptanceForm('<bean:write name="cut" property="id"/>','transfer')">转审</a>
						</logic:equal>
					</c:if>
					<c:if test="${state eq '8' }">
						<a href="javascript:toViewCutAcceptance('<bean:write name="cut" property="id"/>')">查看</a> |
						<a href="javascript:toEditAcceptanceForm('<bean:write name="cut" property="id"/>')">修改结算</a>
					</c:if>
					<c:if test="${state eq '9' }">
						<a href="javascript:toViewCutAcceptance('<bean:write name="cut" property="id"/>')">查看</a> |
						<a href="javascript:toCutRemarkForm('<bean:write name="cut" property="id"/>')">考核评估</a>
					</c:if>
					<c:if test="${sessionScope.LOGIN_USER.deptype == '1'}">
						<c:if test="${state eq '1'}">
						| <a href="javascript:toCancelForm('<bean:write name="cut" property="id"/>')">取消</a>
						</c:if>
					</c:if>
			</display:column>
			</logic:notEmpty>
		</display:table>
	</body>
</html>
