<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />
<%@page import="com.cabletech.linepatrol.cut.dao.*" %>
<%@page import="com.cabletech.baseinfo.domainobjects.*" %>
<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
<html>
	<head>
		<title>显示割接申请列表</title>
		<script type="text/javascript">
			toViewForm=function(cutId){
            	window.location.href = "${ctx}/cutQueryStatAction.do?method=viewQueryCut&cutId="+cutId;
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
		<%
			BasicDynaBean object=null;
			Object cutId=null;
			Object workorder_id = null;	
		%>
	<body>
		<template:titile value="已办工作(<font color='red'>共${num }条已办</font>)"/>
		<!-- <div style="text-align:center;">
			<iframe
				src="${ctx}/cutAction.do?method=viewCutHistoryProcess&&task_name=${param.task_name }"
				frameborder="0" id="processGraphFrame" height="75" scrolling="no"
				width="95%"></iframe>
		</div> -->
		<display:table name="sessionScope.list" id="cut" pagesize="18">
			<logic:notEmpty name="cut">
			<bean:define id="sendUserId" name="cut"
				property="proposer" />
			<bean:define id="state" name="cut" property="cut_state"></bean:define>
			<display:column title="工单号" media="html"  sortable="true">
				<% object = (BasicDynaBean ) pageContext.findAttribute("cut");
	            if(object != null) {
	               cutId = object.get("id");
	               workorder_id = object.get("workorder_id");
				} %>
      			<a href="javascript:toViewForm('<%=cutId%>')"><%=workorder_id%></a> 
			</display:column>
			<display:column property="cut_name" title="割接名称" headerClass="subject"  sortable="true"/>
			<display:column property="cut_level" title="割接级别" headerClass="subject"  sortable="true"/>
			<display:column property="contractorname" title="代维单位" headerClass="subject"  sortable="true"/>
			<display:column property="apply_date" title="申请时间" headerClass="subject"  sortable="true"/>
			<display:column property="username" title="申请人" headerClass="subject"  sortable="true"/>
			<display:column property="cut_state" title="割接状态" headerClass="subject"  sortable="true"/>
			<display:column media="html" title="操作" >
				<a href="javascript:toViewForm('<bean:write name="cut" property="id"/>')">查看</a>
					<c:if test="${sessionScope.LOGIN_USER.deptype == '1'}">
						<c:if test="${state eq '申请待审批'}">
						| <a href="javascript:toCancelForm('<bean:write name="cut" property="id"/>')">取消</a>
						</c:if>
					</c:if>
			</display:column>
			</logic:notEmpty>
		</display:table>
	</body>
</html>
