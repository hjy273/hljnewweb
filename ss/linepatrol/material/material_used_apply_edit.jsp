<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>�����̵�����</title>
		<script type="text/javascript"
			src="${ctx}/js/WdatePicker/WdatePicker.js"></script>
		<link type="text/css" rel="stylesheet"
			href="${ctx}/js/WdatePicker/skin/WdatePicker.css">
		<link href="${ctx}/css/linepatrol_css.css" rel="stylesheet"
			type="text/css">
		<style>
		table {
			font-size: 12px;
		}
		</style>
		<%
		    UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		    String deptid = user.getDeptID();
		%>
		<script type="text/javascript">
		function checkAddInfo() {
  			var typeName = document.getElementById('createtime');
  			if(typeName.value.length == 0) {
  				alert("����д����ʱ��!");
  				typeName.focus();
  				return;
  			}
  			doMtUsedEditForm.submit();
  		}
		goBack=function(){
			var url = '<%=(String) request.getSession().getAttribute("S_BACK_URL")%>';
			self.location.replace(url);
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
	</head>
	<body>
		<template:titile value="�����̵�����" />
		<html:form method="post"
			action="/mtUsedAction?method=detailMtUsedApplyEditedForm"
			styleId="doMtUsedEditForm">
			<template:formTable namewidth="150" contentwidth="300">
				<template:formTr name="��������">
					<input type="hidden" name="id"
						value='<bean:write name="editBean" property="id"/>' />
					<input name="createtime" class="inputtext Wdate" style="width: 205px"
						value='<bean:write name="editBean" property="createtime"/>'
						onfocus="WdatePicker({dateFmt:'yyyy-MM'})" readonly />
				</template:formTr>
				<template:formSubmit>
					<td>
						<!-- 
                           <input type="button" class="button"  value="�鿴������Ϣ" onclick="openwin('<%//= deptid%>');"/>
                            -->
					</td>
					<td>
						<html:button property="action" styleClass="button"
							onclick="checkAddInfo();">��һ��</html:button>
					</td>
					<td>
					<!-- 	<html:button property="action" styleClass="button"
							onclick="goBack();">����</html:button> -->
							<input type="button" class="button" onclick="history.back()" value="����"/>
							
					</td>
				</template:formSubmit>
			</template:formTable>
		</html:form>
	</body>
</html>
