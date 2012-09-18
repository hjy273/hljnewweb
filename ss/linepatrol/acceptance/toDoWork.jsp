<%@include file="/common/header.jsp"%>
<html>
<head>
	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
	<title></title>
	<script type="text/javascript">
		function allotTasks(id){
			window.location.href = '${ctx}/acceptanceAction.do?method=allotTasks&id='+id;
		}
		function claimTasks(id){
			window.location.href = '${ctx}/acceptanceAction.do?method=claimTask&id='+id;
		}
		function approveTasks(id){
			window.location.href = '${ctx}/acceptanceAction.do?method=approveTasks&id='+id;
		}
		function chooseTask(id, type){
				window.location.href = '${ctx}/acceptanceAction.do?method=chooseTask&id='+id+'&type='+type;
		}
		function push(id){
			window.location.href = '${ctx}/acceptanceAction.do?method=pushLink&id='+id;
		}
		function approve(id){
			window.location.href = '${ctx}/acceptanceAction.do?method=apporveLink&id='+id;
		}
		function exam(id){
			window.location.href = '${ctx}/acceptanceExamAction.do?method=examLink&id='+id;
		}
		function recheck(id){
			window.location.href = '${ctx}/acceptanceAction.do?method=recheckLink&id='+id;
		}
		//ȡ������
		toCancelForm=function(applyId){
			var url;
			if(confirm("ȷ��Ҫȡ�������ս�ά������")){
				url="${ctx}/acceptanceAction.do?method=cancelAcceptanceForm";
				var queryString="apply_id="+applyId;
				//location=url+"&"+queryString+"&rnd="+Math.random();
				window.open(url+"&"+queryString+"&rnd="+Math.random(),'','width=300,height=200,top=0,left=0,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no');
			};
		};
	</script>
</head>
<body>
	<template:titile value="���ս�ά�б�(<font color='red'>��${number}������</font>)" />
	<div style="text-align:center;">
		<iframe
			src="${ctx}/acceptanceAction.do?method=viewAcceptanceProcess&&task_name=${param.task_name }&&process_name=${param.process_name }"
			frameborder="0" id="processGraphFrame" height="100" scrolling="no"
			width="95%"></iframe>
	</div>
	<display:table name="sessionScope.result" id="row" pagesize="18" export="false" defaultsort="4" defaultorder="descending" sort="list">
		<logic:notEmpty name="row">
				<bean:define id="sendUserId" name="row" property="applicant" />
				<bean:define id="id" name="row" property="id"></bean:define>
				<bean:define id="applyState" name="row" property="processState"></bean:define>
		</logic:notEmpty>
		<display:setProperty name="sort.amount" value="list"/>
		<display:column property="name" title="��������"/>
		<display:column property="code" title="������"/>
		<display:column media="html" title="��Ŀ����">
			<c:out value="${users[row.applicant]}" />
		</display:column>
		<display:column media="html" title="����ʱ��">
			<bean:write name="row" property="applyDate" format="yy/MM/dd"/>
		</display:column>
		<display:column media="html" title="��Դ����">
			<c:choose>
				<c:when test="${row.resourceType eq '1'}">����</c:when>
				<c:otherwise>�ܵ�</c:otherwise>
			</c:choose>
		</display:column>
		<display:column media="html" title="״̬">
			<c:if test="${row.processState eq '10'}">��Ҫ��׼����</c:if>
			<c:if test="${row.processState eq '20'}">��Ҫ��������</c:if>
			<c:if test="${row.processState eq '30'}">��Ҫ��׼����</c:if>
			<c:if test="${row.processState eq '40'}">
				<c:if test="${row.subProcessState eq '40'}">��Ҫ¼������</c:if>
				<c:if test="${row.subProcessState eq '42'}">��Ҫ���¼��</c:if>
				<c:if test="${row.subProcessState eq '43'}">��Ҫ��������</c:if>
				<c:if test="${row.subProcessState eq '44'}">��Ҫ¼������</c:if>
			</c:if>
			<c:if test="${row.processState eq '46'}">��Ҫ�����׼</c:if>
		</display:column>
		<display:column media="html" title="����">
			<c:if test="${row.processState eq '10'}">
				<a href="javascript:allotTasks('${row.id}')">��׼����</a>
			</c:if>
			<c:if test="${row.processState eq '20'}">
				<a href="javascript:claimTasks('${row.id}')">��������</a>
			</c:if>
			<c:if test="${row.processState eq '30'}">
				<a href="javascript:approveTasks('${row.id}')">��׼����</a>
			</c:if>
			<c:if test="${row.processState eq '40'}">
				<c:if test="${row.subProcessState eq '40'}">
					<a href="javascript:chooseTask('${row.subflowId}','true')">¼������</a>
				</c:if>
				<c:if test="${row.subProcessState eq '42'}">
					<a href="javascript:approve('${row.subflowId}')">���¼��</a>
				</c:if>
				<c:if test="${row.subProcessState eq '43'}">
					<a href="javascript:exam('${row.subflowId}')">��������</a>
				</c:if>
				<c:if test="${row.subProcessState eq '44'}">
					<a href="javascript:chooseTask('${row.subflowId}','false')">¼������</a>
				</c:if>
			</c:if>
			<c:if test="${row.processState eq '46'}">
				<a href="javascript:recheck('${row.id}')">�����׼</a>
			</c:if>
	        <c:if test="${sessionScope.LOGIN_USER.deptype=='1'&&applyState!='00'&&applyState!='42'}">
				<a href="javascript:toCancelForm('${row.id}')">ȡ��</a>
			</c:if>
		</display:column>
	</display:table>
</body>
</html>