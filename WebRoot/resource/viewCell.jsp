<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<script language="javascript" type="text/javascript">
	var win;
	closeWin = function() {
		win.close();
	}
</script>
<body>
	<template:titile value="查看小区"/>
	<br />
	<table border="0" bgcolor="#FFFFFF" width="100%" align="center" cellpadding="0" cellspacing="0"><tr><td>
	<s:form action="cellAction_save" name="view" method="post">
		<table width="850" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					所属基站：
				</td>
				<td class="tdulright"  style="width:35%">
					${BASESTATIONS[cell.parentId]}
				</td>
				<td class="tdulleft" style="width:15%">
					小区号：
				</td>
				<td class="tdulright" style="width:35%">
					${cell.cellCode}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					网元编号：
				</td>
				<td class="tdulright" style="width:35%">
					${cell.netElementCode }
				</td>
				<td class="tdulleft" style="width:15%">
					网元名称：
				</td>
				<td class="tdulright" style="width:35%">
					${cell.netElementName }
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					中文名称：
				</td>
				<td class="tdulright" style="width:35%">
					${cell.chineseName}
				</td>
				<td class="tdulleft" style="width:15%">
					小区广播信道：
				</td>
				<td class="tdulright" style="width:35%">
					${cell.broadcastChannel }
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					载频数：
				</td>
				<td class="tdulright" style="width:35%">
					${cell.carrierFrequencyNum}
				</td>
				<td class="tdulleft" style="width:15%">
					是否下带直放站：
				</td>
				<td class="tdulright" style="width:35%">
					<c:if test="${cell.isOwnRepeater=='y'}">是</c:if>
					<c:if test="${cell.isOwnRepeater!='y'}">否</c:if>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					是否下带塔放：
				</td>
				<td class="tdulright" style="width:35%">
					<c:if test="${cell.isOwnTma=='y'}">是</c:if>
					<c:if test="${cell.isOwnTma!='y'}">否</c:if>
				</td>
				<td class="tdulleft" style="width:15%">
					是否开启GPRS：
				</td>
				<td class="tdulright" style="width:35%">
					<c:if test="${cell.isOpenGPRS=='y'}">是</c:if>
					<c:if test="${cell.isOpenGPRS!='y'}">否</c:if>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					是否开启EGPRS：
				</td>
				<td class="tdulright" style="width:35%">
					<c:if test="${cell.isOpenEGPRS=='y'}">是</c:if>
					<c:if test="${cell.isOpenEGPRS!='y'}">否</c:if>
				</td>
				<td class="tdulleft" style="width:15%">
					入网时间：
				</td>
				<td class="tdulright" style="width:35%">
					<bean:write name="cell" property="anTime" format="yyyy-MM-dd" />
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					等效全向辐射频率：
				</td>
				<td class="tdulright" style="width:35%">
					${cell.allRoundPower }
				</td>
				<td class="tdulleft" style="width:15%">
					工作频段：
				</td>
				<td class="tdulright" style="width:35%">
					${cell.bcchFreq }
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					静态PDCH数：
				</td>
				<td class="tdulright" style="width:35%">
					${cell.staticPdchNum}
				</td>
				<td class="tdulleft" style="width:15%">
					动态PDCH数：
				</td>
				<td class="tdulright" style="width:35%">
					${cell.dynamicPdchNum}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					是否跳频：
				</td>
				<td class="tdulright" style="width:35%">
					<c:if test="${cell.isFrequencyHopping=='y'}">是</c:if>
					<c:if test="${cell.isFrequencyHopping!='y'}">否</c:if>
				</td>
				<td class="tdulleft" style="width:15%">
					跳频类型：
				</td>
				<td class="tdulright" style="width:35%">
					${cell.frequencyHoppingType}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					归属BCF：
				</td>
				<td class="tdulright" style="width:35%">
					${cell.attachBCF}
				</td>
				<td class="tdulleft" style="width:15%">
					是否边漫小区：
				</td>
				<td class="tdulright" style="width:35%">
					<c:if test="${cell.isBoundaryCell=='y'}">是</c:if>
					<c:if test="${cell.isBoundaryCell!='y'}">否</c:if>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					覆盖的对方城市及电话区号：
				</td>
				<td class="tdulright" colspan="3" style="width:85%">
					${cell.coverThing}
				</td>
			</tr>
			
			
		</table>
		<table width="40%" border="0"  style="margin-top: 6px" align="center" cellpadding="0" cellspacing="0"><tr align="center">
		      	<td>
		      	  	<c:if test="${showClose=='0'}">
		      	  	<input type="button" class="button" onclick="history.back()" value="返回" >
		      	  	</c:if>
		      	  	<c:if test="${showClose=='1'}">
		      	  	<input type="button" class="button" onclick="closeWin();" value="关闭" >
		      	  	</c:if>
		      	</td>
			</tr></table>
	</s:form>
	</td></tr></table>
</body>