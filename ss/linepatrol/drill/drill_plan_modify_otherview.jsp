<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@page import="com.cabletech.linepatrol.drill.module.*" %>

<html>
	<head>
		<title>�鿴�����������</title>
	</head>
	<body>
		<jsp:include page="drill_plan_modify_base.jsp"/>
		<div align="center" style="height:40px">
			<html:button property="button" styleClass="button" onclick="closeWin();">�ر�</html:button>
		</div>
	</body>
	<script type="text/javascript">
		function closeWin(){
			parent.win.close();
		}
	</script>
</html>
