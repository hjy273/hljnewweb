<%@include file="/common/header.jsp"%>
<!--%@include file="/common/listhander.jsp"%-->
<html>
	<head>
		<script type="text/javascript" language="javascript">
		toAdjustForm=function(idValue){
		 	var url = "<%=request.getContextPath()%>/remedy_material.do?method=adjustMaterialForm&&remedy_material_id="+idValue;
		    self.location.replace(url);
		}       
		goBack=function(){
			var url = "<%=request.getContextPath()%>/remedy_material.do?method=queryMaterialForm";
		    self.location.replace(url);
		}     
		</script>
		<title></title>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
		<style type="text/css">
		.subject {
			text-align: center;
		}
		</style>
	</head>
	<body>
		<br />
		<%
		    BasicDynaBean object = null;
		    Object id = null;
		%>
		<template:titile value="修缮材料列表" />
		<display:table name="sessionScope.MATERIAL_LIST" id="currentRowObject"
			pagesize="18">
			<%
			    object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
			    if (object != null) {
			        id = object.get("id");
			    }
			%>
			<display:column property="material_name" sortable="true" title="材料名称"
				headerClass="subject" />
			<display:column property="projectname" sortable="true" title="修缮项目名称"
				headerClass="subject" />
			<display:column property="contractorname" sortable="true" title="所属代维"
				headerClass="subject" />
			<display:column property="address" sortable="true" title="存放地点"
				headerClass="subject" />
			<display:column property="storage_type" title="存放材料类型"
				headerClass="subject" />
			<display:column property="use_number" title="使用数量"
				headerClass="subject" />
			<display:column media="html" title="操作">
				<apptag:checkpower thirdmould="53001">
					<a href="javascript:toAdjustForm('<%=id%>')">调整</a>
				</apptag:checkpower>
			</display:column>
		</display:table>
		<table border="0" cellpadding="0" cellspacing="0" width="100%"
			style="border: 0px">
			<tr>
				<td style="text-align: center;">
					<input name="action" class="button" value="返回" onclick="goBack();"
						type="button" />
				</td>
			</tr>
		</table>
	</body>
</html>
