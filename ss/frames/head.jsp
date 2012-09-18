<%@page contentType="text/html; charset=gb2312"%>
<%@page import="com.cabletech.commons.config.*"%>
<%@page
	import="com.cabletech.commons.web.*,com.cabletech.baseinfo.domainobjects.*"%>
<%
	GisConInfo conInfo = GisConInfo.newInstance();
	EncryptDecrypt enCode = new EncryptDecrypt();
	UserInfo userInfo = (UserInfo) session.getAttribute("LOGIN_USER");
	String userid = enCode.Encode(userInfo.getUserID());
%>
<html>
	<head>
		<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
		<title>移动传输线路巡检管理系统</title>
		<link rel="stylesheet" href="../css/head_style.css" type="text/css">
		<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
	</head>
	<script language="JavaScript" src="../js/frame_head.js"></script>
	<script language="javascript" type="text/javascript">
<!--
var rId = "---";
document.oncontextmenu = function() { return false;}
function openmenu(){
 document.all.linemenucls.style.display = 'none';
 document.all.linemenuopen.style.display = 'block';
}
function closemenu(){
 document.all.linemenucls.style.display = 'block';
 document.all.linemenuopen.style.display = 'none';
}

function setOpnerPro(){
    try{
        top.opener.opener=null;
        top.opener.close();
    }catch(e){}

    openmenu();
}

function setViewMap(){
  //alert("test");
    if(rId=="---")return;

    parent.controlFrame.location = "${ctx}/frames/controlframe.jsp";
    parent.mainFrame.location = "${ctx}/frames/GIS_realTime/index_gis.jsp?init=already&cRegion="+rId;

}
function logout(){
    parent.window.close();
    parent.controlFrame.location = "${ctx}/login.do?&method=logout";
}

function help(){
    helpwin=window.open ("", "helpwindow");
    helpwin.location.href = "../common/help.htm";
}
function lqc(){
    helpwin=window.open ("", "lqcwindow");
    //helpwin.location.href = "http://<%=conInfo.getUnifyEntryIP()%>:<%=conInfo.getUnifyEntryPort()%>/lqc/index.jsp";
    helpwin.location.href = "http://<%=conInfo.getUnifyEntryIP()%>:<%=conInfo.getUnifyEntryPort()%>/<%=conInfo.getUnifyEntryUrl()%>?n=<%=userid%>";
}
function kf(){
    parent.mainFrame.location = "${ctx}/help/kf.htm ";

}
function window.onbeforeunload(){

  if(document.body.clientWidth-event.clientX< 170&&event.clientY< 0||event.altKey)
  {
    parent.controlFrame.location = "${ctx}/login.do?&method=logout";
  }
}



-->
</script>
	<%
		String str = (String) session.getAttribute("REGION_ROOT");
		System.out.print("========== " + str);
	%>
	<body onLoad="setOpnerPro()">
		<table width="100%" height="62" border="0" cellpadding="0"
			cellspacing="0" background="../images/bg_head.gif">
			<tr>
				<td width="400">
					<img
						src="../images/<%=session.getAttribute("REGION_ROOT")%>/logo.gif"
						width="513" height="62">
				</td>
				<td align="right">
					<table border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td nowrap id="linemenuopen" style="display: none;">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td width="20">
											<img src="../images/pic_head_lmenu_open.gif" alt="收缩功能菜单"
												width="20" height="36" style="cursor: hand;"
												onClick="closemenu()">
										</td>
										<td background="../images/bg_head_lmenu.gif">
											<table border="0" cellspacing="0" cellpadding="0">
												<tr align="center">
													<!--
                        <td width="40" ><a href="${ctx}/frames/GIS_realTime/index_gis.jsp?" target="parent.mainFrame" >
                        <img src="../images/helper/star.gif" title="显示地图" width="32" height="32" border="0"></a></td>
                      -->
													<td width="40" nowrap>
														<a href="javascript:kf()"> <img
																src="../images/icon_show_uinfo.gif" alt="客服热线"
																width="32" height="32" border="0"> </a>
													</td>
													<td width="40" nowrap>
														<a href="javascript:help()"> <img
																src="../images/icon_help.gif" alt="帮助" width="32"
																height="32" border="0"> </a>
													</td>
													<td width="40" nowrap>
														<a href="javascript:lqc()"> <img
																src="../images/icon_remind.gif" alt="综合平台" width="32"
																height="32" border="0"> </a>
													</td>
													<td width="40" nowrap style="cursor: pointer"
														onClick="setViewMap()">
														<img src="../images/menu/base.gif" title="返回地图" width="32"
															height="22" border="0">
													</td>
													<td width="40" nowrap>
														<a href="${ctx}/login.do?&method=reloginNoSession"
															target="_top"> <img src="../images/helper/star.gif"
																title="返回主界面" width="32" height="32" border="0"> </a>
													</td>
													<td width="40" nowrap>
														<a href="${ctx}/login.do?&method=relogin" target="_top">
															<img src="../images/icon_reuser.gif" title="用户切换"
																width="32" height="32" border="0"> </a>
													</td>
													<td width="40" nowrap>
														<a href="javascript:logout()"> <img
																src="../images/icon_logout.gif" alt="退出系统" width="32"
																height="32" border="0"> </a>
													</td>
												</tr>
											</table>
										</td>
										<!--HFDHGFH-->
									</tr>
								</table>
							</td>
							<td width="20" id="linemenucls" style="display: block">
								<img src="../images/pic_head_lmenu_close.gif" title="展开功能菜单"
									width="20" height="36" style="cursor: hand;"
									onClick="openmenu()">
							</td>
							<td width="6">
								<img src="../images/pic_head_lmenu_right.gif" width="6"
									height="36">
							</td>

						</tr>
					</table>
				</td>
			</tr>
		</table>
	</body>
</html>
