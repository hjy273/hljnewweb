<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<!--%@include file="/common/listhander.jsp"%-->
<SCRIPT language=JavaScript src="${ctx}/common/PopupDlg.js" type=""></SCRIPT>
<SCRIPT language=javascript src="${ctx}/common/Comm.js" type=""></SCRIPT>
<html>
	<head>
		<title>sendtask</title>
		<script type="" language="javascript">
		toCancel=function(scheduleName){
			var url="${ctx}/schedule.do?method=cancelSchedule&&schedule_name="+scheduleName;
			if(confirm("ȷʵҪȡ���ö�ʱ������")){
				location=url;
			}
		}
		toView=function(id){
			var url="${ctx}/schedule.do?method=viewScheduleInfo&&id="+id;
			location=url;
		}
		</script>
	</head>
	<body>
		<!--��ʾ�����ɵ�ҳ��-->
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
		<br>
		<template:titile value="��ʱ�����б�" />
			<display:table name="sessionScope.DATA_LIST"
				requestURI="${ctx}/schedule.do" id="currentRowObject" pagesize="20"
				style="width:100%">
				<logic:notEmpty name="DATA_LIST">
				<bean:define id="schedularName" name="currentRowObject"
					property="schedular_name" />
				<bean:define id="sendType" name="currentRowObject"
					property="send_type" />
				<bean:define id="sendState" name="currentRowObject"
					property="send_state" />
				<bean:define id="id" name="currentRowObject"
					property="id" />
				<bean:define id="createUserId" name="currentRowObject"
					property="create_user_id" />
				<display:column property="sim_id" title="����Ŀ���ֻ�" maxLength="26" />
				<display:column property="send_content" title="��������" maxLength="80" />
				<display:column property="send_type_label" title="��������"
					style="width:50px" sortable="true" />
				<display:column property="send_state_label" title="����״̬"
					style="width:50px" sortable="true" />
				<display:column media="html" title="����" style="width:50px">
					<a href="javascript:toView('${id }')">�鿴</a>
					<c:if
						test="${sessionScope.LOGIN_USER.userID==createUserId&&sendState=='01'}">
						<a href="javascript:toCancel('${schedularName }')">ȡ��</a>
					</c:if>
				</display:column>
				</logic:notEmpty>
			</display:table>
		<logic:empty name="DATA_LIST">
			û���ҵ�������Ϣ��
		</logic:empty>
	</body>
</html>
