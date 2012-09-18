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
		<title>问题光缆一览表</title>
	</head>
	<template:titile value="处理问题光缆" />
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
				       <html:textarea property="processComment" value="${problem.processComment}"  styleClass="required max-length-512" rows="4" style="width:375px"></html:textarea>&nbsp;&nbsp;<font color="red">*</font>       
				     </td>
				    </tr>
				    <tr class=trwhite>
				      <td class="tdulleft">原因分析：</td>
				      <td class="tdulright" >				       
				       <html:textarea property="reason" value="${problem.reason}"  styleClass="required max-length-512" rows="4" style="width:375px"></html:textarea>&nbsp;&nbsp;<font color="red">*</font>	       
				     </td>
				    </tr>
				     <tr class=trcolor>
				      <td class="tdulleft">计划解决措施：</td>
				      <td class="tdulright" >				      
				       <html:textarea property="testMethod" value="${problem.testMethod}"  styleClass="required max-length-512" rows="4" style="width:375px"></html:textarea>&nbsp;&nbsp;<font color="red">*</font>       
				     </td>
				    </tr>
				     <tr class=trwhite>
				      <td class="tdulleft">计划解决时间：</td>
				      <td class="tdulright" >				        
				      <input name="testTime" class="Wdate" style="width:205"
					onfocus="WdatePicker({dateFmt:'yyyy/MM/dd'})" value="<bean:write name="problem" property="testTime" format="yyyy/MM/dd"/>" readonly/>
				     </td>
				    </tr>
				     <tr class=trcolor>
				      <td class="tdulleft">解决状态：</td>
				      <td class="tdulright" >				       
				      	<select name="problemState">
				      		<option value="0">未解决</option>
				      		<option value="1">已解决</option>
				      	</select>
				     </td>
				    </tr>
				    <tr>
				      <td align="center" colspan="2">				       
				        <html:submit styleClass="button" value="保存"></html:submit>
				        <input type="button" class="button" value="返回" onclick="goBack();"/>
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
