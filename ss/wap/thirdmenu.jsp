<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="com.cabletech.menu.domainobjects.*" %>
<%@ page import="com.cabletech.menu.services.*" %>
<%@ page import="com.cabletech.baseinfo.domainobjects.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.*" %>
<!--JSP初始化设置-->
<html>
<head>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<title>移动传输线路巡检管理系统</title>
<link href="../css/style.css" rel="stylesheet" type="text/css">
</head>
<%
	HashMap thirdMenu = (HashMap) session
				.getAttribute("MENU_THIRDMENU");
		String menuID = request.getParameter("id");
		UserInfo userinfo = (UserInfo) session
				.getAttribute("LOGIN_USER");
		Vector v;
		if (menuID != null) {
			v = (Vector) thirdMenu.get(menuID);
			//如果为null表明，三级级菜单不在内存之中，需要从数据库进行装载
			if (v == null) {
				MenuService service = new MenuService();
				List usergroups = (List) session
						.getAttribute("USERGROUP");
				if (usergroups != null) {
					v = service.getThirdMenu(menuID, usergroups);
				} else {
					v = service.getThirdMenu(menuID, userinfo);
				}
				thirdMenu.put(menuID, v);
			}
		} else {
			v = (Vector) thirdMenu.values().iterator().next();
		}
%>
<body>
	<%
		ThirdMenu menu = (ThirdMenu) v.get(0);
	%>
	<script type="text/javascript">
	location="<%=menu.getHrefurl().replaceAll(request.getContextPath()+"/",request.getContextPath()+"/wap/")%>&&env=${param.env}";
	</script>
</body>
</html>
