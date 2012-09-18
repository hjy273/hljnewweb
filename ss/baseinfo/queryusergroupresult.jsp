<%@include file="/common/header.jsp"%>
<%@page import="com.cabletech.baseinfo.domainobjects.*"%>
<jsp:useBean id="userInfoBean" class="com.cabletech.baseinfo.beans.UserInfoBean" scope="request"/>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script language="javascript" type="">
function toDelete(idValue,usernum){

    if(confirm("该用户组下有"+usernum+" 个用户,确定删除该纪录？")){
        var url = "${ctx}/UsergroupAction.do?method=deleteUsergroup&id=" + idValue;
        self.location.replace(url);
    }

}

function toEdit(idValue){
        var url = "${ctx}/UsergroupAction.do?method=loadUsergroup&id=" + idValue;
        self.location.replace(url);

}


</script>
<template:titile value="查询用户组结果"/>
<%
	UserInfo userInfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
    String usertype = userInfo.getType();
%>
<display:table name="sessionScope.queryresult" requestURI="${ctx}/UsergroupAction.do?method=getUserGroups" id="currentRowObject" pagesize="18">

  <display:column property="groupname" title="用户组名称" maxLength="20"/>
  <display:column property="region" title="所属区域"/>
 <apptag:checkpower thirdmould="70404" ishead="0">
      <display:column media="html" title="操作">
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
        <a href="javascript:toEdit('<%=id%>')">修改</a>
      <%
        //}
      %>
      </display:column>
  </apptag:checkpower>
  <apptag:checkpower thirdmould="70405" ishead="0">
      <display:column media="html" title="操作">
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
        <a href="javascript:toDelete('<%=id%>','<%=usernum%>')">删除</a>
      <%
        //}
      %>
      </display:column>
  </apptag:checkpower>

</display:table>
<logic:empty name="queryresult">
</logic:empty>
<logic:notEmpty name="queryresult">
<html:link action="/UsergroupAction.do?method=exportUsergroupResult">导出为Excel文件</html:link>
</logic:notEmpty>
