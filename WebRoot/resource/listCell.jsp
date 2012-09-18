<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script src="${ctx}/js/extjs/ux/ux-all-debug.js" type="text/javascript"></script>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
	media="screen, print" />
<script language="javascript" type="text/javascript">
//所属区县
var patrolregioncombotree;
Ext.onReady(function() {
	patrolregioncombotree = new TreeComboField({
		width : 150,
		height: 100,
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
	patrolregioncombotree.setComboValue("${district}",'<apptag:dynLabel objType="region" id="${district}" dicType=""></apptag:dynLabel>');
});
	var win;
	function view(id) {
		window.location.href = '${ctx}/cellAction_view.jspx?flag=view&id=' + id;
	}
	function edit(id) {
		window.location.href = '${ctx}/cellAction_view.jspx?flag=edit&id=' + id;
	}
	function del(id) {
		if (confirm("删除将不能恢复，请确认是否要删除该小区，按‘确定’删除？")) {
			window.location.href = '${ctx}/cellAction_delete.jspx?id=' + id;
		}
	}
	function exportForm() {
		var actionUrl = "${ctx}/cellAction_exportForm.jspx?exportType=cell&cellCode="
				+ $('cellCode').value
				+ "&chineseName="
				+ $('chineseName').value;
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
			title : "选择要导出的小区属性"
		});
		win.show(Ext.getBody());
	}
	closeWin = function() {
		win.close();
	}
</script>
<template:titile value="查询小区信息" />
<br />
<s:form action="cellAction_query" method="post">
	<input name="is_query" value="1" type="hidden" />
	<table width="850" border="0" align="center" cellpadding="3"
		cellspacing="0" class="tabout">
		<tr class="trwhite">
			<td class="tdulleft" style="width: 15%">
				小区号:
			</td>
			<td class="tdulright" style="width: 35%">
				<input type="text" class="inputtext" style="width: 150px"
					name="cellCode" value="${cellCode}" />
			</td>
			<td class="tdulleft" style="width: 15%">
				中文名称:
			</td>
			<td class="tdulright" style="width: 35%">
				<input type="text" class="inputtext" style="width: 150px"
					name="chineseName" value="${chineseName}" />
			</td>
		</tr>
		<tr class="trwhite">
			<td class="tdulleft" style="width: 15%">
				所属基站站址编号:
			</td>
			<td class="tdulright" style="width: 35%">
				<input type="text" class="inputtext" style="width: 150px"
					name="stationCode" value="${stationCode}" />
			</td>
			<td class="tdulleft" style="width: 15%">
				所属基站名称:
			</td>
			<td class="tdulright" style="width: 35%">
				<input type="text" class="inputtext" style="width: 150px"
					name="stationName" value="${stationName}" />
			</td>
		</tr>
		<tr class="trwhite">
			<td class="tdulleft" style="width: 15%">
				所在区县:
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
	export="fasle" requestURI="${ctx }/cellAction_query.jspx">
	<display:column title="所属基站" maxLength="15" sortable="true">
		<c:out value="${sessionScope.BASESTATIONS[row.parentId]}" />
	</display:column>
	<display:column property="cellCode" title="小区号" maxLength="15"
		sortable="true" />
	<display:column property="chineseName" title="中文名称" maxLength="15"
		sortable="true" />
	<display:column title="是否调频" maxLength="15" sortable="true">
		<c:if test="${row.isFrequencyHopping == 'y'}">是</c:if>
		<c:if test="${row.isFrequencyHopping != 'y'}">否</c:if>
	</display:column>
	<display:column title="是否下带直放站" maxLength="15" sortable="true">
		<c:if test="${row.isOwnRepeater == 'y'}">是</c:if>
		<c:if test="${row.isOwnRepeater != 'y'}">否</c:if>
	</display:column>
	<display:column property="carrierFrequencyNum" title="载频数"
		maxLength="15" sortable="true" />
	<display:column property="anTime" title="入网时间"
		format="{0,date,yyyy年MM月dd日}" maxLength="15" sortable="true" />
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