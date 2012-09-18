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
		<title>�������һ����</title>
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
		<template:titile value="�������һ����" />
		<display:table name="sessionScope.list" id="currentRowObject" pagesize="15">
			<display:column property="trouble_name" sortable="true" title="��������" headerClass="subject" />
			<display:column property="trouble_send_man" sortable="true" title="�����ɷ���" headerClass="subject" />
			<display:column property="trouble_start_time" sortable="true" title="���Ϸ���ʱ��" headerClass="subject"/>
			<display:column property="trouble_send_time" sortable="true" title="�����ɷ�ʱ��" headerClass="subject" />
			<display:column property="is_great_trouble" sortable="true" title="�ش����" headerClass="subject" maxLength="10"/>
			<display:column property="trouble_state" sortable="true" title="״̬" headerClass="subject" maxLength="10"/>
			 <display:column media="html" title="����" >
				<% object = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
	            if(object != null) {
	            	state = object.get("state");
	      	 		id = object.get("id");
				} %>
				<a href="javascript:toViewForm('<%=id%>')">�鿴</a>
	            	<% if(state.equals("40")){ %>
	            	| <a href="javascript:toExamForm('<%=id%>')">��������</a>
	            	<%} %>
	            	
            </display:column>
		</display:table>
		<table border="0" cellpadding="0" cellspacing="0" width="100%" style="border:0px">		
		    <tr>
		    	<td>
		    		 <logic:notEmpty name="list1">
					  	<a href="javascript:exportList()">����ΪExcel�ļ�</a>
					  </logic:notEmpty>
		    	</td>
		    </tr>
		</table>
	</body>
</html>
