<%@ include file="/common/header.jsp"%>
<%@ page import="com.cabletech.baseinfo.beans.*" %>
<%@ page import="com.cabletech.baseinfo.domainobjects.*"%>
<br>
<template:titile value="�޸ĵ�ǰ�û�����"/>
<script src="${ctx}/js/md5.js" type=text/javascript></script>
<script language="JavaScript">
function String.prototype.trim(){return this.replace(/(^\s*)/g, "").replace(/(\s*$)/g, "");}

function submitForm()
{
    var pv = /^\d{1,16}$/;

       if(document.userInfoBean.oldpassword.value.length < 1)
       {
         alert("ԭʼ���벻��Ϊ�գ�������������! ");
         return false;
       }

	if(document.userInfoBean.newpassword.value.length < 1||document.userInfoBean.newpassword.value.trim().length<1)
    {
		alert("�����벻��Ϊ�գ�������������!! ");
        document.userInfoBean.newpassword.value="";
		return false;
    }
	if(document.userInfoBean.affirmpassword.value.length < 1||document.userInfoBean.affirmpassword.value.trim().length<1)
    {
		alert("ȷ�����벻��Ϊ�գ�������������!! ");
        document.userInfoBean.affirmpassword.value="";
		return false;
    }
    document.userInfoBean.newpassword.value=document.userInfoBean.newpassword.value.trim();
    document.userInfoBean.affirmpassword.value=document.userInfoBean.affirmpassword.value.trim();
//    if(pv.test(document.updatePsw.newPassword.value)){
//        alert("���벻��Ϊ����,��������ֺ���ĸ��Ϸ�ʽ!!");
//        return false;
//    }
//    if(document.updatePsw.newPassword.value.length <= 5){
//        alert("���볤�Ȳ���С��6λ!! ");
//		return false;
//    }

	if(document.userInfoBean.newpassword.value != document.userInfoBean.affirmpassword.value)
    {
		alert("�����������������������!! ");
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

		<template:formTr name="��ǰ�û�">
			<%=uiBean.getUserName()%>
            <html:hidden property="userID"/>
		</template:formTr>

		<template:formTr name="ԭʼ����">
			<input type="password" class="inputtext" style="width:170" name="oldpassword" maxlength="12">&nbsp;&nbsp;<font color="red">*</font>
		</template:formTr>

		<template:formTr name="�� �� ��">
			<input type="password" class="inputtext" style="width:170" name="newpassword" maxlength="16">&nbsp;&nbsp;<font color="red">*</font>
        </template:formTr>

		<template:formTr name="����ȷ��">
			<input type="password" class="inputtext" style="width:170" name="affirmpassword" maxlength="16">&nbsp;&nbsp;<font color="red">*</font>
        </template:formTr>

		<template:formSubmit>
		  <td >
		  <input type="button" class="button" style="button" name="Submit" value="�޸�����" onClick="submitForm()">
		  </td>
		  <td >
		  <input type="button" class="button" style="button" name="Submit" value="��������" onClick="resetForm()">
		  </td>
        </template:formSubmit>

   </template:formTable>

</html:form>


