<%@ page language="java" pageEncoding="GBK"%>
<%@include file="/wap/header.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=GBK">
		<title>�ܵ��б�</title>
		<script type="text/javascript">
	function back() {
		location = "${ctx}/wap/resourceAction.do?method=queryPipeForm&&env=wap";
	}
</script>
	</head>
	<body>
		<div>
			<a class="dept1">${LOGIN_USER.userName} ���ã�</a><a class="dept2"
				href="${ctx}/wap/navigation.jsp">���ܵ���</a><a class="dept"
				href="javascript:exitSys();">�˳�</a><br /><br />
		</div>
		<template:titile value="�ܵ��б�"/>
		${PIPE}
		<br />
		<center>
			<input type="button" value="����" onclick="back();" />
		</center>
	</body>
</html>