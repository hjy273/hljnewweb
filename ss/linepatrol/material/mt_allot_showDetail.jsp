<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<script type="text/javascript" language="javascript">		
		exportList=function(){
			var url="${ctx}/materialAllotAction.do?method=exportAllotDetailList";
			self.location.replace(url);
		}
		function  goBack(){
			var url="${ctx}/materialAllotAction.do?method=queryMaterialAllotForm";
			self.location.replace(url);
		}
		</script>
		<title>������ϸ</title>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
		<style type="text/css">
		.subject{text-align:center;}
		</style>
	</head>
	<body>
		<br />	
		<template:titile value="���ϵ�������ϸһ����" />
		<display:table name="sessionScope.materialAllotItemss" id="currentRowObject" pagesize="18">
			<display:column property="oldconname" title="������ά" headerClass="subject"  sortable="true"/>	
			<display:column property="oldaddr" sortable="true" title="������ַ" headerClass="subject" />
			<display:column property="basename" title="��������" headerClass="subject"  sortable="true"/>	
			<display:column property="changedate" title="����ʱ��" headerClass="subject"  sortable="true"/>	
			<display:column property="newconname" title="�����ά" headerClass="subject"  sortable="true"/>	
			<display:column property="newaddr" sortable="true" title="�����ַ" headerClass="subject" />
			<display:column property="newstock" title="��������" headerClass="subject"  sortable="true"/>	 
			<display:column property="oldstock" title="��������" headerClass="subject"  sortable="true"/>	  
		</display:table>
		<table border="0" cellpadding="0" cellspacing="0" width="100%" style="border:0px">		
		    <tr>
		    	<td>
		    		 <logic:notEmpty name="materialAllotItemss">
					  	<a href="javascript:exportList()">����ΪExcel�ļ�</a>
					  </logic:notEmpty>
		    	</td>
		    </tr>
			<tr>
				<td style="text-align:center;">
					<!-- <input type="button" class="button" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')" value="����" >-->
					<input type="button" class="button" onclick="history.back()" value="����"/>
					
				</td>
			</tr>
			
		
			
		</table>
	</body>
</html>
