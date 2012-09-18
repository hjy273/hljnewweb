<%@include file="/common/header.jsp"%>
<!--%@include file="/common/listhander.jsp"%-->
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script language="javascript">
function toDelete(idValue){
    if(confirm("确定删除该纪录？")){
       var url = "${ctx}/materialAddressAction.do?method=deletePartAddress&id=" + idValue;
        self.location.replace(url);
    }
     //confirm("不能删除该纪录!");

}


function toEdit(idValue){
        var url = "${ctx}/materialAddressAction.do?method=loadAddress&id=" + idValue;
        self.location.replace(url);

}
</script>
<template:titile value="查询材料存放信息结果"/>
<display:table name="sessionScope.queryresult" requestURI="${ctx}/materialAddressAction.do?method=queryAddress" id="currentRowObject" pagesize="18">
  <display:column property="address" style="width:30%" title="材料存放地点名称"/>
  <display:column property="contractorid" style="width:30%" title="代维单位"/>
  <display:column property="remark" style="width:30%" title="备注"/>
   <apptag:checkpower thirdmould="70204" ishead="0">
<display:column media="html" style="width:10%" title="操作">
   <%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
    Object id=null;
    if(object != null)
     id =  object.get("id");
  %>
    <a href="javascript:toEdit('<%=id%>')">修改</a>
     <apptag:checkpower thirdmould="70205" ishead="0">
    <a href="javascript:toDelete('<%=id%>')">删除</a>
    </apptag:checkpower>
  </display:column>
   </apptag:checkpower>
</display:table>
<logic:empty name="queryresult">
</logic:empty>
<logic:notEmpty name="queryresult">
<html:link action="/materialAddressAction.do?method=exportMaterialAddressResult">导出为Excel文件</html:link>
</logic:notEmpty>

