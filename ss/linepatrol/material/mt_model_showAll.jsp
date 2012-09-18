<%@include file="/common/header.jsp"%>
<!--%@include file="/common/listhander.jsp"%-->
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<script type="text/javascript" language="javascript">
		
		 toGetForm=function(idValue){
            	window.location.href = "${ctx}/materialTypeAction.do?method=getTypesByItemID&id="+idValue;
		}
		
		 toViewForm=function(idValue){
            	window.location.href = "${ctx}/materialModelAction.do?method=viewModelByID&id="+idValue;
		}
		
        toDeletForm=function(idValue,bnum){
       	 	if(confirm("该规格下包含"+bnum+"个材料,你确定要删除吗?")){
            	window.location.href = "${ctx}/materialModelAction.do?method=deleteMeterialModel&id="+idValue;
            }           
		}
		toEditForm=function(idValue){
		 	window.location.href = "${ctx}/materialModelAction.do?method=editMaterialModelForm&id="+idValue;
		}       
		
		exportList=function(){
			var url="${ctx}/materialModelAction.do?method=exportModelList";
			self.location.replace(url);
		}
		
		function  goBack(){
			var url="${ctx}/materialModelAction.do?method=queryMaterialModelForm";
			self.location.replace(url);
		}
		</script>
		<title>材料规格</title>
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
		Object bnum = null;
		 %>
		<template:titile value="材料规格一览表" />
		<display:table name="sessionScope.materialModels" id="currentRowObject" pagesize="18">
			<display:column property="modelname" title="规格名称" headerClass="subject"  sortable="true"/>	
			<display:column property="typename" sortable="true" title="所属类型" headerClass="subject" maxLength="10"/>
			<display:column property="unit" title="单位" headerClass="subject"  sortable="true"/>	      	>
			 <display:column media="html" title="操作" >
				<% object = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
	            if(object != null) {
	      	 		id = object.get("id");
	      	 		bnum = object.get("bnum");
				} %>
				<a href="javascript:toViewForm('<%=id%>')">查看</a>
				|
	            		<a href="javascript:toEditForm('<%=id%>')">修改</a>
	            	
	            			| <a href="javascript:toDeletForm('<%=id%>','<%=bnum%>')">删除</a>
	            		
	             
            </display:column>
		</display:table>
		<table border="0" cellpadding="0" cellspacing="0" width="100%" style="border:0px">		
		    <tr>
		    	<td>
		    		 <logic:notEmpty name="materialModels">
					  	<a href="javascript:exportList()">导出为Excel文件</a>
					  </logic:notEmpty>
		    	</td>
		    </tr>
			<tr>
				<td style="text-align:center;">
				<!-- <input name="action" class="button" value="返回"
						onclick="goBack();" type="button" />
						-->
						<input type="button" class="button" onclick="history.back()" value="返回"/>
				</td>
			</tr>
			
		
			
		</table>
	</body>
</html>
