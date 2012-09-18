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
	<template:titile value="�鿴��վ������Ϣ" />

	<s:form action="antennaAction_save" name="view" method="post">
		<table width="850" border="0" align="center" cellpadding="3"
			cellspacing="0" class="tabout">
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					վַ��ţ�
				</td>
				<td class="tdulright" colspan="3" style="width: 85%">
					${BASESTATIONS[antenna.parentId]}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					���߷�λ�ǣ�
				</td>
				<td class="tdulright" style="width: 35%">
					${antenna.azimuth}
				</td>
				<td class="tdulleft" style="width: 15%">
					���߸�����
				</td>
				<td class="tdulright" style="width: 35%">
					${antenna.pitchofangle}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					����������
				</td>
				<td class="tdulright" style="width: 35%">
					${antenna.antennaNumber}
				</td>
				<td class="tdulleft" style="width: 15%">
					�������ͣ�
				</td>
				<td class="tdulright" style="width: 35%">
					<apptag:dynLabel objType="dic" id="${antenna.antennaType}"
						dicType="antenna_type"></apptag:dynLabel>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					�������棺
				</td>
				<td class="tdulright" style="width: 35%">
					${antenna.gain}
				</td>
				<td class="tdulleft" style="width: 15%">
					���߹Ҹߣ�
				</td>
				<td class="tdulright" style="width: 35%">
					${antenna.height}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					���߳��̣�
				</td>
				<td class="tdulright" style="width: 35%">
					${antenna.producter}
				</td>
				<td class="tdulleft" style="width: 15%">
					Ͷ��ʹ�����ڣ�
				</td>
				<td class="tdulright" style="width: 35%">
					${antenna.createTime}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					���߼������ͣ�
				</td>
				<td class="tdulright" style="width: 35%">
					<apptag:dynLabel objType="dic" id="${antenna.polarizationType}"
						dicType="antenna_polarization"></apptag:dynLabel>
				</td>
				<td class="tdulleft" style="width: 15%">
					�������ƣ�
				</td>
				<td class="tdulright" style="width: 35%">
					${antenna.antennaName}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					���߱�ţ�
				</td>
				<td class="tdulright" style="width: 35%">
					${antenna.antennaCode}
				</td>
				<td class="tdulleft" style="width: 15%">
					���ȣ�
				</td>
				<td class="tdulright" style="width: 35%">
					${antenna.longitude}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					γ�ȣ�
				</td>
				<td class="tdulright" style="width: 35%">
					${antenna.latitude}
				</td>
				<td class="tdulleft" style="width: 15%">
					��е��ǣ�
				</td>
				<td class="tdulright" style="width: 35%">
					${antenna.machineObliquity}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					������ǣ�
				</td>
				<td class="tdulright" style="width: 35%">
					${antenna.electornObliquity}
				</td>
				<td class="tdulleft" style="width: 15%">
					�����ͺţ�
				</td>
				<td class="tdulright" style="width: 35%">
					${antenna.antennaModel}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					����ͨ����������
				</td>
				<td class="tdulright" style="width: 35%">
					${antenna.chunncelDatasize}
				</td>
				<td class="tdulleft" style="width: 15%">
					�Ƿ��������ߣ�
				</td>
				<td class="tdulright" style="width: 35%">
					<c:if test="${row.isBeautify == 'y'}">��</c:if>
					<c:if test="${row.isBeautify != 'y'}">��</c:if>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					�������ң�
				</td>
				<td class="tdulright" style="width: 35%">
					${antenna.beautifyVender}
				</td>
				<td class="tdulleft" style="width: 15%">
					������ʽ��
				</td>
				<td class="tdulright" style="width: 35%">
					${antenna.beautifyMode}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					�̶��ʲ���ţ�
				</td>
				<td class="tdulright" colspan="3" style="width: 85%">
					${antenna.assetCode}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					��ע��
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
								value="����">
						</c:if>
						<c:if test="${showClose=='1'}">
							<input type="button" class="button" onclick="closeWin();"
								value="�ر�">
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
