<%@ page language="java" pageEncoding="GBK"%>
<%@include file="/wap/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>任务派单</title>
		<script type="text/javascript">
	showDiv = function(value) {
		if (value == "0") {
			toUrlForm.submit();
		}
		if (value == "1") {
			location = "${ctx}/wap/dispatchtask/dispatch_task.do?method=queryForHandleDispatchTask&&env=wap";
		}
	};
</script>
	</head>
	<body>
		<div>
			<a class="dept1">${LOGIN_USER.userName} 您好！</a><a class="dept2"
				href="${ctx}/wap/navigation.jsp">功能导航</a><a class="dept"
				href="javascript:exitSys();">退出</a><br />
		</div>
		<br />
		<br />
		<br />
		<br />
		<table width="100%" style="text-align: center;">
			<tr>
				<td height="60px" valign="bottom" width="33.3%">
					<a href="javascript:showDiv('0');"><img
							src="${ctx}/images/wap/pd.png" border="0" /> <br />派单</a>
				</td>
				<td height="60px" valign="bottom">
					<a href="javascript:showDiv('1');"><img
							src="${ctx}/images/wap/dbgz.png" border="0" /> <br />待办工作</a>
				</td>
				<td height="60px" valign="bottom">
					<a href="${ctx}/wap/navigation.jsp"><img
							src="${ctx}/images/wap/fh.png" border="0" /> <br />返回</a>
				</td>
			</tr>
		</table>

		<div style="display: none;">
			<form id="toUrlForm" method="post"
				action="${ctx}/wap/load_processors.do?method=loadWapDeparts">
				<c:if test="${sessionScope.LOGIN_USER.deptype=='1'}">
					<input name="display_type" value="contractor" type="hidden" />
					<input name="input_type" value="checkbox" />
				</c:if>
				<c:if test="${sessionScope.LOGIN_USER.deptype=='2'}">
					<input name="display_type" value="mobile" type="hidden" />
					<input name="input_type" value="radio" />
				</c:if>
				<input name="exist_value" value="" type="hidden" />
				<input name="except_self" value="" type="hidden" />
				<input name="action_url"
					value="${ctx}/wap/dispatchtask/dispatch_task.do?method=dispatchTaskForm&&env=wap"
					type="hidden" />
				<input name="input_name" value="dept_id,user,mobile_id,user_id"
					type="hidden" />
			</form>
		</div>

	</body>
</html>