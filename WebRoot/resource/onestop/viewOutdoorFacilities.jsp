<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<script src="${ctx}/js/validation/prototype.js" type="text/javascript"></script>
<script src="${ctx}/js/validation/effects.js" type="text/javascript"></script>
<script src="${ctx}/js/validation/validation_cn.js"
	type="text/javascript"></script>
<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet"
	type="text/css">
<script type="text/javascript"
	src="${ctx}/js/wdatePicker/WdatePicker.js"></script>
<link type="text/css" rel="stylesheet"
	href="${ctx}/js/wdatePicker/skin/WdatePicker.css">
<head>
	<script language="javascript" type="">

</script>
</head>
<body>
	<template:titile value="查看铁塔信息" />

	<s:form action="outdoorFacilitiesAction_save" name="view" method="post">
		<table width="850" border="0" align="center" cellpadding="3"
			cellspacing="0" class="tabout">
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					站址编号：
				</td>
				<td class="tdulright" colspan="3" style="width: 85%">
					${BASESTATIONS[outdoorFacilities.parentId]}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					铁塔类型：
				</td>
				<td class="tdulright" style="width: 35%">
					<apptag:dynLabel dicType="tower_type" objType="dic"
						id="${outdoorFacilities.towerType}"></apptag:dynLabel>
				</td>
				<td class="tdulleft" style="width: 15%">
					铁塔高度：
				</td>
				<td class="tdulright" style="width: 35%">
					${outdoorFacilities.towerHeight}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					铁塔平台数：
				</td>
				<td class="tdulright" style="width: 35%">
					${outdoorFacilities.towerPlatformNum}
				</td>
				<td class="tdulleft" style="width: 15%">
					屋顶桅杆类型：
				</td>
				<td class="tdulright" style="width: 35%">
					<apptag:dynLabel dicType="mast_type" objType="dic"
						id="${outdoorFacilities.mastType}"></apptag:dynLabel>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					抱杆高度：
				</td>
				<td class="tdulright" style="width: 35%">
					${outdoorFacilities.poleHeight}
				</td>
				<td class="tdulleft" style="width: 15%">
					铁塔名称：
				</td>
				<td class="tdulright" style="width: 35%">
					${outdoorFacilities.towerName}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					铁塔编号：
				</td>
				<td class="tdulright" style="width: 35%">
					${outdoorFacilities.towerCode}
				</td>
				<td class="tdulleft" style="width: 15%">
					原名称：
				</td>
				<td class="tdulright" style="width: 35%">
					${outdoorFacilities.oldName}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					铁塔厂家：
				</td>
				<td class="tdulright" style="width: 35%">
					${outdoorFacilities.producter}
				</td>
				<td class="tdulleft" style="width: 15%">
					规格型号：
				</td>
				<td class="tdulright" style="width: 35%">
					${outdoorFacilities.towerMode}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					铁塔材质：
				</td>
				<td class="tdulright" style="width: 35%">
					<apptag:dynLabel dicType="tower_material" objType="dic"
						id="${outdoorFacilities.towerMaterial}"></apptag:dynLabel>
				</td>
				<td class="tdulleft" style="width: 15%">
					铁塔基础：
				</td>
				<td class="tdulright" style="width: 35%">
					<apptag:dynLabel dicType="tower_base" objType="dic"
						id="${outdoorFacilities.towerBase}"></apptag:dynLabel>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					已用平台数：
				</td>
				<td class="tdulright" style="width: 35%">
					${outdoorFacilities.usePlatformNum}
				</td>
				<td class="tdulleft" style="width: 15%">
					产权性质：
				</td>
				<td class="tdulright" style="width: 35%">
					<apptag:dynLabel dicType="property_right" objType="dic"
						id="${outdoorFacilities.propertyRight}"></apptag:dynLabel>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					设备状态：
				</td>
				<td class="tdulright" style="width: 35%">
					<apptag:dynLabel dicType="device_state" objType="dic"
						id="${outdoorFacilities.state}"></apptag:dynLabel>
				</td>
				<td class="tdulleft" style="width: 15%">
					入网时间：
				</td>
				<td class="tdulright" style="width: 35%">
					${outdoorFacilities.anTime}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					是否与其他运营商共享：
				</td>
				<td class="tdulright" style="width: 35%">
					<c:if test="${row.isBeautify == 'y'}">是</c:if>
					<c:if test="${row.isBeautify != 'y'}">否</c:if>
				</td>
				<td class="tdulleft" style="width: 15%">
					共享运营商：
				</td>
				<td class="tdulright" style="width: 35%">
					${outdoorFacilities.shareOperator}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					固定资产编号：
				</td>
				<td class="tdulright" colspan="3" style="width: 85%">
					${outdoorFacilities.assetCode}
				</td>
			</tr>
			<table width="40%" border="0" style="margin-top: 6px" align="center"
				cellpadding="0" cellspacing="0">
				<tr align="center">
				</tr>
			</table>
		</table>
		<script type="text/javascript">
	function formCallback(result, form) {
		window.status = "valiation callback for form '" + form.id
				+ "': result = " + result;
	}

	var valid = new Validation('addStationFrom', {
		immediate : true,
		onFormValidate : formCallback
	});
	</script>
	</s:form>
</body>
