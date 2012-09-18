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
				alert("没有选择油机导出项！");
				return false;
			}
		}else{
			if(!exportDataItems.checked){
				alert("没有选择油机导出项！");
				return false;
			}
		}
		document.forms[0].submit();		
		return true;
	}else{
		return false;
	}
}
allSelected=function(object,itemType){
	var exportDataItems=document.forms[0].elements[itemType];
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
	<template:titile value="选择要导出的油机信息属性"/>
	<s:form action="oeAttemperTaskAction_exportOeAttemperTask" name="exportForm" method="post">
		<c:if test="${exportDataItemList!=null}">
		<table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" class="tabout">
		<input type="hidden" name="time" value="${time}"/>
		<input type="hidden" name="district" value="${district}"/>
			<tr class="trcolor">
				<td class="tdulright" colspan="5">
					断电基站属性
					<input type="checkbox" onclick="allSelected(this,'exportDataItems');">全选/全不选
				</td>
			</tr>
			<c:forEach var="oneItem" items="${exportDataItemList}" varStatus="status">
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
		<c:if test="${exportOeGenEleDataItemList!=null}">
			<tr class="trcolor">
				<td class="tdulright" colspan="5">
					油机发电属性
					<input type="checkbox" onclick="allSelected(this,'exportOeGenEleDataItems');">全选/全不选
				</td>
			</tr>
			<c:forEach var="oneItem" items="${exportOeGenEleDataItemList}" varStatus="status">
				<c:if test="${status.index%5==0}">
					<tr class="trcolor">
				</c:if>
				<td class="tdulright" style="width:20%">
					<input type="checkbox" name="exportOeGenEleDataItems" value="${oneItem.code }"/>
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
