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
            	window.location.href = "${ctx}/materialTypeAction.do?method=viewTypeByID&id="+idValue;
		}
		
        toDeletForm=function(idValue,tnum,bnum){
       	 	if(confirm("该类型下包含"+tnum+"个规格,"+bnum+"个材料，你确定要删除吗?")){
            	window.location.href = "${ctx}/materialTypeAction.do?method=deleteMeterialType&id="+idValue;
            }           
		}
		toEditForm=function(idValue){
		 	window.location.href = "${ctx}/materialTypeAction.do?method=editMaterialTypeForm&id="+idValue;
		}       
		
		exportList=function(){
			var url="${ctx}/materialTypeAction.do?method=exportTypeList";
			self.location.replace(url);
		}
		
		function  goBack(){
			var url="${ctx}/materialTypeAction.do?method=queryMaterialTypeForm";
			self.location.replace(url);
		}
		</script>
		<title>材料类型</title>
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
		Object tnum = null;	
		Object bnum = null;
		 %>
		<template:titile value="材料类型一览表" />
		<display:table name="sessionScope.materialTypes" id="currentRowObject" pagesize="18">
			<display:column property="typename" title="类型名称" headerClass="subject"  sortable="true">	
      		</display:column>
			<display:column property="regionname" sortable="true" title="所属区域" headerClass="subject" maxLength="10"/>
			 <display:column media="html" title="操作" >
				<% object = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
	            if(object != null) {
	      	 		id = object.get("id");
	      	 		tnum = object.get("tnum");
	      	 		bnum = object.get("bnum");
				} %>
				<a href="javascript:toViewForm('<%=id%>')">查看</a>
				|
	            		<a href="javascript:toEditForm('<%=id%>')">修改</a>
	            	
	            			| <a href="javascript:toDeletForm('<%=id%>','<%=tnum%>','<%=bnum%>')">删除</a>
	            		
	             
            </display:column>
		</display:table>
		<table border="0" cellpadding="0" cellspacing="0" width="100%" style="border:0px">		
		    <tr>
		    	<td>
		    		 <logic:notEmpty name="materialTypes">
					  	<a href="javascript:exportList()">导出为Excel文件</a>
					  </logic:notEmpty>
		    	</td>
		    </tr>
			<tr>
				<td style="text-align:center;">
				<!-- <input type="button" class="button" onclick="goBack();" value="返回" >-->
				<input type="button" class="button" onclick="history.back()" value="返回"/>
				</td>
			</tr>
			
		
			
		</table>
	</body>
</html>
