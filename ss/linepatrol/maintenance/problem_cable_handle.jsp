<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
		<script language="javascript" src="${ctx}/js/validation/effects.js" type=""></script>
		<script language="javascript" src="${ctx}/js/validation/validation_cn.js" type=""></script>
		<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" language="javascript">
			handleProblemForm=function(){
			var url="${ctx}/problemCableAction.do?method=handleProblemForm";
			self.location.replace(url);
		}
				
				
		exportList=function(){
			var url="${ctx}/testPlanQueryStatAction.do?method=exportStatTestPlans";
			self.location.replace(url);
		}
		
		function  goBack(){
			var url="${ctx}/problemCableAction.do?method=getProblemCables";
			self.location.replace(url);
		}
				
		</script>
		<title>�������һ����</title>
	</head>
	<template:titile value="�����������" />
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
				       <html:textarea property="processComment" value="${problem.processComment}"  styleClass="required max-length-512" rows="4" style="width:375px"></html:textarea>&nbsp;&nbsp;<font color="red">*</font>       
				     </td>
				    </tr>
				    <tr class=trwhite>
				      <td class="tdulleft">ԭ�������</td>
				      <td class="tdulright" >				       
				       <html:textarea property="reason" value="${problem.reason}"  styleClass="required max-length-512" rows="4" style="width:375px"></html:textarea>&nbsp;&nbsp;<font color="red">*</font>	       
				     </td>
				    </tr>
				     <tr class=trcolor>
				      <td class="tdulleft">�ƻ������ʩ��</td>
				      <td class="tdulright" >				      
				       <html:textarea property="testMethod" value="${problem.testMethod}"  styleClass="required max-length-512" rows="4" style="width:375px"></html:textarea>&nbsp;&nbsp;<font color="red">*</font>       
				     </td>
				    </tr>
				     <tr class=trwhite>
				      <td class="tdulleft">�ƻ����ʱ�䣺</td>
				      <td class="tdulright" >				        
				      <input name="testTime" class="Wdate" style="width:205"
					onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" value="<bean:write name="problem" property="testTime" format="yyyy/MM/dd"/>" readonly/>
				     </td>
				    </tr>
				     <tr class=trcolor>
				      <td class="tdulleft">���״̬��</td>
				      <td class="tdulright" >				       
				      	<select name="problemState">
				      		<option value="0">δ���</option>
				      		<option value="1">�ѽ��</option>
				      	</select>
				     </td>
				    </tr>
				    <tr>
				      <td align="center" colspan="2">				       
				        <html:submit styleClass="button" value="����"></html:submit>
				        <input type="button" class="button" value="����" onclick="goBack();"/>
				     </td>
				    </tr>
				  </table>
		</html:form>
		<script type="text/javascript">
			function formCallback(result, form) {
				window.status = "valiation callback for form '" + form.id + "': result = " + result;
			}

			var valid = new Validation('saveProblem', {immediate : true, onFormValidate : formCallback});
	</script>
	</body>
</html>
