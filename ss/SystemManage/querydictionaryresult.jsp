<%@ include file="/common/header.jsp"%>
<html>
<head>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script language="javascript">

function rowDelete(idValue){
	if(confirm("确定删除该纪录？")){
        var url = "${ctx}/dictionary.do?method=delDict&id=" + idValue;
        self.location.replace(url);
	}
}
function toEdit(idValue){
	   var url = "${ctx}/dictionary.do?method=loadDict&id=" + idValue;
       self.location.replace(url);
}
function addDict(){
	 var url = "${ctx}/dictionary.do?method=addDict";
     self.location.replace(url);
}

</script>
</head>
<body>
<template:titile value="数据字典信息"/>
<display:table name="sessionScope.result"  id="row" pagesize="15">
	
	<display:column property="code" title="编码" />
	<display:column property="lable" title="名称" />
	<display:column property="assortmentId" title="分类" />
	<display:column property="sort" title="排序" />
	<display:column property="parentId" title="父类型" />
	<display:column media="html" title="操作">
		<a href="javascript:toEdit('${row.id}')">修改</a>
	</display:column>	
</display:table>
<br>
<div style="text-align: center"><input type="button" onclick="addDict()" class="lbutton" value="添加数据字典"></input></div>
</body>
</html>

