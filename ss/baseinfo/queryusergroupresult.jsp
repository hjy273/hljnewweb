<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.baseinfo.domainobjects.*"%>
<jsp:useBean id="userInfoBean" class="com.cabletech.baseinfo.beans.UserInfoBean" scope="request"/>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script language="javascript" type="">
function toDelete(idValue,usernum){

    if(confirm("���û�������"+usernum+" ���û�,ȷ��ɾ���ü�¼��")){
        var url = "${ctx}/UsergroupAction.do?method=deleteUsergroup&id=" + idValue;
        self.location.replace(url);
    }

}

function toEdit(idValue){
        var url = "${ctx}/UsergroupAction.do?method=loadUsergroup&id=" + idValue;
        self.location.replace(url);

}


</script>
<template:titile value="��ѯ�û�����"/>
<%
	UserInfo userInfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
    String usertype = userInfo.getType();
%>
<display:table name="sessionScope.queryresult" requestURI="${ctx}/UsergroupAction.do?method=getUserGroups" id="currentRowObject" pagesize="18">

  <display:column property="groupname" title="�û�������" maxLength="20"/>
  <display:column property="region" title="��������"/>
 <apptag:checkpower thirdmould="70404" ishead="0">
      <display:column media="html" title="����">
      <%
        BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
        String id = (String) object.get("id");

        String type=(String) object.get("type");
        String regionId=(String) object.get("regionid");
        //if(usertype.equals("11")&&!regionId.substring(2).equals("0000")&&type.equals("2")){
      %><%
        //}
        //else{
      %>
        <a href="javascript:toEdit('<%=id%>')">�޸�</a>
      <%
        //}
      %>
      </display:column>
  </apptag:checkpower>
  <apptag:checkpower thirdmould="70405" ishead="0">
      <display:column media="html" title="����">
      <%
        BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
        String id = (String) object.get("id");

        String type=(String) object.get("type");
        String regionId=(String) object.get("regionid");
        String usernum = (String)object.get("usernum");
        //if(usertype.equals("11")&&!regionId.substring(2).equals("0000")&&type.equals("2")){
      %><%
        //}
        //else{
      %>
        <a href="javascript:toDelete('<%=id%>','<%=usernum%>')">ɾ��</a>
      <%
        //}
      %>
      </display:column>
  </apptag:checkpower>

</display:table>
<logic:empty name="queryresult">
</logic:empty>
<logic:notEmpty name="queryresult">
<html:link action="/UsergroupAction.do?method=exportUsergroupResult">����ΪExcel�ļ�</html:link>
</logic:notEmpty>
