<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />
<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
<html>
	<head>
		<title>������ѯ�б�</title>
		<script type="text/javascript">
	deleteAnnex = function() {
		if (confirm("ȷ��ɾ���ü�¼��")) {
			submitForm.submit();
		}
	}
	goback = function() {
		var url = "${ctx}/AnnexAction.do?method=queryAnnexForm";
		self.location.replace(url);
	}
	updateFileNameForm=function(fileId){
		var url="${ctx}/AnnexAction.do?method=updateFileNameForm&&file_id="+fileId;
		window.open(url,'','width=300,height=200,top=0,left=0,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no');
	}
	selectAll=function(){
		var allCheckBoxs=document.getElementsByName("id");
		if("select"==document.getElementById("select").value){
			document.getElementById("select").value="notselect";
			for(var i=0;i<allCheckBoxs.length;i++){
				allCheckBoxs[i].checked=true;
			}
		}
		else{
			document.getElementById("select").value="select";
			for(var i=0;i<allCheckBoxs.length;i++){
				allCheckBoxs[i].checked=false;
			}
		}
	}
</script>
	</head>
	<%  BasicDynaBean object=null;
		Object fileid=null; 
		Object originalname=null;
		Object id=null;
	 %>
	<body>
		<template:titile value="������ѯ�б�"/>
		<form action="/WebApp/AnnexAction.do?method=deleteAnnex" id="submitForm" method="post">
		<display:table name="sessionScope.annex_list"  pagesize="18" id="annex_view">
		<% object = (BasicDynaBean ) pageContext.findAttribute("annex_view"); 
		if(object!=null){
	               fileid = object.get("fileid"); 
	               originalname=object.get("originalname"); 
	               id=object.get("id"); 
	    %>
		<display:column >
			<input type="checkbox" name="id" value="<%=id %>" />
		</display:column>
			<display:column  property="module_catalog" title="ҵ��ģ��" headerClass="subject"  sortable="true">
			</display:column>
			
			<display:column title="�ļ���" headerClass="subject"  sortable="true">		
	                <a href="/WebApp/download.do?method=download&fileid=<%=fileid %>"><%=originalname %></a>
			</display:column>
			<display:column property="filetype" title="�ļ�����" headerClass="subject"  sortable="true"/>
			<display:column property="filesize" title="�ļ���С(KB)" headerClass="subject"  sortable="true"/>
			<display:column property="username" title="�ϴ���" headerClass="subject"  sortable="true"/>
			<display:column title="�ϴ�ʱ��" headerClass="subject"  sortable="true">
				<bean:write name="annex_view" property="upload_date"
						format="yyyy-MM-dd HH:mm:ss" />
			</display:column>
			<display:column >
				<a href="javascript:updateFileNameForm('<%=id %>');">�޸��ļ���</a>
			</display:column>
			<%} %>
		</display:table>
		<div style="width=100%;height:50px;text-align:center;">
			<input type="hidden" name="select" id="select" value="select"/>
			<html:button property="button" styleClass="button" onclick="javascript:selectAll();">ȫѡ/ȫ��ѡ</html:button>
			<html:button property="button" styleClass="button" onclick="javascript:deleteAnnex();">ɾ��</html:button>
			<html:button property="button" styleClass="button" onclick="javascript:goback();">����</html:button>
		</div>
		</form>
	</body>
</html>

