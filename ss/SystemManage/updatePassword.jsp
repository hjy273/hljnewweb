<%@ include file="/common/header.jsp"%>
<%@ page import="com.cabletech.baseinfo.beans.*" %>
<%@ page import="com.cabletech.baseinfo.domainobjects.*"%>
<br>
<template:titile value="修改当前用户密码"/>
<script src="${ctx}/js/md5.js" type=text/javascript></script>
<script language="JavaScript">
function String.prototype.trim(){return this.replace(/(^\s*)/g, "").replace(/(\s*$)/g, "");}

function submitForm()
{
    var pv = /^\d{1,16}$/;

       if(document.userInfoBean.oldpassword.value.length < 1)
       {
         alert("原始密码不能为空，请输入新密码! ");
         return false;
       }

	if(document.userInfoBean.newpassword.value.length < 1||document.userInfoBean.newpassword.value.trim().length<1)
    {
		alert("新密码不能为空，请输入新密码!! ");
        document.userInfoBean.newpassword.value="";
		return false;
    }
	if(document.userInfoBean.affirmpassword.value.length < 1||document.userInfoBean.affirmpassword.value.trim().length<1)
    {
		alert("确认密码不能为空，请输入新密码!! ");
        document.userInfoBean.affirmpassword.value="";
		return false;
    }
    document.userInfoBean.newpassword.value=document.userInfoBean.newpassword.value.trim();
    document.userInfoBean.affirmpassword.value=document.userInfoBean.affirmpassword.value.trim();
//    if(pv.test(document.updatePsw.newPassword.value)){
//        alert("密码不能为数字,请采用数字和字母结合方式!!");
//        return false;
//    }
//    if(document.updatePsw.newPassword.value.length <= 5){
//        alert("密码长度不得小于6位!! ");
//		return false;
//    }

	if(document.userInfoBean.newpassword.value != document.userInfoBean.affirmpassword.value)
    {
		alert("新密码输入错误，请重新输入!! ");
        document.userInfoBean.newpassword.value="";
        document.userInfoBean.affirmpassword.value="";
		return false;
	}
    document.userInfoBean.affirmpassword.value=document.userInfoBean.affirmpassword.value.trim();
    userInfoBean.submit();
}
function resetForm()
{
	document.userInfoBean.oldpassword.value = "";
	document.userInfoBean.newpassword.value = "";
	document.userInfoBean.affirmpassword.value = "";
}
</script>
<%
        UserInfo uiBean=(UserInfo)session.getAttribute("LOGIN_USER");

%>
<html:form method="Post" action="/login.do?method=updatePsw">

    <template:formTable contentwidth="200" namewidth="200">

		<template:formTr name="当前用户">
			<%=uiBean.getUserName()%>
            <html:hidden property="userID"/>
		</template:formTr>

		<template:formTr name="原始密码">
			<input type="password" class="inputtext" style="width:170" name="oldpassword" maxlength="12">&nbsp;&nbsp;<font color="red">*</font>
		</template:formTr>

		<template:formTr name="新 密 码">
			<input type="password" class="inputtext" style="width:170" name="newpassword" maxlength="16">&nbsp;&nbsp;<font color="red">*</font>
        </template:formTr>

		<template:formTr name="密码确认">
			<input type="password" class="inputtext" style="width:170" name="affirmpassword" maxlength="16">&nbsp;&nbsp;<font color="red">*</font>
        </template:formTr>

		<template:formSubmit>
		  <td >
		  <input type="button" class="button" style="button" name="Submit" value="修改密码" onClick="submitForm()">
		  </td>
		  <td >
		  <input type="button" class="button" style="button" name="Submit" value="重新输入" onClick="resetForm()">
		  </td>
        </template:formSubmit>

   </template:formTable>

</html:form>


