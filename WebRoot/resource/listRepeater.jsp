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
		allowBlank : true,
		leafOnly : false,
		renderTo : 'combotree_patrolregiondiv',
		name : "regionname",
		hiddenName : "district",
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
	patrolregioncombotree.setComboValue("${district}","${region_name}");
});
	var win;
	function view(id) {
		window.location.href = '${ctx}/repeaterAction_view.jspx?flag=view&id=' + id;
	}
	function edit(id) {
		window.location.href = '${ctx}/repeaterAction_view.jspx?flag=edit&id=' + id;
	}
	function del(id) {
		if (confirm("删除将不能恢复，请确认是否要删除该直放站，按‘确定’删除？")) {
			window.location.href = '${ctx}/repeaterAction_delete.jspx?id=' + id;
		}
	}
	function exportForm() {
		var actionUrl = "${ctx}/repeaterAction_exportForm.jspx?exportType=repeater";
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
			title : "选择要导出的直放站属性"
		});
		win.show(Ext.getBody());
	}
	closeWin = function() {
		win.close();
	}
</script>
<template:titile value="查询直放站信息" />
<br />
<s:form action="repeaterAction_query" method="post">
	<input name="is_query" value="1" type="hidden" />
	<table width="850" border="0" align="center" cellpadding="3"
		cellspacing="0" class="tabout">
		<tr class="trwhite">
			<td class="tdulleft" style="width: 15%">
				直放站号:
			</td>
			<td class="tdulright" style="width: 35%">
				<input type="text" class="inputtext" style="width: 150px"
					name="repeaterType" value="${repeater.repeaterType}" />
			</td>
			<td class="tdulleft" style="width: 15%">
				直放站名称:
			</td>
			<td class="tdulright" style="width: 35%">
				<input type="text" class="inputtext" style="width: 150px"
					name="repeaterName" value="${repeater.repeaterName}" />
			</td>
		</tr>
		<tr class="trwhite">
			<td class="tdulleft" style="width: 15%">
				安装位置:
			</td>
			<td class="tdulright" style="width: 35%">
				<input type="text" class="inputtext" style="width: 150px"
					name="installPlace" value="${repeater.installPlace}" />
			</td>
			<td class="tdulleft" style="width: 15%">
				所在区县:
			</td>
			<td class="tdulright" style="width: 35%">
				<div id="combotree_patrolregiondiv" style="width: 180;"></div>
			</td>
		</tr>
		<!-- 
		<tr class="trwhite">
			<td class="tdulleft" style="width: 15%">
				信源基站站址编号:
			</td>
			<td class="tdulright" style="width: 35%">
				<input type="text" class="inputtext" style="width: 150px"
					name="stationCode" value="${stationCode}" />
			</td>
			<td class="tdulleft" style="width: 15%">
				信源基站名称:
			</td>
			<td class="tdulright" style="width: 35%">
				<input type="text" class="inputtext" style="width: 150px"
					name="stationName" value="${stationName}" />
			</td>
		</tr>
		 -->
	</table>
	<div align="center">
		<html:submit styleClass="button">查询</html:submit>
	</div>
</s:form>
<display:table name="sessionScope.RESULTLIST" id="row" pagesize="18"
	export="fasle" requestURI="${ctx }/repeaterAction_query.jspx">
	<display:column property="repeaterType" title="直放站号" maxLength="15"
		sortable="true" />
	<display:column property="repeaterName" title="直放站名称" maxLength="15"
		sortable="true" />
	<display:column property="city" title="所属城市" maxLength="15"
		sortable="true" />
	<display:column property="installPlace" title="安装位置" maxLength="15"
		sortable="true" />
	<display:column title="覆盖范围" maxLength="15" sortable="true">
		<apptag:dynLabel objType="dic" id="${row.coverageArea}"
			dicType="overlay_area"></apptag:dynLabel>
	</display:column>
	<display:column title="覆盖区域类型" maxLength="15" sortable="true">
		<apptag:dynLabel objType="dic" id="${row.coverageAreaType}"
			dicType="overlay_area_type"></apptag:dynLabel>
	</display:column>
	<display:column property="createTime" title="建站时间"
		format="{0,date,yyyy年MM月dd日}" maxLength="15" sortable="true" />
	<display:column title="维护单位" maxLength="15" sortable="true">
		<apptag:dynLabel objType="org" id="${row.maintenanceId}"
			dicType=""></apptag:dynLabel>
	</display:column>
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