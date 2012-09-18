<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link type="text/css" rel="stylesheet" href="${ctx}/frames/bj/css/css.css" />
		<link type="text/css" rel="stylesheet"
			href="../js/jscal2/src/css/jscal2.css" />
		<link type="text/css" rel="stylesheet"
			href="../js/jscal2/src/css/cabletech/gold.css" />
		<style>
body {
	text-align: left;
	margin: 0;
	padding: 0;
	font: normal 12px/ 150% simsun;
	color: #000;
}

.highlight {
	color: #f00 !important;
	font-weight: bold
}

.highlight2 {
	color: #090 !important;
	font-weight: bold
}

.birthday {
	background: #fff;
	font-weight: bold
}

.birthday.DynarchCalendar-day-selected {
	background: #89f;
	font-weight: bold
}

.txt_blue {
	color: #015FB6;
}

.txt_blue a {
	color: #015FB6;
}

input {
	padding: 0 10px;
	height: 20px;
	border: 1px #ccc solid;
	background: url(../images/btnbg15.gif) repeat-x;
	font-size: 12px;
	font-weight: 700;
	color: #000;
	text-decoration: none;
	line-height: 28px;
	cursor: pointer;
}
</style>
<script>
function open_notify(NOTICE_ID,FORMAT)
{
 URL="${ctx}/NoticeAction.do?method=showNotice&id="+NOTICE_ID;
 myleft=(screen.availWidth-650)/2;
 mytop=100
 mywidth=650;
 myheight=500;
 if(FORMAT=="1")
 {
    myleft=0;
    mytop=0
    mywidth=screen.availWidth-10;
    myheight=screen.availHeight-40;
 }
 window.open(URL,"read_news","height="+myheight+",width="+mywidth+",status=1,resizable=no,toolbar=no,menubar=no,location=no,scrollbars=yes,top="+mytop+",left="+myleft+",resizable=yes");
}
</script>
		<title>Main</title>
	</head>
	<body>
		<div class="Announcement_bg">
			<div class="more">
				
			</div>
			<img src="${ctx}/frames/bj/images/info_tab.jpg" height="28" />
		</div>
		<br />
		<div>
			${requestScope.notice }
		</div>
		
		<div align="right">
			<a href="javascript:window.history.go(-1)">返回</a>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</div>
	</body>
</html>

