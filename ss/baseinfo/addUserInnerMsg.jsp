<%@include file="/common/header.jsp"%>
<%String msg = (String) request.getAttribute("innerMsg");%>
<span id="msgSpan"> <font color="red"> ϵͳ��ʾ��Ϣ ��
<%=msg%></font></span>
<script language="javascript">
<!--
parent.msgSpan.innerHTML = msgSpan.innerHTML;
//-->
</script>
