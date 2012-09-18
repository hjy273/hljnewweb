<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script language="javascript" type="">
	function view(id){
		window.location.href = '${ctx}/stationAction_view.jspx?flag=view&id='+id;
	}
	function edit(id){
		window.location.href = '${ctx}/stationAction_view.jspx?flag=edit&id='+id;
	}
	function del(id){
    	if(confirm("删除将不能恢复，请确认是否要删除该基站，按‘确定’删除？")){
			window.location.href = '${ctx}/stationAction_delete.jspx?id='+id;
    	}
	}
</script>
<template:titile value="查询基站信息结果"/>
<display:table name="sessionScope.RESULTLIST" id="row"  pagesize="18" export="fasle">
	<display:column property="code" title="基站编号"  maxLength="15"/>
	<display:column property="stationname" title="基站名称"  maxLength="15"/>
	<display:column property="stationtype" title="基站类型"  maxLength="15"/>
	<display:column property="stationlevel" title="基站级别"  maxLength="15"/>
	<display:column property="regionname" title="区域"  maxLength="15"/>
	<display:column media="html" title="操作" paramId="tid">
		<a href="javascript:view('${row.tid}')">查看</a>

		<a href="javascript:edit('${row.tid}')">修改</a>
		<a href="javascript:del('${row.tid}')">删除</a>
	</display:column>
</display:table>