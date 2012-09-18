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
	<template:titile value="�鿴������Ϣ" />
	<br />
	<table border="0" bgcolor="#FFFFFF" width="100%" align="center" cellpadding="0" cellspacing="0"><tr><td>
	<s:form action="outdoorFacilitiesAction_save" name="view" method="post">
		<table width="850" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
		<tr class="trwhite">
				<td class="tdulright" style="width:18%">
					������Ϣ
				</td>
				<td class="tdulright" colspan="3" style="width:82%">
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:18%">
					������վ��
				</td>
				<td class="tdulright" style="width:32%">
					${BASESTATIONS[outdoorFacilities.parentId]}
				</td>
				<td class="tdulleft" style="width:15%">
					�������ƣ�
				</td>
				<td class="tdulright" style="width:35%">
					${outdoorFacilities.towerName}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:18%">
					������ţ�
				</td>
				<td class="tdulright" style="width:32%">
					${outdoorFacilities.towerCode}
				</td>
				<td class="tdulleft" style="width:15%">
					�豸״̬��
				</td>
				<td class="tdulright" style="width:35%">
					<apptag:dynLabel objType="dic" id="${outdoorFacilities.state}" dicType="device_state"></apptag:dynLabel>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:18%">
					�������ͣ�
				</td>
				<td class="tdulright" style="width:32%">
					<apptag:dynLabel objType="dic" id="${outdoorFacilities.towerType}" dicType="TOWERTYPE"></apptag:dynLabel>
				</td>
				<td class="tdulleft" style="width:15%">
					�����߶ȣ�
				</td>
				<td class="tdulright" style="width:35%">
					${outdoorFacilities.towerHeight}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:18%">
					����ƽ̨����
				</td>
				<td class="tdulright" style="width:32%">
					${outdoorFacilities.towerPlatformNum}
				</td>
				<td class="tdulleft" style="width:15%">
					����ƽ̨����
				</td>
				<td class="tdulright" style="width:35%">
					${outdoorFacilities.usePlatformNum}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:18%">
					ԭ���ƣ�
				</td>
				<td class="tdulright" style="width:32%">
					${outdoorFacilities.oldName}
				</td>
				<td class="tdulleft" style="width:15%">
					�������ң�
				</td>
				<td class="tdulright" style="width:35%">
					${outdoorFacilities.producter}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:18%">
					����ͺţ�
				</td>
				<td class="tdulright" style="width:32%">
					${outdoorFacilities.towerMode}
				</td>
				<td class="tdulleft" style="width:15%">
					�������ʣ�
				</td>
				<td class="tdulright" style="width:35%">
					<apptag:dynLabel objType="dic" id="${outdoorFacilities.towerMaterial}" dicType="tower_material"></apptag:dynLabel>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:18%">
					����������
				</td>
				<td class="tdulright" colspan="3" style="width:82%">
					<apptag:dynLabel objType="dic" id="${outdoorFacilities.towerBase}" dicType="tower_base"></apptag:dynLabel>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulright" style="width:18%">
					Φ����Ϣ
				</td>
				<td class="tdulright" colspan="3" style="width:82%">
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:18%">
					�ݶ�Φ�����ͣ�
				</td>
				<td class="tdulright" style="width:32%">
					<apptag:dynLabel objType="dic" id="${outdoorFacilities.mastType}" dicType="mast_type"></apptag:dynLabel>
				</td>
				<td class="tdulleft" style="width:15%">
					���˸߶ȣ�
				</td>
				<td class="tdulright" style="width:35%">
					${outdoorFacilities.poleHeight}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulright" style="width:18%">
					������Ϣ
				</td>
				<td class="tdulright" colspan="3" style="width:82%"></td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:18%">
					����ʱ�䣺
				</td>
				<td class="tdulright" style="width:32%">
					 ${outdoorFacilities.anTime}
				</td>
				<td class="tdulleft" style="width:15%">
					��Ȩ���ʣ�
				</td>
				<td class="tdulright" style="width:35%">
					<apptag:dynLabel objType="dic" id="${outdoorFacilities.propertyRight}" dicType="property_right"></apptag:dynLabel>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:18%">
					�Ƿ���������Ӫ�̹���
				</td>
				<td class="tdulright" style="width:32%">
					<c:if test="${outdoorFacilities.isShare == 'y'}">��</c:if>
					<c:if test="${outdoorFacilities.isShare != 'y'}">��</c:if>
				</td>
				<td class="tdulleft" style="width:15%">
					������Ӫ�̣�
				</td>
				<td class="tdulright" style="width:35%">
					${outdoorFacilities.shareOperator}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:18%">
					�̶��ʲ���ţ�
				</td>
				<td class="tdulright" style="width:32%">
					 ${outdoorFacilities.assetCode}
				</td>
				<td class="tdulleft" style="width:15%">
					�����룺
				</td>
				<td class="tdulright" style="width:35%">
					${outdoorFacilities.barCode}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">ά���飺</td>
				<td class="tdulright" colspan="3">${outdoorFacilities.patrolGroupName }</td>
			</tr>
		</table>
		<table width="40%" border="0"  style="margin-top: 6px" align="center" cellpadding="0" cellspacing="0"><tr align="center">
				<td>
					<c:if test="${showClose=='0'}">
		      	  	<input type="button" class="button" onclick="history.back()" value="����" >
		      	  	</c:if>
		      	  	<c:if test="${showClose=='1'}">
		      	  	<input type="button" class="button" onclick="closeWin();" value="�ر�" >
		      	  	</c:if>
				</td>
		    </tr></table>
	</s:form>
	</td></tr></table>
</body>
