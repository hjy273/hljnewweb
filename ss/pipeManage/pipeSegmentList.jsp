<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script language="javascript" type="">
function toDelete(idValue){
    if(confirm("确定删除该纪录？")){
       var url = "${ctx}/PipeSegmentAction.do?method=deletePipeSegment&id=" + idValue;
        self.location.replace(url);
    }
     //confirm("不能删除该纪录!");

}


function toEdit(idValue){
        var url = "${ctx}/PipeSegmentAction.do?method=editPipeSegmentForm&id=" + idValue;
        self.location.replace(url);

}

</script>
<template:titile value="查询管道段信息结果"/>
<display:table name="sessionScope.pipeSegmentList" requestURI="${ctx}/PipeSegmentAction.do?method=queryPipeSegment&isReturn=false" id="currentRowObject" pagesize="18">

 c.id,c.pipeno,ct.contractorname,c.pipename,c.county,c.area,c.pipehole,c.pipetype,c.
  <display:column property="pipeno" style="width:10%" title="管道段编号"/>
  <display:column property="pipename" style="width:15%" title="管道段名称"/>
  <display:column property="contractorname" style="width:10%" title="代维单位"/>
 <display:column property="county" style="width:10%" title="所属乡镇"/>

<display:column property="pipehole" style="width:10%" title="管孔数"/>
<display:column property="pipetype" style="width:10%" title="管孔规格"/>
<display:column property="subpipehole" style="width:10%" title="子管总数"/>
<display:column property="unuserpipe" style="width:10%" title="未用子管数"/>

  <apptag:checkpower thirdmould="70204" ishead="0">

  <display:column media="html" style="width:10%" title="修改">
   <%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
    String id = "";
    
    if(object != null)
     id = (String) object.get("id");
  %>
    <a href="javascript:toEdit('<%=id%>')">修改</a>
  </display:column>
  </apptag:checkpower>
  <apptag:checkpower thirdmould="70205" ishead="0">

  <display:column media="html" style="width:10%" title="删除">
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
<logic:empty name="pipeSegmentList">
</logic:empty>
<logic:notEmpty name="pipeSegmentList">
<html:link action="/PipeSegmentAction.do?method=exportPipeSegmentResult">导出为Excel文件</html:link>
</logic:notEmpty>
