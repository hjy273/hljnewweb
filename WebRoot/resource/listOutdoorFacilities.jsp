<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<script src="${ctx}/js/extjs/ux/ux-all-debug.js" type="text/javascript"></script>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
	media="screen, print" />
<script language="javascript" type="text/javascript">
//所属区县
var patrolregioncombotree;
Ext.onReady(function() {
	patrolregioncombotree = new TreeComboField({
		width : 150,
		height : 100,
		renderTo : 'combotree_patrolregiondiv',
		name : "regionname",
		hiddenName : "district",
		displayField : 'text',
		valueField : 'id',
		tree : new Ext.tree.TreePanel({
			autoScroll : true,
			rootVisible : false,
			root : new Ext.tree.AsyncTreeNode({
				id : '000000',
				loader : new Ext.tree.TreeLoader({
					dataUrl : '${ctx}/common/externalresources_getRegionJson.jspx'
				})

			})
		})
	});
	patrolregioncombotree.setComboValue("${district}","${region_name}");
});
	var win;
	function view(id) {
		window.location.href = '${ctx}/outdoorFacilitiesAction_view.jspx?flag=view&id=' + id;
	}
	function edit(id) {
		window.location.href = '${ctx}/outdoorFacilitiesAction_view.jspx?flag=edit&id=' + id;
	}
	function del(id) {
		if (confirm("删除将不能恢复，请确认是否要删除该铁塔，按‘确定’删除？")) {
			window.location.href = '${ctx}/outdoorFacilitiesAction_delete.jspx?id=' + id;
		}
	}
	function exportForm() {
		var actionUrl = "${ctx}/outdoorFacilitiesAction_exportForm.jspx?exportType=outdoor_facility";
		//window.open(actionUrl);
		win = new Ext.Window( {
			layout : 'fit',
			width : 650,
			height : 400,
			resizable : true,
			closeAction : 'close',
			modal : true,
			autoScroll : true,
			autoLoad : {
				url : actionUrl,
				scripts : true
			},
			plain : true,
			title : "选择要导出的铁塔属性"
		});
		win.show(Ext.getBody());
	}
	closeWin = function() {
		win.close();
	}
</script>
<template:titile value="查询铁塔信息" />
<br />
<s:form action="outdoorFacilitiesAction_query" method="post">
	<input name="is_query" value="1" type="hidden" />
	<table width="850" border="0" align="center" cellpadding="3"
		cellspacing="0" class="tabout">
		<tr class="trwhite">
			<td class="tdulleft" style="width: 15%">
				铁塔编号:
			</td>
			<td class="tdulright" style="width: 35%">
				<input type="text" class="inputtext" style="width: 150px"
					name="towerCode" value="${outdoorFacilities.towerCode}" />
			</td>
			<td class="tdulleft" style="width: 15%">
				铁塔名称:
			</td>
			<td class="tdulright" style="width: 35%">
				<input type="text" class="inputtext" style="width: 150px"
					name="towerName" value="${outdoorFacilities.towerName}" />
			</td>
		</tr>
		<tr class="trwhite">
			<td class="tdulleft" style="width: 15%">
				原名称:
			</td>
			<td class="tdulright" style="width: 35%">
				<input type="text" class="inputtext" style="width: 150px"
					name="oldName" value="${outdoorFacilities.oldName}" />
			</td>
			<td class="tdulleft" style="width: 15%">
				所在区县:
			</td>
			<td class="tdulright" style="width: 35%">
				<div id="combotree_patrolregiondiv" style="width: 180;"></div>
			</td>
		</tr>
		<tr class="trwhite">
			<td class="tdulleft" style="width: 15%">
				所属基站站址编号:
			</td>
			<td class="tdulright" style="width: 35%">
				<input type="text" class="inputtext" style="width: 150px"
					name="stationCode" value="${parentId}" />
			</td>
			<td class="tdulleft" style="width: 15%">
				所属基站名称:
			</td>
			<td class="tdulright" style="width: 35%">
				<input type="text" class="inputtext" style="width: 150px"
					name="stationName" value="${stationName}" />
			</td>
		</tr>
	</table>
	<div align="center">
		<html:submit styleClass="button">查询</html:submit>
	</div>
</s:form>
<display:table name="sessionScope.RESULTLIST" id="row" pagesize="18"
	export="fasle" requestURI="${ctx }/outdoorFacilitiesAction_query.jspx">
	<display:column title="所属基站" maxLength="15" sortable="true">
		<c:out value="${sessionScope.BASESTATIONS[row.parent_id]}" />
	</display:column>
	<display:column title="铁塔编号 " property="tower_code" maxLength="15"
		sortable="true" />
	<display:column title="铁塔名称 " property="tower_name" maxLength="15"
		sortable="true" />
	<display:column title="铁塔类型" maxLength="15" sortable="true">
		<apptag:dynLabel objType="dic" id="${row.tower_type}"
			dicType="TOWERTYPE"></apptag:dynLabel>
	</display:column>
	<display:column property="tower_height" title="铁塔高度" maxLength="15"
		sortable="true" />
	<display:column property="tower_platform_num" title="铁塔平台数"
		maxLength="15" sortable="true" />
	<display:column title="屋顶桅杆类型" maxLength="15" sortable="true">
		<apptag:dynLabel objType="dic" id="${row.mast_type}"
			dicType="mast_type"></apptag:dynLabel>${row.mast_type}
	</display:column>
	<display:column property="pole_height" title="抱杆高度" maxLength="15"
		sortable="true" />
	<display:column title="已用平台数" property="use_platform_num" maxLength="15"
		sortable="true" />
	<display:column title="入网时间 " property="an_time" maxLength="15"
		sortable="true" />
	<display:column media="html" title="操作" paramId="tid">
		<apptag:privilege operation="view">
			<a href="javascript:view('${row.id}')">查看</a>
		</apptag:privilege>
		<apptag:privilege operation="edit">
			<a href="javascript:edit('${row.id}')">修改</a>
		</apptag:privilege>
		<apptag:privilege operation="del">
			<a href="javascript:del('${row.id}')">删除</a>
		</apptag:privilege>
	</display:column>
</display:table>
<div align="left">
	<a href="javascript:exportForm();">导出Excel文件</a>
</div>