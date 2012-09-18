<%@ include file="/common/header.jsp"%>
<html>
	<script language="javascript" type="text/javascript">
	function String.prototype.trim(){return this.replace(/(^\s*)/g, "").replace(/(\s*$)/g, "");}
	function isValidForm() {
		var regNum=/^\d+$/;
		var regMail=/^.+@.+$/;
		if(document.UserMailInfoBean.orderNumberStr.value ==""||document.UserMailInfoBean.orderNumberStr.value.trim()==""){
			alert("显示序号不能为空!! ");
			return false;
		}
		if(document.UserMailInfoBean.mailName.value ==""||document.UserMailInfoBean.mailName.value.trim()==""){
			alert("邮箱名称不能为空!! ");
			return false;
		}
		if(document.UserMailInfoBean.emailAddress.value ==""||document.UserMailInfoBean.emailAddress.value.trim()==""){
			alert("邮箱地址不能为空!! ");
			return false;
		}
		if(!regNum.test(document.UserMailInfoBean.orderNumberStr.value)){
			alert("显示序号必须为数字！！");
			return false;
		}
		if(!regMail.test(document.UserMailInfoBean.emailAddress.value)){
			alert("邮箱地址不符合电子邮箱格式！！");
			return false;
		}
		return true;
	}
	//-->
</script>
	<script type="text/javascript" src="${ctx}/js/ckeditor/ckeditor.js"></script>
	<body>
		<template:titile value="修改邮箱信息" />
		<html:form action="/user_mail.do?method=updateUserMail" method="post"
			onsubmit="return isValidForm();">
			<template:formTable>
				<template:formTr name="显示序号" isOdd="false">
					<html:hidden property="id" name="user_mail_info_bean" />
					<html:text property="orderNumberStr" name="user_mail_info_bean"
						styleClass="inputtext" style="width:300" />&nbsp;&nbsp;<font
						color="red">*</font>
				</template:formTr>
				<template:formTr name="邮箱名称" isOdd="true">
					<html:text property="mailName" name="user_mail_info_bean"
						styleClass="inputtext" style="width:300" />&nbsp;&nbsp;<font
						color="red">*</font>
				</template:formTr>
				<template:formTr name="邮箱地址" isOdd="true">
					<html:text property="emailAddress" name="user_mail_info_bean"  styleClass="inputtext"
						style="width:300" />&nbsp;&nbsp;<font color="red">*</font>
				</template:formTr>
				<template:formSubmit>
					<td colspan="text-align:center">
						<html:submit property="action2" styleClass="button">保存</html:submit>
						<html:reset styleClass="button">重填</html:reset>
					</td>
				</template:formSubmit>
			</template:formTable>
		</html:form>
	</body>
</html>
