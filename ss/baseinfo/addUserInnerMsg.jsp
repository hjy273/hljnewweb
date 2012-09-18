<%@include file="/common/header.jsp"%>
<%String msg = (String) request.getAttribute("innerMsg");%>
<span id="msgSpan"> <font color="red"> 系统提示信息 ：
<%=msg%></font></span>
<script language="javascript">
<!--
parent.msgSpan.innerHTML = msgSpan.innerHTML;
//-->
</script>
