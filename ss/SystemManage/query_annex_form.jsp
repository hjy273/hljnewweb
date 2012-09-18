<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.*" %>
<html>
	<head>
		<title>附件查询</title>
		<script type="text/javascript">
			function submitForm(){
				var flag = true;
			
				
				/*
				if(document.getElementById("module_catalog").value.length==0){
				alert("附件类型不能为空！");
					document.getElementById("module_catalog").focus();
					flag=false;
					return;
				}
				if(document.getElementById("beginTime").value.length==0){
					alert("开始时间不能为空！");
					document.getElementById("beginTime").focus();
					flag=false;
					return;
				}
				if(document.getElementById("endTime").value.length==0){
					alert("结束时间不能为空！");
					document.getElementById("endTime").focus();
					flag=false;
					return;
				}
				if(document.getElementById("endTime").value<document.getElementById("beginTime").value){
				alert("结束时间不能大于开始时间！");
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

			<template:titile value="附件查询"/>
		<form action="/WebApp/AnnexAction.do?method=queryAnnex" method="post" id="submitForm1">
		<template:formTable >
			<template:formTr name="附件类型" isOdd="false">
				<select name="module_catalog" id="module_catalog" style="width:155px"  class="inputtext">
								<option value="">全部</option>																
								<c:forEach items="${module_catalog_list}" var="module_catalog">
								<c:set var="selected" value=""></c:set>
								<c:if test="${module_catalog==requestMap['moduleCatalog']}">
									<c:set var="selected" value="selected=\"selected\""/>
								</c:if>
								<option value="${module_catalog}" ${selected}>${module_catalog}</option>
								</c:forEach>
							</select>
			</template:formTr>
			<template:formTr name="上传用户" isOdd="false">
				<apptag:setSelectOptions columnName2="userid" columnName1="username" valueName="users" condition="state is null" tableName="userinfo"/>
			        <select name="uploader" id="uploader" class="inputtext" style="width:155px" >
			        	<option value="">全部</option>
			        	<c:forEach var="bean" items="${users}">
			        		<c:set var="selected" value=""></c:set>
								<c:if test="${bean['label']==requestMap['uploader']}">
								<c:set var="selected" value="selected=\"selected\""> </c:set>
								</c:if>
			        		<option value="${bean['label']}" ${selected }>${bean['label']}</option>
			        	</c:forEach>
			        </select>
			</template:formTr>
			<template:formTr name="附件名称" isOdd="false">
				<input name="original_name" type="text" value="${requestMap['originalName']}"  class="inputtext"/>
			</template:formTr>
			<template:formTr name="上传时间" isOdd="false">
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
						<input property="action" type="button" onclick="submitForm()" value="查询" class="button"></input> &nbsp;&nbsp;&nbsp;&nbsp;
						<input property="action" type="reset" value="重写" class="button"></input>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
