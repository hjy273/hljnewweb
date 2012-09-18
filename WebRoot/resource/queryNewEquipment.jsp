<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script src="${ctx}/js/extjs/ux/ux-all-debug.js" type="text/javascript"></script>
<script language="JavaScript" src="${ctx}/js/dhtmlxTree/dhtmlxcommon.js"></script>
<script language="JavaScript" src="${ctx}/js/dhtmlxTree/dhtmlxtree.js"></script>
<script language="JavaScript" src="${ctx}/js/dhtmlxTree/ext/dhtmlxtree_json.js"></script>

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
<script type="text/javascript">
Ext.onReady(function() {
     patrolregioncombotree = new TreeComboField({
		width : 180,
		height : 100,
		allowBlank : true,
		renderTo : 'combotree_regionid',
		name : "regionname",
		hiddenName : "districtId",
		cls:"required",
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
})
</script>
<head>
	
</head>
<body>
	<template:titile value="查询新增设备${districtId}" />
	<br />
<s:form action="equipmentAction_queryEqu.jspx" method="post">
	<template:formTable contentwidth="200" namewidth="200">
		<template:formTr name="所属区县" isOdd="false">
			<div id="combotree_regionid" style="width: 180;"></div>
		</template:formTr>
		<template:formTr name="查询时间区间">
			<input name="type" value="new" type="hidden" />
			<input name="startTime" class="inputtext" style="width: 100px" 
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly />
					-
					<input name="endTime" class="inputtext" style="width: 100px" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startTime\')}'})" readonly />
		</template:formTr>		
		<template:formSubmit>
			<td>
				<html:submit styleClass="button">查询</html:submit>
			</td>
			<td>
				<input type="button" class="button" onclick=history.back(); value="返回">
			</td>
		</template:formSubmit>
	</template:formTable>
</s:form>
</body>
