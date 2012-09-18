<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script language="javascript" type="">
	function edit(id){
		window.location.href = '${ctx}/oneStopQuick_inputCell.jspx?flag=edit&id='+id;
	}
	function del(id){
    	if(confirm("删除将不能恢复，请确认是否要删除该小区，按‘确定’删除？")){
			window.location.href = '${ctx}/oneStopQuick_deleteCell.jspx?id='+id;
    	}
	}
</script>
<body style="overflow-x:hidden;">
<template:titile value="小区列表"/>
<display:table name="sessionScope.RESULTLIST" id="row"  pagesize="18" export="fasle" requestURI="${ctx }/oneStopQuick_listCell.jspx">
	<display:column title="站址编号" maxLength="15" sortable="true">
		<c:out value="${sessionScope.BASESTATIONS[row.parentId]}"  />
	</display:column>
	<display:column property="cellCode" title="小区号"  maxLength="15" sortable="true"/>
	<display:column property="chineseName" title="中文名称"  maxLength="15" sortable="true"/>
	<display:column title="是否调频"  maxLength="15" sortable="true">
		<c:if test="${row.isFrequencyHopping == 'y'}">是</c:if>
		<c:if test="${row.isFrequencyHopping != 'y'}">否</c:if>
	</display:column>
	<display:column title="是否下带直放站"  maxLength="15" sortable="true">
		<c:if test="${row.isOwnRepeater == 'y'}">是</c:if>
		<c:if test="${row.isOwnRepeater != 'y'}">否</c:if>
	</display:column>
	<display:column property="carrierFrequencyNum" title="载频数"  maxLength="15" sortable="true"/>
	<display:column property="anTime" title="入网时间" format="{0,date,yyyy年MM月dd日}" maxLength="15" sortable="true"/>
	<display:column media="html" title="操作" paramId="tid">
		<a href="javascript:edit('${row.id}')">修改</a>
		<a href="javascript:del('${row.id}')">删除</a>
	</display:column>
</display:table>
<br/>
<div align="right"><input type="button" class="button" onClick="window.location.href ='/bspweb/resource/onestop/oneStopQuick_inputCell.jspx?flag=save'" value="添加小区"> </div>
</body>