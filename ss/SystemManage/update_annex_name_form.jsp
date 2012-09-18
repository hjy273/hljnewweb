<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>附件查询</title>
		<script type="text/javascript">
			function submitForm(){
				if(submitForm1.original_name.value!=""){
					submitForm1.submit();
				}else{
					alert("请输入附件名称！");
				}
			}
		</script>
	</head>
	<body>
		<template:titile value="附件名称修改" />
		<form action="/WebApp/AnnexAction.do?method=updateAnnexFileName" method="post"
			id="submitForm1">
			<template:formTable>
				<template:formTr name="附件名称" isOdd="false">
					<input name="annex_id" type="hidden" value="${annex_id}" />
					<input name="old_name" type="hidden" value="${file_name}" />
					<input name="original_name" type="text" value="${file_name}"
						class="inputtext" style="width:120px;" />
				</template:formTr>
			</template:formTable>
				<table width="90%" border="0" align="center" cellpadding="3"
				cellspacing="0">
				<tr class=trwhite heigth="40">
					<td colspan="2" align="center">
						<input property="action" type="button" onclick="submitForm()"
							value="修改" class="button"></input>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input property="action" type="reset" value="重写" class="button"></input>
					</td>
				</tr>
				</table>
		</form>
	</body>
</html>
