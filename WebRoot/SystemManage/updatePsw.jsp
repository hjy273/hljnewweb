<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ include file="/common/header.jsp"%>
<html>
	<head>
		<script language="JavaScript" type="text/javascript">

function submitForm(){
    if(jQuery('#oldpassword').val().length < 1){
         alert("原始密码不能为空，请输入新密码! ");
         return false;
    }
	if(jQuery('#newpassword').val().length < 1||jQuery('#newpassword').val().trim().length<1){
		alert("新密码不能为空，请输入新密码!! ");
		jQuery('#newpassword').val("");
		return false;
    }
	if(jQuery('#newpassword').val().length < 1||jQuery('#newpassword').val().trim().length<1){
		alert("确认密码不能为空，请输入新密码!! ");
		jQuery('#newpassword').val("");
		return false;
    }
	jQuery('#newpassword').val(jQuery('#newpassword').val());
	jQuery('#newpassword').val(jQuery('#newpassword').val());
    if(jQuery('#newpassword').val().length <= 5){
        alert("密码长度不得小于6位!! ");
		return false;
    }

	if(jQuery('#newpassword').val() != jQuery('#newpassword').val())
    {
		alert("新密码输入错误，请重新输入!! ");
		jQuery('#newpassword').val("");
		jQuery('#newpassword').val("");
		return false;
	}
	jQuery('#newpassword').val(jQuery('#newpassword').val());
    userInfoBean.submit();
}
function resetForm()
{
	jQuery('#oldpassword').val("");
	jQuery('#newpassword').val("");
	jQuery('#newpassword').val("");
}
</script>
	</head>
	<body>
		<template:titile value="修改当前用户密码" />
		<html:form method="Post" action="/userinfoAction.do?method=updatePsw" >
			<template:formTable contentwidth="200" namewidth="200">
				<template:formTr name="当前用户">
			${LOGIN_USER.userName }
			<input type="hidden" name="userid" value="${LOGIN_USER.userID}" />
				</template:formTr>

				<template:formTr name="原始密码">
					<input type="password" class="inputtext" style="width: 170" id="oldpassword" name="oldpassword" maxlength="12">
				</template:formTr>

				<template:formTr name="新 密 码">
					<input type="password" class="inputtext" style="width: 170" id="newpassword" name="newpassword" maxlength="16">
				</template:formTr>

				<template:formTr name="密码确认">
					<input type="password" class="inputtext" style="width: 170" id="affirmpassword" name="affirmpassword" maxlength="16">
				</template:formTr>

				<template:formSubmit>
					<input type="button" class="button" style="" name="Submit" value="修改密码" onClick="submitForm();">
					<input type="button" class="button" style="" name="Submit" value="重新输入" onClick="resetForm();">
				</template:formSubmit>

			</template:formTable>

		</html:form>
	</body>
</html>