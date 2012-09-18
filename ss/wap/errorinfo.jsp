<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="com.cabletech.commons.config.*"%>
<html>
	<head>

		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="pragma" CONTENT="no-cache">
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
		<META HTTP-EQUIV="expires" CONTENT="Wed, 26 Feb 1997 08:21:57 GMT">
		<META HTTP-EQUIV="expires" CONTENT="0">
	</HEAD>
	<%
		MsgInfo msg = (MsgInfo) request.getAttribute("MESSAGEINFO");
		System.out.println(msg.getMessage());
		System.out.println(msg.getLink());
	%>
	<body text="#000000">
		<form action="2.htm">
			<p>
				&nbsp;
			</p>
			<p>
				<div align="center"><%=msg.getMessage()%></div>
			</p>
			<p>
				&nbsp;
			</p>
			<p>
				<div align="center">
					<input type="button" name="btnReturn" value="返回"
						onClick="javascript:location.href='<%=msg.getLink()%><%=msg.getRequestUri()%>'" />
				</div>
			</p>
		</form>
		<p>
			&nbsp;
		</p>
	</body>
</html>
