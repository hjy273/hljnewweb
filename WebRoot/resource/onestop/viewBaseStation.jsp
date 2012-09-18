<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<body>
	<template:titile value="查看基站"/>
	<s:form action="stationAction_save" name="view" method="post">
		<table width="850" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
			<tr class="trwhite">
				<td class="tdulright" style="width:15%">
					基础信息
				</td>
				<td class="tdulright" colspan="3" style="width:85%">
					<a href="javascript:showOrHide('baseInfoTb');">展开/隐藏</a>
				</td>
			</tr>
			<tbody id="baseInfoTb" style="display:;">
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					站址编号：
				</td>
				<td class="tdulright" style="width:35%">
					${baseStation.stationCode}
				</td>
				<td class="tdulleft" style="width:15%">
					基站名称：
				</td>
				<td class="tdulright" style="width:35%">
					${baseStation.stationName}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					所属地市：
				</td>
				<td class="tdulright" style="width:35%">
					${baseStation.city }
				</td>
				<td class="tdulleft" style="width:15%">
					所属区县：
				</td>
				<td class="tdulright" style="width:35%">
					<apptag:dynLabel objType="region" id="${baseStation.district}"
							dicType=""></apptag:dynLabel>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					所属乡镇：
				</td>
				<td class="tdulright" style="width:35%">
					${baseStation.town}
				</td>
				<td class="tdulleft" style="width:15%">
					所属行政村：
				</td>
				<td class="tdulright" style="width:35%">
					${baseStation.village }
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					详细地址：
				</td>
				<td class="tdulright" style="width:35%">
					${baseStation.address }
				</td>
				<td class="tdulleft" style="width:15%">
					入网时间：
				</td>
				<td class="tdulright" style="width:35%">
					${baseStation.anTime }
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					机房类型：
				</td>
				<td class="tdulright" style="width:35%">
					<apptag:dynLabel objType="dic" id="${baseStation.mrType}"
							dicType="property_right"></apptag:dynLabel>
				</td>
				<td class="tdulleft" style="width:15%">
					房屋面积：
				</td>
				<td class="tdulright" style="width:35%">
					${baseStation.roomArea}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					房屋结构：
				</td>
				<td class="tdulright" colspan="3" style="width:85%">
					<apptag:dynLabel objType="dic" id="${baseStation.roomStructure}"
							dicType="room_structure"></apptag:dynLabel>
				</td>
			</tr>
			</tbody>
			<tr class="trwhite">
				<td class="tdulright" style="width:15%">
					地理信息
				</td>
				<td class="tdulright" colspan="3" style="width:85%">
				</td>
			</tr>
			<tbody id="geoInfoTb">
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					经度：
				</td>
				<td class="tdulright" style="width:35%">
					${baseStation.longitude}
				</td>
				<td class="tdulleft" style="width:15%">
					纬度：
				</td>
				<td class="tdulright" style="width:35%">
					${baseStation.latitude}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					海拔(米)：
				</td>
				<td class="tdulright" style="width:35%">
					${baseStation.elevation}
				</td>
				<td class="tdulleft" style="width:15%">
					地理特征：
				</td>
				<td class="tdulright" style="width:35%">
					<apptag:dynLabel objType="dic" id="${baseStation.geoFeature}"
							dicType="geography"></apptag:dynLabel>
				</td>
			</tr>
			</tbody>
			<tr class="trwhite">
				<td class="tdulright" style="width:15%">
					标识信息
				</td>
				<td class="tdulright" colspan="3" style="width:85%">
				</td>
			</tr>
			<tbody id="flagInfoTb">
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					边界基站标识：
				</td>
				<td class="tdulright" style="width:35%">
					<c:if test="${baseStation.isBoundary=='y'}">是</c:if>
					<c:if test="${baseStation.isBoundary!='y'}">否</c:if>
				</td>
				<td class="tdulleft" style="width:15%">
					VIP基站标识：
				</td>
				<td class="tdulright" style="width:35%">
					<c:if test="${baseStation.isVip=='y'}">是</c:if>
					<c:if test="${baseStation.isVip !='y'}">否</c:if>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					传输节点标识：
				</td>
				<td class="tdulright" style="width:35%">
					<c:if test="${baseStation.isTransitNode == 'y'}">是</c:if>
					<c:if test="${baseStation.isTransitNode != 'y'}">否</c:if>
				</td>
				<td class="tdulleft" style="width:15%">
					边际站标识：
				</td>
				<td class="tdulright" style="width:35%">
					<c:if test="${baseStation.isLimit == 'y'}">是</c:if>
					<c:if test="${baseStation.isLimit != 'y'}">否</c:if>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					室内覆盖标识：
				</td>
				<td class="tdulright" style="width:35%">
					<c:if test="${baseStation.isIndoorCoverage == 'y'}">是</c:if>
					<c:if test="${baseStation.isIndoorCoverage != 'y'}">否</c:if>
				</td>
				<td class="tdulleft" style="width:15%">
					村通标识：
				</td>
				<td class="tdulright" style="width:35%">
					<c:if test="${baseStation.isEveryVillage == 'y'}">是</c:if>
					<c:if test="${baseStation.isEveryVillage != 'y'}">否</c:if>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					施主基站标识：
				</td>
				<td class="tdulright" style="width:35%">
					<c:if test="${baseStation.isBenefactorBs == 'y'}">是</c:if>
					<c:if test="${baseStation.isBenefactorBs != 'y'}">否</c:if>
				</td>
				<td class="tdulleft" style="width:15%">
					下挂基站标识：
				</td>
				<td class="tdulright" style="width:35%">
					<c:if test="${baseStation.isDrive == 'y'}">是</c:if>
					<c:if test="${baseStation.isDrive != 'y'}">否</c:if>
				</td>
			</tr>
			</tbody>
			<tr class="trwhite">
				<td class="tdulright" style="width:15%">
					站点情况
				</td>
				<td class="tdulright" colspan="3" style="width:85%">
				</td>
			</tr>
			<tbody id="stationInfoTb">
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					卫生洁具：
				</td>
				<td class="tdulright" style="width:35%">
					<c:if test="${baseStation.isSanitaryWare == 'y'}">是</c:if>
					<c:if test="${baseStation.isSanitaryWare != 'y'}">否</c:if>
				</td>
				<td class="tdulleft" style="width:15%">
					市电引入情况：
				</td>
				<td class="tdulright" style="width:35%">
					${baseStation.mainsLeadin}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					门锁情况：
				</td>
				<td class="tdulright" style="width:35%">
					${baseStation.gateLock}
				</td>
				<td class="tdulleft" style="width:15%">
					遗留问题：
				</td>
				<td class="tdulright" style="width:35%">
					${baseStation.leaveOverQuestion}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					业主联系人：
				</td>
				<td class="tdulright" style="width:35%">
					${baseStation.owner}
				</td>
				<td class="tdulleft" style="width:15%">
					业主联系电话：
				</td>
				<td class="tdulright" style="width:35%">
					${baseStation.ownerTel}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					维护单位：
				</td>
				<td class="tdulright" colspan="3" style="width:85%">
					${maintenances[baseStation.maintenanceId]}
				</td>
			</tr>
			</tbody>
			<tr class="trwhite">
				<td class="tdulright" style="width:15%">
					其他信息
				</td>
				<td class="tdulright" colspan="3" style="width:85%">
				</td>
			</tr>
			<tbody id="otherInfoTb">
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					归属BSC：
				</td>
				<td class="tdulright" style="width:35%">
					${baseStation.attachBSC}
				</td>
				<td class="tdulleft" style="width:15%">
					BCF数量：
				</td>
				<td class="tdulright" style="width:35%">
					${baseStation.bcfNum}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					频段：
				</td>
				<td class="tdulright" style="width:35%">
					${baseStation.frequencyChannel}
				</td>
				<td class="tdulleft" style="width:15%">
					基站类型：
				</td>
				<td class="tdulright" style="width:35%">
					<apptag:dynLabel objType="dic" id="${baseStation.bsType}"
							dicType="basestation_type"></apptag:dynLabel>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					基站标识：
				</td>
				<td class="tdulright" style="width:35%">
					${baseStation.bsIdentifies}
				</td>
				<td class="tdulleft" style="width:15%">
					基站级别：
				</td>
				<td class="tdulright" style="width:35%">
					<apptag:dynLabel objType="dic" id="${baseStation.bsLevel}"
							dicType="basestation_level"></apptag:dynLabel>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					基站分类：
				</td>
				<td class="tdulright" style="width:35%">
					<apptag:dynLabel objType="dic" id="${baseStation.bsSort}"
							dicType="basestation_sort"></apptag:dynLabel>
				</td>
				<td class="tdulleft" style="width:15%">
					基站配置：
				</td>
				<td class="tdulright" style="width:35%">
					${baseStation.bsDeploy}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					区域类别：
				</td>
				<td class="tdulright" style="width:35%">
					${baseStation.areaType}
				</td>
				<td class="tdulleft" style="width:15%">
					覆盖区域类型：
				</td>
				<td class="tdulright" style="width:35%">
					<apptag:dynLabel objType="dic"
							id="${baseStation.coverageAreaType}" dicType="overlay_area_type"></apptag:dynLabel>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					覆盖区域：
				</td>
				<td class="tdulright" colspan="3" style="width:85%">
					<apptag:dynLabel objType="dic" id="${baseStation.coverageArea}"
							dicType="overlay_area"></apptag:dynLabel>
				</td>
			</tr>
			</tbody>
			<table width="40%" border="0"  style="margin-top: 6px" align="center" cellpadding="0" cellspacing="0"><tr align="center">
		      	<td>
		      	  	<input type="button" class="button" onclick="history.back()" value="返回" >
		      	</td>
		    </tr></table>
		</table>
	</s:form>
</body>