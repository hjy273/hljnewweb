<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>�����̵�����</title>
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
  				alert("����д����ʱ��!");
  				typeName.focus();
  				return;
  			}
  			addMtUsedApplyForm.submit();
  		}
  		function openwin(contractorid){ 
  			var typeName = document.getElementById('createtime');
  			if(typeName.value.length == 0) {
  				alert("����д����ʱ��!");
  				typeName.focus();
  				return;
  			}
			window.open("${ctx}/MonthStateAction.do?method=statMonth&contractorid="+contractorid+"&Month="+typeName.value,"xx","height=600,width=800,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no"); 
			//д��һ�� 
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
		<template:titile value="�����̵�����" />
		<html:form method="post"
			action="/mtUsedAction?method=detailMtUsedApplyForm"
			styleId="addMtUsedApplyForm">
			<template:formTable namewidth="150" contentwidth="300">
				<template:formTr name="��������">
					<input id="createtime" name="createtime" class="inputtext Wdate"
						style="width: 205px" onfocus="WdatePicker({dateFmt:'yyyy-MM'})"
						readonly />
				</template:formTr>
				<template:formSubmit>
					<td>
						<!-- 
						<input type="button" class="button" value="�鿴������Ϣ"
							onclick="openwin('<%//=deptid%>');" />
						 -->
					</td>
					<td>
						<html:button property="action" styleClass="button"
							onclick="checkAddInfo();">��һ��</html:button>
					</td>
					<td>
						<html:reset styleClass="button">����</html:reset>
					</td>
				</template:formSubmit>
			</template:formTable>
		</html:form>
	</body>
</html>
