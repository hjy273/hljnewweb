<%@include file="/common/header.jsp"%>
<!--%@include file="/common/listhander.jsp"%-->
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<script type="text/javascript" language="javascript">
		
		 toGetForm=function(idValue){
            	window.location.href = "${ctx}/materialTypeAction.do?method=getTypesByItemID&id="+idValue;
		}
		
		 toViewForm=function(idValue){
            	window.location.href = "${ctx}/materialTypeAction.do?method=viewTypeByID&id="+idValue;
		}
		
        toDeletForm=function(idValue){
       	 	if(confirm("��ȷ��Ҫִ�д˴�ɾ��������?")){
            	window.location.href = "${ctx}/materialTypeAction.do?method=deleteMeterialType&id="+idValue;
            }           
		}
		toEditForm=function(idValue){
		 	window.location.href = "${ctx}/mtUsedAssessAction.do?method=goMtUsedAppoverForm&mtUsedId="+idValue;
		}       
		
		exportList=function(){
			var url="${ctx}/materialTypeAction.do?method=exportTypeList";
			self.location.replace(url);
		}
		</script>
		<title>��������</title>
		<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
			media="screen, print" />
		<style type="text/css">
		.subject{text-align:center;}
		</style>
	</head>
	<body>
		<br />
		<%
		BasicDynaBean object=null;
		Object id=null;		
		 %>
		<template:titile value="���ƶ���ˣ������̵�����һ����" />
		<display:table name="sessionScope.approveList" id="currentRowObject" pagesize="18">
			<display:column property="createtime" title="����ʱ��" headerClass="subject"  sortable="true"/>	
      		<display:column property="contractorname" sortable="true" title="��ά��˾" headerClass="subject" maxLength="10"/>
			<display:column property="username" sortable="true" title="������" headerClass="subject" maxLength="10"/>
			<display:column property="remark" sortable="true" title="��ע" headerClass="subject" maxLength="10"/>
			<display:column media="html" sortable="true" title="״̬" headerClass="subject" maxLength="10">
			<% 
				object = (BasicDynaBean ) pageContext.findAttribute("currentRowObject");
				 if(object != null) {
				String state = (String)object.get("state");
				if("2".equals(state)){
					out.print("���ͨ��");
				}else if("1".equals(state)){
					out.print("��˲�ͨ��");
				}else{
					out.print("�����");
				}
				}
			%>
			</display:column>
			 <display:column media="html" title="����" >
				<% 
	            if(object != null) {
	      	 		id = object.get("id");
				} %>
	            		<a href="javascript:toEditForm('<%=id%>')">���</a>
            </display:column>
		</display:table>
	</body>
</html>
