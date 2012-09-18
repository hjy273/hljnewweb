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
	<br />
	<table border="0" bgcolor="#FFFFFF" width="100%" align="center" cellpadding="0" cellspacing="0"><tr><td>
	<s:form action="outdoorFacilitiesAction_save" name="view" method="post">
		<table width="850" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
		<tr class="trwhite">
				<td class="tdulright" style="width:18%">
					铁塔信息
				</td>
				<td class="tdulright" colspan="3" style="width:82%">
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:18%">
					所属基站：
				</td>
				<td class="tdulright" style="width:32%">
					${BASESTATIONS[outdoorFacilities.parentId]}
				</td>
				<td class="tdulleft" style="width:15%">
					铁塔名称：
				</td>
				<td class="tdulright" style="width:35%">
					${outdoorFacilities.towerName}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:18%">
					铁塔编号：
				</td>
				<td class="tdulright" style="width:32%">
					${outdoorFacilities.towerCode}
				</td>
				<td class="tdulleft" style="width:15%">
					设备状态：
				</td>
				<td class="tdulright" style="width:35%">
					<apptag:dynLabel objType="dic" id="${outdoorFacilities.state}" dicType="device_state"></apptag:dynLabel>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:18%">
					铁塔类型：
				</td>
				<td class="tdulright" style="width:32%">
					<apptag:dynLabel objType="dic" id="${outdoorFacilities.towerType}" dicType="TOWERTYPE"></apptag:dynLabel>
				</td>
				<td class="tdulleft" style="width:15%">
					铁塔高度：
				</td>
				<td class="tdulright" style="width:35%">
					${outdoorFacilities.towerHeight}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:18%">
					铁塔平台数：
				</td>
				<td class="tdulright" style="width:32%">
					${outdoorFacilities.towerPlatformNum}
				</td>
				<td class="tdulleft" style="width:15%">
					已用平台数：
				</td>
				<td class="tdulright" style="width:35%">
					${outdoorFacilities.usePlatformNum}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:18%">
					原名称：
				</td>
				<td class="tdulright" style="width:32%">
					${outdoorFacilities.oldName}
				</td>
				<td class="tdulleft" style="width:15%">
					铁塔厂家：
				</td>
				<td class="tdulright" style="width:35%">
					${outdoorFacilities.producter}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:18%">
					规格型号：
				</td>
				<td class="tdulright" style="width:32%">
					${outdoorFacilities.towerMode}
				</td>
				<td class="tdulleft" style="width:15%">
					铁塔材质：
				</td>
				<td class="tdulright" style="width:35%">
					<apptag:dynLabel objType="dic" id="${outdoorFacilities.towerMaterial}" dicType="tower_material"></apptag:dynLabel>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:18%">
					铁塔基础：
				</td>
				<td class="tdulright" colspan="3" style="width:82%">
					<apptag:dynLabel objType="dic" id="${outdoorFacilities.towerBase}" dicType="tower_base"></apptag:dynLabel>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulright" style="width:18%">
					桅杆信息
				</td>
				<td class="tdulright" colspan="3" style="width:82%">
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:18%">
					屋顶桅杆类型：
				</td>
				<td class="tdulright" style="width:32%">
					<apptag:dynLabel objType="dic" id="${outdoorFacilities.mastType}" dicType="mast_type"></apptag:dynLabel>
				</td>
				<td class="tdulleft" style="width:15%">
					抱杆高度：
				</td>
				<td class="tdulright" style="width:35%">
					${outdoorFacilities.poleHeight}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulright" style="width:18%">
					附加信息
				</td>
				<td class="tdulright" colspan="3" style="width:82%"></td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:18%">
					入网时间：
				</td>
				<td class="tdulright" style="width:32%">
					 ${outdoorFacilities.anTime}
				</td>
				<td class="tdulleft" style="width:15%">
					产权性质：
				</td>
				<td class="tdulright" style="width:35%">
					<apptag:dynLabel objType="dic" id="${outdoorFacilities.propertyRight}" dicType="property_right"></apptag:dynLabel>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:18%">
					是否与其他运营商共享：
				</td>
				<td class="tdulright" style="width:32%">
					<c:if test="${outdoorFacilities.isShare == 'y'}">是</c:if>
					<c:if test="${outdoorFacilities.isShare != 'y'}">否</c:if>
				</td>
				<td class="tdulleft" style="width:15%">
					共享运营商：
				</td>
				<td class="tdulright" style="width:35%">
					${outdoorFacilities.shareOperator}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:18%">
					固定资产编号：
				</td>
				<td class="tdulright" style="width:32%">
					 ${outdoorFacilities.assetCode}
				</td>
				<td class="tdulleft" style="width:15%">
					条形码：
				</td>
				<td class="tdulright" style="width:35%">
					${outdoorFacilities.barCode}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">维护组：</td>
				<td class="tdulright" colspan="3">${outdoorFacilities.patrolGroupName }</td>
			</tr>
		</table>
		<table width="40%" border="0"  style="margin-top: 6px" align="center" cellpadding="0" cellspacing="0"><tr align="center">
				<td>
					<c:if test="${showClose=='0'}">
		      	  	<input type="button" class="button" onclick="history.back()" value="返回" >
		      	  	</c:if>
		      	  	<c:if test="${showClose=='1'}">
		      	  	<input type="button" class="button" onclick="closeWin();" value="关闭" >
		      	  	</c:if>
				</td>
		    </tr></table>
	</s:form>
	</td></tr></table>
</body>
