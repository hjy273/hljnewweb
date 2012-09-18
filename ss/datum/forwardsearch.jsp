<%@page import="com.cabletech.commons.config.*" %>
<%
	GisConInfo gconfig = GisConInfo.newInstance();
    String searchip = gconfig.getSearchip();
    String searchport = gconfig.getSearchport();
    String searchdir = gconfig.getSearchdir();
 %>
<jsp:forward page="http://<%=searchip %>:<%=searchport %>/<%=searchdir %>"/>