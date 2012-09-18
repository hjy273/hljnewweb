<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<script src="${ctx}/js/extjs/ux/Appcombox.js" type="text/javascript"></script>
<head>
<script language="javascript" type="text/javascript">

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
	function getitude() {
 
		var actionUrl = "/WEBGIS/gisextend/igis.jsp?actiontype=101&userid=yxcs&eid=position";
		window
				.open(actionUrl, 'map',
						'width=400,height=300,scrollbars=yes,z-look=yes,alwaysRaised=yes');
	}
	
function back(){
		window.location.href='${ctx}/antennaAction_query.jspx';
	}
Ext.onReady(function() {
	jQuery("#editAntennaForm").validate();
	//��������	antenna.antennaType		antenna_type		antenna_type
	var  antenna_type = new Appcombox({
		hiddenName : 'antenna.antennaType',
		hiddenId:'antennaType',
		emptyText : '��ѡ��',
		dataUrl : '${ctx}/common/externalresources_getDictionaryJson.jspx?type=antenna_type',
	   	dataCode : 'CODEVALUE',
	   	dataText : 'LABLE',
		allowBlank:true,
		renderTo: 'antenna_type'
	});
	antenna_type.setComboValue('${antenna.antennaType}','<apptag:dynLabel objType="dic" id="${antenna.antennaType}" dicType="antenna_type"></apptag:dynLabel>');
	//���߼�������	antenna.polarizationType	antenna_polarization	antenna_polarization
	var  antenna_polarization = new Appcombox({
		hiddenName : 'antenna.polarizationType',
		hiddenId:'polarizationType',
		emptyText : '��ѡ��',
		dataUrl : '${ctx}/common/externalresources_getDictionaryJson.jspx?type=antenna_polarization',
	   	dataCode : 'CODEVALUE',
	   	dataText : 'LABLE',
		allowBlank:true,
		renderTo: 'antenna_polarization'
	});
	antenna_polarization.setComboValue('${antenna.polarizationType}','<apptag:dynLabel objType="dic" id="${antenna.polarizationType}" dicType="antenna_polarization"></apptag:dynLabel>');
});
</script>
</head>
<body>
	<template:titile value="�޸Ļ�վ������Ϣ"/>

	<s:form action="antennaAction_update" name="editAntennaForm"
		method="post" id="editAntennaForm">
		<table width="850" border="0" align="center" cellpadding="3"
			cellspacing="0" class="tabout">
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					������վ��
				</td>
				<td class="tdulright" colspan="3" style="width: 85%">
					<s:select list="%{#request.BASESTATIONS}"
						onchange="getLatitudeAndLongitude()" name="antenna.parentId"
						value="%{#request.antenna.parentId}" cssClass="inputtext required"
						cssStyle="width:180"></s:select>
					<span style="color: red">*</span>
				</td>
			</tr>
			<tr class="trwhite">

				<td class="tdulleft" style="width: 15%">
					���߱�ţ�
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="antenna.antennaCode"
						value="${antenna.antennaCode }" class="inputtext required"
						style="width: 220px" maxlength="40" />
					<span style="color: red">*</span>
				</td>
				<td class="tdulleft" style="width: 15%">
					�������ƣ�
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="antenna.antennaName"
						value="${antenna.antennaName}" class="inputtext required "
						style="width: 220px" maxlength="40" />
					<span style="color: red">*</span>
				</td>
			</tr>
			<tr class="trwhite">

				<td class="tdulleft" style="width: 15%">
					��γ�ȣ�
				</td>
				<td class="tdulright" colspan="3" style="width: 85%">
					<input type="text" id="position" name="position"
						value="${position}"
						class="inputtext required isJW"
						style="width: 220px" maxlength="40" />
					<input type="button" value="ѡ��" style="display: none"  onclick="getitude()" />
					<span style="color: red">*������γ���Զ��Ÿ�����</span>

				</td>
			</tr>
			<tr class="trwhite">

				<td class="tdulleft" style="width: 15%">
					�����ͺţ�
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="antenna.antennaModel"
						value="${antenna.antennaModel}" class="inputtext"
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
						value="${antenna.antennaNumber}"
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
						value="${antenna.azimuth }" class="inputtext required"
						style="width: 220px" maxlength="40" />
					<span style="color: red">*</span>
				</td>
				<td class="tdulleft" style="width: 15%">
					���߸�����
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="antenna.pitchofangle"
						value="${antenna.pitchofangle }" class="inputtext required"
						style="width: 220px" maxlength="40" />
					<span style="color: red">*</span>
				</td>
			</tr>

			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					�������棺
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="antenna.gain" value="${antenna.gain }"
						class="number  " style="width: 220px"
						maxlength="40" />
				</td>
				<td class="tdulleft" style="width: 15%">
					���߹Ҹߣ�
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="antenna.height" value="${antenna.height }"
						class="number  " style="width: 220px"
						maxlength="40" />
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					���߳��̣�
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="antenna.producter"
						value="${antenna.producter }" class="inputtext "
						style="width: 220px" maxlength="40" />
				</td>
				<td class="tdulleft" style="width: 15%">
					Ͷ��ʹ�����ڣ�
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="antenna.createTime"
						value="${antenna.createTime}" class="Wdate inputtext required"
						style="width: 220px" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
					<span style="color: red">*</span>
				</td>
			</tr>


			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					��е��ǣ�
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="antenna.machineObliquity"
						value="${antenna.machineObliquity }"
						class="number" style="width: 220px"
						maxlength="40" />
				</td>
				<td class="tdulleft" style="width: 15%">
					������ǣ�
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="antenna.electornObliquity"
						value="${antenna.electornObliquity}"
						class="number" style="width: 220px"
						maxlength="40" />
				</td>

			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					����ͨ����������
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="antenna.chunncelDatasize"
						value="${antenna.chunncelDatasize}"
						class="inputtext validate-integer" style="width: 220px"
						maxlength="40" />
				</td>
				<td class="tdulleft" style="width: 15%">
					�Ƿ��������ߣ�
				</td>
				<td class="tdulright">
					<s:select list="#{'n':'��','y':'��'}" name="antenna.isBeautify"
						value="%{#request.baseStation.isBoundary}" listKey="key"
						listValue="value" cssClass="inputtext" cssStyle="width:220px"></s:select>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					�������ң�
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="antenna.beautifyVender"
						value="${antenna.beautifyVender }" class="inputtext"
						style="width: 220px" maxlength="40" />
				</td>
				<td class="tdulleft" style="width: 15%">
					������ʽ��
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="antenna.beautifyMode"
						value="${antenna.beautifyMode}" class="inputtext"
						style="width: 220px" maxlength="40" />
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					�̶��ʲ���ţ�
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="antenna.assetCode"
						value="${antenna.assetCode }" class="inputtext"
						style="width: 220px" maxlength="40" />
				</td>
				<td class="tdulleft" style="width: 15%">
					��ע��
				</td>
				<td class="tdulright" style="width: 35%">
					<textArea name="antenna.remark" class="inputtext"
						value="${antenna.remark}" style="width: 220px"></textArea>
				</td>
			</tr>
		</table>
		<table width="40%" border="0" style="margin-top: 6px" align="center"
			cellpadding="0" cellspacing="0">
			<tr align="center">
				<td>
					<input type="submit" class="button" value="����">
				</td>
				<td>
					<input type="button" class="button" onclick="back()" value="����">
				</td>
			</tr>
		</table>
		<div align="center" style="margin-top: 20px; color: red;">
			��ɫ*Ϊ������
		</div>
	</s:form>
</body>
