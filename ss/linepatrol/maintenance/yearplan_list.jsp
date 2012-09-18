<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<script type="text/javascript" language="javascript">
			 toViewForm=function(idValue,isread){
	            	window.location.href = "${ctx}/testYearPlanAction.do?method=viewYearPlanForm&planid="+idValue+"&query=no&isread="+isread;
			 }
			 
			 toEditForm=function(idValue){
	            	window.location.href = "${ctx}/testYearPlanAction.do?method=editYearPlanForm&planid="+idValue;
			 }
			 
			  toApproveForm=function(idValue,act){
	            	window.location.href = "${ctx}/testYearPlanAction.do?method=approverPlanForm&planid="+idValue+"&act="+act;
			 }
			 
			  toDelete=function(idValue){
			       if(confirm("确定要删除吗?")){
	            		window.location.href = "${ctx}/testYearPlanAction.do?method=deleteYearPlan&planid="+idValue;
	            	}
			 }
			
			function  goBack(){
				var url="${ctx}/expensesAction.do?method=addCableTypePrice";
				self.location.replace(url);
			}
		</script>
		<title></title>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
		<style type="text/css">
		.subject{text-align:center;}
		</style>
	</head>
	<body>
		<br />

		<template:titile value="待办年计划一览表" />
		<display:table name="sessionScope.yearplans" id="currentRowObject" pagesize="15">
      		<display:column property="contractorname" title="代维单位" headerClass="subject"  sortable="true"/>	
			<display:column property="plan_name" title="计划名称" headerClass="subject"  sortable="true"/>	
			<display:column property="year" sortable="true" title="计划年份" headerClass="subject" />
			<display:column property="create_time" sortable="true" title="创建时间" headerClass="subject" />
			 <display:column media="html" title="操作" >
			 <bean:define id="tid" name="currentRowObject" property="id"></bean:define>
			 <bean:define id="read" name="currentRowObject" property="isread"></bean:define>
			  <a href="javascript:toViewForm('<%=tid%>','<%=read%>')">查看</a>
			 <logic:equal value="1" property="deptype" name="LOGIN_USER">
			 	<logic:equal value="false" property="isread" name="currentRowObject">
					 <a href="javascript:toApproveForm('<%=tid%>','approve')">|审核</a>
					  <a href="javascript:toApproveForm('<%=tid%>','transfer')">|转审</a>
			 	</logic:equal>
			 </logic:equal>
			  <logic:equal value="2" property="deptype" name="LOGIN_USER">
				 <a href="javascript:toEditForm('<%=tid%>')">|修改</a>
				 <a href="javascript:toDelete('<%=tid%>')">|删除</a>
			 </logic:equal>
            </display:column>
		</display:table>
	</body>
</html>
