<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<html>
	<head>
		<title>��������Ѳ���</title>
		<script type="text/javascript">
		Ext.onReady(function(){
			jQuery("#planPatrolTableAction_input").validate();
		});
		</script>
	</head>
	<body>
		<template:titile value="��������Ѳ���" />
		<s:form action="planPatrolTableAction_input" name="form" method="post"
			id="planPatrolTableAction_input" onsubmit="return check()">
			<template:formTable namewidth="150" contentwidth="300">
				<template:formTr name="��֤��">
					<input id="businessType" name="businessType" value="${businessType }"
						type="hidden" />
					<input id="identifyingCode" name="identifyingCode" value=""
						type="text" class="inputtext required" />
					<font color="#FF0000">*</font>
				</template:formTr>
				<template:formSubmit>
					<td>
						<input name="btnSubmit" value="�ύ" type="submit" class="button" />
						&nbsp;
						<input name="btnReset" value="ȡ��" type="reset" class="button" />
					</td>
				</template:formSubmit>
			</template:formTable>
		</s:form>
	</body>
</html>


