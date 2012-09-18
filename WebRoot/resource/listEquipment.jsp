<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script src="${ctx}/js/extjs/ux/ux-all-debug.js" type="text/javascript"></script>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
	media="screen, print" />
<script language="javascript" type="text/javascript">
//��������
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
		window.location.href = '${ctx}/equipmentAction_view.jspx?flag=view&id=' + id;
	}
	function edit(id) {
		window.location.href = '${ctx}/equipmentAction_view.jspx?flag=edit&id=' + id;
	}
	function del(id) {
		if (confirm("ɾ�������ָܻ�����ȷ���Ƿ�Ҫɾ�����豸������ȷ����ɾ����")) {
			window.location.href = '${ctx}/equipmentAction_delete.jspx?id=' + id;
		}
	}
	function exportForm() {
		var actionUrl = "${ctx}/equipmentAction_exportForm.jspx?exportType=equipment";
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
			title : "ѡ��Ҫ�������豸����"
		});
		win.show(Ext.getBody());
	}
	closeWin = function() {
		win.close();
	}
</script>
<template:titile value="��ѯ�豸��Ϣ" />

<br />
<s:form action="equipmentAction_query" method="post">
	<input name="is_query" value="1" type="hidden" />
	<table width="850" border="0" align="center" cellpadding="3"
		cellspacing="0" class="tabout">
		<!-- ���豸���ͣ��豸���Ʋ�ѯ -->
		<tr class="trwhite">
			<td class="tdulleft" style="width: 15%">
				�豸����:
			</td>
			<td class="tdulright" style="width: 35%">
				<s:select list="%{#session.EQUIPMENTSTYPE}" name="equTypeId"
					listKey="key" listValue="value" cssClass="inputtext"
					cssStyle="width:150px" headerKey="" headerValue="=��ѡ��=" />
			</td>
			<td class="tdulleft" style="width: 15%">
				�豸����:
			</td>
			<td class="tdulright" style="width: 35%">
				<input type="text" class="inputtext" style="width: 150px"
					name="equName" value="${equipment.equName}" />
			</td>
		</tr>
		<tr class="trwhite">
			<td class="tdulleft" style="width: 15%">
				�豸�ͺ�:
			</td>
			<td class="tdulright" style="width: 35%">
				<input type="text" class="inputtext" style="width: 150px"
					name="equModel" value="${equipment.equModel}" />
			</td>
			<td class="tdulleft" style="width: 15%">
				�豸����:
			</td>
			<td class="tdulright" style="width: 35%">
				<input type="text" class="inputtext" style="width: 150px"
					name="equProducter" value="${equipment.equProducter}" />
			</td>
		</tr>
		<tr class="trwhite">
			<td class="tdulleft" style="width: 15%">
				վַ���:
			</td>
			<td class="tdulright" style="width: 35%">
				<input type="text" class="inputtext" style="width: 150px"
					name="stationCode" value="${stationCode }" />
			</td>
			<td class="tdulleft" style="width: 15%">
				��վ����:
			</td>
			<td class="tdulright" style="width: 35%">
				<input type="text" class="inputtext" style="width: 150px"
					name="stationName" value="${stationName}" />
			</td>
		</tr>
		<tr class="trwhite">
			<td class="tdulleft" style="width: 15%">
				��������:
			</td>
			<td class="tdulright" style="width: 35%">
				<div id="combotree_patrolregiondiv" style="width: 180;"></div>
			</td>
			<td class="tdulleft" style="width: 15%">
				������:
			</td>
			<td class="tdulright" style="width: 35%">
				<input type="text" class="inputtext" style="width: 150px"
					name="barCode" value="${equipment.barCode }" />
			</td>
		</tr>
	</table>
	<div align="center">
		<html:submit styleClass="button">��ѯ</html:submit>
	</div>
</s:form>
<display:table name="sessionScope.RESULTLIST" id="row" pagesize="18"
	export="fasle" requestURI="${ctx }/equipmentAction_query.jspx">
	<display:column title="������վ" maxLength="15" sortable="true">
		<c:out value="${BASESTATIONS[row.parentId]}" />
	</display:column>
	<display:column title="�豸����" maxLength="15" sortable="true">
		<c:out value="${EQUIPMENTSTYPE[row.equTypeId]}" />
	</display:column>
	<display:column property="equName" title="�豸����" maxLength="15"
		sortable="true" />
	<display:column property="equProducter" title="�豸����" maxLength="15"
		sortable="true" />
	<display:column property="equModel" title="�豸�ͺ�" maxLength="15"
		sortable="true" />
	<display:column property="equDeploy" title="����" maxLength="15"
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
<script language="javascript" type="">
var selectOptions=document.getElementById("equTypeId");
for(i=0;i<selectOptions.length;i++){
	if(selectOptions.options[i].value=="${equipment.equTypeId}"){
		selectOptions.options[i].selected=true;
		break;
	}
}
</script>