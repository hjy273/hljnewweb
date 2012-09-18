<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />
<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
<html>
	<head>
		<title>���ϲ�ѯ�б�</title>
		<script type="text/javascript">
			toViewSafeguardEndPlan=function(spplanId,endId){
            	var url = "${ctx}/specialEndPlanAction.do?method=specialEndPlanForward&id="+spplanId+"&spEndId="+endId+"&type=view";
				win = new Ext.Window({
					layout : 'fit',
					width:650,
					height:400,
					resizable:true,
					closeAction : 'close',
					modal:true,
					html:'<iframe scrolling=auto frameborder=0 width=100% height=100% src="'+url+'" />',
					plain: true
				});
				win.show(Ext.getBody());
			}
		</script>
	</head>
	<%
		BasicDynaBean object = null;
		Object spendId = null;
		Object spId = null;
	%>
	<body>
		<template:titile value="���Ϸ�����ֹ�б�"/>
		<display:table name="sessionScope.list" id="safeguard" pagesize="18">
			<display:column title="��ֹ���" media="html"  sortable="true">
				<% object = (BasicDynaBean ) pageContext.findAttribute("safeguard");
	            if(object != null) {
	               spendId = object.get("sp_end_id");
	               spId = object.get("plan_id");
				} %>
      			<a href="javascript:toViewSafeguardEndPlan('<%=spId%>','<%=spendId%>')"><%=spendId%></a> 
			</display:column>
			<display:column property="plan_name" title="��Ѳ�ƻ�" headerClass="subject"  sortable="true"/>
			<display:column property="end_type" title="��ֹ����" headerClass="subject"  sortable="true"/>
			<display:column property="prev_end_date" title="��ֹǰ��ֹ����" headerClass="subject" sortable="true"/>
			<display:column property="end_date" title="��ֹ���ֹ����" headerClass="subject"  sortable="true"/>
			<display:column property="creater" title="������" headerClass="subject"  sortable="true"/>
			<display:column property="create_time" title="��������" headerClass="subject"  sortable="true"/>
		</display:table>
	</body>
</html>

