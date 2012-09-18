<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script language="javascript" type="">
function toDelete(idValue){
    if(confirm("确定删除该纪录？")){
       var url = "${ctx}/CableAction.do?method=deleteCable&id=" + idValue;
        self.location.replace(url);
    }
     //confirm("不能删除该纪录!");

}


function toEdit(idValue,type){
        var url = "${ctx}/CableAction.do?method=loadCable&id=" + idValue+"&t="+type;
        self.location.replace(url);

}

</script>
<template:titile value="查询光缆段结果"/>
<display:table name="sessionScope.cableList" requestURI="${ctx}/CableAction.do?method=queryCable" id="currentRowObject" pagesize="18">
  <display:column property="cableno" style="width:10%" title="光缆段编号"/>
  <display:column property="contractorname" style="width:10%" title="代维单位"/>
  <display:column property="cablename" style="width:15%" title="光缆名称"/>
<display:column property="cablelinename" style="width:15%" title="光缆段名称"/>
<display:column property="fibertype" style="width:15%" title="纤芯型号"/>
<display:column property="fibernumber" style="width:15%" title="纤芯数量"/>
 
<display:column media="html" style="width:5%" title="查看">
  <%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
    String id = "";
    
    if(object != null)
     id = (String) object.get("id");
  %>
    <a href="javascript:toEdit('<%=id%>','r')">查看</a>
  </display:column>
  <apptag:checkpower thirdmould="70204" ishead="0">
  <display:column media="html" style="width:5%" title="修改">
   <%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
    String id = "";
    
    if(object != null)
     id = (String) object.get("id");
  %>
    <a href="javascript:toEdit('<%=id%>','e')">修改</a>
  </display:column>
  </apptag:checkpower>
  <apptag:checkpower thirdmould="70205" ishead="0">

  <display:column media="html" style="width:5%" title="删除">
    <%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
    String id = "";
    
    if(object != null)
     id = (String) object.get("id");
  %>
    <a href="javascript:toDelete('<%=id%>')">删除</a>
  </display:column>
  </apptag:checkpower>
</display:table>
<logic:empty name="cableList">
</logic:empty>
<logic:notEmpty name="cableList">
<html:link action="/CableAction.do?method=exportCableResult">导出为Excel文件</html:link>
</logic:notEmpty>