<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script src="${ctx}/js/extjs/ux/ux-all-debug.js" type="text/javascript"></script>
<script src="${ctx}/js/extjs/ux/Appcombox.js" type="text/javascript"></script>
<head>
	<script language="javascript" type="text/javascript">
	function getStation(district) {
		if(!district){
			district=jQuery("#oilEngine_district").val();
		}
		jQuery.ajax({
			type: 'POST',
			async: false,
			url: "${ctx}/oilEngineAction_getBaseStation.jspx?district="+district ,
			success: function (data, textStatus){
				setStationCode(data);
			},
			dataType: 'html'});
	}
	function setStationCode(responseText) {
		jQuery("#oilEngine_stationId option").length = 1;
		var str = responseText;
		if (str == "")
			return true;
		var optionlist = str.split(";");
		for ( var i = 0; i < optionlist.length - 1; i++) {
			var v = optionlist[i].split("=")[0];
			var t = optionlist[i].split("=")[1];
			jQuery("<option value='"+v+"'>"+t+"</option>").appendTo("#oilEngine_stationId");
		}
	}
	function getMessage() {
		jQuery.ajax({
			type: 'POST',
			async: false,
			url: "${ctx}/oilEngineAction_getStationMessage.jspx?id="+ jQuery("#oilEngine_stationId").val(),
			success: function (data, textStatus){
				setMessage(data);
			},
			dataType: 'html'});
	}
	function setMessage(response) {
		var str=response;
		if (str == "")
			return true;
		var optionlist = str.split(";");
		jQuery('#oilEngine_longitude').val(optionlist[0]);
		jQuery('#oilEngine_latitude').val(optionlist[1]);
		jQuery('#oilEngine_address').val(optionlist[2]);
		}
	function getitude(){
		var actionUrl="/WEBGIS/gisextend/igis.jsp?actiontype=101&userid=${LOGIN_USER.userID}&eid=itudes";
		window.open(actionUrl,'map','width=800,height=600,scrollbars=yes,resizable=yes,toolbar=no,menubar=no');
	}
	function check(){
		if($("#oilEngineType").val()==""){
			alert("��ѡ���ͻ����ͣ�");
			return false;
		}
		if($("#oilType").val()==""){
			alert("��ѡ���������ͣ�");
			return false;
		}
		if($("#oilEngine.oilEnginePhase").val()==""){
			alert("��ѡ���ͻ�������");
			return false;
		}
		if($("#district").val()==""){
			alert("��ѡ���������أ�");
			return false;
		}
		return true;
	}
	//��������
	var patrolregioncombotree;
	Ext.onReady(function() {
	      //jquery��֤
		jQuery("#editoilEngineForm").validate({	
			debug: true, 
			submitHandler: function(form){
				if(check()){
	        		form.submit();
	        	 }
	        }
		});
		Ext.BLANK_IMAGE_URL = "${ctx}/js/extjs/resources/images/default/s.gif";
		patrolregioncombotree = new TreeComboField({
			width : 220,
			height : 100,
			allowBlank : false,
			leafOnly : false,
			renderTo : 'combotree_patrolregiondiv',
			name : "regionname",
			hiddenName : "district",
			displayField : 'text',
			valueField : 'id',
			tree : new Ext.tree.TreePanel({
				autoScroll : true,
				rootVisible : false,
				autoHeight:true, 
				root : new Ext.tree.AsyncTreeNode({
					id : '000000',
					loader : new Ext.tree.TreeLoader({
						dataUrl : '${ctx}/common/externalresources_getRegionJson.jspx'
					})

				})
			})
		});
		patrolregioncombotree.on('select', function(combo, record) {
			var regionId=record.attributes.id;
			document.forms[0].elements["district_input"].value = regionId;
			getStation(regionId);
		});
		patrolregioncombotree.setComboValue("${oilEngine.district}","${oilEngine.districtName}");

		//�ͻ�����
		var  oilEngineType = new Appcombox({
		   	hiddenName : 'oilEngine.oilEngineType',
		   	hiddenId : 'oilEngineType',
		   	emptyText : '��ѡ��',
		   	dataUrl : '${ctx}/common/externalresources_getDictionaryJson.jspx?type=OIL_ENGINE_TYPE',
		   	dataCode : 'CODEVALUE',
		   	dataText : 'LABLE',
		   	allowBlank:false,
		   	renderTo: 'oilEngine_oilEngineType'
		})
		oilEngineType.setComboValue('${oilEngine.oilEngineType }','<apptag:dynLabel objType="dic" id="${oilEngine.oilEngineType }" dicType="OIL_ENGINE_TYPE"></apptag:dynLabel>');

		//��������
		var  oilType = new Appcombox({
		   	hiddenName : 'oilEngine.oilType',
			hiddenId : 'oilType',
		   	emptyText : '��ѡ��',
		   	dataUrl : '${ctx}/common/externalresources_getDictionaryJson.jspx?type=OIL_TYPE',
		   	dataCode : 'CODEVALUE',
		   	dataText : 'LABLE',
		   	allowBlank:false,
		   	renderTo: 'oilEngine_oilType'
		})
		oilType.setComboValue('${oilEngine.oilType }','<apptag:dynLabel objType="dic" id="${oilEngine.oilType }" dicType="OIL_TYPE"></apptag:dynLabel>');
		

	    //�ͻ�����
		var  oilEnginePhase = new Appcombox({
		   	hiddenName : 'oilEngine.oilEnginePhase',
			hiddenId : 'oilEnginePhase',
		   	emptyText : '��ѡ��',
		   	dataUrl : '${ctx}/common/externalresources_getDictionaryJson.jspx?type=OIL_ENGINE_PHASE',
		   	dataCode : 'CODEVALUE',
		   	dataText : 'LABLE',
		   	allowBlank:false,
		   	renderTo: 'oilEngine_oilEnginePhase'
		})
		oilEnginePhase.setComboValue('${oilEngine.oilEnginePhase }','<apptag:dynLabel objType="dic" id="${oilEngine.oilEnginePhase }" dicType="OIL_ENGINE_PHASE"></apptag:dynLabel>');

	});
</script>
</head>
<body>
	<template:titile value="�޸��ͻ���Ϣ" />

	<s:form action="oilEngineAction_update" name="editoilEngineForm" id="editoilEngineForm"
		method="post">

		<table width="850" border="0" align="center" cellpadding="3"
			cellspacing="0" class="tabout">
			<tr class="trwhite" id="town">
				<td class="tdulright" style="width: 15%">
					�ͻ���Ϣ
				</td>
				<td class="tdulright" colspan="3" style="width: 85%">
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					�ͻ���ţ�
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="oilEngine.oilEngineCode"
						value="${oilEngine.oilEngineCode }" class="inputtext required"
						style="width: 220px" maxlength="15" />
					<span style="color: red">*</span>
					<input type="hidden" name="district_input" value="${oilEngine.district }" />
				</td>
				<td class="tdulleft" style="width: 15%">
					�ͻ����̣�
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="oilEngine.producer"
						value="${oilEngine.producer}" class="inputtext"
						style="width: 220px" maxlength="40" />
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					�ͻ��ͺţ�
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="oilEngine.oilEngineModel"
						value="${oilEngine.oilEngineModel}" class="inputtext"
						style="width: 220px" maxlength="15" />
				</td>
				<td class="tdulleft" style="width: 15%">
					�ͻ����ͣ�
				</td>
				<td class="tdulright" style="width: 35%">
					<div id="oilEngine_oilEngineType"></div>
					<span style="color: red">*</span>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					�������ͣ�
				</td>
				<td class="tdulright" style="width: 35%">
				    <div id="oilEngine_oilType"></div>
					<span style="color: red">*</span>
					
				</td>
				<td class="tdulleft" style="width: 15%">
					�ͻ�������
				</td>
				<td class="tdulright" style="width: 35%">
					<div id="oilEngine_oilEnginePhase"></div>
					<span style="color: red">*</span>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					�������أ�
				</td>
				<td class="tdulright" style="width: 35%">
					<div id="combotree_patrolregiondiv" style="width: 180;"></div>
				</td>
				<td class="tdulleft" style="width: 15%">
					�ͻ�������
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="oilEngine.oilEngineWeight"
						value="${oilEngine.oilEngineWeight}"
						class="inputtext required validate-number" style="width: 220px"
						maxlength="40" />
					<span style="color: red">*</span>
				</td>
			</tr>
			<tr class="trwhite" id="STATION">
				<td class="tdulleft" style="width: 15%">
					���ڻ�վ��
				</td>
				<td class="tdulright" style="width: 35%" id="baseStation">
					<select name="oilEngine.stationId" id="oilEngine_stationId"
						onchange="getMessage()"  style="width: 220px">
					</select>
				</td>
				<td class="tdulleft" style="width: 15%">
					���ڵأ�
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="oilEngine.address" id="oilEngine_address"
						value="${oilEngine.address}" class="inputtext"
						style="width: 220px" maxlength="40" />
				</td>
			</tr>
			<tr class="trwhite" id="LONGITUDE">
				<td class="tdulleft" style="width: 15%">
					���ȣ�
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="oilEngine.longitude" id="oilEngine_longitude"
						value="${oilEngine.longitude}" class="inputtext number"
						style="width: 220px" maxlength="40" />
				</td>
				<td class="tdulleft" style="width: 15%">
					γ�� ��
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="oilEngine.latitude" id="oilEngine_latitude"
						value="${oilEngine.latitude}" class="inputtext number"
						style="width: 220px" maxlength="40" />
				</td>
			</tr>
			<tr class="trwhite" id="ADDR" style="display: none;">
				<td class="tdulleft" style="width: 15%">
					���ڵأ�
				</td>
				<td class="tdulright" style="width: 15%">
					<input type="text" name="addr" class="inputtext" value="${addr}"
						style="width: 220px" maxlength="40" />
				</td>
				<td class="tdulleft" style="width: 15%">
					��γ�ȣ�
				</td>
				<td class="tdulright" style="width: 15%">
					<input type="text" name="itudes" value="${itudes}"
						class="inputtext required isJW"
						style="width: 220px" maxlength="40" />
					<input type="button" value="ѡ��" style="display: none"  onclick="getitude()" />
					<span style="color: red">*������γ���Զ��Ÿ�����</span>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					����ʣ�
				</td>
				<td class="tdulright" colspan="3" style="width: 35%">
					<input type="text" name="oilEngine.rationPower"
						value="${oilEngine.rationPower}"
						class="inputtext required number" style="width: 220px"
						maxlength="40" />
					<span style="color: red">*</span>
				</td>
			</tr>
			<tr class="trwhite" id="town">
				<td class="tdulright" style="width: 15%">
					������Ϣ
				</td>
				<td class="tdulright" colspan="3" style="width: 85%">
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					�����ˣ�
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="oilEngine.principal"
						value="${oilEngine.principal}" class="inputtext required"
						style="width: 220px" />
					<span style="color: red">*</span>
				</td>
				<td class="tdulleft" style="width: 15%">
					��ϵ�绰��
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="oilEngine.phone"
						value="${oilEngine.phone}"
						class="inputtext required isTel" style="width: 220px"
						maxlength="40" />
					<span style="color: red">*</span>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					��ע��
				</td>
				<td class="tdulright" colspan="3" style="width: 35%">
					<textarea name="oilEngine.remark" class="inputtext"
						style="width: 220px" maxlength="40">${oilEngine.remark}</textarea>
				</td>
			</tr>
		</table>
		<table width="40%" border="0" style="margin-top: 6px" align="center"
			cellpadding="0" cellspacing="0">
			<tr align="center">
				<td>
					<input type="submit" class="button" value="�޸�">
				</td>
				<td>
					<input type="button" class="button" onclick="history.back()"
						value="����">
				</td>
			</tr>
		</table>
	</s:form>
	<script type="text/javascript">
	getStation('${oilEngine.district}');
	jQuery("#oilEngine_stationId option[value='${oilEngine.stationId}']").attr("selected", true);
	</script>
</body>
