<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<script src="${ctx}/js/validation/prototype.js" type="text/javascript"></script>
<script src="${ctx}/js/validation/effects.js" type="text/javascript"></script>
<script src="${ctx}/js/validation/validation_cn.js" type="text/javascript"></script>
<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${ctx}/js/wdatePicker/WdatePicker.js"></script>
<link type="text/css" rel="stylesheet" href="${ctx}/js/wdatePicker/skin/WdatePicker.css">
<head>
<script language="javascript" type="text/javascript">
</script>
</head>
<body>
	<template:titile value="查看油机信息"/>
	<br/>
		
		<table width="850" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
			<tr class="trwhite" id="town">
				<td class="tdulright" style="width:15%">
					油机信息
				</td>
				<td class="tdulright" colspan="3" style="width:85%">
				</td>
			</tr> 
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					油机编号：
				</td>
				<td class="tdulright" style="width:35%">
					${oilEngine.oilEngineCode }
				</td>
				<td class="tdulleft" style="width:15%">
					油机厂商：
				</td>
				<td class="tdulright" style="width:35%">
					${oilEngine.producer}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					油机型号：
				</td>
				<td class="tdulright" style="width:35%">
					${oilEngine.oilEngineModel}
				</td>
				<td class="tdulleft" style="width:15%">
					油机类型：
				</td>
				<td class="tdulright" style="width:35%">
					<apptag:dynLabel dicType="OIL_ENGINE_TYPE" objType="dic" id="${oilEngine.oilEngineType}"></apptag:dynLabel>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					油料类型：
				</td>
				<td class="tdulright" style="width:35%">
					<apptag:dynLabel dicType="OIL_TYPE" objType="dic" id="${oilEngine.oilType}"></apptag:dynLabel>
				</td>
				<td class="tdulleft" style="width:15%">
					油机相数：
				</td>
				<td class="tdulright" style="width:35%">
					<apptag:dynLabel dicType="OIL_ENGINE_PHASE" objType="dic" id="${oilEngine.oilEnginePhase}"></apptag:dynLabel>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					所属区县：
				</td>
				<td class="tdulright" style="width:35%">
				${oilEngine.districtName}
				</td>
				<td class="tdulleft" style="width:15%">
					所在基站：
				</td>
				<td class="tdulright" style="width:35%" id="baseStation">
					${BASESTATIONNAME}
				</select>
				</td>
			</tr>
			<tr class="trwhite">
			<td class="tdulleft" style="width:15%">
					经度：
				</td>
				<td class="tdulright" style="width:35%" id="baseStation">
					${oilEngine.longitude}
				</select>
				</td>
				<td class="tdulleft" style="width:15%">
					纬度：
				</td>
				<td class="tdulright" style="width:35%">
					${oilEngine.latitude}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					所在地：
				</td>
				<td class="tdulright" style="width:35%">
				${oilEngine.address}
				</td>
				<td class="tdulleft" style="width:15%">
					油机重量：
				</td>
				<td class="tdulright" style="width:35%">
					${oilEngine.oilEngineWeight}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					额定功率：
				</td>
				<td class="tdulright" colspan="3" style="width:35%">
					${oilEngine.rationPower} KW
				</td>
			</tr>
			<tr class="trwhite" id="town">
				<td class="tdulright" style="width:15%">
					附加信息
				</td>
				<td class="tdulright" colspan="3" style="width:85%">
				</td>
			</tr> 
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					负责人：
				</td>
				<td class="tdulright" style="width:35%">
					${oilEngine.principal}
				</td>
				<td class="tdulleft" style="width:15%">
					联系电话：
				</td>
				<td class="tdulright" style="width:35%">
					${oilEngine.phone}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					备注：
				</td>
				<td class="tdulright" colspan="3" style="width:35%">
					${oilEngine.remark}
				</td>
			</tr>
		</table>
		<table width="40%" border="0"  style="margin-top: 6px" align="center" cellpadding="0" cellspacing="0">
			<tr align="center">
		      	<td>
		      	  	<input type="button" class="button" onclick="history.back()" value="返回" >
		      	</td>
		    </tr>
		   </table>
</body>
