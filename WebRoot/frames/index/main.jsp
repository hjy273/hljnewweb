  <%@ page contentType="text/html; charset=GBK" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>巡检系统</title>
<link href="${ctx}/css/divstyle.css" rel="stylesheet" type="text/css">
<link href="${ctx}/css/style.css" rel="stylesheet" type="text/css">
</head>
<body class="main_border">
    <div style="width:100%;height:100%">
	<iframe src="${ctx}/DesktopAction.do?method=loadinfo&type=<%=request.getParameter("type")%>" id="maintext" width="100%" height="99%" scrolling="auto" frameborder="no"></iframe>
    </div>
</body>
</html>
