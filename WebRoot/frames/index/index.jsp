<%@ page contentType="text/html; charset=GBK" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>${AppName}</title>
</head>

<frameset rows="88,*,28" cols="*" border="0" framespacing="0">
    <frame src="${ctx}/frames/index/top.jsp" name="topFrame" scrolling="NO" noresize >
    <frameset cols="185,*" frameborder="NO" border="0" framespacing="0">
    	<frame src="${ctx}/frames/module/index.jsp" name="mainFrame" id="mainFrame" scrolling="no" oresize  >
    </frameset>
	<frame src="${ctx}/frames/index/bottom.jsp" name="bottomFrame" scrolling="no" noresize>
</frameset>
<noframes>
<body>
</body>
</noframes>
</html>
