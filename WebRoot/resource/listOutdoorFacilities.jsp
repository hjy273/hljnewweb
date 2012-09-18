<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<script src="${ctx}/js/extjs/ux/ux-all-debug.js" type="text/javascript"></script>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
	media="screen, print" />
<script language="javascript" type="text/javascript">
//��������
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
		if (confirm("ɾ�������ָܻ�����ȷ���Ƿ�Ҫɾ��������������ȷ����ɾ����")) {
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
			title : "ѡ��Ҫ��������������"
		});
		win.show(Ext.getBody());
	}
	closeWin = function() {
		win.close();
	}
</script>
<template:titile value="��ѯ������Ϣ" />
<br />
<s:form action="outdoorFacilitiesAction_query" method="post">
	<input name="is_query" value="1" type="hidden" />
	<table width="850" border="0" align="center" cellpadding="3"
		cellspacing="0" class="tabout">
		<tr class="trwhite">
			<td class="tdulleft" style="width: 15%">
				�������:
			</td>
			<td class="tdulright" style="width: 35%">
				<input type="text" class="inputtext" style="width: 150px"
					name="towerCode" value="${outdoorFacilities.towerCode}" />
			</td>
			<td class="tdulleft" style="width: 15%">
				��������:
			</td>
			<td class="tdulright" style="width: 35%">
				<input type="text" class="inputtext" style="width: 150px"
					name="towerName" value="${outdoorFacilities.towerName}" />
			</td>
		</tr>
		<tr class="trwhite">
			<td class="tdulleft" style="width: 15%">
				ԭ����:
			</td>
			<td class="tdulright" style="width: 35%">
				<input type="text" class="inputtext" style="width: 150px"
					name="oldName" value="${outdoorFacilities.oldName}" />
			</td>
			<td class="tdulleft" style="width: 15%">
				��������:
			</td>
			<td class="tdulright" style="width: 35%">
				<div id="combotree_patrolregiondiv" style="width: 180;"></div>
			</td>
		</tr>
		<tr class="trwhite">
			<td class="tdulleft" style="width: 15%">
				������վվַ���:
			</td>
			<td class="tdulright" style="width: 35%">
				<input type="text" class="inputtext" style="width: 150px"
					name="stationCode" value="${parentId}" />
			</td>
			<td class="tdulleft" style="width: 15%">
				������վ����:
			</td>
			<td class="tdulright" style="width: 35%">
				<input type="text" class="inputtext" style="width: 150px"
					name="stationName" value="${stationName}" />
			</td>
		</tr>
	</table>
	<div align="center">
		<html:submit styleClass="button">��ѯ</html:submit>
	</div>
</s:form>
<display:table name="sessionScope.RESULTLIST" id="row" pagesize="18"
	export="fasle" requestURI="${ctx }/outdoorFacilitiesAction_query.jspx">
	<display:column title="������վ" maxLength="15" sortable="true">
		<c:out value="${sessionScope.BASESTATIONS[row.parent_id]}" />
	</display:column>
	<display:column title="������� " property="tower_code" maxLength="15"
		sortable="true" />
	<display:column title="�������� " property="tower_name" maxLength="15"
		sortable="true" />
	<display:column title="��������" maxLength="15" sortable="true">
		<apptag:dynLabel objType="dic" id="${row.tower_type}"
			dicType="TOWERTYPE"></apptag:dynLabel>
	</display:column>
	<display:column property="tower_height" title="�����߶�" maxLength="15"
		sortable="true" />
	<display:column property="tower_platform_num" title="����ƽ̨��"
		maxLength="15" sortable="true" />
	<display:column title="�ݶ�Φ������" maxLength="15" sortable="true">
		<apptag:dynLabel objType="dic" id="${row.mast_type}"
			dicType="mast_type"></apptag:dynLabel>${row.mast_type}
	</display:column>
	<display:column property="pole_height" title="���˸߶�" maxLength="15"
		sortable="true" />
	<display:column title="����ƽ̨��" property="use_platform_num" maxLength="15"
		sortable="true" />
	<display:column title="����ʱ�� " property="an_time" maxLength="15"
		sortable="true" />
	<display:column media="html" title="����" paramId="tid">
		<apptag:privilege operation="view">
			<a href="javascript:view('${row.id}')">�鿴</a>
		</apptag:privilege>
		<apptag:privilege operation="edit">
			<a href="javascript:edit('${row.id}')">�޸�</a>
		</apptag:privilege>
		<apptag:privilege operation="del">
			<a href="javascript:del('${row.id}')">ɾ��</a>
		</apptag:privilege>
	</display:column>
</display:table>
<div align="left">
	<a href="javascript:exportForm();">����Excel�ļ�</a>
</div>