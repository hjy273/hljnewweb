<%@ include file="/common/header.jsp"%>
<%@ page import="com.cabletech.baseinfo.beans.*,org.apache.struts.util.*" %>
<%@ page import="com.cabletech.baseinfo.domainobjects.*"%>
<%
List list = (List)request.getAttribute("userlist");
List userlist = (List) list.get(0);
//System.out.println(list.size()+" "+((DynaBean)(userlist).get(0)).get("username"));
%>
<script language="JavaScript" type="">
function String.prototype.trim(){return this.replace(/(^\s*)/g, "").replace(/(\s*$)/g, "");}

function submitForm()
{
    var pv = /^\d{1,16}$/;
    if(document.userInfoBean.p1.value.length < 1||document.userInfoBean.p1.value.trim().length<1)
    {
		alert("新密码不能为空，请输入新密码!! ");
        document.userInfoBean.p1.value="";
        document.userInfoBean.p1.focus();
		return false;
	}
    document.userInfoBean.p1.value=document.userInfoBean.p1.value.trim();
    if(document.userInfoBean.p2.value.length < 1||document.userInfoBean.p2.value.trim().length<1)
    {
		alert("确认密码不能为空，请输入!! ");
        document.userInfoBean.p2.value="";
        document.userInfoBean.p2.focus();
		return false;
	}
    document.userInfoBean.p2.value=document.userInfoBean.p2.value.trim();
	if(document.userInfoBean.p1.value != document.userInfoBean.p2.value)
    {
		alert("两次密码不一致，请重新输入!! ");
        document.userInfoBean.p1.value="";
        document.userInfoBean.p2.value="";
        document.userInfoBean.p1.focus();
		return false;
    }
//    if(document.userInfoBean.p1.value.length <= 5){
//       alert("密码长度不得小于6位!! ");
//        return false;
//    }
//    obj = document.userInfoBean.p1
//    if(pv.test(obj.value)){
//       alert("密码不能为数字,请采用数字和字母结合方式!!");
//       return false;
//    }

    userInfoBean.submit();
}
</script>
<br>
<template:titile value="修改用户密码"/>
<html:form  method="post" action="/userinfoAction.do?method=updatePsw">

    <template:formTable>

		<template:formTr name="修改用户">
       <select name="userid" class="inputtext" style="width:170px">
        <%
        for(int i=0 ;i<userlist.size();i++){
          DynaBean db = (DynaBean)(userlist).get(i);
         %>
         <option value="<%=db.get("userid")%>"><%=db.get("username")%>----<%=db.get("userid")%></option>

         <%
        }
        %>
         </select>
		</template:formTr>

		<template:formTr name="新&nbsp;密&nbsp;码">
			<input type="password" class="inputtext" style="width:170px" name="p1" maxlength="16" >
		</template:formTr>
        <template:formTr name="确认密码">
			<input type="password" class="inputtext" style="width:170px" name="p2" maxlength="16" >
		</template:formTr>

		<template:formSubmit>
		  <td>
		  <input type="button" class="button" style="button" name="Submit" value="确认" onClick="submitForm()">
		  </td>
		  <td>
		  <input type="reset" class="button" style="button" name="Submit" value="取消">
		  </td>
        </template:formSubmit>

   </template:formTable>

</html:form>
