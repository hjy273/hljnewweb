<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.*" %>
<html>
	<head>
		<title>������ѯ</title>
		<script type="text/javascript">
			function submitForm(){
				var flag = true;
			
				
				/*
				if(document.getElementById("module_catalog").value.length==0){
				alert("�������Ͳ���Ϊ�գ�");
					document.getElementById("module_catalog").focus();
					flag=false;
					return;
				}
				if(document.getElementById("beginTime").value.length==0){
					alert("��ʼʱ�䲻��Ϊ�գ�");
					document.getElementById("beginTime").focus();
					flag=false;
					return;
				}
				if(document.getElementById("endTime").value.length==0){
					alert("����ʱ�䲻��Ϊ�գ�");
					document.getElementById("endTime").focus();
					flag=false;
					return;
				}
				if(document.getElementById("endTime").value<document.getElementById("beginTime").value){
				alert("����ʱ�䲻�ܴ��ڿ�ʼʱ�䣡");
					document.getElementById("endTime").focus();
					flag=false;
					return;
				}*/
				
				if(flag==true){
					submitForm1.submit();
				}
			}
		</script>
	</head>
	<body>

			<template:titile value="������ѯ"/>
		<form action="/WebApp/AnnexAction.do?method=queryAnnex" method="post" id="submitForm1">
		<template:formTable >
			<template:formTr name="��������" isOdd="false">
				<select name="module_catalog" id="module_catalog" style="width:155px"  class="inputtext">
								<option value="">ȫ��</option>																
								<c:forEach items="${module_catalog_list}" var="module_catalog">
								<c:set var="selected" value=""></c:set>
								<c:if test="${module_catalog==requestMap['moduleCatalog']}">
									<c:set var="selected" value="selected=\"selected\""/>
								</c:if>
								<option value="${module_catalog}" ${selected}>${module_catalog}</option>
								</c:forEach>
							</select>
			</template:formTr>
			<template:formTr name="�ϴ��û�" isOdd="false">
				<apptag:setSelectOptions columnName2="userid" columnName1="username" valueName="users" condition="state is null" tableName="userinfo"/>
			        <select name="uploader" id="uploader" class="inputtext" style="width:155px" >
			        	<option value="">ȫ��</option>
			        	<c:forEach var="bean" items="${users}">
			        		<c:set var="selected" value=""></c:set>
								<c:if test="${bean['label']==requestMap['uploader']}">
								<c:set var="selected" value="selected=\"selected\""> </c:set>
								</c:if>
			        		<option value="${bean['label']}" ${selected }>${bean['label']}</option>
			        	</c:forEach>
			        </select>
			</template:formTr>
			<template:formTr name="��������" isOdd="false">
				<input name="original_name" type="text" value="${requestMap['originalName']}"  class="inputtext"/>
			</template:formTr>
			<template:formTr name="�ϴ�ʱ��" isOdd="false">
				<input name="beginTime" value="${requestMap['beginTime'] }" class="Wdate" id="beginTime" style="width:150"
							onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss'})" readonly/> 
						- 
						<input name="endTime" value="${requestMap['endTime'] }" class="Wdate" id="endTime" style="width:150"
							onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss'})" readonly/> 
			</template:formTr>
			</template:formTable>
			<table width="90%" border="0" align="center" cellpadding="3"
				cellspacing="0">
				<tr class=trwhite heigth="40">
				<td colspan="2" align="center">
						<input property="action" type="button" onclick="submitForm()" value="��ѯ" class="button"></input> &nbsp;&nbsp;&nbsp;&nbsp;
						<input property="action" type="reset" value="��д" class="button"></input>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
