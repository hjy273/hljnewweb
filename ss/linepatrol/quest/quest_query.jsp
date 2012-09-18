<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>�ʾ��ѯ</title>
		<script type="text/javascript">
	function checkForm() {
		queryIssueByCondition.submit();
	}
</script>
	</head>
	<body>
		<template:titile value="�ʾ��ѯ" />
		<html:form action="/questAction.do?method=queryIssueByCondition"
			styleId="queryIssueByCondition">
			<table align="center" cellpadding="1" cellspacing="0" class="tabout"
				width="90%">
				<tr class="trcolor">
					<td class="tdulleft" style="width: 20%;">
						�ʾ����ƣ�
					</td>
					<td class="tdulright" style="width: 80%;" id="usertree">
						<input name="issueName" type="text" id="issueName"
							class="inputtext" style="width: 150px;" />
					</td>
				</tr>
				<tr class="trcolor">
					<td class="tdulleft" style="width: 20%;">
						����ʱ�䣺
					</td>
					<td class="tdulright" style="width: 80%;">
						<input name="beginTime" class="Wdate" id="beginTime"
							style="width: 150px;"
							onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" readonly />
						-
						<input name="endTime" class="Wdate" id="endTime"
							style="width: 150px;"
							onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',minDate:'#F{$dp.$D(\'beginTime\')}'})" readonly />
					</td>
				</tr>
			</table>
			<div align="center" style="height: 40px">
				<input type="hidden" name="isQuery" value="true" />
				<html:button property="action" styleClass="button"
					onclick="checkForm()">�ύ</html:button>
			</div>
		</html:form>
		<template:displayHide styleId="queryIssueByCondition"></template:displayHide>
		<link rel="stylesheet" href="/WebApp/css/screen.css" type="text/css"
			media="screen, print" />
		<logic:notEmpty name="query_list">
			<script type="text/javascript">
	toViewIssue = function(issueId) {
		window.location.href = "/WebApp/questAction.do?method=viewQuestIssue&issueId="
				+ issueId;
	}
</script>
			<display:table name="sessionScope.query_list" id="issue"
				pagesize="18">
				<display:column property="id" title="���" headerClass="subject"
					maxLength="15" sortable="true" />
				<display:column property="name" title="�ʾ�����" headerClass="subject"
					sortable="true" />
				<display:column property="type" title="�ʾ�����" headerClass="subject"
					sortable="true" />
				<display:column property="create_date" title="����ʱ��"
					headerClass="subject" sortable="true" />
				<display:column media="html" title="����">
					<a
						href="javascript:toViewIssue('<bean:write name="issue" property="id"/>')">�鿴</a>
				</display:column>
			</display:table>
			<div align="center" style="height: 40px">
				<html:button property="button" styleClass="button"
					onclick="javascript:history.go(-1);">����</html:button>
			</div>
		</logic:notEmpty>
	</body>
</html>
