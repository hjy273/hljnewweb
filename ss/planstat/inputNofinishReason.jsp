<%@ include file="/common/header.jsp"%>
<%
	String strPlanid = request.getParameter("planid");
	String strexecutorname = request.getParameter("executorname");
 %>
<script language="javascript" type="">
	function toSaveReson() {
		var planid = document.getElementById("planid").value;
		var executorname = document.getElementById("executorname").value;

		var nofinishreason = document.getElementById("nofinishreason").value;

		if(nofinishreason == "") {
			alert("δ���ԭ����Ϊ�գ�");
			return false;
		} else if(nofinishreason.length > 510) {
			alert("δ���ԭ�����Ҫ����512�����֣�");
			return false;
		}
		var url="${ctx}/PlanExeResultAction.do?method=saveNofinishReason";

  //	self.location.replace(url);
document.forms[0].action = url;
document.forms[0].submit();
	}
	
</script>

<html>
<head>
</head>
<br>
<body>
<template:titile value="��д�ƻ�δ���ԭ��"/>
<br>
<form method="post" action="${ctx}/planstat/inputNofinishReason.jsp">
	<table  width="99%" align="center">
		<tr>
			<td><textarea name="nofinishreason" style="width:98%"  rows="10"></textarea></td>
		</tr>
		<tr>
			<td><font size="2" color="red">ע�⣺�ƻ�δ���ԭ��ֻ����дһ�Σ��뿼�Ǻ��ٱ���</font></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td align="center">		
				<input type="hidden" id="planid" name="planid" value="<%=strPlanid%>">
				<input type="hidden" id="executorname" name="executorname" value="<%=strexecutorname %>">	
				<input type="button" class="button" onclick='toSaveReson()' value="����" >
			</td>
		</tr>
		
		
	</table>

   
</form>
</body>
</html>



  
