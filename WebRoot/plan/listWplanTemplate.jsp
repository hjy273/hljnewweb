<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<script src="${ctx}/js/extjs/ux/ux-all-debug.js" type="text/javascript"></script>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
	media="screen, print" />
<script language="javascript" type="text/javascript">
	var win;
	function view(id) {
		window.location.href = '${ctx}/wplanTemplateAction_view.jspx?flag=view&id='
				+ id;
	}
	function edit(id) {
		window.location.href = '${ctx}/patrolManager/wplanTemplateAction_view.jspx?flag=copy&id='
				+ id;
	}
	function del(id) {
		//if (confirm("ɾ�������ָܻ�����ȷ���Ƿ�Ҫɾ������Ϣ������ȷ����ɾ����")) {
		window.location.href = '${ctx}/res/wplanTemplateAction_delete.jspx?id='
				+ id;
		//}
	}
	function startUsing(id) {
		//if (confirm("ɾ�������ָܻ�����ȷ���Ƿ�Ҫɾ������Ϣ������ȷ����ɾ����")) {
		window.location.href = '${ctx}/res/wplanTemplateAction_startUsing.jspx?id='
				+ id;
		//}
	}
</script>
<template:titile value="�ƻ�ģ����Ϣ��ѯ" />
<br />
<s:form action="patrolManager/wplanTemplateAction_query.jspx"
	method="post">
	<input name="is_query" value="1" type="hidden" />
	<table width="850" border="0" align="center" cellpadding="3"
		cellspacing="0" class="tabout">
		<tr>

			<td class="xianshi1">
				ģ�����ƣ�
			</td>
			<td class="xianshi2">
				<input type="text" style="width: 220px;" name="templateName"
					value="${templateName }" />
			</td>
			<td class="xianshi1">
				רҵ��
			</td>
			<td class="xianshi2">
				<apptag:dynLabel dicType="businesstype" objType="dic"
					id="${businessType }"></apptag:dynLabel>
			</td>
			<td>
				<input type="submit" class="button" value="��ѯ">
			</td>
		</tr>
	</table>
</s:form>
<display:table name="sessionScope.RESULTLIST" id="row" pagesize="18"
	export="fasle" requestURI="${ctx }/wplanTemplateAction_query.jspx">
	<display:column property="businesstypename" style="width:160px;"
		title="רҵ" sortable="true" />
	<display:column property="template_name" title="ģ������" sortable="true" />
	<display:column property="remark" style="width:4%" title="��ע"
		sortable="true" />
	<display:column media="html" title="����" style="width:100px;">
		<a href="javascript:view('${row.id}')">�鿴</a>&nbsp;
   	    <a href="javascript:edit('${row.id}')">����</a>&nbsp;
   	    <c:if test="${row.is_forbidden_state=='N' }">
			<a href="javascript:del('${row.id}')">ͣ��</a>
		</c:if>
		<c:if test="${row.is_forbidden_state=='Y' }">
			<a href="javascript:startUsing('${row.id}')">����</a>
		</c:if>
	</display:column>
</display:table>
<!--<div align="left">
	<a href="${ctx}/patrolItemAction_export.jspx">����Excel�ļ�</a>
</div>-->