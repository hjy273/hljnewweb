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
	<template:titile value="�鿴�ͻ���Ϣ"/>
	<br/>
		
		<table width="850" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
			<tr class="trwhite" id="town">
				<td class="tdulright" style="width:15%">
					�ͻ���Ϣ
				</td>
				<td class="tdulright" colspan="3" style="width:85%">
				</td>
			</tr> 
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					�ͻ���ţ�
				</td>
				<td class="tdulright" style="width:35%">
					${oilEngine.oilEngineCode }
				</td>
				<td class="tdulleft" style="width:15%">
					�ͻ����̣�
				</td>
				<td class="tdulright" style="width:35%">
					${oilEngine.producer}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					�ͻ��ͺţ�
				</td>
				<td class="tdulright" style="width:35%">
					${oilEngine.oilEngineModel}
				</td>
				<td class="tdulleft" style="width:15%">
					�ͻ����ͣ�
				</td>
				<td class="tdulright" style="width:35%">
					<apptag:dynLabel dicType="OIL_ENGINE_TYPE" objType="dic" id="${oilEngine.oilEngineType}"></apptag:dynLabel>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					�������ͣ�
				</td>
				<td class="tdulright" style="width:35%">
					<apptag:dynLabel dicType="OIL_TYPE" objType="dic" id="${oilEngine.oilType}"></apptag:dynLabel>
				</td>
				<td class="tdulleft" style="width:15%">
					�ͻ�������
				</td>
				<td class="tdulright" style="width:35%">
					<apptag:dynLabel dicType="OIL_ENGINE_PHASE" objType="dic" id="${oilEngine.oilEnginePhase}"></apptag:dynLabel>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					�������أ�
				</td>
				<td class="tdulright" style="width:35%">
				${oilEngine.districtName}
				</td>
				<td class="tdulleft" style="width:15%">
					���ڻ�վ��
				</td>
				<td class="tdulright" style="width:35%" id="baseStation">
					${BASESTATIONNAME}
				</select>
				</td>
			</tr>
			<tr class="trwhite">
			<td class="tdulleft" style="width:15%">
					���ȣ�
				</td>
				<td class="tdulright" style="width:35%" id="baseStation">
					${oilEngine.longitude}
				</select>
				</td>
				<td class="tdulleft" style="width:15%">
					γ�ȣ�
				</td>
				<td class="tdulright" style="width:35%">
					${oilEngine.latitude}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					���ڵأ�
				</td>
				<td class="tdulright" style="width:35%">
				${oilEngine.address}
				</td>
				<td class="tdulleft" style="width:15%">
					�ͻ�������
				</td>
				<td class="tdulright" style="width:35%">
					${oilEngine.oilEngineWeight}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					����ʣ�
				</td>
				<td class="tdulright" colspan="3" style="width:35%">
					${oilEngine.rationPower} KW
				</td>
			</tr>
			<tr class="trwhite" id="town">
				<td class="tdulright" style="width:15%">
					������Ϣ
				</td>
				<td class="tdulright" colspan="3" style="width:85%">
				</td>
			</tr> 
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					�����ˣ�
				</td>
				<td class="tdulright" style="width:35%">
					${oilEngine.principal}
				</td>
				<td class="tdulleft" style="width:15%">
					��ϵ�绰��
				</td>
				<td class="tdulright" style="width:35%">
					${oilEngine.phone}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					��ע��
				</td>
				<td class="tdulright" colspan="3" style="width:35%">
					${oilEngine.remark}
				</td>
			</tr>
		</table>
		<table width="40%" border="0"  style="margin-top: 6px" align="center" cellpadding="0" cellspacing="0">
			<tr align="center">
		      	<td>
		      	  	<input type="button" class="button" onclick="history.back()" value="����" >
		      	</td>
		    </tr>
		   </table>
</body>
