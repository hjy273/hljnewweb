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
        	//if(confirm("你确定要执行此次删除操作吗?")){
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
			//		document.getElementById("leaveWin").innerHTML="<center><font style='font-size:12px;'>载入中......</font></center>";
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

		<template:titile value="代维公司人员通讯录" />
		<display:table name="sessionScope.QUERYREUSLT" id="currentRowObject"
			pagesize="18">
			<display:column property="name" title="姓名" maxLength="10" />
			<display:column property="mobile" title="手机号" maxLength="12" />
			<display:column property="contractorname" title="代维单位" maxLength="18" />
			<display:column property="jobinfo" title="职务" maxLength="18" />
			<display:column property="employee_num" title="员工编号" maxLength="10" />
		</display:table>
	</body>
</html>


