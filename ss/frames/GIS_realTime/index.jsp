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

  //�������ص�ͼ����
  String init = request.getParameter("init");
 
  if( init.equals("")){
	  init = "yes";
  }
  if(holdGroup != null){
	  
	  holdGroupStr = StrListUtil.listToString(holdGroup);
  }

  //���õ�ͼ�ĵ�ַ
  String mapPath = GisPath + "/frame_index.jsp?init="+init+"&regionID=" + regionid+"&userID="+userid+"&pmType="+PMType+"&holdGroups="+holdGroupStr;
  System.out.println("mapPath :"+mapPath);
  
%>

<script language="javascript">
	//Ϊ�ڶ�����ʾ��ͼ���ò���
	parent.parent.topFrame.rId = <%=regionid%>
	//ת�Ƶ���ͼ
	document.location.replace("<%=mapPath%>")
</script>

<p>�Բ�������������������⣬���ܷ��ʵ�ͼ��</p>