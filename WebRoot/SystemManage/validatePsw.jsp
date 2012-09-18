<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<script src="${ctx}/js/md5.js" type=text/javascript></script>
<script language="JavaScript" type="">
function submitForm()
{
  if($("p2").value.length < 1){
    alert("密码不能为空，请重新输入!! ");
    return false;
  }
//  var p = md5(document.userInfoBean.p2.value)
//  if(document.userInfoBean.p1.value.length == 32){
//    if(document.userInfoBean.p1.value != p)
//    {
//      alert("密码输入错误，请重新输入!! ");
//      return false;
//    }
//  }else{
//    if(document.userInfoBean.p1.value != document.userInfoBean.p2.value)
//    {
//      alert("密码输入错误，请重新输入!! ");
//      return false;
//    }
//  }
 // alert(document.userInfoBean.p1.value+" "+document.userInfoBean.p1.value.length+" "+document.userInfoBean.p2.value);
}
function resetForm()
{
	document.userInfoBean.p2.value = "";

}
</script>
<template:titile value="重置用户口令" />
<html:form method="post" action="/userinfoAction.do?method=validatePsw" onsubmit="return submitForm()">
	<template:formTable contentwidth="200" namewidth="200">
		<template:formTr name="当前用户">${LOGIN_USER.userName}
			<input type="hidden" name="userid" value="${LOGIN_USER.userID}" />
		</template:formTr>
		<template:formTr name="确认密码">
			<input type="password" class="inputtext" style="width: 170" name="p2" maxlength="12">
		</template:formTr>
		<template:formSubmit>
			<td>
				<input type="submit" class="button" style="" name="Submit" value="确认">
			</td>
			<td>
				<input type="button" class="button" style="" name="Submit" value="取消" onClick="resetForm()">
			</td>
		</template:formSubmit>
	</template:formTable>
</html:form>
