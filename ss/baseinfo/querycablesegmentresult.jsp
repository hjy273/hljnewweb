<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script language="javascript" type="">
function toDelete(idValue){
    if(confirm("确定删除该纪录？")){
        var url = "${ctx}/CableSegmentAction.do?method=delCableSegment&kid=" + idValue;
        self.location.replace(url);
    }
//confirm("不能删除该纪录!");
}

function toEdit(idValue,type){
        var url = "${ctx}/CableSegmentAction.do?method=loadEditForm&kid=" + idValue +"&t="+type;
        self.location.replace(url);

}


</script>
<template:titile value="光缆信息一览表"/>
<display:table name="sessionScope.cablelist" requestURI="${ctx}/CableSegmentAction.do?method=queryCableSegment" style="width:100%" id="currentRowObject"  pagesize="16">
  <display:column property="segmentid" style="width:6%"title="光缆段号"/>
  <display:column property="segmentname" maxLength="10" style="width:20%" title="光缆名"/>
  <display:column property="segmentdesc" maxLength="15" style="width:25%" title="光缆段"/>
  <display:column property="pointa" maxLength="4" style="width:10%" title="A点"/>
  <display:column property="pointz" maxLength="4" style="width:10%"title="Z点"/>

  <display:column media="html" style="width:5%" title="查看">
             <%
                BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
                String kid = (String) object.get("kid");
            %>
            <a href="javascript:toEdit('<%=kid%>','r')">查看</a>
  </display:column>
  <apptag:checkpower thirdmould="72104" ishead="0">
        <display:column media="html" style="width:5%" title="修改">
             <%
                BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
                String kid = (String) object.get("kid");
            %>
            <a href="javascript:toEdit('<%=kid%>','e')">修改</a>
        </display:column>
  </apptag:checkpower>
  <apptag:checkpower thirdmould="72105" ishead="0">

        <display:column media="html" style="width:5%" title="删除">
         <%
            BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
            String kid = (String) object.get("kid");
        %>
        <a href="javascript:toDelete('<%=kid%>')">删除</a>
        </display:column>
  </apptag:checkpower>
</display:table>
<logic:empty name="cablelist">
</logic:empty>
<logic:notEmpty name="cablelist">
<html:link action="/CableSegmentAction.do?method=exportCableSegment">导出为Excel文件</html:link>
</logic:notEmpty>
