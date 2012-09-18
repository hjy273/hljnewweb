<%@include file="/common/header.jsp"%>
<!--%@include file="/common/listhander.jsp"%-->
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<script type="text/javascript" language="javascript">	
		toGetForm=function(idValue){
            	window.location.href = "${ctx}/materialAllotAction.do?method=getMaterialAllotItems&allotid="+idValue;
		}
		exportList=function(){
			var url="${ctx}/materialAllotAction.do?method=exportAllotList";
			self.location.replace(url);
		}
		function  goBack(){
			var url="${ctx}/materialAllotAction.do?method=queryMaterialAllotForm";
			self.location.replace(url);
		}
		</script>
		<title>调拨单</title>
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
		Object username = null;
		Object changedate = null;
		Object title = null;
		 %>
		<template:titile value="材料调拨单一览表" />
		<display:table name="sessionScope.materialAllots" id="currentRowObject" pagesize="18">
			<display:column media="html" title="调拨标题"  sortable="true">
				<% object = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
	            if(object != null) {
	               id = object.get("id");
	      	 	   username = object.get("username");
	      	 	   changedate = object.get("changedate");
	      	 	   title = username+""+changedate+" 000"+id;
				} %>
      			<a href="javascript:toGetForm('<%=id%>')"><%=title%></a> 
      		</display:column>	
			<display:column property="username" title="调拨人" headerClass="subject"  sortable="true"/>	
			<display:column property="changedate" title="调拨时间" headerClass="subject"  sortable="true"/>	
			<display:column property="remark" title="调拨备注" headerClass="subject"  sortable="true"/>	
		</display:table>
		<table border="0" cellpadding="0" cellspacing="0" width="100%" style="border:0px">	
			
		    <tr>
		    	<td>
		    		 <logic:notEmpty name="materialAllots">
					  	<a href="javascript:exportList()">导出为Excel文件</a>
					  </logic:notEmpty>
		    	</td>
		    </tr>
			<tr>
				<td style="text-align:center;">
				<!-- 	<input name="action" class="button" value="返回"
						onclick="goBack();" type="button" />-->
						<input type="button" class="button" onclick="history.back()" value="返回"/>
				</td>
			</tr>
			
		
			
		</table>
	</body>
</html>
