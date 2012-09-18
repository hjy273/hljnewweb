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
		<title>�������һ����</title>
	</head>
	<template:titile value="���������Ϣ" />
		<html:form action="/problemCableAction.do?method=saveProblem"
			styleId="saveProblem" >
			<input type="hidden" name="pid" value="${problem.id}"/>
			<table width="80%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
				    <tr class=trcolor>
				      <td class="tdulleft">�м̶Σ�</td>
				      <td class="tdulright"><c:out value="${problem.cableName}"></c:out></td>
				    </tr>
				     <tr class=trwhite>
				      <td class="tdulleft">����������</td>
				      <td class="tdulright">				       
				       <c:out value="${problem.problemDescription}"></c:out>
				     </td>
				    </tr>
				    <tr class=trcolor>
				      <td class="tdulleft">�������˵����</td>
				      <td class="tdulright" >				       
				       ${problem.processComment}
				     </td>
				    </tr>
				    <tr class=trwhite>
				      <td class="tdulleft">ԭ�������</td>
				      <td class="tdulright" >				       
				      ${problem.reason}
				     </td>
				    </tr>
				     <tr class=trcolor>
				      <td class="tdulleft">�ƻ������ʩ��</td>
				      <td class="tdulright" >				      
				      ${problem.testMethod}
				     </td>
				    </tr>
				     <tr class=trwhite>
				      <td class="tdulleft">�ƻ����ʱ�䣺</td>
				      <td class="tdulright" >				        
				         <bean:write name="problem" property="testTime" format="yyyy/MM/dd"/>
				     </td>
				    </tr>
				     <tr class=trcolor>
				      <td class="tdulleft">���״̬��</td>
				      <td class="tdulright" >	
				      	<c:if test="${problem.problemState=='0'}">δ���</c:if>	
				      	<c:if test="${problem.problemState=='1'}">�ѽ��</c:if>			 		       
				     </td>
				    </tr>
				    <tr>
				      <td align="center" colspan="2">				       
				        <input type="button" class="button" value="����" onclick="javascript:history.back();"/>
				     </td>
				    </tr>
				  </table>
		</html:form>
	</body>
</html>
