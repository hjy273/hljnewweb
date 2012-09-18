<%@ page language="java" contentType="text/html; charset=GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/common/header.jsp"%>
<link rel='stylesheet' type='text/css'
	href='${ctx}/js/extjs/resources/css/ext-all.css' />
<script type='text/javascript'
	src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>

<script type="text/javascript" src="${ctx}/js/view/simplegallery.js"></script>
<script type="text/javascript" src="${ctx}/js/view/jquery-1.2.6.pack.js"></script>

<script type="text/javascript">
	var win;
	showOrHide = function(divId) {
		var display = document.getElementById(divId).style.display;
		document.getElementById("baseInfoTb").style.display = "none";
		document.getElementById("geoInfoTb").style.display = "none";
		document.getElementById("flagInfoTb").style.display = "none";
		document.getElementById("stationInfoTb").style.display = "none";
		document.getElementById("otherInfoTb").style.display = "none";
		document.getElementById("outdoorFacilityInfoTb").style.display = "none";
		document.getElementById("antennaInfoTb").style.display = "none";
		document.getElementById("equipmentInfoTb").style.display = "none";
		document.getElementById("cellInfoTb").style.display = "none";
		document.getElementById("repeaterInfoTb").style.display = "none";
		if (display == "none") {
			document.getElementById(divId).style.display = "";
		} else {
			document.getElementById(divId).style.display = "none";
		}
	}
	showDetail = function(objectType, objectId) {
		var actionUrl = "";
		var objName = "";
		if (objectType == "outdoorFacility") {
			actionUrl = "${ctx}/outdoorFacilitiesAction_view.jspx?flag=view&&showClose=1";
			objName = "铁塔";
		}
		if (objectType == "antenna") {
			actionUrl = "${ctx}/antennaAction_view.jspx?flag=view&&showClose=1";
			objName = "天线";
		}
		if (objectType == "cell") {
			actionUrl = "${ctx}/cellAction_view.jspx?flag=view&&showClose=1";
			objName = "小区";
		}
		if (objectType == "equipment") {
			actionUrl = "${ctx}/equipmentAction_view.jspx?flag=view&&showClose=1";
			objName = "设备";
		}
		if (objectType == "repeater") {
			actionUrl = "${ctx}/repeaterAction_view.jspx?flag=view&&showClose=1";
			objName = "直放站";
		}
		actionUrl = actionUrl + "&id=" + objectId + "&rnd=" + Math.random();
		win = new Ext.Window( {
			layout : 'fit',
			width : 950,
			height : 400,
			resizable : true,
			closeAction : 'close',
			modal : true,
			autoScroll : true,
			autoLoad : {
				url : actionUrl,
				scripts : true
			},
			plain : true,
			title : "查看" + objName + "详细信息"
		});
		win.show(Ext.getBody());
	}
	closeWin = function() {
		win.close();
	}
</script>
<style type="text/css">
#simplegallery1 {
	position: absolute;
	visibility: hidden;
	border: 3px solid darkred;
	left: 50%;
	top: -84.3%;
}

#simplegallery1.gallerydesctext {
	text-align: left;
	padding: 2px 5px;
}
</style>
<c:if test="${PICTURES!=null}">
	<script type="text/javascript">
	var mygallery=new simpleGallery({
	wrapperid: "simplegallery1",
	dimensions: [413, 291], 
	imagearray: ${PICTURES},
	autoplay: [true, 2500, 2],
	persist: false, 
	fadeduration: 500,
	oninit:function(){
	},
	onslide:function(curslide, i){
	}
	})
</script>
</c:if>
<body>
	<template:titile value="查看基站" />
	<br />
	<s:form action="stationAction_save" name="view" method="post">
		<table class="tabout" width="850" style="width: 850px;" border="1"
			align="center" cellpadding="3" cellspacing="0">
			<tr class="trwhite">
				<td class="tdulright" style="width: 15%">
					基础信息
				</td>
				<td class="tdulright" colspan="3" style="width: 85%">
				</td>
			</tr>
			<tbody id="baseInfoTb" style="display: ;">
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						站址编号：
					</td>
					<td class="tdulright" colspan="3" style="width: 85%">
						${baseStation.stationCode}
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						基站名称：
					</td>
					<td class="tdulright" colspan="3" style="width: 85%">
						${baseStation.stationName}
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						所属地市：
					</td>
					<td class="tdulright" colspan="3" style="width: 85%">
						${baseStation.city }
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						所属区县：
					</td>
					<td class="tdulright" colspan="3" style="width: 85%">
						<apptag:dynLabel objType="region" id="${baseStation.district}"
							dicType=""></apptag:dynLabel>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						所属乡镇：
					</td>
					<td class="tdulright" colspan="3" style="width: 85%">
						${baseStation.town}
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						所属行政村：
					</td>
					<td class="tdulright" colspan="3" style="width: 85%">
						${baseStation.village }
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						详细地址：
					</td>
					<td class="tdulright" colspan="3" style="width: 85%">
						${baseStation.address }
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						入网时间：
					</td>
					<td class="tdulright" colspan="3" style="width: 85%">
						${baseStation.anTime }
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						机房类型：
					</td>
					<td class="tdulright" colspan="3" style="width: 85%">
						<apptag:dynLabel objType="dic" id="${baseStation.mrType}"
							dicType="property_right"></apptag:dynLabel>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						房屋面积：
					</td>
					<td class="tdulright" colspan="3" style="width: 85%">
						${baseStation.roomArea}
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						房屋结构：
					</td>
					<td class="tdulright" colspan="3" style="width: 85%">
						<apptag:dynLabel objType="dic" id="${baseStation.roomStructure}"
							dicType="room_structure"></apptag:dynLabel>
					</td>
				</tr>
			</tbody>
			<tr class="trwhite">
				<td class="tdulright" style="width: 15%">
					地理信息
				</td>
				<td class="tdulright" colspan="3" style="width: 85%">
				</td>
			</tr>
			<tbody id="geoInfoTb" style="display: ;">
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						经度：
					</td>
					<td class="tdulright" style="width: 35%">
						${baseStation.longitude}
					</td>
					<td class="tdulleft" style="width: 15%">
						纬度：
					</td>
					<td class="tdulright" style="width: 35%">
						${baseStation.latitude}
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						海拔(米)：
					</td>
					<td class="tdulright" style="width: 35%">
						${baseStation.elevation}
					</td>
					<td class="tdulleft" style="width: 15%">
						地理特征：
					</td>
					<td class="tdulright" style="width: 35%">
						<apptag:dynLabel objType="dic" id="${baseStation.geoFeature}"
							dicType="geography"></apptag:dynLabel>
					</td>
				</tr>
			</tbody>
			<tr class="trwhite">
				<td class="tdulright" style="width: 15%">
					标识信息
				</td>
				<td class="tdulright" colspan="3" style="width: 85%">
				</td>
			</tr>
			<tbody id="flagInfoTb" style="display: ;">
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						边界基站标识：
					</td>
					<td class="tdulright" style="width: 35%">
						<c:if test="${baseStation.isBoundary=='y'}">是</c:if>
						<c:if test="${baseStation.isBoundary!='y'}">否</c:if>
					</td>
					<td class="tdulleft" style="width: 15%">
						VIP基站标识：
					</td>
					<td class="tdulright" style="width: 35%">
						<c:if test="${baseStation.isVip=='y'}">是</c:if>
						<c:if test="${baseStation.isVip !='y'}">否</c:if>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						传输节点标识：
					</td>
					<td class="tdulright" style="width: 35%">
						<c:if test="${baseStation.isTransitNode == 'y'}">是</c:if>
						<c:if test="${baseStation.isTransitNode != 'y'}">否</c:if>
					</td>
					<td class="tdulleft" style="width: 15%">
						边际站标识：
					</td>
					<td class="tdulright" style="width: 35%">
						<c:if test="${baseStation.isLimit == 'y'}">是</c:if>
						<c:if test="${baseStation.isLimit != 'y'}">否</c:if>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						室内覆盖标识：
					</td>
					<td class="tdulright" style="width: 35%">
						<c:if test="${baseStation.isIndoorCoverage == 'y'}">是</c:if>
						<c:if test="${baseStation.isIndoorCoverage != 'y'}">否</c:if>
					</td>
					<td class="tdulleft" style="width: 15%">
						村通标识：
					</td>
					<td class="tdulright" style="width: 35%">
						<c:if test="${baseStation.isEveryVillage == 'y'}">是</c:if>
						<c:if test="${baseStation.isEveryVillage != 'y'}">否</c:if>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						施主基站标识：
					</td>
					<td class="tdulright" style="width: 35%">
						<c:if test="${baseStation.isBenefactorBs == 'y'}">是</c:if>
						<c:if test="${baseStation.isBenefactorBs != 'y'}">否</c:if>
					</td>
					<td class="tdulleft" style="width: 15%">
						下挂基站标识：
					</td>
					<td class="tdulright" style="width: 35%">
						<c:if test="${baseStation.isDrive == 'y'}">是</c:if>
						<c:if test="${baseStation.isDrive != 'y'}">否</c:if>
					</td>
				</tr>
			</tbody>
			<tr class="trwhite">
				<td class="tdulright" style="width: 15%">
					站点情况
				</td>
				<td class="tdulright" colspan="3" style="width: 85%">
				</td>
			</tr>
			<tbody id="stationInfoTb" style="display: ;">
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						卫生洁具：
					</td>
					<td class="tdulright" style="width: 35%">
						<c:if test="${baseStation.isSanitaryWare == 'y'}">是</c:if>
						<c:if test="${baseStation.isSanitaryWare != 'y'}">否</c:if>
					</td>
					<td class="tdulleft" style="width: 15%">
						市电引入情况：
					</td>
					<td class="tdulright" style="width: 35%">
						${baseStation.mainsLeadin}
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						门锁情况：
					</td>
					<td class="tdulright" style="width: 35%">
						${baseStation.gateLock}
					</td>
					<td class="tdulleft" style="width: 15%">
						遗留问题：
					</td>
					<td class="tdulright" style="width: 35%">
						${baseStation.leaveOverQuestion}
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						业主联系人：
					</td>
					<td class="tdulright" style="width: 35%">
						${baseStation.owner}
					</td>
					<td class="tdulleft" style="width: 15%">
						业主联系电话：
					</td>
					<td class="tdulright" style="width: 35%">
						${baseStation.ownerTel}
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						维护单位：
					</td>
					<td class="tdulright" style="width: 35%">
						${maintenances[baseStation.maintenanceId]}
					</td>
					<td class="tdulleft" style="width: 15%">
						维护组：
					</td>
					<td class="tdulright" style="width: 35%">
						${baseStation.patrolGroupName}
					</td>
				</tr>
			</tbody>
			<tr class="trwhite">
				<td class="tdulright" style="width: 15%">
					其他信息
				</td>
				<td class="tdulright" colspan="3" style="width: 85%">
				</td>
			</tr>
			<tbody id="otherInfoTb" style="display: ;">
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						归属BSC：
					</td>
					<td class="tdulright" style="width: 35%">
						${baseStation.attachBSC}
					</td>
					<td class="tdulleft" style="width: 15%">
						BCF数量：
					</td>
					<td class="tdulright" style="width: 35%">
						${baseStation.bcfNum}
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						频段：
					</td>
					<td class="tdulright" style="width: 35%">
						${baseStation.frequencyChannel}
					</td>
					<td class="tdulleft" style="width: 15%">
						基站类型：
					</td>
					<td class="tdulright" style="width: 35%">
						<apptag:dynLabel objType="dic" id="${baseStation.bsType}"
							dicType="basestation_type"></apptag:dynLabel>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						基站标识：
					</td>
					<td class="tdulright" style="width: 35%">
						${baseStation.bsIdentifies}
					</td>
					<td class="tdulleft" style="width: 15%">
						基站级别：
					</td>
					<td class="tdulright" style="width: 35%">
						<apptag:dynLabel objType="dic" id="${baseStation.bsLevel}"
							dicType="basestation_level"></apptag:dynLabel>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						基站分类：
					</td>
					<td class="tdulright" style="width: 35%">
						<apptag:dynLabel objType="dic" id="${baseStation.bsSort}"
							dicType="basestation_sort"></apptag:dynLabel>
					</td>
					<td class="tdulleft" style="width: 15%">
						基站配置：
					</td>
					<td class="tdulright" style="width: 35%">
						${baseStation.bsDeploy}
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						区域类别：
					</td>
					<td class="tdulright" style="width: 35%">
						${baseStation.areaType}
					</td>
					<td class="tdulleft" style="width: 15%">
						覆盖区域类型：
					</td>
					<td class="tdulright" style="width: 35%">
						<apptag:dynLabel objType="dic"
							id="${baseStation.coverageAreaType}" dicType="overlay_area_type"></apptag:dynLabel>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						覆盖区域：
					</td>
					<td class="tdulright" colspan="3" style="width: 85%">
						<apptag:dynLabel objType="dic" id="${baseStation.coverageArea}"
							dicType="overlay_area"></apptag:dynLabel>
					</td>
				</tr>
			</tbody>
			<tr class="trwhite">
				<td class="tdulright" style="width: 15%">
					铁塔信息
				</td>
				<td class="tdulright" colspan="3" style="width: 85%">
				</td>
			</tr>
			<tbody id="outdoorFacilityInfoTb" style="display: ;">
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						铁塔名称：
					</td>
					<td class="tdulright" style="width: 35%">
						${outdoorFacilities.towerName}
					</td>
					<td class="tdulleft" style="width: 15%">
						铁塔编号：
					</td>
					<td class="tdulright" style="width: 35%">
						${outdoorFacilities.towerCode}
					</td>

				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						设备状态：
					</td>
					<td class="tdulright" style="width: 35%">
						<apptag:dynLabel objType="dic" id="${outdoorFacilities.state}"
							dicType="device_state"></apptag:dynLabel>
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
						铁塔类型：
					</td>
					<td class="tdulright" style="width: 35%">
						<apptag:dynLabel objType="dic" id="${outdoorFacilities.towerType}"
							dicType="tower_type"></apptag:dynLabel>
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
						已用平台数：
					</td>
					<td class="tdulright" style="width: 35%">
						${outdoorFacilities.usePlatformNum}
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						屋顶桅杆类型：
					</td>
					<td class="tdulright" style="width: 35%">
						<apptag:dynLabel objType="dic" id="${outdoorFacilities.mastType}"
							dicType="mast_type"></apptag:dynLabel>
					</td>
					<td class="tdulleft" style="width: 15%">
						抱杆高度：
					</td>
					<td class="tdulright" style="width: 35%">
						${outdoorFacilities.poleHeight}
					</td>

				</tr>

				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						原名称：
					</td>
					<td class="tdulright" style="width: 35%">
						${outdoorFacilities.oldName}
					</td>
					<td class="tdulleft" style="width: 15%">
						铁塔厂家：
					</td>
					<td class="tdulright" style="width: 35%">
						${outdoorFacilities.producter}
					</td>

				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						规格型号：
					</td>
					<td class="tdulright" style="width: 35%">
						${outdoorFacilities.towerMode}
					</td>
					<td class="tdulleft" style="width: 15%">
						铁塔材质：
					</td>
					<td class="tdulright" style="width: 35%">
						<apptag:dynLabel objType="dic"
							id="${outdoorFacilities.towerMaterial}" dicType="tower_material"></apptag:dynLabel>
					</td>

				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						铁塔基础：
					</td>
					<td class="tdulright" style="width: 35%">
						<apptag:dynLabel objType="dic" id="${outdoorFacilities.towerBase}"
							dicType="tower_base"></apptag:dynLabel>
					</td>

					<td class="tdulleft" style="width: 15%">
						产权性质：
					</td>
					<td class="tdulright" style="width: 35%">
						<apptag:dynLabel objType="dic"
							id="${outdoorFacilities.propertyRight}" dicType="property_right"></apptag:dynLabel>
					</td>
				</tr>

				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						是否与其他运营商共享：
					</td>
					<td class="tdulright" style="width: 35%">
						<c:if test="${outdoorFacilities.isShare == 'y'}">是</c:if>
						<c:if test="${outdoorFacilities.isShare != 'y'}">否</c:if>
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
			</tbody>
			<tr class="trwhite">
				<td class="tdulright" style="width: 15%">
					天线信息
				</td>
				<td class="tdulright" colspan="3" style="width: 85%">
				</td>
			</tr>
			<tbody id="antennaInfoTb" style="display: ;">
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						天线编号：
					</td>
					<td class="tdulright" style="width: 35%">
						${antenna.antennaCode}
					</td>
					<td class="tdulleft" style="width: 15%">
						天线名称：
					</td>
					<td class="tdulright" style="width: 35%">
						${antenna.antennaName}
					</td>
				</tr>
				<tr class="trwhite">

					<td class="tdulleft" style="width: 15%">
						经度：
					</td>
					<td class="tdulright" style="width: 35%">
						${antenna.longitude}
					</td>
					<td class="tdulleft" style="width: 15%">
						纬度：
					</td>
					<td class="tdulright" style="width: 35%">
						${antenna.latitude}
					</td>
				</tr>
				<tr class="trwhite">

					<td class="tdulleft" style="width: 15%">
						天线型号：
					</td>
					<td class="tdulright" style="width: 35%">
						${antenna.antennaModel}
					</td>
					<td class="tdulleft" style="width: 15%">
						天线极化类型：
					</td>
					<td class="tdulright" style="width: 35%">
						<apptag:dynLabel objType="dic" id="${antenna.polarizationType}"
							dicType="antenna_polarization"></apptag:dynLabel>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						天线数量：
					</td>
					<td class="tdulright" style="width: 35%">
						${antenna.antennaNumber}
					</td>
					<td class="tdulleft" style="width: 15%">
						天线类型：
					</td>
					<td class="tdulright" style="width: 35%">
						<apptag:dynLabel objType="dic" id="${antenna.antennaType}"
							dicType="antenna_type"></apptag:dynLabel>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						天线方位角：
					</td>
					<td class="tdulright" style="width: 35%">
						${antenna.azimuth}
					</td>
					<td class="tdulleft" style="width: 15%">
						天线俯仰角
					</td>
					<td class="tdulright" style="width: 35%">
						${antenna.pitchofangle}
					</td>
				</tr>

				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						天线增益：
					</td>
					<td class="tdulright" style="width: 35%">
						${antenna.gain}
					</td>
					<td class="tdulleft" style="width: 15%">
						天线挂高：
					</td>
					<td class="tdulright" style="width: 35%">
						${antenna.height}
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						天线厂商：
					</td>
					<td class="tdulright" style="width: 35%">
						${antenna.producter}
					</td>
					<td class="tdulleft" style="width: 15%">
						投入使用日期：
					</td>
					<td class="tdulright" style="width: 35%">
						${antenna.createTime}
					</td>
				</tr>

				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						电子倾角：
					</td>
					<td class="tdulright" style="width: 35%">
						${antenna.electornObliquity}
					</td>
					<td class="tdulleft" style="width: 15%">
						机械倾角：
					</td>
					<td class="tdulright" style="width: 35%">
						${antenna.machineObliquity}
					</td>

				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						天线通道数据量：
					</td>
					<td class="tdulright" style="width: 35%">
						${antenna.chunncelDatasize}
					</td>
					<td class="tdulleft" style="width: 15%">
						是否美化天线：
					</td>
					<td class="tdulright" style="width: 35%">
						<c:if test="${antenna.isBeautify == 'y'}">是</c:if>
						<c:if test="${antenna.isBeautify != 'y'}">否</c:if>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						美化厂家：
					</td>
					<td class="tdulright" style="width: 35%">
						${antenna.beautifyVender}
					</td>
					<td class="tdulleft" style="width: 15%">
						美化方式：
					</td>
					<td class="tdulright" style="width: 35%">
						${antenna.beautifyMode}
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						固定资产编号：
					</td>
					<td class="tdulright" style="width: 35%">
						${antenna.assetCode}
					</td>
					<td class="tdulleft" style="width: 15%">
						备注：
					</td>
					<td class="tdulright" style="width: 35%">
						${antenna.remark}
					</td>
				</tr>
			</tbody>
			<tr class="trwhite">
				<td class="tdulright" style="width: 15%">
					设备信息
				</td>
				<td class="tdulright" colspan="3" style="width: 85%">
				</td>
			</tr>
			<tbody id="equipmentInfoTb" style="display: ;">
				<tr class="trwhite">
					<td colspan="4" class="tdulright"
						style="padding: 10px; text-align: center;">
						<table width="100%" id="row" border="1" align="center"
							cellpadding="3" cellspacing="0" class="tabout">
							<tr>
								<th id="thlist" style="text-align: center;">
									设备类型
								</th>
								<th id="thlist" style="text-align: center;">
									设备名称
								</th>
								<th id="thlist" style="text-align: center;">
									设备厂家
								</th>
								<th id="thlist" style="text-align: center;">
									设备型号
								</th>
								<th id="thlist" style="text-align: center;">
									设备配置
								</th>
								<th id="thlist" style="text-align: center;">
									操作
								</th>
							</tr>
							<c:set var="trStyle" value="trwhite"></c:set>
							<c:forEach var="equipment" items="${equipments}"
								varStatus="status">
								<c:if test="${status.index%2==1}">
									<c:set var="trStyle" value="trcolor"></c:set>
								</c:if>
								<tr style="line-height: 20px;">
									<td class="tdulright">
										${EQUIPMENTSTYPE[equipment.equTypeId]}
									</td>
									<td class="tdulright">
										${equipment.equName }
									</td>
									<td class="tdulright">
										${equipment.equProducter }
									</td>
									<td class="tdulright">
										${equipment.equModel }
									</td>
									<td class="tdulright">
										${equipment.equDeploy }
									</td>
									<td class="tdulright">
										<a
											href="javascript:showDetail('equipment','${equipment.id }');">查看详细信息</a>
									</td>
								</tr>
							</c:forEach>
						</table>
					</td>
				</tr>
			</tbody>
			<tr class="trwhite">
				<td class="tdulright" style="width: 15%">
					小区信息
				</td>
				<td class="tdulright" colspan="3" style="width: 85%">
				</td>
			</tr>
			<tbody id="cellInfoTb" style="display: ;">
				<tr class="trwhite">
					<td colspan="4" class="tdulright"
						style="padding: 10px; text-align: center;">
						<table width="100%" id="row" border="1" align="center"
							cellpadding="3" cellspacing="0" class="tabout">
							<tr>
								<th class="thlist" style="text-align: center;">
									小区名称
								</th>
								<th class="thlist" style="text-align: center;">
									网元编号
								</th>
								<th class="thlist" style="text-align: center;">
									网元名称
								</th>
								<th class="thlist" style="text-align: center;">
									小区广播信道
								</th>
								<th class="thlist" style="text-align: center;">
									等效全向辐射功率
								</th>
								<th class="thlist" style="text-align: center;">
									是否跳频
								</th>
								<th class="thlist" style="text-align: center;">
									操作
								</th>
							</tr>
							<c:set var="trStyle" value="odd"></c:set>
							<c:forEach var="cell" items="${cells}" varStatus="status">
								<c:if test="${status.index%2==1}">
									<c:set var="trStyle" value="trcolor"></c:set>
								</c:if>
								<tr style="line-height: 20px;" class="${trStyle }">
									<td class="tdulright">
										${cell.chineseName }
									</td>
									<td class="tdulright">
										${cell.netElementCode}
									</td>
									<td class="tdulright">
										${cell.netElementName }
									</td>
									<td class="tdulright">
										${cell.broadcastChannel }
									</td>
									<td class="tdulright">
										${cell.allRoundPower }
									</td>
									<td class="tdulright">
										<c:if test="${cell.isFrequencyHopping=='y'}">是</c:if>
										<c:if test="${cell.isFrequencyHopping!='y'}">否</c:if>
									</td>
									<td class="tdulright">
										<a href="javascript:showDetail('cell','${cell.id }');">查看详细信息</a>
									</td>
								</tr>
							</c:forEach>
						</table>
					</td>
				</tr>
			</tbody>
			<tr class="trwhite">
				<td class="tdulright" style="width: 15%">
					直放站信息
				</td>
				<td class="tdulright" colspan="3" style="width: 85%">
				</td>
			</tr>
			<tbody id="repeaterInfoTb" style="display: ;">
				<tr class="trwhite">
					<td colspan="4" class="tdulright"
						style="padding: 10px; text-align: center;">
						<table width="100%" id="row" border="1" align="center"
							cellpadding="3" cellspacing="0" class="trwhite">
							<tr>
								<th class="thlist" style="text-align: center;">
									直放站号
								</th>
								<th class="thlist" style="text-align: center;">
									直放站名称
								</th>
								<th class="thlist" style="text-align: center;">
									所属地市
								</th>
								<th class="thlist" style="text-align: center;">
									所属区县
								</th>
								<th class="thlist" style="text-align: center;">
									经度
								</th>
								<th class="thlist" style="text-align: center;">
									纬度
								</th>
								<th class="thlist" style="text-align: center;">
									海拔（米）
								</th>
								<th class="thlist" style="text-align: center;">
									操作
								</th>
							</tr>
							<c:set var="trStyle" value="odd"></c:set>
							<c:forEach var="repeater" items="${repeaters}" varStatus="status">
								<c:if test="${status.index%2==1}">
									<c:set var="trStyle" value="trcolor"></c:set>
								</c:if>
								<tr style="line-height: 20px;">
									<td class="tdulright">
										${repeater.repeaterType }
									</td>
									<td class="tdulright">
										${repeater.repeaterName}
									</td>
									<td class="tdulright">
										${repeater.city }
									</td>
									<td class="tdulright">
										<apptag:dynLabel objType="region" id="${repeater.district}"
											dicType=""></apptag:dynLabel>
									</td>
									<td class="tdulright">
										${repeater.longitude }
									</td>
									<td class="tdulright">
										${repeater.latitude }
									</td>
									<td class="tdulright">
										${repeater.elevation }
									</td>
									<td class="tdulright">
										<a href="javascript:showDetail('repeater','${repeater.id }');">查看详细信息</a>
									</td>
								</tr>
							</c:forEach>
						</table>

					</td>
				</tr>
			</tbody>
		</table>
		<table width="40%" border="0" style="margin-top: 6px" align="center"
			cellpadding="0" cellspacing="0">
			<tr align="center">
				<td>
					<c:if test="${goback!='false'}">
						<input type="button" class="button" onclick="history.back()"
							value="返回">
					</c:if>
				</td>
			</tr>
		</table>
	</s:form>
	<div id="simplegallery1" style="position: absolute;"></div>
</body>