<%@page contentType="text/html; charset=GBK"%>
<%
  String taskname = (String) request.getAttribute("taskname");
  String taskid = (String) request.getAttribute("taskid");
  String excutetimes = (String) request.getAttribute("excutetimes");
  String linenum = (String) request.getAttribute("lineNum");
  String linelevel = (String) request.getAttribute("linelevel");

  System.out.println("第几排 ：" + linenum);
  System.out.println("level ：" + linelevel);
%>
<html>
<head>
<title>自动关闭页面 update</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<script language="javascript">
<!--

function initPage(taskname,taskid,excutetimes,linenum, linelevel){
	if(opener.reSetTaskList(taskname,taskid,excutetimes,linenum, linelevel )){
	 self.close();
	} 
}

//-->
</script></head>
<body bgcolor="#FFFFFF" text="#000000" onload="initPage('<%=taskname%>','<%=taskid%>','<%=excutetimes%>',<%=linenum%>, '<%=linelevel%>')">
<span id="tempSpan"></span>
</body>
</html>
