<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>网络代维综合管理系统</title>
<link href="${ctx}/frames/default/css/style.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript"
	src="${ctx}/js/jQuery/jquery-1.7.1.min.js"></script>
<script language="javascript" type="text/javascript">
	//主目录URL

function gotoMainUrl(index,hrefurl,text){
	$('li a').removeClass('hovertad');
    $('#'+index).addClass('hovertad');
		if(hrefurl){
			if(hrefurl.indexOf("userId=")!=-1){
				document.getElementById("bodyFrame").src=hrefurl+'${LOGIN_USER.userID}';
			}else{
				document.getElementById("bodyFrame").src=hrefurl;
			}
			
		}else{
			if(index!="index"){
				if(text==="我的工作")
					{
				document.getElementById("bodyFrame").src='${ctx}/desktop/leftNavigateAction_getmenu.jspx?type='+index+'&text=1';
					}else if(text==='基础模块'){
						document.getElementById("bodyFrame").src='${ctx}/desktop/leftNavigateAction_getmenu.jspx?type='+index+'&text=2';
					}else{
						document.getElementById("bodyFrame").src='${ctx}/desktop/leftNavigateAction_getmenu.jspx?type='+index;	
					}
			}else{
				document.getElementById("bodyFrame").src='/nbspweb/desktop/desktop!index.action';
			}
		}
	}
		//退出
		function exitSystem() {
			if (confirm("您确实要关闭系统吗?")) {
				var _window = top.location != self.location? window.parent.window : window;
				_window.location="${ctx}/desktop/desktopAction_logout.jspx";
				//window.location="${caslogoutredirect}";
				//window.location="${caslogoutredirect}";
				//window.location.reload();
				//history.go(0);
			}
		}
		function help(){
		    window.open ("${ctx}/frames/default/help.jsp");
		}
		function updatepws(){
		    window.open ("${ctx}/SystemManage/updatePsw.jsp");
		}
		jQuery(function(){
			$('#index').addClass('hovertad');
			autoHeight();				
			})
		//自适应浏览器高度
		function autoHeight()
		{
			var section_middle = jQuery(document).height()-jQuery("#framecontentTop").outerHeight()-jQuery("#framecontentBottom").outerHeight();
			jQuery("#bodyFrame").height(section_middle);
			jQuery("#bodyFrame").width(jQuery(document).width());
			    setTimeout(autoHeight, 500);
		}
</script>
</head>
<html>
<body>
	<div id="framecontentTop">
		<div class="head_top">
			<div class="head_topleft_bg">
				<div class="head_logo"></div>
			</div>
			<div class="head_System_Name">网络代维综合管理系统</div>
			<div class="head_text_bg"></div>
			<div class="head_Button">
				<ul>
					<li>
						<div class="pass">
							<a href="javascript:updatepws();">修改密码</a>
						</div></li>
					<li>
						<div class="help">
							<a href="javascript:help();">帮助</a>
						</div></li>
					<li>
						<div class="quit">
							<a href="javascript:exitSystem();">退出</a>
						</div></li>
				</ul>
			</div>
		</div>
		<div class="head_banner">
			<div class="menu">
				<ul>
					<c:forEach var="item" items="${sessionScope.indexmenu}">
						<c:choose>
							<c:when test="${item.TEXT=='知识论坛'}">
								<li><a id='${item.ID }' href="${item.HREFURL }" target="_blank">知识论坛</a>
								</li>
							</c:when>
							<c:otherwise>
								<li><a id='${item.ID }' href="#"
									onclick="gotoMainUrl('${item.ID }','${item.HREFURL }','${item.TEXT}')">${item.TEXT}</a>
								</li>
							</c:otherwise>
						</c:choose>
					</c:forEach>

				</ul>
			</div>
		</div>
	</div>
	<div id="maincontent">
		<iframe id="bodyFrame" name="bodyFrame" scrolling="auto" style="overflow-x:hidden; " width="100%" 
			frameborder="0" src='/nbspweb/desktop/desktop!index.action'></iframe>
	</div>
	<div id="framecontentBottom">
		<div class="bottom_left_Information">版本：1.2.1 2012.04.16</div>
		<div class="bottom_right_cabletech">北京鑫干线网络科技发展有限公司</div>
	</div>
</body>
</html>