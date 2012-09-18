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
		//if (confirm("删除将不能恢复，请确认是否要删除该信息，按‘确定’删除？")) {
		window.location.href = '${ctx}/res/wplanTemplateAction_delete.jspx?id='
				+ id;
		//}
	}
	function startUsing(id) {
		//if (confirm("删除将不能恢复，请确认是否要删除该信息，按‘确定’删除？")) {
		window.location.href = '${ctx}/res/wplanTemplateAction_startUsing.jspx?id='
				+ id;
		//}
	}
</script>
<template:titile value="计划模板信息查询" />
<br />
<s:form action="patrolManager/wplanTemplateAction_query.jspx"
	method="post">
	<input name="is_query" value="1" type="hidden" />
	<table width="850" border="0" align="center" cellpadding="3"
		cellspacing="0" class="tabout">
		<tr>

			<td class="xianshi1">
				模板名称：
			</td>
			<td class="xianshi2">
				<input type="text" style="width: 220px;" name="templateName"
					value="${templateName }" />
			</td>
			<td class="xianshi1">
				专业：
			</td>
			<td class="xianshi2">
				<apptag:dynLabel dicType="businesstype" objType="dic"
					id="${businessType }"></apptag:dynLabel>
			</td>
			<td>
				<input type="submit" class="button" value="查询">
			</td>
		</tr>
	</table>
</s:form>
<display:table name="sessionScope.RESULTLIST" id="row" pagesize="18"
	export="fasle" requestURI="${ctx }/wplanTemplateAction_query.jspx">
	<display:column property="businesstypename" style="width:160px;"
		title="专业" sortable="true" />
	<display:column property="template_name" title="模板名称" sortable="true" />
	<display:column property="remark" style="width:4%" title="备注"
		sortable="true" />
	<display:column media="html" title="操作" style="width:100px;">
		<a href="javascript:view('${row.id}')">查看</a>&nbsp;
   	    <a href="javascript:edit('${row.id}')">复制</a>&nbsp;
   	    <c:if test="${row.is_forbidden_state=='N' }">
			<a href="javascript:del('${row.id}')">停用</a>
		</c:if>
		<c:if test="${row.is_forbidden_state=='Y' }">
			<a href="javascript:startUsing('${row.id}')">启用</a>
		</c:if>
	</display:column>
</display:table>
<!--<div align="left">
	<a href="${ctx}/patrolItemAction_export.jspx">导出Excel文件</a>
</div>-->