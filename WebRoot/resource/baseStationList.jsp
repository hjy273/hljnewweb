<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
	media="screen, print" />
<script src="${ctx}/js/extjs/ux/ux-all-debug.js" type="text/javascript"></script>
<script src="${ctx}/js/extjs/ux/Appcombox.js" type="text/javascript"></script>
<script language="javascript" type="text/javascript">
//��������
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
		//��վ����	baseStation.bsLevel				basestation_level	basestaion_basestation_level
		var  basestation_level = new Appcombox({
			width : 160,
			hiddenName : 'bsLevel',
			emptyText : '��ѡ��',
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
		if (confirm("ɾ�������ָܻ�����ȷ���Ƿ�Ҫɾ���û�վ������ȷ����ɾ����")) {
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
			title : "ѡ��Ҫ�����Ļ�վ����"
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
<template:titile value="��վ��Ϣ��ѯ" />
<br />
<s:form action="baseStationAction_query" method="post">
	<input name="is_query" value="1" type="hidden" />
	<table width="850" border="0" align="center" cellpadding="3"
		cellspacing="0" class="tabout">
		<tr class="trwhite">
			<td class="tdulleft" style="width: 15%">
				վַ���:
			</td>
			<td class="tdulright" style="width: 35%">
				<input type="text" class="inputtext" style="width: 150px"
					name="stationCode" value="${baseStation.stationCode}" />
			</td>
			<td class="tdulleft" style="width: 15%">
				��վ����:
			</td>
			<td class="tdulright" style="width: 35%">
				<input type="text" class="inputtext" style="width: 150px"
					name="stationName" value="${baseStation.stationName}" />
			</td>
		</tr>
		<tr class="trwhite">
			<td class="tdulleft" style="width: 15%">
				��վ��ַ:
			</td>
			<td class="tdulright" style="width: 35%">
				<input type="text" class="inputtext" style="width: 150px"
					name="address" alt="֧��ģ������" value="${baseStation.address}" />
			</td>
			<td class="tdulleft" style="width: 15%">
				��վ����:
			</td>
			<td class="tdulright" style="width: 35%">
				<div id="basestaion_basestation_level"></div>
			</td>
		</tr>
		<tr class="trwhite">
			<td class="tdulleft" style="width: 15%">
				��������:
			</td>
			<td class="tdulright" colspan="3" style="width: 85%">
				<div id="combotree_patrolregiondiv" style="width: 180;"></div>
			</td>
		</tr>
	</table>
	<div align="center">
		<html:submit styleClass="button">��ѯ</html:submit>
	</div>
</s:form>
<display:table name="sessionScope.RESULTLIST" id="row" pagesize="18"
	export="fasle" requestURI="${ctx }/baseStationAction_query.jspx">
	<display:column property="stationCode" title="վַ���" maxLength="15"
		sortable="true" />
	<display:column property="stationName" title="��վ����" maxLength="15"
		sortable="true" />
	<display:column title="��վ����" maxLength="15" sortable="true">
		<apptag:dynLabel objType="dic" id="${row.bsLevel}"
			dicType="basestation_level"></apptag:dynLabel>
	</display:column>
	<display:column media="html" title="����" sortable="true">
		<c:set var="key" value="${row.district}"></c:set>
		<c:out value="${DISTRICTS[key]}"></c:out>
	</display:column>
	<display:column property="anTime" title="����ʱ��"
		format="{0,date,yyyy��MM��dd��}" maxLength="15" sortable="true" />
	<display:column media="html" title="ά����λ" sortable="true">
		<c:out value="${CONTRACTORS[row.maintenanceId]}"></c:out>
	</display:column>
	<display:column media="html" title="����" paramId="tid">
		<!-- 
		<a href="javascript:vrp('${row.stationCode }')">����ȫ��</a>
		 -->
		<apptag:privilege operation="view">
			<a href="javascript:view('${row.id}')">�鿴</a>
		</apptag:privilege>
		<apptag:privilege operation="edit">
			<a href="javascript:edit('${row.id}')">�޸�</a>
		</apptag:privilege>
		<apptag:privilege operation="del">
			<a href="javascript:del('${row.id}')">ɾ��</a>
		</apptag:privilege>|
		<apptag:privilege operation="edit"></apptag:privilege>
			<a href="javascript:oneStopEdit('${row.id}')" title="�ܹ�ʹ�������޸Ļ�վ��Ϣ">�����޸�</a>
		
	</display:column>
</display:table>
<div align="left">
	<a href="javascript:exportForm();">����Excel�ļ�</a>
</div>