<%@ page contentType="text/html; charset=GBK" %>
<%@page import="com.cabletech.baseinfo.domainobjects.UserInfo"%>
<%@page import="com.cabletech.baseinfo.services.RoleManage"%>
<%
try{

String preUrl = "${ctx}/TroubleManage/troubleMenu.jsp?regionid=";
String preLUrl = "${ctx}/TroubleManage/largetroubleMenu.jsp?regionid=";

  UserInfo userinfo = (UserInfo) session.getAttribute("LOGIN_USER");
  String regionid = userinfo.getRegionID();
  System.out.println("getRegionID:"+regionid);

  RoleManage roleM = new RoleManage();

  Vector vct = roleM.getRegionPras(regionid);

  String role = (String)vct.get(0);
  String supReId = (String)vct.get(1);
  String supReName = (String)vct.get(2);

  Vector unitsVct = (Vector)vct.get(3);

  //String[] curStr = (String[])unitsVct.get(1);
%>
<html>
<head>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=gb2312">
<META NAME="DESCRIPTION" CONTENT="移动传输线路巡检管理系统">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Window-target" CONTENT="_top">
<title>移动传输线路巡检管理系统</title>
<link href="../css/menu_style.css" rel="stylesheet" type="text/css">
</head>
<style>
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
<script language="JavaScript" src="../js/frame_tree.js"></script>

<body>
<table id=control width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="24" align="center" background="../images/bg_menu_title.gif" style="border:#C2C2C2 solid;border-width:1 1 1 1;">主菜单名称</td>
    <td width="25" align="center" background="../images/bg_menu_title_r.gif">
      <img src="../images/icon_reload.gif" alt="刷新菜单" width="17" height="17" style="cursor:hand; " onClick="window.location.reload()">
    </td>
  </tr>
</table>
<table width="100%" height="95%" border="0" cellpadding="0" cellspacing="0" style="border:#C2C2C2 solid;border-width:0 1 1 1; ">
  <tr>
    <td valign="top">
      <div id="navi" style="position:relative; width:100%; height:expression(body.offsetHeight-control.offsetHeight-2); z-index:1; left: 0px; top: 0px; overflow: auto">
        <div style="position:relative; top:3px; left:5px;">
          <!-- 顶级名称 --><!-- 
		  <table height="31" border="0" cellpadding="0" cellspacing="0">
			  <tr>
				<td height="25" align="center"><%=supReName%>实时监控</td>
				</tr>
			</table> -->
		<!-- 二级 -->
<%

if(role.equals("1")){

	out.println("    <script language=\"JavaScript\" src=\"../js/tree.js\"></script>    	");
	out.println("    <script language=\"JavaScript\" src=\"../js/tree_tpl.js\"></script>	");
	out.println("    <script language=\"JavaScript\">                             	");

	out.println(" 	var TREE_ITEMS = [                                                    ");
	out.println(" 		['"+supReName+"',  null,                                             ");

	for(int k = 0; k < unitsVct.size(); k ++){

		Vector oneUnitVct = (Vector)unitsVct.get(k);	
		String[] titleArr = (String[])oneUnitVct.get(0);

		out.println(" 			['"+titleArr[1]+" 隐患处理', '"+preUrl+titleArr[0]+"'],                                ");
		//out.println(" 			['"+titleArr[1]+" 障碍处理', '"+preLUrl+titleArr[0]+"'],                                ");

	}
	out.println(" 		],	                                                                ");
	out.println(" 		                                                                    ");
	out.println(" 	];  ");
	out.println("</script>                                                      	");

	out.println("<script language=\"JavaScript\">    	");
	out.println("	new tree (TREE_ITEMS, tree_tpl);	");
	out.println("</script>                         	");

} else if(role.equals("2")){

	String[] titleArr = (String[])unitsVct.get(0);

	out.println("    <script language=\"JavaScript\" src=\"../js/tree.js\"></script>    	");
	out.println("    <script language=\"JavaScript\" src=\"../js/tree_tpl.js\"></script>	");
	out.println("    <script language=\"JavaScript\">                             	");
	
	out.println("var TREE_ITEMS = [                                             	");
	out.println(" 	['"+supReName+"',  null,                                             ");

	out.println(" 			['"+titleArr[1]+" 隐患处理', '"+preUrl+titleArr[0]+"'],                                ");
	//out.println(" 			['"+titleArr[1]+" 障碍处理', '"+preLUrl+titleArr[0]+"'],                                ");

	out.println("	],	                                                         	");
	out.println("	                                                             	");
	out.println("];                                                             	");

	out.println("</script>                                                      	");

	out.println("<script language=\"JavaScript\">    	");
	out.println("	new tree (TREE_ITEMS, tree_tpl);	");
	out.println("</script>                         	");
	out.println("<br>                         	");

} 
%>
        </div>
        <div style="width:100%; height:100;">        </div>
      </div>
    </td>
  </tr>
</table>
</body>
</html>
<%
	
}catch(Exception e){
%>
<script language="javascript">
<!--
top.location.replace("${ctx}/login.do?&method=relogin");
//-->
</script>
<%
}
%>