<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
	media="screen, print" />
<script src="${ctx}/js/extjs/ux/ux-all-debug.js" type="text/javascript"></script>
<script src="${ctx}/js/extjs/ux/Appcombox.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx }/bs/fault/js/fault_common.js"></script>
<script language="javascript" type="text/javascript">
	setContextPath("${ctx}");
	Ext.onReady(function() {
		// 发现方式		parameter.findType			FIND_TYPE		parameter_findtype
		var find_type = new Appcombox(
			{
				hiddenName : 'parameter.findType',
				emptyText : '请选择',
				dataUrl : '${ctx}/common/externalresources_getDictionaryJson.jspx?type=FIND_TYPE',
				dataCode : 'CODEVALUE',
				dataText : 'LABLE',
				allowBlank : true,
				renderTo : 'parameter_findtype'
			});
		find_type
			.setComboValue(
				'${parameter.findType}',
				'<apptag:dynLabel objType="dic" id="${parameter.findType}" dicType="FIND_TYPE"></apptag:dynLabel>');
	});
</script>
<template:titile value="故障派单信息列表" />
<s:form action="faultDispatchAction_list.jspx" method="post">
	<input name="parameter.isQuery" value="1" type="hidden" />
	<table width="850" border="0" align="center" cellpadding="3"
		cellspacing="0" class="tabout">
		<tr class="trwhite">
			<td class="tdulleft" style="width: 15%">
				故障单标题:
			</td>
			<td class="tdulright" style="width: 35%">
				<input type="hidden" name="businessType" value="${businessType}" />
				<input type="text" class="inputtext" style="width: 220px"
					name="parameter.troubleTitle" value="${parameter.troubleTitle}" />
			</td>
			<td class="tdulleft" style="width: 15%">
				故障发生时间:
			</td>
			<td class="tdulright" style="width: 35%">
				<input type="text" class="inputtext" style="width: 95px"
					name="parameter.troubleTimeStart" class="Wdate inputtext"
					value="${parameter.troubleTimeStart}"
					onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
				至
				<input type="text" class="inputtext" style="width: 95px"
					name="parameter.troubleTimeEnd" class="Wdate inputtext"
					value="${parameter.troubleTimeEnd}"
					onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
			</td>
		</tr>
		<tr class="trwhite">
			<td class="tdulleft" style="width: 15%">
				故障发生地点:
			</td>
			<td class="tdulright" style="width: 35%">
				<input type="text" class="inputtext" style="width: 220px"
					name="parameter.address" alt="支持模糊搜索" value="${parameter.address}" />
			</td>
			<td class="tdulleft" style="width: 15%">
				是否紧急:
			</td>
			<td class="tdulright" style="width: 35%">
				<c:set var="checked_isinstancy_unlimited" value=" checked"></c:set>
				<c:set var="checked_isinstancy_no" value=""></c:set>
				<c:set var="checked_isinstancy_yes" value=""></c:set>
				<c:if test="${parameter.isInstancy==1 }">
					<c:set var="checked_isinstancy_unlimited" value=""></c:set>
					<c:set var="checked_isinstancy_no" value=""></c:set>
					<c:set var="checked_isinstancy_yes" value=" checked"></c:set>
				</c:if>
				<c:if test="${parameter.isInstancy==2 }">
					<c:set var="checked_isinstancy_unlimited" value=""></c:set>
					<c:set var="checked_isinstancy_no" value=" checked"></c:set>
					<c:set var="checked_isinstancy_yes" value=""></c:set>
				</c:if>
				<input type="radio" name="parameter.isInstancy" value=""${checked_isinstancy_unlimited }>
				不限
				<input type="radio" name="parameter.isInstancy" value="2"${checked_isinstancy_no }>
				否
				<input type="radio" name="parameter.isInstancy" value="1"${checked_isinstancy_yes }>
				是
			</td>
		</tr>
		<tr class="trwhite">
			<td class="tdulleft" style="width: 15%">
				发现方式:
			</td>
			<td class="tdulright" style="width: 85%" colspan="3">
				<div id="parameter_findtype"></div>
			</td>
		</tr>
	</table>
	<div align="center">
		<html:submit styleClass="button">查询</html:submit>
	</div>
</s:form>
<display:table name="sessionScope.FAULT_DISPATCH_LIST" id="row"
	pagesize="18" export="false">
	<display:column property="trouble_title" title="故障单标题" />
	<display:column property="eoms_id" title="EOMS单号" />
	<display:column property="trouble_time_dis" title="故障发生时间" />
	<display:column property="address" title="故障发生地点" />
	<display:column title="故障站点">
		<c:set var="key" value="${row.station_id}_${row.station_type }"></c:set>
		<c:out value="${RESOURCES_MAP[key]}"></c:out>
	</display:column>
	<display:column title="是否紧急">
		<c:if test="${row.is_instancy==1 }">是</c:if>
		<c:if test="${row.is_instancy==2 }">否</c:if>
	</display:column>
	<display:column property="lable" title="发现方式" />
	<display:column media="html" title="操作" paramId="id">
		<a
			href="javascript:viewFaultDispatch('${row.dispatch_id}','${businessType }')">查看</a>
	</display:column>
</display:table>
