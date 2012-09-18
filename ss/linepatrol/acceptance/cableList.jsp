<%@include file="/common/header.jsp"%>
<html>
<head>
	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
	<title></title>
	<script type="text/javascript">
		function viewCableData(id){
			window.location.href = '${ctx}/acceptanceAction.do?method=viewCableData&id=${apply.id}&cableId='+id;
		}
		function deleteCableData(id){
			var con = confirm("�Ƿ�ɾ��������¼��");
			if(con){
				window.location.href = '${ctx}/acceptancePlanQueryAction.do?method=deleteCableData&cableId='+id;
			}
		}
		
		//����
		goBack = function() {
			var url = '${sessionScope.S_BACK_URL}';
			self.location.replace(url);
		}
	</script>
</head>
<body>
	<template:titile value="�����б�" />
	<display:table name="sessionScope.apply.cables" id="row"  pagesize="8" export="false" defaultsort="1" defaultorder="ascending">
		<display:column property="cableNo" title="���±��"/>
		<display:column property="trunk" title="�����м̶�"/>
		<display:column property="fibercoreNo" title="��о��"/>
		<display:column property="issueNumber" title="��������"/>
		<display:column media="html" title="���¼���">
			${cabletype[row.cableLevel] }
		</display:column>
		<display:column media="html" title="���³���">
			${row.cableLength}ǧ��
		</display:column>
		<c:if test="${apply.subProcessState eq '43' || apply.processState eq '00'}">
			<display:column media="html" title="����">
				<a href="javascript:viewCableData('${row.id}')">�鿴����</a>
			</display:column>
		</c:if>
		<c:if test="${canDelete=='can'}">
			<display:column media="html" title="����">
				<a href="javascript:deleteCableData('${row.id}')">ɾ��</a>
			</display:column>
		</c:if>
	</display:table>
	<table style="border:0px;">
		<tr><td style="border:0px;text-align:center;">	
			<font color="red">${error}</font>
		</td></tr>
		<tr><td style="border:0px;text-align:center;">
			<c:if test="${canDelete=='can'}">
				<html:button property="action" styleClass="button" onclick="goBack();">����</html:button>
			</c:if>
			<c:if test="${canDelete!='can'}">
				<!-- 
				<input type="button" value="��������" class="button" onclick="javascript:resumeInput();">
				 -->
				<input type="button" value="�˳�" class="button" onclick="javascript:parent.close();">
			</c:if>
		</td></tr>
	</table>
</body>