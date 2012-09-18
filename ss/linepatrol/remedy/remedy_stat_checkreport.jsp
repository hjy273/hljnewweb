<%@include file="/common/header.jsp"%>
<html>
	<head>
		<title>验收报告</title>
		<link href="${ctx}/css/linepatrol_css.css" rel="stylesheet"
			type="text/css">
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/js/prototype.js"></script>
		<script type="text/javascript"
			src="${ctx}/js/WdatePicker/WdatePicker.js"></script>
		<script type="text/javascript">
			function checkForm(){
				var remedyDate = document.getElementById("remedyDate");
				if(remedyDate.value == ""){
					alert("请选择月份!");
					remedyDate.focus();
					return false;
				}
				return true;
			}
		</script>
	</head>
	<body>
		<template:titile value="查询验收报告" />
		<form action="${ctx}/remedyReportAction.do?method=getCheckReport"
			onsubmit="return checkForm();" method="post">
			<template:formTable contentwidth="200" namewidth="200">
				<logic:equal value="1" name="LOGIN_USER" property="deptype">
					<template:formTr name="施工单位<font color='red'>*</font>">
						<select id="selectContractor" class="inputtext" name="selectContractor" style="width:200px" >
							<logic:present scope="request" name="contractors">
								<logic:iterate id="contractor" name="contractors">
									<option
										value="<bean:write name="contractor" property="contractorid" />">
										<bean:write name="contractor" property="contractorname" />
									</option>
								</logic:iterate>
							</logic:present>
						</select>
					</template:formTr>
				</logic:equal>
				<template:formTr name="区县<font color='red'>*</font>">
					<select id="selectTown" name="selectTown" class="inputtext" style="width:200px">
						<logic:present scope="request" name="towns">
							<logic:iterate id="oneTown" name="towns">
								<option value="<bean:write name="oneTown" property="id" />">
									<bean:write name="oneTown" property="town" />
								</option>
							</logic:iterate>
						</logic:present>
					</select>
				</template:formTr>
				<template:formTr name="月份<font color='red'>*</font>">
					<input name="remedyDate" class="inputtext" style="width: 200"
						id="remedyDate" onfocus="WdatePicker({dateFmt:'yyyy-MM'})"
						readonly />
				</template:formTr>
				<template:formSubmit>
					<td>
						<input type="submit" class="button" name="sub" value="查询" />
					<td>
						<input type="reset" class="button" value="重置" />
					</td>
				</template:formSubmit>
			</template:formTable>
		</form>
	</body>