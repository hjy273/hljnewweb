<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<head>
	<script language="javascript" type="">
	Ext.onReady(function(){
		jQuery("#editCellForm").validate();
	});
	function back(){
		window.location.href='${ctx}/cellAction_query.jspx';
	}
</script>
</head>
<body>
	<template:titile value="更新小区" />

	<s:form action="cellAction_update" name="editCellForm" method="post"
		id="editCellForm">
		<table width="850" border="0" align="center" cellpadding="3"
			cellspacing="0" class="tabout">
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					所属基站：
				</td>
				<td class="tdulright" style="width: 35%">
					<s:select list="%{#request.BASESTATIONS}"
						value="%{#request.cell.parentId}" name="cell.parentId"
						cssClass="inputtext  validate-select-one" cssStyle="width:220"></s:select>
					<span style="color: red">*</span>
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
						<option <c:if test="${cell.isOwnRepeater == 'y'}">selected</c:if>
							value="y">
							是
						</option>
						<option <c:if test="${cell.isOwnRepeater == 'n'}">selected</c:if>
							value="n">
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
						<option <c:if test="${cell.isOwnTma == 'y'}">selected</c:if>
							value="y">
							是
						</option>
						<option <c:if test="${cell.isOwnTma == 'n'}">selected</c:if>
							value="n">
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
						<option <c:if test="${cell.isOpenGPRS == 'y'}">selected</c:if>
							value="y">
							是
						</option>
						<option <c:if test="${cell.isOpenGPRS == 'n'}">selected</c:if>
							value="n">
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
					<select name="cell.isOpenEGPRS" class="inputtext "
						style="width: 220">
						<option <c:if test="${cell.isOpenEGPRS == 'y'}">selected</c:if>
							value="y">
							是
						</option>
						<option <c:if test="${cell.isOpenEGPRS == 'n'}">selected</c:if>
							value="n">
							否
						</option>
					</select>
				</td>
				<td class="tdulleft" style="width: 15%">
					入网时间：
				</td>
				<td class="tdulright" style="width: 35%">
					<fmt:formatDate pattern="yyyy-MM-dd" value="${cell.anTime}"
						var="anTime" />
					<input type="text" name="cell.anTime" value="${anTime }"
						class="Wdate inputtext required" readonly="readonly"
						style="width: 220" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
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
						<option
							<c:if test="${cell.isFrequencyHopping == 'y'}">selected</c:if>
							value="y">
							是
						</option>
						<option
							<c:if test="${cell.isFrequencyHopping == 'n'}">selected</c:if>
							value="n">
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
						<option <c:if test="${cell.isBoundaryCell == 'y'}">selected</c:if>
							value="y">
							是
						</option>
						<option <c:if test="${cell.isBoundaryCell == 'n'}">selected</c:if>
							value="n">
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
					<input type="submit" class="button" value="更新">
				</td>
				<td>
					<input type="button" class="button" onclick="back();" value="返回">
				</td>
			</tr>
		</table>
		<div align="center" style="margin-top: 20px; color: red;">
			红色*为必填项
		</div>
	</s:form>
</body>
