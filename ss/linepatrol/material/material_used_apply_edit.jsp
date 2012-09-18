<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>材料盘点申请</title>
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
  				alert("请填写申请时间!");
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
  				alert("请填写申请时间!");
  				typeName.focus();
  				return;
  			}
			window.open("${ctx}/MonthStateAction.do?method=statMonth&contractorid="+contractorid+"&Month="+typeName.value,"xx","height=600,width=800,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no"); 
			//写成一行 
		} 
		</script>
	</head>
	<body>
		<template:titile value="材料盘点申请" />
		<html:form method="post"
			action="/mtUsedAction?method=detailMtUsedApplyEditedForm"
			styleId="doMtUsedEditForm">
			<template:formTable namewidth="150" contentwidth="300">
				<template:formTr name="申请年月">
					<input type="hidden" name="id"
						value='<bean:write name="editBean" property="id"/>' />
					<input name="createtime" class="inputtext Wdate" style="width: 205px"
						value='<bean:write name="editBean" property="createtime"/>'
						onfocus="WdatePicker({dateFmt:'yyyy-MM'})" readonly />
				</template:formTr>
				<template:formSubmit>
					<td>
						<!-- 
                           <input type="button" class="button"  value="查看材料信息" onclick="openwin('<%//= deptid%>');"/>
                            -->
					</td>
					<td>
						<html:button property="action" styleClass="button"
							onclick="checkAddInfo();">下一步</html:button>
					</td>
					<td>
					<!-- 	<html:button property="action" styleClass="button"
							onclick="goBack();">返回</html:button> -->
							<input type="button" class="button" onclick="history.back()" value="返回"/>
							
					</td>
				</template:formSubmit>
			</template:formTable>
		</html:form>
	</body>
</html>
