<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script src="${ctx}/js/extjs/ux/ux-all-debug.js" type="text/javascript"></script>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
	media="screen, print" />
<script src="${ctx}/js/extjs/ux/Appcombox.js" type="text/javascript"></script>
<script language="javascript" type="text/javascript">
//所属区县
var patrolregioncombotree;
Ext.onReady(function() {
	Ext.BLANK_IMAGE_URL = "${ctx}/js/extjs/resources/images/default/s.gif";
	patrolregioncombotree = new TreeComboField({
		width : 150,
		height :  100,
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
	function view(id){
		window.location.href = '${ctx}/antennaAction_view.jspx?flag=view&id='+id;
	}
	function edit(id){
		window.location.href = '${ctx}/antennaAction_view.jspx?flag=edit&id='+id;
	}
	function del(id){
    	if(confirm("删除将不能恢复，请确认是否要删除该基站天线信息，按‘确定’删除？")){
			window.location.href = '${ctx}/antennaAction_delete.jspx?id='+id;
    	}
	}
	function exportForm(){
		var actionUrl="${ctx}/antennaAction_exportForm.jspx?exportType=antenna";
		//window.open(actionUrl);
		win = new Ext.Window({
			layout : 'fit',
			width:650,height:400, 
			resizable:true,
			closeAction : 'close', 
			modal:true,
			autoScroll:true,
			autoLoad:{url: actionUrl,scripts:true}, 
			plain: true,
			title:"选择要导出的天线属性" 
		});
		win.show(Ext.getBody());
	}
	closeWin=function(){
		win.close();
	}
</script>
<template:titile value="查询基站天线信息" />
<br />
<s:form action="antennaAction_query" method="post">
	<input name="is_query" value="1" type="hidden" />
	<table width="850" border="0" align="center" cellpadding="3"
		cellspacing="0" class="tabout">
		<tr class="trwhite">
			<td class="tdulleft" style="width: 15%">
				天线编号:
			</td>
			<td class="tdulright" style="width: 35%">
				<input type="text" class="inputtext" style="width: 150px"
					name="antennaCode" value="${antenna.antennaCode}" />
			</td>
			<td class="tdulleft" style="width: 15%">
				天线名称:
			</td>
			<td class="tdulright" style="width: 35%">
				<input type="text" class="inputtext" style="width: 150px"
					name="antennaName" value="${antenna.antennaName}" />
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
	export="fasle" requestURI="${ctx }/antennaAction_query.jspx">
	<display:column title="所属基站" maxLength="15" sortable="true">
		<c:out value="${sessionScope.BASESTATIONS[row.parentId]}" />
	</display:column>
	<display:column title="天线编号" property="antennaCode" maxLength="15"
		sortable="true" />
	<display:column title="天线名称" property="antennaName" maxLength="15"
		sortable="true" />
	<display:column property="azimuth" title="天线方位角" maxLength="15"
		sortable="true" />
	<display:column property="pitchofangle" title="天线俯仰角" maxLength="15"
		sortable="true" />
	<display:column title="经度" property="longitude" maxLength="15"
		sortable="true" />
	<display:column title="纬度" property="latitude" maxLength="15"
		sortable="true" />
	<display:column property="antennaNumber" title="天线数量" maxLength="15"
		sortable="true" />
	<display:column title="天线类型" maxLength="15" sortable="true">
		<apptag:dynLabel objType="dic" id="${row.antennaType}"
			dicType="antenna_type"></apptag:dynLabel>
	</display:column>
	<display:column property="createTime" title="投入使用日期" maxLength="15"
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