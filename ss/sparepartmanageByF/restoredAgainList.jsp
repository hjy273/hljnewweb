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
			if(!confirm("你确定要报废此备件吗?")) {
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
		<template:titile value="备件重新入库信息一览表" />
		<display:table name="sessionScope.APPLY_LIST" id="currentRowObject" pagesize="18">
			<display:column property="product_factory" sortable="true" title="生产厂商" headerClass="subject" maxLength="10" />
			<display:column property="spare_part_name" sortable="true" title="备件名称" headerClass="subject" maxLength="10" />
			<display:column property="spare_part_model" sortable="true" title="备件型号" headerClass="subject" maxLength="10" />
			<display:column property="name" sortable="true" title="备件库存位置" headerClass="subject" maxLength="20" />
			<display:column media="html" title="操作" headerClass="subject">
				<%
				    object = (BasicDynaBean)pageContext.findAttribute("currentRowObject");
				    id = (String) object.get("stoid");
				  %>
				<a href="javascript:toViewForm('<%=id%>')">详细 </a>|
				<a href="javascript:toGetForm('<%=id%>')">重新入库 </a>|
				<a href="javascript:toScrappedForm('<%=id%>')">报废</a>
			</display:column>
		</display:table>
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
			<tr>
				<td style="text-align:center;">
					<input name="action" class="button" value="导出为Excel"
						 onclick="exportList()" type="button" />
				</td>
			</tr>
		</table>
	</body>
</html>
	