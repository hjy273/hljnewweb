<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
	media="screen, print" />
<script language="javascript" type="">
	function edit(id){
		window.location.href = '${ctx}/oneStopQuick_inputRepeater.jspx?flag=edit&id='+id;
	}
	function del(id){
    	if(confirm("删除将不能恢复，请确认是否要删除该直放站，按‘确定’删除？")){
			window.location.href = '${ctx}/oneStopQuick_deleteRepeater.jspx?id='+id;
    	}
	}
</script>
<body style="overflow-x: hidden;">
	<template:titile value="直放站列表" />
	<display:table name="sessionScope.RESULTLIST" id="row" pagesize="18"
		export="fasle" requestURI="${ctx }/oneStopQuick_listRepeater.jspx">
		<display:column property="repeaterType" title="直放站号" maxLength="15"
			sortable="true" />
		<display:column property="city" title="所属城市" maxLength="15"
			sortable="true" />
		<display:column property="installPlace" title="安装位置" maxLength="15"
			sortable="true" />
		<display:column title="覆盖范围" maxLength="15" sortable="true">
			<apptag:dynLabel dicType="overlay_area" objType="dic"
				id="${row.coverageArea}"></apptag:dynLabel>
		</display:column>
		<display:column title="覆盖区域类型" maxLength="15" sortable="true">
			<apptag:dynLabel dicType="overlay_area_type" objType="dic"
				id="${row.coverageAreaType}"></apptag:dynLabel>
		</display:column>
		<display:column property="attachBsc" title="归属BSC" maxLength="15"
			sortable="true" />
		<display:column title="信源基站" maxLength="15" sortable="true">
			<c:out value="${BASESTATIONS[row.msBsId]}"></c:out>
		</display:column>
		<display:column property="createTime" title="建站时间"
			format="{0,date,yyyy年MM月dd日}" maxLength="15" sortable="true" />
		<display:column title="维护单位" maxLength="15" sortable="true">
			<c:out value="${sessionScope.maintenances[row.maintenanceId]}"></c:out>
		</display:column>
		<display:column media="html" title="操作" paramId="tid">
			<a href="javascript:edit('${row.id}')">修改</a>
			<a href="javascript:del('${row.id}')">删除</a>
		</display:column>
	</display:table>
	<br />
	<div align="right">
		<input type="button" class="button"
			onClick="window.location.href ='/bspweb/resource/onestop/oneStopQuick_inputRepeater.jspx?flag=save'"
			value="添加直放站">
	</div>
</body>