<%@ page language="java" pageEncoding="GBK"%>
<%@include file="/wap/header.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="test/html; charset=GBK">
		<title>ͨѶ¼��ѯ</title>
		<script type="text/javascript">
	function back() {
		location = "${ctx}/wap/navigation.jsp";
	}
</script>
	</head>
	<body>
		<div>
			<a class="dept1">${LOGIN_USER.userName} ���ã�</a><a class="dept2"
				href="${ctx}/wap/navigation.jsp">���ܵ���</a><a class="dept"
				href="javascript:exitSys();">�˳�</a><br /><br />
		</div>
		<div>
			<template:titile value="ͨѶ¼��ѯ"/>
		</div>
		<form action="${ctx}/wap/index.do?method=addressBook" name="form"
			method="post">
			<table width="100%" border="0" style="text-align: center;">
				<tr align="center">
					<td>
						������
					</td>
					<td>
						<input type="text" name="name" size="15" />
					</td>
				</tr>
				${contractorHtml }
			</table>
			<center>
				<input type="submit" name="query" value="��ѯ" />
				<input type="button" value="����" onclick="back();" />
			</center>
		</form>
	</body>
</html>
