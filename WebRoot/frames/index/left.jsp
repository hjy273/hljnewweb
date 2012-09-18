<%@ page contentType="text/html; charset=GBK" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>巡检系统</title>
<link href="${ctx}/css/divstyle.css" rel="stylesheet" type="text/css">
<link href="${ctx}/css/style.css" rel="stylesheet" type="text/css">
</head>
<script type="text/javascript">
function index(){
	parent.frames.mainFrame.frames.location.href='${ctx}/DesktopAction.do?method=loadinfo';
}

//转到桌面
function goDeskTop(){
    var businessType =  ${sessionScope.businessType}
	parent.frames.mainFrame.frames.location.href='${ctx}/DesktopAction.do?method=loadinfo&type='+businessType;
}

function vrp(){
	window.open ("/vrp/locallogin.action?userId=${LOGIN_USER.userID }&password=${LOGIN_USER.password}");
}
</script>
<body class="left_border">
	<div class="left">
		<div class="layout_left_1">
			<table width="100%"  border="0" cellspacing="0" cellpadding="0">
				<tr align="center" valign="middle">
				  <td width="27%" valign="middle"><img src="${ctx}/images2/bg/left_arrow.gif" width="21"></td>
				  <td width="38%" align="left" valign="bottom" class="menu_title">功能菜单</td>
				  <td width="15%" align="left">&nbsp;</td>
				  <td width="20%" align="left" valign="bottom"><a href="javascript:goDeskTop()">桌面</a></td>
				</tr>
			</table>
		</div>
		<div class="layout_leftbg">
			<iframe src="treediv.jsp" id="tree" width="100%" height="90%" scrolling="no" frameborder="no"></iframe>
		</div>
	</div>
</body>
</html>
