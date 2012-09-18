<%@include file="/wap/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
		<title>${AppName}</title>
		<link rel="shortcut icon" type="image/x-icon" href="../../images/logo.ico" />
	</head>
	<body>
		
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td style="text-align:center;height:10px;font-size: 22px">
						${FirstParty }
					</td>
				</tr>
				<tr>
					<td style="text-align:center;font-size: 22px;">
						${AppName}
					</td>
				</tr>
				<tr>
					<td style="text-align:center;height:15px;">
						
					</td>
				</tr>
				<tr>
					<td style="text-align:center;">
					<form id="loginForm" name="loginForm" method="post"
			action="${ctx}/wap/login.do?method=login&&env=wap">
						<div>用户名：</div>
						<div><input type="text" name="userid" value="${userId}" id="userid" size="15" /></div>
						<div>密码：</div>
						<div><input type="password" name="password" id="password" size="15" /></div>
						<div><input type="submit" name="btnLogin" size="5" value="登   录" /></div>
					</form>	
					</td>
				</tr>
				<tr>
				<td style="height:25px;">
						
				</td>
				</tr>	
				<tr>
				<td style="text-align:center;">${copyright}<br>
				由<a style="color:blue;" href="http://www.cabletech.com.cn">北京鑫干线</a>提供
				</td>
				</tr>
			</table>
		
	</body>
</html>
