<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script language="javascript" type="">

function toEdit(idValue){
    var url = "${ctx}/ReportAction.do?method=getReport&id=" + idValue+"&t=edit";
    self.location.replace(url);
}
function toAuditing(idValue){
	var url = "${ctx}/ReportAction.do?method=getReport&id=" + idValue+"&t=auditing";
    self.location.replace(url);
}
function toDelete(idValue){
    var url = "${ctx}/ReportAction.do?method=delReport&id=" + idValue;
    self.location.replace(url);
}
</script>
<%
	String con = (String)session.getAttribute("con");
	
 %>
<template:titile value="查询结果信息"/>
<body>

<display:table name="sessionScope.queryresult" requestURI="${ctx}/ReportAction.do?method=queryReport&con=noauditing" pagesize="10" id="currentRowObject">
<%
  BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
  String id =null;
  String reporturl = null;
  String auditingr = null;

  if (object != null) {
     id = (String) object.get("id");
     reporturl = (String)object.get("reporturl");
     auditingr = (String)object.get("auditingresult");
  }
%>
  <display:column property="reportname" style="align:center" title="文档名称" maxLength="10"/>
  <display:column property="typename" title="文档类型" style="align:center" maxLength="10"/>
  <display:column property="remark" title="文档描述" style="align:center" maxLength="10"/>
  
  <display:column media="html" title="文档" style="align:center">
  	<%
  		
  		if(reporturl == null || reporturl.equals("")){
  			out.print("");
  		}else{
  	%>
        <apptag:listAttachmentLink fileIdList="<%=reporturl%>" />
    <%} %>
  </display:column>
  <display:column media="html" title="状态" style="align:center">
  	<%
  		if(auditingr == null || auditingr.equals("")){
  			out.print("未审核");
  		}else{
  			out.print(auditingr);
  		}
  	 %>
  </display:column>
  <apptag:checkpower thirdmould="140103" ishead="0">
  <display:column media="html" title="操作" style="align:center">
  	<%if(auditingr !=null && auditingr.equals("通过")){ 
  	 out.print("");
  	 }else{ %>
  	 	<a href="javascript:toEdit('<%=id%>')">修改</a>
  	 <%	}
  	 %>
  </display:column>
  </apptag:checkpower>
  
  <apptag:checkpower thirdmould="140104" ishead="0">
  <display:column media="html" title="操作" style="align:center">
  <%if(auditingr !=null && auditingr.equals("通过")){ 
  	 out.print("");
  	 }else{ %>
  	<a href="javascript:toDelete('<%=id%>')">删除</a>
  	<%	}
  	 %>
  </displ
  </display:column>
  </apptag:checkpower>
  <%if(con == null || !con.equals("auditing")){ %>
  <apptag:checkpower thirdmould="140201" ishead="0">
  <display:column media="html" title="操作" style="align:center">
  <%
  	if(auditingr !=null && auditingr.equals("通过")){
  		out.print("");
  	}else{
   %>
  	<a href="javascript:toAuditing('<%=id%>')">审核</a>
  	
  <%} %>
  </display:column>
  </apptag:checkpower>
  <%} %>
</display:table>

</body>
