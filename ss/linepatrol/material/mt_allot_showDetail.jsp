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
		<title>调拨明细</title>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
		<style type="text/css">
		.subject{text-align:center;}
		</style>
	</head>
	<body>
		<br />	
		<template:titile value="材料调拨单明细一览表" />
		<display:table name="sessionScope.materialAllotItemss" id="currentRowObject" pagesize="18">
			<display:column property="oldconname" title="调出代维" headerClass="subject"  sortable="true"/>	
			<display:column property="oldaddr" sortable="true" title="调出地址" headerClass="subject" />
			<display:column property="basename" title="调出材料" headerClass="subject"  sortable="true"/>	
			<display:column property="changedate" title="调出时间" headerClass="subject"  sortable="true"/>	
			<display:column property="newconname" title="调入代维" headerClass="subject"  sortable="true"/>	
			<display:column property="newaddr" sortable="true" title="调入地址" headerClass="subject" />
			<display:column property="newstock" title="调入新增" headerClass="subject"  sortable="true"/>	 
			<display:column property="oldstock" title="调入利旧" headerClass="subject"  sortable="true"/>	  
		</display:table>
		<table border="0" cellpadding="0" cellspacing="0" width="100%" style="border:0px">		
		    <tr>
		    	<td>
		    		 <logic:notEmpty name="materialAllotItemss">
					  	<a href="javascript:exportList()">导出为Excel文件</a>
					  </logic:notEmpty>
		    	</td>
		    </tr>
			<tr>
				<td style="text-align:center;">
					<!-- <input type="button" class="button" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')" value="返回" >-->
					<input type="button" class="button" onclick="history.back()" value="返回"/>
					
				</td>
			</tr>
			
		
			
		</table>
	</body>
</html>
