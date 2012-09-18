<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="/WebApp/css/screen.css" type="text/css" media="screen, print" />
<html>
	<head>
		<script type="" language="javascript">
        function toGetForm(idValue){
            var url = "/WebApp/ConPersonAction.do?method=showOnePerson&id=" + idValue;
            self.location.replace(url);
        }
        function addGoBack()
        {
            try
			{
                location.href = "/WebApp/ConPersonAction.do?method=showConPerson";
                return true;
            }
            catch(e){
                alert(e);
            }
        }
        function toUpForm(idValue){
            var url =  "/WebApp/ConPersonAction.do?method=showUp&id=" + idValue;
            self.location.replace(url);
        }

        function toDeletForm(idValue){
        	if(confirm("你确定要执行此次删除操作吗?")){
            	var url =  "/WebApp/ConPersonAction.do?method=deleteInfo&id=" + idValue;
            	self.location.replace(url);
        	}
        	else
        		return ;
        }
    </script>

		<title>partBaseInfo</title>
	</head>
	<body>
		<template:titile value="人员信息一览表" />
		<display:table name="sessionScope.QUERYREUSLT" id="currentRowObject"
			pagesize="18">
			<display:column property="name" title="姓名" maxLength="10" />
			<display:column property="sex" title="性别" maxLength="4" />
			<display:column property="jobinfo" title="职务" maxLength="12" />
			<display:column property="mobile" title="手机号码" maxLength="12" />
			<display:column property="contractorname" title="代维单位" maxLength="18" />
			<display:column property="residantarea" title="常驻区域"></display:column>
			<display:column title="在职情况" property="conditions" />
			<display:column property="enter_time" title="入职时间"></display:column>
			<display:column property="leave_time" title="离职时间"></display:column>
			
			<display:column media="html" title="操作">
				<%
                    BasicDynaBean  object = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
                    String id = (String) object.get("id");
                  %>
				<a href="javascript:toGetForm('<%=id%>')">详细</a>|
					<%
                        BasicDynaBean  object1 = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
                        String id1 = (String) object1.get("id");
                      %>
					<a href="javascript:toUpForm('<%=id1%>')">修改</a>
				</display:column>

		</display:table>
		<html:link action="/ConPersonAction.do?method=exportConPerson">导出为Excel文件</html:link>
	</body>
</html>


