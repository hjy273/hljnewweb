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
<META NAME="DESCRIPTION" CONTENT="�ƶ�������·Ѳ�����ϵͳ">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Window-target" CONTENT="_top">
<script language="JavaScript" type="text/JavaScript">
</script>
<title>�ƶ�������·Ѳ�����ϵͳ</title>
<link href="../css/menu_style.css" rel="stylesheet" type="text/css">
</head>
    <style type="">
    /* Style for tree item text */
    .t0i {
        font-family: Tahoma, Verdana, Geneva, Arial, Helvetica, sans-serif;
        font-size: 11px;
        color: #000000;
        background-color: #ffffff;
        text-decoration: none;
    }
    /* Style for tree item image */
    .t0im {
        border: 0px;
        width: 19px;
        height: 16px;
    }
    body {
    margin-left: 0px;
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 0px;
}
 </style>
<script language="JavaScript" src="../js/frame_tree.js" type=""></script>
<%

      HashMap secondlyMenu=(HashMap)session.getAttribute("MENU_SECONDLYMENU");

    //��øö����˵�����Ӧ��һ���˵�������
    String name=request.getParameter("name");
    if(name==null){
          Vector firstMenu=(Vector)session.getAttribute("MENU_FIRSTMENU");
        FirstMenu menu=(FirstMenu)firstMenu.get(0);
        name= menu.getLablename();
    }
    Vector v;
    String id=request.getParameter("id");


    if(id!=null){

        if(id.equals("1")){
            response.sendRedirect("${ctx}/realtime/realtime_tree.jsp");
        }

        if(id.equals("6")){
            response.sendRedirect("${ctx}/TroubleManage/troubleMgr_tree.jsp");
        }

        v=(Vector)secondlyMenu.get(id);
        //���Ϊnull�����������˵������ڴ�֮�У���Ҫ�����ݿ����װ��
        if(v==null){
            MenuService service=new MenuService();
            //��õ�ǰ��½�û���UserID
            UserInfo userinfo=(UserInfo)session.getAttribute("LOGIN_USER");
            List usergroups = (List)session.getAttribute("USERGROUP");
            if(usergroups != null){
            	v=service.getSecondlyMenu(id,usergroups);
            }else{
            	v=service.getSecondlyMenu(id,userinfo);
            }
            //�����ݿ��ö����˵���Ȼ����뻺��
            secondlyMenu.put(id,v);
        }
    }else{
        v=(Vector)secondlyMenu.values().iterator().next();
    }
    Map firstMenuMap = (Map)session.getAttribute("FIRSTMENUMAP");
    FirstMenu firstMenu= (FirstMenu)firstMenuMap.get(id);
%>
<body>
<table id=control width="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
<td height="24px" align="center" background="../images/bg_menu_title.gif" style="border:#C2C2C2 solid;border-width:1px 1px 1px 1px;">���˵�����</td>
<td width="25px" align="center" background="../images/bg_menu_title_r.gif"><img src="../images/icon_reload.gif" alt="ˢ�²˵�" width="17px" height="17px" style="cursor:hand; " onClick="window.location.reload()"></td>
</tr>
</table>
<table width="100%" height="95%"  border="0" cellpadding="0" cellspacing="0" style="border:#C2C2C2 solid;border-width:0 1px 1px 1px; ">
  <tr>
    <td valign="top">
    <div id="navi" style="position:relative; width:100%; height:100%; z-index:1; left: 0px; top: 0px; overflow: auto">
  <div style="position:relative; top:3px; left:5px;">
    <script language="JavaScript" src="../js/tree.js"></script>
    <script language="JavaScript" src="../js/tree_tpl.js"></script>
    <script language="JavaScript">
var TREE_ITEMS = [
    ['<%=firstMenu.getLablename()%>',  null,
    <%
        for(int i=0;i<v.size();i++){
            SecondlyMenu menu=(SecondlyMenu)v.get(i);
    %>
        ['<%=menu.getLablename()%>','${ctx}/frames/thirdmenu.jsp?id=<%=menu.getId()%>'],
    <%
        }
    %>
    ],

];
</script>
    <script language="JavaScript">
    new tree (TREE_ITEMS, tree_tpl);
</script>
  </div>

     </div></td>
  </tr>
</table>
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
