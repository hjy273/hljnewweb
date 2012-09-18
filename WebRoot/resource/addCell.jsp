<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<head>
	<script language="javascript" type="text/javascript">
	Ext.onReady(function(){
		jQuery("#addCellForm").validate();
	});
	function getStation() {
		//var url = "${ctx}/baseStationAction_getStation.jspx?district=&&id="+ $('stationName').value;
		//var myAjax = new Ajax.Request(url, {
		//	method : 'get',
		//	asynchronous : 'false',
		//	onComplete : setStationCode
		//});
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
	function setStationCode(responseText) {
		//$("cell.parentId").options.length = 1;
		//var str = response.responseText;
		//if (str == "")
		//	return true;
		//var optionlist = str.split(";");
		//for ( var i = 0; i < optionlist.length - 1; i++) {
		//	var t = optionlist[i].split("=")[0];
		//	var v = optionlist[i].split("=")[1];
		//	$("cell.parentId").options[i + 1] = new Option(v, t);
		//}
		jQuery("#cell_parentId option").length = 1;
		var str = responseText;
		if (str == "")
			return true;
		var optionlist = str.split(";");
		for ( var i = 0; i < optionlist.length - 1; i++) {
			var v = optionlist[i].split("=")[0];
			var t = optionlist[i].split("=")[1];
			jQuery("<option value='"+v+"'>"+t+"</option>").appendTo("#cell_parentId");
		}
	}
	function back(){
		window.location.href='${ctx}/cellAction_query.jspx';
	}
	//function String.prototype.trim(){return this.replace(/(^\s*)/g, "").replace(/(\s*$)/g, "");}
	function validateCode(){
		//if($('cell.cellCode').value==''||$('cell.cellCode').value.trim()==''||$('cell.cellCode').value=='null'){
		//	msg.innerHTML="小区号不能为空或者null！";
		//	return;
		//}
	}
</script>
</head>
<body>
	<template:titile value="添加小区" />

	<s:form action="cellAction_save" name="addcellFrom" method="post" id="addCellForm">
		<table width="850" border="0" align="center" cellpadding="3"
			cellspacing="0" class="tabout">
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					所属基站：
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" id="stationName" onchange="getStation()"
						value="请输入查询内容，在点击下面列表" onmouseover="this.select()"
						class="inputtext" style="width: 220" maxlength="40" />
					<br>
					<select name="cell.parentId" id="cell_parentId" class="inputtext required"
						style="width: 220">
						<option value="">
							请选择
						</option>
					</select>
					<span style="color: red">*</span>
				</td>
				<td class="tdulleft" style="width: 15%">
					小区号：
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="cell.cellCode" class="inputtext required"
						style="width: 220" maxlength="40" />
					<span style="color: red">*</span>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					网元编号：
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="cell.netElementCode" class="inputtext"
						style="width: 220" maxlength="40" />
				</td>
				<td class="tdulleft" style="width: 15%">
					网元名称：
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="cell.netElementName" class="inputtext "
						style="width: 220" maxlength="40" />
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					中文名称：
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="cell.chineseName"
						class="inputtext required" style="width: 220" maxlength="40" />
					<span style="color: red">*</span>
				</td>
				<td class="tdulleft" style="width: 15%">
					小区广播信道：
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="cell.broadcastChannel" class="inputtext "
						style="width: 220" maxlength="40" />
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					载频数：
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="cell.carrierFrequencyNum" class="number "
						style="width: 220" maxlength="40" />
				</td>
				<td class="tdulleft" style="width: 15%">
					是否下带直放站：
				</td>
				<td class="tdulright" style="width: 35%">
					<select name="cell.isOwnRepeater" class="inputtext"
						style="width: 220">
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
					是否下带塔放：
				</td>
				<td class="tdulright" style="width: 35%">
					<select name="cell.isOwnTma" class="inputtext" style="width: 220">
						<option value="y">
							是
						</option>
						<option value="n">
							否
						</option>
					</select>
				</td>
				<td class="tdulleft" style="width: 15%">
					是否开启GPRS：
				</td>
				<td class="tdulright" style="width: 35%">
					<select name="cell.isOpenGPRS" class="inputtext" style="width: 220">
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
					是否开启EGPRS：
				</td>
				<td class="tdulright" style="width: 35%">
					<select name="cell.isOpenEGPRS" class="inputtext "
						style="width: 220">
						<option value="y">
							是
						</option>
						<option value="n">
							否
						</option>
					</select>
				</td>
				<td class="tdulleft" style="width: 15%">
					入网时间：
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="cell.anTime"
						class="Wdate inputtext required" style="width: 220"
						readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
					<span style="color: red">*</span>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					等效全向辐射频率：
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="cell.allRoundPower" class="number "
						style="width: 220" maxlength="40" />
				</td>
				<td class="tdulleft" style="width: 15%">
					工作频段：
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="cell.bcchFreq" class="number "
						style="width: 220" maxlength="40" />
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					静态PDCH数：
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="cell.staticPdchNum" class="number"
						style="width: 220" maxlength="40" />
				</td>
				<td class="tdulleft" style="width: 15%">
					动态PDCH数：
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="cell.dynamicPdchNum" class="number"
						style="width: 220" maxlength="40" />
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					是否跳频：
				</td>
				<td class="tdulright" style="width: 35%">
					<select name="cell.isFrequencyHopping" class="inputtext required"
						style="width: 220">
						<option value="y">
							是
						</option>
						<option value="n">
							否
						</option>
					</select>
				</td>
				<td class="tdulleft" style="width: 15%">
					跳频类型：
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="cell.frequencyHoppingType"
						class="inputtext" style="width: 220" maxlength="40" />
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					归属BCF：
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="cell.attachBCF" class="inputtext"
						style="width: 220" maxlength="40" />
				</td>
				<td class="tdulleft" style="width: 15%">
					是否边漫小区：
				</td>
				<td class="tdulright" style="width: 35%">
					<select name="cell.isBoundaryCell" class="inputtext "
						style="width: 220">
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
					覆盖的对方城市及电话区号：
				</td>
				<td class="tdulright" colspan="3" style="width: 85%">
					<textarea name="cell.coverThing" class="inputtext"
						style="width: 220"></textarea>
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
					<input type="button" class="button" onclick="back();" value="返回">
				</td>
			</tr>
		</table>
		<div align="center" style="margin-top: 20px; color: red;">
			红色*为必填项
		</div>
	</s:form>
</body>

