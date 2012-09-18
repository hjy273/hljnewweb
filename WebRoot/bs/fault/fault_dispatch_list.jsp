<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
	media="screen, print" />
<script src="${ctx}/js/extjs/ux/ux-all-debug.js" type="text/javascript"></script>
<script src="${ctx}/js/extjs/ux/Appcombox.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx }/bs/fault/js/fault_common.js"></script>
<script language="javascript" type="text/javascript">
	setContextPath("${ctx}");
	Ext.onReady(function() {
		// ���ַ�ʽ		parameter.findType			FIND_TYPE		parameter_findtype
		var find_type = new Appcombox(
			{
				hiddenName : 'parameter.findType',
				emptyText : '��ѡ��',
				dataUrl : '${ctx}/common/externalresources_getDictionaryJson.jspx?type=FIND_TYPE',
				dataCode : 'CODEVALUE',
				dataText : 'LABLE',
				allowBlank : true,
				renderTo : 'parameter_findtype'
			});
		find_type
			.setComboValue(
				'${parameter.findType}',
				'<apptag:dynLabel objType="dic" id="${parameter.findType}" dicType="FIND_TYPE"></apptag:dynLabel>');
	});
</script>
<template:titile value="�����ɵ���Ϣ�б�" />
<s:form action="faultDispatchAction_list.jspx" method="post">
	<input name="parameter.isQuery" value="1" type="hidden" />
	<table width="850" border="0" align="center" cellpadding="3"
		cellspacing="0" class="tabout">
		<tr class="trwhite">
			<td class="tdulleft" style="width: 15%">
				���ϵ�����:
			</td>
			<td class="tdulright" style="width: 35%">
				<input type="hidden" name="businessType" value="${businessType}" />
				<input type="text" class="inputtext" style="width: 220px"
					name="parameter.troubleTitle" value="${parameter.troubleTitle}" />
			</td>
			<td class="tdulleft" style="width: 15%">
				���Ϸ���ʱ��:
			</td>
			<td class="tdulright" style="width: 35%">
				<input type="text" class="inputtext" style="width: 95px"
					name="parameter.troubleTimeStart" class="Wdate inputtext"
					value="${parameter.troubleTimeStart}"
					onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
				��
				<input type="text" class="inputtext" style="width: 95px"
					name="parameter.troubleTimeEnd" class="Wdate inputtext"
					value="${parameter.troubleTimeEnd}"
					onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
			</td>
		</tr>
		<tr class="trwhite">
			<td class="tdulleft" style="width: 15%">
				���Ϸ����ص�:
			</td>
			<td class="tdulright" style="width: 35%">
				<input type="text" class="inputtext" style="width: 220px"
					name="parameter.address" alt="֧��ģ������" value="${parameter.address}" />
			</td>
			<td class="tdulleft" style="width: 15%">
				�Ƿ����:
			</td>
			<td class="tdulright" style="width: 35%">
				<c:set var="checked_isinstancy_unlimited" value=" checked"></c:set>
				<c:set var="checked_isinstancy_no" value=""></c:set>
				<c:set var="checked_isinstancy_yes" value=""></c:set>
				<c:if test="${parameter.isInstancy==1 }">
					<c:set var="checked_isinstancy_unlimited" value=""></c:set>
					<c:set var="checked_isinstancy_no" value=""></c:set>
					<c:set var="checked_isinstancy_yes" value=" checked"></c:set>
				</c:if>
				<c:if test="${parameter.isInstancy==2 }">
					<c:set var="checked_isinstancy_unlimited" value=""></c:set>
					<c:set var="checked_isinstancy_no" value=" checked"></c:set>
					<c:set var="checked_isinstancy_yes" value=""></c:set>
				</c:if>
				<input type="radio" name="parameter.isInstancy" value=""${checked_isinstancy_unlimited }>
				����
				<input type="radio" name="parameter.isInstancy" value="2"${checked_isinstancy_no }>
				��
				<input type="radio" name="parameter.isInstancy" value="1"${checked_isinstancy_yes }>
				��
			</td>
		</tr>
		<tr class="trwhite">
			<td class="tdulleft" style="width: 15%">
				���ַ�ʽ:
			</td>
			<td class="tdulright" style="width: 85%" colspan="3">
				<div id="parameter_findtype"></div>
			</td>
		</tr>
	</table>
	<div align="center">
		<html:submit styleClass="button">��ѯ</html:submit>
	</div>
</s:form>
<display:table name="sessionScope.FAULT_DISPATCH_LIST" id="row"
	pagesize="18" export="false">
	<display:column property="trouble_title" title="���ϵ�����" />
	<display:column property="eoms_id" title="EOMS����" />
	<display:column property="trouble_time_dis" title="���Ϸ���ʱ��" />
	<display:column property="address" title="���Ϸ����ص�" />
	<display:column title="����վ��">
		<c:set var="key" value="${row.station_id}_${row.station_type }"></c:set>
		<c:out value="${RESOURCES_MAP[key]}"></c:out>
	</display:column>
	<display:column title="�Ƿ����">
		<c:if test="${row.is_instancy==1 }">��</c:if>
		<c:if test="${row.is_instancy==2 }">��</c:if>
	</display:column>
	<display:column property="lable" title="���ַ�ʽ" />
	<display:column media="html" title="����" paramId="id">
		<a
			href="javascript:viewFaultDispatch('${row.dispatch_id}','${businessType }')">�鿴</a>
	</display:column>
</display:table>
