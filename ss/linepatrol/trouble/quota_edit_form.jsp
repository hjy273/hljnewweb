<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>���ϻ�׼ָ��</title>
		    <script language="javascript" src="${ctx}/js/validation/prototype.js" type=""></script>
			<script language="javascript" src="${ctx}/js/validation/effects.js" type=""></script>
			<script language="javascript" src="${ctx}/js/validation/validation_cn.js" type=""></script>
			<link href="${ctx}/js/validation/styles/style_min.css" rel="stylesheet" type="text/css">
		
	</head>

	<body>
		<template:titile value="�޸Ĺ��ϻ�׼ָ��" />
		<html:form action="/troubleQuotaAction.do?method=editQuota"
			styleId="quotaInfo">
			<input type="hidden" name="id" value="${quota.id}" />
			<input type="hidden" name="idCity" value="${quotacity.id}" />
			<table width="70%" border="0" align="center" cellpadding="3" cellspacing="0" class="tabout">
			  <tr>
			    <td colspan="4" bgcolor="#E6EFF7" height="25px" align="center">
			    	<font size="2px" style="font-weight: bold">һ�ɹ���ָ��</font>
			    </td>
			  </tr>
			  <tr  class=trwhite>
			    <td colspan="4">һ��ǧ���������ʱ</td>
			  </tr>
			  <tr>
			    <td class="tdulleft">��׼ֵ��</td>
			    <td class="tdulright"><html:text property="interdictionTimeNormValue" value="${quota.interdictionTimeNormValue}" styleClass="required validate-integer-float-double"></html:text></td>
			    <td class="tdulleft">��սֵ��</td>
			    <td class="tdulright"><html:text property="interdictionTimeDareValue" value="${quota.interdictionTimeDareValue}" styleClass="required validate-integer-float-double"></html:text></td>
			  </tr>
			  <tr  class=trwhite>
			    <td colspan="4">һ��ǧ������ϴ���</td>
			  </tr>
			  <tr>
			    <td class="tdulleft">��׼ֵ��</td>
			    <td class="tdulright"><html:text property="interdictionTimesNormValue" value="${quota.interdictionTimesNormValue}" styleClass="required validate-integer-float-double"></html:text></td>
			    <td class="tdulleft">��սֵ��</td>
			    <td class="tdulright"><html:text property="interdictionTimesDareValue" value="${quota.interdictionTimesDareValue}" styleClass="required validate-integer-float-double"></html:text></td>
			  </tr>
			  <tr  class=trwhite>
			    <td colspan="4">һ�ɹ���ƽ��������ʱ</td>
			  </tr>
			  <tr>
			    <td class="tdulleft">��׼ֵ��</td>
			    <td class="tdulright"><html:text property="rtrTimeNormValue" value="${quota.rtrTimeNormValue}" styleClass="required validate-integer-float-double"></html:text></td>
			    <td class="tdulleft">��սֵ��</td>
			    <td class="tdulright"><html:text property="rtrTimeDareValue" value="${quota.rtrTimeDareValue}"  styleClass="required validate-integer-float-double"></html:text></td>
			  </tr>
			  <tr  class=trwhite>
			    <td colspan="4">һ�ɵ���������ʱ</td>
			  </tr>
			  <tr>
			    <td class="tdulleft">��׼ֵ��</td>
			    <td class="tdulright" colspan="3"><html:text property="singleRtrTimeNormValue" value="${quota.singleRtrTimeNormValue}" styleClass="required validate-integer-float-double"></html:text></td>
			  
			  </tr>
			  <tr> 
			    <td colspan="4" height="25px" bgcolor="#E6EFF7" align="center">
			    	<font size="2px" style="font-weight: bold">����������ָ��</font>
			    </td>
			  </tr>
			  <tr  class=trwhite>
			    <td colspan="4">������ǧ���������ʱ</td>
			  </tr>
			  <tr>
			    <td class="tdulleft">��׼ֵ��</td>
			    <td class="tdulright"><html:text property="interdictionTimeNormValueCity" value="${quotacity.interdictionTimeNormValue}" styleClass="required validate-integer-float-double"></html:text></td>
			    <td class="tdulleft">��սֵ��</td>
			    <td class="tdulright"><html:text property="interdictionTimeDareValueCity" value="${quotacity.interdictionTimeDareValue}" styleClass="required validate-integer-float-double"></html:text></td>
			  </tr>
			  <tr  class=trwhite>
			    <td colspan="4">������ǧ������ϴ���</td>
			  </tr>
			  <tr>
			    <td class="tdulleft">��׼ֵ��</td>
			    <td class="tdulright"><html:text property="interdictionTimesNormValueCity" value="${quotacity.interdictionTimesNormValue}" styleClass="required validate-integer-float-double"></html:text></td>
			    <td class="tdulleft">��սֵ��</td>
			    <td class="tdulright"><html:text property="interdictionTimesDareValueCity" value="${quotacity.interdictionTimesDareValue}" styleClass="required validate-integer-float-double"></html:text></td>
			  </tr>
			  <tr  class=trwhite>
			    <td colspan="4">����������ƽ��������ʱ</td>
			  </tr>
			  <tr>
			    <td class="tdulleft">��׼ֵ��</td>
			    <td class="tdulright"><html:text property="rtrTimeNormValueCity" value="${quotacity.rtrTimeNormValue}" styleClass="required validate-integer-float-double"></html:text></td>
			    <td class="tdulleft">��սֵ��</td>
			    <td class="tdulright"><html:text property="rtrTimeDareValueCity" value="${quotacity.rtrTimeDareValue}"  styleClass="required validate-integer-float-double"></html:text></td>
			  </tr>
			  <tr  class=trwhite> 
			    <td colspan="4">����������������ʱ</td>
			  </tr>
			  <tr>
			    <td class="tdulleft">��׼ֵ��</td>
			    <td class="tdulright" colspan="3"><html:text property="singleRtrTimeNormValueCity"  value="${quotacity.singleRtrTimeNormValue}" styleClass="required validate-integer-float-double"></html:text></td>
			  </tr>
			  <tr align="center">
				  <td colspan="4">
					  <html:submit property="action" styleClass="button" value="�ύ"></html:submit>
					  <html:reset property="action" styleClass="button">����</html:reset>
				  </td>
			  </tr>
			</table>
		</html:form>
		<script type="text/javascript">
			function formCallback(result, form) {
				window.status = "valiation callback for form '" + form.id + "': result = " + result;
			}

			var valid = new Validation('quotaInfo', {immediate : true, onFormValidate : formCallback});
		</script>
	</body>
</html>
