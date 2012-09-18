<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<html>
<head>
<script language="javascript" type="">
function toDelete(idValue){
    if(confirm("确定删除该纪录？")){
        var url = "${ctx}/TroubleCodeAction.do?method=delete_TroubleType&id=" + idValue;
        self.location.replace(url);
    }
}

function toEdit(idValue){

        var url = "${ctx}/TroubleCodeAction.do?method=show_edit_TroubleType&id=" + idValue;
        self.location.replace(url);

}
function toAdd(){
        var url = "${ctx}/TroubleCodeAction.do?method=show_Add_TroubleType";
        self.location.replace(url);

}

</script>
<title>showtroublecode</title>
</head>
<body>

<template:titile value="CT系列故障类型一览表"/>
<display:table name="sessionScope.Troubletype" id="currentRowObject" pagesize="18">
  <display:column property="code" title="类型代码" sortable="true"/>
  <display:column property="typename" title="类型名称" sortable="true"/>
  <apptag:checkpower thirdmould="72208" ishead="0">
    <display:column media="html" title="修改">
    <%
      BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
      String lineid = "";
      String regionid = "";
      if (object != null) {
        lineid = (String) object.get("id");
        regionid = ((String) object.get("regionid")).substring(2, 6);
        request.setAttribute("regionid", regionid);
      }
    %>
      <logic:equal value="12" name="LOGIN_USER" property="type">
        <logic:equal value="0000" name="regionid">----</logic:equal>
        <logic:notEqual value="0000" name="regionid">
          <a href="javascript:toEdit('<%=lineid%>')">修改</a>
        </logic:notEqual>
      </logic:equal>
      <logic:equal value="11" name="LOGIN_USER" property="type">
        <logic:equal value="0000" name="regionid">
          <a href="javascript:toEdit('<%=lineid%>')">修改</a>
        </logic:equal>
        <logic:notEqual value="0000" name="regionid">----        </logic:notEqual>
      </logic:equal>
    </display:column>
  </apptag:checkpower>
  <apptag:checkpower thirdmould="72209" ishead="0">
    <display:column media="html" title="删除">
    <%
      BasicDynaBean object1 = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
      String lineid1 = "";
      String regionid1 = "";
      if (object1 != null) {
        lineid1 = (String) object1.get("id");
        regionid1 = ((String) object1.get("regionid")).substring(2, 6);
        request.setAttribute("regionid1", regionid1);
      }
    %>
      <logic:equal value="12" name="LOGIN_USER" property="type">
        <logic:equal value="0000" name="regionid1">----</logic:equal>
        <logic:notEqual value="0000" name="regionid1">
           <a href="javascript:toDelete('<%=lineid1%>')">删除</a>
        </logic:notEqual>
      </logic:equal>
      <logic:equal value="11" name="LOGIN_USER" property="type">
        <logic:equal value="0000" name="regionid1">
           <a href="javascript:toDelete('<%=lineid1%>')">删除</a>
        </logic:equal>
        <logic:notEqual value="0000" name="regionid1">----        </logic:notEqual>
      </logic:equal>
    </display:column>
  </apptag:checkpower>
  <apptag:checkpower thirdmould="72207" ishead="0">
    <display:column media="html" title="增加">
         <a href="javascript:toAdd()">增加</a>
    </display:column>
  </apptag:checkpower>
</display:table>
</body>
</html>
