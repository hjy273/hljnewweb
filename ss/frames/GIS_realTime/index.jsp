<%@page contentType="text/html; charset=GBK"%>

<%@page import="com.cabletech.commons.config.*"%>
<%@page import="com.cabletech.utils.GISkit.*"%>
<%@page import="com.cabletech.baseinfo.domainobjects.UserInfo"%>
<%@page import="com.cabletech.commons.StrListUtil" %>

<%
  String regionid = "";
  String userid = "";
  String holdGroupStr="";
  String PMType = (String)session.getAttribute("PMType");
  List holdGroup = (List)session.getAttribute("USERGROUP");
  if (request.getParameter("cRegion") != null) {
    regionid = request.getParameter("cRegion");
  }

  if (request.getParameter("userID")!=null){
    userid = request.getParameter("userID");
  } 

  String GisPath = GISPath.getGisPath(regionid);

  //设置重载地图参数
  String init = request.getParameter("init");
 
  if( init.equals("")){
	  init = "yes";
  }
  if(holdGroup != null){
	  
	  holdGroupStr = StrListUtil.listToString(holdGroup);
  }

  //调用地图的地址
  String mapPath = GisPath + "/frame_index.jsp?init="+init+"&regionID=" + regionid+"&userID="+userid+"&pmType="+PMType+"&holdGroups="+holdGroupStr;
  System.out.println("mapPath :"+mapPath);
  
%>

<script language="javascript">
	//为在顶部显示地图设置参数
	parent.parent.topFrame.rId = <%=regionid%>
	//转移到地图
	document.location.replace("<%=mapPath%>")
</script>

<p>对不起，您的浏览器存在问题，不能访问地图！</p>