<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>�����ά�ۺϹ���ϵͳ</title>
<link href="${ctx}/frames/default/css/style.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript"
	src="${ctx}/js/jQuery/jquery-1.7.1.min.js"></script>
<script language="javascript" type="text/javascript">
	//��Ŀ¼URL

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
				if(text==="�ҵĹ���")
					{
				document.getElementById("bodyFrame").src='${ctx}/desktop/leftNavigateAction_getmenu.jspx?type='+index+'&text=1';
					}else if(text==='����ģ��'){
						document.getElementById("bodyFrame").src='${ctx}/desktop/leftNavigateAction_getmenu.jspx?type='+index+'&text=2';
					}else{
						document.getElementById("bodyFrame").src='${ctx}/desktop/leftNavigateAction_getmenu.jspx?type='+index;	
					}
			}else{
				document.getElementById("bodyFrame").src='/nbspweb/desktop/desktop!index.action';
			}
		}
	}
		//�˳�
		function exitSystem() {
			if (confirm("��ȷʵҪ�ر�ϵͳ��?")) {
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
		//����Ӧ������߶�
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
			<div class="head_System_Name">�����ά�ۺϹ���ϵͳ</div>
			<div class="head_text_bg"></div>
			<div class="head_Button">
				<ul>
					<li>
						<div class="pass">
							<a href="javascript:updatepws();">�޸�����</a>
						</div></li>
					<li>
						<div class="help">
							<a href="javascript:help();">����</a>
						</div></li>
					<li>
						<div class="quit">
							<a href="javascript:exitSystem();">�˳�</a>
						</div></li>
				</ul>
			</div>
		</div>
		<div class="head_banner">
			<div class="menu">
				<ul>
					<c:forEach var="item" items="${sessionScope.indexmenu}">
						<c:choose>
							<c:when test="${item.TEXT=='֪ʶ��̳'}">
								<li><a id='${item.ID }' href="${item.HREFURL }" target="_blank">֪ʶ��̳</a>
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
		<div class="bottom_left_Information">�汾��1.2.1 2012.04.16</div>
		<div class="bottom_right_cabletech">�����θ�������Ƽ���չ���޹�˾</div>
	</div>
</body>
</html>