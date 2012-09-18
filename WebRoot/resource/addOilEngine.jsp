<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script src="${ctx}/js/extjs/ux/ux-all-debug.js" type="text/javascript"></script>
<script src="${ctx}/js/extjs/ux/Appcombox.js" type="text/javascript"></script>
<script language="javascript" type="text/javascript">
//��������
var patrolregioncombotree;
var  oilEngineType;
Ext.onReady(function() {
	jQuery("#addOilEngineForm").validate({	
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
		maxHeight : 100,
		allowBlank : false,
		leafOnly : false,
		renderTo : 'combotree_patrolregiondiv',
		name : "regionname",
		hiddenId : "district",
		hiddenName : "district",
		displayField : 'text',
		allowBlank:false,
		valueField : 'id',
		tree : new Ext.tree.TreePanel({
			autoScroll : true,
			rootVisible : false,
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
		var urls = "${ctx}/oilEngineAction_getBaseStation.jspx?district="+ regionId;
		//var myAjax = new Ajax.Request(url, {
		//	method : 'get',
		//	asynchronous : 'false',
		//	onComplete : setStationCode
		//});
		jQuery.ajax({
			  type: 'POST',
			  async: false,
			  url: urls,
			  success: function (data, textStatus){
				  setStationCode(data);
						},
			  dataType: 'html'
			  });
	});
	
	//�ͻ�����
	oilEngineType = new Appcombox({
		hiddenId : 'oilEngine_oilEngineType',
	   	hiddenName : 'oilEngine.oilEngineType',
	   	emptyText : '��ѡ��',
	   	dataUrl : '${ctx}/common/externalresources_getDictionaryJson.jspx?type=OIL_ENGINE_TYPE',
	   	dataCode : 'CODEVALUE',
	   	dataText : 'LABLE',
	   	allowBlank:false,
	   	renderTo: 'oilEngine_oilEngineType_div'
	})

	//��������
	var  oilType = new Appcombox({
	   	hiddenId : 'oilEngine_oilType',
	   	hiddenName : 'oilEngine.oilType',
	   	emptyText : '��ѡ��',
	   	dataUrl : '${ctx}/common/externalresources_getDictionaryJson.jspx?type=OIL_TYPE',
	   	dataCode : 'CODEVALUE',
	   	dataText : 'LABLE',
	   	allowBlank:false,
	   	renderTo: 'oilEngine_oilType_div'
	})

    //�ͻ�����
	var  oilEnginePhase = new Appcombox({
		hiddenId : 'oilEngine_oilEnginePhase',
	   	hiddenName : 'oilEngine.oilEnginePhase',
	   	emptyText : '��ѡ��',
	   	dataUrl : '${ctx}/common/externalresources_getDictionaryJson.jspx?type=OIL_ENGINE_PHASE',
	   	dataCode : 'CODEVALUE',
	   	dataText : 'LABLE',
	   	allowBlank:false,
	   	renderTo: 'oilEngine_oilEnginePhase_div'
	})
	
});
</script>
<head>
<script language="javascript" type="text/javascript">
	function getStation() {
		//var url = "${ctx}/oilEngineAction_getBaseStation.jspx?district="+ $('oilEngine.district').value;
		//var myAjax = new Ajax.Request(url, {
		//	method : 'get',
		//	asynchronous : 'false',
		//	onComplete : setStationCode
		//});
		jQuery.ajax({
			  type: 'POST',
			  async: false,
			  url: "${ctx}/oilEngineAction_getBaseStation.jspx?district="+ jQuery("#oilEngine.district").val(),
			  success: function (data, textStatus){
				  setStationCode(data);
						},
			  dataType: 'html'
			  });
	}
	function setStationCode(responseText) {
		//$("oilEngine.stationId").options.length = 1;
		//var str = response.responseText;
		//if (str == "")
		//	return true;
		//var optionlist = str.split(";");
		//for ( var i = 0; i < optionlist.length - 1; i++) {
		//	var t = optionlist[i].split("=")[0];
		//	var v = optionlist[i].split("=")[1];
		//	$("oilEngine.stationId").options[i + 1] = new Option(v, t);
		//}
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
		//var url = "${ctx}/oilEngineAction_getStationMessage.jspx?id="+ $('oilEngine.stationId').value;
		//var myAjax = new Ajax.Request(url, {
		//	method : 'get',
		//	asynchronous : 'false',
		//	onComplete : setMessage
		//});
		jQuery.ajax({
			  type: 'POST',
			  async: false,
			  url: "${ctx}/oilEngineAction_getStationMessage.jspx?id="+ jQuery("#oilEngine_stationId").val(),
			  success: function (data, textStatus){
				  setMessage(data);
						},
			  dataType: 'html'
			  });
	}
	function setMessage(response) {
		//var str = response.responseText;
		var str=response;
		if (str == "")
			return true;
		var optionlist = str.split(";");
		jQuery('#oilEngine_longitude').val(optionlist[0]);
		jQuery('#oilEngine_latitude').val(optionlist[1]);
		jQuery('#oilEngine_address').val(optionlist[2]);
		}
	//У��
	function check(){
		if(jQuery('#oilEngine_oilEngineType').val()==""){
			alert("��ѡ���ͻ����ͣ�");
			return false;
		}
		if(jQuery('#oilEngine_oilType').val()==""){
			alert("��ѡ���������ͣ�");
			return false;
		}
		if(jQuery('#oilEngine_oilEnginePhase').val()==""){
			alert("��ѡ���ͻ�������");
			return false;
		}
		if(jQuery('#district').val()==""){
			alert("��ѡ���������أ�");
			return false;
		}
		//if($('oilEngine.stationId').value==""||$('oilEngine.stationId').value=="null"){
		//	alert("��ѡ�����ڻ�վ��");
		//	return false;
		//}
		return true;
	}
	function getitude(){
		var actionUrl="/WEBGIS/gisextend/igis.jsp?actiontype=101&userid=${LOGIN_USER.userID}&eid=itudes";
		window.open(actionUrl,'map','width=800,height=600,scrollbars=yes,resizable=yes,toolbar=no,menubar=no');
	}
</script>
</head>
<body>
	<template:titile value="����ͻ���Ϣ" />

	<s:form action="oilEngineAction_save" id="addOilEngineForm" name="oilEngineForm" method="post" >

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
						class="inputtext required" style="width: 220px" maxlength="15" />
					<span style="color: red">*</span>
					<input type="hidden" name="district_input" value="" />
				</td>
				<td class="tdulleft" style="width: 15%">
					�ͻ����̣�
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="oilEngine.producer" class="inputtext"
						style="width: 220px" maxlength="40" />
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					�ͻ��ͺţ�
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="oilEngine.oilEngineModel"
						class="inputtext" style="width: 220px" maxlength="15" />
				</td>
				<td class="tdulleft" style="width: 15%">
					�ͻ����ͣ�
				</td>
				<td class="tdulright" style="width: 35%">
					<div id="oilEngine_oilEngineType_div"></div>
					<span style="color: red">*</span>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					�������ͣ�
				</td>
				<td class="tdulright" style="width: 35%">
					<div id="oilEngine_oilType_div"></div>
					<span style="color: red">*</span>
				</td>
				<td class="tdulleft" style="width: 15%">
					�ͻ�������
				</td>
				<td class="tdulright" style="width: 35%">
					<div id="oilEngine_oilEnginePhase_div"></div>
					<span style="color: red">*</span>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					�������أ�
				</td>
				<td class="tdulright" style="width: 35%">
					<div id="combotree_patrolregiondiv" style="width: 180;"></div>
					<span style="color: red">*</span>
				</td>
				<td class="tdulleft" style="width: 15%">
					�ͻ�������
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="oilEngine.oilEngineWeight"
						class="inputtext required number" style="width: 220px"
						maxlength="40" />
					(��λ��KG)
					<span style="color: red">*</span>
				</td>
			</tr>
			<tr class="trwhite" id="STATION">
				<td class="tdulleft" style="width: 15%">
					���ڻ�վ��
				</td>
				<td class="tdulright" style="width: 35%" id="baseStation">
					<select name="oilEngine.stationId" id="oilEngine_stationId"
						onchange="getMessage()" class="inputtext required" style="width: 220px">
						<option value="">
							��ѡ��
						</option>
					</select>
					<span style="color: red">*</span>
				</td>

				<td class="tdulleft" style="width: 15%">
					���ڵأ�
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="oilEngine.address" id="oilEngine_address" class="inputtext"
						style="width: 220px" maxlength="40" />
				</td>
			</tr>
			<tr class="trwhite" id="LONGITUDE">
				<td class="tdulleft" style="width: 15%">
					���ȣ�
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="oilEngine.longitude" id="oilEngine_longitude"
						class="inputtext number" style="width: 220px"
						maxlength="40" />
				</td>
				<td class="tdulleft" style="width: 15%">
					γ�ȣ�
				</td>
				<td class="tdulright" style="width: 35%" id="baseStation">
					<input type="text" name="oilEngine.latitude" id="oilEngine_latitude"
						class="inputtext number" style="width: 220px"
						maxlength="40" />
				</td>
			</tr>
			<tr class="trwhite" id="ADDR" style="display: none;">
				<td class="tdulleft" style="width: 15%">
					���ڵأ�
				</td>
				<td class="tdulright" style="width: 15%">
					<input type="text" name="addr" class="inputtext"
						style="width: 220px" maxlength="40" />
				</td>
				<td class="tdulleft" style="width: 15%">
					��γ�ȣ�
				</td>
				<td class="tdulright" style="width: 15%">
					<input type="text" name="itudes"
						class="inputtext required isJW"
						style="width: 220px" maxlength="40" />
					<input type="button" value="ѡ��" style="display: none" onclick="getitude()" />
					<span style="color: red">*������γ���Զ��Ÿ�����</span>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					����ʣ�
				</td>
				<td class="tdulright" colspan="3" style="width: 35%">
					<input type="text" name="oilEngine.rationPower"
						class="inputtext required number" style="width: 220px"
						maxlength="40" />
					(��λ��KW)
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
						class="inputtext required" style="width: 220px" />
					<span style="color: red">*</span>
				</td>
				<td class="tdulleft" style="width: 15%">
					��ϵ�绰��
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="oilEngine.phone"
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
					<textarea name="oilEngine.remark" class="inputtext "
						style="width: 220px" maxlength="40"></textarea>
				</td>
			</tr>
		</table>
		<table width="40%" border="0" style="margin-top: 6px" align="center"
			cellpadding="0" cellspacing="0">
			<tr align="center">
				<td>
					<input type="submit" class="button" value="���">
				</td>
				<td>
					<input type="button" class="button" onclick="history.back()"
						value="����">
				</td>
			</tr>
		</table>
	</s:form>
</body>
