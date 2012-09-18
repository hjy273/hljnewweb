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
		<template:titile value="�м̶δ�����б�" />
		<display:table name="sessionScope.result" id="currentRowObject" pagesize="15">
			<display:column media="html" title="�м̶α��" sortable="true" >
			<a href="javascript:view('${currentRowObject.kid}')">${currentRowObject.segmentid}</a>
			</display:column>
			<display:column property="assetno" title="�ʲ����" sortable="true"/>
			<display:column property="segmentname" title="�м̶�����" maxLength="10" sortable="true"/>
      		<display:column property="fiberType" title="��о�ͺ�" maxLength="10" sortable="true"/>
			<display:column media="html" title="���¼���" sortable="true">
				<c:out value="${cabletype[currentRowObject.cableLevel]}" />  
			</display:column>
			<display:column media="html" sortable="true" title="���跽ʽ" >
				<c:forEach var="s" items="${fn:split(currentRowObject.laytype,',')}">
					${layingmethod[s]} 
				</c:forEach>
			</display:column>
			<display:column property="grossLength" title="���³���" headerClass="subject"  sortable="true"/>
			<display:column property="producer" title="����" sortable="true" />
			<display:column media="html" sortable="true" title="��ά����" >
				${currentRowObject.finishtime}
			</display:column>
			<display:column media="html" sortable="true" title="��ά" >
				<apptag:assorciateAttr table="contractorinfo" label="contractorname" key="contractorid" keyValue="${currentRowObject.maintenanceId}" />
			</display:column>
			<display:column media="html">
				<a href="javascript:approve('${currentRowObject.kid}')" >���</a>
			</display:column>
		</display:table>
	</body>
</html>
