<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<script src="${ctx}/js/validation/prototype.js" type="text/javascript"></script>
<script src="${ctx}/js/validation/effects.js" type="text/javascript"></script>
<script src="${ctx}/js/validation/validation_cn.js" type="text/javascript"></script>
<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${ctx}/js/wdatePicker/WdatePicker.js"></script>
<link type="text/css" rel="stylesheet" href="${ctx}/js/wdatePicker/skin/WdatePicker.css">
<head>
<script language="javascript" type="text/javascript">
exports=function(){
	var exportDataItems=document.getElementById("exportForm").elements["exportDataItems"];
	if(typeof(exportDataItems)!="undefined"){
		if(typeof(exportDataItems.length)!="undefined"){
			var flag=false;
			for(i=0;i<exportDataItems.length;i++){
				if(exportDataItems[i].checked){
					flag=true;
					break;
				}
			}
			if(!flag){
				alert("没有选择铁塔导出项！");
				return false;
			}
		}else{
			if(!exportDataItems.checked){
				alert("没有选择铁塔导出项！");
				return false;
			}
		}
		document.forms[1].submit();		
		return true;
	}else{
		return false;
	}
}
allSelected=function(object,itemType){
	var exportDataItems=document.forms[1].elements[itemType];
	if(typeof(exportDataItems)!="undefined"){
		if(typeof(exportDataItems.length)!="undefined"){
			if(object.checked){
				for(i=0;i<exportDataItems.length;i++){
					exportDataItems[i].checked=true;
				}
			}else{
				for(i=0;i<exportDataItems.length;i++){
					exportDataItems[i].checked=false;
				}
			}
		}else{
			if(object.checked){
				exportDataItems.checked=true;
			}else{
				exportDataItems.checked=false;
			}
		}
	}
}
</script>
</head>
<body>
	<template:titile value="选择要导出的铁塔属性"/>
	<s:form action="outdoorFacilitiesAction_export" name="exportForm" method="post">
		<input type="hidden" name="towerName" value="${outdoorFacilities.towerName}"/> 
		<input type="hidden" name="towerCode" value="${outdoorFacilities.towerCode}"/> 
		<input type="hidden" name="oldName" value="${outdoorFacilities.oldName}"/> 
		<input type="hidden" name="parentId" value="${outdoorFacilities.parentId}"/> 
		<c:if test="${export_data_items!=null}">
		<table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" class="tabout">
			<tr class="trcolor">
				<td class="tdulright" colspan="5">
					铁塔属性
					<input type="checkbox" onclick="allSelected(this,'exportDataItems');">全选/全不选
				</td>
			</tr>
			<c:forEach var="oneItem" items="${export_data_items}" varStatus="status">
				<c:if test="${status.index%5==0}">
					<tr class="trcolor">
				</c:if>
				<td class="tdulright" style="width:20%">
					<input type="checkbox" name="exportDataItems" value="${oneItem.code }"/>
					${oneItem.itemDesc } 
				</td>
				<c:if test="${status.index%5==4}">
					</tr>
				</c:if>
				<c:if test="${status.last==true&&status.index%5==0}">
						<td class="tdulleft" style="width:20%">
						</td>
						<td class="tdulleft" style="width:20%">
						</td>
						<td class="tdulleft" style="width:20%">
						</td>
						<td class="tdulleft" style="width:20%">
						</td>
					</tr>
				</c:if>
				<c:if test="${status.last==true&&status.index%5==1}">
						<td class="tdulleft" style="width:20%">
						</td>
						<td class="tdulleft" style="width:20%">
						</td>
						<td class="tdulleft" style="width:20%">
						</td>
					</tr>
				</c:if>
				<c:if test="${status.last==true&&status.index%5==2}">
						<td class="tdulleft" style="width:20%">
						</td>
						<td class="tdulleft" style="width:20%">
						</td>
					</tr>
				</c:if>
				<c:if test="${status.last==true&&status.index%5==3}">
						<td class="tdulleft" style="width:20%">
						</td>
					</tr>
				</c:if>
			</c:forEach>
		</c:if>
		<c:if test="${export_station_data_items!=null}">
			<tr class="trcolor">
				<td class="tdulright" colspan="5">
					附带基站属性
					<input type="checkbox" onclick="allSelected(this,'exportStationDataItems');">全选/全不选
				</td>
			</tr>
			<c:forEach var="oneItem" items="${export_station_data_items}" varStatus="status">
				<c:if test="${status.index%5==0}">
					<tr class="trcolor">
				</c:if>
				<td class="tdulright" style="width:20%">
					<input type="checkbox" name="exportStationDataItems" value="${oneItem.code }"/>
					${oneItem.itemDesc } 
				</td>
				<c:if test="${status.index%5==4}">
					</tr>
				</c:if>
				<c:if test="${status.last==true&&status.index%5==0}">
						<td class="tdulleft" style="width:20%">
						</td>
						<td class="tdulleft" style="width:20%">
						</td>
						<td class="tdulleft" style="width:20%">
						</td>
						<td class="tdulleft" style="width:20%">
						</td>
					</tr>
				</c:if>
				<c:if test="${status.last==true&&status.index%5==1}">
						<td class="tdulleft" style="width:20%">
						</td>
						<td class="tdulleft" style="width:20%">
						</td>
						<td class="tdulleft" style="width:20%">
						</td>
					</tr>
				</c:if>
				<c:if test="${status.last==true&&status.index%5==2}">
						<td class="tdulleft" style="width:20%">
						</td>
						<td class="tdulleft" style="width:20%">
						</td>
					</tr>
				</c:if>
				<c:if test="${status.last==true&&status.index%5==3}">
						<td class="tdulleft" style="width:20%">
						</td>
					</tr>
				</c:if>
			</c:forEach>
			<tr class="trcolor">
				<td class="tdulright" style="text-align:center;" colspan="5">
					<input type="button" onclick="exports();" class="button" value="导出"/>
					<input type="button" onclick="closeWin();" class="button" value="关闭"/>
				</td>
			</tr>
		</table> 
		</c:if>
	</s:form>
</body>
