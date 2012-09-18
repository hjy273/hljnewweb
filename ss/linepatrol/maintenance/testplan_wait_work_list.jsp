<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<script type="text/javascript" language="javascript">
		 toViewForm=function(idValue,isread){
            window.location.href = "${ctx}/testPlanAction.do?method=viewPaln&planId="+idValue+"&query=no&isread="+isread;
		}
		
		//审核计划
		 toAddPlanApproveForm=function(idValue,act){
            window.location.href = "${ctx}/testApproveAction.do?method=approveTestPlanForm&planid="+idValue+"&act="+act;
		}
		
		//审核录入数据
		 toAddDataApproveForm=function(idValue,act){
            window.location.href = "${ctx}/testApproveAction.do?method=approveTestDataForm&planid="+idValue+"&act="+act;
		}
		
		//修改测试计划
		 toEditTestPlanForm=function(idValue){
            window.location.href = "${ctx}/testPlanAction.do?method=editTestPlanForm&planid="+idValue;
		}
		
		//录入数据
		 toEnteringDateListForm=function(idValue,type){
		 	if(type=='1'){
		 	 	 window.location.href = "${ctx}/enteringCableDataAction.do?method=enteringDateListForm&planid="+idValue;
		 	}
		 	if(type=='2'){
		 	  	window.location.href = "${ctx}/enteringStationDataAction.do?method=enteringDateListForm&planid="+idValue;
		 	}
		}
		
		//考核
		toExamForm=function(idValue){
            window.location.href = "${ctx}/testPlanExamAction.do?method=examForm&planid="+idValue;
		}
		
		//取消流程
//		toCancelForm=function(planId){
//			var url;
//			if(confirm("确定要取消该技术维护流程吗？")){
//				url="${ctx}/testPlanAction.do?method=cancelTestPlanForm";
//				var queryString="test_plan_id="+planId;
//				//location=url+"&"+queryString+"&rnd="+Math.random();
//				window.open(url+"&"+queryString+"&rnd="+Math.random(),'','width=300,height=200,top=0,left=0,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no');
//			}
	//	}
		
		exportList=function(){
			//var url="${ctx}/troubleReplyAction.do?method=exportWaitReplyList";
			//self.location.replace(url);
		}
				
		</script>
		<title>测试计划一览表</title>
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
			Object planstate="";
			Object type="";
			Object isread="";
		 %>
		 <bean:size id="size"  name="waitPlans" />
		<template:titile value="待办测试计划一览表  (<font color='red'>${size}条待办</font>)" />
		<div style="text-align:center;">
			<iframe
				src="${ctx}/testPlanAction.do?method=viewTestPlanProcess&&task_name=${param.task_name }"
				frameborder="0" id="processGraphFrame" height="100" scrolling="no"
				width="95%"></iframe>
		</div>
		<logic:notEmpty name="waitPlans">
		<display:table name="sessionScope.waitPlans" id="currentRowObject" pagesize="15">
			<bean:define name="currentRowObject" property="creator_id" id="sendUserId" />
		 	<bean:define id="testState" name="currentRowObject" property="test_state"></bean:define>
			<c:if test="${LOGIN_USER.deptype eq 1}">
				<display:column property="contractorname" sortable="true" title="代维单位" headerClass="subject" />
			</c:if>
			<display:column property="test_plan_name" sortable="true" title="测试计划名称" headerClass="subject" />
			<display:column property="plantime" sortable="true" title="测试计划时间" headerClass="subject" />
			<display:column property="plantype" sortable="true" title="测试类型" headerClass="subject"/>
			<display:column property="plannum" sortable="true" title="计划测试数量" headerClass="subject"/>
			<display:column property="testnum" sortable="true" title="实际测试数量" headerClass="subject"/>
			<display:column property="state" sortable="true" title="状态" headerClass="subject" />
			 <display:column media="html" title="操作" >
				<% object = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
	           		 if(object != null) {
		      	 		id = object.get("id");
		      	 		state = object.get("state");
		      	 		planstate = object.get("planstate");
		      	 		type = object.get("type");
		      	 		isread = object.get("isread");
		      	 		
				} %>
				
				<a href="javascript:toViewForm('<%=id%>','<%=isread%>')">查看</a>
	            	<% if(planstate.equals("0") && isread.equals("false")){ %>
	            	| <a href="javascript:toAddPlanApproveForm('<%=id%>','approve')">审核计划</a>
	            	| <a href="javascript:toAddPlanApproveForm('<%=id%>','transfer')">转审计划</a>
	            	<%} 
	            	if(planstate.equals("1")||planstate.equals("00")){ %>
	            	| <a href="javascript:toEditTestPlanForm('<%=id%>')">修改测试计划</a>
	            	<%} 
	            	if(planstate.equals("20") || planstate.equals("21")){%>
	            	| <a href="javascript:toEnteringDateListForm('<%=id%>','<%=type%>')">录入数据</a>
	            	<%} 
	            	if(planstate.equals("30") && isread.equals("false")){%>
	            	| <a href="javascript:toAddDataApproveForm('<%=id%>','approve')">审核录入数据</a>
	            	| <a href="javascript:toAddDataApproveForm('<%=id%>','transfer')">转审录入数据</a>
	            	<%}
	            	if(planstate.equals("31")){%>
	            	| <a href="javascript:toEnteringDateListForm('<%=id%>','<%=type%>')">修改录入数据</a>
	            	<%}
	            	if(planstate.equals("40")){%>
	            	|<a href="javascript:toExamForm('<%=id%>')">考核</a>
	                <%}%>
	        <!--         <c:if test="${sessionScope.LOGIN_USER.deptype=='1'&&testState!='999'}">
						| <a href="javascript:toCancelForm('<%=id%>')">取消</a>
					</c:if>  --> 
            </display:column>
		</display:table>
		</logic:notEmpty>
		<table border="0" cellpadding="0" cellspacing="0" width="100%" style="border:0px">		
		    <tr>
		    	<td>
		    		 <logic:notEmpty name="waitPlans1">
					  	<a href="javascript:exportList()">导出为Excel文件</a>
					  </logic:notEmpty>
		    	</td>
		    </tr>
		</table>
	</body>
</html>
