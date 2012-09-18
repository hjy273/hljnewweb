<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<html>
	<head>
		<title>���Լƻ�</title>
			<script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
			<script language="javascript" src="${ctx}/js/validation/effects.js" type=""></script>
			<script language="javascript" src="${ctx}/js/validation/validation_cn.js" type=""></script>
			<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">
			
		
		<script type="text/javascript">
		function checkAddInfo() {
			var testBeginDate =testPlanBean.testBeginDate.value;
			if(testBeginDate==""){
				alert("�ƻ����Կ�ʼʱ�䲻��Ϊ��!");
				return false;
			}
			
			var testEndDate =testPlanBean.testEndDate.value;
			if(testEndDate==""){
				alert("�ƻ����Խ���ʱ�䲻��Ϊ��!");
				return false;
			}
			if(testBeginDate!=""){
				 var endyear = testBeginDate.substring(0,4);
		         var endmonth = parseInt(testBeginDate.substring(5,7) ,10) - 1;
		         var endday = parseInt(testBeginDate.substring(8,10),10);
		         var theenddate = new Date(endyear,endmonth,endday,"23","59","59");
				 var myDate = new Date();
				 if(theenddate <myDate){
				 	alert("�ƻ����Կ�ʼʱ�䲻�����ڵ�ǰ����!");
					return false;
				}
			}
			if(testBeginDate>testEndDate){
				alert("�ƻ����Խ���ʱ�䲻�����ڿ�ʼʱ��!");
				return false;
			}
			var approvers = document.getElementById("approvers").value;
			if(approvers==""){
				alert("��ѡ����Լƻ������!");
		  		return false;
	  		}
			return true;
		}
		
		
	</script>
	</head>

	<body>
		
		<template:titile value="�޸Ĳ��Լƻ�" />
		<html:form action="/testPlanAction.do?method=addTestPlan&act=edit"
			styleId="saveTestPlan" onsubmit="return checkAddInfo();">
			<html:hidden property="id" name="testPlan"/>
			<html:hidden property="approveTimes" name="testPlan"/>
			<table width="80%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
				    <tr class=trcolor>
				      <td class="tdulleft">��ά��λ��</td>
				      <td class="tdulright"><c:out value="${LOGIN_USER.deptName}"></c:out></td>
				      <td class="tdulleft">�ƻ��ƶ��ˣ�</td>
				      <td class="tdulright"><c:out value="${LOGIN_USER.userName}"></c:out></td>
				    </tr>
				    <tr class=trwhite>
				      <td class="tdulleft">�ƻ����ƣ�</td>
				      <td class="tdulright" colspan="3">
				          <html:text property="testPlanName"  value="${testPlan.testPlanName}"styleClass="required" style="width:205"></html:text>&nbsp;&nbsp;<font color="red">*</font>
				      </td>
				    </tr>
				    <tr class=trcolor>
				      <td class="tdulleft">�ƻ����ͣ�</td>
				      <td class="tdulright" colspan="3">
					      <c:if test="${testPlan.testPlanType=='1'}">
					        <input type="radio" name="testPlanType" checked="checked" value="1" />
							        ���˲���
							        <input type="radio" name="testPlanType" value="2" />
							        �ӵص������
					     </c:if>
					      <c:if test="${testPlan.testPlanType=='2'}">
					        <input type="radio" name="testPlanType"  value="1" />
							        ���˲���
							        <input type="radio" name="testPlanType" checked="checked" value="2" />
							        �ӵص������
					     </c:if>
				     </td>
				    </tr>
				    <tr class=trwhite>
				      <td class="tdulleft">�ƻ��������ڣ�</td>
				      <td class="tdulright" colspan="3">
				      <input name="testBeginDate" id="testBeginDate" class="Wdate" style="width:205" value="<bean:write name="testPlan" property="testBeginDate" format="yyyy/MM/dd"/>" 
					onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',maxDate: '#F{$dp.$D(\'testEndDate\')}',minDate: '%y-%M-%d-%H-%m-%s'})" readonly/>
				      
				      ��
				    
				    <input name="testEndDate" id="testEndDate" class="Wdate" style="width:205" value="<bean:write name="testPlan" property="testEndDate" format="yyyy/MM/dd"/>" 
					onfocus="WdatePicker({dateFmt:'yyyy/MM/dd',minDate: '%y-%M-%d-%H-%m-%s'})" readonly/>&nbsp;&nbsp;<font color="red">*</font>
				    
				      </div></td>
				    </tr>
				 
				    <tr class=trcolor>
				      <td class="tdulleft">��ע��</td>
				      <td class="tdulright" colspan="3">				       
				       <html:textarea property="testPlanRemark" value="${testPlan.testPlanRemark}" styleClass="max-length-512" rows="4" style="width:375px"></html:textarea>	       
				     </td>
				    </tr>
				     <tr class=trcolor>
				    	<apptag:approverselect inputName="approvers,mobiles" label="�����" 
				    	colSpan="4" inputType="radio" notAllowName="reads"  approverType="approver" 
				    	objectType="LP_TEST_PLAN" objectId="${testPlan.id}" 
				    	></apptag:approverselect>
				     </tr>
				      <tr class=trcolor>
				    	<apptag:approverselect inputName="reads,rmobiles" label="������"
				    	 colSpan="4" spanId="reader" notAllowName="approvers" approverType="reader"
				    	 objectType="LP_TEST_PLAN" objectId="${testPlan.id}" 
				    	 ></apptag:approverselect>
				     </tr>
				    <tr>
				      <td align="center" colspan="4">				       
				        <html:submit styleClass="button" value="��һ��"></html:submit>
				        <input type="button" class="button" value="����" onclick="javascript:history.back();"/>
				     </td>
				    </tr>
				  </table>
		</html:form>
		<script type="text/javascript">
			function formCallback(result, form) {
				window.status = "valiation callback for form '" + form.id + "': result = " + result;
			}

			var valid = new Validation('saveTestPlan', {immediate : true, onFormValidate : formCallback});
	</script>
	</body>
</html>
