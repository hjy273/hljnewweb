<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ include file="/common/header.jsp"%>
<script type="text/javascript" src="${ctx}/js/easyui/jquery-1.6.1.js"></script>
<link href="${ctx}/js/pikachoose/styles/bottom.css" rel="stylesheet"
	type="text/css">
<script type="text/javascript"
	src="${ctx}/js/pikachoose/lib/jquery.pikachoose.js"></script>
<script type="text/javascript"
	src="${ctx}/sendtask/js/sendtask-common.js"></script>
<html>
	<head>
		<title>sendtask</title>
		<script type="text/javascript" src="/WebApp/js/prototype.js"></script>
		<script type="" language="javascript">
		var contextPath="${ctx}";
		jQuery(document).ready(function (){
			jQuery("#pikame").PikaChoose({carousel:true});
		});
		</script>
	</head>
	<body>
		<!--显示一个派单详细信息页面-->
		<br>
		<template:titile value="${sendTask.sendtopic}派单现场照片" />
		<table width="90%" border="0" align="center" cellpadding="3"
			cellspacing="0" class="tabout">
			<tr class=trcolor>
				<td class="tdr" width="15%">
					工作类别：
				</td>
				<td class="tdl" width="35%">
					<apptag:quickLoadList cssClass="" name="" listName="dispatch_task"
						keyValue="${sendTask.sendtype}" type="look" isRegion="false"></apptag:quickLoadList>
				</td>
				<td class="tdr">
					派单时间：
				</td>
				<td class="tdl">
					${sendtime }
				</td>
			</tr>
			<!-- 
			<tr class=trcolor>
				<td class="tdr">
					派单部门：
				</td>
				<td class="tdl">
					${sendTask.sendorgname }
				</td>
				<td class="tdr">
					派 单 人：
				</td>
				<td class="tdl">
					${sendTask.sendusername }
				</td>
			</tr>
			 -->
			<tr class=trcolor>
				<td class="tdr">
					受理部门：
				</td>
				<td class="tdl">
					${sendTask.acceptorgname }
				</td>
				<td class="tdr">
					受理人：
				</td>
				<td class="tdl">
					${sendTask.acceptusername }
				</td>
			</tr>
			<!-- 
			<tr class=trcolor>
				<td class="tdr">
					任务主题：
				</td>
				<td class="tdl" colspan="3">
					${sendTask.sendtopic }
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdr">
					任务说明：
				</td>
				<td class="tdl" colspan="3">
					${sendTask.sendtext }
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdr">
					处理时限：
				</td>
				<td class="tdl" colspan="3">
					${processterm }
				</td>
			</tr>
			<tr class=trcolor>
				<td class="tdr">
					任务附件：
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
			 -->
			<tr class=trcolor>
				<td class="tdr">
					照片：
				</td>
				<td class="tdl" colspan="3">
					<!-- 
					<apptag:upload cssClass="" entityId="${sendTask.id}"
						entityType="COMMON_SENDTASK" state="look" />
					 -->
					<div class="pikachoose">
						<ul id="pikame">
							<c:forEach var="oneImage" items="${PHOTO_VIEW_LIST}">
								<c:set var="fileId" value="${oneImage.fileid}"></c:set>
								<c:set var="savePath" value="${oneImage.savepath}"></c:set>
								<li>
									<img src="${ctx }/imageServlet?imageId=${fileId }" />
									<!-- <img src="${ctx }${savePath }"> -->
									<!-- <span>${oneImage.fileid}</span> -->
								</li>
							</c:forEach>
						</ul>
					</div>
				</td>
			</tr>
		</table>
		<p align="center">
			<input type="button" value="返回" class="button"
				onclick="goBack('${url}');">
		</p>
	</body>
</html>
