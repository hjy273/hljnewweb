<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script language="javascript" type="">
	function view(id){
		window.location.href = '${ctx}/MenuManageAction.do?method=viewFirstMenu&flag=view&id='+id;
	}
	function edit(id){
		window.location.href = '${ctx}/MenuManageAction.do?method=viewFirstMenu&flag=edit&id='+id;
	}
	function del(id){
    	if(confirm("确定删除该菜单？")){
			window.location.href = '${ctx}/MenuManageAction.do?method=deleteFirstMenu&id='+id;
    	}
	}
</script>
<template:titile value="查询一级菜单结果"/>
<display:table name="sessionScope.list" id="row" pagesize="18" export="true">
	<display:column property="id" title="菜单id"/>
	<display:column property="lablename" title="菜单名称"/>
	<display:column property="businessTypeName" title="业务类型"/>
	<display:column property="imgurl" title="图标路径"/>
	<display:column property="showno" title="序号"/>
	<display:column media="html" title="查看">
		<a href="javascript:view('${row.id}')">查看</a>
	</display:column>
	<display:column media="html" title="修改">
		<a href="javascript:edit('${row.id}')">修改</a>
	</display:column>
	<display:column media="html" title="删除">
		<a href="javascript:del('${row.id}')">删除</a>
	</display:column>
</display:table>