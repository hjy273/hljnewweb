<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<script type="text/javascript" language="javascript">
		 toViewForm=function(idValue,isread,flowState){
           // window.location.href = "${ctx}/troubleInfoAction.do?method=viewTroubleInfo&troubleid="+idValue;
           window.location.href = "${ctx}/troubleQueryStatAction.do?method=viewTrouble&troubleid="+idValue+"&isread="+isread+"&flowState="+flowState+"&queryact=no";
		}
		
		 toAddReplyForm=function(idValue,unitid,state){
            window.location.href = "${ctx}/troubleReplyAction.do?method=addReplyForm&troubleid="+idValue+"&unitid="+unitid+"&state="+state;
		}
		
		//平台审核
		 toEditDispatchForm=function(idValue,unitid,prounit){
            window.location.href = "${ctx}/troubleReplyAction.do?method=editDispatch&troubleid="+idValue+"&unitid="+unitid+"&processUnit="+prounit;
		 }
		
		 toAddApproveForm=function(idValue,act){
            window.location.href = "${ctx}/replyApproveAction.do?method=addApproveForm&troubleid="+idValue+"&act="+act;
		}
		
		 toExamForm=function(idValue){
            window.location.href = "${ctx}/troubleExamAction.do?method=examForm&troubleid="+idValue;
		}
		
		
		exportList=function(){
			//var url="${ctx}/troubleReplyAction.do?method=exportWaitReplyList";
			//self.location.replace(url);
		}
				
		toCancelForm=function(idValue){
			var url;
			if(confirm("确定要取消该故障流程吗？")){
				url="${ctx}/troubleInfoAction.do?method=cancelTroubleForm";
				var queryString="troubleId="+idValue;
				//location=url+"&"+queryString+"&rnd="+Math.random();
				window.open(url+"&"+queryString+"&rnd="+Math.random(),'','width=300,height=200,top=0,left=0,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no');
			}
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
		<%
			BasicDynaBean object=null;
			Object id=null;		
			Object uid="";
			Object state="";
			Object flowState="";
			Object isread="";
			Object conid="";
			Object havereply="";
		 %>
		 <bean:size id="size"  name="waitReplys" />
		<template:titile value="待办故障一览表  (<font color='red'>${size}条待办</font>)" />
		<div style="text-align:center;">
			<iframe
				src="${ctx}/troubleReplyAction.do?method=viewTroubleProcess&&task_name=${param.task_name }"
				frameborder="0" id="processGraphFrame" height="100" scrolling="no"
				width="95%"></iframe>
		</div>
		<display:table name="sessionScope.waitReplys" id="currentRowObject" pagesize="15" defaultsort="5" defaultorder="descending" sort="list">
			<logic:notEmpty name="currentRowObject">
			<bean:define id="sendUserId" name="currentRowObject" property="send_man_id" />
			
			</logic:notEmpty>
			
		    <display:column property="trouble_id" sortable="true" title="故障单单号" headerClass="subject" />
			<display:column property="trouble_name" sortable="true" title="故障名称" headerClass="subject" />
			<display:column property="trouble_send_man" sortable="true" title="故障派发人" headerClass="subject" />
			<display:column property="trouble_start_time" sortable="true" title="故障发生时间" headerClass="subject"/>
			<display:column property="trouble_send_time" sortable="true" title="故障派发时间" headerClass="subject" />
			<display:column property="is_great_trouble" sortable="true" title="重大故障" headerClass="subject" maxLength="10"/>
			 <display:column media="html" title="操作" >
				<% object = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
	            if(object != null) {
	            	uid = object.get("unitid");
	            	conid= object.get("process_unit_id");
	            	state = object.get("state");
	      	 		id = object.get("id");
	      	 		flowState = object.get("flow_state"); 
	      	 		isread = object.get("isread");
	      	 		havereply = object.get("havereply");
	      	 		if(havereply==null){
	      	 			havereply="";
	      	 		}
	      	 		//reply_task ,approve_task,evaluate_task,edit_dispatch_task
				} %>
				
				<a href="javascript:toViewForm('<%=id%>','<%=isread%>','<%=flowState%>')">查看</a>
	            	<% if(flowState.equals("reply_task")){ %>
	            	| <a href="javascript:toAddReplyForm('<%=id%>','<%=uid%>','<%=flowState%>')">反馈</a>
	            	<%} 
	            	 if(flowState.equals("edit_dispatch_task")){ %>
	            	| <a href="javascript:toAddApproveForm('<%=id%>','dispatch')">平台核准</a>
	            	<%} 
	            	if(flowState.equals("approve_task") && isread.equals("false") && havereply.equals("y")){%>
	            	| <a href="javascript:toAddApproveForm('<%=id%>','')">审核</a>
	            	<%} 
	            	if(flowState.equals("evaluate_task")){%>
	            	| <a href="javascript:toExamForm('<%=id%>')">考核</a>
	            	<%}%>
					<c:if test="${sessionScope.deptype== '1' && state == '0'}">
						| <a href="javascript:toCancelForm('<%=id%>')">取消</a>
					</c:if>
            </display:column>
		</display:table>
		<table border="0" cellpadding="0" cellspacing="0" width="100%" style="border:0px">		
		    <tr>
		    	<td>
		    		 <logic:notEmpty name="waitReplys1">
					  	<a href="javascript:exportList()">导出为Excel文件</a>
					  </logic:notEmpty>
		    	</td>
		    </tr>
		</table>
	</body>
</html>
