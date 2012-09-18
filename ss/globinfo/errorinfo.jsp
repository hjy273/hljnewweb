<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="com.cabletech.commons.config.*"%>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<META HTTP-EQUIV="pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
<META HTTP-EQUIV="expires" CONTENT="Wed, 26 Feb 1997 08:21:57 GMT">
<META HTTP-EQUIV="expires" CONTENT="0">
<script language="JavaScript">
<!--
function MM_openBrWindow(theURL,winName,features) { //v2.0
  window.open(theURL,winName,features);
}
//-->
</script>
</HEAD>

<% MsgInfo msg=(MsgInfo)request.getAttribute("MESSAGEINFO");
   System.out.println(msg.getMessage());
   System.out.println(msg.getLink());
%>
<script type="text/javascript">
document.onkeydown=function(event){
	e = event ? event :(window.event ? window.event : null);
	if(e.keyCode==13){  
		<% if(msg.getLink().equals("返回")) {%>
			history.go(-1);
		<%} else if(msg.getLink().indexOf("history.go")!=-1){%>
			<%=msg.getLink()%>;
		<%} else{%>
			location.href='<%=msg.getLink()%><%=msg.getRequestUri()%>';
		<%}%>
	}
};
</script>
<body text="#000000">
<form action="2.htm">
  <p>&nbsp;</p>
  <p>&nbsp;</p>
  <p>&nbsp;</p>
  <table width="95%" border="0" cellspacing="0" cellpadding="6" align="center">
    <tr>
      <td>
        <table border="0" cellspacing="10" cellpadding="0" align="center" bgcolor="8DAAD8" bordercolor="#000000">
          <tr>
            <td width="60" height="60" bgcolor="#FFFFFF" align="center"><img src="${ctx}/images/error.gif" width="49" height="49"></td>
            <td width="250" bgcolor="#FFFFFF" align="center">  <%=msg.getMessage()%></td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
  <table border="0" cellspacing="10" cellpadding="0" align="center" >
             <tr>
             <td   align="center">
              <% if(msg.getLink().equals("返回")) {%>
              		<img  border="0" style="cursor:hand"   src="${ctx}/images/goback.gif" width="80" height="22" onClick="javascript:history.go(-1);" alt=""/>
             <%} else if(msg.getLink().indexOf("history.go")!=-1){%>
          		<img  border="0" style="cursor:hand"   src="${ctx}/images/goback.gif" width="80" height="22" onClick="javascript:<%=msg.getLink()%>;" alt=""/>
            <%} else{%>
                <img  border="0" style="cursor:hand"   src="${ctx}/images/goback.gif" width="80" height="22" onClick="javascript:location.href='<%=msg.getLink()%><%=msg.getRequestUri()%>'" alt=""/>
            <%}%>
             </td>


            </tr>
   </table>
  <p>&nbsp;</p>
</form>
<p>&nbsp;</p></body>
</html>
