<%@include file="/common/header.jsp"%>
<html>
	<head>
		<script type="text/javascript" language="javascript">		
		function  goBack(){
			window.location.href="${ctx}/remedyItemAction.do?method=getRemedyItemsByBack";
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
		<template:titile value="���������Ϣһ����" />
		<display:table name="sessionScope.types" id="currentRowObject" pagesize="18">
			<display:column property="typename" sortable="true" title="�������" headerClass="subject" maxLength="10"/>
			<display:column property="itemname" sortable="true" title="������Ŀ" headerClass="subject" maxLength="10"/>
			<display:column property="price" sortable="true" title="����" headerClass="subject" maxLength="10"/>
			<display:column property="unit" sortable="true" title="��λ" headerClass="subject" maxLength="10"/>			 
		</display:table>
		<table border="0" cellpadding="0" cellspacing="0" width="100%" style="border:0px">	
			<tr><td height="10px"></td></tr>	   
			<tr>
				<td style="text-align:center;">
					<input type="button" class="button" onclick="location.replace('<%=session.getAttribute("S_BACK_URL")%>')" value="����" >
				</td>
			</tr>
			
		
			
		</table>
	</body>
</html>
