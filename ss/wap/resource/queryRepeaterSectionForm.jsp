<%@ page language="java" pageEncoding="GBK"%>
<%@include file="/wap/header.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="test/html; charset=GBK">
		<title>�м̶β�ѯ</title>
		<script type="text/javascript">
	function back() {
		location = "${ctx}/wap/resourceAction.do?method=index&&env=wap";
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
			<template:titile value="�м̶β�ѯ"/>
		</div>
		<form
			action="${ctx}/wap/resourceAction.do?method=queryRepeaterSection"
			name="form" method="post">
			<table width="100%" border="0" style="text-align: center;">
				<tr align="center">
					<td>
						�м̶����ƣ�
					</td>
					<td>
						<input type="text" name="segmentName" size="15" />
					</td>
				</tr>
				<tr align="center">
					<td>
						��ά�·ݣ�
					</td>
					<td>
						<input type="text" name="finishTime" size="15" value="${date }" />
					</td>
				</tr>
				${contractorHtml }
			</table>
			<center>
				<input type="submit" name="query" value="��ѯ">
				<input type="button" value="����" onclick="back();" />
			</center>
		</form>
	</body>
</html>
