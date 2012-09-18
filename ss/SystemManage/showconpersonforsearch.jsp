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
        	if(confirm("��ȷ��Ҫִ�д˴�ɾ��������?")){
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
		<template:titile value="��Ա��Ϣһ����" />
		<display:table name="sessionScope.QUERYREUSLT" id="currentRowObject"
			pagesize="18">
			<display:column property="name" title="����" maxLength="10" />
			<display:column property="sex" title="�Ա�" maxLength="4" />
			<display:column property="jobinfo" title="ְ��" maxLength="12" />
			<display:column property="mobile" title="�ֻ�����" maxLength="12" />
			<display:column property="contractorname" title="��ά��λ" maxLength="18" />
			<display:column property="residantarea" title="��פ����"></display:column>
			<display:column title="��ְ���" property="conditions" />
			<display:column property="enter_time" title="��ְʱ��"></display:column>
			<display:column property="leave_time" title="��ְʱ��"></display:column>
			
			<display:column media="html" title="����">
				<%
                    BasicDynaBean  object = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
                    String id = (String) object.get("id");
                  %>
				<a href="javascript:toGetForm('<%=id%>')">��ϸ</a>|
					<%
                        BasicDynaBean  object1 = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
                        String id1 = (String) object1.get("id");
                      %>
					<a href="javascript:toUpForm('<%=id1%>')">�޸�</a>
				</display:column>

		</display:table>
		<html:link action="/ConPersonAction.do?method=exportConPerson">����ΪExcel�ļ�</html:link>
	</body>
</html>


