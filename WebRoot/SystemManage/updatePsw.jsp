<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ include file="/common/header.jsp"%>
<html>
	<head>
		<script language="JavaScript" type="text/javascript">

function submitForm(){
    if(jQuery('#oldpassword').val().length < 1){
         alert("ԭʼ���벻��Ϊ�գ�������������! ");
         return false;
    }
	if(jQuery('#newpassword').val().length < 1||jQuery('#newpassword').val().trim().length<1){
		alert("�����벻��Ϊ�գ�������������!! ");
		jQuery('#newpassword').val("");
		return false;
    }
	if(jQuery('#newpassword').val().length < 1||jQuery('#newpassword').val().trim().length<1){
		alert("ȷ�����벻��Ϊ�գ�������������!! ");
		jQuery('#newpassword').val("");
		return false;
    }
	jQuery('#newpassword').val(jQuery('#newpassword').val());
	jQuery('#newpassword').val(jQuery('#newpassword').val());
    if(jQuery('#newpassword').val().length <= 5){
        alert("���볤�Ȳ���С��6λ!! ");
		return false;
    }

	if(jQuery('#newpassword').val() != jQuery('#newpassword').val())
    {
		alert("�����������������������!! ");
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
		<template:titile value="�޸ĵ�ǰ�û�����" />
		<html:form method="Post" action="/userinfoAction.do?method=updatePsw" >
			<template:formTable contentwidth="200" namewidth="200">
				<template:formTr name="��ǰ�û�">
			${LOGIN_USER.userName }
			<input type="hidden" name="userid" value="${LOGIN_USER.userID}" />
				</template:formTr>

				<template:formTr name="ԭʼ����">
					<input type="password" class="inputtext" style="width: 170" id="oldpassword" name="oldpassword" maxlength="12">
				</template:formTr>

				<template:formTr name="�� �� ��">
					<input type="password" class="inputtext" style="width: 170" id="newpassword" name="newpassword" maxlength="16">
				</template:formTr>

				<template:formTr name="����ȷ��">
					<input type="password" class="inputtext" style="width: 170" id="affirmpassword" name="affirmpassword" maxlength="16">
				</template:formTr>

				<template:formSubmit>
					<input type="button" class="button" style="" name="Submit" value="�޸�����" onClick="submitForm();">
					<input type="button" class="button" style="" name="Submit" value="��������" onClick="resetForm();">
				</template:formSubmit>

			</template:formTable>

		</html:form>
	</body>
</html>