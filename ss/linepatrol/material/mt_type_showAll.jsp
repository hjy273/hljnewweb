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
       	 	if(confirm("�������°���"+tnum+"�����,"+bnum+"�����ϣ���ȷ��Ҫɾ����?")){
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
		<title>��������</title>
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
		<template:titile value="��������һ����" />
		<display:table name="sessionScope.materialTypes" id="currentRowObject" pagesize="18">
			<display:column property="typename" title="��������" headerClass="subject"  sortable="true">	
      		</display:column>
			<display:column property="regionname" sortable="true" title="��������" headerClass="subject" maxLength="10"/>
			 <display:column media="html" title="����" >
				<% object = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
	            if(object != null) {
	      	 		id = object.get("id");
	      	 		tnum = object.get("tnum");
	      	 		bnum = object.get("bnum");
				} %>
				<a href="javascript:toViewForm('<%=id%>')">�鿴</a>
				|
	            		<a href="javascript:toEditForm('<%=id%>')">�޸�</a>
	            	
	            			| <a href="javascript:toDeletForm('<%=id%>','<%=tnum%>','<%=bnum%>')">ɾ��</a>
	            		
	             
            </display:column>
		</display:table>
		<table border="0" cellpadding="0" cellspacing="0" width="100%" style="border:0px">		
		    <tr>
		    	<td>
		    		 <logic:notEmpty name="materialTypes">
					  	<a href="javascript:exportList()">����ΪExcel�ļ�</a>
					  </logic:notEmpty>
		    	</td>
		    </tr>
			<tr>
				<td style="text-align:center;">
				<!-- <input type="button" class="button" onclick="goBack();" value="����" >-->
				<input type="button" class="button" onclick="history.back()" value="����"/>
				</td>
			</tr>
			
		
			
		</table>
	</body>
</html>
