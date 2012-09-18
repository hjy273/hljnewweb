<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<script src="${ctx}/js/extjs/ux/ux-all-debug.js" type="text/javascript"></script>
<script src="${ctx}/js/extjs/ux/Appcombox.js" type="text/javascript"></script>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
	media="screen, print" />
<script language="javascript" type="text/javascript">
	var win;
	function view(id){
		window.location.href = '${ctx}/oilEngineAction_view.jspx?flag=view&id='+id;
	}
	function edit(id){
		window.location.href = '${ctx}/oilEngineAction_view.jspx?flag=edit&id='+id;
	}
	function del(id){
    	if(confirm("删除将不能恢复，请确认是否要删除该油机，按‘确定’删除？")){
			window.location.href = '${ctx}/oilEngineAction_del.jspx?id='+id;
    	}
	}

	function exportForm() {
		var actionUrl = "${ctx}/oilEngineAction_exprotForm.jspx";
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
			title : "选择要导出的油机属性"
		});
		win.show(Ext.getBody());
	}
	closeWin = function() {
		win.close();
	}
	
	
//所属区县
var patrolregioncombotree;
Ext.onReady(function() {
	patrolregioncombotree = new TreeComboField({
		width : 220,
		height : 100,
		allowBlank : true,
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
	patrolregioncombotree.setComboValue("${oilEngine.district}","${oilEngine.districtName}");//中文
	
	//油机类型
	var  oilEngineType = new Appcombox({
	   	hiddenName : 'oilEngine.oilEngineType',
	   	emptyText : '请选择',
	   	dataUrl : '${ctx}/common/externalresources_getDictionaryBlankJson.jspx?type=OIL_ENGINE_TYPE',
	   	dataCode : 'CODEVALUE',
	   	dataText : 'LABLE',
	   	allowBlank:true,
	   	renderTo: 'oilEngine_oilEngineType'
	})
	oilEngineType.setComboValue('${oilEngine.oilEngineType }','<apptag:dynLabel objType="dic" id="${oilEngine.oilEngineType }" dicType="OIL_ENGINE_TYPE"></apptag:dynLabel>');
	//油料类型
	var  oilType = new Appcombox({
	   	hiddenName : 'oilEngine.oilType',
	   	emptyText : '请选择',
	   	dataUrl : '${ctx}/common/externalresources_getDictionaryBlankJson.jspx?type=OIL_TYPE',
	   	dataCode : 'CODEVALUE',
	   	dataText : 'LABLE',
	   	allowBlank:true,
	   	renderTo: 'oilEngine_oilType'
	})
	oilType.setComboValue('${oilEngine.oilType }','<apptag:dynLabel objType="dic" id="${oilEngine.oilType }" dicType="OIL_TYPE"></apptag:dynLabel>');
	
	
});
</script>


<template:titile value="油机信息查询" />
<br />
<s:form action="oilEngineAction_list" name="form" method="post">
	<input name="is_query" value="1" type="hidden" />
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
				<input type="text" value="${oilEngine.oilEngineCode }"
					name="oilEngine.oilEngineCode" style="width: 220px"></input>
			</td>
		</tr>
		<tr class="trwhite">
			<td class="tdulleft" style="width: 15%">
				油机类型：
			</td>
			<td class="tdulright" style="width: 35%">
				<div id="oilEngine_oilEngineType"></div>
			</td>
			<td class="tdulleft" style="width: 15%">
				油料类型：
			</td>
			<td class="tdulright" style="width: 35%">
				<div id="oilEngine_oilType"></div>
			</td>
		</tr>
	</table>
	<br />
	<div align="center">
		<html:submit styleClass="button">查询</html:submit>
	</div>
</s:form>
<display:table name="sessionScope.RESULTS" id="row" pagesize="18"
	export="fasle" requestURI="${ctx }/oilEngineAction_list.jspx">
	<display:column title="油机编号" maxLength="15" sortable="true">
		${row.oilEngineCode }
	</display:column>
	<display:column title="油机类型">
		<apptag:dynLabel objType="dic" id="${row.oilEngineType}"
			dicType="OIL_ENGINE_TYPE"></apptag:dynLabel>
	</display:column>
	<display:column title="油机型号">
		${row.oilEngineModel}
	</display:column>
	<display:column title="额定功率">
		${row.rationPower} KW
	</display:column>
	<display:column title="所属区县">
		${sessionScope.district[row.district]}
	</display:column>
	<display:column title="状态">
		${row.state}
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