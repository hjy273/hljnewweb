<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">
<head>
</head>
<body>
	<template:titile value="查看室内分布信息"/>
	<s:form action="/res/indoorOverRideAction_update.jspx" name="addantennaFrom" method="post">
		<input type="hidden" name="ior.id" value="${ior.id}"/>
		<input type="hidden" name="ior.pointId" value="${ior.pointId}"/>
		<table width="850" border="0" id="_table" align="center" cellpadding="3" cellspacing="0" class="tabout">
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					分布系统名称：
				</td>
				<td class="tdulright" style="width:35%">
					${ior.distributeSystem}
				</td>
				<td class="tdulleft" style="width:15%">
					维护组：
				</td>
				<td class="tdulright" style="width:35%" >
					${patrolManName}
				</td>
			</tr>
			
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					区域：
				</td>
				<td class="tdulright" style="width:35%">
					<apptag:dynLabel objType="region" id="${ior.regionId}" dicType=""></apptag:dynLabel>
				</td>
				<td class="tdulleft" style="width:15%">
					合成方式：
				</td>
				<td class="tdulright" style="width:35%">
				    <apptag:dynLabel objType="dic" id="${ior.blending}" dicType="blending_type" ></apptag:dynLabel>
				</td>
			</tr>

			<tr class="trwhite">	
				<td class="tdulleft" style="width:15%">
					入网时间：
				</td>
				<td class="tdulright" style="width:35%">
				    ${ior.createtime}
				</td>
				<td class="tdulleft" style="width:15%">
					所属站号：
				</td>
				<td class="tdulright" style="width:35%">
					${ior.stationCode}
				</td>
			</tr>
			<tr class="trwhite">
					<td class="tdulleft" style="width: 15%">
						经纬度：
					</td>
					<td class="tdulright" colspan="3" style="width: 85%">
						${position}
					</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					覆盖区域：
				</td>
				<td class="tdulright" style="width:35%">
					<apptag:dynLabel objType="dic" id="${ior.coverageArea}" dicType="overlay_area" ></apptag:dynLabel>
				</td>
				<td class="tdulleft" style="width:15%">
					覆盖类型:
				</td>
				<td class="tdulright" style="width:35%">
					 <apptag:dynLabel objType="dic" id="${ior.coverageAreaType}" dicType="overlay_area_type" ></apptag:dynLabel>
				</td>
			</tr>
			
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					信源方式：
				</td>
				<td class="tdulright" style="width:35%">
					${ior.source}
				</td>
				<td class="tdulleft" style="width:15%">
					楼层数：
				</td>
				<td class="tdulright" style="width:35%">
					${ior.floorCount}
				</td>
			</tr>
			
			
			<tr class="trwhite">
				<td class="tdulleft" style="width:15%">
					地址：
				</td>
				<td class="tdulright" colspan="3" style="width:85%">
					${ior.address}
				</td>
			</tr>


		
		<tr class="trwhite">
				<td colspan="4" class="tdulright" style="width:15%">
					巡检位置信息：
				</td>
		</tr>
		
		<c:forEach var="item" items="${patrolAddressInfos}" varStatus="stauts">
               <tr class="trwhite" id="rfid_row_${stauts.index}">
				<td class="tdulleft" style="width:15%">
					射频卡编号：
				</td>
				<td class="tdulright" style="width:35%">
					${item.rfid}
				</td>
				<td class="tdulleft" style="width:15%">
					位置描述：
				</td>
				<td class="tdulright">
					${item.address}
				</td>
			</tr>
            </c:forEach>
			

		</table>
			<table width="40%" border="0"  style="margin-top: 6px" align="center" cellpadding="0" cellspacing="0"><tr align="center">
		      	<td>
		      	  	<input type="button" class="button" onclick="history.back()" value="返回" >
		      	</td>
		    </tr>
		    </table>
	</s:form>
</body>
