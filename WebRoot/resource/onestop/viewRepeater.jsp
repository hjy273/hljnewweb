<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<body>
	<template:titile value="�鿴ֱ��վ" />
	<s:form action="repeaterAction_save" name="view" method="post">
		<table width="850" border="0" align="center" cellpadding="3"
			cellspacing="0" class="tabout">
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					ֱ��վ�ţ�
				</td>
				<td class="tdulright" style="width: 35%">
					${repeater.repeaterType}
				</td>
				<td class="tdulleft" style="width: 15%">
					ֱ��վ���ƣ�
				</td>
				<td class="tdulright" style="width: 35%">
					${repeater.repeaterName}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					��Դ��վ��
				</td>
				<td class="tdulright" colspan="3" style="width: 85%">
					${BASESTATIONS[repeater.msBsId]}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					��ԴС���ţ�
				</td>
				<td class="tdulright" style="width: 35%">
					${repeater.cellCode}
				</td>
				<td class="tdulleft" style="width: 15%">
					��Ԫ���룺
				</td>
				<td class="tdulright" style="width: 35%">
					${repeater.netElementCode}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					��Ԫ���ƣ�
				</td>
				<td class="tdulright" style="width: 35%">
					${repeater.netElementName}
				</td>
				<td class="tdulleft" style="width: 15%">
					�������У�
				</td>
				<td class="tdulright" style="width: 35%">
					${repeater.city}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					�������أ�
				</td>
				<td class="tdulright" style="width: 35%">
					<apptag:dynLabel objType="region" id="${repeater.district}"
						dicType=""></apptag:dynLabel>
				</td>
				<td class="tdulleft" style="width: 15%">
					��������
				</td>
				<td class="tdulright" style="width: 35%">
					${repeater.town}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					��װλ�ã�
				</td>
				<td class="tdulright" style="width: 35%">
					${repeater.installPlace}
				</td>
				<td class="tdulleft" style="width: 15%">
					���ȣ�
				</td>
				<td class="tdulright" style="width: 35%">
					${repeater.longitude}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					γ�ȣ�
				</td>
				<td class="tdulright" style="width: 35%">
					${repeater.latitude}
				</td>
				<td class="tdulleft" style="width: 15%">
					����(��)��
				</td>
				<td class="tdulright" style="width: 35%">
					${repeater.elevation}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					����BSC��
				</td>
				<td class="tdulright" style="width: 35%">
					${repeater.attachBsc}
				</td>
				<td class="tdulleft" style="width: 15%">
					���Ƿ�Χ��
				</td>
				<td class="tdulright" style="width: 35%">
					<apptag:dynLabel dicType="overlay_area_type" objType="dic"
						id="${repeater.coverageArea}"></apptag:dynLabel>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					�����������ͣ�
				</td>
				<td class="tdulright" style="width: 35%">
					<apptag:dynLabel dicType="overlay_area" objType="dic"
						id="${repeater.coverageAreaType}"></apptag:dynLabel>
				</td>
				<td class="tdulleft" style="width: 15%">
					�źŽ��շ�ʽ��
				</td>
				<td class="tdulright" style="width: 35%">
					${repeater.receieMode}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					���緽ʽ��
				</td>
				<td class="tdulright" style="width: 35%">
					${repeater.powerSupplyMode}
				</td>
				<td class="tdulleft" style="width: 15%">
					���ӱ�ʶ��
				</td>
				<td class="tdulright" style="width: 35%">
					<c:if test="${repeater.isMasterSlave=='y'}">��</c:if>
					<c:if test="${repeater.isMasterSlave!='y'}">��</c:if>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					�Ƿ��أ�
				</td>
				<td class="tdulright" style="width: 35%">
					<c:if test="${repeater.isMonitor=='y'}">��</c:if>
					<c:if test="${repeater.isMonitor!='y'}">��</c:if>
				</td>
				<td class="tdulleft" style="width: 15%">
					�豸�ͺţ�
				</td>
				<td class="tdulright" style="width: 35%">
					${repeater.equModuel}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					�������ң�
				</td>
				<td class="tdulright" style="width: 35%">
					${repeater.producter}
				</td>
				<td class="tdulleft" style="width: 15%">
					���ʣ�
				</td>
				<td class="tdulright" style="width: 35%">
					${repeater.power}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					ѡƵ����
				</td>
				<td class="tdulright" style="width: 35%">
					${repeater.chooseFrequent}
				</td>
				<td class="tdulleft" style="width: 15%">
					���SIM���ţ�
				</td>
				<td class="tdulright" style="width: 35%">
					${repeater.monitorSim}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					��վʱ�䣺
				</td>
				<td class="tdulright" style="width: 35%">
					${repeater.createTime}
				</td>
				<td class="tdulleft" style="width: 15%">
					ά����λ��
				</td>
				<td class="tdulright" style="width: 35%">
					${maintenances[repeater.maintenanceId]}
				</td>
			</tr>
			<table width="40%" border="0" style="margin-top: 6px" align="center"
				cellpadding="0" cellspacing="0">
				<tr align="center">
					<td>
						<input type="button" class="button" onclick="history.back()"
							value="����">
					</td>
				</tr>
			</table>
		</table>
	</s:form>
</body>