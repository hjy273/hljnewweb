<%@ page language="java" pageEncoding="GBK"%>
<%@include file="/wap/header.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=GBK">
		<title>���Ϲ���</title>
		<script type="text/javascript">
	function query(state) {
		url = "${ctx}/wap/troubleAction.do?method=";
		if (state == '0') {
			location = url + "queryTrouble&&env=wap";
		} else {
			location = url + "queryApprove&&env=wap";
		}
	}
	function back() {
		location = "${ctx}/wap/navigation.jsp";
	}
</script>
	</head>
	<body>
		<div>
			<a class="dept1">${LOGIN_USER.userName} ���ã�</a><a class="dept2"
				href="${ctx}/wap/navigation.jsp">���ܵ���</a><a class="dept"
				href="javascript:exitSys();">�˳�</a><br />
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
						href="${ctx}/wap/troubleAction.do?method=queryTroubleNum&&env=wap"><img
							src="${ctx}/images/wap/dclgz.png" border="0" /> <br />���ϴ������</a>
				</td>
				<td height="60px" valign="bottom">
					<a href="${ctx}/wap/troubleAction.do?method=queryApprove&&env=wap"><img
							src="${ctx}/images/wap/dshgzpng" border="0" /> <br />����˹���</a>
				</td>
				<td height="60px" valign="bottom">
					<a href="${ctx}/wap/navigation.jsp"><img
							src="${ctx}/images/wap/fh.png" border="0" /> <br />����</a>
				</td>
			</tr>
		</table>
	</body>
</html>