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
		alert("�����벻��Ϊ�գ�������������!! ");
        document.userInfoBean.p1.value="";
        document.userInfoBean.p1.focus();
		return false;
	}
    document.userInfoBean.p1.value=document.userInfoBean.p1.value.trim();
    if(document.userInfoBean.p2.value.length < 1||document.userInfoBean.p2.value.trim().length<1)
    {
		alert("ȷ�����벻��Ϊ�գ�������!! ");
        document.userInfoBean.p2.value="";
        document.userInfoBean.p2.focus();
		return false;
	}
    document.userInfoBean.p2.value=document.userInfoBean.p2.value.trim();
	if(document.userInfoBean.p1.value != document.userInfoBean.p2.value)
    {
		alert("�������벻һ�£�����������!! ");
        document.userInfoBean.p1.value="";
        document.userInfoBean.p2.value="";
        document.userInfoBean.p1.focus();
		return false;
    }
//    if(document.userInfoBean.p1.value.length <= 5){
//       alert("���볤�Ȳ���С��6λ!! ");
//        return false;
//    }
//    obj = document.userInfoBean.p1
//    if(pv.test(obj.value)){
//       alert("���벻��Ϊ����,��������ֺ���ĸ��Ϸ�ʽ!!");
//       return false;
//    }

    userInfoBean.submit();
}
</script>
<br>
<template:titile value="�޸��û�����"/>
<html:form  method="post" action="/userinfoAction.do?method=updatePsw">

    <template:formTable>

		<template:formTr name="�޸��û�">
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

		<template:formTr name="��&nbsp;��&nbsp;��">
			<input type="password" class="inputtext" style="width:170px" name="p1" maxlength="16" >
		</template:formTr>
        <template:formTr name="ȷ������">
			<input type="password" class="inputtext" style="width:170px" name="p2" maxlength="16" >
		</template:formTr>

		<template:formSubmit>
		  <td>
		  <input type="button" class="button" style="button" name="Submit" value="ȷ��" onClick="submitForm()">
		  </td>
		  <td>
		  <input type="reset" class="button" style="button" name="Submit" value="ȡ��">
		  </td>
        </template:formSubmit>

   </template:formTable>

</html:form>
