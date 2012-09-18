<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<script type="text/javascript" src="${ctx}/js/wdatePicker/WdatePicker.js"></script>
<link type="text/css" rel="stylesheet" href="${ctx}/js/wdatePicker/skin/WdatePicker.css">
<head>
<script language="javascript" type="">

</script>
</head>
<body>
	<template:titile value="查看断电基站统计信息"/>
	<br />
		<table width="850" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					断电基站：
				</td>
				<td class="tdulright"  style="width:35%">
					${OEATTEMPERTASK.stationName}
				</td>
				<td class="tdulleft" style="width:15%">
					断电时间：
				</td>
				<td class="tdulright"  style="width:35%">
					${OEATTEMPERTASK.blackoutTime}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					断电原因：
				</td>
				<td class="tdulright" style="width:35%">
					${OEATTEMPERTASK.blackoutReason}
				</td>
				<td class="tdulleft" style="width:15%">
					油机编号：
				</td>
				<td class="tdulright" style="width:35%">
					${OEATTEMPERTASK.oilEngineCode}
				</td>
			</tr>
			<tr class="trwhite">
				
				<td class="tdulleft" style="width:15%">
					原来维护点：
				</td>
				<td class="tdulright" style="width:35%">
					${OEATTEMPERTASK.perStationName}
				</td>
				<td class="tdulleft" style="width:15%">
					调动人：
				</td>
				<td class="tdulright" style="width:35%">
					${OEATTEMPERTASK.yardMan}
				</td>
			</tr>
			<tr class="trwhite">
				
				<td class="tdulleft" style="width:15%">
					调动时间：
				</td>
				<td class="tdulright" style="width:35%">
					${OEATTEMPERTASK.shiftTime}
				</td>
				<td class="tdulleft" style="width:15%">
					油机响应时间：
				</td>
				<td class="tdulright" style="width:35%">
					${OEATTEMPERTASK.responseTime}
				</td>
			</tr>
		</table>
		<br />
		<div align="center">
			断电基站油机发电信息
		</div>
		<br />
		<table width="850" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					油机编号：
				</td>
				<td class="tdulright" style="width:35%">
					${OEGENELE.oilEngineCode}
				</td>
				<td class="tdulleft" style="width:15%">
					断电基站：
				</td>
				<td class="tdulright" style="width:35%">
					${OEGENELE.stationName}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					出发经度：
				</td>
				<td class="tdulright" style="width:35%">
					${OEGENELE.startLongitude}
				</td>
				<td class="tdulleft" style="width:15%">
					出发纬度：
				</td>
				<td class="tdulright" style="width:35%">
					${OEGENELE.startLatitude}
				</td>
			</tr>
			<tr class="trwhite">
				
				<td class="tdulleft" style="width:15%">
					出发时间：
				</td>
				<td class="tdulright" style="width:35%">
					${OEGENELE.setoutTime}
				</td>
				<td class="tdulleft" style="width:15%">
					发电时间：
				</td>
				<td class="tdulright" style="width:35%">
					${OEGENELE.genEleTime}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					开始度数：
				</td>
				<td class="tdulright" style="width:35%">
					${OEGENELE.startDegree}  度
				</td>
				<td class="tdulleft" style="width:15%">
					结束度数：
				</td>
				<td class="tdulright" style="width:35%">
					${OEGENELE.endDegree}  度
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					结束时间：
				</td>
				<td class="tdulright" style="width:35%">
					${OEGENELE.endTime}
				</td>
				<td class="tdulleft" style="width:15%">
					油耗量：
				</td>
				<td class="tdulright" style="width:35%">
					${OEGENELE.consumeQuantity}  升
				</td>
			</tr>
			
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					到达经度：
				</td>
				<td class="tdulright" style="width:35%">
					${OEGENELE.endLongitude}
				</td>
				<td class="tdulleft" style="width:15%">
					到达纬度：
				</td>
				<td class="tdulright" style="width:35%">
					${OEGENELE.endLatitude}
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					发电量：
				</td>
				<td class="tdulright" style="width:35%">
					${OEGENELE.endDegree-OEGENELE.startDegree}  度
				</td>
				<td class="tdulleft" style="width:15%">
					发电时间：
				</td>
				<td class="tdulright" style="width:35%">
					${OEGENELE.eleTime}  分钟
				</td>
			</tr>
			
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					油机入库时间：
				</td>
				<td class="tdulright" style="width:35%">
					${OEGENELE.restoreTime}
				</td>
				<td class="tdulleft" style="width:15%">
					描述：
				</td>
				<td class="tdulright" style="width:35%">
					${OEGENELE.remark}
				</td>
			</tr>
		</table>
		<br />
		      	<div align="center">
		      	  	<input type="button" class="button" onclick="history.back()" value="返回" >
		      	</div>
</body>
