<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<html>
	<head>
		<script type="text/javascript">
		function check(){
			if(document.forms[0].businessType.value == ''){
				alert("רҵ���Ʋ���Ϊ��");
				return false;
			}
			if(document.forms[0].file.value == ''){
				alert("����ĸ�������Ϊ��");
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
		<template:titile value="����Ѳ������Ϣ" />
		<s:form action="patrolItemImportAction_preview.jspx"
			enctype="multipart/form-data" onsubmit="return check();">
			<template:formTable contentwidth="300" namewidth="220">
				<template:formTr name="רҵ����">
					<input type="hidden" name="businessType" value="${businessType }" />
					<apptag:dynLabel dicType="businesstype" objType="dic"
						id="${businessType }"></apptag:dynLabel>
				</template:formTr>
				<template:formTr name="�����ļ�">
					<s:file name="file" cssClass="validate-file-xls"></s:file>
				</template:formTr>
				<template:formSubmit>
					<td width="100%" align="center">
						<input name="Submit" type="submit" class="button" value="����">
						<input type="button" class="button_length" value="Ѳ������Ϣģ������"
							onclick="downloadFile()">
					</td>
				</template:formSubmit>
			</template:formTable>
		</s:form>
		<table width="520" border="0" align="center" cellpadding="3"
			cellspacing="0">
			<tr>
				<td width="15%" valign="top" style="color: red">
					<b>˵����</b>
				</td>
				<td width="85%" valign="top" style="line-height: 20px; color: red;">
					<b>1�����뱣֤ģ��ķ�񲻱䣬����ģ�治�ܵ��룻 <br />
						2��ά�������Ŀ�����������ܵļ�̣���8-20������������������������������������������׼�н�����ϸ�������� <br />
						3�������Ǹ�Ѳ����Ѳ�����ڣ�Ŀǰ�������꣬�����£� <br /> 4������������������ά�������Ŀ��ѡ����ֵ���ı��� <br />
						5��ֵ��Χ���������Ͷ�Ӧ���磺ѡ��ѡ��ô��ֵ��Χ�п��Ը���ά�������Ŀ�ľ����������롰��,�񡱡�����,�ޡ���������,���������ȣ�������������ʾ����
						<br /> 6������������Ϊ��ֵ���ı���ʽʱ��ֵ��ΧΪ��ѡ� <br />
						7���쳣״̬�����ж�Ѳ����Ա�ϴ���Ѳ���������Ƿ�����쳣��ͨ���������жϳ��ü����Ŀ�Ƿ��쳣��ע�⣺ֵ��Χ�б�������쳣״̬�е�ֵ��
						<br /> 8��Ĭ��ֵ ��ѡ���������Ѳ���ն���Ѳ����Ŀ��Ĭ��ѡ� <br /> </b>
				</td>
			</tr>
		</table>
	</body>
</html>