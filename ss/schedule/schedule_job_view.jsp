<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<html>
	<head>
		<title>sendtask</title>
		<script type="text/javascript">
		goBack=function(){
			var url="${sessionScope.S_BACK_URL}";
			location=url;
		}
		</script>
	</head>
	<body>
		<!--�Ѿ���֤���ɵ���ϸ��Ϣ-->
		<br>
		<template:titile value="��ʱ������ϸ��Ϣ" />
		<logic:notEmpty name="job_detail">
			<table width="90%" border="0" align="center" cellpadding="3"
				cellspacing="0" class="tabout">
				<tr class=trcolor>
					<td class="tdr" >
						��ʱ�������ƣ�
					</td>
					<td class="tdl" colspan="3">
						<bean:write name="job_detail" property="sendObjectName" />
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr">
						���Ͷ��ź��룺
					</td>
					<td class="tdl" colspan="3">
						<bean:write name="job_detail" property="simId" />
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr" >
						���Ͷ������ݣ�
					</td>
					<td class="tdl" colspan="3">
						<bean:write name="job_detail" property="sendContent" />
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr" >
						ָ������ʱ�䣺
					</td>
					<td class="tdl" colspan="3">
						<logic:notEmpty name="job_detail" property="firstSendTime">
						��ʼʱ�䣺
						<bean:write name="job_detail" property="firstSendTime" />
						</logic:notEmpty>
						<logic:notEmpty name="job_detail" property="lastSendTime">
						����ʱ�䣺
						<bean:write name="job_detail" property="lastSendTime" />
						</logic:notEmpty>
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr" >
						�������ͣ�
					</td>
					<td class="tdl">
						<c:if test="${job_detail.sendType=='-1'}">��ʱ���η���</c:if>
						<c:if test="${job_detail.sendType=='1'}">��ʱ�ظ�����</c:if>
						<c:if test="${job_detail.sendType=='2'}">�̶����ڷ���</c:if>
					</td>
					<td class="tdr" >
						���ͼ����
					</td>
					<td class="tdl">
						<c:if test="${job_detail.sendType=='1'}">
							<bean:write name="job_detail" property="sendTimeSpace" />
							<c:if test="${job_detail.sendTimeType=='0'}">��</c:if>
							<c:if test="${job_detail.sendTimeType=='1'}">��</c:if>
							<c:if test="${job_detail.sendTimeType=='2'}">Сʱ</c:if>
							<c:if test="${job_detail.sendTimeType=='3'}">��</c:if>
							<c:if test="${job_detail.sendTimeType=='4'}">��</c:if>
						</c:if>
						<c:if test="${job_detail.sendType=='2'}">
							<bean:write name="job_detail" property="sendCycleRule" />
						</c:if>
					</td>
				</tr>
				<tr class=trcolor>
					<td class="tdr" >
						�ϴη���ʱ�䣺
					</td>
					<td class="tdl">
						<bean:write name="job_detail" property="prevSentTime" />
					</td>
					<td class="tdr" >
						�´η���ʱ�䣺
					</td>
					<td class="tdl">
						<bean:write name="job_detail" property="nextSentTime" />
					</td>
				</tr>
			</table>
		</logic:notEmpty>
		<p align="center">
			<input type="button" onclick="goBack();" class="button"
				value="����" />
		</p>
	</body>
</html>
