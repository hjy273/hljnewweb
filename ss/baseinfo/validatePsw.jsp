<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.baseinfo.beans.*"%>
<%@page import="com.cabletech.baseinfo.domainobjects.*"%>
<%UserInfo uiBean = (UserInfo) session.getAttribute("LOGIN_USER");%>
<script src="${ctx}/js/md5.js" type=text/javascript></script>
<script language="JavaScript" type="">
function submitForm()
{
  if(document.userInfoBean.p2.value.length < 1){
    alert("���벻��Ϊ�գ�����������!! ");
    return false;
  }
//  var p = md5(document.userInfoBean.p2.value)
//  if(document.userInfoBean.p1.value.length == 32){
//    if(document.userInfoBean.p1.value != p)
//    {
//      alert("���������������������!! ");
//      return false;
//    }
//  }else{
//    if(document.userInfoBean.p1.value != document.userInfoBean.p2.value)
//    {
//      alert("���������������������!! ");
//      return false;
//    }
//  }
 // alert(document.userInfoBean.p1.value+" "+document.userInfoBean.p1.value.length+" "+document.userInfoBean.p2.value);
  userInfoBean.submit();
}
function resetForm()
{
	document.userInfoBean.p2.value = "";

}
</script>
<br>
<template:titile value="�޸��û�����"/>
<html:form method="post" action="/userinfoAction.do?method=validatePsw" onsubmit="return submitForm()">
  <template:formTable contentwidth="200" namewidth="200">
    <template:formTr name="��ǰ�û�"><%=uiBean.getUserName()%>      <input type="hidden" name="userid" value="<%=uiBean.getUserID()%>"/>
      <!--input type="hidden" name="p1" value="<-=uiBean.getPassword()>"/-->
    </template:formTr>
    <template:formTr name="ȷ������">
      <input type="password" class="inputtext" style="width:170" name="p2" maxlength="12">&nbsp;&nbsp;<font color="red">*</font>
    </template:formTr>
    <template:formSubmit>
      <td>
        <input type="submit" class="button" style="button" name="Submit" value="ȷ��">
      </td>
      <td>
        <input type="button" class="button" style="button" name="Submit" value="ȡ��" onClick="resetForm()">
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
