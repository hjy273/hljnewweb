<%@include file="/common/header.jsp"%>
<html>
<head>
	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
	<title></title>
	
	<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
	<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
	<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
	
	<script type="text/javascript">
		function viewPipeData(id){
			var url = '${ctx}/acceptanceAction.do?method=viewPipeData&id=${apply.id}&pipeId='+id;
			parent.showWin(url);
		}
		//ɾ���ܵ���Ϣ
		function deletePipeData(id){
			var con = confirm("�Ƿ�ɾ��������¼��");
			if(con){
				window.location.href = '${ctx}/acceptancePlanQueryAction.do?method=deletePipeData&pipeId='+id;
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
	<template:titile value="�ܵ��б�" />
	<display:table name="sessionScope.apply.pipes" id="row"  pagesize="8" export="false" defaultsort="1" defaultorder="ascending">
		<display:column property="projectName" title="��������"/>
		<display:column property="pipeAddress" title="�ܵ��ص�"/>
		<display:column property="pipeRoute" title="��ϸ·��"/>
		<display:column property="builder" title="ʩ����λ"/>
		<display:column property="pipeLength0" title="�ܵ�����(KM)"/>
		<display:column property="planAcceptanceTime" title="��������"/>
		<display:column media="html" title="��Ȩ����">
			${pipeProperty[row.pipeProperty]}
		</display:column>
		<display:column media="html" title="�ܵ�����">
			${pipeType[row.pipeType]}
		</display:column>
		<c:if test="${apply.subProcessState eq '43' || apply.processState eq '00'}">
			<display:column media="html" title="����">
				<a href="javascript:viewPipeData('${row.id}')">�鿴����</a>
			</display:column>
		</c:if>
		<c:if test="${canDelete=='can'}">
			<display:column media="html" title="����">
				<a href="javascript:deletePipeData('${row.id}')">ɾ��</a>
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
				<input type="button" value="�˳�" class="button" onclick="javascript:parent.close()">
			</c:if>
		</td></tr>
	</table>
</body>