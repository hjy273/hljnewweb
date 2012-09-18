<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<body>
	<template:titile value="查看直放站" />
	<s:form action="repeaterAction_save" name="view" method="post">
		<table width="850" border="0" align="center" cellpadding="3"
			cellspacing="0" class="tabout">
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					直放站号：
				</td>
				<td class="tdulright" style="width: 35%">
					${repeater.repeaterType}
				</td>
				<td class="tdulleft" style="width: 15%">
					直放站名称：
				</td>
				<td class="tdulright" style="width: 35%">
					${repeater.repeaterName}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					信源基站：
				</td>
				<td class="tdulright" colspan="3" style="width: 85%">
					${BASESTATIONS[repeater.msBsId]}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					信源小区号：
				</td>
				<td class="tdulright" style="width: 35%">
					${repeater.cellCode}
				</td>
				<td class="tdulleft" style="width: 15%">
					网元编码：
				</td>
				<td class="tdulright" style="width: 35%">
					${repeater.netElementCode}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					网元名称：
				</td>
				<td class="tdulright" style="width: 35%">
					${repeater.netElementName}
				</td>
				<td class="tdulleft" style="width: 15%">
					所属城市：
				</td>
				<td class="tdulright" style="width: 35%">
					${repeater.city}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					所属区县：
				</td>
				<td class="tdulright" style="width: 35%">
					<apptag:dynLabel objType="region" id="${repeater.district}"
						dicType=""></apptag:dynLabel>
				</td>
				<td class="tdulleft" style="width: 15%">
					所属乡镇：
				</td>
				<td class="tdulright" style="width: 35%">
					${repeater.town}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					安装位置：
				</td>
				<td class="tdulright" style="width: 35%">
					${repeater.installPlace}
				</td>
				<td class="tdulleft" style="width: 15%">
					经度：
				</td>
				<td class="tdulright" style="width: 35%">
					${repeater.longitude}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					纬度：
				</td>
				<td class="tdulright" style="width: 35%">
					${repeater.latitude}
				</td>
				<td class="tdulleft" style="width: 15%">
					海拔(米)：
				</td>
				<td class="tdulright" style="width: 35%">
					${repeater.elevation}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					归属BSC：
				</td>
				<td class="tdulright" style="width: 35%">
					${repeater.attachBsc}
				</td>
				<td class="tdulleft" style="width: 15%">
					覆盖范围：
				</td>
				<td class="tdulright" style="width: 35%">
					<apptag:dynLabel dicType="overlay_area_type" objType="dic"
						id="${repeater.coverageArea}"></apptag:dynLabel>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					覆盖区域类型：
				</td>
				<td class="tdulright" style="width: 35%">
					<apptag:dynLabel dicType="overlay_area" objType="dic"
						id="${repeater.coverageAreaType}"></apptag:dynLabel>
				</td>
				<td class="tdulleft" style="width: 15%">
					信号接收方式：
				</td>
				<td class="tdulright" style="width: 35%">
					${repeater.receieMode}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					供电方式：
				</td>
				<td class="tdulright" style="width: 35%">
					${repeater.powerSupplyMode}
				</td>
				<td class="tdulleft" style="width: 15%">
					主从标识：
				</td>
				<td class="tdulright" style="width: 35%">
					<c:if test="${repeater.isMasterSlave=='y'}">主</c:if>
					<c:if test="${repeater.isMasterSlave!='y'}">从</c:if>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					是否监控：
				</td>
				<td class="tdulright" style="width: 35%">
					<c:if test="${repeater.isMonitor=='y'}">是</c:if>
					<c:if test="${repeater.isMonitor!='y'}">否</c:if>
				</td>
				<td class="tdulleft" style="width: 15%">
					设备型号：
				</td>
				<td class="tdulright" style="width: 35%">
					${repeater.equModuel}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					生产厂家：
				</td>
				<td class="tdulright" style="width: 35%">
					${repeater.producter}
				</td>
				<td class="tdulleft" style="width: 15%">
					功率：
				</td>
				<td class="tdulright" style="width: 35%">
					${repeater.power}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					选频数：
				</td>
				<td class="tdulright" style="width: 35%">
					${repeater.chooseFrequent}
				</td>
				<td class="tdulleft" style="width: 15%">
					监控SIM卡号：
				</td>
				<td class="tdulright" style="width: 35%">
					${repeater.monitorSim}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					建站时间：
				</td>
				<td class="tdulright" style="width: 35%">
					${repeater.createTime}
				</td>
				<td class="tdulleft" style="width: 15%">
					维护单位：
				</td>
				<td class="tdulright" style="width: 35%">
					${maintenances[repeater.maintenanceId]}
				</td>
			</tr>
			<table width="40%" border="0" style="margin-top: 6px" align="center"
				cellpadding="0" cellspacing="0">
				<tr align="center">
					<td>
						<input type="button" class="button" onclick="history.back()"
							value="返回">
					</td>
				</tr>
			</table>
		</table>
	</s:form>
</body>