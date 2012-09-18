<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
	media="screen, print" />
<script src="${ctx}/js/extjs/ux/ux-all-debug.js" type="text/javascript"></script>
<script src="${ctx}/js/extjs/ux/Appcombox.js" type="text/javascript"></script>
<script language="javascript" type="text/javascript">
//所属区县
var patrolregioncombotree;
Ext.onReady(function() {
	patrolregioncombotree = new TreeComboField({
		width : 160,
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
					dataUrl : '${ctx}/common/externalresources_getRegionJson.jspx?rnd='+Math.random()
				})
			})
		})
	});
	patrolregioncombotree.setComboValue("${district}",'<apptag:dynLabel objType="region" id="${district}" dicType=""></apptag:dynLabel>');
		//基站级别	baseStation.bsLevel				basestation_level	basestaion_basestation_level
		var  basestation_level = new Appcombox({
			width : 160,
			hiddenName : 'bsLevel',
			emptyText : '请选择',
			dataUrl : '${ctx}/common/externalresources_getDictionaryBlankJson.jspx?type=basestation_level',
	   		dataCode : 'CODEVALUE',
	   		dataText : 'LABLE',
			allowBlank:true,
			renderTo: 'basestaion_basestation_level'
		});
		basestation_level.setComboValue('${bsLevel}','<apptag:dynLabel objType="dic" id="${bsLevel}" dicType="basestation_level"></apptag:dynLabel>');
});
	var win;
	function view(id) {
		window.location.href = '${ctx}/baseStationAction_view.jspx?flag=view&id=' + id;
	}
	function edit(id) {
		window.location.href = '${ctx}/baseStationAction_view.jspx?flag=edit&id=' + id;
	}
	function del(id) {
		if (confirm("删除将不能恢复，请确认是否要删除该基站，按‘确定’删除？")) {
			window.location.href = '${ctx}/baseStationAction_delete.jspx?id=' + id;
		}
	}
	function exportForm() {
		var actionUrl = "${ctx}/baseStationAction_exportForm.jspx?exportType=station&&stationCode="
				+ $('stationCode').value
				+ "&bsLevel="
				+ $('bsLevel').value
				+ "&district="
				+ $('district').value
				+ "&address="
				+ $('address').value;
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
			title : "选择要导出的基站属性"
		});
		win.show(Ext.getBody());
	}
	closeWin = function() {
		win.close();
	}
	function vrp(siteid){
		var url="/vrp/display/exhibit.action?siteid="+siteid+"&monitor=true&winwidth=300&winheight=200&wintop=0&winright=200;";
		window.open(url);
	}
	function oneStopEdit(id) {
		window.location.href = '${ctx}/oneStopQuick_input.jspx?id=' + id;
	}
	function oneStopView(id) {
		//window.location.href='${ctx}/oneStopQuick_input.jspx?flag=view&id='+id;
		window.location.href = '${ctx}/baseStationAction_view.jspx?flag=view&id=' + id;
	}
</script>
<template:titile value="基站信息查询" />
<br />
<s:form action="baseStationAction_query" method="post">
	<input name="is_query" value="1" type="hidden" />
	<table width="850" border="0" align="center" cellpadding="3"
		cellspacing="0" class="tabout">
		<tr class="trwhite">
			<td class="tdulleft" style="width: 15%">
				站址编号:
			</td>
			<td class="tdulright" style="width: 35%">
				<input type="text" class="inputtext" style="width: 150px"
					name="stationCode" value="${baseStation.stationCode}" />
			</td>
			<td class="tdulleft" style="width: 15%">
				基站名称:
			</td>
			<td class="tdulright" style="width: 35%">
				<input type="text" class="inputtext" style="width: 150px"
					name="stationName" value="${baseStation.stationName}" />
			</td>
		</tr>
		<tr class="trwhite">
			<td class="tdulleft" style="width: 15%">
				基站地址:
			</td>
			<td class="tdulright" style="width: 35%">
				<input type="text" class="inputtext" style="width: 150px"
					name="address" alt="支持模糊搜索" value="${baseStation.address}" />
			</td>
			<td class="tdulleft" style="width: 15%">
				基站级别:
			</td>
			<td class="tdulright" style="width: 35%">
				<div id="basestaion_basestation_level"></div>
			</td>
		</tr>
		<tr class="trwhite">
			<td class="tdulleft" style="width: 15%">
				所属区县:
			</td>
			<td class="tdulright" colspan="3" style="width: 85%">
				<div id="combotree_patrolregiondiv" style="width: 180;"></div>
			</td>
		</tr>
	</table>
	<div align="center">
		<html:submit styleClass="button">查询</html:submit>
	</div>
</s:form>
<display:table name="sessionScope.RESULTLIST" id="row" pagesize="18"
	export="fasle" requestURI="${ctx }/baseStationAction_query.jspx">
	<display:column property="stationCode" title="站址编号" maxLength="15"
		sortable="true" />
	<display:column property="stationName" title="基站名称" maxLength="15"
		sortable="true" />
	<display:column title="基站级别" maxLength="15" sortable="true">
		<apptag:dynLabel objType="dic" id="${row.bsLevel}"
			dicType="basestation_level"></apptag:dynLabel>
	</display:column>
	<display:column media="html" title="区县" sortable="true">
		<c:set var="key" value="${row.district}"></c:set>
		<c:out value="${DISTRICTS[key]}"></c:out>
	</display:column>
	<display:column property="anTime" title="入网时间"
		format="{0,date,yyyy年MM月dd日}" maxLength="15" sortable="true" />
	<display:column media="html" title="维护单位" sortable="true">
		<c:out value="${CONTRACTORS[row.maintenanceId]}"></c:out>
	</display:column>
	<display:column media="html" title="操作" paramId="tid">
		<!-- 
		<a href="javascript:vrp('${row.stationCode }')">虚拟全景</a>
		 -->
		<apptag:privilege operation="view">
			<a href="javascript:view('${row.id}')">查看</a>
		</apptag:privilege>
		<apptag:privilege operation="edit">
			<a href="javascript:edit('${row.id}')">修改</a>
		</apptag:privilege>
		<apptag:privilege operation="del">
			<a href="javascript:del('${row.id}')">删除</a>
		</apptag:privilege>|
		<apptag:privilege operation="edit"></apptag:privilege>
			<a href="javascript:oneStopEdit('${row.id}')" title="能够使您快速修改基站信息">快速修改</a>
		
	</display:column>
</display:table>
<div align="left">
	<a href="javascript:exportForm();">导出Excel文件</a>
</div>