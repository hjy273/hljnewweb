<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<SCRIPT language=JavaScript src="${ctx}/common/PopupDlg.js" type=""></SCRIPT>
<SCRIPT language=javascript src="${ctx}/common/Comm.js" type=""></SCRIPT>
<script type="text/javascript"
	src="${ctx}/sendtask/js/sendtask-common.js"></script>
<html>
	<head>
		<title>sendtask</title>
		<script type="text/javascript" src="/WebApp/js/prototype.js"></script>
		<script type="" language="javascript">
		var contextPath="${ctx}";
		</script>
	</head>
	<body>
		<!--��ʾһ���ɵ���ϸ��Ϣҳ��-->
		<br>
		<template:titile value="�ɵ���ϸ��Ϣ" />
		<table width="90%" border="0" align="center" cellpadding="3"
			cellspacing="0" class="tabout">
			<tr class=trcolor>
				<td class="tdr" width="15%">
					�������
				</td>
				<td class="tdl" width="35%">
					<apptag:quickLoadList cssClass="" name="" listName="dispatch_task"
						keyValue="${sendTask.sendtype}" type="look" isRegion="false"></apptag:quickLoadList>
				</td>
				<td class="tdr">
					�ɵ�ʱ�䣺
				</td>
				<td class="tdl">
					${sendtime }
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdr">
					�ɵ����ţ�
				</td>
				<td class="tdl">
					${sendTask.sendorgname }
				</td>
				<td class="tdr">
					�� �� �ˣ�
				</td>
				<td class="tdl">
					${sendTask.sendusername }
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdr">
					�����ţ�
				</td>
				<td class="tdl">
					${sendTask.acceptorgname }
				</td>
				<td class="tdr">
					�����ˣ�
				</td>
				<td class="tdl">
					${sendTask.acceptusername }
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdr">
					�������⣺
				</td>
				<td class="tdl" colspan="3">
					${sendTask.sendtopic }
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdr">
					����˵����
				</td>
				<td class="tdl" colspan="3">
					${sendTask.sendtext }
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdr">
					����ʱ�ޣ�
				</td>
				<td class="tdl" colspan="3">
					${processterm }
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdr">
					���񸽼���
				</td>
				<td class="tdl" colspan="3">
					<apptag:upload cssClass="" entityId="${sendTask.id}"
						entityType="COMMON_SENDTASK" state="look" />
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdc" colspan="4" id="processHistoryTd"
					style="padding: 5px;">
				</td>
			</tr>
		</table>
		<p align="center">
			<input type="button" value="����" class="button" onclick="goBack('${url}');">
		</p>
		<script type="text/javascript">
		showProcessHistory('${sendTask.id}');
		</script>
	</body>
</html>
