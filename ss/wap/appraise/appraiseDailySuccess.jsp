<%@ page language="java" pageEncoding="GBK"%>
<%@include file="/wap/header.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=GBK">
		<script type="text/javascript">
		function back(){
			window.location.href="${ctx}/wap/appraise/index.jsp";
		}
</script>
	</head>
	<body>
		<div>
			<a class="dept1">${LOGIN_USER.userName} ���ã�</a><a class="dept2"
				href="${ctx}/wap/navigation.jsp">���ܵ���</a><a class="dept"
				href="javascript:exitSys();">�˳�</a>
		</div>
		<br />
		<br />
		<br />
		<br />
		<br />
		<div align="center">�����ճ����˳ɹ���</div>
		<div>		<input type="button" value="����" onclick="back()"></div>

	</body>
</html>