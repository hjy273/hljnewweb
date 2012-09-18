<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
<jsp:useBean id="userInfoBean" class="com.cabletech.sysmanage.beans.UserInfoBean" scope="request"/>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script language="javascript" type="">
function toDelete(idValue){

    if(confirm("确定删除该纪录？")){
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
<display:table name="sessionScope.queryresult" id="currentRowObject" pagesize="18">

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
        //if(usertype.equals("11")&&!regionId.substring(2).equals("0000")&&type.equals("2")){
      %><%
        //}
        //else{
      %>
        <a href="javascript:toDelete('<%=id%>')">删除</a>
      <%
        //}
      %>
      </display:column>
  </apptag:checkpower>

</display:table>
<html:link action="/UsergroupAction.do?method=exportUsergroupResult">导出为Excel文件</html:link>
