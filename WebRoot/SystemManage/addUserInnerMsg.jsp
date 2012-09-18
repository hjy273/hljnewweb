<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<%String msg = (String) request.getAttribute("innerMsg");%>
<span id="msgSpan">  系统提示信息 ：
<%=msg%></span>
<script language="javascript">
<!--
parent.msgSpan.innerHTML = msgSpan.innerHTML;
//-->
</script>
