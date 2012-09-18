<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<html>
	<head>
		<script type="text/javascript">
		function check(){
			if(document.forms[0].businessType.value == ''){
				alert("专业名称不能为空");
				return false;
			}
			if(document.forms[0].file.value == ''){
				alert("导入的附件不能为空");
				return false;
			}
			return true;
		}
		function downloadFile() {
			location.href = "/bspweb/patrolManager/patrolItemImportAction_downloadTemplate.jspx";
		}
	</script>
	</head>
	<body>
		<template:titile value="导入巡检项信息" />
		<s:form action="patrolItemImportAction_preview.jspx"
			enctype="multipart/form-data" onsubmit="return check();">
			<template:formTable contentwidth="300" namewidth="220">
				<template:formTr name="专业名称">
					<input type="hidden" name="businessType" value="${businessType }" />
					<apptag:dynLabel dicType="businesstype" objType="dic"
						id="${businessType }"></apptag:dynLabel>
				</template:formTr>
				<template:formTr name="数据文件">
					<s:file name="file" cssClass="validate-file-xls"></s:file>
				</template:formTr>
				<template:formSubmit>
					<td width="100%" align="center">
						<input name="Submit" type="submit" class="button" value="导入">
						<input type="button" class="button_length" value="巡检项信息模板下载"
							onclick="downloadFile()">
					</td>
				</template:formSubmit>
			</template:formTable>
		</s:form>
		<table width="520" border="0" align="center" cellpadding="3"
			cellspacing="0">
			<tr>
				<td width="15%" valign="top" style="color: red">
					<b>说明：</b>
				</td>
				<td width="85%" valign="top" style="line-height: 20px; color: red;">
					<b>1、必须保证模版的风格不变，否则模版不能导入； <br />
						2、维护检测项目的描述尽可能的简短，在8-20个汉字内描述清楚，若不能描述清楚可以在质量标准中进行详细的描述； <br />
						3、周期是改巡检项巡检周期，目前包括：年，季，月； <br /> 4、输入类型用与描述维护检测项目是选择，数值，文本； <br />
						5、值域范围与数据类型对应，如：选择单选那么在值域范围中可以根据维护检测项目的具体描述输入“是,否”、“有,无”、“正常,不正常”等，具体详见上面的示例；
						<br /> 6、在输入类型为数值或文本方式时，值域范围为可选项； <br />
						7、异常状态用于判断巡检人员上传的巡检项数据是否存在异常。通过它才能判断出该检测项目是否异常。注意：值域范围中必须包含异常状态中的值；
						<br /> 8、默认值 可选项，用于设置巡检终端上巡检项目的默认选项； <br /> </b>
				</td>
			</tr>
		</table>
	</body>
</html>