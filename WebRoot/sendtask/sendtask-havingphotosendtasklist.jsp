<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<SCRIPT language=JavaScript src="${ctx}/common/PopupDlg.js" type=""></SCRIPT>
<SCRIPT language=javascript src="${ctx}/common/Comm.js" type=""></SCRIPT>
<link rel="stylesheet" type="text/css"
	href="${ctx }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${ctx }/js/easyui/themes/icon.css">
<script type="text/javascript" src="${ctx}/js/easyui/jquery-1.6.1.js"></script>
<script type="text/javascript"
	src="${ctx }/js/easyui/jquery.easyui.min.js"></script>
<script src="${ctx }/js/easyui/easyloader.js"></script>
<html>
	<head>
		<title>sendtask</title>
		<script type="text/javascript"
			src="${ctx }/sendtask/js/sendtask-common.js"></script>
		<script type="text/javascript"
			src="${ctx }/sendtask/js/sendtask-list.js"></script>
		<script type="" language="javascript">
		var contextPath="${ctx}";
		setRegionId("${LOGIN_USER.regionID}");
		toViewPhotoForm = function (dispatchId, inputUrl) {
			var url = contextPath + "/sendTaskPhotoAction_photoView.jspx?id=" + dispatchId + "&&backUrl=" + inputUrl;
			location.href = url;
		};
		</script>
	</head>
	<body>
		<!--��ʾ�����ɵ�ҳ��-->
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
		<br>
		<template:titile value="��Ŀ���Ƶ���Ƭ��ѯ" />
		<s:form action="sendTaskWorkAction_photoQuery" method="post">
			<input name="is_query" value="1" type="hidden" />
			<table width="850" border="0" align="center" cellpadding="3"
				cellspacing="0" class="tabout">
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						�ɵ���λ:
					</td>
					<td class="tdulright" style="width: 35%">
						<select id="sel_send_orgs" style="width: 240px;"
							name="sendTask.sendorgid"></select>
					</td>
					<td class="tdulleft" style="width: 15%">
						�ɵ���:
					</td>
					<td class="tdulright" style="width: 35%">
						<select id="sel_send_users" style="width: 240px;"
							name="sendTask.senduserid"></select>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						����λ:
					</td>
					<td class="tdulright" style="width: 35%">
						<select id="sel_accept_orgs" style="width: 240px;"
							name="sendTask.acceptorgid"></select>
					</td>
					<td class="tdulleft" style="width: 15%">
						������:
					</td>
					<td class="tdulright" style="width: 35%">
						<select id="sel_accept_users" style="width: 240px;"
							name="sendTask.acceptuserid"></select>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						�ɵ�ʱ��:
					</td>
					<td class="tdulright" style="width: 35%">
						<input type="text" name="sendTask.sendTimeStart"
							value="${sendTask.sendTimeStart}" readonly="readonly"
							class="Wdate" style="width: 110px"
							onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
						-
						<input type="text" name="sendTask.sendTimeEnd"
							value="${sendTask.sendTimeEnd}" readonly="readonly" class="Wdate"
							style="width: 110px"
							onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
					</td>
					<td class="tdulleft" style="width: 15%">
						�ɵ�����:
					</td>
					<td class="tdulright" style="width: 35%">
						<apptag:quickLoadList cssClass="inputtext" style="width: 240px;"
							name="sendTask.sendtype" listName="dispatch_task" type="select"
							isRegion="false" keyValue="${sendTask.sendtype}" isQuery="query" />
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						�ɵ�����:
					</td>
					<td class="tdulright" style="width: 35%">
						<input name="sendTask.sendtopic" style="width: 240px;"
							class="inputtext" value="${sendTask.sendtopic}" maxlength="100" />
					</td>
					<td class="tdulleft" style="width: 15%">
						�ɵ�״̬:
					</td>
					<td class="tdulright" style="width: 35%">
						<select id="sendTask.workstate" style="width: 240px;"
							name="sendTask.workstate">
							<option value="">
								����
							</option>
							<option value="ǩ��">
								��ǩ��
							</option>
							<option value="�ظ�">
								���ظ�
							</option>
							<option value="��֤">
								����֤
							</option>
							<option value="0">
								�����ɵ�
							</option>
						</select>
					</td>
				</tr>
			</table>
			<div align="center">
				<input type="submit" value="��ѯ" class="button">
			</div>
		</s:form>
		<c:if test="${sessionScope.SEND_TASK_LIST!=null}">
			<display:table name="sessionScope.SEND_TASK_LIST"
				requestURI="${ctx}/sendTaskWorkAction_photoQuery.jspx"
				id="currentRowObject" pagesize="20" style="width:100%">
				<c:set var="dispatchId" value="${currentRowObject.id}"></c:set>
				<c:set var="sendTopic" value="${currentRowObject.sendtopic}"></c:set>
				<display:column media="html" title="����" sortable="true"
					maxLength="40">
					<a
						href="javascript:toViewForm('${dispatchId }','/sendTaskWorkAction_photoQuery.jspx')">
						${sendTopic }</a>
				</display:column>
				<display:column property="sendtypelabel" title="����"
					style="width:80px" maxLength="5" sortable="true" />
				<display:column property="sendorgname" title="�ɵ���λ"
					style="width:80px" sortable="true" />
				<display:column property="sendusername" title="�ɷ���"
					style="width:60px" sortable="true" />
				<display:column property="sendtime" title="�ɷ�ʱ��" style="width:140px"
					sortable="true" />
				<display:column media="html" title="�ֳ���Ƭ" style="width:100px">
					<a href="javascript:toViewPhotoForm('${dispatchId }','/sendTaskPhotoAction_photoQuery.jspx');">�鿴</a>
				</display:column>
			</display:table>
			<a href="#">����ΪExcel�ļ�</a>
		</c:if>
	</body>
	<script type="text/javascript">
	var values=['${sendTask.sendorgid}','${sendTask.senduserid}','${sendTask.acceptorgid}','${sendTask.acceptuserid}']
	setDefaultValues(values);
	</script>
</html>
