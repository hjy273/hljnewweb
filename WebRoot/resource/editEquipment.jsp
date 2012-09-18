<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<link href="${ctx }/js/jQuery/jautocomplete/jquery.autocomplete.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${ctx}/js/jQuery/jautocomplete/jquery.autocomplete.min.js"></script>
<head>
	<script language="javascript" type="text/javascript">
	function back(){
	window.location.href='${ctx}/equipmentAction_query.jspx';
}
jQuery().ready(function() {
	jQuery("#editEquipmentForm").validate();
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
</script>
</head>
<body>
	<template:titile value="�����豸" />

	<s:form action="equipmentAction_update" name="editEquipmentForm"
		id="editEquipmentForm" method="post">
		<table width="850" border="0" align="center" cellpadding="3"
			cellspacing="0" class="tabout">
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					�豸���ͣ�
				</td>
				<td class="tdulright" style="width: 35%">
					<s:select list="%{#request.EQUIPMENTSTYPE}"
						name="equipment.equTypeId" id="equTypeId"
						value="%{#request.equipment.equTypeId}"
						cssClass="inputtext required" cssStyle="width:220"></s:select>
					<span style="color: red">*</span>
				</td>
				<td class="tdulleft" style="width: 15%">
					�豸���ƣ�
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="equipment.equName" id="equname" readonly="readonly"
						autocomplete="off" class="inputtext required"
						value="${equipment.equName}" style="width: 220" maxlength="50" />
					<span style="color: red">*</span>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					�豸���̣�
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="equipment.equProducter"
						value="${equipment.equProducter }" class="inputtext required"
						style="width: 220" maxlength="40" />
					<span style="color: red">*</span>
				</td>
				<td class="tdulleft" style="width: 15%">
					�豸�ͺţ�
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="equipment.equModel"
						value="${equipment.equModel}" class="inputtext required"
						style="width: 220" maxlength="40" />
					<span style="color: red">*</span>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					���ã�
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="equipment.equDeploy"
						value="${equipment.equDeploy }" class="inputtext"
						style="width: 220" maxlength="40" />
					<span style="color: red"><br>�����豸�����д</span>
				</td>
				<td class="tdulleft" style="width: 15%">
					������
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="equipment.equNum"
						value="${equipment.equNum}" class="inputtext required digits"
						style="width: 220" maxlength="40" />
					<span style="color: red"><br>�����豸�����д</span>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					������
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="equipment.equCapacity"
						value="${equipment.equCapacity}" class="inputtext"
						style="width: 220" maxlength="40" />
					<span style="color: red"><br>�����豸�����д</span>
				</td>
				<td class="tdulleft" style="width: 15%">
					�������ڣ�
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="equipment.equEnableDate"
						value="${equipment.equEnableDate}" readonly="readonly"
						class="Wdate inputtext required" style="width: 220px"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
					<span style="color: red">*</span>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					�����룺
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="equipment.barCode"
						value="${equipment.barCode}" class="inputtext"
						style="width: 220px" />
				</td>
				<td class="tdulleft" style="width: 15%">
					��ע��
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="equipment.remark"
						value="${equipment.remark}" class="inputtext" style="width: 220"
						maxlength="200" />
				</td>
			</tr>

		</table>
		<table width="40%" border="0" style="margin-top: 6px" align="center"
			cellpadding="0" cellspacing="0">
			<tr align="center">
				<td>
					<input type="submit" class="button" value="����">
				</td>
				<td>
					<input type="button" class="button" onclick="back()" value="����">
				</td>
			</tr>
		</table>
		<div align="center" style="margin-top: 20px; color: red;">
			��ɫ*Ϊ������
		</div>
	</s:form>
</body>
