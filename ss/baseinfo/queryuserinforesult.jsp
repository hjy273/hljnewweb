<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.baseinfo.domainobjects.UserInfo" %>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<%
	UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
    String curusertype=userinfo.getType();
%>
<script language="javascript" type="">
function toDelete(idValue){

    if(idValue =="<%=userinfo.getUserID()%>"){
    	alert("����ɾ���Լ�!!");
        return ;
    }
    if(confirm("ȷ��ɾ���ü�¼��")){
        var url = "${ctx}/userinfoAction.do?method=deleteUserinfo&id=" + idValue;
        self.location.replace(url);
   }
}

function toEdit(idValue){
        var url = "${ctx}/userinfoAction.do?method=loadUserinfo&id=" + idValue;
        self.location.replace(url);

}

</script>
<template:titile value="��ѯ�û���Ϣ���"/>
<display:table name="sessionScope.queryresult" requestURI="${ctx}/userinfoAction.do?method=queryUserinfo" id="currentRowObject" pagesize="18" >


  <display:column property="userid" title="�û�ID" style="width:10%" maxLength="15"/>
  <display:column property="username" style="width:30%" title="�û���" maxLength="20"/>
  <display:column property="deptid" style="width:30%" title="����/��λ" maxLength="20"/>
  <display:column property="regionid" style="width:15%" title="��������" maxLength="5"/>

  <apptag:checkpower thirdmould="70504" ishead="0">
	  <display:column media="html" title="����">
	  <%
	     BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
	     String userid = (String) object.get("userid");

         String usertype=(String)object.get("userstype");
         String userregion=(String)object.get("userregion");
         if(curusertype.equals("11")&&!userregion.substring(2).equals("0000")&&usertype.equals("2")){
      %>
        <a href="javascript:toEdit('<%=userid%>')">�޸�</a>
      <%
        }
        else{
      %>
		    <a href="javascript:toEdit('<%=userid%>')">�޸�</a>
      <%
        }
      %>
	  </display:column>
  </apptag:checkpower>
  <apptag:checkpower thirdmould="70505" ishead="0">
	   <display:column media="html" title="����">
	   <%
		    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
		    String userid = (String) object.get("userid");

            String usertype=(String)object.get("userstype");
            String userregion=(String)object.get("userregion");
            if(curusertype.equals("11")&&!userregion.substring(2).equals("0000")&&usertype.equals("2")){
      %>
        <a href="javascript:toDelete('<%=userid%>')">ɾ��</a>
      <%
        }
        else{
      %>
		    <a href="javascript:toDelete('<%=userid%>')">ɾ��</a>
      <%
        }
      %>
	  </display:column>
  </apptag:checkpower>
</display:table>
<logic:empty name="queryresult">
</logic:empty>
<logic:notEmpty name="queryresult">
<html:link action="/userinfoAction.do?method=exportUserinfoResult">����ΪExcel�ļ�</html:link>
</logic:notEmpty>
