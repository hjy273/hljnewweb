<%@ include file="/common/header.jsp"%>
<html>
	<script language="javascript" type="text/javascript">
	function String.prototype.trim(){return this.replace(/(^\s*)/g, "").replace(/(\s*$)/g, "");}
	function isValidForm() {
		var regNum=/^\d+$/;
		var regMail=/^.+@.+$/;
		if(document.UserMailInfoBean.orderNumberStr.value ==""||document.UserMailInfoBean.orderNumberStr.value.trim()==""){
			alert("��ʾ��Ų���Ϊ��!! ");
			return false;
		}
		if(document.UserMailInfoBean.mailName.value ==""||document.UserMailInfoBean.mailName.value.trim()==""){
			alert("�������Ʋ���Ϊ��!! ");
			return false;
		}
		if(document.UserMailInfoBean.emailAddress.value ==""||document.UserMailInfoBean.emailAddress.value.trim()==""){
			alert("�����ַ����Ϊ��!! ");
			return false;
		}
		if(!regNum.test(document.UserMailInfoBean.orderNumberStr.value)){
			alert("��ʾ��ű���Ϊ���֣���");
			return false;
		}
		if(!regMail.test(document.UserMailInfoBean.emailAddress.value)){
			alert("�����ַ�����ϵ��������ʽ����");
			return false;
		}
		return true;
	}
	//-->
</script>
	<script type="text/javascript" src="${ctx}/js/ckeditor/ckeditor.js"></script>
	<body>
		<template:titile value="�޸�������Ϣ" />
		<html:form action="/user_mail.do?method=updateUserMail" method="post"
			onsubmit="return isValidForm();">
			<template:formTable>
				<template:formTr name="��ʾ���" isOdd="false">
					<html:hidden property="id" name="user_mail_info_bean" />
					<html:text property="orderNumberStr" name="user_mail_info_bean"
						styleClass="inputtext" style="width:300" />&nbsp;&nbsp;<font
						color="red">*</font>
				</template:formTr>
				<template:formTr name="��������" isOdd="true">
					<html:text property="mailName" name="user_mail_info_bean"
						styleClass="inputtext" style="width:300" />&nbsp;&nbsp;<font
						color="red">*</font>
				</template:formTr>
				<template:formTr name="�����ַ" isOdd="true">
					<html:text property="emailAddress" name="user_mail_info_bean"  styleClass="inputtext"
						style="width:300" />&nbsp;&nbsp;<font color="red">*</font>
				</template:formTr>
				<template:formSubmit>
					<td colspan="text-align:center">
						<html:submit property="action2" styleClass="button">����</html:submit>
						<html:reset styleClass="button">����</html:reset>
					</td>
				</template:formSubmit>
			</template:formTable>
		</html:form>
	</body>
</html>
