<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script src="${ctx}/js/extjs/ux/ux-all-debug.js" type="text/javascript"></script>

<script src="${ctx}/js/extjs/ux/Appcombox.js" type="text/javascript"></script>
<head>
	<script language="javascript" type="text/javascript">
	Ext.onReady(function() {
				jQuery("#onestopQuickOutdoorFacilitiesForm").validate({	
					debug: true, 
					submitHandler: function(form){
						if(check()){
			        		form.submit();
			        	 }
			        }
				});
				//�豸״̬
				var device_state = new Appcombox(
						{
							hiddenName : 'outdoorFacilities.state',
							hiddenId :'outdoorFacilitiesstate',
							emptyText : '��ѡ��',
							dataUrl : '${ctx}/common/externalresources_getDictionaryJson.jspx?type=device_state',
							dataCode : 'CODEVALUE',
							dataText : 'LABLE',
							allowBlank : true,
							renderTo : 'outdoorFacilities_state'
						})
				device_state
						.setComboValue(
								'${outdoorFacilities.state}',
								'<apptag:dynLabel objType="dic" id="${outdoorFacilities.state}" dicType="device_state"></apptag:dynLabel>');

				//��������
				var tower_type = new Appcombox(
						{
							hiddenName : 'outdoorFacilities.towerType',
							hiddenId :'towerType',
							emptyText : '��ѡ��',
							dataUrl : '${ctx}/common/externalresources_getDictionaryJson.jspx?type=TOWERTYPE',
							dataCode : 'CODEVALUE',
							dataText : 'LABLE',
							allowBlank : false,
							renderTo : 'outdoorFacilities_towerType'
						})
				tower_type
						.setComboValue(
								'${outdoorFacilities.towerType}',
								'<apptag:dynLabel objType="dic" id="${outdoorFacilities.towerType}" dicType="TOWERTYPE"></apptag:dynLabel>');

				//��������
				var tower_material = new Appcombox(
						{
							hiddenName : 'outdoorFacilities.towerMaterial',
							hiddenId :'towerMaterial',
							emptyText : '��ѡ��',
							dataUrl : '${ctx}/common/externalresources_getDictionaryJson.jspx?type=tower_material',
							dataCode : 'CODEVALUE',
							dataText : 'LABLE',
							allowBlank : true,
							renderTo : 'outdoorFacilities_towerMaterial'
						})
				tower_material
						.setComboValue(
								'${outdoorFacilities.towerMaterial}',
								'<apptag:dynLabel objType="dic" id="${outdoorFacilities.towerMaterial}" dicType="tower_material"></apptag:dynLabel>');

				//��������
				var tower_base = new Appcombox(
						{
							hiddenName : 'outdoorFacilities.towerBase',
							hiddenId :'towerBase',
							emptyText : '��ѡ��',
							dataUrl : '${ctx}/common/externalresources_getDictionaryJson.jspx?type=tower_base',
							dataCode : 'CODEVALUE',
							dataText : 'LABLE',
							allowBlank : true,
							renderTo : 'outdoorFacilities_towerBase'
						})
				tower_base
						.setComboValue(
								'${outdoorFacilities.towerBase}',
								'<apptag:dynLabel objType="dic" id="${outdoorFacilities.towerBase}" dicType="tower_base"></apptag:dynLabel>');

				//�ݶ�Φ������
				var mast_type = new Appcombox(
						{
							hiddenName : 'outdoorFacilities.mastType',
							hiddenId :'mastType',
							emptyText : '��ѡ��',
							dataUrl : '${ctx}/common/externalresources_getDictionaryJson.jspx?type=mast_type',
							dataCode : 'CODEVALUE',
							dataText : 'LABLE',
							allowBlank : false,
							renderTo : 'outdoorFacilities_mastType'
						})
				mast_type
						.setComboValue(
								'${outdoorFacilities.mastType}',
								'<apptag:dynLabel objType="dic" id="${outdoorFacilities.mastType}" dicType="mast_type"></apptag:dynLabel>');

				//��Ȩ����
				var property_right = new Appcombox(
						{
							hiddenName : 'outdoorFacilities.propertyRight',
							hiddenId :'propertyRight',
							emptyText : '��ѡ��',
							dataUrl : '${ctx}/common/externalresources_getDictionaryJson.jspx?type=property_right',
							dataCode : 'CODEVALUE',
							dataText : 'LABLE',
							allowBlank : true,
							renderTo : 'outdoorFacilities_propertyRight'
						})
				property_right
						.setComboValue(
								'${outdoorFacilities.propertyRight}',
								'<apptag:dynLabel objType="dic" id="${outdoorFacilities.propertyRight}" dicType="property_right"></apptag:dynLabel>');
			});
	function displayCondition(value) {
		var town = document.getElementById("town");
		var town1 = document.getElementById("town1");
		var town2 = document.getElementById("town2");
		var town3 = document.getElementById("town3");
		var town4 = document.getElementById("town4");
		var town5 = document.getElementById("town5");
		var town6 = document.getElementById("town6");
		var mast = document.getElementById("mast");
		var mast1 = document.getElementById("mast1");
		if (value == "town") {
			town.style.display = "";
			town1.style.display = "";
			town2.style.display = "";
			town3.style.display = "";
			town4.style.display = "";
			town5.style.display = "";
			town6.style.display = "";
			mast.style.display = "none";
			mast1.style.display = "none";
			//$('outdoorFacilities.mastType').value="";
			//$('outdoorFacilities.poleHeight').value="";
			$('#outdoorFacilities_poleHeight').val("");
		} else {
			town.style.display = "none";
			town1.style.display = "none";
			town2.style.display = "none";
			town3.style.display = "none";
			town4.style.display = "none";
			town5.style.display = "none";
			town6.style.display = "none";
			mast.style.display = "";
			mast1.style.display = "";
			$('#outdoorFacilities_towerName').val("");
			$('#outdoorFacilities_towerCode').val("");
			//$('outdoorFacilities.state').value="";
			//$('outdoorFacilities.towerType').value="";
			if (device_state)
				device_state.setComboValue('', '');
			if (TOWERTYPE)
				TOWERTYPE.setComboValue('', '');
			$('#outdoorFacilities_towerHeight').val("");
			$('#outdoorFacilities_towerPlatformNum').val("");
			$('#outdoorFacilities_usePlatformNum').val("");
			$('#outdoorFacilities_oldName').val("");
			$('#outdoorFacilities_producter').val("");
			$('#outdoorFacilities_towerMode').val("");
			//$('outdoorFacilities.towerMaterial').value="";
			//$('outdoorFacilities.towerBase').value="";
			if (tower_material)
				tower_material.setComboValue('', '');
			if (tower_base)
				tower_base.setComboValue('', '');
		}
	}
	function getTown() {
		var towerCode = "${outdoorFacilities.towerCode}";
		if (towerCode == null || towerCode == "") {
			displayCondition("mast");
		} else {
			displayCondition("town");
		}
	}
	function check() {
		if('town'==jQuery(":radio[name='type']:checked").val()){
			if(jQuery("#outdoorFacilitiesstate").val()==''){
				alert('��ѡ���豸״̬');
				return false;
			}
			if(jQuery("#towerType").val()==''){
				alert('��ѡ����������');
				return false;
			}
			if(jQuery("#towerMaterial").val()==''){
				alert('��ѡ����������');
				return false;
			}
			if(jQuery("#towerBase").val()==''){
				alert('��ѡ����������');
				return false;
			}
		}else{
			if(jQuery("#mastType").val()==''){
				alert('��ѡ������Φ������');
				return false;
			}
		}
		if(jQuery("#propertyRight").val()==''){
				alert('��ѡ���Ȩ����');
				return false;
		}
		return true;
	}

	var rfids = 1;
	function addrfids() {
		rfids = rfids + 1;
		var _tr = '<tr class="trwhite" id="rfids_row_'+rfids+'">'
				+ '<td class="tdulleft" style="width:15%">'
				+ '	��Ƶ����ţ�'
				+ '</td>'
				+ '<td class="tdulright" style="width:35%">'
				+ '	<input id="rfids_'+rfids+'" type="text" name="rfids" onblur="validRfid(this,\'\')" class="inputtext required " style="width:220px" maxlength="40" /><span style="color:red">*</span>'
				+ '</td>'
				+ '<td class="tdulleft" style="width:15%">'
				+ '	λ��������'
				+ '</td>'
				+ '<td class="tdulright">'
				+ '	<input id="addresses_'+rfids+'" type="text" name="addresses" class="inputtext required" style="width:220px" maxlength="40" /><span style="color:red">*</span>'
				+ '	<a href="javascript:void(0);" onclick="delrfids(\'rfids_row_'
				+ rfids + '\')">ɾ��</a>' + '</td>' + '</tr>';
		jQuery("#_table").append(_tr);
	}

	function delrfids(id) {
		jQuery("tr[id=" + id + "]").remove();
	}

	//��֤��Ƶ����Ψһ  ����֤ǰ̨�����ظ�������֤��̨
	function validRfid(rfid, id) {
		if ('' == rfid.value) {
			return;
		}
		var flag = true;
		jQuery("#isubmit").removeAttr('disabled');
		jQuery("input:text[name='rfids']").each(function(i, e) {
			if (this != rfid) {
				if (jQuery(this).val() == rfid.value) {
					jQuery("#isubmit").attr('disabled', 'disabled');
					flag = false;
					alert("��Ƶ���Ų����ظ�");
				}
			}
		});

		if (flag) {
			jQuery.ajax({
				type : 'POST',
				async : false,
				url : 'res/indoorOverRideAction_valid.jspx?rfid=' + rfid.value
						+ "&id=" + id,
				success : function(data, textStatus) {
					if ('true' == data) {
						jQuery("#isubmit").attr('disabled', 'disabled');
						alert("��Ƶ�����Ѵ���");
					}
				},
				dataType : 'html'
			});
		}
	}
</script>
</head>
<body onload="getTown()">
	<template:titile value="������Ϣ" />
	<s:form action="oneStopQuick_saveTower" name="onestopQuickOutdoorFacilitiesForm"
		method="post" id="onestopQuickOutdoorFacilitiesForm">
		<table width="850" border="0" id="_table" align="center"
			cellpadding="3" cellspacing="0" class="tabout">
			<tr class="trwhite">
				<td class="tdulright" style="width: 15%">
					������վ��
				</td>
				<td class="tdulright" colspan="3" style="width: 85%">
					<input type="text" name="antenna.parentId"
						value="${BASESTATIONS[sessionScope.baseStationId]}"
						class="inputtext" style="width: 180" maxlength="40"
						readonly="readonly" />
					<!-- 
					<input type="text" id="stationName" onchange="getStation()" value="�������ѯ���ݣ��ڵ�������б�"  onmouseover="this.select()" class="inputtext" style="width: 220px" maxlength="40" />
					<br>
					<select name="outdoorFacilities.parentId" id="outdoorFacilities.parentId" class="inputtext" style="width: 220px">
					<option value="">��ѡ��</option>
					</select>
					<span style="color:red">*</span>
					 -->
					<input name="patrolGroupId" id="patrolGroupId" value="${baseStation.patrolGroupId}"
						type="hidden" />
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulright" style="width: 15%">
					ѡ��������ͣ�
				</td>
				<td class="tdulright" colspan="3" style="width: 85%">
					<input type="radio" name="type" value="town"
						onclick="displayCondition(this.value);">
					����
					</input>
					<input type="radio" name="type" value="mast"
						onclick="displayCondition(this.value);">
					Φ��
					</input>
				</td>
			</tr>
			<tr class="trwhite" id="town">
				<td class="tdulright" style="width: 15%">
					������Ϣ
				</td>
				<td class="tdulright" colspan="3" style="width: 85%">
				</td>
			</tr>
			<tr class="trwhite" id="town1">
				<td class="tdulleft" style="width: 15%">
					�������ƣ�
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" id="outdoorFacilities_towerName"
						name="outdoorFacilities.towerName"
						value="${outdoorFacilities.towerName}" class="inputtext required"
						style="width: 220" maxlength="40" />
					<span style="color: red">*</span>
				</td>
				<td class="tdulleft" style="width: 15%">
					������ţ�
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" id="outdoorFacilities_towerCode"
						name="outdoorFacilities.towerCode"
						value="${outdoorFacilities.towerCode}" class="inputtext required"
						style="width: 220" maxlength="40" />
					<span style="color: red">*</span>
				</td>
			</tr>
			<tr class="trwhite" id="town2">
				<td class="tdulleft" style="width: 15%">
					�豸״̬��
				</td>
				<td class="tdulright" style="width: 35%">
					<div id="outdoorFacilities_state"></div>
				</td>
				<td class="tdulleft" style="width: 15%">
					�������ͣ�
				</td>
				<td class="tdulright" style="width: 35%">
					<div id="outdoorFacilities_towerType"></div>
					<span style="color: red">*</span>
				</td>
			</tr>
			<tr class="trwhite" id="town3">
				<td class="tdulleft" style="width: 15%">
					�����߶ȣ�
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" id="outdoorFacilities_towerHeight"
						name="outdoorFacilities.towerHeight"
						value="${outdoorFacilities.towerHeight}" class="number "
						style="width: 220" maxlength="40" />
					<span style="color: red">*</span>
				</td>
				<td class="tdulleft" style="width: 15%">
					����ƽ̨����
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" id="outdoorFacilities_towerPlatformNum"
						name="outdoorFacilities.towerPlatformNum"
						value="${outdoorFacilities.towerPlatformNum}"
						class="number " style="width: 220"
						maxlength="40" />
					<span style="color: red">*</span>
				</td>
			</tr>
			<tr class="trwhite" id="town4">
				<td class="tdulleft" style="width: 15%">
					����ƽ̨����
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" id="outdoorFacilities_usePlatformNum"
						name="outdoorFacilities.usePlatformNum"
						value="${outdoorFacilities.usePlatformNum}"
						class="number" style="width: 220"
						maxlength="40" />
					<span style="color: red">*</span>
				</td>
				<td class="tdulleft" style="width: 15%">
					ԭ���ƣ�
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" id="outdoorFacilities_oldName"
						name="outdoorFacilities.oldName"
						value="${outdoorFacilities.oldName}" class="inputtext required"
						style="width: 220" maxlength="40" />
					<span style="color: red">*</span>
				</td>
			</tr>
			<tr class="trwhite" id="town5">
				<td class="tdulleft" style="width: 15%">
					�������ң�
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="outdoorFacilities.producter"
						value="${outdoorFacilities.producter}" class="inputtext"
						style="width: 220" maxlength="40" />
				</td>
				<td class="tdulleft" style="width: 15%">
					����ͺţ�
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="outdoorFacilities.towerMode"
						value="${outdoorFacilities.towerMode}" class="inputtext"
						style="width: 220" maxlength="40" />
				</td>
			</tr>
			<tr class="trwhite" id="town6">
				<td class="tdulleft" style="width: 15%">
					�������ʣ�
				</td>
				<td class="tdulright" style="width: 35%">
					<div id="outdoorFacilities_towerMaterial"></div>
				</td>
				<td class="tdulleft" style="width: 15%">
					����������
				</td>
				<td class="tdulright" style="width: 35%">
					<div id="outdoorFacilities_towerBase"></div>
				</td>
			</tr>
			<tr class="trwhite" id="mast" style="display: none">
				<td class="tdulright" style="width: 15%">
					Φ����Ϣ
				</td>
				<td class="tdulright" colspan="3" style="width: 85%">
				</td>
			</tr>
			<tr class="trwhite" id="mast1">
				<td class="tdulleft" style="width: 15%">
					�ݶ�Φ�����ͣ�
				</td>
				<td class="tdulright" style="width: 35%">
					<div id="outdoorFacilities_mastType"></div>
					<span style="color: red">*</span>
				</td>
				<td class="tdulleft" style="width: 15%">
					���˸߶ȣ�
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="outdoorFacilities.poleHeight"
						value="${outdoorFacilities.poleHeight}" class="number "
						style="width: 220" maxlength="40" />
					<span style="color: red">*</span>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulright" style="width: 15%">
					������Ϣ
				</td>
				<td class="tdulright" colspan="3" style="width: 85%"></td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					����ʱ�䣺
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="outdoorFacilities.anTime"
						value="<bean:write name="outdoorFacilities" property="anTime" format="yyyy-MM-dd" />"
						class="Wdate inputtext " style="width: 220"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
					<span style="color: red">*</span>
				</td>
				<td class="tdulleft" style="width: 15%">
					��Ȩ���ʣ�
				</td>
				<td class="tdulright" style="width: 35%">
					<div id="outdoorFacilities_propertyRight"></div>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					�Ƿ���������Ӫ�̹���
				</td>
				<td class="tdulright" style="width: 35%">
					<s:select list="#{'n':'��','y':'��'}"
						name="outdoorFacilities.isShare"
						value="%{#request.outdoorFacilities.isShare}" listKey="key"
						listValue="value" cssClass="inputtext" cssStyle="width:220px"></s:select>
				</td>
				<td class="tdulleft" style="width: 15%">
					������Ӫ�̣�
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="outdoorFacilities.shareOperator"
						value="${outdoorFacilities.shareOperator}" class="inputtext"
						style="width: 220" maxlength="40" />
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					�̶��ʲ���ţ�
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="outdoorFacilities.assetCode"
						value="${outdoorFacilities.assetCode}" class="inputtext"
						style="width: 220" maxlength="40" />
				</td>
				<td class="tdulleft" style="width: 15%">
					�����룺
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="outdoorFacilities.barCode"
						value="${outdoorFacilities.barCode}" class="inputtext"
						style="width: 220" maxlength="40" />
				</td>
			</tr>

			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					��γ�ȣ�
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" id="position" name="position"
						value="${position}"
						class="inputtext required isJW"
						style="width: 220" maxlength="40" />
					<input type="button" value="ѡ��" style="display: none" onclick="getitude()" />
					<span style="color: red">*������γ���Զ��Ÿ�����</span>
				</td>
				<td class="tdulleft" style="width: 15%">
				</td>
				<td class="tdulright" style="width: 35%">
				</td>
			</tr>


			<tr class="trwhite">
				<td colspan="4" class="tdulleft" style="width: 15%">
					<a href="javascript:void(0);" onclick="addrfids()">����Ѳ��λ����Ϣ</a>
					<input type="hidden" name="rfids" value="" />
					<input type="hidden" name="addresses" value="" />
				</td>
			</tr>

			<c:if test="${outdoorFacilities.id==null}">
				<tr class="trwhite" id="rfid_row_${item.rfid}">
					<td class="tdulleft" style="width: 15%">
						��Ƶ����ţ�
					</td>
					<td class="tdulright" style="width: 35%">
						<input id="rfids_0" type="text" name="rfids" onblur="validRfid(this,'')"
							class="inputtext required" style="width: 220px" maxlength="30" />
						<span style="color: red">*</span>
					</td>
					<td class="tdulleft" style="width: 15%">
						λ��������
					</td>
					<td class="tdulright">
						<input id="addresses_0" type="text" name="addresses" class="inputtext required"
							style="width: 220px" maxlength="100" />
						<span style="color: red">*</span>
					</td>
				</tr>
			</c:if>
			<c:forEach var="item" items="${patrolAddressInfosTower}"
				varStatus="stauts">
				<tr class="trwhite" id="rfid_row_${stauts.index}">
					<td class="tdulleft" style="width: 15%">
						��Ƶ����ţ�
					</td>
					<td class="tdulright" style="width: 35%">
						<input type="text" name="rfids"
							onblur="validRfid(this,'${item.id }')" value="${item.rfid}"
							class="inputtext required " style="width: 220px" maxlength="40" />
						<span style="color: red">*</span>
					</td>
					<td class="tdulleft" style="width: 15%">
						λ��������
					</td>
					<td class="tdulright">
						<input type="text" name="addresses" value="${item.address}"
							class="inputtext required " style="width: 220px" maxlength="40" />
						<span style="color: red">*</span>
						<c:if test="${stauts.index > 0}">
							<a href="javascript:void(0);"
								onclick="delrfids('rfid_row_${stauts.index}')">ɾ��</a>
						</c:if>
					</td>
				</tr>
			</c:forEach>
		</table>

		<table width="40%" border="0" style="margin-top: 6px" align="center"
			cellpadding="0" cellspacing="0">
			<tr align="center">
				<td>
					<input type="submit" class="button" value="����">
				</td>
			</tr>
		</table>
		<div align="center" style="margin-top: 20px; color: red;">
			��ɫ*Ϊ������
		</div>
	</s:form>
</body>
