<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<script type="text/javascript" language="javascript">
		 	function approve(id){
				window.location.href="${ctx}/resAction.do?method=approveLink&type=LP_ACCEPTANCE_CABLE&id="+id;
		 	}
		</script>
		<title></title>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
	</head>
	<body>
		<template:titile value="中继段待审核列表" />
		<display:table name="sessionScope.result" id="currentRowObject" pagesize="15">
			<display:column media="html" title="中继段编号" sortable="true" >
			<a href="javascript:view('${currentRowObject.kid}')">${currentRowObject.segmentid}</a>
			</display:column>
			<display:column property="assetno" title="资产编号" sortable="true"/>
			<display:column property="segmentname" title="中继段名称" maxLength="10" sortable="true"/>
      		<display:column property="fiberType" title="纤芯型号" maxLength="10" sortable="true"/>
			<display:column media="html" title="光缆级别" sortable="true">
				<c:out value="${cabletype[currentRowObject.cableLevel]}" />  
			</display:column>
			<display:column media="html" sortable="true" title="敷设方式" >
				<c:forEach var="s" items="${fn:split(currentRowObject.laytype,',')}">
					${layingmethod[s]} 
				</c:forEach>
			</display:column>
			<display:column property="grossLength" title="光缆长度" headerClass="subject"  sortable="true"/>
			<display:column property="producer" title="厂家" sortable="true" />
			<display:column media="html" sortable="true" title="交维日期" >
				${currentRowObject.finishtime}
			</display:column>
			<display:column media="html" sortable="true" title="代维" >
				<apptag:assorciateAttr table="contractorinfo" label="contractorname" key="contractorid" keyValue="${currentRowObject.maintenanceId}" />
			</display:column>
			<display:column media="html">
				<a href="javascript:approve('${currentRowObject.kid}')" >审核</a>
			</display:column>
		</display:table>
	</body>
</html>
