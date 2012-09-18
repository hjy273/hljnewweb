<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<script src="${ctx}/js/validation/prototype.js" type="text/javascript"></script>
<script src="${ctx}/js/validation/effects.js" type="text/javascript"></script>
<script src="${ctx}/js/validation/validation_cn.js"
	type="text/javascript"></script>
<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet"
	type="text/css">
<script type="text/javascript"
	src="${ctx}/js/wdatePicker/WdatePicker.js"></script>
<link type="text/css" rel="stylesheet"
	href="${ctx}/js/wdatePicker/skin/WdatePicker.css">
<head>
	<script language="javascript" type="">

</script>
</head>
<body>
	<template:titile value="查看基站天线信息" />

	<s:form action="antennaAction_save" name="view" method="post">
		<table width="850" border="0" align="center" cellpadding="3"
			cellspacing="0" class="tabout">
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					站址编号：
				</td>
				<td class="tdulright" colspan="3" style="width: 85%">
					${BASESTATIONS[antenna.parentId]}
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
					天线极化类型：
				</td>
				<td class="tdulright" style="width: 35%">
					<apptag:dynLabel objType="dic" id="${antenna.polarizationType}"
						dicType="antenna_polarization"></apptag:dynLabel>
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
					天线编号：
				</td>
				<td class="tdulright" style="width: 35%">
					${antenna.antennaCode}
				</td>
				<td class="tdulleft" style="width: 15%">
					经度：
				</td>
				<td class="tdulright" style="width: 35%">
					${antenna.longitude}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					纬度：
				</td>
				<td class="tdulright" style="width: 35%">
					${antenna.latitude}
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
					电子倾角：
				</td>
				<td class="tdulright" style="width: 35%">
					${antenna.electornObliquity}
				</td>
				<td class="tdulleft" style="width: 15%">
					天线型号：
				</td>
				<td class="tdulright" style="width: 35%">
					${antenna.antennaModel}
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
					<c:if test="${row.isBeautify == 'y'}">是</c:if>
					<c:if test="${row.isBeautify != 'y'}">否</c:if>
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
				<td class="tdulright" colspan="3" style="width: 85%">
					${antenna.assetCode}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					备注：
				</td>
				<td class="tdulright" colspan="3" style="width: 85%">
					${antenna.remark}
				</td>
			</tr>
			<table width="40%" border="0" style="margin-top: 6px" align="center"
				cellpadding="0" cellspacing="0">
				<tr align="center">
					<td>
						<c:if test="${showClose=='0'}">
							<input type="button" class="button" onclick="history.back()"
								value="返回">
						</c:if>
						<c:if test="${showClose=='1'}">
							<input type="button" class="button" onclick="closeWin();"
								value="关闭">
						</c:if>
					</td>
				</tr>
			</table>
			<script type="text/javascript">
			function formCallback(result, form) {
				window.status = "valiation callback for form '" + form.id + "': result = " + result;
			}

			var valid = new Validation('addStationFrom', {immediate : true, onFormValidate : formCallback});
		</script>
			</s:form>
</body>
