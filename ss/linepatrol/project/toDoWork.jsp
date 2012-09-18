<%@include file="/common/header.jsp"%>
<!--%@include file="/common/listhander.jsp"%-->
<html>
	<head>
		<title></title>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />
		<script type="text/javascript" language="javascript">
			function needCompleteApply(id){
				var url = "${ctx}/project/remedy_apply.do?method=updateApplyForm&apply_id="+id;
				window.location.href = url;
			}
			function needApprove(id, type){
				var url = "${ctx}/project/remedy_apply_approve.do?method=approveApplyForm&apply_id="+id+'&type='+type;
				window.location.href = url;
			}
		//ȡ���������
		toCancelForm=function(applyId){
			var url;
			if(confirm("ȷ��Ҫȡ���ù�������������")){
				url="${ctx}/project/remedy_apply.do?method=cancelRemedyForm";
				var queryString="apply_id="+applyId;
				//location=url+"&"+queryString+"&rnd="+Math.random();
				window.open(url+"&"+queryString+"&rnd="+Math.random(),'','width=300,height=200,top=0,left=0,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no');
			}
		}
		</script>
	</head>
	<body>
		<template:titile value="�����б�" />
		<div style="text-align:center;">
			<iframe
				src="${ctx}/project/remedy_apply.do?method=viewRemedyProcess&&task_name=${param.task_name }"
				frameborder="0" id="processGraphFrame" height="70" scrolling="no"
				width="95%"></iframe>
		</div>
		<%
			session.setAttribute("S_BACK_URL",request.getContextPath()+"/project/remedy_apply.do?method=toDoWork");
		%>
		<logic:notEmpty name="result">
		<display:table name="sessionScope.result" id="row" pagesize="18">
			<logic:notEmpty name="row">
			<bean:define id="sendUserId" name="row" property="creator" />
			<bean:define id="applyState" name="row" property="state" />
			</logic:notEmpty>
			<display:column property="remedyCode" sortable="true" title="���" />
			<display:column property="projectName" sortable="true" title="��Ŀ����"  />
			<display:column property="remedyDate" sortable="true" title="����ʱ��"  format="{0,date,yyyy-MM-dd}"/>
			<display:column property="totalFee" title="������ϼƽ��" sortable="true"/>
			<display:column property="mtotalFee" title="ʹ�ò��Ϻϼƽ��" sortable="true" />
			<display:column media="html" title="״̬" sortable="true">
				<c:if test="${row.state eq '10'}">
					��Ҫ��д����
				</c:if>
				<c:if test="${row.state eq '20'}">
					��Ҫ���
				</c:if>
			</display:column>
			<display:column media="html" title="����">
				<c:if test="${row.state eq '10'}">
					<a href="javascript:needCompleteApply('${row.id}')">��д</a>
				</c:if>
				<c:if test="${row.state eq '20'}">
					<c:choose>
						<c:when test="${!row.flag}">
							<a href="javascript:needApprove('${row.id}', '')">���</a> | 
							<a href="javascript:needApprove('${row.id}', '2')">ת��</a>
						</c:when>
						<c:otherwise>
							<a href="javascript:needApprove('${row.id}', '3')">�鿴</a>
						</c:otherwise>
					</c:choose>
				</c:if>
			</display:column>
		</display:table>
		</logic:notEmpty>
		<table border="0" cellpadding="0" cellspacing="0" width="100%"
			style="border: 0px">
			<tr>
				<td>
					<a href="#"></a>
				</td>
			</tr>
			<tr>
				<td style="text-align: center;">
				</td>
			</tr>
		</table>
	</body>
</html>