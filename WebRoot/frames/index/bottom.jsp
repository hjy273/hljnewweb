<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="com.cabletech.base.SysConstant;"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>巡检系统</title>
<link href="../../css/divstyle.css" rel="stylesheet" type="text/css">

</head>
<jsp:useBean id="now" class="java.util.Date"></jsp:useBean>
<fmt:setLocale value="zh_CN"/>
<body class="top_bottom_border">
	<div class="layout_bottombg">
	  <table width="100%"  border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="20"><img src="${ctx}/images2/bg/bottomleft.gif" width="14" height="26"></td>
          <td width="91" align="center">版本：${Version}</td>
          <td width="21"><img src="${ctx}/images2/bg/bottom_1.gif" width="14" height="26"></td>
          <td align="center"> Copyright &copy; 2004-2010  北京鑫干线网络科技发展有限公司 版权所有</td>
          <td width="14"><img src="${ctx}/images2/bg/bottom_1.gif" width="14" height="26"></td>
          <td width="175" align="center">日期 <fmt:formatDate value="${now}" type="date" dateStyle="full"/></td>
          <td width="20"><img src="${ctx}/images2/bg/bottomright.gif" width="14" height="26"></td>
        </tr>
      </table>
	</div>
</body>
</html>
