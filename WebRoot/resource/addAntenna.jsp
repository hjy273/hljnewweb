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
			//msg.innerHTML="天线名称不能为空或null";
			//return;
			//}
			}
	function validateCode(){
			//if($('antenna.antennaCode').value==''||$('antenna.antennaCode').value.trim()==''||$('antenna.antennaCode').value=='null'){
			//msg1.innerHTML="天线编号不能为空或null";
			//return;
			//}
	}
Ext.onReady(function() {
	jQuery("#addAntennaForm").validate();
	//inputtext validate-number -> number
	//天线类型	antenna.antennaType			antenna_type			antenna_type
	var  antenna_type = new Appcombox({
		hiddenName : 'antenna.antennaType',
		emptyText : '请选择',
		dataUrl : '${ctx}/common/externalresources_getDictionaryJson.jspx?type=antenna_type',
	   	dataCode : 'CODEVALUE',
	   	dataText : 'LABLE',
		allowBlank:true,
		renderTo: 'antenna_type'
	});
	//天线极化类型	antenna.polarizationType	antenna_polarization	antenna_polarization
	var  antenna_polarization = new Appcombox({
		hiddenName : 'antenna.polarizationType',
		emptyText : '请选择',
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
	<template:titile value="添加基站天线信息"/>

	<s:form action="antennaAction_save" name="addAntennaForm" method="post" id="addAntennaForm">
		<table width="850" border="0" align="center" cellpadding="3"
			cellspacing="0" class="tabout">
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					所属基站：
				</td>
				<td class="tdulright" colspan="3" style="width: 85%">
					<input type="text" id="stationName" onchange="getStation()"
						value="请输入查询内容，在点击下面列表" onmouseover="this.select()"
						class="inputtext" style="width: 220px" maxlength="40" />
					<br />
					<select name="antenna.parentId"
						onchange="getLatitudeAndLongitude()" id="antenna_parentId"
						class="inputtext required" style="width: 220px">
						<option value="">
							请选择
						</option>
					</select>
					<span style="color: red">*</span>
				</td>
			</tr>
			<tr class="trwhite">

				<td class="tdulleft" style="width: 15%">
					天线编号：
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="antenna.antennaCode"
						class="inputtext required" style="width: 220px" maxlength="40" />
					<span style="color: red">*</span>
				</td>
				<td class="tdulleft" style="width: 15%">
					天线名称：
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="antenna.antennaName"
						class="inputtext required " style="width: 220px" maxlength="40" />
					<span style="color: red">*</span>
				</td>
			</tr>
			<tr class="trwhite">

				<td class="tdulleft" style="width: 15%">
					经纬度：
				</td>
				<td class="tdulright" colspan="3" style="width: 85%">
					<input type="text" id="position" name="position"
						class="inputtext required isJW"
						style="width: 220px" maxlength="40" />
					<input type="button" value="选择" onclick="getitude()" />
					<span style="color: red">*（经度纬度以逗号隔开）</span>
				</td>
			</tr>
			<tr class="trwhite">

				<td class="tdulleft" style="width: 15%">
					天线型号：
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="antenna.antennaModel" class="inputtext"
						style="width: 220px" maxlength="40" />
				</td>
				<td class="tdulleft" style="width: 15%">
					天线类型：
				</td>
				<td class="tdulright" style="width: 35%">
					<div id="antenna_type"></div>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					天线数量：
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="antenna.antennaNumber"
						class="inputtext validate-integer required" style="width: 220px"
						maxlength="40" />
					<span style="color: red">*</span>
				</td>
				<td class="tdulleft" style="width: 15%">
					天线极化类型：
				</td>
				<td class="tdulright" style="width: 35%">
					<div id="antenna_polarization"></div>
				</td>

			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					天线方位角：
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="antenna.azimuth"
						class="inputtext required" style="width: 220px" maxlength="40" />
					<span style="color: red">*</span>
				</td>
				<td class="tdulleft" style="width: 15%">
					天线俯仰角
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="antenna.pitchofangle"
						class="inputtext required" style="width: 220px" maxlength="40" />
					<span style="color: red">*</span>
				</td>
			</tr>

			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					天线增益：
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="antenna.gain"
						class="number  " style="width: 220px"
						maxlength="40" />
				</td>
				<td class="tdulleft" style="width: 15%">
					天线挂高：
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="antenna.height"
						class="number  " style="width: 220px"
						maxlength="40" />
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					天线厂商：
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="antenna.producter" class="inputtext "
						style="width: 220px" maxlength="40" />
				</td>
				<td class="tdulleft" style="width: 15%">
					投入使用日期：
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
					机械倾角：
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="antenna.machineObliquity"
						class="number" style="width: 220px"
						maxlength="40" />
				</td>
				<td class="tdulleft" style="width: 15%">
					电子倾角：
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="antenna.electornObliquity"
						class="number" style="width: 220px"
						maxlength="40" />
				</td>

			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					天线通道数据量：
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="antenna.chunncelDatasize;"
						class="inputtext validate-integer" style="width: 220px"
						maxlength="40" />
				</td>
				<td class="tdulleft" style="width: 15%">
					是否美化天线：
				</td>
				<td class="tdulright">
					<select name="isBeautify" class="inputtext" style="width: 220px">
						<option value="y">
							是
						</option>
						<option value="n">
							否
						</option>
					</select>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					美化厂家：
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="antenna.beautifyVender" class="inputtext"
						style="width: 220px" maxlength="40" />
				</td>
				<td class="tdulleft" style="width: 15%">
					美化方式：
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="antenna.beautifyMode" class="inputtext"
						style="width: 220px" maxlength="40" />
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					固定资产编号：
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="antenna.assetCode" class="inputtext"
						style="width: 220px" maxlength="40" />
				</td>
				<td class="tdulleft" style="width: 15%">
					备注：
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
					<input type="submit" class="button" value="添加">
				</td>
				<td>
					<input type="button" class="button" onclick="history.back()"
						value="返回">
				</td>
			</tr>
		</table>
	</s:form>
</body>
