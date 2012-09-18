<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script language="javascript" type="">
    function toGetForm(id){
    	var url = "${ctx}/getaction.do?method=showOne&id=" + id;
        self.location.replace(url);
    }
</script>
<template:titile value="查询修缮信息"/>
<body>

<display:table name="sessionScope.queryresult" requestURI="${ctx}/stataction.do?method=getStatInfo" pagesize="18" id="currentRowObject">
  <%
  BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
  String id =null,changeName="",changeSimpleName="";
  if (object != null) {
     id = (String) object.get("id");
     changeName=(String)object.get("changename");
     changeSimpleName=changeName;
     if(changeName!=null&&changeName.length()>10){
     	changeSimpleName=changeName.substring(0,10)+"...";
     }
  }
%>
  <display:column media="html" sortable="true" style="align:center" title="工程名称" style="width:160px">
  	<a href="javascript:toGetForm('<%=id%>')" title="<%=changeName %>"><%=changeSimpleName %></a>
  </display:column>
  <display:column property="lineclass" title="网络性质" style="align:center;width:80px" maxLength="4" />
  <display:column property="changepro" title="整修性质" style="align:center;width:80px" maxLength="4"  />
  <display:column property="startdate" title="修缮日期" style="align:center;width:80px" maxLength="15"  />
  <display:column property="budget" title="预算（万）" style="align:center;width:70px" maxLength="10" />
  <display:column property="square" title="结算（万）" style="align:center;width:70px" maxLength="10"  />
  <display:column property="squared" title="已付款（万）" style="align:center;width:80px" maxLength="10" />
  <display:column property="sqdate" title="付款日期" style="align:center;width:70px" maxLength="10" />
</display:table>
<html:link action="/stataction.do?method=exportChangeStat">导出为Excel文件</html:link>
</body>
