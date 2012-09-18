<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script language="javascript">
function toDelete(idValue){
    if(confirm("确定删除该纪录？")){
        var url = "${ctx}/regionAction.do?method=deleteRegion&id=" + idValue;
        self.location.replace(url);
    }
}


function toEdit(idValue){
        var url = "${ctx}/regionAction.do?method=loadRegion&id=" + idValue;
        self.location.replace(url);

}

</script>
<template:titile value="查询区域信息结果"/>
<display:table name="sessionScope.queryresult" pagesize="18" id="currentRowObject">

  <display:column property="regionid" title="区域编号"/>
  <display:column property="regionname" title="区域名称"/>
  <display:column property="parentregionid" title="上级区域"/>
  <display:column property="regiondes" title="区域说明"/>

<apptag:checkpower thirdmould="70104" ishead="0">

    <display:column media="html" title="操作">
	  <%
	    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
	    String regionid  = "";
        if(object != null)
        regionid = (String) object.get("regionid");
	  %>
	    <a href="javascript:toEdit('<%=regionid%>')">修改</a>
  </display:column>
</apptag:checkpower>
<apptag:checkpower thirdmould="70105" ishead="0">

    <display:column media="html" title="操作">
	  <%
	    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
	    String regionid = (String) object.get("regionid");
	  %>
	   <a href="javascript:toDelete('<%=regionid%>')">删除</a>
  </display:column>
</apptag:checkpower>
</display:table>
<logic:empty name="queryresult">
</logic:empty>
<logic:notEmpty name="queryresult">
<html:link action="/regionAction.do?method=exportRegionResult">导出为Excel文件</html:link>
</logic:notEmpty>
