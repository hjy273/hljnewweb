<%@ include file="/common/header.jsp"%>

<script language="javascript" type="text/javascript">
function toGetBack(){
        var url = "${ctx}/batchPlan.do?method=queryBatchPlan";
        self.location.replace(url);

}
</script>



<br>
<br>
<template:titile value="���������ƻ�" />

<html:form action="/batchPlan?method=queryBatchPlan">

	<template:formTable namewidth="150" contentwidth="300">
		
		
		<template:formTr name="�����ƻ�����">
			<html:text property="batchname" style="width:225" styleClass="inputtext"></html:text>
		</template:formTr>
		<template:formSubmit>
			<td>
				<html:submit property="action" styleClass="button">����</html:submit>
			</td>
			<td>
				<input name="Button2" type="reset" class="button" value="ȡ��">
			</td>
			<td>
				<input type="button" class="button" onclick="toGetBack()" value="����">

			</td>
		</template:formSubmit>

	</template:formTable>
</html:form>
