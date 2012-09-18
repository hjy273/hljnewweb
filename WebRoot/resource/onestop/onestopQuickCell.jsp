<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<head>
	<script language="javascript" type="">
	Ext.onReady(function(){
		jQuery("#addCellForm").validate();
	});
</script>
</head>
<body>
	<template:titile value="С����Ϣ" />

	<s:form action="oneStopQuick_saveCell" name="edit" method="post"
		id="addCellForm">
		<table width="850" border="0" align="center" cellpadding="3"
			cellspacing="0" class="tabout">
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					������վ��
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="cell.parentId"
						value="${BASESTATIONS[sessionScope.baseStationId]}"
						class="inputtext" style="width: 220" maxlength="40"
						readonly="readonly" />
				</td>
				<td class="tdulleft" style="width: 15%">
					С���ţ�
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="cell.cellCode" value="${cell.cellCode}"
						class="inputtext required" style="width: 220" maxlength="40" />
					<span style="color: red">*</span>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					��Ԫ��ţ�
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="cell.netElementCode"
						value="${cell.netElementCode}" class="inputtext "
						style="width: 220" maxlength="40" />
				</td>
				<td class="tdulleft" style="width: 15%">
					��Ԫ���ƣ�
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="cell.netElementName"
						value="${cell.netElementName }" class="inputtext "
						style="width: 220" maxlength="40" />
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					�������ƣ�
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="cell.chineseName"
						value="${cell.chineseName }" class="inputtext required"
						style="width: 220" maxlength="40" />
					<span style="color: red">*</span>
				</td>
				<td class="tdulleft" style="width: 15%">
					С���㲥�ŵ���
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="cell.broadcastChannel"
						value="${cell.broadcastChannel }" class="inputtext "
						style="width: 220" maxlength="40" />
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					��Ƶ����
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="cell.carrierFrequencyNum"
						value="${cell.carrierFrequencyNum}" class="number "
						style="width: 220" maxlength="40" />
				</td>
				<td class="tdulleft" style="width: 15%">
					�Ƿ��´�ֱ��վ��
				</td>
				<td class="tdulright" style="width: 35%">
					<select name="cell.isOwnRepeater" class="inputtext "
						style="width: 220">
						<option value="y">
							��
						</option>
						<option value="n">
							��
						</option>
					</select>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					�Ƿ��´����ţ�
				</td>
				<td class="tdulright" style="width: 35%">
					<select name="cell.isOwnTma" class="inputtext " style="width: 220">
						<option value="y">
							��
						</option>
						<option value="n">
							��
						</option>
					</select>
				</td>
				<td class="tdulleft" style="width: 15%">
					�Ƿ���GPRS��
				</td>
				<td class="tdulright" style="width: 35%">
					<select name="cell.isOpenGPRS" class="inputtext "
						style="width: 220">
						<option value="y">
							��
						</option>
						<option value="n">
							��
						</option>
					</select>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					�Ƿ���EGPRS��
				</td>
				<td class="tdulright" style="width: 35%">
					<select name="cell.IS_OPEN_EGPRS" class="inputtext "
						style="width: 220">
						<option value="y">
							��
						</option>
						<option value="n">
							��
						</option>
					</select>
				</td>
				<td class="tdulleft" style="width: 15%">
					����ʱ�䣺
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="cell.anTime"
						value="<bean:write name="cell" property="anTime" format="yyyy-MM-dd" />"
						class="Wdate inputtext required" style="width: 220"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
					<span style="color: red">*</span>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					��Чȫ�����Ƶ�ʣ�
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="cell.allRoundPower"
						value="${cell.allRoundPower}" class="number " style="width: 220"
						maxlength="40" />
				</td>
				<td class="tdulleft" style="width: 15%">
					����Ƶ�Σ�
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="cell.bcchFreq" value="${cell.bcchFreq}"
						class="number " style="width: 220" maxlength="40" />
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					��̬PDCH����
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="cell.staticPdchNum"
						value="${cell.staticPdchNum}" class="number" style="width: 220"
						maxlength="40" />
				</td>
				<td class="tdulleft" style="width: 15%">
					��̬PDCH����
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="cell.dynamicPdchNum"
						value="${cell.dynamicPdchNum}" class="number" style="width: 220"
						maxlength="40" />
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					�Ƿ���Ƶ��
				</td>
				<td class="tdulright" style="width: 35%">
					<select name="cell.isFrequencyHopping" class="inputtext "
						style="width: 220">
						<option value="y">
							��
						</option>
						<option value="n">
							��
						</option>
					</select>
				</td>
				<td class="tdulleft" style="width: 15%">
					��Ƶ���ͣ�
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="cell.frequencyHoppingType"
						value="${cell.frequencyHoppingType}" class="inputtext "
						style="width: 220" maxlength="40" />
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					����BCF��
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="cell.attachBCF" value="${cell.attachBCF}"
						class="inputtext " style="width: 220" maxlength="40" />
				</td>
				<td class="tdulleft" style="width: 15%">
					�Ƿ����С����
				</td>
				<td class="tdulright" style="width: 35%">
					<select name="cell.isBoundaryCell" class="inputtext "
						style="width: 220">
						<option value="y">
							��
						</option>
						<option value="n">
							��
						</option>
					</select>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					���ǵĶԷ����м��绰���ţ�
				</td>
				<td class="tdulright" colspan="3" style="width: 85%">
					<textarea name="cell.coverThing" class="inputtext "
						style="width: 220">${cell.coverThing}</textarea>
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
					<input type="button" class="button" onclick="back();" value="����">
				</td>
			</tr>
		</table>
		<div align="center" style="margin-top: 20px; color: red;">
			��ɫ*Ϊ������
		</div>
		<script type="text/javascript">
	function back() {
		window.location.href = "/bspweb/oneStopQuick_listCell.jspx";
	}
</script>
	</s:form>
</body>
