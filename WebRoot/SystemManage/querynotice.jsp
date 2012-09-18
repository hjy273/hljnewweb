<%@ page language="java" contentType="text/html; charset=GBK"%><%@ include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script type="text/javascript">
<!--
function toDelete(idValue){

    if(confirm("确定删除该纪录？")){
        var url = "${ctx}/NoticeAction.do?method=delNotice&id=" + idValue;
        self.location.replace(url);
   }
}

function toEdit(idValue){
        var url = "${ctx}/NoticeAction.do?method=editForm&id=" + idValue;
        self.location.replace(url);

}
//-->
</script>
<template:titile value="公告信息"/>

<display:table name="sessionScope.noticelist"  id="currentRowObject"pagesize="15">
	<display:column property="title" title="标题"  maxLength="10"/>
	<display:column property="content"  title="内容摘要"  maxLength="15"/>
	<display:column property="issueperson" title="发布人"  maxLength="10" />
	<display:column property="isissue" title="是否发布"  />
	<display:column property="issuedate" title="发布日期"  maxLength="10"></display:column>
	<display:column media="html" title="操作">
	<%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
    String id = (String) object.get("id");
    String isissue = (String) object.get("isissue");
    if(isissue.indexOf("未")!= -1){
  	%>
		<a href="javascript:toEdit('<%=id%>')">修改</a>
	<%}else{ %>
	 ---
	 <%} %>
    </display:column>
	<display:column media="html" title="操作">
	<%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
    String id = (String) object.get("id");
  	%>
		<a href="javascript:toDelete('<%=id%>')">删除</a>

    </display:column>
</display:table>

