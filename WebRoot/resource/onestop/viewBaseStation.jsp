<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<body>
	<template:titile value="�鿴��վ"/>
	<s:form action="stationAction_save" name="view" method="post">
		<table width="850" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
			<tr class="trwhite">
				<td class="tdulright" style="width:15%">
					������Ϣ
				</td>
				<td class="tdulright" colspan="3" style="width:85%">
					<a href="javascript:showOrHide('baseInfoTb');">չ��/����</a>
				</td>
			</tr>
			<tbody id="baseInfoTb" style="display:;">
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					վַ��ţ�
				</td>
				<td class="tdulright" style="width:35%">
					${baseStation.stationCode}
				</td>
				<td class="tdulleft" style="width:15%">
					��վ���ƣ�
				</td>
				<td class="tdulright" style="width:35%">
					${baseStation.stationName}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					�������У�
				</td>
				<td class="tdulright" style="width:35%">
					${baseStation.city }
				</td>
				<td class="tdulleft" style="width:15%">
					�������أ�
				</td>
				<td class="tdulright" style="width:35%">
					<apptag:dynLabel objType="region" id="${baseStation.district}"
							dicType=""></apptag:dynLabel>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					��������
				</td>
				<td class="tdulright" style="width:35%">
					${baseStation.town}
				</td>
				<td class="tdulleft" style="width:15%">
					���������壺
				</td>
				<td class="tdulright" style="width:35%">
					${baseStation.village }
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					��ϸ��ַ��
				</td>
				<td class="tdulright" style="width:35%">
					${baseStation.address }
				</td>
				<td class="tdulleft" style="width:15%">
					����ʱ�䣺
				</td>
				<td class="tdulright" style="width:35%">
					${baseStation.anTime }
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					�������ͣ�
				</td>
				<td class="tdulright" style="width:35%">
					<apptag:dynLabel objType="dic" id="${baseStation.mrType}"
							dicType="property_right"></apptag:dynLabel>
				</td>
				<td class="tdulleft" style="width:15%">
					���������
				</td>
				<td class="tdulright" style="width:35%">
					${baseStation.roomArea}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					���ݽṹ��
				</td>
				<td class="tdulright" colspan="3" style="width:85%">
					<apptag:dynLabel objType="dic" id="${baseStation.roomStructure}"
							dicType="room_structure"></apptag:dynLabel>
				</td>
			</tr>
			</tbody>
			<tr class="trwhite">
				<td class="tdulright" style="width:15%">
					������Ϣ
				</td>
				<td class="tdulright" colspan="3" style="width:85%">
				</td>
			</tr>
			<tbody id="geoInfoTb">
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					���ȣ�
				</td>
				<td class="tdulright" style="width:35%">
					${baseStation.longitude}
				</td>
				<td class="tdulleft" style="width:15%">
					γ�ȣ�
				</td>
				<td class="tdulright" style="width:35%">
					${baseStation.latitude}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					����(��)��
				</td>
				<td class="tdulright" style="width:35%">
					${baseStation.elevation}
				</td>
				<td class="tdulleft" style="width:15%">
					����������
				</td>
				<td class="tdulright" style="width:35%">
					<apptag:dynLabel objType="dic" id="${baseStation.geoFeature}"
							dicType="geography"></apptag:dynLabel>
				</td>
			</tr>
			</tbody>
			<tr class="trwhite">
				<td class="tdulright" style="width:15%">
					��ʶ��Ϣ
				</td>
				<td class="tdulright" colspan="3" style="width:85%">
				</td>
			</tr>
			<tbody id="flagInfoTb">
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					�߽��վ��ʶ��
				</td>
				<td class="tdulright" style="width:35%">
					<c:if test="${baseStation.isBoundary=='y'}">��</c:if>
					<c:if test="${baseStation.isBoundary!='y'}">��</c:if>
				</td>
				<td class="tdulleft" style="width:15%">
					VIP��վ��ʶ��
				</td>
				<td class="tdulright" style="width:35%">
					<c:if test="${baseStation.isVip=='y'}">��</c:if>
					<c:if test="${baseStation.isVip !='y'}">��</c:if>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					����ڵ��ʶ��
				</td>
				<td class="tdulright" style="width:35%">
					<c:if test="${baseStation.isTransitNode == 'y'}">��</c:if>
					<c:if test="${baseStation.isTransitNode != 'y'}">��</c:if>
				</td>
				<td class="tdulleft" style="width:15%">
					�߼�վ��ʶ��
				</td>
				<td class="tdulright" style="width:35%">
					<c:if test="${baseStation.isLimit == 'y'}">��</c:if>
					<c:if test="${baseStation.isLimit != 'y'}">��</c:if>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					���ڸ��Ǳ�ʶ��
				</td>
				<td class="tdulright" style="width:35%">
					<c:if test="${baseStation.isIndoorCoverage == 'y'}">��</c:if>
					<c:if test="${baseStation.isIndoorCoverage != 'y'}">��</c:if>
				</td>
				<td class="tdulleft" style="width:15%">
					��ͨ��ʶ��
				</td>
				<td class="tdulright" style="width:35%">
					<c:if test="${baseStation.isEveryVillage == 'y'}">��</c:if>
					<c:if test="${baseStation.isEveryVillage != 'y'}">��</c:if>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					ʩ����վ��ʶ��
				</td>
				<td class="tdulright" style="width:35%">
					<c:if test="${baseStation.isBenefactorBs == 'y'}">��</c:if>
					<c:if test="${baseStation.isBenefactorBs != 'y'}">��</c:if>
				</td>
				<td class="tdulleft" style="width:15%">
					�¹һ�վ��ʶ��
				</td>
				<td class="tdulright" style="width:35%">
					<c:if test="${baseStation.isDrive == 'y'}">��</c:if>
					<c:if test="${baseStation.isDrive != 'y'}">��</c:if>
				</td>
			</tr>
			</tbody>
			<tr class="trwhite">
				<td class="tdulright" style="width:15%">
					վ�����
				</td>
				<td class="tdulright" colspan="3" style="width:85%">
				</td>
			</tr>
			<tbody id="stationInfoTb">
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					������ߣ�
				</td>
				<td class="tdulright" style="width:35%">
					<c:if test="${baseStation.isSanitaryWare == 'y'}">��</c:if>
					<c:if test="${baseStation.isSanitaryWare != 'y'}">��</c:if>
				</td>
				<td class="tdulleft" style="width:15%">
					�е����������
				</td>
				<td class="tdulright" style="width:35%">
					${baseStation.mainsLeadin}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					���������
				</td>
				<td class="tdulright" style="width:35%">
					${baseStation.gateLock}
				</td>
				<td class="tdulleft" style="width:15%">
					�������⣺
				</td>
				<td class="tdulright" style="width:35%">
					${baseStation.leaveOverQuestion}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					ҵ����ϵ�ˣ�
				</td>
				<td class="tdulright" style="width:35%">
					${baseStation.owner}
				</td>
				<td class="tdulleft" style="width:15%">
					ҵ����ϵ�绰��
				</td>
				<td class="tdulright" style="width:35%">
					${baseStation.ownerTel}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					ά����λ��
				</td>
				<td class="tdulright" colspan="3" style="width:85%">
					${maintenances[baseStation.maintenanceId]}
				</td>
			</tr>
			</tbody>
			<tr class="trwhite">
				<td class="tdulright" style="width:15%">
					������Ϣ
				</td>
				<td class="tdulright" colspan="3" style="width:85%">
				</td>
			</tr>
			<tbody id="otherInfoTb">
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					����BSC��
				</td>
				<td class="tdulright" style="width:35%">
					${baseStation.attachBSC}
				</td>
				<td class="tdulleft" style="width:15%">
					BCF������
				</td>
				<td class="tdulright" style="width:35%">
					${baseStation.bcfNum}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					Ƶ�Σ�
				</td>
				<td class="tdulright" style="width:35%">
					${baseStation.frequencyChannel}
				</td>
				<td class="tdulleft" style="width:15%">
					��վ���ͣ�
				</td>
				<td class="tdulright" style="width:35%">
					<apptag:dynLabel objType="dic" id="${baseStation.bsType}"
							dicType="basestation_type"></apptag:dynLabel>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					��վ��ʶ��
				</td>
				<td class="tdulright" style="width:35%">
					${baseStation.bsIdentifies}
				</td>
				<td class="tdulleft" style="width:15%">
					��վ����
				</td>
				<td class="tdulright" style="width:35%">
					<apptag:dynLabel objType="dic" id="${baseStation.bsLevel}"
							dicType="basestation_level"></apptag:dynLabel>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					��վ���ࣺ
				</td>
				<td class="tdulright" style="width:35%">
					<apptag:dynLabel objType="dic" id="${baseStation.bsSort}"
							dicType="basestation_sort"></apptag:dynLabel>
				</td>
				<td class="tdulleft" style="width:15%">
					��վ���ã�
				</td>
				<td class="tdulright" style="width:35%">
					${baseStation.bsDeploy}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					�������
				</td>
				<td class="tdulright" style="width:35%">
					${baseStation.areaType}
				</td>
				<td class="tdulleft" style="width:15%">
					�����������ͣ�
				</td>
				<td class="tdulright" style="width:35%">
					<apptag:dynLabel objType="dic"
							id="${baseStation.coverageAreaType}" dicType="overlay_area_type"></apptag:dynLabel>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					��������
				</td>
				<td class="tdulright" colspan="3" style="width:85%">
					<apptag:dynLabel objType="dic" id="${baseStation.coverageArea}"
							dicType="overlay_area"></apptag:dynLabel>
				</td>
			</tr>
			</tbody>
			<table width="40%" border="0"  style="margin-top: 6px" align="center" cellpadding="0" cellspacing="0"><tr align="center">
		      	<td>
		      	  	<input type="button" class="button" onclick="history.back()" value="����" >
		      	</td>
		    </tr></table>
		</table>
	</s:form>
</body>