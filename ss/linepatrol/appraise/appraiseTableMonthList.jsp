<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
	media="screen, print" />
<%@page import="com.cabletech.linepatrol.cut.dao.*"%>
<%@page import="com.cabletech.baseinfo.domainobjects.*"%>
<link rel='stylesheet' type='text/css'
	href='${ctx}/js/extjs/resources/css/ext-all.css' />
<script type='text/javascript'
	src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
<html>
	<head>
		<title>���꿼�˱�</title>
		<script type="text/javascript">
	toViewForm = function(id) {
		window.location.href = "${ctx}/appraiseTableMonthAction.do?method=viewTable&id="+id;
	}
	function toDelete(id){
		if(confirm("ȷ��Ҫɾ����ģ�壿")){
			location="${ctx}/appraiseTableMonthAction.do?method=deleteTable&id="+id;
		}
		
	}
</script>

		<body>
			<template:titile value="�����¿��˱�" />
			<display:table name="sessionScope.tables" id="table" pagesize="18" style="width:99%" >
				<logic:notEmpty name="table">
					<bean:define id="id" name="table" property="id" />
					<bean:define id="year" name="table" property="year" />
					<display:column property="tableName" title="���˱���" headerClass="subject" sortable="true" ></display:column>
					<display:column property="year" title="���" headerClass="subject"   sortable="true" />
					<display:column media="html" title="�ƶ�����"
						headerClass="subject" sortable="true">
							<bean:write name="table" property="createDate" format="yyyy-MM-dd" />
						</display:column>
					<display:column property="creater" title="�ƶ���"
						headerClass="subject" sortable="true" />
					<display:column media="html" title="����">
						<a href="javascript:toViewForm('${id}')">�鿴</a>
						<c:if test="${year > years}">
						| <a href="javascript:toDelete('${id}')">ɾ��</a>
						</c:if>
						| <html:link action="appraiseTableMonthAction.do?method=exportTable&id=${id}">����</html:link>
					</display:column>
				</logic:notEmpty>
			</display:table>
			
		</body>
</html>
