<%@include file="/common/header.jsp"%>
<%
String id = request.getParameter("id");
String sim = request.getParameter("sim");
String tm = request.getParameter("tm");
String pw = request.getParameter("pw");
%>
<html>
<head>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=gb2312">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<title>移动传输线路巡检管理系统</title>
<link href="${ctx}/css/style.css" rel="stylesheet" type="text/css">
</head>

<body>
<TABLE class=borderon id=control cellSpacing=0 cellPadding=0 width="100%" border=0>
  <TBODY>
    <TR>
      <td>
        <table border="0" cellspacing="0" cellpadding="0">
          <tr align="center">
            <td width=100 class="lbutton"  style="cursor:hand"><a href="${ctx}/realtime/sendCommandMsg.jsp?sim=<%=sim%>&id=<%=id%>&<%=pw%>" target="iframemain">发送短信</a></td>
            <!-- td width=100 class="lbutton"  style="cursor:hand"> <a href="${ctx}/realtime/sendHomingMsg.jsp?sim=<%=//sim%>&id=<%=//id%>&<%=//pw%>" target="iframemain">发送自动巡检命令</a> </td-->
          </tr>
      </table></td>
    </TR>
   <tr>
    <td height="2" background="${ctx}/images/bg_line.gif"><img src="${ctx}/images/1px.gif" width="1" height="1"></td>
  </tr>
  </TBODY>
</TABLE>
<TABLE width="100%" height="100%" border=0 cellPadding=0 cellSpacing=0 class=borderon id=control>
  <TR>
    <TD>
      <DIV id=iframexxx >
        <IFRAME name=iframemain
            marginWidth=0 marginHeight=0 src="${ctx}/realtime/sendCommandMsg.jsp?sim=<%=sim%>&id=<%=id%>&pw=<%=pw%>&tm=<%=tm%>" frameBorder=0 width="100%" scrolling=YES height="95%">
        </IFRAME>
      </DIV>
    </TD>
  </TR>
</TABLE>
</body>
</html>
