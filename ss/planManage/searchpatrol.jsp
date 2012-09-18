<%@ include file="/common/header.jsp"%>

<script language="javascript" type="text/javascript">
function toGetBack(){
        var url = "${ctx}/batchPlan.do?method=queryBatchPlan";
        self.location.replace(url);

}
</script>



<br>
<br>
<template:titile value="检索批量计划" />

<html:form action="/batchPlan?method=queryBatchPlan">

	<template:formTable namewidth="150" contentwidth="300">
		
		
		<template:formTr name="批量计划名称">
			<html:text property="batchname" style="width:225" styleClass="inputtext"></html:text>
		</template:formTr>
		<template:formSubmit>
			<td>
				<html:submit property="action" styleClass="button">检索</html:submit>
			</td>
			<td>
				<input name="Button2" type="reset" class="button" value="取消">
			</td>
			<td>
				<input type="button" class="button" onclick="toGetBack()" value="返回">

			</td>
		</template:formSubmit>

	</template:formTable>
</html:form>
