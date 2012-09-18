<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.baseinfo.domainobjects.UserInfo"%>
<%@page import="com.cabletech.commons.config.*"%>
<%@ page import="com.cabletech.sysmanage.domainobjects.*" %>
<%
  UserInfo userinfo = (UserInfo) session.getAttribute("LOGIN_USER");
  String regionid = userinfo.getRegionID();

%>
<html>
<head>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=gb2312">
<META NAME="DESCRIPTION" CONTENT="移动传输线路巡检管理系统">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Window-target" CONTENT="_top">
<link href="../css/main_style.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body {
    margin-left: 0px;
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 0px;
    border-right: #3E4245 1px solid;
    border-left: #3E4245 1px solid;
    border-top: #2A58A6 1px solid;
    background-color: #EAE9E4;
}
-->
</style></head>
<script type="text/javascript">
 function init(){
 	var URL = "${ctx}/initdisplayAction.do?method=init";
	hiddenInfoFrame.location.replace(URL);
	//onload="init();"
 }
</script>
<body >
<table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="3" valign="top" bgcolor="#B4B4B4"><img src="../images/1px.gif" width="1" height="1"></td>
    <td valign="top"><img src="../images/1px.gif"><DIV>
    <IFRAME id=mainPage name=mainPage border=0 marginWidth=0 marginHeight=0 src="${ctx}/frames/main_init.jsp" frameBorder=0 width="100%" scrolling=yes height="100%"></IFRAME></DIV>	</td>
    <td width="3" valign="top" bgcolor="#B4B4B4"><img src="../images/1px.gif" width="1" height="1"></td>
  </tr>
</table>
</body>
</html>
<iframe name="hiddenInfoFrame" style="display:none"></iframe>
<!-- 重要公告提示 start-->
<%
	Notice notice = (Notice)session.getAttribute("notice");	
 %>
<script>
/*eMsg*/
var divTop,divLeft,divWidth,divHeight,docHeight,docWidth,i = 0;
var eMsg = new Object();
eMsg.lightSrc = '../images/light.gif';
eMsg.closeSrc = '../images/msgclose.gif';
eMsg.id = 'eMsg';
eMsg.obj = function(){return document.getElementById(eMsg.id);};

eMsg.onLoad = function(){
    try{
        divTop = parseInt(eMsg.obj().style.top,10);
        divLeft = parseInt(eMsg.obj().style.left,10);
        divHeight = parseInt(eMsg.obj().offsetHeight,10);
        divWidth = parseInt(eMsg.obj().offsetWidth,10);
        docWidth = document.body.clientWidth;
        docHeight = document.body.clientHeight;
        eMsg.obj().style.top = parseInt(document.body.scrollTop,10) + docHeight + 10;
        eMsg.obj().style.left = parseInt(document.body.scrollLeft,10) + docWidth - divWidth;
        eMsg.obj().style.visibility="visible";
        eMsg.timer = window.setInterval(eMsg.move,1);
        hp.skin.onBeforChange = function(){
            if(eMsg.obj())eMsg.obj().parentNode.removeChild(eMsg.obj());
        };
    }
    catch(e){}
};
eMsg.onResize = function(){
    i+=1;
    if(i>1000) eMsg.close();
    try{
        divHeight = parseInt(eMsg.obj().offsetHeight,10);
        divWidth = parseInt(eMsg.obj().offsetWidth,10);
        docWidth = document.body.clientWidth;
        docHeight = document.body.clientHeight;
        eMsg.obj().style.top = docHeight - divHeight + parseInt(document.body.scrollTop,10);
        eMsg.obj().style.left = docWidth - divWidth + parseInt(document.body.scrollLeft,10);
    }
    catch(e){}
};
eMsg.move = function(){
    try
    {
        if(parseInt(eMsg.obj().style.top,10) <= (docHeight - divHeight + parseInt(document.body.scrollTop,10)))
        {
            window.clearInterval(eMsg.timer);
            eMsg.timer = window.setInterval(eMsg.onResize,1);
        }
        divTop = parseInt(eMsg.obj().style.top,10);
        eMsg.obj().style.top = divTop - 1;
    }
    catch(e){}
};
eMsg.close = function(){
    eMsg.obj().parentNode.removeChild(eMsg.obj());
    if(eMsg.timer) window.clearInterval(eMsg.timer);
};
eMsg.open_notify=function(NOTICE_ID){
URL="${ctx}/NoticeAction.do?method=showNotice&id="+NOTICE_ID;
 myleft=(screen.availWidth-650)/2;
 mytop=100
 mywidth=650;
 myheight=500;
  window.open(URL,"read_news","height="+myheight+",width="+mywidth+",status=1,toolbar=no,resizable=no,menubar=no,location=no,scrollbars=yes,top="+mytop+",left="+myleft);
 };
eMsg.createInstance = function(titleHtml,bodyHtml){
    if(!titleHtml || !bodyHtml) throw '必须为titleHtml指定值,必须为bodyHtml指定值。';
    var odiv = document.createElement('DIV');
    odiv.id = eMsg.id;
    odiv.innerHTML = '<div class="eMsgInner">'
    + '<div class="head">'
       + '<div class="headLeft"><img src="' + eMsg.lightSrc + '" height="15" width="15"/></div>'
		+ '<div class="headMiddle">' + titleHtml + '</div>'
        + '<div class="headRight"><img src="' + eMsg.closeSrc  + '" onclick="eMsg.close()" align="absmiddle"/></div>'
    + '</div>'
    + '<div class="body" oncontextmenu="eMsg.close();return false;" title="右击关闭">'
    + '<div style="PADDING-LEFT: 8px; FONT-SIZE: 12px;  WIDTH: 100%; COLOR: #1f336b; PADDING-TOP: 8px; ">您有1条重要信息待读 </div>'
        + '<div class="content"> '
        + bodyHtml
        + '</div>'
    + '</div>'
    + '</div>';
    document.body.appendChild(odiv);
};

window.onresize = eMsg.onResize;
window.onload = function(){
eMsg.createInstance('重要提示信息',
'<a href="javascript:eMsg.open_notify(\'<%=notice.getId()%>\');"><%=notice.getTitle() == null?"":notice.getTitle()%> </a>');
<%
 	if(notice.getTitle() != null){
 %>
eMsg.onLoad();
<%}%>
};
</script>
<style>
DIV#eMsg{
    background-color: #c9d3f3;
    border-left: #a6b4cf 1px solid;
    border-right: #455690 1px solid; 
    border-top: #a6b4cf 1px solid;
    border-bottom: #455690 1px solid;
    visibility: hidden; 
    width: 199px; 
    height: 97px; 
    position: absolute; 
    z-index: 99999; 
    left: 0px;
}
DIV#eMsg DIV.eMsgInner
{
    border-top: #ffffff 1px solid;
    border-left: #ffffff 1px solid;
    background-color:#cfdef4;
    height:96px;
    width:198px;
}
DIV#eMsgInner DIV.head{width:197px}
DIV.headLeft{width:15px;float:left;}
DIV.headMiddle{ 
    FONT-SIZE: 12px; 
    COLOR: #1f336b;
    width:165px;
    text-align:left;
    float:left;
    padding-top:2px;
    font-weight:normal
    
}
DIV.headRight{width:16;float:left;}
DIV.headRight IMG{
    width:13px;
    height:13px;
    border:0px;
    cursor:hand;
    margin:2px;
}
DIV.body{
    height:82px;
    clear:both;
    border-right: #b9c9ef 1px solid; 
    padding: 5px;  
    padding-top: 1px; 
    border-top: #728eb8 1px solid;
    border-left: #728eb8 1px solid; 
    color: #1f336b; 
    word-break: break-all;
    border-bottom: #b9c9ef 1px solid;
}
DIV.light{text-align:left; FONT-SIZE: 12px; padding:5px 20px}
DIV.content{text-align:left;height:50px;padding:6px;padding-top:10px;FONT-SIZE: 12px; }
</style>
<!-- 重要公告提示 end-->