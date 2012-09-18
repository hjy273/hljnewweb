<%@include file="/common/header.jsp"%>
<!--%@include file="/common/listhander.jsp"%-->
<html>
	<head>
		<script type="text/javascript" language="javascript">
		toViewForm=function(idValue){
		 	window.location.href = "${ctx}/project/remedy_type.do?method=viewRemedyTypeForm&id="+idValue;
		}    
		
        toDeletForm=function(idValue){
       	 	if(confirm("��ȷ��Ҫִ�д˴�ɾ��������?")){
            	window.location.href = "${ctx}/project/remedy_type.do?method=deleteRemedyType&id="+idValue;
            }           
		}
		toEditForm=function(idValue){
		 	window.location.href = "${ctx}/project/remedy_type.do?method=editRemedyTypeForm&id="+idValue;
		}       
		
		exportList=function(){
			var url="${ctx}/project/remedy_type.do?method=exportTypeList";
			self.location.replace(url);
		}
		
		function  goBack(){
			var url="${ctx}/project/remedy_type.do?method=queryRemedyTypeForm";
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
		<template:titile value="���������Ϣһ����" />
			<logic:notEmpty name="types">
		<display:table name="sessionScope.types" id="currentRowObject"
			pagesize="18">
			<bean:define id="id" name="currentRowObject" property="id"></bean:define>
			<display:column property="typename" sortable="true" title="�������"
				headerClass="subject" maxLength="10" />
			<display:column property="itemname" sortable="true" title="������Ŀ"
				headerClass="subject" maxLength="10" />
			<display:column property="price" sortable="true" title="����"
				headerClass="subject" maxLength="10" />
			<display:column property="unit" sortable="true" title="��λ"
				headerClass="subject" maxLength="10" />
			<display:column media="html" title="����">
				<a href="javascript:toViewForm('${id }')">�鿴</a>
				|<a href="javascript:toEditForm('${id }')">�޸�</a>
				| <a href="javascript:toDeletForm('${id }')">ɾ��</a>
			</display:column>
		</display:table>
		<table border="0" cellpadding="0" cellspacing="0" width="100%"
			style="border: 0px">
			<tr>
				<td>
					<logic:notEmpty name="types">
						<a href="javascript:exportList()">����ΪExcel�ļ�</a>
					</logic:notEmpty>
				</td>
			</tr>
			<tr>
				<td style="text-align: center;">
					<input name="action" class="button" value="����" onclick="goBack();"
						type="button" />
				</td>
			</tr>
		</table>
		</logic:notEmpty>
	</body>
</html>
