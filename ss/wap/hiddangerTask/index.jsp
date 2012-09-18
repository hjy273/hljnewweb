<%@ page language="java" pageEncoding="GBK"%>
<%@include file="/wap/header.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=GBK">
		<title>隐患盯防</title>
		<script type="text/javascript">
	function query(state) {
		url = "${ctx}/wap/hiddangerAction.do?method=";
		if (state == '0') {
			location = url + "queryHiddangerNum&&env=wap";
		} else {
			location = url + "querySpecialForm&&env=wap";
		}
	}
	function back() {
		location = "${ctx}/wap/navigation.jsp";
	}
</script>
	</head>
	<body>
		<div>
			<a class="dept1">${LOGIN_USER.userName} 您好！</a><a class="dept2"
				href="${ctx}/wap/navigation.jsp">功能导航</a><a class="dept"
				href="javascript:exitSys();">退出</a>
		</div>
		<br />
		<br />
		<br />
		<br />
		<br />
		<table width="100%" style="text-align: center;">
			<tr>
				<td height="60px" valign="bottom" width="33.3%">
					<a
						href="${ctx}/wap/hiddangerAction.do?method=queryHiddangerNum&&env=wap"><img
							src="${ctx}/images/wap/隐患数.png" border="0" /> <br />隐患数</a>
				</td>
				<td height="60px" valign="bottom">
					<a
						href="${ctx}/wap/hiddangerAction.do?method=queryHiddangerSpecialForm&&env=wap"><img
							src="${ctx}/images/wap/dqdf.png" border="0" /> <br />当前盯防</a>
				</td>
				<td height="60px" valign="bottom">
					<a href="${ctx}/wap/navigation.jsp"><img
							src="${ctx}/images/wap/fh.png" border="0" /> <br />返回</a>
				</td>
			</tr>
		</table>

	</body>
</html>