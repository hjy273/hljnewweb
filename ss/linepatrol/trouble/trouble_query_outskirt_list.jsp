<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<script type="text/javascript" language="javascript">
		
		
		 toViewForm=function(idValue){
            	window.location.href = "${ctx}/troubleQueryStatAction.do?method=viewTrouble&troubleid="+idValue;
		}
		
		exportList=function(){
			//var url="${ctx}/";
			//self.location.replace(url);
		}
		
		function  goBack(){
			var url="${ctx}/troubleQueryStatAction.do?method=queryTroubleForm";
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
		<%
		BasicDynaBean object=null;
		Object troubleid=null;
		Object tid=null;	
		Object replyid = null;	
		 %>
		<template:titile value="��������һ����" />
		<display:table name="sessionScope.troubles" id="currentRowObject" pagesize="15">
		<display:column media="html" title="���ϵ����"  sortable="true">
				<% object = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
	            if(object != null) {
	               troubleid = object.get("trouble_id");
	               tid = object.get("id");
	               replyid = object.get("replyid");
				} %>
      			<a href="javascript:toViewForm('<%=tid%>')"><%=troubleid%></a> 
      		</display:column>	
      		<display:column property="trouble_name" title="��������" headerClass="subject" maxLength="12" sortable="true"/>	
			<display:column property="contractorname" title="�¹ʴ���λ" headerClass="subject" maxLength="8" sortable="true"/>	
			<display:column property="trunk" sortable="true" title="�����м̶�" headerClass="subject" maxLength="15"/>
			<display:column property="trouble_send_time" sortable="true" title="�����ɷ�ʱ��" headerClass="subject" />
			<display:column property="etime" title="������ʱ(Сʱ)" headerClass="subject"  sortable="true"/>	
		</display:table>
		<table border="0" cellpadding="0" cellspacing="0" width="100%" style="border:0px">		
		    <tr>
		    	<td>
		    		 <logic:notEmpty name="troubles1">
					  	<a href="javascript:exportList()">����ΪExcel�ļ�</a>
					  </logic:notEmpty>
		    	</td>
		    </tr>
			<tr>
				<td style="text-align:center;">
					<input name="action" class="button" value="����"
						onclick="goBack();" type="button" />
				</td>
			</tr>
			
		
			
		</table>
	</body>
</html>
