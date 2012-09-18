<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.sparepartmanage.domainobjects.SparepartConstant" %>
<html>
	<head>
		<script type="" language="javascript">
       
        toGetForm=function(idValue){
			var flag = 2;
        	window.location.href = "${ctx}/SparepartStorageAction.do?method=viewSavedStorage&base_id="+idValue+"&flag=" + flag;
        	//self.location.replace(url);
		}
		toStorageOpForm=function(method,param){
		 	window.location.href = "${ctx}/SparepartStorageAction.do?method="+method+"&"+param;
		   // self.location.replace(url);
		}
		
		
		exportList=function(){
			window.location.href="${ctx}/SparepartStorageAction.do?method=exportStorageListForRe";
			//self.location.replace(url);
		}
		</script>
		<title>partBaseInfo</title>
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
		String id="";
		 %>
		<template:titile value="备件领用一览表" />
		<display:table name="sessionScope.STORAGE_LIST" requestURI="${ctx}/SparepartStorageAction.do?method=listSparepartStorageForDraw" id="currentRowObject" pagesize="18">			
			<display:column property="product_factory" sortable="true" title="生产厂商" headerClass="subject" maxLength="10" />
			<display:column property="spare_part_name" sortable="true" title="备件名称" headerClass="subject" maxLength="10" />
			<display:column property="spare_part_model" sortable="true" title="备件型号" headerClass="subject" maxLength="10" />			
			<display:column property="storage_number" title="库存数量" sortable="true" headerClass="subject" maxLength="10" />
			<display:column media="html" title="操作" headerClass="subject">
				<%
				    object = (BasicDynaBean)pageContext.findAttribute("currentRowObject");
				    id = (String) object.get("tid");
				  %>
				<a href="javascript:toGetForm('<%=id%>')">详细</a>&nbsp;&nbsp;
				<apptag:checkpower thirdmould="90706">
					<%
				    	object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
				    	id = (String) object.get("tid");
				  	%>
				  	<logic:notEqual value="0" name="currentRowObject" property="storage_number">
						|&nbsp;<a href="javascript:toStorageOpForm('takeOutFromStorageForm','baseid=<%=id%>')"> 领用备件</a>
					</logic:notEqual>
				</apptag:checkpower>	
			</display:column>
		</display:table>
		<div style="width: 100%; text-align: center;">		
				<input name="action" class="button" value="导出为Excel" onclick="exportList()" type="button" />
		</div>
	</body>
</html>
