<%@include file="/common/header.jsp"%>
<!--%@include file="/common/listhander.jsp"%-->
<html>
	<head>
		<script type="text/javascript" language="javascript">
		
		 toGetForm=function(idValue){
            	window.location.href = "${ctx}/remedyItemAction.do?method=getTypesByItemID&id="+idValue;
		}
		
		 toViewForm=function(idValue){
            	window.location.href = "${ctx}/remedyItemAction.do?method=viewTypesByID&id="+idValue;
		}
        toDeletForm=function(idValue,typenum){
       	 	if(confirm("���������°���"+typenum+"���������!��ȷ��Ҫɾ����?")){
            	window.location.href = "${ctx}/remedyItemAction.do?method=deleteRemedyItem&id="+idValue;
            }           
		}
		toEditForm=function(idValue){
		 	window.location.href = "${ctx}/remedyItemAction.do?method=editRemedyItemForm&id="+idValue;
		}       
		
		exportList=function(){
			var url="${ctx}/remedyItemAction.do?method=exportItmeList";
			self.location.replace(url);
		}
		
		function  goBack(){
			var url="${ctx}/remedyItemAction.do?method=queryRemedyItemForm";
			self.location.replace(url);
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
		Object id=null;
		Object itemname = null;
		Object typenum = null;
		 %>
		<template:titile value="��������Ϣһ����" />
		<display:table name="sessionScope.items" id="currentRowObject" pagesize="18">
			<display:column media="html" title="��Ŀ����"  sortable="true">
				<% object = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
	            if(object != null) {
	               id = object.get("id");
	      	 		itemname = object.get("itemname");
				} %>
      			<a href="javascript:toGetForm('<%=id%>')"><%=itemname %></a> 
      		</display:column>
			<display:column property="regionname" sortable="true" title="��������" headerClass="subject" maxLength="10"/>
			 <display:column media="html" title="����" >
				<% 
	            if(object != null) {
	      	 		id = object.get("id");
	      	 		typenum = object.get("typenum");
				} %>
				<a href="javascript:toViewForm('<%=id%>')">�鿴</a>
				|
	            		<a href="javascript:toEditForm('<%=id%>')">�޸�</a>
	            	
	            			| <a href="javascript:toDeletForm('<%=id%>','<%=typenum%>')">ɾ��</a>
	            		
	             
            </display:column>
		</display:table>
		<table border="0" cellpadding="0" cellspacing="0" width="100%" style="border:0px">		
		    <tr>
		    	<td>
		    		 <logic:notEmpty name="items">
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
