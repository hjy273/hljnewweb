<%@include file="/common/header.jsp"%>
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
<br>
<template:titile value="隐患类型一览表"/>
<display:table name="sessionScope.Troubletype" id="currentRowObject" pagesize="18">
  <display:column property="code" title="类型代码" sortable="true"/>
  <display:column property="typename" title="类型名称" sortable="true"/>
  <apptag:checkpower thirdmould="72208" ishead="0">
    <display:column media="html" title="修改">
    <%
      BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
      String lineid = "";
   
      if (object != null) {
        lineid = (String) object.get("id");
      
      }
    %>
     
          <a href="javascript:toEdit('<%=lineid%>')">修改</a>
       
    </display:column>
  </apptag:checkpower>
  <apptag:checkpower thirdmould="72209" ishead="0">
    <display:column media="html" title="删除">
    <%
      BasicDynaBean object1 = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
      String lineid1 = "";
    
      if (object1 != null) {
        lineid1 = (String) object1.get("id");
      }
    %>
      <a href="javascript:toDelete('<%=lineid1%>')">删除</a>
      
    </display:column>
  </apptag:checkpower>
 
</display:table>
  <apptag:checkpower thirdmould="72207" ishead="0">
<div><a href="javascript:toAdd()">增加</a></div>
 </apptag:checkpower>

</body>
</html>
