<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<script type="text/javascript" language="javascript">
		 toViewForm=function(idValue,isread){
            window.location.href = "${ctx}/testPlanAction.do?method=viewPaln&planId="+idValue+"&query=no&isread="+isread;
		}
		
		//��˼ƻ�
		 toAddPlanApproveForm=function(idValue,act){
            window.location.href = "${ctx}/testApproveAction.do?method=approveTestPlanForm&planid="+idValue+"&act="+act;
		}
		
		//���¼������
		 toAddDataApproveForm=function(idValue,act){
            window.location.href = "${ctx}/testApproveAction.do?method=approveTestDataForm&planid="+idValue+"&act="+act;
		}
		
		//�޸Ĳ��Լƻ�
		 toEditTestPlanForm=function(idValue){
            window.location.href = "${ctx}/testPlanAction.do?method=editTestPlanForm&planid="+idValue;
		}
		
		//¼������
		 toEnteringDateListForm=function(idValue,type){
		 	if(type=='1'){
		 	 	 window.location.href = "${ctx}/enteringCableDataAction.do?method=enteringDateListForm&planid="+idValue;
		 	}
		 	if(type=='2'){
		 	  	window.location.href = "${ctx}/enteringStationDataAction.do?method=enteringDateListForm&planid="+idValue;
		 	}
		}
		
		//����
		toExamForm=function(idValue){
            window.location.href = "${ctx}/testPlanExamAction.do?method=examForm&planid="+idValue;
		}
		
		//ȡ������
//		toCancelForm=function(planId){
//			var url;
//			if(confirm("ȷ��Ҫȡ���ü���ά��������")){
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
		<title>���Լƻ�һ����</title>
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
		<template:titile value="������Լƻ�һ����  (<font color='red'>${size}������</font>)" />
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
				<display:column property="contractorname" sortable="true" title="��ά��λ" headerClass="subject" />
			</c:if>
			<display:column property="test_plan_name" sortable="true" title="���Լƻ�����" headerClass="subject" />
			<display:column property="plantime" sortable="true" title="���Լƻ�ʱ��" headerClass="subject" />
			<display:column property="plantype" sortable="true" title="��������" headerClass="subject"/>
			<display:column property="plannum" sortable="true" title="�ƻ���������" headerClass="subject"/>
			<display:column property="testnum" sortable="true" title="ʵ�ʲ�������" headerClass="subject"/>
			<display:column property="state" sortable="true" title="״̬" headerClass="subject" />
			 <display:column media="html" title="����" >
				<% object = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
	           		 if(object != null) {
		      	 		id = object.get("id");
		      	 		state = object.get("state");
		      	 		planstate = object.get("planstate");
		      	 		type = object.get("type");
		      	 		isread = object.get("isread");
		      	 		
				} %>
				
				<a href="javascript:toViewForm('<%=id%>','<%=isread%>')">�鿴</a>
	            	<% if(planstate.equals("0") && isread.equals("false")){ %>
	            	| <a href="javascript:toAddPlanApproveForm('<%=id%>','approve')">��˼ƻ�</a>
	            	| <a href="javascript:toAddPlanApproveForm('<%=id%>','transfer')">ת��ƻ�</a>
	            	<%} 
	            	if(planstate.equals("1")||planstate.equals("00")){ %>
	            	| <a href="javascript:toEditTestPlanForm('<%=id%>')">�޸Ĳ��Լƻ�</a>
	            	<%} 
	            	if(planstate.equals("20") || planstate.equals("21")){%>
	            	| <a href="javascript:toEnteringDateListForm('<%=id%>','<%=type%>')">¼������</a>
	            	<%} 
	            	if(planstate.equals("30") && isread.equals("false")){%>
	            	| <a href="javascript:toAddDataApproveForm('<%=id%>','approve')">���¼������</a>
	            	| <a href="javascript:toAddDataApproveForm('<%=id%>','transfer')">ת��¼������</a>
	            	<%}
	            	if(planstate.equals("31")){%>
	            	| <a href="javascript:toEnteringDateListForm('<%=id%>','<%=type%>')">�޸�¼������</a>
	            	<%}
	            	if(planstate.equals("40")){%>
	            	|<a href="javascript:toExamForm('<%=id%>')">����</a>
	                <%}%>
	        <!--         <c:if test="${sessionScope.LOGIN_USER.deptype=='1'&&testState!='999'}">
						| <a href="javascript:toCancelForm('<%=id%>')">ȡ��</a>
					</c:if>  --> 
            </display:column>
		</display:table>
		</logic:notEmpty>
		<table border="0" cellpadding="0" cellspacing="0" width="100%" style="border:0px">		
		    <tr>
		    	<td>
		    		 <logic:notEmpty name="waitPlans1">
					  	<a href="javascript:exportList()">����ΪExcel�ļ�</a>
					  </logic:notEmpty>
		    	</td>
		    </tr>
		</table>
	</body>
</html>
