<%@include file="/common/header.jsp"%>
<%@page
	import="com.cabletech.sparepartmanage.domainobjects.SparepartConstant"%>
<html>
	<head>
		<script type="" language="javascript">	
		toGetForm=function(idvalue){
			window.location.href="${ctx}/SparepartStorageAction.do?method=againToStorageForm&stoid="+idvalue;
			//self.location.replace(url);
		}
		toViewForm=function(idvalue){
			window.location.href="${ctx}/SparepartStorageAction.do?method=againToStorageForm&stoid="+idvalue+"&act=view";
			//self.location.replace(url);
		}
		toScrappedForm=function(idvalue){
			if(!confirm("��ȷ��Ҫ���ϴ˱�����?")) {
				return;
			}else{
				window.location.href="${ctx}/SparepartStorageAction.do?method=scrappedStorage&stoid="+idvalue;
			}
			
		}
		exportList=function(){
			window.location.href="${ctx}/SparepartStorageAction.do?method=exportAgainStorageList";
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
		BasicDynaBean object = null;
		String id = "";
		%>
		<template:titile value="�������������Ϣһ����" />
		<display:table name="sessionScope.APPLY_LIST" id="currentRowObject" pagesize="18">
			<display:column property="product_factory" sortable="true" title="��������" headerClass="subject" maxLength="10" />
			<display:column property="spare_part_name" sortable="true" title="��������" headerClass="subject" maxLength="10" />
			<display:column property="spare_part_model" sortable="true" title="�����ͺ�" headerClass="subject" maxLength="10" />
			<display:column property="name" sortable="true" title="�������λ��" headerClass="subject" maxLength="20" />
			<display:column media="html" title="����" headerClass="subject">
				<%
				    object = (BasicDynaBean)pageContext.findAttribute("currentRowObject");
				    id = (String) object.get("stoid");
				  %>
				<a href="javascript:toViewForm('<%=id%>')">��ϸ </a>|
				<a href="javascript:toGetForm('<%=id%>')">������� </a>|
				<a href="javascript:toScrappedForm('<%=id%>')">����</a>
			</display:column>
		</display:table>
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
			<tr>
				<td style="text-align:center;">
					<input name="action" class="button" value="����ΪExcel"
						 onclick="exportList()" type="button" />
				</td>
			</tr>
		</table>
	</body>
</html>
	