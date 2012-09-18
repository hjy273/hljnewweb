<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script src="${ctx}/js/extjs/ux/ux-all-debug.js" type="text/javascript"></script>
<script src="${ctx}/js/extjs/ux/Appcombox.js" type="text/javascript"></script>
<script language="javascript" type="text/javascript">
//所属区县
var patrolregioncombotree;
Ext.onReady(function() {
	patrolregioncombotree = new TreeComboField({
		width : 220,
		height : 100,
		renderTo : 'combotree_patrolregiondiv',
		name : "regionname",
		hiddenName : "district",
		displayField : 'text',
		
		valueField : 'id',
		tree : new Ext.tree.TreePanel({
			autoScroll : true,
			rootVisible : false,
			autoHeight:true,
			root : new Ext.tree.AsyncTreeNode({
				id : '000000',
				loader : new Ext.tree.TreeLoader({
					dataUrl : '${ctx}/common/externalresources_getRegionJson.jspx'
				})

			})
		})
	});
		//油机类型	oilEngine.oilEngineType		OIL_ENGINE_TYPE		oilEngine_OIL_ENGINE_TYPE
		var oilEngine_OIL_ENGINE_TYPE = new Appcombox({
			hiddenName : 'oilEngine.oilEngineType',
			emptyText : '不限',
			dataUrl : '${ctx}/common/externalresources_getDictionaryJson.jspx?type=OIL_ENGINE_TYPE',
	   		dataCode : 'CODEVALUE',
	   		dataText : 'LABLE',
			allowBlank:true,
			renderTo: 'oilEngine_OIL_ENGINE_TYPE'
		});	
		//油料类型	oilEngine.oilType		OIL_TYPE		oilEngine_OIL_TYPE
		var oilEngine_OIL_TYPE = new Appcombox({
			hiddenName : 'oilEngine.oilType',
			emptyText : '不限',
			dataUrl : '${ctx}/common/externalresources_getDictionaryJson.jspx?type=OIL_TYPE',
	   		dataCode : 'CODEVALUE',
	   		dataText : 'LABLE',
			allowBlank:true,
			renderTo: 'oilEngine_OIL_TYPE'
		});	
});
</script>

<script language="JavaScript" src="${ctx}/js/dhtmlxTree/dhtmlxcommon.js"></script>
<script language="JavaScript" src="${ctx}/js/dhtmlxTree/dhtmlxtree.js"></script>
<script language="JavaScript"
	src="${ctx}/js/dhtmlxTree/ext/dhtmlxtree_json.js"></script>

<style type="text/css">
.input_line {
	BORDER-TOP-STYLE: none;
	BORDER-BOTTOM: #cccccc 1px solid;
	BORDER-RIGHT-STYLE: none;
	BORDER-LEFT-STYLE: none;
	TEXT-ALIGN: center;
	COLOR: RED
}
</style>

<head>
	<script language="javascript">
	
</script>
</head>
<body>
	<template:titile value="查询油机信息" />
	<br />
	<s:form action="oilEngineAction_list" name="form" method="post">
		<table width="850" border="0" align="center" cellpadding="3"
			cellspacing="0" class="tabout">
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					所属区县：
				</td>
				<td class="tdulright" style="width: 35%">
					<div id="combotree_patrolregiondiv" style="width: 220;"></div>
				</td>
				<td class="tdulleft" style="width: 15%">
					油机编码：
				</td>
				<td class="tdulright" style="width: 35%">
					<input type="text" name="oilEngine.oilEngineCode"
						style="width: 220px"></input>
				</td>
			</tr>
			<tr class="trwhite">
				<td class="tdulleft" style="width: 15%">
					油机类型：
				</td>
				<td class="tdulright" style="width: 35%">
					<div id="oilEngine_OIL_ENGINE_TYPE"></div>
				</td>
				<td class="tdulleft" style="width: 15%">
					油料类型：
				</td>
				<td class="tdulright" style="width: 35%">
					<div id="oilEngine_OIL_TYPE"></div>
				</td>
			</tr>
		</table>
		<br />
		<div align="center">
			<html:submit styleClass="button">查询</html:submit>
		</div>
	</s:form>
</body>