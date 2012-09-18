<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link type="text/css" rel="stylesheet" href="../css/load.css" />
<link type="text/css" rel="stylesheet" 	href="../js/jscal2/src/css/jscal2.css" />
<link type="text/css" rel="stylesheet" 	href="../js/jscal2/src/css/cabletech/gold.css" />
<script src="../js/jscal2/src/js/jscal2.js"></script>
<script src="../js/jscal2/src/js/lang/cn.js"></script>
<script type="text/javascript" language="javascript" 	src="${ctx}/js/prototype.js"></script>
<style type="">
	.birthday { background: #fff; font-weight: bold }
	.birthday.DynarchCalendar-day-selected { background: #89f; font-weight: bold }
	.txt_blue {color:#015FB6;}
	.txt_blue a {color:#015FB6;}
</style>
	
<script type="text/javascript">
		//ajax 加载消息信息
		  function getNoticeContent(){
		    $("divnotice1").innerHTML = "数据加载中........<img src='${ctx}/images/onloading.gif' border='0'/>";
		     var pars = Math.random();
		     var url = "${ctx}/NoticeAction.do?method=showNewNotices&&shownum=7&type=公告";
		     url=encodeURI(url);
		     new Ajax.Request(url,
		      {
		        method:"post",
		        parameters:pars,
		        onSuccess: function(transport){
		          var response = transport.responseText || "加载数据为空！";
		          $("divnotice1").innerHTML = response;
		        },
		        onFailure: function(){  $("divnotice3").innerHTML = "加载失败......"; }
		      });
		     
		  }
		  
		  function getNewContent(){
			     var pars = Math.random();
			     var url = "${ctx}/NoticeAction.do?method=showNewNotices&&shownum=7&type=新闻";
			     url=encodeURI(url);
			     new Ajax.Request(url,
			      {
			        method:"post",
			        parameters:pars,
			        onSuccess: function(transport){
			          var response = transport.responseText || "加载数据为空！";
			          $("divnotice3").innerHTML = response;
			        },
			        onFailure: function(){  $("divnotice3").innerHTML = "加载失败......"; }
			      });
			     
		  }
		  function getNewMeeting(){
			     var pars = Math.random();
			     var url = "${ctx}/NoticeAction.do?method=showNewNotices&&shownum=7&type=会议";
			     url=encodeURI(url);
			     new Ajax.Request(url,
			      {
			        method:"post",
			        parameters:pars,
			        onSuccess: function(transport){
			          var response = transport.responseText || "加载数据为空！";
			          $("divnotice2").innerHTML = response;
			        },
			        onFailure: function(){  $("divnotice2").innerHTML = "加载失败......"; }
			      });
			     
			  }
		  
		  //ajax加载待办工单
		   function getUndoSheet(){
		    $("divundosheet").innerHTML = "数据加载中........<img src='${ctx}/images/onloading.gif' border='0'/>";
		     var pars = Math.random();
		     new Ajax.Request('${ctx}/frames/wait_process_work.do?method=showWaitProcessWorkNum&isdowork=1',
		      {
		        method:"post",
		        parameters:pars,
		        onSuccess: function(transport){
		          var response = transport.responseText || "加载数据为空！";
		          $("divundosheet").innerHTML = response;
		        },
		        onFailure: function(){  $("divundosheet").innerHTML = "加载失败。。。。。"; }
		      });
		     
		  }
		  
		  function ajaxLoad(){
		    getNoticeContent(); //加载公告
		    getNewMeeting();
		    getNewContent();
		    getUndoSheet(); //加载待办工单
		  }
		  toUrl = function(oneLevelId, twoLevelId, name) {
			  parent.bodyFrame.location = "${ctx}/frames/bj/frame/work_main.jsp?mainmodulemenu_id=" + oneLevelId + "&submenu_id="
				+ twoLevelId+"&name=" + name ;
		 }
		  function open_notify(NOTICE_ID,FORMAT)
		  {
		   URL="${ctx}/NoticeAction.do?method=showNotice&id="+NOTICE_ID+"&model=brower&preview=false";
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

<script>

	function secBoard(n){
		for(i=0;i<menu.childNodes.length;i++){
			menu.childNodes[i].className="sec1";
			menu.childNodes[n].className="sec2";
		}
		for(i=0;i<main.childNodes.length;i++){
			main.childNodes[i].style.display="none";
			main.childNodes[n].style.display="block";
		}
	}
	</script>
<title>首页</title>
</head>
<body onload="ajaxLoad()">
<table width="100%" cellpadding="0" cellspacing="1">
	<tr>
		<td width="280" valign="top">
		<span> <%@include file="/frames/bj/frame/left.jsp"%> </span></td>
		<td width="1"></td>
		<td valign="top">
			<div class="Framework">
			<div class="title_bg">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="45px"><img src="../images/gg.jpg" /></td>
						<td align="left">
						<ul id="menu" class="tabmenu">
							<li id="sec0" onMouseOver="secBoard(0)" class="sec2">公告栏</li>
							<li id="sec1" onMouseOver="secBoard(1)" class="sec1">会&nbsp;议</li>
							<li id="sec2" onMouseOver="secBoard(2)" class="sec1">新&nbsp;闻</li>
						</ul>
						</td>
						<td align="right"><img src="../images/right.jpg" /></td>
					</tr>
				</table>
			</div>
			<div class="Announcement">
				<div id="header">
				<br/>
				<ul id="main" style="list-style-type: none; margin-left: 15px;">
					<li id="divnotice1"></li>
					<li id="divnotice2" class="unblock"></li>
					<li id="divnotice3" class="unblock"></li>
				</ul>
				</div>
			</div>
			<span class="l4"></span>
			<span class="l3"></span>
			<span class="l2"></span>
			<span class="l1"></span>
			</div>
			<br/>



			<div class="Framework">
			<div class="title_bg">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="23%"><img src="../images/dbgz.jpg" /></td>
					<td width="25%">&nbsp;</td>
					<td width="27%">&nbsp;</td>
					<td align="right"><img src="../images/right.jpg" /></td>
				</tr>
			</table>
			</div>
	
			<div class="work"><span id="divundosheet"><br/></span></div>
	
			<span class="l4"></span>
			<span class="l3"></span>
			<span class="l2"></span>
			<span class="l1"></span>
				
			</div>
		</td>
		<td width="1"></td>
		<td width="300px" valign="top"><%@include file="/frames/bj/frame/right.jsp"%></td>

	</tr>
</table>



</body>
</html>

