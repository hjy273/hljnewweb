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

		<template:titile value="��ά��˾��ԱͨѶ¼" />
		<display:table name="sessionScope.QUERYREUSLT" id="currentRowObject"
			pagesize="18">
			<display:column property="name" title="����" maxLength="10" />
			<display:column property="mobile" title="�ֻ���" maxLength="12" />
			<display:column property="contractorname" title="��ά��λ" maxLength="18" />
			<display:column property="jobinfo" title="ְ��" maxLength="18" />
			<display:column property="employee_num" title="Ա�����" maxLength="10" />
		</display:table>
	</body>
</html>


