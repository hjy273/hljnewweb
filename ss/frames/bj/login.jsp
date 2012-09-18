<%@ page language="java" pageEncoding="UTF-8"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
com.cabletech.commons.config.GisConInfo gisConfig=com.cabletech.commons.config.GisConInfo.newInstance();
 %>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>${AppName }</title>
	<link rel="shortcut icon" type="image/x-icon" href="../../images/logo.ico" />
	<link href="${ctx}/frames/bj/css/login.css" rel="stylesheet" type="text/css" />
	
	<script type="text/javascript">
		function init(){
			document.getElementById('userid').focus();
		}
		function openwin(){
			window.open("http://<%=gisConfig.getBbsServerIp()%>:<%=gisConfig.getBbsServerPort()%>/bbs","知识交流");
		}
	</script>
</head>
<body onload="init()">
	
	<div class="login_bg">
		<div align="right" style="color: red;font-weight: bold;margin-right: 20px;margin-top: 20px">
			<c:if test="${REGION_ROOT =='110000' }">
			<a href="#" sytle="text-decoration:none" onclick="openwin()">${AppName}知识论坛入口</a>
			</c:if>
		</div>
		<div class="login_content">
			<div class="ge"></div>
			<div class="logo_${LogoImg}"></div>
			<div class="box">
				<span class="Sub_system_name">${FirstParty }</span> 
				<span class="System_Name">${AppName} </span>
			</div>
			<form id="loginForm" name="loginForm" method="post" action="${ctx}/frames/login.do?method=login">
				<input type="hidden" name="type" value="0,1,7,9"/>
				<div class="login">
					<div class="login_left">
						<label style="font-size: 14px; color: #FFCC33; font-weight: bold;">
						用户名: <input type="text" name="userid"  id="userid" class="login_input" /> 
						</label> <label style="font-size: 14px; color: #FFCC33; font-weight: bold;">
						密 &nbsp;&nbsp;码: <input type="password"  name="password" id="password" class="login_input" />
						</label>
					</div>
					
					<div class="login_right">
						<input type="submit" class="login_button"  title="登陆" value="登 陆" /> 
						<input type="reset"	class="login_button"  title="重置" value="取 消" /> 
					</div>
				</div>
				<div class="login_msg">${msg}</div>
			</form>
		</div>
		<div class="down">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				height="23">
				<tr>
					<td width="100%" align="center">
						${copyright }
					</td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>