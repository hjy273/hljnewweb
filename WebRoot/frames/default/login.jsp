<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@include file="/common/header.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>${AppName}-v${Version }</title>
<link rel="shortcut icon" type="image/x-icon" href="images/logo.ico" />
<link href="frames/default/css/login.css" rel="stylesheet"
	type="text/css" />
<style type="text/css">
.aa {
	-moz-user-select: none;
}
</style>

<script type="text/javascript">
	function init() {
		document.getElementById('userid').focus();
	}
	
</script>
</head>
<body onload="init()">
	<div class="login_bg">
		<div class="login_content">
			<div class="ge"></div>
			<div class="logo"></div>
			<div class="box">
				<span class="Sub_system_name">${FirstParty }</span> <span
					class="System_Name">${AppName}<span style="font-size: 10px">v${Version}</span>
				</span>
			</div>
			<form id="loginForm" name="loginForm" method="post"
				action="${ctx}/login_security.jspx">
				<div class="login">
					<div class="login_left">
						<label style="font-size: 14px; color: #FFCC33; font-weight: bold;">
							用户名: <input type="text" name="userid" id="userid"
							class="login_input" /> </label> <label
							style="font-size: 14px; color: #FFCC33; font-weight: bold;">
							密 &nbsp;&nbsp;码: <input type="password" name="password"
							id="password" class="login_input" /> </label>
					</div>

					<div class="login_right">
						<input type="submit" class="login_button" title="登录" value="登录" />
						<!-- input type="reset"	class="login_button"  title="重置" value="取 消" / -->
					</div>
				</div>
				<div class="login_msg">${msg}</div>
			</form>
		</div>
		<div class="copyright">版权所有· 2009-2011 北京鑫干线网络科技发展有限公司</div>
	</div>
</body>
</html>