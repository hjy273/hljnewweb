<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<html>
	<head>
		<title>生成联机巡检表</title>
		<script type="text/javascript">
		Ext.onReady(function(){
			jQuery("#planPatrolTableAction_input").validate();
		});
		</script>
	</head>
	<body>
		<template:titile value="生成联机巡检表" />
		<s:form action="planPatrolTableAction_input" name="form" method="post"
			id="planPatrolTableAction_input" onsubmit="return check()">
			<template:formTable namewidth="150" contentwidth="300">
				<template:formTr name="验证码">
					<input id="businessType" name="businessType" value="${businessType }"
						type="hidden" />
					<input id="identifyingCode" name="identifyingCode" value=""
						type="text" class="inputtext required" />
					<font color="#FF0000">*</font>
				</template:formTr>
				<template:formSubmit>
					<td>
						<input name="btnSubmit" value="提交" type="submit" class="button" />
						&nbsp;
						<input name="btnReset" value="取消" type="reset" class="button" />
					</td>
				</template:formSubmit>
			</template:formTable>
		</s:form>
	</body>
</html>


