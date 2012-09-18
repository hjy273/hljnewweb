<%@include file="/common/header.jsp"%>
<html>
	<head>
		<title>查询修缮结算信息</title>
		<script type="text/javascript" src="./js/prototype.js"></script>
		<script type="text/javascript" src="./js/json2.js"></script>
		<script type="text/javascript" defer="defer"
			src="<%=request.getContextPath()%>/js/WdatePicker/WdatePicker.js"></script>
		<script type="text/javascript">
		getSelectDateThis=function(strID) {
			document.all.item(strID).value = getPopDate(document.all.item(strID).value);
			return true;
		}
    	</script>
	</head>
	<body>
		<br>
		<template:titile value="查询修缮结算信息" />
		<form action="${ctx}/remedy_apply_square.do?method=querySquareList"
			id="queryForm" method="post">
			<template:formTable namewidth="200" contentwidth="200">
				<html:hidden property="reset_query" value="1" />
				<html:hidden property="power" value="52302" />
				<template:formTr name="编号">
					<input name="remedyCode" class="inputtext" type="text"
						style="width:215;" maxlength="20" />
				</template:formTr>
				<template:formTr name="项目名称">
					<input name="projectName" class="inputtext" type="text"
						style="width:215;" maxlength="20" />
				</template:formTr>
				<template:formTr name="修缮合计费用">
					介于
					<input name="totalFeeMin" class="inputtext" type="text"
						style="width:65;" maxlength="20" />
					和
					<input name="totalFeeMax" class="inputtext" type="text"
						style="width:65;" maxlength="20" />
					之间
				</template:formTr>
				<template:formTr name="申请时间">
					介于
					<input name="remedyDateMin" class="inputtext" type="text"
						style="width:65;" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" />
					<!-- 
					<INPUT TYPE='BUTTON' VALUE='▼' ID='btnRemedyDateMin'
						onclick="getSelectDateThis('remedyDateMin')"
						STYLE="font: 'normal small-caps 6pt serif';">
					 -->
					和
					<input name="remedyDataMax" class="inputtext" type="text"
						style="width:65;" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" />
					<!-- 
					<INPUT TYPE='BUTTON' VALUE='▼' ID='btnRemedyDateMax'
						onclick="getSelectDateThis('remedyDateMax')"
						STYLE="font: 'normal small-caps 6pt serif';">
					 -->
					之间
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
		</form>
		<script type="text/javascript">
		</script>
	</body>
</html>
