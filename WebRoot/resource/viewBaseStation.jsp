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
			objName = "����";
		}
		if (objectType == "antenna") {
			actionUrl = "${ctx}/antennaAction_view.jspx?flag=view&&showClose=1";
			objName = "����";
		}
		if (objectType == "cell") {
			actionUrl = "${ctx}/cellAction_view.jspx?flag=view&&showClose=1";
			objName = "С��";
		}
		if (objectType == "equipment") {
			actionUrl = "${ctx}/equipmentAction_view.jspx?flag=view&&showClose=1";
			objName = "�豸";
		}
		if (objectType == "repeater") {
			actionUrl = "${ctx}/repeaterAction_view.jspx?flag=view&&showClose=1";
			objName = "ֱ��վ";
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
			title : "�鿴" + objName + "��ϸ��Ϣ"
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
	<template:titile value="�鿴��վ" />
	<br />
	<s:form action="stationAction_save" name="view" method="post">
		<table class="tabout" width="850" style="width: 850px;" border="1"
			align="center" cellpadding="3" cellspacing="0">
			<tr class="trwhite">
				<td class="tdulright" style="width: 15%">
					������Ϣ
				</td>
				<td class="tdulright" colspan="3" style="width: 85%">
				</td>
			</tr>
			<tbody id="baseInfoTb" style="display: ;">
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						վַ��ţ�
					</td>
					<td class="tdulright" colspan="3" style="width: 85%">
						${baseStation.stationCode}
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						��վ���ƣ�
					</td>
					<td class="tdulright" colspan="3" style="width: 85%">
						${baseStation.stationName}
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						�������У�
					</td>
					<td class="tdulright" colspan="3" style="width: 85%">
						${baseStation.city }
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						�������أ�
					</td>
					<td class="tdulright" colspan="3" style="width: 85%">
						<apptag:dynLabel objType="region" id="${baseStation.district}"
							dicType=""></apptag:dynLabel>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						��������
					</td>
					<td class="tdulright" colspan="3" style="width: 85%">
						${baseStation.town}
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						���������壺
					</td>
					<td class="tdulright" colspan="3" style="width: 85%">
						${baseStation.village }
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						��ϸ��ַ��
					</td>
					<td class="tdulright" colspan="3" style="width: 85%">
						${baseStation.address }
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						����ʱ�䣺
					</td>
					<td class="tdulright" colspan="3" style="width: 85%">
						${baseStation.anTime }
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						�������ͣ�
					</td>
					<td class="tdulright" colspan="3" style="width: 85%">
						<apptag:dynLabel objType="dic" id="${baseStation.mrType}"
							dicType="property_right"></apptag:dynLabel>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						���������
					</td>
					<td class="tdulright" colspan="3" style="width: 85%">
						${baseStation.roomArea}
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						���ݽṹ��
					</td>
					<td class="tdulright" colspan="3" style="width: 85%">
						<apptag:dynLabel objType="dic" id="${baseStation.roomStructure}"
							dicType="room_structure"></apptag:dynLabel>
					</td>
				</tr>
			</tbody>
			<tr class="trwhite">
				<td class="tdulright" style="width: 15%">
					������Ϣ
				</td>
				<td class="tdulright" colspan="3" style="width: 85%">
				</td>
			</tr>
			<tbody id="geoInfoTb" style="display: ;">
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						���ȣ�
					</td>
					<td class="tdulright" style="width: 35%">
						${baseStation.longitude}
					</td>
					<td class="tdulleft" style="width: 15%">
						γ�ȣ�
					</td>
					<td class="tdulright" style="width: 35%">
						${baseStation.latitude}
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						����(��)��
					</td>
					<td class="tdulright" style="width: 35%">
						${baseStation.elevation}
					</td>
					<td class="tdulleft" style="width: 15%">
						����������
					</td>
					<td class="tdulright" style="width: 35%">
						<apptag:dynLabel objType="dic" id="${baseStation.geoFeature}"
							dicType="geography"></apptag:dynLabel>
					</td>
				</tr>
			</tbody>
			<tr class="trwhite">
				<td class="tdulright" style="width: 15%">
					��ʶ��Ϣ
				</td>
				<td class="tdulright" colspan="3" style="width: 85%">
				</td>
			</tr>
			<tbody id="flagInfoTb" style="display: ;">
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						�߽��վ��ʶ��
					</td>
					<td class="tdulright" style="width: 35%">
						<c:if test="${baseStation.isBoundary=='y'}">��</c:if>
						<c:if test="${baseStation.isBoundary!='y'}">��</c:if>
					</td>
					<td class="tdulleft" style="width: 15%">
						VIP��վ��ʶ��
					</td>
					<td class="tdulright" style="width: 35%">
						<c:if test="${baseStation.isVip=='y'}">��</c:if>
						<c:if test="${baseStation.isVip !='y'}">��</c:if>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						����ڵ��ʶ��
					</td>
					<td class="tdulright" style="width: 35%">
						<c:if test="${baseStation.isTransitNode == 'y'}">��</c:if>
						<c:if test="${baseStation.isTransitNode != 'y'}">��</c:if>
					</td>
					<td class="tdulleft" style="width: 15%">
						�߼�վ��ʶ��
					</td>
					<td class="tdulright" style="width: 35%">
						<c:if test="${baseStation.isLimit == 'y'}">��</c:if>
						<c:if test="${baseStation.isLimit != 'y'}">��</c:if>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						���ڸ��Ǳ�ʶ��
					</td>
					<td class="tdulright" style="width: 35%">
						<c:if test="${baseStation.isIndoorCoverage == 'y'}">��</c:if>
						<c:if test="${baseStation.isIndoorCoverage != 'y'}">��</c:if>
					</td>
					<td class="tdulleft" style="width: 15%">
						��ͨ��ʶ��
					</td>
					<td class="tdulright" style="width: 35%">
						<c:if test="${baseStation.isEveryVillage == 'y'}">��</c:if>
						<c:if test="${baseStation.isEveryVillage != 'y'}">��</c:if>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						ʩ����վ��ʶ��
					</td>
					<td class="tdulright" style="width: 35%">
						<c:if test="${baseStation.isBenefactorBs == 'y'}">��</c:if>
						<c:if test="${baseStation.isBenefactorBs != 'y'}">��</c:if>
					</td>
					<td class="tdulleft" style="width: 15%">
						�¹һ�վ��ʶ��
					</td>
					<td class="tdulright" style="width: 35%">
						<c:if test="${baseStation.isDrive == 'y'}">��</c:if>
						<c:if test="${baseStation.isDrive != 'y'}">��</c:if>
					</td>
				</tr>
			</tbody>
			<tr class="trwhite">
				<td class="tdulright" style="width: 15%">
					վ�����
				</td>
				<td class="tdulright" colspan="3" style="width: 85%">
				</td>
			</tr>
			<tbody id="stationInfoTb" style="display: ;">
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						������ߣ�
					</td>
					<td class="tdulright" style="width: 35%">
						<c:if test="${baseStation.isSanitaryWare == 'y'}">��</c:if>
						<c:if test="${baseStation.isSanitaryWare != 'y'}">��</c:if>
					</td>
					<td class="tdulleft" style="width: 15%">
						�е����������
					</td>
					<td class="tdulright" style="width: 35%">
						${baseStation.mainsLeadin}
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						���������
					</td>
					<td class="tdulright" style="width: 35%">
						${baseStation.gateLock}
					</td>
					<td class="tdulleft" style="width: 15%">
						�������⣺
					</td>
					<td class="tdulright" style="width: 35%">
						${baseStation.leaveOverQuestion}
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						ҵ����ϵ�ˣ�
					</td>
					<td class="tdulright" style="width: 35%">
						${baseStation.owner}
					</td>
					<td class="tdulleft" style="width: 15%">
						ҵ����ϵ�绰��
					</td>
					<td class="tdulright" style="width: 35%">
						${baseStation.ownerTel}
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						ά����λ��
					</td>
					<td class="tdulright" style="width: 35%">
						${maintenances[baseStation.maintenanceId]}
					</td>
					<td class="tdulleft" style="width: 15%">
						ά���飺
					</td>
					<td class="tdulright" style="width: 35%">
						${baseStation.patrolGroupName}
					</td>
				</tr>
			</tbody>
			<tr class="trwhite">
				<td class="tdulright" style="width: 15%">
					������Ϣ
				</td>
				<td class="tdulright" colspan="3" style="width: 85%">
				</td>
			</tr>
			<tbody id="otherInfoTb" style="display: ;">
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						����BSC��
					</td>
					<td class="tdulright" style="width: 35%">
						${baseStation.attachBSC}
					</td>
					<td class="tdulleft" style="width: 15%">
						BCF������
					</td>
					<td class="tdulright" style="width: 35%">
						${baseStation.bcfNum}
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						Ƶ�Σ�
					</td>
					<td class="tdulright" style="width: 35%">
						${baseStation.frequencyChannel}
					</td>
					<td class="tdulleft" style="width: 15%">
						��վ���ͣ�
					</td>
					<td class="tdulright" style="width: 35%">
						<apptag:dynLabel objType="dic" id="${baseStation.bsType}"
							dicType="basestation_type"></apptag:dynLabel>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						��վ��ʶ��
					</td>
					<td class="tdulright" style="width: 35%">
						${baseStation.bsIdentifies}
					</td>
					<td class="tdulleft" style="width: 15%">
						��վ����
					</td>
					<td class="tdulright" style="width: 35%">
						<apptag:dynLabel objType="dic" id="${baseStation.bsLevel}"
							dicType="basestation_level"></apptag:dynLabel>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						��վ���ࣺ
					</td>
					<td class="tdulright" style="width: 35%">
						<apptag:dynLabel objType="dic" id="${baseStation.bsSort}"
							dicType="basestation_sort"></apptag:dynLabel>
					</td>
					<td class="tdulleft" style="width: 15%">
						��վ���ã�
					</td>
					<td class="tdulright" style="width: 35%">
						${baseStation.bsDeploy}
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						�������
					</td>
					<td class="tdulright" style="width: 35%">
						${baseStation.areaType}
					</td>
					<td class="tdulleft" style="width: 15%">
						�����������ͣ�
					</td>
					<td class="tdulright" style="width: 35%">
						<apptag:dynLabel objType="dic"
							id="${baseStation.coverageAreaType}" dicType="overlay_area_type"></apptag:dynLabel>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						��������
					</td>
					<td class="tdulright" colspan="3" style="width: 85%">
						<apptag:dynLabel objType="dic" id="${baseStation.coverageArea}"
							dicType="overlay_area"></apptag:dynLabel>
					</td>
				</tr>
			</tbody>
			<tr class="trwhite">
				<td class="tdulright" style="width: 15%">
					������Ϣ
				</td>
				<td class="tdulright" colspan="3" style="width: 85%">
				</td>
			</tr>
			<tbody id="outdoorFacilityInfoTb" style="display: ;">
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						�������ƣ�
					</td>
					<td class="tdulright" style="width: 35%">
						${outdoorFacilities.towerName}
					</td>
					<td class="tdulleft" style="width: 15%">
						������ţ�
					</td>
					<td class="tdulright" style="width: 35%">
						${outdoorFacilities.towerCode}
					</td>

				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						�豸״̬��
					</td>
					<td class="tdulright" style="width: 35%">
						<apptag:dynLabel objType="dic" id="${outdoorFacilities.state}"
							dicType="device_state"></apptag:dynLabel>
					</td>
					<td class="tdulleft" style="width: 15%">
						����ʱ�䣺
					</td>
					<td class="tdulright" style="width: 35%">
						${outdoorFacilities.anTime}
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						�������ͣ�
					</td>
					<td class="tdulright" style="width: 35%">
						<apptag:dynLabel objType="dic" id="${outdoorFacilities.towerType}"
							dicType="tower_type"></apptag:dynLabel>
					</td>
					<td class="tdulleft" style="width: 15%">
						�����߶ȣ�
					</td>
					<td class="tdulright" style="width: 35%">
						${outdoorFacilities.towerHeight}
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						����ƽ̨����
					</td>
					<td class="tdulright" style="width: 35%">
						${outdoorFacilities.towerPlatformNum}
					</td>
					<td class="tdulleft" style="width: 15%">
						����ƽ̨����
					</td>
					<td class="tdulright" style="width: 35%">
						${outdoorFacilities.usePlatformNum}
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						�ݶ�Φ�����ͣ�
					</td>
					<td class="tdulright" style="width: 35%">
						<apptag:dynLabel objType="dic" id="${outdoorFacilities.mastType}"
							dicType="mast_type"></apptag:dynLabel>
					</td>
					<td class="tdulleft" style="width: 15%">
						���˸߶ȣ�
					</td>
					<td class="tdulright" style="width: 35%">
						${outdoorFacilities.poleHeight}
					</td>

				</tr>

				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						ԭ���ƣ�
					</td>
					<td class="tdulright" style="width: 35%">
						${outdoorFacilities.oldName}
					</td>
					<td class="tdulleft" style="width: 15%">
						�������ң�
					</td>
					<td class="tdulright" style="width: 35%">
						${outdoorFacilities.producter}
					</td>

				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						����ͺţ�
					</td>
					<td class="tdulright" style="width: 35%">
						${outdoorFacilities.towerMode}
					</td>
					<td class="tdulleft" style="width: 15%">
						�������ʣ�
					</td>
					<td class="tdulright" style="width: 35%">
						<apptag:dynLabel objType="dic"
							id="${outdoorFacilities.towerMaterial}" dicType="tower_material"></apptag:dynLabel>
					</td>

				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						����������
					</td>
					<td class="tdulright" style="width: 35%">
						<apptag:dynLabel objType="dic" id="${outdoorFacilities.towerBase}"
							dicType="tower_base"></apptag:dynLabel>
					</td>

					<td class="tdulleft" style="width: 15%">
						��Ȩ���ʣ�
					</td>
					<td class="tdulright" style="width: 35%">
						<apptag:dynLabel objType="dic"
							id="${outdoorFacilities.propertyRight}" dicType="property_right"></apptag:dynLabel>
					</td>
				</tr>

				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						�Ƿ���������Ӫ�̹���
					</td>
					<td class="tdulright" style="width: 35%">
						<c:if test="${outdoorFacilities.isShare == 'y'}">��</c:if>
						<c:if test="${outdoorFacilities.isShare != 'y'}">��</c:if>
					</td>
					<td class="tdulleft" style="width: 15%">
						������Ӫ�̣�
					</td>
					<td class="tdulright" style="width: 35%">
						${outdoorFacilities.shareOperator}
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						�̶��ʲ���ţ�
					</td>
					<td class="tdulright" colspan="3" style="width: 85%">
						${outdoorFacilities.assetCode}
					</td>
				</tr>
			</tbody>
			<tr class="trwhite">
				<td class="tdulright" style="width: 15%">
					������Ϣ
				</td>
				<td class="tdulright" colspan="3" style="width: 85%">
				</td>
			</tr>
			<tbody id="antennaInfoTb" style="display: ;">
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						���߱�ţ�
					</td>
					<td class="tdulright" style="width: 35%">
						${antenna.antennaCode}
					</td>
					<td class="tdulleft" style="width: 15%">
						�������ƣ�
					</td>
					<td class="tdulright" style="width: 35%">
						${antenna.antennaName}
					</td>
				</tr>
				<tr class="trwhite">

					<td class="tdulleft" style="width: 15%">
						���ȣ�
					</td>
					<td class="tdulright" style="width: 35%">
						${antenna.longitude}
					</td>
					<td class="tdulleft" style="width: 15%">
						γ�ȣ�
					</td>
					<td class="tdulright" style="width: 35%">
						${antenna.latitude}
					</td>
				</tr>
				<tr class="trwhite">

					<td class="tdulleft" style="width: 15%">
						�����ͺţ�
					</td>
					<td class="tdulright" style="width: 35%">
						${antenna.antennaModel}
					</td>
					<td class="tdulleft" style="width: 15%">
						���߼������ͣ�
					</td>
					<td class="tdulright" style="width: 35%">
						<apptag:dynLabel objType="dic" id="${antenna.polarizationType}"
							dicType="antenna_polarization"></apptag:dynLabel>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						����������
					</td>
					<td class="tdulright" style="width: 35%">
						${antenna.antennaNumber}
					</td>
					<td class="tdulleft" style="width: 15%">
						�������ͣ�
					</td>
					<td class="tdulright" style="width: 35%">
						<apptag:dynLabel objType="dic" id="${antenna.antennaType}"
							dicType="antenna_type"></apptag:dynLabel>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						���߷�λ�ǣ�
					</td>
					<td class="tdulright" style="width: 35%">
						${antenna.azimuth}
					</td>
					<td class="tdulleft" style="width: 15%">
						���߸�����
					</td>
					<td class="tdulright" style="width: 35%">
						${antenna.pitchofangle}
					</td>
				</tr>

				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						�������棺
					</td>
					<td class="tdulright" style="width: 35%">
						${antenna.gain}
					</td>
					<td class="tdulleft" style="width: 15%">
						���߹Ҹߣ�
					</td>
					<td class="tdulright" style="width: 35%">
						${antenna.height}
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						���߳��̣�
					</td>
					<td class="tdulright" style="width: 35%">
						${antenna.producter}
					</td>
					<td class="tdulleft" style="width: 15%">
						Ͷ��ʹ�����ڣ�
					</td>
					<td class="tdulright" style="width: 35%">
						${antenna.createTime}
					</td>
				</tr>

				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						������ǣ�
					</td>
					<td class="tdulright" style="width: 35%">
						${antenna.electornObliquity}
					</td>
					<td class="tdulleft" style="width: 15%">
						��е��ǣ�
					</td>
					<td class="tdulright" style="width: 35%">
						${antenna.machineObliquity}
					</td>

				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						����ͨ����������
					</td>
					<td class="tdulright" style="width: 35%">
						${antenna.chunncelDatasize}
					</td>
					<td class="tdulleft" style="width: 15%">
						�Ƿ��������ߣ�
					</td>
					<td class="tdulright" style="width: 35%">
						<c:if test="${antenna.isBeautify == 'y'}">��</c:if>
						<c:if test="${antenna.isBeautify != 'y'}">��</c:if>
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						�������ң�
					</td>
					<td class="tdulright" style="width: 35%">
						${antenna.beautifyVender}
					</td>
					<td class="tdulleft" style="width: 15%">
						������ʽ��
					</td>
					<td class="tdulright" style="width: 35%">
						${antenna.beautifyMode}
					</td>
				</tr>
				<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						�̶��ʲ���ţ�
					</td>
					<td class="tdulright" style="width: 35%">
						${antenna.assetCode}
					</td>
					<td class="tdulleft" style="width: 15%">
						��ע��
					</td>
					<td class="tdulright" style="width: 35%">
						${antenna.remark}
					</td>
				</tr>
			</tbody>
			<tr class="trwhite">
				<td class="tdulright" style="width: 15%">
					�豸��Ϣ
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
									�豸����
								</th>
								<th id="thlist" style="text-align: center;">
									�豸����
								</th>
								<th id="thlist" style="text-align: center;">
									�豸����
								</th>
								<th id="thlist" style="text-align: center;">
									�豸�ͺ�
								</th>
								<th id="thlist" style="text-align: center;">
									�豸����
								</th>
								<th id="thlist" style="text-align: center;">
									����
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
											href="javascript:showDetail('equipment','${equipment.id }');">�鿴��ϸ��Ϣ</a>
									</td>
								</tr>
							</c:forEach>
						</table>
					</td>
				</tr>
			</tbody>
			<tr class="trwhite">
				<td class="tdulright" style="width: 15%">
					С����Ϣ
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
									С������
								</th>
								<th class="thlist" style="text-align: center;">
									��Ԫ���
								</th>
								<th class="thlist" style="text-align: center;">
									��Ԫ����
								</th>
								<th class="thlist" style="text-align: center;">
									С���㲥�ŵ�
								</th>
								<th class="thlist" style="text-align: center;">
									��Чȫ����书��
								</th>
								<th class="thlist" style="text-align: center;">
									�Ƿ���Ƶ
								</th>
								<th class="thlist" style="text-align: center;">
									����
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
										<c:if test="${cell.isFrequencyHopping=='y'}">��</c:if>
										<c:if test="${cell.isFrequencyHopping!='y'}">��</c:if>
									</td>
									<td class="tdulright">
										<a href="javascript:showDetail('cell','${cell.id }');">�鿴��ϸ��Ϣ</a>
									</td>
								</tr>
							</c:forEach>
						</table>
					</td>
				</tr>
			</tbody>
			<tr class="trwhite">
				<td class="tdulright" style="width: 15%">
					ֱ��վ��Ϣ
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
									ֱ��վ��
								</th>
								<th class="thlist" style="text-align: center;">
									ֱ��վ����
								</th>
								<th class="thlist" style="text-align: center;">
									��������
								</th>
								<th class="thlist" style="text-align: center;">
									��������
								</th>
								<th class="thlist" style="text-align: center;">
									����
								</th>
								<th class="thlist" style="text-align: center;">
									γ��
								</th>
								<th class="thlist" style="text-align: center;">
									���Σ��ף�
								</th>
								<th class="thlist" style="text-align: center;">
									����
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
										<a href="javascript:showDetail('repeater','${repeater.id }');">�鿴��ϸ��Ϣ</a>
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
							value="����">
					</c:if>
				</td>
			</tr>
		</table>
	</s:form>
	<div id="simplegallery1" style="position: absolute;"></div>
</body>