<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<html>
	<head>
		<script type="text/javascript">
		function check(){
			if(document.forms[0].file.value == ''){
				alert("����ĸ�������Ϊ��");
				return false;
			}
			return true;
		}
		function downloadFile() {
			location.href = "/bspweb/cell/cellImportAction_downloadTemplate.jspx";
		}
	</script>
	</head>
	<body>
		<template:titile value="����С����Ϣ" />
		<s:form action="/cellImportAction_checkImportData.jspx"
			enctype="multipart/form-data" onsubmit="return check();">
			<template:formTable contentwidth="300" namewidth="220">
				<template:formTr name="����С�������ļ�">
					<s:file name="file" cssClass="validate-file-xls"></s:file>
				</template:formTr>
				<template:formSubmit>
					<td width="100%" align="center">
						<input name="Submit" type="submit" class="button" value="����">
						<input type="button" class="button_length" value="С����Ϣģ������"
							onclick="downloadFile()">
					</td>
				</template:formSubmit>
			</template:formTable>
		</s:form>
	</body>
</html>