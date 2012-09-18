<%@page contentType="text/html; charset=GBK"%>
<%@page import="com.cabletech.baseinfo.domainobjects.UserInfo"%>
<%@page import="com.cabletech.commons.config.*"%><%@page import="com.cabletech.utils.GISkit.*"%>
<%

try{

	UserInfo userinfo = (UserInfo) session.getAttribute("LOGIN_USER");
	String regionid = userinfo.getRegionID();
    String userid = userinfo.getUserID();

System.out.println(".................................."+userid);

  if(request.getParameter("cRegion") != null){
	  regionid = request.getParameter("cRegion");
  }
	String initMap = request.getParameter("init");
	if(initMap==null) initMap = "";
  System.out.println("写入session : "+regionid);

  session.setAttribute("cRegion", regionid);

  //String GisPath = GisConInfo.newInstance().getWholePath();

  ////////////////////////////////// 
  response.sendRedirect("index.jsp?cRegion="+regionid+"&init="+initMap+"&userID="+userid+"");

  //////////////////////////////////
%>
<html>
	<script language="javascript">
		//by maqx 为在顶部显示地图设置参数
parent.parent.topFrame.rId = <%=regionid%>
</script>
<head>
<title>GIS实施监控</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
</head>
<frameset id=gis_mainframeset cols="*,6,200,0" frameborder="NO" border="0" framespacing="0">
  <frame id="Map" name="Map" src="" scrolling="NO" noresize>
  <frame id="gis_controlFrame" border=0 name="gis_controlFrame" src="gis_controlframe.jsp?cRegion=<%=regionid%>&init=<%=initMap%>&userID=<%=userid%>" frameBorder=0 scrolling="NO" noresize>
 
  <frame name="gis_infoFrame" scrolling="auto" noresize src="">
  <frame name="MapEngine" target="main" src="" scrolling="no">
</frameset>
<noframes>


<body bgcolor="#FFFFFF" text="#000000"></body>
</noframes>
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
