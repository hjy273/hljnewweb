<%@include file="/common/header.jsp"%>
<%@ page contentType="text/html; charset=GBK"%>
<html>
	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
		media="screen, print" />
	<head>
		<title>巡检明细信息表</title>

		<style type="text/css">
.rc {
	color: red;
}
</style>

		<script type="text/javascript">
	
</script>

	</head>
	<body>
		<template:titile value="巡检表情况" />
		<div
			style="width: 100%; height: 23px; margin-top: 10px; border-bottom: 1px dashed gray;">
			<div style="height: 100%; float: left; margin-right: 10px;">
				<span style="display: block; float: left;">资源名称：</span>
			</div>
			<div style="height: 100%; float: left;; margin-right: 10px;">
				${vo.tresourcename }
			</div>
			<div style="height: 100%; float: left; margin-right: 10px;">
				资源类型：
			</div>
			<div style="height: 100%; float: left;; margin-right: 10px;">
				${vo.tresourcetype }
			</div>
			<div style="height: 100%; float: left; margin-right: 10px;">
				巡检人：
			</div>
			<div style="height: 100%; float: left;; margin-right: 10px;">
				${vo.tpatrolmen }
			</div>
			<div style="height: 100%; float: left; margin-right: 10px;">
				巡检日期：
			</div>
			<div style="height: 100%; float: left;; margin-right: 10px;">
				${vo.tpatroldate }
			</div>
			<div style="height: 100%; float: left; margin-right: 10px;">
				<span style="color: red;">问题项数：</span>
			</div>
			<div style="height: 100%; float: left;; margin-right: 10px;">
				<span style="color: red;">${vo.texceptioncount }</span>
			</div>
		</div>

		<display:table name="sessionScope.vos" id="currentRowObject"
			pagesize="18">
			<display:column title="维护对象" property="tpkey" />
			<display:column title="维护检测项目" property="tkey" />
			<display:column title="巡检结果" media="html">
				<c:choose>
					<c:when test="${currentRowObject.tcheck=='1'}">
						<span class="rc">${currentRowObject.tvalue }</span>
					</c:when>
					<c:otherwise>
						<span>${currentRowObject.tvalue }</span>
					</c:otherwise>
				</c:choose>
			</display:column>
			<display:column title="巡检描述" property="tdesc" />
		</display:table>
		<div align="center">
			<input type="button" class="button" value="返回"
				onclick="javascript:history.go(-1);">
		</div>

	</body>
</html>