<%@ page language="java" pageEncoding="GBK"%>
<%@include file="/wap/header.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=GBK">
		<title>ά��ָ���б�</title>
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
		<template:titile value="ά��ָ���б�"/>
		<br />
		һ��ָ����ͳ�ƣ�<br />
		<img src="${ctx}/wap/mainGuideBarChartAction.do?method=generateChart&width=300&heigth=180&CategoryType=10" width="300px" height="180" border=0 />
		<br />
		һ��ָ����ͳ�ƣ�<br />
		<img src="${ctx}/wap/yearGuideBarChartAction.do?method=generateChart&width=300&heigth=180&CategoryType=100" width="300px" height="180" border=0 />
		<br />
		����ָ����ͳ�ƣ�<br />
		<img src="${ctx}/wap/normGuideBarChartAction.do?method=generateChart&width=300&heigth=180&CategoryType=21" width="300px" height="180" border=0 /><br />
		<img src="${ctx}/wap/normGuideBarChartAction.do?method=generateChart&width=300&heigth=180&CategoryType=22" width="300px" height="180" border=0 /><br />
		<img src="${ctx}/wap/normGuideBarChartAction.do?method=generateChart&width=300&heigth=180&CategoryType=23" width="300px" height="180" border=0 /><br />
		����ָ����ͳ�ƣ�<br />
		<img src="${ctx}/wap/yearGuideBarChartAction.do?method=generateChart&width=300&heigth=180&CategoryType=21" width="300px" height="180" border=0 /><br />
		<img src="${ctx}/wap/yearGuideBarChartAction.do?method=generateChart&width=300&heigth=180&CategoryType=22" width="300px" height="180" border=0 /><br />
		<img src="${ctx}/wap/yearGuideBarChartAction.do?method=generateChart&width=300&heigth=180&CategoryType=23" width="300px" height="180" border=0 /><br />
		<center>
			<input type="button" value="����" onclick="back();" />
		</center>
	</body>
</html>