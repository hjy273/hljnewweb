<%@ page contentType="text/html; charset=GBK" %>
<%@page import="com.cabletech.baseinfo.domainobjects.UserInfo"%>
<%@page import="com.cabletech.commons.config.*"%>
<%
try{
	UserInfo userinfo = (UserInfo) session.getAttribute("LOGIN_USER");

	String regionid = userinfo.getRegionID();
	String flag = request.getParameter("flag");

	if (session.getAttribute("cRegion") != null) {
		regionid = (String)session.getAttribute("cRegion");
	}

	String GisPath = GisConInfo.newInstance().getWholePathFromDB(regionid);

	System.out.println("×ó È¡µÃÂ·¾¶£º" + GisPath);
	%>
	<input type="hidden" name="GisPath" value="<%=GisPath%>">
	<input type="hidden" name="flag" value="<%=flag%>">

	<script language="javascript">
	<!--

	parent.GisPath.value = GisPath.value;

	if(flag.value == "hide"){
		
		try{
			parent.hidemenuSub();
		}catch(e){}

	}else{
		try{
			parent.showmenuSub();
		}catch(e){}
	}

	//-->
	</script>

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