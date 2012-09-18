<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<link href="${ctx }/js/jQuery/jautocomplete/jquery.autocomplete.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${ctx}/js/jQuery/jautocomplete/jquery.autocomplete.min.js"></script>
<head>
<script language="javascript" type="text/javascript">
	function getStation() {
		//var url = "${ctx}/baseStationAction_getStation.jspx";
		//var myAjax = new Ajax.Request(url, {
		//	method : 'post',
		//	asynchronous : 'false',
		//	parameters : "district=&&id="+ $('stationName').value,
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
		//$("equipment.parentId").options.length = 1;
		//var str = response.responseText;
		//if (str == "")
		//	return true;
		//var optionlist = str.split(";");
		//for ( var i = 0; i < optionlist.length - 1; i++) {
		//	var t = optionlist[i].split("=")[0];
		//	var v = optionlist[i].split("=")[1];
		//	$("equipment.parentId").options[i + 1] = new Option(v, t);
		//}
		jQuery("#equipment_parentId option").length = 1;
		var str = responseText;
		if (str == "")
			return true;
		var optionlist = str.split(";");
		for ( var i = 0; i < optionlist.length - 1; i++) {
			var v = optionlist[i].split("=")[0];
			var t = optionlist[i].split("=")[1];
			jQuery("<option value='"+v+"'>"+t+"</option>").appendTo("#equipment_parentId");
		}
	}
	
	jQuery().ready(function() {
		jQuery("#addEquForm").validate();
		jQuery("#equTypeId").change(function(){
			jQuery("#equname").flushCache();
		});
		
		jQuery("#equname").autocomplete("${ctx}/equipmentAction_autoCompleteEquName.jspx", {
			minChars: 0,
			extraParams:{typeId:function(){return jQuery('#equTypeId').val();}},
			delay:10,
            matchSubset:1,
            matchContains:1,
            cacheLength:10,
            matchContains: true,
            mustMatch: false,
            scrollHeight: 200,
            width:150,
            autoFill:false
		});
	});
	function back(){
		window.location.href='${ctx}/equipmentAction_query.jspx';
	}
</script>
</head>
<body>
	<template:titile value="添加设备"/>

	<s:form action="equipmentAction_save" name="addEquipmentFrom"
	 method="post"  id="addEquForm">
		<table width="850" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					所属基站：
				</td>
				<td class="tdulright" colspan="3" style="width:85%">
					<input type="text" id="stationName" onchange="getStation()" value="请输入查询内容，在点击下面列表" onmouseover="this.select()" class="inputtext" style="width: 220px" maxlength="40" />
					<br/>
					<select name="equipment.parentId" id="equipment_parentId" class="inputtext required" style="width: 220px">
					<option value="">请选择</option>
					</select>
					<span style="color:red">*</span>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					设备类型：
				</td>
				<td class="tdulright" style="width:35%">
					<s:select list="%{#request.EQUIPMENTSTYPE}" name="equipment.equTypeId" id="equTypeId" cssClass="inputtext" cssStyle="width:220"></s:select>
				</td>
				<td class="tdulleft" style="width:15%">
					设备名称：
				</td>
				<td class="tdulright" style="width:35%">
					<input type="text" name="equipment.equName" id="equname" autocomplete="off" readonly="readonly"  class="inputtext required" style="width:220" maxlength="50"/><span style="color:red">*</span><br>鼠标点击两下进行选择
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					设备厂商：
				</td>
				<td class="tdulright" style="width:35%">
					<input type="text" name="equipment.equProducter" class="inputtext required" style="width:220" maxlength="40"/><span style="color:red">*</span>
				</td>
				<td class="tdulleft" style="width:15%">
					设备型号：
				</td>
				<td class="tdulright" style="width:35%">
					<input type="text" name="equipment.equModel" class="inputtext required" style="width:220" maxlength="40"/><span style="color:red">*</span>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					配置：
				</td>
				<td class="tdulright" style="width:35%">
					<input type="text" name="equipment.equDeploy" class="inputtext" style="width:220" maxlength="40"/><span style="color:red"><br>根据设备情况填写</span>
				</td>
				<td class="tdulleft" style="width:15%">
					数量：
				</td>
				<td class="tdulright" style="width:35%">
					<input type="text" name="equipment.equNum" class="number" style="width:220" maxlength="40" value="1"/><span style="color:red"><br>根据设备情况填写</span>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					容量：
				</td>
				<td class="tdulright" style="width:35%">
					<input type="text" name="equipment.equCapacity" class="inputtext" style="width:220" maxlength="40"/><span style="color:red"><br>根据设备情况填写</span>
				</td>
				<td class="tdulleft" style="width:15%">
					启用日期：
				</td>
				<td class="tdulright" style="width:35%">
					<input type="text" name="equipment.equEnableDate"  readonly="readonly" class="Wdate inputtext required" style="width:220px" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/><span style="color:red">*</span>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					条形码：
				</td>
				<td class="tdulright" style="width:35%">
					<input type="text" name="equipment.barCode" value="${equipment.barCode}" class="inputtext" style="width:220px" />
				</td>
				<td class="tdulleft" style="width:15%">
					备注：
				</td>
				<td class="tdulright" style="width:35%">
					<input type="text" name="equipment.remark" class="inputtext" style="width:220" maxlength="200"/>
				</td>
			</tr>
			
		</table>
		<table width="40%" border="0"  style="margin-top: 6px" align="center" cellpadding="0" cellspacing="0"><tr align="center">
		      	<td>
		      		<input type="submit" class="button" value="添加">
		      	</td>
		      	<td>
		      	  	<input type="button" class="button" onclick="back()" value="返回" >
		      	</td>
		    </tr></table>
		    <div align="center" style="margin-top: 20px;color:red;">红色*为必填项</div>
	</s:form>
</body>
