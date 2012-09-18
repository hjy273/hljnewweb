<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script language="javascript" type="">
	function view(id){
		window.location.href = '${ctx}/oneStopQuick_inputCell.jspx?flag=edit&id='+id;
	}
</script>
<template:titile value="小区列表"/>
<display:table name="sessionScope.RESULTLIST" id="row"  pagesize="18" export="fasle">
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
		<a href="javascript:view('${row.id}')">查看</a>
	</display:column>
</display:table>
<br/>
