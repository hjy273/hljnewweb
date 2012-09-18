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
			alert("未完成原因不能为空！");
			return false;
		} else if(nofinishreason.length > 510) {
			alert("未完成原因最长不要超过512个汉字！");
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
<template:titile value="填写计划未完成原因"/>
<br>
<form method="post" action="${ctx}/planstat/inputNofinishReason.jsp">
	<table  width="99%" align="center">
		<tr>
			<td><textarea name="nofinishreason" style="width:98%"  rows="10"></textarea></td>
		</tr>
		<tr>
			<td><font size="2" color="red">注意：计划未完成原因只能填写一次！请考虑好再保存</font></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td align="center">		
				<input type="hidden" id="planid" name="planid" value="<%=strPlanid%>">
				<input type="hidden" id="executorname" name="executorname" value="<%=strexecutorname %>">	
				<input type="button" class="button" onclick='toSaveReson()' value="保存" >
			</td>
		</tr>
		
		
	</table>

   
</form>
</body>
</html>



  
