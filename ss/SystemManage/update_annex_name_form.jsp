<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>������ѯ</title>
		<script type="text/javascript">
			function submitForm(){
				if(submitForm1.original_name.value!=""){
					submitForm1.submit();
				}else{
					alert("�����븽�����ƣ�");
				}
			}
		</script>
	</head>
	<body>
		<template:titile value="���������޸�" />
		<form action="/WebApp/AnnexAction.do?method=updateAnnexFileName" method="post"
			id="submitForm1">
			<template:formTable>
				<template:formTr name="��������" isOdd="false">
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
							value="�޸�" class="button"></input>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input property="action" type="reset" value="��д" class="button"></input>
					</td>
				</tr>
				</table>
		</form>
	</body>
</html>
