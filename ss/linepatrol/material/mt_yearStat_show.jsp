<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<script type="text/javascript" language="javascript">
		exportList=function(){
			var url="${ctx}/materialYearStatAction.do?method=exportYearStatList";
			self.location.replace(url);
		}
		
		function  goBack(){
			var url="${ctx}/materialYearStatAction.do?method=materialStateForm";
			self.location.replace(url);
		}
		</script>
		<title>��������</title>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
		<style type="text/css">
		.subject{text-align:center;}
		</style>
	</head>
	<body>
		<br />		
		<template:titile value="������ͳ��һ����" />
		<display:table name="sessionScope.mtstatyear" id="currentRowObject" pagesize="18">
			<display:column property="modelname" title="��������" headerClass="subject"  sortable="true"/>	
			<display:column property="typename" sortable="true" title="���Ϲ��" headerClass="subject" maxLength="10"/>
			<display:column property="unit" sortable="true" title="������λ" headerClass="subject" maxLength="10"/>
			<display:column value="0" title="�ƻ���ģ" headerClass="subject" maxLength="10"/>
			<display:column property="use_number" sortable="true" title="�ۼ�ʹ������" headerClass="subject" maxLength="10"/>	
			<display:column property="jan" sortable="true" title="1�·�" headerClass="subject" maxLength="10"/>	   
			<display:column property="feb" sortable="true" title="2�·�" headerClass="subject" maxLength="10"/>	   
			<display:column property="mar" sortable="true" title="3�·�" headerClass="subject" maxLength="10"/>	   
			<display:column property="apr" sortable="true" title="4�·�" headerClass="subject" maxLength="10"/>	   
			<display:column property="may" sortable="true" title="5�·�" headerClass="subject" maxLength="10"/>	   
			<display:column property="june" sortable="true" title="6�·�" headerClass="subject" maxLength="10"/>	   
			<display:column property="july" sortable="true" title="7�·�" headerClass="subject" maxLength="10"/>	   
			<display:column property="aug" sortable="true" title="8�·�" headerClass="subject" maxLength="10"/>	   
			<display:column property="sep" sortable="true" title="9�·�" headerClass="subject" maxLength="10"/>	   
			<display:column property="oct" sortable="true" title="10�·�" headerClass="subject" maxLength="10"/>	   
			<display:column property="nov" sortable="true" title="11�·�" headerClass="subject" maxLength="10"/>	   
			<display:column property="dece" sortable="true" title="12�·�" headerClass="subject" maxLength="10"/>	   
		</display:table>
		<table border="0" cellpadding="0" cellspacing="0" width="100%" style="border:0px">		
		    <tr>
		    	<td>
		    		 <logic:notEmpty name="mtstatyear">
					  	<a href="javascript:exportList()">����ΪExcel�ļ�</a>
					  </logic:notEmpty>
		    	</td>
		    </tr>
			<tr>
				<td style="text-align:center;">
				<!-- <input name="action" class="button" value="����"
						onclick="goBack();" type="button" />
						-->
						<input type="button" class="button" onclick="history.back()" value="����"/>
				</td>
			</tr>
			
		
			
		</table>
	</body>
</html>
