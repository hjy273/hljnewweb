<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<script type="text/javascript" language="javascript">
		 toViewForm=function(idValue){
            window.location.href = "${ctx}/troubleInfoAction.do?method=viewTroubleInfo&troubleid="+idValue;
		}
		
		 toExamForm=function(idValue){
            window.location.href = "${ctx}/troubleExamAction.do?method=examForm&troubleid="+idValue;
		}
		
		exportList=function(){
			//var url="${ctx}/troubleReplyAction.do?method=exportWaitReplyList";
			//self.location.replace(url);
		}
				
		</script>
		<title>代办故障一览表</title>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
		<style type="text/css">
		.subject{text-align:center;}
		</style>
	</head>
	<body>
		<br />
		<%
			BasicDynaBean object=null;
			Object id=null;		
			Object state="";
		 %>
		<template:titile value="代办故障一览表" />
		<display:table name="sessionScope.list" id="currentRowObject" pagesize="15">
			<display:column property="trouble_name" sortable="true" title="故障名称" headerClass="subject" />
			<display:column property="trouble_send_man" sortable="true" title="故障派发人" headerClass="subject" />
			<display:column property="trouble_start_time" sortable="true" title="故障发生时间" headerClass="subject"/>
			<display:column property="trouble_send_time" sortable="true" title="故障派发时间" headerClass="subject" />
			<display:column property="is_great_trouble" sortable="true" title="重大故障" headerClass="subject" maxLength="10"/>
			<display:column property="trouble_state" sortable="true" title="状态" headerClass="subject" maxLength="10"/>
			 <display:column media="html" title="操作" >
				<% object = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
	            if(object != null) {
	            	state = object.get("state");
	      	 		id = object.get("id");
				} %>
				<a href="javascript:toViewForm('<%=id%>')">查看</a>
	            	<% if(state.equals("40")){ %>
	            	| <a href="javascript:toExamForm('<%=id%>')">考核评估</a>
	            	<%} %>
	            	
            </display:column>
		</display:table>
		<table border="0" cellpadding="0" cellspacing="0" width="100%" style="border:0px">		
		    <tr>
		    	<td>
		    		 <logic:notEmpty name="list1">
					  	<a href="javascript:exportList()">导出为Excel文件</a>
					  </logic:notEmpty>
		    	</td>
		    </tr>
		</table>
	</body>
</html>
