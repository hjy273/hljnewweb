<%@include file="/common/header.jsp"%>
<!--%@include file="/common/listhander.jsp"%-->
<html>
	<head>
		<script type="text/javascript" language="javascript">
		
		 toGetForm=function(idValue){
            	window.location.href = "${ctx}/project/remedy_item.do?method=getTypesByItemID&id="+idValue;
		}
		
		 toViewForm=function(idValue){
            	window.location.href = "${ctx}/project/remedy_item.do?method=viewTypesByID&id="+idValue;
		}
        toDeletForm=function(idValue,typenum){
       	 	if(confirm("该修缮项下包含"+typenum+"个修缮类别!你确定要删除吗?")){
            	window.location.href = "${ctx}/project/remedy_item.do?method=deleteRemedyItem&id="+idValue;
            }           
		}
		toEditForm=function(idValue){
		 	window.location.href = "${ctx}/project/remedy_item.do?method=editRemedyItemForm&id="+idValue;
		}       
		
		exportList=function(){
			var url="${ctx}/project/remedy_item.do?method=exportItemList";
			self.location.replace(url);
		}
		
		function  goBack(){
			var url="${ctx}/project/remedy_item.do?method=queryRemedyItemForm";
			self.location.replace(url);
		}
		</script>
		<title>partBaseInfo</title>
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
		<template:titile value="修缮项信息一览表" />
		<logic:notEmpty name="items">
		<display:table name="sessionScope.items" id="currentRowObject"
			pagesize="18">
			<bean:define id="id" name="currentRowObject" property="id"></bean:define>
			<bean:define id="itemname" name="currentRowObject"
				property="itemname"></bean:define>
			<bean:define id="typenum" name="currentRowObject" property="typenum"></bean:define>
			<display:column media="html" title="项目名称" sortable="true">
				<a href="javascript:toGetForm('${id }')">${itemname }</a>
			</display:column>
			<display:column property="regionname" sortable="true" title="所属区域"
				headerClass="subject" maxLength="10" />
			<display:column media="html" title="操作">
				<a href="javascript:toViewForm('${id }')">查看</a>
				| <a href="javascript:toEditForm('${id }')">修改</a>
	           	| <a href="javascript:toDeletForm('${id }','${typenum }')">删除</a>
			</display:column>
		</display:table>
		<table border="0" cellpadding="0" cellspacing="0" width="100%"
			style="border: 0px">
			<tr>
				<td>
					<logic:notEmpty name="items">
						<a href="javascript:exportList()">导出为Excel文件</a>
					</logic:notEmpty>
				</td>
			</tr>
			<tr>
				<td style="text-align: center;">
					<input name="action" class="button" value="返回" onclick="goBack();"
						type="button" />
				</td>
			</tr>
		</table>
	</logic:notEmpty>
	</body>
</html>
