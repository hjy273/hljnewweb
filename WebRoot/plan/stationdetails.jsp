<%@include file="/common/header.jsp"%>
<%@ page contentType="text/html; charset=GBK"%>
<html>
	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
		media="screen, print" />
	<head>
		<title>Ѳ����ϸ��Ϣ��</title>

		<style type="text/css">
.rc {
	color: red;
}
</style>

		<script type="text/javascript">
	
</script>

	</head>
	<body>
		<template:titile value="Ѳ������" />
		<div
			style="width: 100%; height: 23px; margin-top: 10px; border-bottom: 1px dashed gray;">
			<div style="height: 100%; float: left; margin-right: 10px;">
				<span style="display: block; float: left;">��Դ���ƣ�</span>
			</div>
			<div style="height: 100%; float: left;; margin-right: 10px;">
				${vo.tresourcename }
			</div>
			<div style="height: 100%; float: left; margin-right: 10px;">
				��Դ���ͣ�
			</div>
			<div style="height: 100%; float: left;; margin-right: 10px;">
				${vo.tresourcetype }
			</div>
			<div style="height: 100%; float: left; margin-right: 10px;">
				Ѳ���ˣ�
			</div>
			<div style="height: 100%; float: left;; margin-right: 10px;">
				${vo.tpatrolmen }
			</div>
			<div style="height: 100%; float: left; margin-right: 10px;">
				Ѳ�����ڣ�
			</div>
			<div style="height: 100%; float: left;; margin-right: 10px;">
				${vo.tpatroldate }
			</div>
			<div style="height: 100%; float: left; margin-right: 10px;">
				<span style="color: red;">����������</span>
			</div>
			<div style="height: 100%; float: left;; margin-right: 10px;">
				<span style="color: red;">${vo.texceptioncount }</span>
			</div>
		</div>

		<display:table name="sessionScope.vos" id="currentRowObject"
			pagesize="18">
			<display:column title="ά������" property="tpkey" />
			<display:column title="ά�������Ŀ" property="tkey" />
			<display:column title="Ѳ����" media="html">
				<c:choose>
					<c:when test="${currentRowObject.tcheck=='1'}">
						<span class="rc">${currentRowObject.tvalue }</span>
					</c:when>
					<c:otherwise>
						<span>${currentRowObject.tvalue }</span>
					</c:otherwise>
				</c:choose>
			</display:column>
			<display:column title="Ѳ������" property="tdesc" />
		</display:table>
		<div align="center">
			<input type="button" class="button" value="����"
				onclick="javascript:history.go(-1);">
		</div>

	</body>
</html>