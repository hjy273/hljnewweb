<%@ page language="java" pageEncoding="GBK"%>
<%@include file="/wap/header.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=GBK">
		<title>资料查询</title>
		<script type="text/javascript">
	function query(state) {
		url = "${ctx}/wap/resourceAction.do?method=";
		if (state == '0') {
			location = url + "queryRepeaterSectionForm&&env=wap";
		} else {
			location = url + "queryPipeForm&&env=wap";
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
						href="${ctx}/wap/resourceAction.do?method=queryRepeaterSectionForm&&env=wap"><img
							src="${ctx}/images/wap/zjdcx.png" border="0" /> <br />中继段查询</a>
				</td>
				<td height="60px" valign="bottom">
					<a
						href="${ctx}/wap/resourceAction.do?method=queryPipeForm&&env=wap"><img
							src="${ctx}/images/wap/gdcx.png" border="0" /> <br />管道查询</a>
				</td>
				<td height="60px" valign="bottom">
					<a href="${ctx}/wap/navigation.jsp"><img
							src="${ctx}/images/wap/fh.png" border="0" /> <br />返回</a>
				</td>
			</tr>
		</table>
	</body>
</html>