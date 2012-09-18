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
    	alert("不能删除自己!!");
        return ;
    }
    if(confirm("确定删除该纪录？")){
        var url = "${ctx}/userinfoAction.do?method=deleteUserinfo&id=" + idValue;
        self.location.replace(url);
   }
}

function toEdit(idValue){
        var url = "${ctx}/userinfoAction.do?method=loadUserinfo&id=" + idValue;
        self.location.replace(url);

}

</script>
<template:titile value="查询用户信息结果"/>
<display:table name="sessionScope.queryresult" requestURI="${ctx}/userinfoAction.do?method=queryUserinfo" id="currentRowObject" pagesize="18" >


  <display:column property="userid" title="用户ID" style="width:10%" maxLength="15"/>
  <display:column property="username" style="width:30%" title="用户名" maxLength="20"/>
  <display:column property="deptid" style="width:30%" title="部门/单位" maxLength="20"/>
  <display:column property="regionid" style="width:15%" title="所属区域" maxLength="5"/>

  <apptag:checkpower thirdmould="70504" ishead="0">
	  <display:column media="html" title="操作">
	  <%
	     BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
	     String userid = (String) object.get("userid");

         String usertype=(String)object.get("userstype");
         String userregion=(String)object.get("userregion");
         if(curusertype.equals("11")&&!userregion.substring(2).equals("0000")&&usertype.equals("2")){
      %>
        <a href="javascript:toEdit('<%=userid%>')">修改</a>
      <%
        }
        else{
      %>
		    <a href="javascript:toEdit('<%=userid%>')">修改</a>
      <%
        }
      %>
	  </display:column>
  </apptag:checkpower>
  <apptag:checkpower thirdmould="70505" ishead="0">
	   <display:column media="html" title="操作">
	   <%
		    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
		    String userid = (String) object.get("userid");

            String usertype=(String)object.get("userstype");
            String userregion=(String)object.get("userregion");
            if(curusertype.equals("11")&&!userregion.substring(2).equals("0000")&&usertype.equals("2")){
      %>
        <a href="javascript:toDelete('<%=userid%>')">删除</a>
      <%
        }
        else{
      %>
		    <a href="javascript:toDelete('<%=userid%>')">删除</a>
      <%
        }
      %>
	  </display:column>
  </apptag:checkpower>
</display:table>
<logic:empty name="queryresult">
</logic:empty>
<logic:notEmpty name="queryresult">
<html:link action="/userinfoAction.do?method=exportUserinfoResult">导出为Excel文件</html:link>
</logic:notEmpty>
