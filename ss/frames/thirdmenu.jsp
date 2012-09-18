<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="com.cabletech.menu.domainobjects.*" %>
<%@ page import="com.cabletech.menu.services.*" %>
<%@ page import="com.cabletech.baseinfo.domainobjects.*" %>

<%@ page import="java.util.*" %>
<%
try{

%>
<html>
<head>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<title>移动传输线路巡检管理系统</title>

<script language="JavaScript" type="text/JavaScript">
	document.oncontextmenu = function() { return false;};
	function change(whichLink) {
		var box = document.getElementById("box");
   		var links = box.getElementsByTagName("td");
		for (var i = 0; i < links.length; i++) {
			links[i].className = "lbutton";
		}
		whichLink.className = "lbutton_sel";
	}
	function hrefto(url){
		var iframeid = document.getElementById("iframemain");
		iframeid.src=url;
	}
	
</script>
<link href="../css/old_style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../js/jquery-1.3.2.min.js"></script>
</head>
<%
	HashMap thirdMenu=(HashMap)session.getAttribute("MENU_THIRDMENU");
    String menuID=request.getParameter("id");
    UserInfo userinfo=(UserInfo)session.getAttribute("LOGIN_USER");
    Vector v;
    String cssClass="lbutton";
    if(menuID!=null){
        v=(Vector)thirdMenu.get(menuID);
        //如果为null表明，三级级菜单不在内存之中，需要从数据库进行装载
        if(v==null){
            MenuService service=new MenuService();
            List usergroups = (List)session.getAttribute("USERGROUP");
            if(usergroups != null){
            	v=service.getThirdMenu(menuID,usergroups);
            }else{
           		v=service.getThirdMenu(menuID,userinfo);
            }
            thirdMenu.put(menuID,v);
        }
    }else{
        v=(Vector)thirdMenu.values().iterator().next();
    }
     
%>
<body >
<table class=borderon id=control cellspacing=0 cellpadding=0 width="100%" border=0>
  <tbody>
    <tr>
      <td><div id="box">
        <table border="0" cellspacing="0" cellpadding="0">
          <tr align="center" id="box">
           <%
               for(int i=0;i<v.size();i++){
                ThirdMenu menu=(ThirdMenu)v.get(i);
                String id = menu.getId();
                System.out.println("menu.getHrefurl( : " + menu.getHrefurl());
                if(i==0){
                	cssClass="lbutton_sel";
                }else{
                	cssClass="lbutton";
                }
                // add by guixy 2008-11-13 盯防模块不显示查询的三级菜单
                if(!"40301".equals(id) 
                	&& !"40201".equals(id) && !"40203".equals(id) 
                	&& !"40101".equals(id) && !"40102".equals(id)) {
               
                %>
             <td width="100" class="<%=cssClass %>" onclick="change(this);hrefto('<%=menu.getHrefurl()%>')" style="cursor:hand;" >
                <a href="#"> <%=menu.getLablename()%>
                </a>
            </td>
           <%
           }}
           %>
          </tr>
      </table>
      </div></td>
    </tr>
   <tr>
    <td height="2" background="../images/bg_line.gif"><img src="../images/1px.gif" width="1" height="1"></td>
  </tr>
  </tbody>
</table>

	<%ThirdMenu menu=(ThirdMenu)v.get(0);%>
        <iframe name='iframemain' id="iframemain"
            marginWidth=0 marginHeight=0 src="<%=menu.getHrefurl()%>" frameBorder=0 width="100%" height="95%" scrolling="yes" ></iframe>

</body>
</html>
<%

}catch(Exception e){
%>
<script language="javascript">
<!--
top.location.replace("/");
//-->
</script>
<%
}
%>
