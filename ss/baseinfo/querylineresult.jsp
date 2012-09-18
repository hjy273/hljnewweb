<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script language="javascript">
function toDelete(idValue){
    if(confirm("确定删除该纪录？")){
        var url = "${ctx}/lineAction.do?method=delLine&id=" + idValue;
        self.location.replace(url);
    }
}

function toEdit(idValue){
        var url = "${ctx}/lineAction.do?method=loadLine&id=" + idValue;
        self.location.replace(url);

}

</script>
<br>
<template:titile value="查询巡检线路信息结果"/>
<display:table name="sessionScope.queryresult" requestURI="${ctx}/lineAction.do?method=queryLine" id="currentRowObject"  pagesize="18">

  <display:column property="linename" title="线路名称" sortable="true"/>
  <display:column property="linetype" title="线路级别" sortable="true"/>
  <display:column property="ruledeptid" title="所属部门" sortable="true"/>
  <display:column property="regionid" title="所属区域" sortable="true"/>

<apptag:checkpower thirdmould="70904" ishead="0">

  <display:column media="html" title="修改"><%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
    String lineid = "";
    if(object != null)
     lineid = (String) object.get("lineid");
  %>
    <a href="javascript:toEdit('<%=lineid%>')">修改</a>
  </display:column>
  </apptag:checkpower>
  <logic:equal value="12" name="LOGIN_USER" property="type">

  <display:column media="html" title="删除">
  <%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
    String lineid = (String) object.get("lineid");
    //<display:column property="lineid" title="线路编号"  sortable="true"/>  由上面的column移至而来，可删除
  %>
    <a href="javascript:toDelete('<%=lineid%>')">删除</a>
  </display:column>
  </logic:equal>

</display:table>
<logic:empty name="queryresult">
</logic:empty>
<logic:notEmpty name="queryresult">
<html:link action="/lineAction.do?method=exportLineResult">导出为Excel文件</html:link>
</logic:notEmpty>