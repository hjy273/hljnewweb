<%@include file="/common/header.jsp"%>
<html>
	<script type="text/javascript"
		src="${ctx}/js/WdatePicker/WdatePicker.js"></script>
	<link type="text/css" rel="stylesheet"
		href="${ctx}/js/WdatePicker/skin/WdatePicker.css">
	<head>
		<script language="javascript" type="">
       			function addGoBack(){
   			location.href = "${ctx}/stataction.do?method=loadQueryForm";
    			return true;
			}
			function checkForm(){
				var month = document.getElementById("month");
				if(month.value == ""){
					alert("请选择月份!");
					month.focus();
					return false;
				}
				return true;
			}
		</script>
	</head>
	<body>
		<template:titile value="按代维统计" />
		<html:form method="Post"
			action="/statRemedyAction.do?method=statRemedyByContractor"
			onsubmit="return checkForm();">
			<template:formTable contentwidth="200" namewidth="200">
				<template:formTr name="代维<font color='red'>*</font>">
					<select name="contractorId" id="contractorId" class="inputtext"
						style="width: 200px">
						<logic:present name="contractors">
							<logic:iterate id="contractor" name="contractors">
								<option
									value="<bean:write name='contractor' property='contractorid'/>">
									<bean:write name="contractor" property="contractorname" />
								</option>
							</logic:iterate>
						</logic:present>
					</select>
				</template:formTr>
				<template:formTr name="月份<font color='red'>*</font>">
					<input name="month" id="month" class="inputtext"
						style="width: 200" onfocus="WdatePicker({dateFmt:'yyyy-MM'})"
						readonly />
				</template:formTr>

				<template:formSubmit>
					<td>
						<html:submit styleClass="button">查询</html:submit>
					</td>
					<td>
						<html:reset styleClass="button">重置</html:reset>
					</td>
				</template:formSubmit>

			</template:formTable>
		</html:form>
	</body>