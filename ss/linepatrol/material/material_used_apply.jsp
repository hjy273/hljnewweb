<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>材料盘点申请</title>
		<%
		    UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		    String deptid = user.getDeptID();
		%>
		<script type="text/javascript"
			src="${ctx}/js/WdatePicker/WdatePicker.js"></script>
		<script type="text/javascript">
  		function checkAddInfo() {
  			var typeName = document.getElementById('createtime');
  			if(typeName.value.length == 0) {
  				alert("请填写申请时间!");
  				typeName.focus();
  				return;
  			}
  			addMtUsedApplyForm.submit();
  		}
  		function openwin(contractorid){ 
  			var typeName = document.getElementById('createtime');
  			if(typeName.value.length == 0) {
  				alert("请填写申请时间!");
  				typeName.focus();
  				return;
  			}
			window.open("${ctx}/MonthStateAction.do?method=statMonth&contractorid="+contractorid+"&Month="+typeName.value,"xx","height=600,width=800,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no"); 
			//写成一行 
		} 
  		</script>
		<link type="text/css" rel="stylesheet"
			href="${ctx}/js/WdatePicker/skin/WdatePicker.css">
		<style>
		table {
			font-size: 12px;
		}
		</style>
	</head>
	<body>
		<template:titile value="材料盘点申请" />
		<html:form method="post"
			action="/mtUsedAction?method=detailMtUsedApplyForm"
			styleId="addMtUsedApplyForm">
			<template:formTable namewidth="150" contentwidth="300">
				<template:formTr name="申请年月">
					<input id="createtime" name="createtime" class="inputtext Wdate"
						style="width: 205px" onfocus="WdatePicker({dateFmt:'yyyy-MM'})"
						readonly />
				</template:formTr>
				<template:formSubmit>
					<td>
						<!-- 
						<input type="button" class="button" value="查看材料信息"
							onclick="openwin('<%//=deptid%>');" />
						 -->
					</td>
					<td>
						<html:button property="action" styleClass="button"
							onclick="checkAddInfo();">下一步</html:button>
					</td>
					<td>
						<html:reset styleClass="button">重置</html:reset>
					</td>
				</template:formSubmit>
			</template:formTable>
		</html:form>
	</body>
</html>
