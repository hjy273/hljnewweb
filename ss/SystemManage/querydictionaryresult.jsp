<%@ include file="/common/header.jsp"%>
<html>
<head>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script language="javascript">

function rowDelete(idValue){
	if(confirm("ȷ��ɾ���ü�¼��")){
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
<template:titile value="�����ֵ���Ϣ"/>
<display:table name="sessionScope.result"  id="row" pagesize="15">
	
	<display:column property="code" title="����" />
	<display:column property="lable" title="����" />
	<display:column property="assortmentId" title="����" />
	<display:column property="sort" title="����" />
	<display:column property="parentId" title="������" />
	<display:column media="html" title="����">
		<a href="javascript:toEdit('${row.id}')">�޸�</a>
	</display:column>	
</display:table>
<br>
<div style="text-align: center"><input type="button" onclick="addDict()" class="lbutton" value="��������ֵ�"></input></div>
</body>
</html>

