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
	<template:titile value="小区信息" />

	<s:form action="oneStopQuick_saveCell" name="edit" method="post"
		id="addCellForm">
		<table width="850" border="0" align="center" cellpadding="3"
			cellspacing="0" class="tabout">
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					所属基站：
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="cell.parentId"
						value="${BASESTATIONS[sessionScope.baseStationId]}"
						class="inputtext" style="width: 220" maxlength="40"
						readonly="readonly" />
				</td>
				<td class="tdulleft" style="width: 15%">
					小区号：
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="cell.cellCode" value="${cell.cellCode}"
						class="inputtext required" style="width: 220" maxlength="40" />
					<span style="color: red">*</span>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					网元编号：
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="cell.netElementCode"
						value="${cell.netElementCode}" class="inputtext "
						style="width: 220" maxlength="40" />
				</td>
				<td class="tdulleft" style="width: 15%">
					网元名称：
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="cell.netElementName"
						value="${cell.netElementName }" class="inputtext "
						style="width: 220" maxlength="40" />
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					中文名称：
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="cell.chineseName"
						value="${cell.chineseName }" class="inputtext required"
						style="width: 220" maxlength="40" />
					<span style="color: red">*</span>
				</td>
				<td class="tdulleft" style="width: 15%">
					小区广播信道：
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="cell.broadcastChannel"
						value="${cell.broadcastChannel }" class="inputtext "
						style="width: 220" maxlength="40" />
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					载频数：
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="cell.carrierFrequencyNum"
						value="${cell.carrierFrequencyNum}" class="number "
						style="width: 220" maxlength="40" />
				</td>
				<td class="tdulleft" style="width: 15%">
					是否下带直放站：
				</td>
				<td class="tdulright" style="width: 35%">
					<select name="cell.isOwnRepeater" class="inputtext "
						style="width: 220">
						<option value="y">
							是
						</option>
						<option value="n">
							否
						</option>
					</select>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					是否下带塔放：
				</td>
				<td class="tdulright" style="width: 35%">
					<select name="cell.isOwnTma" class="inputtext " style="width: 220">
						<option value="y">
							是
						</option>
						<option value="n">
							否
						</option>
					</select>
				</td>
				<td class="tdulleft" style="width: 15%">
					是否开启GPRS：
				</td>
				<td class="tdulright" style="width: 35%">
					<select name="cell.isOpenGPRS" class="inputtext "
						style="width: 220">
						<option value="y">
							是
						</option>
						<option value="n">
							否
						</option>
					</select>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					是否开启EGPRS：
				</td>
				<td class="tdulright" style="width: 35%">
					<select name="cell.IS_OPEN_EGPRS" class="inputtext "
						style="width: 220">
						<option value="y">
							是
						</option>
						<option value="n">
							否
						</option>
					</select>
				</td>
				<td class="tdulleft" style="width: 15%">
					入网时间：
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
					等效全向辐射频率：
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="cell.allRoundPower"
						value="${cell.allRoundPower}" class="number " style="width: 220"
						maxlength="40" />
				</td>
				<td class="tdulleft" style="width: 15%">
					工作频段：
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="cell.bcchFreq" value="${cell.bcchFreq}"
						class="number " style="width: 220" maxlength="40" />
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					静态PDCH数：
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="cell.staticPdchNum"
						value="${cell.staticPdchNum}" class="number" style="width: 220"
						maxlength="40" />
				</td>
				<td class="tdulleft" style="width: 15%">
					动态PDCH数：
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="cell.dynamicPdchNum"
						value="${cell.dynamicPdchNum}" class="number" style="width: 220"
						maxlength="40" />
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					是否跳频：
				</td>
				<td class="tdulright" style="width: 35%">
					<select name="cell.isFrequencyHopping" class="inputtext "
						style="width: 220">
						<option value="y">
							是
						</option>
						<option value="n">
							否
						</option>
					</select>
				</td>
				<td class="tdulleft" style="width: 15%">
					跳频类型：
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="cell.frequencyHoppingType"
						value="${cell.frequencyHoppingType}" class="inputtext "
						style="width: 220" maxlength="40" />
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					归属BCF：
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="cell.attachBCF" value="${cell.attachBCF}"
						class="inputtext " style="width: 220" maxlength="40" />
				</td>
				<td class="tdulleft" style="width: 15%">
					是否边漫小区：
				</td>
				<td class="tdulright" style="width: 35%">
					<select name="cell.isBoundaryCell" class="inputtext "
						style="width: 220">
						<option value="y">
							是
						</option>
						<option value="n">
							否
						</option>
					</select>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					覆盖的对方城市及电话区号：
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
					<input type="submit" class="button" value="保存">
				</td>
				<td>
					<input type="button" class="button" onclick="back();" value="返回">
				</td>
			</tr>
		</table>
		<div align="center" style="margin-top: 20px; color: red;">
			红色*为必填项
		</div>
		<script type="text/javascript">
	function back() {
		window.location.href = "/bspweb/oneStopQuick_listCell.jspx";
	}
</script>
	</s:form>
</body>
