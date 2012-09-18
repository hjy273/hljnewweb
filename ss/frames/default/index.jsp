<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@include file="/common/header.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>${AppName}</title>
<link href="./frames/default/css/header.css" rel="stylesheet" type="text/css" />
<link rel="shortcut icon" type="image/x-icon" href="images/logo.ico" />
<script language="javascript" type="text/javascript">
	$.get('/WebApp/sso/index.do?method=index&userid=${user.userID}', {
		Action : "get",
		password : "${user.password}",
		type : "0,1,7,9"
	}, function(data, textStatus) {
		document.getElementById("bodyFrame").src = '/WebApp/frames/bj/frame/main.jsp';
	});
	function gotoUrl(type){
		url = "";
		if(type == "index"){
			url = '/WebApp/frames/bj/frame/main.jsp';
			document.getElementById("bodyFrame").src=url;
		}
		if(type == "mywork"){
			
		}
		if(type == "1"){
			url = "/WebApp/frames/bj/frame/my_work.jsp";
			document.getElementById("bodyFrame").src=url;
		}
		if(type == "2"){
			url = "/bspweb/moduleLogin.do?method=login&type=2&userid=${user.userID}&pwd=${LOGIN_USER.password}";
			document.getElementById("bodyFrame").src=url;
		}
		if(type == "3"){
			url = "/bspweb/moduleLogin.do?method=login&type=3&userid=${user.userID}&pwd=${LOGIN_USER.password}";
			document.getElementById("bodyFrame").src=url;
		}
		if(type == "8"){
			url = "/bspweb/moduleLogin.do?method=login&type=8&userid=${user.userID}&pwd=${LOGIN_USER.password}";
			document.getElementById("bodyFrame").src=url;
		}
		if(type == "71"){
			toUrl("10","710","维护资源");
		}
		if(type == "72"){
			url = "/bspweb/moduleLogin.do?method=login&type=7&userid=${user.userID}&pwd=${LOGIN_USER.password}";
			document.getElementById("bodyFrame").src=url;
		}
		if(type == "9"){
			toUrl("7","712","系统管理");
		}
		if(type == "vrp"){
			window.open ("/vrp/locallogin.action?userId=${LOGIN_USER.userID }&password=${LOGIN_USER.password}");
		}	
		
	}
	toUrl = function(oneLevelId, twoLevelId, name) {
		document.getElementById("bodyFrame").src= "/WebApp/frames/bj/frame/work_main.jsp?mainmodulemenu_id=" + oneLevelId + "&submenu_id="
		+ twoLevelId+"&name=" + name ;
	};
	function exitSystem() {
		if (confirm("您确实要退出系统吗？")) {
			top.location = '/';
		}
	}
	function help() {
		URL = "${ctx}/frames/default/help.jsp";
		myleft = (screen.availWidth - 1024) / 2;
		mytop = 100;
		mywidth = 1024;
		myheight = 768;
		window
				.open(
						URL,
						"read_news",
						"height="
								+ myheight
								+ ",width="
								+ mywidth
								+ ",status=1,resizable=no,toolbar=no,menubar=no,location=no,scrollbars=yes,top="
								+ mytop + ",left=" + myleft + ",resizable=yes");
	}
	$(document).ready(
			function() {
				/* 菜单初始化 */
				$('#menu>ul>li>ul').find('li:has(ul:not(:empty))>a').append(
						"<span class='arrow'>></span>"); // 为有子菜单的菜单项添加'>'符号
				$("#menu>ul>li").bind('mouseover', function() // 顶级菜单项的鼠标移入操作 mouseover
				{
					$(this).children('ul').slideDown('fast');
				}).bind('mouseleave', function() // 顶级菜单项的鼠标移出操作 mouseleave
				{
					$(this).children('ul').slideUp('fast');
				});
				$('#menu>ul>li>ul li').bind('mouseover', function() // 子菜单的鼠标移入操作
				{
					$(this).children('ul').slideDown('fast');
				}).bind('mouseleave', function() // 子菜单的鼠标移出操作
				{
					$(this).children('ul').slideUp('fast');
				});
			});

	function reinitIframe() {
		var iframe = document.getElementById("bodyFrame");
		try {
			var bHeight = iframe.contentWindow.document.body.scrollHeight;
			var dHeight = document.body.scrollHeight-130;//550
			var height = Math.max(bHeight, dHeight);
			iframe.height = height;
			var bwidth = document.body.scrollWidth;
			var dwidth = 890;//document.documentElement.scrollWidth;
			var width = Math.max(bwidth - 2, dwidth);
			iframe.width = width;
		} catch (ex) {
		}
	}
	window.setInterval("reinitIframe()", 200);
</script>
</head>
<html>
<%@ include file="top.jsp"%>
<iframe id="bodyFrame" name="bodyFrame" scrolling="no" frameborder="0" onload="this.height=400;"></iframe>
<%@ include file="bottom.jsp"%>
</html>