<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<jsp:useBean id="selectForTag"
	class="com.cabletech.commons.tags.SelectForTag" scope="request" />
<html>
	<head>
		<script type="" language="javascript">
        function toGetForm(idValue){
            var url = "${ctx}/ConPersonAction.do?method=showOnePerson&id=" + idValue;
            self.location.replace(url);
        }
        function addGoBack()
        {
            try
			{
                location.href = "${ctx}/ConPersonAction.do?method=showConPerson";
                return true;
            }
            catch(e){
                alert(e);
            }
        }
        function toUpForm(idValue){
            var url =  "${ctx}/ConPersonAction.do?method=showUp&id=" + idValue;
            self.location.replace(url);
        }
        function toDeletForm(idValue){
        	//if(confirm("��ȷ��Ҫִ�д˴�ɾ��������?")){
            //self.location.replace(url);
        	//}
        	//else
        	//	return ;
			//new Dialog({type:'url',value:url}).show();
            var url =  "${ctx}/ConPersonAction.do?method=leaveConPersonForm&id=" + idValue+"&rnd="+Math.random();
			self.location.replace(url);
			//var myAjax=new Ajax.Updater(
			//	"leaveWin",url,{
			//	onLoading:function(){
			//		document.getElementById("leaveWin").innerHTML="<center><font style='font-size:12px;'>������......</font></center>";
			//	},
			//	method:"post",
			//	evalScripts:true
			//	}
			//);
        }
    </script>

		<title>partBaseInfo</title>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
	</head>
	<body>

		<template:titile value="��Ա��Ϣһ����" />
		<display:table name="sessionScope.QUERYREUSLT" id="currentRowObject"
			pagesize="18">
			<display:column property="employee_num" title="Ա�����" maxLength="10" />
			<display:column property="name" title="����" maxLength="10" />
			<display:column property="contractorname" title="��ά��λ" maxLength="18" />
			<display:column property="jobinfo" title="ְ��" maxLength="18" />
			<display:column property="enter_time" title="��ְʱ��" />
			<display:column property="mobile" title="�ֻ���" maxLength="12" />
			<display:column property="culture" title="�Ļ��̶�" maxLength="10" />
			<display:column media="html" title="����">
				<%
				    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
				    String id = (String) object.get("id");
				%>
				<a href="javascript:toGetForm('<%=id%>')">��ϸ</a>
			</display:column>
			<apptag:checkpower thirdmould="72004" ishead="0">
				<display:column media="html" title="����">
					<%
					    BasicDynaBean object1 = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
					    String id1 = (String) object1.get("id");
					    if("�ڸ�".equals(object1.get("conditions"))){
					%>
					<a href="javascript:toUpForm('<%=id1%>')">�޸�</a>
					<%
						}
					 %>
				</display:column>
			</apptag:checkpower>
			<apptag:checkpower thirdmould="72005" ishead="0">
				<display:column media="html" title="����">
					<%
					    BasicDynaBean object2 = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
					    String id2 = (String) object2.get("id");
					    if("�ڸ�".equals(object2.get("conditions"))){
					%>
					<a href="javascript:toDeletForm('<%=id2%>')">��ְ</a>
					<%
						}
					 %>
				</display:column>
			</apptag:checkpower>
		</display:table>
		<html:link action="/ConPersonAction.do?method=exportConPerson">����ΪExcel�ļ�</html:link>
		<div id="win">
			<div id="leaveWin"></div>
        </div>
	</body>
</html>


