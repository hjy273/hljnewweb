<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<body>
	<template:titile value="�鿴�豸"/>
	<s:form action="equipmentAction_save" name="view" method="post">
		<table width="850" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					վַ��ţ�
				</td>
				<td class="tdulright" colspan="3" style="width:85%">
					${BASESTATIONS[equipment.parentId]}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					�豸���ͣ�
				</td>
				<td class="tdulright" style="width:35%">
					${EQUIPMENTSTYPE[equipment.equTypeId]}
				</td>
				<td class="tdulleft" style="width:15%">
					�豸���ƣ�
				</td>
				<td class="tdulright" style="width:35%">
					${equipment.equName}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					�豸���̣�
				</td>
				<td class="tdulright" style="width:35%">
					${equipment.equProducter}
				</td>
				<td class="tdulleft" style="width:15%">
					�豸�ͺţ�
				</td>
				<td class="tdulright" style="width:35%">
					${equipment.equModel}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					���ã�
				</td>
				<td class="tdulright" style="width:35%">
					${equipment.equDeploy}
				</td>
				<td class="tdulleft" style="width:15%">
					������
				</td>
				<td class="tdulright" style="width:35%">
					${equipment.equNum}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					������
				</td>
				<td class="tdulright" style="width:35%">
					${equipment.equCapacity}
				</td>
				<td class="tdulleft" style="width:15%">
					�������ڣ�
				</td>
				<td class="tdulright" style="width:35%">
					${equipment.equEnableDate}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					��ע��
				</td>
				<td class="tdulright" colspan="3" style="width:85%">
					${equipment.remark}
				</td>
			</tr>
			<table width="40%" border="0"  style="margin-top: 6px" align="center" cellpadding="0" cellspacing="0"><tr align="center">
		      	<td>
		      	  	<input type="button" class="button" onclick="back()" value="����" >
		      	</td>
		    </tr></table>
		</table>
	</s:form>
</body>