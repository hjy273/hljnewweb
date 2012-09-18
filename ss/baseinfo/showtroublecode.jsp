<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<html>
<head>
<script language="javascript" type="">
function toDelete(idValue){
    if(confirm("È·¶¨É¾³ý¸Ã¼ÍÂ¼£¿")){
        var url = "${ctx}/TroubleCodeAction.do?method=delete_TroubleCode&id=" + idValue;
        self.location.replace(url);
    }
}

function toEdit(idValue){
        var url = "${ctx}/TroubleCodeAction.do?method=show_edit_TroubleCode&id=" + idValue;
        self.location.replace(url);

}

</script>
<title>showtroublecode</title>
</head>
<body>
<br>
<template:titile value="Òþ»¼ÂëÒ»ÀÀ±í"/>
<display:table name="sessionScope.Troublecode" requestURI="${ctx}/TroubleCodeAction.do?method=load_TroubleCode" id="currentRowObject" pagesize="18">
  <display:column property="troublecode" title="Òþ»¼´úÂë" sortable="true"/>
  <display:column property="typename" title="Òþ»¼ÀàÐÍ" sortable="true"/>
  <display:column property="emergencylevel" title="Òþ»¼¼¶±ð" sortable="true"/>
  <display:column property="troublename" title="Òþ»¼Ãû³Æ" sortable="true"/>
  <apptag:checkpower thirdmould="72204" ishead="0">
    <display:column media="html" title="ÐÞ¸Ä">
    <%
      BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
      String lineid = "";
      String regionid = "";
      if (object != null) {
        lineid = (String) object.get("id");
      }
    %>
          <a href="javascript:toEdit('<%=lineid%>')">ÐÞ¸Ä</a>
    </display:column>
  </apptag:checkpower>
  <apptag:checkpower thirdmould="72205" ishead="0">
    <display:column media="html" title="É¾³ý">
    <%
      BasicDynaBean object1 = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
      String lineid1 = "";
      String regionid1 = "";
      if (object1 != null) {
        lineid1 = (String) object1.get("id");
      }
    %>
           <a href="javascript:toDelete('<%=lineid1%>')">É¾³ý</a>

    </display:column>
  </apptag:checkpower>
</display:table>
</body>
</html>
