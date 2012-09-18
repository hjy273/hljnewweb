<%@ page contentType="text/html; charset=GBK" %>
<%
	String pid = request.getParameter("sPID");
    String type = request.getParameter("sType");
    String funid = request.getParameter("sFunID");
	if (pid == null)
		pid = "0";
	if (type == null)
		type = "null";
	if (funid == null)
		funid = "null";
%>
<jsp:forward page= "addpoint.jsp" >
	<jsp:param name="sPID" value="<%=pid%>" />
	<jsp:param name="sType" value="<%=type%>" />
	<jsp:param name="sFunID" value="<%=funid%>" />
	</jsp:forward>

