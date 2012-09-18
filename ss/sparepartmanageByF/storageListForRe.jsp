<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.sparepartmanage.domainobjects.SparepartConstant" %>
<html>
	<head>
		<script type="" language="javascript">       
	
		toApplyFormMod=function(url) {
			//self.location.replace(url);
			window.location.href=url;
		}
		toStorageOpForm=function(method,param){
		 	window.location.href = "${ctx}/SparepartStorageAction.do?method="+method+"&"+param;
		    //self.location.replace(url);
		}
	
		exportList=function(){
			window.location.href="${ctx}/SparepartStorageAction.do?method=exportStorageListForApply";
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
		String name = "";
		 %>
		<template:titile value="��������һ����" />
		<display:table name="sessionScope.STORAGE_LIST" id="currentRowObject" pagesize="18">
			<display:column property="product_factory" sortable="true" title="��������" headerClass="subject" maxLength="15" />
			<display:column property="spare_part_name" sortable="true" title="��������" headerClass="subject" maxLength="15" />
			<display:column property="spare_part_model" sortable="true" title="�����ͺ�" headerClass="subject" maxLength="10" />
			<display:column property="storage_number" title="�������" sortable="true" headerClass="subject" maxLength="10" />
			<display:column media="html" title="����" headerClass="subject">
				<apptag:checkpower thirdmould="90711">
					<%
				    	object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
				    	id = (String) object.get("tid");
				    	name= (String)object.get("spare_part_name");
				    	%>
				    	&nbsp;&nbsp;
						<a href="javascript:toApplyFormMod('${ctx}/SparepartApplyAction.do?method=addApplyForm&baseid=<%=id %>&name=<%=name%>')" >�������</a> | 
						<a href="javascript:toStorageOpForm('giveBackToStorageForm','base_id=<%=id%>')">�黹����</a>
				</apptag:checkpower>	
			</display:column>
		</display:table>
		<table border="0" cellpadding="0" cellspacing="0" width="100%" style="border:0px">
			<tr>
				<td style="text-align:center;">
					<input name="action" class="button" value="����ΪExcel"
						onclick="exportList()" type="button" />
				</td>
			</tr>
		</table>
	</body>
</html>
