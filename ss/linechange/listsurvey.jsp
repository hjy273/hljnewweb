<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<style type="text/css">
.subject{text-align:center;}
</style>
<script language="javascript" type="">

function toLook(idValue){
    var url = "${ctx}/changesurveyaction.do?method=getSurveyInfo&id=" + idValue;
    self.location.replace(url);
}
function toEdit(idValue){
	var url="${ctx}/changesurveyaction.do?method=loadEditForm&id="+idValue;
	self.location.replace(url);
}

</script>
<template:titile value="查询勘查审定信息"/>
<body>

<display:table name="sessionScope.queryresult" requestURI="${ctx}/changesurveyaction.do?method=getSurveyInfoList" pagesize="18" id="currentRowObject">
<%
  BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
  String id =null,approveresult="",deptName="",changeName="",changeSimpleName="";

  if (object != null) {
     id = (String) object.get("id");
     approveresult=(String)object.get("approveresult");
     deptName=(String)object.get("approvedept");
     changeName=(String)object.get("changename");
     changeSimpleName=changeName;
     if(changeName!=null&&changeName.length()>10){
     	changeSimpleName=changeName.substring(0,10)+"...";
     }
  }
%>
  <display:column media="html" sortable="true" style="align:center;width:160px" title="工程名称"  >
  	<a href="javascript:toLook('<%=id%>')" title="<%=changeName %>"><%=changeSimpleName %></a>
  </display:column>
  <display:column property="budget" title="工程预算（万元）" style="align:center"/>
  <display:column property="principal" title="勘查负责人" style="align:center" maxLength="5"/>
  <display:column property="surveydate" title="勘查日期" style="align:center" maxLength="10"/>
  <display:column property="state" title="工程状态" style="align:center" maxLength="4"/>
  <display:column property="approveresult" title="审定结果" style="align:center" maxLength="4"/>
  <display:column property="approvedate"  title="审定日期" style="align:center" maxLength="10"/>
  <display:column media="html" title="操 作" style="align:center" headerClass="subject">
    <apptag:checkpower thirdmould="50201">
    	<%if("未通过".equals(approveresult)&&request.getSession().getAttribute("LOGIN_USER_DEPT_NAME").equals(deptName)){ %>
    	<a href="javascript:toEdit('<%=id%>')">修改</a>
    	<%} %>
    </apptag:checkpower>
  </display:column>
</display:table>
<html:link action="/changesurveyaction.do?method=exportSurveyResult">导出为Excel文件</html:link>
</body>
