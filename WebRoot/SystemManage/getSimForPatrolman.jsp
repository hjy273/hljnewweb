<%@ page language="java" contentType="text/html; charset=GBK"%><%@page import="com.cabletech.commons.hb.*"%>
<%
  String userid = request.getParameter("userid");
  String sql="select simnumber from terminalinfo where ownerid='"+userid+"' and (state  <> '1' or state is null)";
  QueryUtil util=new QueryUtil();
  java.util.List list=util.queryBeans(sql);
  org.apache.commons.beanutils.BasicDynaBean bean=(org.apache.commons.beanutils.BasicDynaBean)list.get(0);
  String value=(String)bean.get("simnumber");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<meta http-equiv="Pragma" content="no-cache">
<title>移动传输线路巡检管理系统</title>
<link href="${ctx}/css/style.css" rel="stylesheet" type="text/css">
<script language="javascript">
<!--

function init(){

	parent.document.forms[0].simid.value="<%=value%>" ;
    //alert(parent.document.forms[0].simid.value);

}
init();
//-->
</script>
<body onload="//init()">
</body>
</html>
