<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<link href="${ctx}/js/jQuery/pikachoose/styles/bottom.css"
	rel="stylesheet" type="text/css">
<script type="text/javascript"
	src="${ctx}/js/jQuery/pikachoose/lib/jquery.pikachoose.js"></script>
<script type="text/javascript" src="${ctx}/js/prototype.js"></script>
<script type="text/javascript" src="${ctx }/bs/fault/js/fault_common.js"></script>
<body>
	<template:titile value="故障告警单详细信息" />
	<table width="850" border="0" align="center" cellpadding="3"
		cellspacing="0" class="tabout">
		<c:if test="${fault_alert.findType=='B16' }">
			<tr class="trwhite">
				<td class="tdulright" colspan="4" style="width: 100%">
					<b>故障图片</b>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulright" colspan="4"
					style="width: 100%; padding: 5px; text-align: center;"
					id="photosTd">
				</td>
			</tr>
		</c:if>
		<tr class="trwhite">
			<td class="tdulright" colspan="4" style="width: 100%">
				<b>故障单信息</b>
			</td>
		</tr>
		<tr class="trwhite">
			<td class="tdulleft" style="width: 15%">
				故障标题：
			</td>
			<td class="tdulright" colspan="3" style="width: 85%">
				${fault_alert.troubleTitle }
			</td>
		</tr>
		<tr class="trwhite">
			<td class="tdulleft" style="width: 15%">
				发现方式：
			</td>
			<td class="tdulright" style="width: 35%">
				<apptag:dynLabel objType="dic" id="${fault_alert.findType}"
					dicType="FIND_TYPE"></apptag:dynLabel>
			</td>
			<td class="tdulleft" style="width: 15%">
				EMOS单号：
			</td>
			<td class="tdulright" style="width: 35%">
				${fault_alert.eomsId }
			</td>
		</tr>
		<tr class="trwhite">
			<td class="tdulleft" style="width: 15%">
				故障发生时间：
			</td>
			<td class="tdulright" style="width: 35%">
				<fmt:formatDate value="${fault_alert.troubleTime }"
					pattern="yyyy-MM-dd HH:mm:ss" var="troubleTime" />
				${troubleTime }
			</td>
			<td class="tdulleft" style="width: 15%">
				是否紧急故障：
			</td>
			<td class="tdulright" style="width: 35%">
				<c:if test="${fault_alert.isInstancy==1 }">是</c:if>
				<c:if test="${fault_alert.isInstancy==2 }">否</c:if>
			</td>
		</tr>
		<tr class="trwhite">
			<td class="tdulleft" style="width: 15%">
				故障级别：
			</td>
			<td class="tdulright" style="width: 35%">
				<apptag:dynLabel objType="dic" id="${fault_alert.troubleLevel}"
					dicType="TROUBLE_LEVEL"></apptag:dynLabel>
			</td>
			<td class="tdulleft" style="width: 15%">
			</td>
			<td class="tdulright" style="width: 35%">
			</td>
		</tr>
		<tr class="trwhite">
			<td class="tdulleft" style="width: 15%">
				故障描述：
			</td>
			<td class="tdulright" colspan="3" style="width: 85%">
				${fault_alert.troubleDesc }
			</td>
		</tr>
		<tr class="trwhite">
			<td class="tdulleft" style="width: 15%">
				故障站点：
			</td>
			<td class="tdulright" style="width: 35%">
				${fault_alert.stationName }
			</td>
			<td class="tdulleft" style="width: 15%">
				故障地点：
			</td>
			<td class="tdulright" style="width: 35%">
				${fault_alert.address }
			</td>
		</tr>
	</table>
	<table width="100%" style="border: 0px">
		<tr class="trwhite">
			<td style="text-align: center; border: 0px">
				<input type="button" class="button" onclick="history.back()"
					value="返回">
			</td>
		</tr>
	</table>
	<script type="text/javascript">
	showFaultPhotos('${fault_alert.id }');
	</script>
</body>