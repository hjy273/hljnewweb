<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<script src="${ctx}/js/extjs/ux/Appcombox.js" type="text/javascript"></script>
<head>
<script language="javascript" type="text/javascript">
	function getStation() {
		jQuery.ajax({
			  type: 'POST',
			  async: false,
			  url: "${ctx}/baseStationAction_getStation.jspx?district=&&id="+ jQuery("#stationName").val(),
			  success: function (data, textStatus){
				  setStationCode(data);
						},
			  dataType: 'html'
			  });
	}
	function getitude() {
		var actionUrl = "/WEBGIS/gisextend/igis.jsp?actiontype=101&userid=${LOGIN_USER.userID}&eid=position";
		window
				.open(actionUrl, 'map',
						'width=400,height=300,scrollbars=yes,z-look=yes,alwaysRaised=yes');
	}
	
	function setStationCode(responseText) {
		jQuery("#antenna_parentId option").length = 1;
		var str = responseText;
		if (str == "")
			return true;
		var optionlist = str.split(";");
		for ( var i = 0; i < optionlist.length - 1; i++) {
			var v = optionlist[i].split("=")[0];
			var t = optionlist[i].split("=")[1];
			jQuery("<option value='"+v+"'>"+t+"</option>").appendTo("#antenna_parentId");
		}
	}
	
	function getLatitudeAndLongitude(){
		jQuery.ajax({
			  type: 'POST',
			  async: false,
			  url: "${ctx}/antennaAction_getLatitudeAndLongitude.jspx?parentId="+jQuery("#antenna_parentId").val(),
			  success: function (data, textStatus){
				  settude(data);
						},
			  dataType: 'html'
			  });
	}
	function settude(responseText){
		jQuery("#position").val(responseText);
	}
	//function String.prototype.trim(){return this.replace(/(^\s*)/g, "").replace(/(\s*$)/g, "");}
	function validateName(){
			//if($('antenna.antennaName').value==''||$('antenna.antennaName').value.trim()==''||$('antenna.antennaName').value=='null'){
			//msg.innerHTML="�������Ʋ���Ϊ�ջ�null";
			//return;
			//}
			}
	function validateCode(){
			//if($('antenna.antennaCode').value==''||$('antenna.antennaCode').value.trim()==''||$('antenna.antennaCode').value=='null'){
			//msg1.innerHTML="���߱�Ų���Ϊ�ջ�null";
			//return;
			//}
	}
Ext.onReady(function() {
	jQuery("#addAntennaForm").validate();
	//inputtext validate-number -> number
	//��������	antenna.antennaType			antenna_type			antenna_type
	var  antenna_type = new Appcombox({
		hiddenName : 'antenna.antennaType',
		emptyText : '��ѡ��',
		dataUrl : '${ctx}/common/externalresources_getDictionaryJson.jspx?type=antenna_type',
	   	dataCode : 'CODEVALUE',
	   	dataText : 'LABLE',
		allowBlank:true,
		renderTo: 'antenna_type'
	});
	//���߼�������	antenna.polarizationType	antenna_polarization	antenna_polarization
	var  antenna_polarization = new Appcombox({
		hiddenName : 'antenna.polarizationType',
		emptyText : '��ѡ��',
		dataUrl : '${ctx}/common/externalresources_getDictionaryJson.jspx?type=antenna_polarization',
	   	dataCode : 'CODEVALUE',
	   	dataText : 'LABLE',
		allowBlank:true,
		renderTo: 'antenna_polarization'
	});
});
</script>
</head>
<body>
	<template:titile value="��ӻ�վ������Ϣ"/>

	<s:form action="antennaAction_save" name="addAntennaForm" method="post" id="addAntennaForm">
		<table width="850" border="0" align="center" cellpadding="3"
			cellspacing="0" class="tabout">
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					������վ��
				</td>
				<td class="tdulright" colspan="3" style="width: 85%">
					<input type="text" id="stationName" onchange="getStation()"
						value="�������ѯ���ݣ��ڵ�������б�" onmouseover="this.select()"
						class="inputtext" style="width: 220px" maxlength="40" />
					<br />
					<select name="antenna.parentId"
						onchange="getLatitudeAndLongitude()" id="antenna_parentId"
						class="inputtext required" style="width: 220px">
						<option value="">
							��ѡ��
						</option>
					</select>
					<span style="color: red">*</span>
				</td>
			</tr>
			<tr class="trwhite">

				<td class="tdulleft" style="width: 15%">
					���߱�ţ�
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="antenna.antennaCode"
						class="inputtext required" style="width: 220px" maxlength="40" />
					<span style="color: red">*</span>
				</td>
				<td class="tdulleft" style="width: 15%">
					�������ƣ�
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="antenna.antennaName"
						class="inputtext required " style="width: 220px" maxlength="40" />
					<span style="color: red">*</span>
				</td>
			</tr>
			<tr class="trwhite">

				<td class="tdulleft" style="width: 15%">
					��γ�ȣ�
				</td>
				<td class="tdulright" colspan="3" style="width: 85%">
					<input type="text" id="position" name="position"
						class="inputtext required isJW"
						style="width: 220px" maxlength="40" />
					<input type="button" value="ѡ��" onclick="getitude()" />
					<span style="color: red">*������γ���Զ��Ÿ�����</span>
				</td>
			</tr>
			<tr class="trwhite">

				<td class="tdulleft" style="width: 15%">
					�����ͺţ�
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="antenna.antennaModel" class="inputtext"
						style="width: 220px" maxlength="40" />
				</td>
				<td class="tdulleft" style="width: 15%">
					�������ͣ�
				</td>
				<td class="tdulright" style="width: 35%">
					<div id="antenna_type"></div>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					����������
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="antenna.antennaNumber"
						class="inputtext validate-integer required" style="width: 220px"
						maxlength="40" />
					<span style="color: red">*</span>
				</td>
				<td class="tdulleft" style="width: 15%">
					���߼������ͣ�
				</td>
				<td class="tdulright" style="width: 35%">
					<div id="antenna_polarization"></div>
				</td>

			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					���߷�λ�ǣ�
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="antenna.azimuth"
						class="inputtext required" style="width: 220px" maxlength="40" />
					<span style="color: red">*</span>
				</td>
				<td class="tdulleft" style="width: 15%">
					���߸�����
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="antenna.pitchofangle"
						class="inputtext required" style="width: 220px" maxlength="40" />
					<span style="color: red">*</span>
				</td>
			</tr>

			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					�������棺
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="antenna.gain"
						class="number  " style="width: 220px"
						maxlength="40" />
				</td>
				<td class="tdulleft" style="width: 15%">
					���߹Ҹߣ�
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="antenna.height"
						class="number  " style="width: 220px"
						maxlength="40" />
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					���߳��̣�
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="antenna.producter" class="inputtext "
						style="width: 220px" maxlength="40" />
				</td>
				<td class="tdulleft" style="width: 15%">
					Ͷ��ʹ�����ڣ�
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="antenna.createTime"
						class="Wdate inputtext required" style="width: 220px"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
					<span style="color: red">*</span>
				</td>
			</tr>


			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					��е��ǣ�
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="antenna.machineObliquity"
						class="number" style="width: 220px"
						maxlength="40" />
				</td>
				<td class="tdulleft" style="width: 15%">
					������ǣ�
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="antenna.electornObliquity"
						class="number" style="width: 220px"
						maxlength="40" />
				</td>

			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					����ͨ����������
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="antenna.chunncelDatasize;"
						class="inputtext validate-integer" style="width: 220px"
						maxlength="40" />
				</td>
				<td class="tdulleft" style="width: 15%">
					�Ƿ��������ߣ�
				</td>
				<td class="tdulright">
					<select name="isBeautify" class="inputtext" style="width: 220px">
						<option value="y">
							��
						</option>
						<option value="n">
							��
						</option>
					</select>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					�������ң�
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="antenna.beautifyVender" class="inputtext"
						style="width: 220px" maxlength="40" />
				</td>
				<td class="tdulleft" style="width: 15%">
					������ʽ��
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="antenna.beautifyMode" class="inputtext"
						style="width: 220px" maxlength="40" />
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					�̶��ʲ���ţ�
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="antenna.assetCode" class="inputtext"
						style="width: 220px" maxlength="40" />
				</td>
				<td class="tdulleft" style="width: 15%">
					��ע��
				</td>
				<td class="tdulright" style="width: 35%">
					<textArea name="antenna.remark" class="inputtext"
						style="width: 220px"></textArea>
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
