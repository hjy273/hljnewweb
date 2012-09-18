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
		if (confirm("ɾ�������ָܻ�����ȷ���Ƿ�Ҫɾ����ֱ��վ������ȷ����ɾ����")) {
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
			title : "ѡ��Ҫ������ֱ��վ����"
		});
		win.show(Ext.getBody());
	}
	closeWin = function() {
		win.close();
	}
</script>
<template:titile value="��ѯֱ��վ��Ϣ" />
<br />
<s:form action="repeaterAction_query" method="post">
	<input name="is_query" value="1" type="hidden" />
	<table width="850" border="0" align="center" cellpadding="3"
		cellspacing="0" class="tabout">
		<tr class="trwhite">
			<td class="tdulleft" style="width: 15%">
				ֱ��վ��:
			</td>
			<td class="tdulright" style="width: 35%">
				<input type="text" class="inputtext" style="width: 150px"
					name="repeaterType" value="${repeater.repeaterType}" />
			</td>
			<td class="tdulleft" style="width: 15%">
				ֱ��վ����:
			</td>
			<td class="tdulright" style="width: 35%">
				<input type="text" class="inputtext" style="width: 150px"
					name="repeaterName" value="${repeater.repeaterName}" />
			</td>
		</tr>
		<tr class="trwhite">
			<td class="tdulleft" style="width: 15%">
				��װλ��:
			</td>
			<td class="tdulright" style="width: 35%">
				<input type="text" class="inputtext" style="width: 150px"
					name="installPlace" value="${repeater.installPlace}" />
			</td>
			<td class="tdulleft" style="width: 15%">
				��������:
			</td>
			<td class="tdulright" style="width: 35%">
				<div id="combotree_patrolregiondiv" style="width: 180;"></div>
			</td>
		</tr>
		<!-- 
		<tr class="trwhite">
			<td class="tdulleft" style="width: 15%">
				��Դ��վվַ���:
			</td>
			<td class="tdulright" style="width: 35%">
				<input type="text" class="inputtext" style="width: 150px"
					name="stationCode" value="${stationCode}" />
			</td>
			<td class="tdulleft" style="width: 15%">
				��Դ��վ����:
			</td>
			<td class="tdulright" style="width: 35%">
				<input type="text" class="inputtext" style="width: 150px"
					name="stationName" value="${stationName}" />
			</td>
		</tr>
		 -->
	</table>
	<div align="center">
		<html:submit styleClass="button">��ѯ</html:submit>
	</div>
</s:form>
<display:table name="sessionScope.RESULTLIST" id="row" pagesize="18"
	export="fasle" requestURI="${ctx }/repeaterAction_query.jspx">
	<display:column property="repeaterType" title="ֱ��վ��" maxLength="15"
		sortable="true" />
	<display:column property="repeaterName" title="ֱ��վ����" maxLength="15"
		sortable="true" />
	<display:column property="city" title="��������" maxLength="15"
		sortable="true" />
	<display:column property="installPlace" title="��װλ��" maxLength="15"
		sortable="true" />
	<display:column title="���Ƿ�Χ" maxLength="15" sortable="true">
		<apptag:dynLabel objType="dic" id="${row.coverageArea}"
			dicType="overlay_area"></apptag:dynLabel>
	</display:column>
	<display:column title="������������" maxLength="15" sortable="true">
		<apptag:dynLabel objType="dic" id="${row.coverageAreaType}"
			dicType="overlay_area_type"></apptag:dynLabel>
	</display:column>
	<display:column property="createTime" title="��վʱ��"
		format="{0,date,yyyy��MM��dd��}" maxLength="15" sortable="true" />
	<display:column title="ά����λ" maxLength="15" sortable="true">
		<apptag:dynLabel objType="org" id="${row.maintenanceId}"
			dicType=""></apptag:dynLabel>
	</display:column>
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