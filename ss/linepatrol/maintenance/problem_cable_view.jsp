<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
		<script language="javascript" src="${ctx}/js/validation/effects.js" type=""></script>
		<script language="javascript" src="${ctx}/js/validation/validation_cn.js" type=""></script>
		<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" language="javascript">
		function  goBack(){
			var url="${ctx}/problemCableAction.do?method=getProblemCables";
			self.location.replace(url);
		}
				
		</script>
		<title>问题光缆一览表</title>
	</head>
	<template:titile value="问题光缆信息" />
		<html:form action="/problemCableAction.do?method=saveProblem"
			styleId="saveProblem" >
			<input type="hidden" name="pid" value="${problem.id}"/>
			<table width="80%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
				    <tr class=trcolor>
				      <td class="tdulleft">中继段：</td>
				      <td class="tdulright"><c:out value="${problem.cableName}"></c:out></td>
				    </tr>
				     <tr class=trwhite>
				      <td class="tdulleft">问题描述：</td>
				      <td class="tdulright">				       
				       <c:out value="${problem.problemDescription}"></c:out>
				     </td>
				    </tr>
				    <tr class=trcolor>
				      <td class="tdulleft">处理跟踪说明：</td>
				      <td class="tdulright" >				       
				       ${problem.processComment}
				     </td>
				    </tr>
				    <tr class=trwhite>
				      <td class="tdulleft">原因分析：</td>
				      <td class="tdulright" >				       
				      ${problem.reason}
				     </td>
				    </tr>
				     <tr class=trcolor>
				      <td class="tdulleft">计划解决措施：</td>
				      <td class="tdulright" >				      
				      ${problem.testMethod}
				     </td>
				    </tr>
				     <tr class=trwhite>
				      <td class="tdulleft">计划解决时间：</td>
				      <td class="tdulright" >				        
				         <bean:write name="problem" property="testTime" format="yyyy/MM/dd"/>
				     </td>
				    </tr>
				     <tr class=trcolor>
				      <td class="tdulleft">解决状态：</td>
				      <td class="tdulright" >	
				      	<c:if test="${problem.problemState=='0'}">未解决</c:if>	
				      	<c:if test="${problem.problemState=='1'}">已解决</c:if>			 		       
				     </td>
				    </tr>
				    <tr>
				      <td align="center" colspan="2">				       
				        <input type="button" class="button" value="返回" onclick="javascript:history.back();"/>
				     </td>
				    </tr>
				  </table>
		</html:form>
	</body>
</html>
