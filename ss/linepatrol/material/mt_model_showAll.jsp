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
       	 	if(confirm("�ù���°���"+bnum+"������,��ȷ��Ҫɾ����?")){
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
		<title>���Ϲ��</title>
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
		<template:titile value="���Ϲ��һ����" />
		<display:table name="sessionScope.materialModels" id="currentRowObject" pagesize="18">
			<display:column property="modelname" title="�������" headerClass="subject"  sortable="true"/>	
			<display:column property="typename" sortable="true" title="��������" headerClass="subject" maxLength="10"/>
			<display:column property="unit" title="��λ" headerClass="subject"  sortable="true"/>	      	>
			 <display:column media="html" title="����" >
				<% object = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
	            if(object != null) {
	      	 		id = object.get("id");
	      	 		bnum = object.get("bnum");
				} %>
				<a href="javascript:toViewForm('<%=id%>')">�鿴</a>
				|
	            		<a href="javascript:toEditForm('<%=id%>')">�޸�</a>
	            	
	            			| <a href="javascript:toDeletForm('<%=id%>','<%=bnum%>')">ɾ��</a>
	            		
	             
            </display:column>
		</display:table>
		<table border="0" cellpadding="0" cellspacing="0" width="100%" style="border:0px">		
		    <tr>
		    	<td>
		    		 <logic:notEmpty name="materialModels">
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
